package com.fertilizer.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DynamicUpdate
@Table(name = "mail_event_types")
public class MailEventTypes extends BaseModel {
	private static final long serialVersionUID = 2711216039399842150L;

	private String eventName;

	private String eventIdentifier;
}
