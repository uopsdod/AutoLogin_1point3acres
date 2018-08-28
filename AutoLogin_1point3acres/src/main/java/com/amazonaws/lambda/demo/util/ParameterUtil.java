package com.amazonaws.lambda.demo.util;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.DeleteParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.DeleteParameterResult;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathResult;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersResult;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;
import com.amazonaws.services.simplesystemsmanagement.model.ParameterType;
import com.amazonaws.services.simplesystemsmanagement.model.PutParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.PutParameterResult;

public class ParameterUtil {
	static private final AWSSimpleSystemsManagement client = AWSSimpleSystemsManagementClientBuilder.defaultClient();
	
	/**
	 * I intend to treat this path in parameter store as the table concept in rdb
	 * @author sam
	 *
	 */
	public enum Table {
		USER("user")
		,UNIT_TEST("unit_test");
		private String name;

		private Table(String name) {
			/** add project name for every table **/
			/** forward slash is required here **/
			this.name = PathBuilder.root("/" + Constants.projectName).go(name).build();
		}

		public String getName() {
			return name;
		}
		
	}
	
	/**
	 * use this to build path string 
	 * @author sam
	 *
	 */
	public static class PathBuilder{
		private StringBuilder finalPath = new StringBuilder();
		public PathBuilder(String path) {
			this.finalPath.append(path);
		}
		public static PathBuilder root(String path) {
			return new PathBuilder(path);
		}
		public PathBuilder go(String path) {
			this.finalPath.append("/").append(path);
			return this;
		}
		public String build() {
			return this.finalPath.toString();
		}
	}
	
	/**
	 * get
	 * @param path
	 * @return
	 */
	public static GetParametersByPathResult getParametersByPath(String path) {

		GetParametersByPathRequest request = new GetParametersByPathRequest();
		request.withPath(path).isWithDecryption();
		
		GetParametersByPathResult parametersByPath = client.getParametersByPath(request);

		return parametersByPath;
	}	
	
	/**
	 * create update
	 * @param name
	 * @param value
	 * @return
	 */
	public static PutParameterResult putParameterStr(String name, String value) {

		PutParameterRequest request = new PutParameterRequest();
		request.withName(name).withValue(value).withType(ParameterType.String)
				.withDescription("decription here")
				.setOverwrite(true);

		PutParameterResult putParameterResult = client.putParameter(request);
		System.out.println("hey");
		return putParameterResult;
	}
	
	/**
	 * create, update
	 * @param name
	 * @param value
	 * @return
	 */
	public static PutParameterResult putParameterSecuredStr(String name, String value) {

		PutParameterRequest request = new PutParameterRequest();
		request.withName(name).withValue(value).withType(ParameterType.SecureString)
				.withDescription("decription here")
				.setOverwrite(true);

		PutParameterResult putParameterResult = client.putParameter(request);
		System.out.println("hey");
		return putParameterResult;
	}
	
	/**
	 * delete all parameters in a specific path 
	 * @param path
	 * @return
	 */
	public static int deleteParameterbyPath(String path) {
		GetParametersByPathResult parametersByPathResult = getParametersByPath(path);
		int deleteCount = 0;
		for (Parameter param : parametersByPathResult.getParameters()) {
			String name = param.getName();
			DeleteParameterResult deleteParameterResult = deleteParameter(name);
			if (200 == deleteParameterResult.getSdkHttpMetadata().getHttpStatusCode()) {
				deleteCount++;
			}
		}
		return deleteCount;
	}
	
	/**
	 * delete
	 * @param name
	 * @return
	 */
	public static DeleteParameterResult deleteParameter(String name) {

		DeleteParameterRequest request = new DeleteParameterRequest();
		request.withName(name);
		
		DeleteParameterResult deleteParameterResult = client.deleteParameter(request);
		System.out.println("hey");
		
		return deleteParameterResult;
	}
	
}
