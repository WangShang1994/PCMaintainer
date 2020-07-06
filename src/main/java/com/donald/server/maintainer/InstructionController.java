package com.donald.server.maintainer;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donald.server.maintainer.task.TaskList;
import com.donald.server.maintainer.utils.HtmlGenerator;
import com.donald.server.maintainer.utils.JsonUtils;

/**
 * @author donald
 *
 */
@RestController
public class InstructionController {

	private static final Logger logger = LoggerFactory.getLogger("SYSTEM");

	@GetMapping("/tasklist")
	public String getTaskList() throws Exception {
		Map<String, String> titleMap = new LinkedHashMap<>();
		titleMap.put("name", "NAME");
		titleMap.put("pid", "PID");
		titleMap.put("memory", "MEMORY");
		String jsonStr = JsonUtils.getJsonString(TaskList.getAllTaskItems());
		logger.info("Json String: \r\n {}", jsonStr);
		return HtmlGenerator.generateTable(titleMap, jsonStr);
	}
}
