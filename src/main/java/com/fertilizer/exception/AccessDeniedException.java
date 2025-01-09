package com.fertilizer.exception;

import com.fertilizer.enums.Activity;

public class AccessDeniedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3488094480136890393L;
	private final Activity activity;

	public AccessDeniedException(String msg) {
		super(msg);
		this.activity=null;
	}

	public AccessDeniedException(String msg, Activity activity) {
		super(msg);
		this.activity=activity;
	}

	public Activity getActivity() {
		return activity;
	}
}
