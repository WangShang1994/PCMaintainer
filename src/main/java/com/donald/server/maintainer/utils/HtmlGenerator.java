package com.donald.server.maintainer.utils;

import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HtmlGenerator {

	private HtmlGenerator() {

	}

	public static String generateTable(Map<String, String> titleKeyMap, String jsonString) throws Exception {
		StringBuilder html = new StringBuilder("<table border=\"1\">");
		JsonNode nodeList = new ObjectMapper().readTree(jsonString);
		Iterator<JsonNode> it = nodeList.elements();
		int index = 1;
		while (it.hasNext()) {
			if (index == 1) {
				html.append("<tr>");
				html.append("<th>" + "#" + "</th>");
				titleKeyMap.values().forEach(v -> html.append("<th>").append(v).append("</th>"));
				html.append("</tr>");

			}
			html.append("<tr>");
			html.append("<td>" + (index++) + "</td>");
			titleKeyMap.entrySet().forEach(e -> {
				JsonNode node = it.next();
				String value = node.findPath(e.getKey()).textValue();
				html.append("<td>").append(value).append("</td>");

			});
			html.append("</tr>");
		}
		html.append("</table>");
		System.out.println(html.toString());
		return html.toString();
	}

}
