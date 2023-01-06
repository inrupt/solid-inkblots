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
package com.inrupt.inkblots.solid;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StorageService extends RouteBuilder {
	private static final Logger LOG = LoggerFactory.getLogger(StorageService.class);

	private String id;
    private int port = 0;

    public StorageService() {
    	this(SolidCommons.randomUuid());
    }

    public StorageService(String id) { 
        this.id = id;
    }

	public void configure() throws Exception {
        String hu = SolidCommons.toHttpUrl("storage", id, port);
        String du = SolidCommons.toDirectUrl("storage", id, port);

        LOG.info("creating routes");

        from("jetty:" + hu).to(du);
        from(du)
            .setBody(simple("Hello at: ${date:now:yyyy-MM-dd'T'HH:mm:ssZ}"));
	}
}
