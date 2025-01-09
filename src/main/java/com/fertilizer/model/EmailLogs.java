package com.fertilizer.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

import com.fertilizer.enums.BooleanEnum;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Table(name = "email_logs")
@Entity
@Getter
@Setter
@DynamicUpdate
public class EmailLogs extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String mailSubject;

	private String mailBody;

	private Long userId;

	private String mailTo;

	private String mailCc;

	private Long mailEventId;

	private Long mailParentId;

	private Long mailSendAttempt;

	private BooleanEnum mailHasAttachment;

	private String mailAttachmentFile;

	private String mailError;

	private LocalDateTime mailToBeSentAt;

	private LocalDateTime mailSentAt;
}
