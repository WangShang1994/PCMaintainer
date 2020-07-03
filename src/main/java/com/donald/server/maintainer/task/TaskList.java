package com.donald.server.maintainer.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {

	private TaskList() {

	}

	public static List<TaskItem> getAllTaskItems() throws Exception {

		List<TaskItem> rsList = new ArrayList<>();
		ProcessBuilder pb = new ProcessBuilder("tasklist");
		Process p = pb.start();
		try {
			TaskItemHandler handler = new TaskItemHandler();
			rsList = handler.getResult(p.getInputStream());
		} catch (Exception e) {
			throw e;
		}
		return rsList;
	}

	public static void killTaskProcess(final String pId) throws IOException {

		Runtime.getRuntime().exec("taskkill /F /PID " + pId);
	}

}
