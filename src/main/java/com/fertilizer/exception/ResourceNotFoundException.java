package com.fertilizer.exception;

/**
 * @author Dhiraj
 *
 */

public class ResourceNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String key;
	private final String data;

	public ResourceNotFoundException() {
		super();
		this.key = null;
		this.data = null;
	}

	public ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.key = null;
		this.data = null;
	}

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
		this.key = null;
		this.data = null;
	}

	public ResourceNotFoundException(String message) {
		super(message);
		this.key = null;
		this.data = null;
	}

	public ResourceNotFoundException(Throwable cause) {
		super(cause);
		this.key = null;
		this.data = null;
	}

	public ResourceNotFoundException(String key, String data) {
		super(String.format("%s can't be found'", data));
		this.key = key;
		this.data = data;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

}
