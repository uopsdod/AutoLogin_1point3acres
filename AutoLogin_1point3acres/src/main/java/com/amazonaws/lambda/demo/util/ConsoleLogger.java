package com.amazonaws.lambda.demo.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class ConsoleLogger implements BaseLogger{
	
	public ConsoleLogger() {
		super();
	}

	@Override
	public void log(Level level, String msg) {
		System.out.println(level.getName() + " - " + msg);
	}

	@Override
	public void log(Level level, Throwable error) {
		/** get the whole stack trace of error **/
	    StringWriter trace = new StringWriter();
	    error.printStackTrace(new PrintWriter(trace));
	    String eMsg = trace.toString();
	    
	    this.log(level, eMsg);
	}

}
