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

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;



/**
 * @author Massimo Gaddini
 *
 */
public class SchemaTestCase extends BaseTestCase {

    @BeforeClass
    public static void beforeTests() throws IOException{
        openSummary("schema");
    }
    
    @Test
    public void noSchemaFile() throws IOException{

        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity.java");

        ArrayList<String> options = new ArrayList<String>();
        addStandardClassPath(options);

        assertTrue(!compileFiles(options, sources));
    }

    @Test
    public void noPackage() throws IOException {
        
        Schema schema = getDefaultSchema();
        schema.setPackage("");
        writeSchema(schema);

        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity.java");
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity2.java");

        ArrayList<String> options = new ArrayList<String>();
        addStandardClassPath(options);

        assertTrue(compileFiles(options, sources));

        assertTrue(checkGeneratedSource("TestDbAdapter", "TestDbMetadata"));
    }

    @Test
    public void noLicenseFile() throws IOException{
        Schema schema = getDefaultSchema();
        schema.setLicense("/*short license\nshort license second line*/");
        schema.setLicenseFile("");
        writeSchema(schema);

        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity.java");

        ArrayList<String> options = new ArrayList<String>();
        addStandardClassPath(options);

        assertTrue(compileFiles(options, sources));
    
        assertTrue(checkGeneratedSource("outpackage/test/TestDbAdapter", "outpackage/test/TestDbMetadata"));
    }

    
    @Test
    public void noLicense() throws IOException{
        Schema schema = getDefaultSchema();
        schema.setLicense("");
        schema.setLicenseFile("");
        writeSchema(schema);
        
        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity.java");

        ArrayList<String> options = new ArrayList<String>();
        addStandardClassPath(options);

        assertTrue(compileFiles(options, sources));

        assertTrue(checkGeneratedSource("outpackage/test/TestDbAdapter", "outpackage/test/TestDbMetadata"));
    }

    @Test
    public void defaultSchema() throws IOException{
        Schema schema = new Schema();
        writeSchema(schema);

        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity.java");

        ArrayList<String> options = new ArrayList<String>();
        addStandardClassPath(options);

        assertTrue(compileFiles(options, sources));
        
        assertTrue(checkGeneratedSource("DbAdapter", "DbMetadata"));
    }

    @Test
    public void emptySchema() throws IOException{
        Schema schema = new Schema();
        schema.setPackage("");
        schema.setAuthor("");
        schema.setDbAdapterClassName("");
        schema.setDbName("");
        schema.setDbVersion("");
        schema.setLicense("");
        schema.setLicenseFile("");
        schema.setMetadataClassName("");
        writeSchema(schema);

        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity.java");

        ArrayList<String> options = new ArrayList<String>();
        addStandardClassPath(options);

        assertTrue(!compileFiles(options, sources));
    }
}
