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


import static org.junit.Assert.*;

import com.sgxmobileapps.androidsqlhelper.processor.model.Schema;
import com.sgxmobileapps.androidsqlhelper.test.entities.SimpleEntity;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



/**
 * @author Massimo Gaddini
 *
 */
public class CompilerTestCase extends BaseTestCase { 
    
    
    @Test
    public void compileWithoutLibs() throws IOException{
        Schema schema = new Schema();
        schema.setPackage("outpackage.test");
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
        options.add(".");
        
        assertTrue(!TestUtil.compileFiles(name.getMethodName(), mOutputWriter, options, sources));
    }
    
    @Test
    public void compileWithAnnotationLib() throws IOException{
        
        Schema schema = new Schema();
        schema.setPackage("outpackage.test");
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
        options.add("lib/androidsqlhelperannotations.jar");
        
        assertTrue(TestUtil.compileFiles(name.getMethodName(), mOutputWriter, options, sources));
        
    }
    
    @Test
    public void compileWithProcessorLib() throws IOException{
        
        Schema schema = new Schema();
        schema.setPackage("outpackage.test");
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
    }
    
    @Test
    public void compileFull() throws IOException{
        Schema schema = new Schema();
        schema.setPackage("outpackage.test");
        schema.setAuthor("smartgaddix");
        schema.setDbAdapterClassName("TestDbAdapter");
        schema.setDbName("test.db");
        schema.setDbVersion("1");
        schema.setLicense("short license");
        schema.setLicenseFile("LICENSEHEADER");
        schema.setMetadataClassName("TestDbMetadata");
        
        schema.storeSchemaProperties(mInDir);
        
        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/FullEntity.java");
        
        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add(mInDir.getAbsolutePath() + File.pathSeparator + "lib/androidsqlhelper.jar" + File.pathSeparator + "lib/android.jar");
        
        assertTrue(TestUtil.compileFiles(name.getMethodName(), mOutputWriter, options, sources));
        
    }
    
    
    @Test
    public void checkRuntimeWithoutAnnotations() throws IOException{
            
        SimpleEntity simple = new SimpleEntity();
        
        assertNotNull(simple);
        
        assertEquals(0, simple.getClass().getAnnotations().length);
                
    }

}
