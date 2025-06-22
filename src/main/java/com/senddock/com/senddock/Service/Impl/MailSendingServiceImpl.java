package com.senddock.com.senddock.Service.Impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senddock.com.senddock.Entity.EMailSending;
import com.senddock.com.senddock.Mapper.MailSendingMapper;
import com.senddock.com.senddock.Model.MailSending;
import com.senddock.com.senddock.Model.MailSendingRequest;
import com.senddock.com.senddock.Model.SendGrid.Attachment;
import com.senddock.com.senddock.Model.SendGrid.From;
import com.senddock.com.senddock.Model.SendGrid.Personalization;
import com.senddock.com.senddock.Model.SendGrid.SendgridEmail;
import com.senddock.com.senddock.Model.SendGrid.To;
import com.senddock.com.senddock.Repository.MailSendingRepository;
import com.senddock.com.senddock.Service.MailSendingService;

@Service
public class MailSendingServiceImpl implements MailSendingService {

	@Autowired
	private MailSendingRepository productCampaignRepository;

	@Autowired
	private MailSendingMapper productCampaignMapper;

	@Autowired
	private MailSendingRepository repository;
	@Override
	public List<MailSending> saveCampaignsAndSendMail(MailSendingRequest productCampaignRequest, String name) {
	    MultipartFile file = productCampaignRequest.getFile();
	    List<MailSending> savedCampaigns = new ArrayList<>();

	    try (InputStream inputStream = file.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {
	        Sheet sheet = workbook.getSheetAt(0);

	        int processedRows = 0;
	        int skippedRows = 0;

	        for (Row row : sheet) {
	            if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                skippedRows++;
	                continue; 
	            }

	            String serial = getString(row, 0);
	            String source = getString(row, 1);
	            String email = getString(row, 2);
	            String status = getString(row, 3);

	            if (email == null || email.isEmpty()) {
	                skippedRows++;
	                continue;
	            }

	            EMailSending campaign = EMailSending.builder()
	                    .serial(serial)
	                    .source(source)
	                    .email(email)
	                    .status(status)
	                    .name(name)
	                    .totalSentTimes(1)
	                    .createdAt(new Timestamp(System.currentTimeMillis()))
	                    .emailSentTime(new Timestamp(System.currentTimeMillis()))
	                    .build();

	            MailSending model = productCampaignMapper.toModel(productCampaignRepository.save(campaign));
	            savedCampaigns.add(model);
	            processedRows++;
	        }

	        MultipartFile pdfFile = productCampaignRequest.getPdf();
	        sendEmailToUsers(savedCampaigns, pdfFile);

	        System.out.println("Processed: " + processedRows + " | Skipped: " + skippedRows);

	    } catch (IOException e) {
	        throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
	    }

	    return savedCampaigns;
	}
	
	private String getString(Row row, int cellIndex) {
	    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
	    if (cell == null) return "";
	    if (cell.getCellType() == CellType.STRING) return cell.getStringCellValue().trim();
	    if (cell.getCellType() == CellType.NUMERIC) return String.valueOf((long) cell.getNumericCellValue());
	    return cell.toString().trim();
	}

	private boolean isRowEmpty(Row row) {
	    for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
	        if (cell != null && cell.getCellType() != CellType.BLANK) {
	            return false;
	        }
	    }
	    return true;
	}

	
	public List<MailSending> sendEmailToUsers(List<MailSending> productCampaigns, MultipartFile pdfFile) {

		String subject = "Resume sending mail";
		String templateId = "d-bdd5ab1224f84afb83500da92f487735";

		
//		String sendGridKey = your sengrid key here;
		String getSupport = "ankushbalharacse@gmail.com";

		ArrayList<Personalization> personalizations = new ArrayList<>();
		for (MailSending r : productCampaigns) {
			if (r.getEmail() == null || r.getEmail().isEmpty()) {
				continue;
			}

			Personalization p = new Personalization();

			To to = new To();
			to.setEmail(r.getEmail());
			ArrayList<To> toList = new ArrayList<>();
			toList.add(to);
			p.setTo(toList);

			Map<String, Object> dynamicData = new HashMap<>();
			dynamicData.put("name", r.getName());
//			    dynamicData.put("subject", subject);
			p.setDynamic_template_data(dynamicData);
//				if (emailTemplateRequest.getCcEmail() != null && !emailTemplateRequest.getCcEmail().isEmpty()) {
//					Cc ccObj = new Cc();
//					ccObj.setEmail(emailTemplateRequest.getCcEmail());
//					ArrayList<Cc> ccList = new ArrayList<>();
//					ccList.add(ccObj);
//					p.setCc(ccList);
//				}
			SendgridEmail sendgridEmail = new SendgridEmail();
			From from = new From();
			from.setEmail(getSupport);
			sendgridEmail.setFrom(from);
			sendgridEmail.setTemplate_id(templateId);
//			sendgridEmail.setAsm(emailTemplateRequest.getAsm());
//				sendgridEmail.setSubject(subject);
			
			personalizations.add(p);
			sendgridEmail.setPersonalizations(personalizations);
			
			
			if (pdfFile != null && !pdfFile.isEmpty()) {
	            try {
	                Attachment attachment = new Attachment();
	                attachment.setContent(Base64.getEncoder().encodeToString(pdfFile.getBytes()));
	                attachment.setType(pdfFile.getContentType());
	                attachment.setFilename(pdfFile.getOriginalFilename());
	                attachment.setDisposition("attachment");

	                sendgridEmail.setAttachments(Collections.singletonList(attachment));
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

			sendMailForCampaignUser(sendgridEmail, sendGridKey, to.getEmail());
			personalizations = new ArrayList<Personalization>();

		}

		return productCampaigns;

	}

	public void sendMailForCampaignUser(SendgridEmail sendgridEmail, String sendGridKey, String email) {

		System.out.println("sendgridEmail#############" + sendgridEmail);
		String sendgridEmailAPIUrl = "https://api.sendgrid.com/v3/mail/send";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Bearer " + sendGridKey);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(sendgridEmail);
//			System.out.println(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		HttpEntity<SendgridEmail> request = new HttpEntity<>(sendgridEmail, headers);
		System.out.println(sendgridEmail);
		try {
			ResponseEntity<String> response = restTemplate.exchange(sendgridEmailAPIUrl, HttpMethod.POST, request,
					String.class);

			String messageId = response.getHeaders().get("X-Message-Id").get(0);
			System.out.println("message id is ===========>>>>" + messageId);

			System.out.println(response.getStatusCode());
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
