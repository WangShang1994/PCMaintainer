package com.donald.server.maintainer;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping("/killtask")
	public void killTask(@RequestParam(name = "pid") String pid, HttpServletResponse resp) throws IOException {
		TaskList.killTaskProcess(pid);
		resp.sendRedirect("/tasklist");
	}

	@GetMapping("/videoCenterList")
	public String videoCenterList(@Value("${spring.video.center.path}") String path) {
		return path;
	}
}
