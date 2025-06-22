package com.senddock.com.senddock.Model.SendGrid;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendgridOrderItem {
	
	private String productQrCode;
	
	private String productName;
	
	private String service;
	
	private String variant;
	
	private String serviceProvider;
	
	private String serviceDate;
	
	private String productQuantity;
	
	private String productPrice;
	
	private String productTax;
	
	private Map<String,String> bookingInfo;
	
	private Map<String,Object> ticketDetails;
	
	private String itemType;
	
	private String subTotal;
	
}
