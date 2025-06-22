package com.senddock.com.senddock.Model;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailSending {

	private Long id;

	private String serial;

	private String source;

	private String email;

	private String status;
	
	
	private String name;
	
	private Integer totalSentTimes;

	private Timestamp emailSentTime;

}
