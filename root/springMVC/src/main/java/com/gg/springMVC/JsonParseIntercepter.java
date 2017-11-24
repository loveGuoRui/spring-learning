package com.gg.springMVC;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class JsonParseIntercepter implements HandlerInterceptor {

	private Log logger = LogFactory.getLog(this.getClass());

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		this.logger.debug("json parse start" + request.getContentType());
		
		String url = request.getRequestURL().toString();
		int start = url.lastIndexOf("/") + 1;
		int end = url.lastIndexOf(".");
		String tranId = url.substring(start, end == -1 ? url.length() : end);
		request.setAttribute("tranID", tranId);
		
		if (request.getContentType() !=null && request.getContentType().indexOf("application/json") >= 0) {

			byte[] bytes = this.getStream(request);
			
			if (bytes == null) {
				return true;
			}
			
			if (this.logger.isDebugEnabled()) {
				this.logger.debug(new String(bytes, "utf-8"));
			}
			
			String jsonStr = new String(bytes, "utf-8");
			if (jsonStr == null || jsonStr.trim().length() == 0) {
				return true;
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Map requestParams = new HashMap();
			if (jsonStr.startsWith("[")) {
				List list = objectMapper.readValue(jsonStr, List.class);
				request.setAttribute("List", list);
				requestParams.put("List", list);
			} else {
				Map map = objectMapper.readValue(jsonStr, Map.class);
				if (map != null) {
					Iterator it = map.keySet().iterator();
					while (it.hasNext()) {
						Object key = it.next();
						request.setAttribute(key.toString(), map.get(key));
						requestParams.put(key.toString(), map.get(key));
					}
				}
			}
			
			request.setAttribute("RequestParams", requestParams);

		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	private byte[] getStream(HttpServletRequest request) throws Exception {
		int contentLength = request.getContentLength();

		byte[] content = new byte[contentLength];

		int offset = 0;
		while (offset < contentLength) {
			try {
				int realLength = request.getInputStream().read(content, offset, contentLength - offset);
				if (realLength == -1) {
					return null;
				}
				offset += realLength;
			} catch (IOException e) {
				this.logger.error("error", e);
				throw new Exception("request_isnot_a_valid_stream");
			}

		}

		return content;
	}

}
