package com.senddock.com.senddock.Model.SendGrid;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class To {
	 public String email ;
	 
	@Override
	public String toString() {
		return "To [email=" + email + "]";
	}

}
