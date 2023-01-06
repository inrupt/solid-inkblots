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

import java.util.UUID;

public final class SolidCommons {
	public static final int DEF_PORT = 6001;

	public static String randomUuid() {
		return UUID.randomUUID().toString();
	}

	public static String toHttpUrl(String type, String id, int port) {
		return new StringBuilder("http://localhost:")
			.append(port > 0 ? port : DEF_PORT).append("/")
			.append(type).append("/")
			.append(id).append("/")
			.toString();
	}

	public static String toDirectUrl(String type, String id, int port) {
		return new StringBuilder("direct:")
			.append(type).append("-")
			.append(id).append("-")
			.append(port > 0 ? port : DEF_PORT)
			.toString();
	}

	private SolidCommons() {
		// do nothing; utility class
	}
}
