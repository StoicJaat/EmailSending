package com.senddock.com.senddock.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senddock.com.senddock.Entity.EMailSending;

@Repository
public interface MailSendingRepository extends JpaRepository<EMailSending, Long> {
}
