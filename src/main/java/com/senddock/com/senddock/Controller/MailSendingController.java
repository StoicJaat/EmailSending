package com.senddock.com.senddock.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.senddock.com.senddock.Model.MailSending;
import com.senddock.com.senddock.Model.MailSendingRequest;
import com.senddock.com.senddock.Service.MailSendingService;

@RestController
@RequestMapping("/api/product/campaign")
public class MailSendingController {
	
	
	
	  @Autowired
	    private MailSendingService productCampaignService;
	    
	    @PostMapping("/send")
	    public ResponseEntity<List<MailSending>> sendCampaign(
	            @RequestParam("name") String name,
	            @RequestParam("file") MultipartFile file,
	            @RequestParam(required = false)  MultipartFile pdfFile) {
	        
	        MailSendingRequest request = MailSendingRequest.builder()
	                .name(name)
	                .file(file)
	                .pdf(pdfFile) 
	                .build();
	        
	        List<MailSending> campaigns = productCampaignService.saveCampaignsAndSendMail(request, name);
	        return ResponseEntity.ok(campaigns);
	    }

	
}

