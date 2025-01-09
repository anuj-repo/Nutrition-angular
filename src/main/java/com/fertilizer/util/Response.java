package com.fertilizer.util;

import java.util.Date;
import java.util.Map;

import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Dhiraj
 *
 * @param <T>
 */
@Getter
@Setter
public class Response<T> {

	private T data;

	private Long total;

	private Long totalEditRequestCount;

	private String message;

	private Map<String, Object> fieldError;

	private final Date timeStamp = new Date();
	
	private  Integer count;
	
	private Boolean status;

	public Response() {

	}

	public Response(T data, Long total, String message, Map<String, Object> fieldError) {
		this.data = data;
		this.total = total;
		this.message = message;
		this.fieldError = fieldError;
	}

	public Response(T data, Long total, String message) {
		this.data = data;
		this.total = total;
		this.message = message;
	}

	public Response(T data, Long total, Long totalEditRequestCount, String message) {
		this.data = data;
		this.total = total;
		this.message = message;
		this.totalEditRequestCount = totalEditRequestCount;
	}

	public Response(T data, String message, Map<String, Object> fieldError) {
		this.data = data;
		this.message = message;
		this.fieldError = fieldError;
	}

	public Response(T data, String message) {
		this.data = data;
		this.message = message;
		this.status = true;
	}

	public Response(T data, Map<String, Object> fieldError) {
		this.data = data;
		this.fieldError = fieldError;
	}

	public Response(T data) {
		this.data = data;
	}

	public Response(String message) {
		this.data = null;
		this.status = false;
		this.message = message;
	}
public Response(String message,Integer count) {
		
		this.message = message;
		this.count=count;
	}
	public Response(Map<String, Object> fieldError) {
		this.fieldError = fieldError;
	}

	public Response(Map<String, Object> fieldError, String message) {
		this.fieldError = fieldError;
		this.message = message;
	}
	public Response(Map<String, Object> fieldError, String message,int count) {
		this.fieldError = fieldError;
		this.message = message;
		this.count=count;
	}

	public static <T> Response<T> ok(@Nullable T data, String message) {
		return new Response<>(data, message);
	}

	public static <T> Response<T> ok(String message) {
		return new Response<>(message);
	}

	public static <T> Response<T> ok(@Nullable T data, Long total, String message) {
		return new Response<>(data, total, message);
	}
	public static <T> Response<T> ok(@Nullable T data, Long total, Long totalEditRequestCount, String message) {
		return new Response<>(data, total, totalEditRequestCount,message);
	}
	@Override
	public String toString() {
		return "Response [data=" + data + ", total=" + total + ", totalEditRequestCount=" + totalEditRequestCount + ", message=" + message + ", fieldError=" + fieldError
				+ ", timeStamp=" + timeStamp + ", count=" + count + ", status=" + status + "]";
	}
}
