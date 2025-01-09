package com.fertilizer.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * @author Dhiraj
 *
 */
public class MyServletOutputStreamWrapper extends ServletOutputStream {

    private OutputStream outputStream;
    private ByteArrayOutputStream copy;

    public MyServletOutputStreamWrapper(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.copy = new ByteArrayOutputStream(1024);
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
        copy.write(b);
    }

    public byte[] getCopy() {
        return copy.toByteArray();
    }

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setWriteListener(WriteListener listener) {
		//not required to override
	}

}