package com.donald.server.maintainer.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskList {

	private static final Logger logger = LoggerFactory.getLogger("SYSTEM");

	private TaskList() {

	}

	public static List<TaskItem> getAllTaskItems() {

		List<TaskItem> rsList = new ArrayList<>();
		ProcessBuilder pb = new ProcessBuilder("tasklist");
		try {
			Process p = pb.start();
			TaskItemHandler handler = new TaskItemHandler();
			rsList = handler.getResult(p.getInputStream());
		} catch (Exception e) {
			logger.error("Execute tasklist Error!", e);
		}
		return rsList;
	}

	public static void killTaskProcess(final String pId) throws IOException {

		Runtime.getRuntime().exec("taskkill /F /PID " + pId);
	}

}
