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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class SimpleServerTest extends CamelTestSupport {

	/* Usual way of setting up routes
    protected RoutesBuilder createRouteBuilder() {
    	return PatternedCamel.simpleServer();
    }
    */

	// Aggregating multiple RoutesBuilder(s) is also possible
    protected RoutesBuilder[] createRouteBuilders() {
        return new RoutesBuilder[] {
            PatternedCamel.simpleServer(),
            new RouteBuilder() {
				public void configure() throws Exception {
	                from("direct:alternative").to(PatternedCamel.DIRECT_HELLO);
				}}
        };
    }

    @Test
    void httpServerTest() {
    	sayHello(PatternedCamel.URL_HELLO);
    }
    
    @Test
    void directMemoryRequestTest() {
    	sayHello(PatternedCamel.DIRECT_HELLO);
    }

    @Test
    void alternativePathTest() {
    	sayHello("direct:alternative");
    }

    private void sayHello(String url) {
        NotifyBuilder notify = new NotifyBuilder(context).whenCompleted(1).create();

        String result = fluentTemplate()
            //.withHeader(...)
            //.withHeaders(...)
            //.withBody(...)
	        .to(url)
	        .request(String.class);

        assertAll("Server said Hello",
            () -> assertTrue(notify.matches(5, TimeUnit.SECONDS)),
            () -> assertTrue(result.startsWith("Hello at: ")));
    }
}

