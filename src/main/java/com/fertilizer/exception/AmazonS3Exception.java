package com.fertilizer.exception;

/**
 * @author Imran
 *
 */
public class AmazonS3Exception extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5307764312079013051L;

	public AmazonS3Exception() {
		super();
	}

	public AmazonS3Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AmazonS3Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public AmazonS3Exception(String message) {
		super(message);
	}

	public AmazonS3Exception(Throwable cause) {
		super(cause);
	}
}
