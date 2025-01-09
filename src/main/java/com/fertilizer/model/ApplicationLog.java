package com.fertilizer.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "application_logs")
@DynamicUpdate
public class ApplicationLog extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@CreatedBy
	private Long userId;
	private String requestUri;
	private String requestPayload;
	private String requestMethod;
	private String response;
	private String responseStatus;
	private String applicationError;
	private String methodName;
	private String className;
	private String lineNo;
	private String remarks;
	private String serverIp;
	private String clientIp;
	@CreatedDate
	@Column(updatable = false)
	// @JsonIgnore
	protected Date requestDate;
	
	@Override
	public String toString() {
		return "ApplicationLog [userId=" + userId + ", requestUri=" + requestUri + ", requestPayload=" + requestPayload
				+ ", requestMethod=" + requestMethod + ", response=" + response + ", responseStatus=" + responseStatus
				+ ", applicationError=" + applicationError + ", methodName=" + methodName + ", className=" + className
				+ ", lineNo=" + lineNo + ", remarks=" + remarks + ", serverIp=" + serverIp + ", clientIp=" + clientIp
				+ ", requestDate=" + requestDate + ", status=" + status + ", id=" + id + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + "]";
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRequestUri() {
		return requestUri;
	}
	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}
	public String getRequestPayload() {
		return requestPayload;
	}
	public void setRequestPayload(String requestPayload) {
		this.requestPayload = requestPayload;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getApplicationError() {
		return applicationError;
	}
	public void setApplicationError(String applicationError) {
		this.applicationError = applicationError;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

}
