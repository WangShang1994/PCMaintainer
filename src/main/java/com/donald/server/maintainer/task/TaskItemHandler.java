/*
 * COPYRIGHT. China Systems Co., Ltd. 2020. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of China Systems Co., Ltd.
 */
package com.donald.server.maintainer.task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class TaskItemHandler {

	private static final Logger logger = LoggerFactory.getLogger("SYSTEM");

	private List<Integer> indexs = new ArrayList<>();

	public List<TaskItem> getResult(final InputStream result) {

		List<TaskItem> rsList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(result))) {
			String line = "";
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\r\n");
				if (isSpliter(line)) {
					getIndex(line);
					continue;
				}
				if (isTaskLine(line) && !StringUtils.isEmpty(line.trim())) {
					rsList.add(buildTaskItem(line));
				}
			}
			logger.info("CMD Execute Result: \r\n {} ", sb);
		} catch (Exception e) {
			logger.error("Build TaskItem Error!", e);
		}

		return rsList;
	}

	private boolean isTaskLine(final String line) {

		for (String s : Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")) {
			if (line.contains(s)) {
				return true;
			}
		}
		return false;
	}

	private boolean isSpliter(final String line) {

		return line.startsWith("=") && line.endsWith("=");
	}

	private void getIndex(final String line) {

		StringBuilder sb = new StringBuilder(line);
		this.indexs.add(0);
		int i = sb.indexOf(" ", 0);
		while (i != -1) {
			this.indexs.add(i);
			this.indexs.add(i + 1);
			i = sb.indexOf(" ", i + 1);
		}
		this.indexs.add(line.length() - 1);
	}

	private TaskItem buildTaskItem(final String line) {

		TaskItem t = new TaskItem();
		ListIterator<Integer> it = this.indexs.listIterator();
		int i = 0;
		while (it.hasNext()) {
			switch (i) {
			case 0:
				t.setName(line.substring(it.next(), it.next()).trim());
				break;
			case 1:
				t.setPid(line.substring(it.next(), it.next()).trim());
				break;
			case 4:
				t.setMemory(line.substring(it.next(), it.next()).trim());
				i = 0;
				break;
			default:
				it.next();
				it.next();
				break;
			}
			i++;
		}
		return t;
	}

}
