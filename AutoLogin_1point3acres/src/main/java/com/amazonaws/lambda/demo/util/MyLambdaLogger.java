package com.amazonaws.lambda.demo.util;

import java.util.logging.Level;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class MyLambdaLogger implements BaseLogger{
	private LambdaLogger logger;
	
	public MyLambdaLogger(LambdaLogger logger) {
		super();
		this.logger = logger;
	}



	@Override
	public void log(Level level, String msg) {
		this.logger.log(level.getName() + " - " + msg);
	}

}
