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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;



/**
 * @author Massimo Gaddini
 *
 */
public class FieldTestCase extends BaseTestCase {

    @BeforeClass
    public static void beforeTests() throws IOException{
        openSummary("field");
    }
    
    @Test
    public void uniqueAndNullableFieldSpecified() throws IOException{
        writeDefaultSchema();

        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/FullEntity.java");

        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add(getInDir().getAbsolutePath() + File.pathSeparator + "lib/androidsqlhelper.jar" + File.pathSeparator + "lib/android.jar" );

        assertTrue(compileFiles(options, sources));
        
        assertTrue(checkGeneratedSource("outpackage/test/TestDbAdapter", "outpackage/test/TestDbMetadata"));

        String createTable = null;
        try {
            Class<?> adapterClazz = loadGeneratedClass("outpackage.test.TestDbAdapter");
            Field createTableField = adapterClazz.getDeclaredField("SQL_FULLENTITY_CREATE_TABLE");
            createTableField.setAccessible(true);
            createTable = (String)createTableField.get(null);    
        } catch (Exception e) {
            fail(e.getMessage());
        } 
        
        printToOutput(createTable);
        
        assertTrue(createTable.contains("LONGO INTEGER NOT NULL UNIQUE")  && createTable.contains("INTO INTEGER NOT NULL UNIQUE"));
    }
    
    @Test
    public void uniqueFieldConstraintSpecified() throws IOException{
        writeDefaultSchema();

        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/TableTestEntity1.java");

        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add(getInDir().getAbsolutePath() + File.pathSeparator + "lib/androidsqlhelper.jar" + File.pathSeparator + "lib/android.jar" );

        assertTrue(compileFiles(options, sources));

        assertTrue(checkGeneratedSource("outpackage/test/TestDbAdapter", "outpackage/test/TestDbMetadata"));
        
        String createTable = null;
        try {
            Class<?> adapterClazz = loadGeneratedClass("outpackage.test.TestDbAdapter");
            Field createTableField = adapterClazz.getDeclaredField("SQL_TABLETESTENTITY1_CREATE_TABLE");
            createTableField.setAccessible(true);
            createTable = (String)createTableField.get(null);    
        } catch (Exception e) {
            fail(e.getMessage());
        } 
        
        printToOutput(createTable);
        
        assertTrue(createTable.contains("MFIELDSTRING TEXT NOT NULL UNIQUE")  && createTable.contains("MFIELDLONG INTEGER NOT NULL UNIQUE"));
    }
    
    public void notNullSpecified() throws IOException{
        writeDefaultSchema();

        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/FullEntity.java");

        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add(getInDir().getAbsolutePath() + File.pathSeparator + "lib/androidsqlhelper.jar" + File.pathSeparator + "lib/android.jar" );

        assertTrue(compileFiles(options, sources));
        
        assertTrue(checkGeneratedSource("outpackage/test/TestDbAdapter", "outpackage/test/TestDbMetadata"));

        String createTable = null;
        try {
            Class<?> adapterClazz = loadGeneratedClass("outpackage.test.TestDbAdapter");
            Field createTableField = adapterClazz.getDeclaredField("SQL_FULLENTITY_CREATE_TABLE");
            createTableField.setAccessible(true);
            createTable = (String)createTableField.get(null);    
        } catch (Exception e) {
            fail(e.getMessage());
        } 
        
        printToOutput(createTable);
        
        assertTrue(createTable.contains("INT INTEGER NOT NULL"));
    }
    
    @Test
    public void columnNameSpecified() throws IOException{
        writeDefaultSchema();

        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/FullEntity.java");

        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add(getInDir().getAbsolutePath() + File.pathSeparator + "lib/androidsqlhelper.jar" + File.pathSeparator + "lib/android.jar" );

        assertTrue(compileFiles(options, sources));
        
        assertTrue(checkGeneratedSource("outpackage/test/TestDbAdapter", "outpackage/test/TestDbMetadata"));

        String createTable = null;
        try {
            Class<?> adapterClazz = loadGeneratedClass("outpackage.test.TestDbAdapter");
            Field createTableField = adapterClazz.getDeclaredField("SQL_FULLENTITY_CREATE_TABLE");
            createTableField.setAccessible(true);
            createTable = (String)createTableField.get(null);    
        } catch (Exception e) {
            fail(e.getMessage());
        } 
        
        printToOutput(createTable);
        
        assertTrue(createTable.contains("LONGPRIV INTEGER"));
    }
    
    @Test
    public void customDefinitionSpecified() throws IOException{
        writeDefaultSchema();

        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/FullEntity.java");

        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add(getInDir().getAbsolutePath() + File.pathSeparator + "lib/androidsqlhelper.jar" + File.pathSeparator + "lib/android.jar" );

        assertTrue(compileFiles(options, sources));
        
        assertTrue(checkGeneratedSource("outpackage/test/TestDbAdapter", "outpackage/test/TestDbMetadata"));

        String createTable = null;
        try {
            Class<?> adapterClazz = loadGeneratedClass("outpackage.test.TestDbAdapter");
            Field createTableField = adapterClazz.getDeclaredField("SQL_FULLENTITY_CREATE_TABLE");
            createTableField.setAccessible(true);
            createTable = (String)createTableField.get(null);    
        } catch (Exception e) {
            fail(e.getMessage());
        } 
        
        printToOutput(createTable);
        
        assertTrue(createTable.contains("INTPRIMITIVE REAL UNIQUE NOT NULL"));
    }
}
