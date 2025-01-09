package com.fertilizer.exception;

/**
 * @author Dhiraj
 *
 */
public class RoleNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -249359710012391217L;

	public RoleNotFoundException() {
		super();
	}

	public RoleNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public RoleNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RoleNotFoundException(String arg0) {
		super(arg0);
	}

	public RoleNotFoundException(Throwable arg0) {
		super(arg0);
	}
}