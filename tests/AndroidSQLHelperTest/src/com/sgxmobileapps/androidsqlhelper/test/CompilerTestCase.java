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

import com.sgxmobileapps.androidsqlhelper.test.entities.SimpleEntity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;


/**
 * @author Massimo Gaddini
 *
 */
public class CompilerTestCase {
    
    protected static final String OUT_PATH = "tests_output";
    protected static final String OUT_FILE_NAME = "out.log";
       
    protected static File mOutputDir;
    protected static Writer mOutputWriter; 
    
    @BeforeClass
    public static void beforeTests() throws IOException{
        mOutputDir = new File("tests_output");
        mOutputDir.mkdirs();
      
        mOutputWriter = new FileWriter(new File(mOutputDir, OUT_FILE_NAME));
    }

    @AfterClass
    public static void afterTests() throws IOException{
        mOutputWriter.close();
    }
       
    protected boolean compileFiles(String testTag, List<String> additionalOptions, ArrayList<String> sources) {
        // Get an instance of java compiler
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        
        if (compiler == null)
            return false;
        
        // Get a new instance of the standard file manager implementation
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null,null);
        
        File outputDir = new File("tests_output", testTag);
        outputDir.mkdirs();
        
        List<String> options = new ArrayList<String>();
        options.add("-d");
        options.add(outputDir.getAbsolutePath());
        options.add("-s");
        options.add(outputDir.getAbsolutePath());
        options.add("-verbose");
                
        if (additionalOptions != null)
            options.addAll(additionalOptions);
        
        // Get the list of java file objects
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(sources);
        CompilationTask task = compiler.getTask(mOutputWriter, fileManager, null, options, null, compilationUnits);
        return task.call();
    }
    
    
    
    @Test
    public void compileWithoutLibs() throws IOException{
        mOutputWriter.write("----------------------compileWithoutLibs----------------------\n");
               
        
        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity.java");
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity2.java");
        
        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add(".");
        
        assertTrue(!compileFiles("compileWithoutLibs", options, sources));
        
        mOutputWriter.write("--------------------------------------------------------------\n");
        mOutputWriter.write("--------------------------------------------------------------\n");
    }
    
    @Test
    public void compileWithAnnotationLib() throws IOException{
        mOutputWriter.write("----------------------compileWithAnnotationLib----------------------\n");
        
        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity.java");
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity2.java");
        
        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add("lib/androidsqlhelperannotations.jar");
        
        assertTrue(compileFiles("compileWithAnnotationLib", options, sources));
        
        mOutputWriter.write("--------------------------------------------------------------------\n");
        mOutputWriter.write("--------------------------------------------------------------------\n");
    }
    
    @Test
    public void compileWithProcessorLib() throws IOException{
        mOutputWriter.write("----------------------compileWithProcessorLib----------------------\n");
        
        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity.java");
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity2.java");
        
        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add("lib/androidsqlhelper.jar");
        
        assertTrue(compileFiles("compileWithProcessorLib", options, sources));
        
        mOutputWriter.write("-------------------------------------------------------------------\n");
        mOutputWriter.write("-------------------------------------------------------------------\n");
    }
    
    @Test
    public void compileFull() throws IOException{
        mOutputWriter.write("----------------------compileFull----------------------\n");
        
        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/FullEntity.java");
        
        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add("lib/androidsqlhelper.jar");
        
        assertTrue(compileFiles("compileFull", options, sources));
        
        mOutputWriter.write("-------------------------------------------------------------------\n");
        mOutputWriter.write("-------------------------------------------------------------------\n");
    }
    
    
    @Test
    public void checkRuntimeWithoutAnnotations() throws IOException{
        mOutputWriter.write("----------------------checkRuntimeWithoutAnnotations----------------------\n");
            
        SimpleEntity simple = new SimpleEntity();
        
        assertNotNull(simple);
        
        assertEquals(0, simple.getClass().getAnnotations().length);
                
        mOutputWriter.write("--------------------------------------------------------------------------\n");
        mOutputWriter.write("--------------------------------------------------------------------------\n");
    }

}
