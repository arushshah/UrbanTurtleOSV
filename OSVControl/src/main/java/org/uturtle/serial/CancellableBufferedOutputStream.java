package org.uturtle.serial;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

public class CancellableBufferedOutputStream extends BufferedOutputStream {

	public CancellableBufferedOutputStream(OutputStream out) {
		super(out);
	}

	public void cancelBuffer() {
		count = 0;
	}

}
