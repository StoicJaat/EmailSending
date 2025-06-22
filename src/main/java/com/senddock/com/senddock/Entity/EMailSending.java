package com.senddock.com.senddock.Entity;


import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_campaign")
public class EMailSending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "serial")
    private String serial;

    @Column(name = "source")
    private String source;

    @Column(name = "email")
    private String email;
    
    @Column(name = "status")
    private String status;
    


    @Column(name = "name")
    private String name;

    @Column(name = "total_sent_times")
    private Integer totalSentTimes;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "email_sent_time")
    private Timestamp emailSentTime;
}
