package com.amazonaws.lambda.demo.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DailyRewardUtil {
	private static BaseLogger logger;
	
	static {
		Logger fileLogger = LogManager.getLogger("util.fileLogger");
		setLogger(new MyLog4jLogger(fileLogger));
	}

	public static BaseLogger getLogger() {
		return logger;
	}

	public static void setLogger(BaseLogger logger) {
		DailyRewardUtil.logger = logger;
	}
	
}
