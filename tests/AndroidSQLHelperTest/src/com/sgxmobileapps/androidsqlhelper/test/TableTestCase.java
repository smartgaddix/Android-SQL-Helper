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


import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
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
    public void tableNameSpecified() throws IOException{
        writeDefaultSchema();

        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/TableTestEntity1.java");

        ArrayList<String> options = new ArrayList<String>();
        addStandardClassPath(options);

        assertTrue(compileFiles(options, sources));
        
        assertTrue(checkGeneratedSource("outpackage/test/TestDbAdapter", "outpackage/test/TestDbMetadata"));

        String tableName = null;
		try {
		    Class<?> metadataClazz = loadGeneratedClass("outpackage.test.TestDbMetadata$TableTestEntity1");
            Field tableNameField = metadataClazz.getField("TABLETESTENTITY1_TABLE_NAME");
            tableName = (String)tableNameField.get(null);    
        } catch (Exception e) {
            fail(e.getMessage());
        } 
		
		assertTrue(tableName.equals("FIRSTENTITY"));
    }
    
    @Test
    public void noIdColumn() throws IOException{
        writeDefaultSchema();

        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/TableTestEntity2.java");

        ArrayList<String> options = new ArrayList<String>();
        addStandardClassPath(options);

        assertTrue(compileFiles(options, sources));

        assertTrue(checkGeneratedSource("outpackage/test/TestDbAdapter", "outpackage/test/TestDbMetadata"));
        
        String createTable = null;
        try {
            Class<?> adapterClazz = loadGeneratedClass("outpackage.test.TestDbAdapter");
            Field createTableField = adapterClazz.getDeclaredField("SQL_TABLETESTENTITY2_CREATE_TABLE");
            createTableField.setAccessible(true);
            createTable = (String)createTableField.get(null);    
        } catch (Exception e) {
            fail(e.getMessage());
        } 
        
        printToOutput(createTable);
        
        assertTrue(!createTable.contains("_id INTEGER PRIMARY KEY AUTOINCREMENT"));
    }
    
    @Test
    public void fieldsPrefix() throws IOException{
        writeDefaultSchema();

        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/TableTestEntity2.java");

        ArrayList<String> options = new ArrayList<String>();
        addStandardClassPath(options);

        assertTrue(compileFiles(options, sources));

        assertTrue(checkGeneratedSource("outpackage/test/TestDbAdapter", "outpackage/test/TestDbMetadata"));
        
        String createTable = null;
        try {
            Class<?> adapterClazz = loadGeneratedClass("outpackage.test.TestDbAdapter");
            Field createTableField = adapterClazz.getDeclaredField("SQL_TABLETESTENTITY2_CREATE_TABLE");
            createTableField.setAccessible(true);
            createTable = (String)createTableField.get(null);    
        } catch (Exception e) {
            fail(e.getMessage());
        } 
        
        printToOutput(createTable);
        
        assertTrue( createTable.contains("FIELDSTRING TEXT")  && 
                    createTable.contains("FIELDLONG INTEGER") &&
                    !createTable.contains("MFIELDSTRING") && !createTable.contains("MFIELDLONG"));
    }
    
    @Test
    public void uniqueTableConstraintSpecified() throws IOException{
        writeDefaultSchema();

        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/TableTestEntity3.java");

        ArrayList<String> options = new ArrayList<String>();
        addStandardClassPath(options);

        assertTrue(compileFiles(options, sources));

        assertTrue(checkGeneratedSource("outpackage/test/TestDbAdapter", "outpackage/test/TestDbMetadata"));
        
        String createTable = null;
        try {
            Class<?> adapterClazz = loadGeneratedClass("outpackage.test.TestDbAdapter");
            Field createTableField = adapterClazz.getDeclaredField("SQL_TABLETESTENTITY3_CREATE_TABLE");
            createTableField.setAccessible(true);
            createTable = (String)createTableField.get(null);    
        } catch (Exception e) {
            fail(e.getMessage());
        } 
        
        printToOutput(createTable);
        
        assertTrue(createTable.contains("UNIQUE (FIELDSTRING, FIELDLONG)"));
    }
    
    @Test
    public void pkTableConstraintSpecified() throws IOException{
        writeDefaultSchema();

        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/TableTestEntity3.java");

        ArrayList<String> options = new ArrayList<String>();
        addStandardClassPath(options);

        assertTrue(compileFiles(options, sources));

        assertTrue(checkGeneratedSource("outpackage/test/TestDbAdapter", "outpackage/test/TestDbMetadata"));
        
        String createTable = null;
        try {
            Class<?> adapterClazz = loadGeneratedClass("outpackage.test.TestDbAdapter");
            Field createTableField = adapterClazz.getDeclaredField("SQL_TABLETESTENTITY3_CREATE_TABLE");
            createTableField.setAccessible(true);
            createTable = (String)createTableField.get(null);    
        } catch (Exception e) {
            fail(e.getMessage());
        } 
        
        printToOutput(createTable);
        
        assertTrue(createTable.contains("PRIMARY KEY (FIELDSTRING, FIELDLONG)"));
    }
}
