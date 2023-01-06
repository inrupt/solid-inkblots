/*
 * Copyright 2022 Inrupt Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.inrupt.inkblots.basic;

import java.util.concurrent.CountDownLatch;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public final class PatternedCamel {
	public final static String URL_HELLO = "http://localhost:6001/hello";
	public final static String URL_BYE = "http://localhost:6001/bye";
	public final static String DIRECT_HELLO = "direct:hello";
	public final static String DIRECT_BYE = "direct:bye";

	private final static CountDownLatch CTRL_C = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {

        // add shutdown hook to wait for ctrl-c
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
            	CTRL_C.countDown();
            }
        });

        // create a CamelContext
        try (CamelContext camel = new DefaultCamelContext()) {
            camel.addRoutes(simpleServer());
            camel.start(); // non-blocking, hence the need for the shutdown hook (above)

            try {
            	CTRL_C.await();
            } catch (InterruptedException e) {
            }
        }
    }
	
	public static RouteBuilder simpleServer() {
        return new RouteBuilder() {
	        public void configure() {
                from("jetty:" + URL_HELLO).to(DIRECT_HELLO);
                from(DIRECT_HELLO)
                    .setBody(simple("Hello at: ${date:now:yyyy-MM-dd'T'HH:mm:ssZ}"));

                from("jetty:" + URL_BYE).to(DIRECT_BYE);
                from(DIRECT_BYE).setBody(simple("Goodbye now..."));
		    }
		};
	}
}
