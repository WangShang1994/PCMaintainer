package com.donald.server.maintainer.utils;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HtmlGenerator {

	private static Logger logger = LoggerFactory.getLogger("SYSTEM");

	private HtmlGenerator() {

	}

	public static String generateTable(Map<String, String> titleKeyMap, String jsonString) throws Exception {
		StringBuilder html = new StringBuilder("<table border=\"1\">");
		JsonNode nodeList = new ObjectMapper().readTree(jsonString);
		for (int i = 0; i < nodeList.size(); i++) {
			if (i == 1) {
				html.append("<tr>");
				html.append("<th>" + "#" + "</th>");
				titleKeyMap.values().forEach(v -> html.append("<th>").append(v).append("</th>"));
				html.append("</tr>");
			}
			html.append("<tr>");
			html.append("<td>" + (i) + "</td>");
			JsonNode node = nodeList.get(i);
			for (String key : titleKeyMap.keySet()) {
				String value = node.findPath(key).textValue();
				html.append("<td>").append(value).append("</td>");
			}
			html.append("</tr>");
		}
		html.append("</table>");
		logger.info("HTML:\r\n {}", html);
		return html.toString();
	}

}
