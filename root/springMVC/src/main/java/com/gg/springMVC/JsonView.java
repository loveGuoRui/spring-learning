package com.gg.springMVC;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class JsonView extends MappingJackson2JsonView {
	
	private Log log = LogFactory.getLog(JsonView.class);
	
	protected Object filterModel(Map<String, Object> model) {
		return null;
	}

}
