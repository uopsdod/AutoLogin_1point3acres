package com.amazonaws.lambda.demo.util;

public class DailyRewardUtil {
	private static BaseLogger logger;

	public static BaseLogger getLogger() {
		return logger;
	}

	public static void setLogger(BaseLogger logger) {
		DailyRewardUtil.logger = logger;
	}
	
}
