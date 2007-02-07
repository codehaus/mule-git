/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.core.distribution;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple decoration of an InputStream
 * 
 * @author jmoller
 *
 */
class DecoratingInputStream extends InputStream {
	public DecoratingInputStream(InputStream delegate) {
		this.delegate = delegate;
	}
	
	private InputStream delegate;

	public int available() throws IOException {
		return delegate.available();
	}

	public void close() throws IOException {
		delegate.close();
	}

	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	public int hashCode() {
		return delegate.hashCode();
	}

	public void mark(int readlimit) {
		delegate.mark(readlimit);
	}

	public boolean markSupported() {
		return delegate.markSupported();
	}

	public int read() throws IOException {
		return delegate.read();
	}

	public int read(byte[] b, int off, int len) throws IOException {
		return delegate.read(b, off, len);
	}

	public int read(byte[] b) throws IOException {
		return delegate.read(b);
	}

	public void reset() throws IOException {
		delegate.reset();
	}

	public long skip(long n) throws IOException {
		return delegate.skip(n);
	}

	public String toString() {
		return delegate.toString();
	}
}