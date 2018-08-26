package com.amazonaws.lambda.demo.util;

import java.util.logging.Level;

public interface BaseLogger {
	public void log(Level level, String msg);
	public void log(Level level, Throwable error);
}
