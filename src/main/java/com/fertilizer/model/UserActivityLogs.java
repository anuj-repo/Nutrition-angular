package com.fertilizer.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Dhiraj
 *
 */
@Entity
@Table(name="user_activity_logs")
@DynamicUpdate
@Getter
@Setter
public class UserActivityLogs extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8959668451134416892L;
	
	private Long entityId;
	
	private String activityIdentifier;
	
	private Long activityId;

	private String requestParams;
	
	private String requestIp;
	
	private String macAddress;
	
	private String requestDevice;
	
	//@Enumerated(EnumType.STRING)
	private String requestMethod;
	
	private String requestUri;
	
	private Integer responseCode;
	
	private String responseError;
	
	private String response;

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getActivityIdentifier() {
		return activityIdentifier;
	}

	public void setActivityIdentifier(String activityIdentifier) {
		this.activityIdentifier = activityIdentifier;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getRequestDevice() {
		return requestDevice;
	}

	public void setRequestDevice(String requestDevice) {
		this.requestDevice = requestDevice;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseError() {
		return responseError;
	}

	public void setResponseError(String responseError) {
		this.responseError = responseError;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	
}
