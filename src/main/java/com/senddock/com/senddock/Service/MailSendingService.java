package com.senddock.com.senddock.Service;

import java.util.List;

import com.senddock.com.senddock.Model.MailSending;
import com.senddock.com.senddock.Model.MailSendingRequest;

public interface MailSendingService {
	
	
	public List<MailSending>  saveCampaignsAndSendMail(MailSendingRequest productCampaignRequest, String name );
	
//	public List<ProductCampaign> sendEmailToUsers(List<ProductCampaign> productCampaigns);
	

}
