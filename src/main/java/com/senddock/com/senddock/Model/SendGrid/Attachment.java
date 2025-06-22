package com.senddock.com.senddock.Model.SendGrid;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {

	private String content;        
    private String type;        
    private String filename;      
    private String disposition;   
    private String contentId; 
}
