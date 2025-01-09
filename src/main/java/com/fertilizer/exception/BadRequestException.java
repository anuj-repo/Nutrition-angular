package com.fertilizer.exception;

import com.fertilizer.enums.Activity;
import com.fertilizer.util.MessageConstant;

public class BadRequestException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 311744294701581861L;

	private final String key;
	private final String data;
	private final Activity activity;
	private final MessageConstant msg;

	public BadRequestException(String message) {
		super(message);
		this.activity = null;
		this.key = null;
		this.data = null;
		this.msg=null;
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
		this.activity = null;
		this.key = null;
		this.data = null;
		this.msg=null;
	}

	public BadRequestException(String key, String data) {
		super(String.format("%s", data));
		this.activity = null;
		this.key = key;
		this.data = data;
		this.msg=null;
	}

	public BadRequestException(String key, String data, Activity activity) {
		super(String.format("%s", data));
		this.activity = activity;
		this.key = key;
		this.data = data;
		this.msg=null;
	}

	public BadRequestException(String message, Activity activity) {
		super(message);
		this.activity = activity;
		this.key = null;
		this.data = null;
		this.msg=null;
	}
	
	public BadRequestException(MessageConstant msg) {
		this.activity = null;
		this.key = null;
		this.data = null;
		this.msg=msg;
	}

	public String getKey() {
		return key;
	}

	public String getData() {
		return data;
	}

	public Activity getActivity() {
		return activity;
	}

	public MessageConstant getMsg() {
		return msg;
	}
	
	

}
