/**
 * Copyright 2011 Massimo Gaddini
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  
 */
package com.sgxmobileapps.androidsqlhelper.test;


import static org.junit.Assert.assertTrue;

import com.sgxmobileapps.androidsqlhelper.processor.model.Schema;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;



/**
 * @author Massimo Gaddini
 *
 */
public class SchemaTestCase {
    protected static File mInDir;
    protected static Writer mOutputWriter; 
    
    @Rule
    public TestName name = new TestName();
    
    @BeforeClass
    public static void beforeTests() throws IOException{
        mOutputWriter = TestUtil.openSummary("schema");
    }

    @AfterClass
    public static void afterTests() throws IOException{
        TestUtil.closeSumamry(mOutputWriter);
    }
    
    @Before
    public void beforeTest() throws IOException {
    	TestUtil.printStartTest(mOutputWriter, name.getMethodName());
    	
    	mInDir = TestUtil.getInDir(name.getMethodName());
    }
    
    @After
    public void afterTest() throws IOException {
        TestUtil.printEndTest(mOutputWriter, name.getMethodName(), true);
    }
    
    @Test
    public void noSchemaFile() throws IOException{
        
        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity.java");
        
        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add(mInDir.getAbsolutePath() + File.pathSeparator + "lib/androidsqlhelper.jar" + File.pathSeparator + "lib/android.jar" );
        
        assertTrue(!TestUtil.compileFiles(name.getMethodName(), mOutputWriter, options, sources));  
    }
    
    @Test
    public void noPackage() throws IOException{
        Schema schema = new Schema();
        schema.setPackage("");
        schema.setAuthor("smartgaddix");
        schema.setDbAdapterClassName("TestDbAdapter");
        schema.setDbName("test.db");
        schema.setDbVersion("1");
        schema.setLicense("short license");
        schema.setLicenseFile("LICENSEHEADER");
        schema.setMetadataClassName("TestDbMetadata");
        
        schema.storeSchemaProperties(mInDir);
        
        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity.java");
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity2.java");
        
        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add(mInDir.getAbsolutePath() + File.pathSeparator + "lib/androidsqlhelper.jar" + File.pathSeparator + "lib/android.jar" );
        
        assertTrue(TestUtil.compileFiles(name.getMethodName(), mOutputWriter, options, sources));  
        
        File outSrcDir = TestUtil.getOutSrcDir(name.getMethodName());
        File dbAdpFile = new File(outSrcDir, "TestDbAdapter.java");
        File dbMetadataFile = new File(outSrcDir, "TestDbMetadata.java");
        
        assertTrue(dbAdpFile.exists() && dbMetadataFile.exists());
    }
}
