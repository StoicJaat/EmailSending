package com.senddock.com.senddock.Model.SendGrid;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class SendgridEmail {
	
	public ArrayList<Personalization> personalizations ;
    public From from;
    public String template_id;
    public String subject;
    public ASM asm;
    private List<Attachment> attachments; 
	@Override
	public String toString() {
		return "SendgridEmail [personalizations=" + personalizations + ", from=" + from + ", template_id=" + template_id
				+ ", subject=" + subject + "]";
	}

}
