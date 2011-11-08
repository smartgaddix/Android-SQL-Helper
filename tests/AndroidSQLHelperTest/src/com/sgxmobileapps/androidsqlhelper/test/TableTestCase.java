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
import static org.junit.Assert.fail;

import com.sgxmobileapps.androidsqlhelper.processor.model.Schema;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;



/**
 * @author Massimo Gaddini
 *
 */
public class TableTestCase extends BaseTestCase {

    @BeforeClass
    public static void beforeTests() throws IOException{
        openSummary("table");
    }
    
    @Test
    public void tebleNameSpecified() throws IOException{
        Schema schema = new Schema();
        schema.setPackage("generated");
        schema.setAuthor("smartgaddix");
        schema.setDbAdapterClassName("TestDbAdapter");
        schema.setDbName("test.db");
        schema.setDbVersion("1");
        schema.setLicense("short license");
        schema.setLicenseFile("LICENSEHEADER");
        schema.setMetadataClassName("TestDbMetadata");

        schema.storeSchemaProperties(mInDir);

        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/TableTestEntity1.java");

        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add(mInDir.getAbsolutePath() + File.pathSeparator + "lib/androidsqlhelper.jar" + File.pathSeparator + "lib/android.jar" );

        assertTrue(compileFiles(options, sources));

        File outSrcDir = getOutSrcDir();
        File dbAdpFile = new File(outSrcDir, "generated/TestDbAdapter.java");
        File dbMetadataFile = new File(outSrcDir, "generated/TestDbMetadata.java");

        File outBuildDir = getOutBuildDir();
		File dbAdpClassFile = new File(outBuildDir, "generated/TestDbAdapter.class");
        File dbMetadataClassFile = new File(outBuildDir, "generated/TestDbMetadata.class");

        assertTrue(dbAdpFile.exists() && dbMetadataFile.exists() && dbAdpClassFile.exists() && dbMetadataClassFile.exists());
        
		URLClassLoader classLoader = new URLClassLoader(new URL[]{outBuildDir.toURI().toURL()});	
		String tableName = null;
		try {
		    Class<?> metadataClazz = classLoader.loadClass("generated.TestDbMetadata$TableTestEntity1");
            Field tableNameField = metadataClazz.getField("TABLETESTENTITY1_TABLE_NAME");
            tableName = (String)tableNameField.get(null);    
        } catch (Exception e) {
            fail(e.getMessage());
        } 
		
		assertTrue(tableName.equals("FIRSTENTITY"));
    }
}
