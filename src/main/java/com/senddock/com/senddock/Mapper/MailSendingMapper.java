package com.senddock.com.senddock.Mapper;

import org.springframework.stereotype.Component;

import com.senddock.com.senddock.Entity.EMailSending;
import com.senddock.com.senddock.Model.MailSending;


@Component
public class MailSendingMapper {

	public  MailSending toModel(EMailSending entity) {
		if (entity == null)
			return null;

		return MailSending.builder().id(entity.getId())
				.email(entity.getEmail())
				.serial(entity.getSerial())
				.source(entity.getSource())
				.status(entity.getStatus())
				.name(entity.getName())
				.totalSentTimes(entity.getTotalSentTimes())
				.emailSentTime(entity.getEmailSentTime())
				.build();
	}

}
