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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

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
    
    protected static final String TESTS_PATH = "tests";
    protected static final String TESTS_IN_PATH = "in";
    protected static final String TESTS_OUT_BUILD_PATH = "out" + File.separatorChar + "build";
    protected static final String TESTS_OUT_SRC_PATH = "out" + File.separatorChar + "src";
    protected static final String SUMMARY_FILE_NAME = "tests_summary.log";
       
    protected static File mOutputBuildDir;
    protected static File mOutputSrcDir;
    protected static File mInDir;
    protected static Writer mOutputWriter; 
    
    @Rule
    public TestName name= new TestName();
    
    @BeforeClass
    public static void beforeTests() throws IOException{
        mOutputWriter = new FileWriter(new File(TESTS_PATH, SUMMARY_FILE_NAME));
    }

    @AfterClass
    public static void afterTests() throws IOException{
        mOutputWriter.close();
    }
    
    @Before
    public void beforeTest() throws IOException {
    	printStartTest(name.getMethodName());
    	
    	setInOutDir(name.getMethodName());
    }
    
    @After
    public void afterTest() throws IOException {
    	printEndTest(name.getMethodName(), true);
    }
     
    protected void setInOutDir(String testTag) {
    	mOutputBuildDir = new File(TESTS_PATH, name.getMethodName());
    	mOutputBuildDir.mkdirs();
    	
    	mInDir = new File(mOutputBuildDir, TESTS_IN_PATH);
    	mInDir.mkdirs();
        
    	mOutputSrcDir = new File(mOutputBuildDir, TESTS_OUT_SRC_PATH);
    	mOutputSrcDir.mkdirs();
    	
    	mOutputBuildDir = new File(mOutputBuildDir, TESTS_OUT_BUILD_PATH);
    	mOutputBuildDir.mkdirs();
    }
    
    protected boolean compileFiles(String testTag, List<String> additionalOptions, ArrayList<String> sources) {
        // Get an instance of java compiler
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        
        if (compiler == null)
            return false;
        
        // Get a new instance of the standard file manager implementation
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null,null);
        
        List<String> options = new ArrayList<String>();
        options.add("-d");
        options.add(mOutputBuildDir.getAbsolutePath());
        options.add("-s");
        options.add(mOutputSrcDir.getAbsolutePath());
        options.add("-verbose");
                
        if (additionalOptions != null)
            options.addAll(additionalOptions);
        
        // Get the list of java file objects
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(sources);
        CompilationTask task = compiler.getTask(mOutputWriter, fileManager, null, options, null, compilationUnits);
        return task.call();
    }
    
    protected void printStartTest(String testTag) throws IOException {
    	mOutputWriter.write("--------------------------------------------------------------\n");
    	mOutputWriter.write("----------------------[" + testTag + "]----------------------\n");
    	mOutputWriter.write("--------------------------------------------------------------\n");
    }
    
    protected void printEndTest(String testTag, boolean success) throws IOException {
    	mOutputWriter.write("\n--------------------------------------------------------------\n");
    	mOutputWriter.write("Test [" + testTag + "] " + (success?"SUCCESS":"FAILED") + "\n");
    	mOutputWriter.write("--------------------------------------------------------------\n");
        mOutputWriter.write("--------------------------------------------------------------\n\n\n\n");
    }
    
    @Test
    public void compileWithoutLibs() throws IOException{
        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity.java");
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity2.java");
        
        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add(".");
        
        assertTrue(!compileFiles("compileWithoutLibs", options, sources));
    }
    
    @Test
    public void compileWithAnnotationLib() throws IOException{
        
        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity.java");
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity2.java");
        
        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add("lib/androidsqlhelperannotations.jar");
        
        assertTrue(compileFiles("compileWithAnnotationLib", options, sources));
        
    }
    
    @Test
    public void compileWithProcessorLib() throws IOException{
        
        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity.java");
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SimpleEntity2.java");
        
        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add(mInDir.getAbsolutePath() + File.pathSeparator + "lib/androidsqlhelper.jar" + File.pathSeparator + "lib/android.jar" );
        
        assertTrue(compileFiles("compileWithProcessorLib", options, sources));        
    }
    
    @Test
    public void compileFull() throws IOException{
        
        ArrayList<String> sources = new ArrayList<String>();
        sources.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/FullEntity.java");
        
        ArrayList<String> options = new ArrayList<String>();
        options.add("-cp");
        options.add(mInDir.getAbsolutePath() + File.pathSeparator + "lib/androidsqlhelper.jar" + File.pathSeparator + "lib/android.jar");
        
        assertTrue(compileFiles("compileFull", options, sources));
        
    }
    
    
    @Test
    public void checkRuntimeWithoutAnnotations() throws IOException{
            
        SimpleEntity simple = new SimpleEntity();
        
        assertNotNull(simple);
        
        assertEquals(0, simple.getClass().getAnnotations().length);
                
    }

}
