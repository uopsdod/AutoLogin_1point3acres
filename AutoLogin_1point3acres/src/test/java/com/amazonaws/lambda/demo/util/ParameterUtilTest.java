package com.amazonaws.lambda.demo.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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

@RunWith(MockitoJUnitRunner.class)
public class ParameterUtilTest {
    @Before
    public void setUp() throws IOException {
    	
    }
    
    @Test
    public void crudTest() {
    	GetParametersByPathResult resultNow = null;
    	/** delete all data in a table **/
		int deleteCount = ParameterUtil.deleteParameterbyPath(ParameterUtil.PathBuilder.root(ParameterUtil.Table.UNIT_TEST.getName()).build());
		resultNow = ParameterUtil.getParametersByPath(ParameterUtil.Table.UNIT_TEST.getName());
		Assert.assertEquals(resultNow.getParameters().size(), 0);
		
		/** put **/
		PutParameterResult putParameterResult = null;
		putParameterResult = ParameterUtil.putParameterStr(ParameterUtil.PathBuilder.root(ParameterUtil.Table.UNIT_TEST.getName()).go("tom").build(),"88888100");
		putParameterResult = ParameterUtil.putParameterStr(ParameterUtil.PathBuilder.root(ParameterUtil.Table.UNIT_TEST.getName()).go("kim").build(),"88888102");
		putParameterResult = ParameterUtil.putParameterSecuredStr(ParameterUtil.PathBuilder.root(ParameterUtil.Table.UNIT_TEST.getName()).go("jane").build(),"88888103");
		putParameterResult = ParameterUtil.putParameterSecuredStr(ParameterUtil.PathBuilder.root(ParameterUtil.Table.UNIT_TEST.getName()).go("lucy").build(),"88888104");
		resultNow = ParameterUtil.getParametersByPath(ParameterUtil.Table.UNIT_TEST.getName());
		Assert.assertEquals(resultNow.getParameters().size(), 4);
		
		/** delete **/
		DeleteParameterResult deleteParameterResult = null;
		deleteParameterResult = ParameterUtil.deleteParameter(ParameterUtil.PathBuilder.root(ParameterUtil.Table.UNIT_TEST.getName()).go("tom").build());
		deleteParameterResult = ParameterUtil.deleteParameter(ParameterUtil.PathBuilder.root(ParameterUtil.Table.UNIT_TEST.getName()).go("lucy").build());
		resultNow = ParameterUtil.getParametersByPath(ParameterUtil.PathBuilder.root(ParameterUtil.Table.UNIT_TEST.getName()).build());
		Assert.assertEquals(resultNow.getParameters().size(), 2);
		
    	/** delete all data in a table **/
		deleteCount = ParameterUtil.deleteParameterbyPath(ParameterUtil.PathBuilder.root(ParameterUtil.Table.UNIT_TEST.getName()).build());
		resultNow = ParameterUtil.getParametersByPath(ParameterUtil.Table.UNIT_TEST.getName());
		Assert.assertEquals(resultNow.getParameters().size(), 0);
		
		
//		Map<String, String> parameterStore = ParameterUtil.parameterStore();
		System.out.println("hey");
	}
}
