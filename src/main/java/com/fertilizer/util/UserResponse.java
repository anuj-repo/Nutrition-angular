package com.fertilizer.util;

import java.util.Map;

/**
 * @author Dhiraj
 *
 * @param <T>
 */
public class UserResponse<T> extends Response<T> {

	private Long unassignedCount;

	public UserResponse() {
		super();

	}

	public UserResponse(T data, Long total, String message, Map<String, Object> fieldError, Long unassignedCount) {
		super(data, total, message, fieldError);
		this.unassignedCount = unassignedCount;
	}

	public UserResponse(T data, Long total, String message,Long unassignedCount) {
		super(data, total, message);
		this.unassignedCount = unassignedCount;
	}

	public UserResponse(T data, String message, Map<String, Object> fieldError) {
		super(data, message, fieldError);
	}

	public UserResponse(T data, String message) {
		super(data, message);
	}

	public UserResponse(T data, String message, Long unassignedCount) {
		super(data, message);
		this.unassignedCount = unassignedCount;
	}

	public UserResponse(T data, Map<String, Object> fieldError) {
		super(data, fieldError);
	}

	public UserResponse(T data) {
		super(data);
	}

	public UserResponse(String message) {
		super(message);
	}

	public UserResponse(Map<String, Object> fieldError) {
		super(fieldError);
	}

	public UserResponse(Map<String, Object> fieldError, String message) {
		super(fieldError, message);
	}

	public Long getUnassignedCount() {
		return unassignedCount;
	}

	public void setUnassignedCount(Long unassignedCount) {
		this.unassignedCount = unassignedCount;
	}
}