package com.senddock.com.senddock.Model.SendGrid;

import java.util.ArrayList;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Personalization {

	public ArrayList<To> to;

	public ArrayList<Cc> cc;

	public Map<String, Object> dynamic_template_data;

	public Map<String, String> dynamic_template_datas;

	@Override
	public String toString() {
		return "Personalization [to=" + to +", cc=" + cc +  ", dynamic_template_data=" + dynamic_template_data
				+ ", dynamic_template_datas=" + dynamic_template_datas + "]";
	}

}
