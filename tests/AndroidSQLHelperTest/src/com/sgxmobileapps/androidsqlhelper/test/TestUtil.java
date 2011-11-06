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
public class TestUtil {
    protected static final String TESTS_PATH = "tests";
    protected static final String TESTS_IN_PATH = "in";
    protected static final String TESTS_OUT_BUILD_PATH = "out" + File.separatorChar + "build";
    protected static final String TESTS_OUT_SRC_PATH = "out" + File.separatorChar + "src";
    protected static final String SUMMARY_FILE_NAME = "tests_summary";
    
    public static Writer openSummary(String testUnit) throws IOException{
        return new FileWriter(new File(TESTS_PATH, SUMMARY_FILE_NAME + "_" + testUnit + ".log"));
    }

    public static void closeSummary(Writer summary) throws IOException{
        summary.close();
    }
    
    public static void printStartTest(Writer summary, String testTag) throws IOException {
        summary.write("--------------------------------------------------------------\n");
        summary.write("----------------------[" + testTag + "]----------------------\n");
        summary.write("--------------------------------------------------------------\n");
    }
    
    public static void printEndTest(Writer summary, String testTag, boolean success) throws IOException {
        summary.write("\n--------------------------------------------------------------\n");
        summary.write("Test [" + testTag + "] " + (success?"SUCCESS":"FAILED") + "\n");
        summary.write("--------------------------------------------------------------\n");
        summary.write("--------------------------------------------------------------\n\n\n\n");
    }
    
    public static File getOutBuildDir(String testTag) {
        File outputBuildDir = new File(TESTS_PATH, testTag);
        outputBuildDir.mkdirs();
        
        outputBuildDir = new File(outputBuildDir, TESTS_OUT_BUILD_PATH);
        outputBuildDir.mkdirs();
        
        return outputBuildDir;
    }
    
    public static File getOutSrcDir(String testTag) {
        File outputSrcDir = new File(TESTS_PATH, testTag);
        outputSrcDir.mkdirs();
        
        outputSrcDir = new File(outputSrcDir, TESTS_OUT_SRC_PATH);
        outputSrcDir.mkdirs();
        
        return outputSrcDir;
    }
    
    public static File getInDir(String testTag) {
        File inDir = new File(TESTS_PATH, testTag);
        inDir.mkdirs();
        
        inDir = new File(inDir, TESTS_IN_PATH);
        inDir.mkdirs();
        
        return inDir;
    }
    
    public static boolean compileFiles(String testTag, Writer outputWriter, List<String> additionalOptions, ArrayList<String> sources) {
        // Get an instance of java compiler
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        
        if (compiler == null)
            return false;
        
        // Get a new instance of the standard file manager implementation
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null,null);
        
        List<String> options = new ArrayList<String>();
        options.add("-d");
        options.add(getOutBuildDir(testTag).getAbsolutePath());
        options.add("-s");
        options.add(getOutSrcDir(testTag).getAbsolutePath());
        options.add("-verbose");
                
        if (additionalOptions != null)
            options.addAll(additionalOptions);
        
        // Get the list of java file objects
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(sources);
        CompilationTask task = compiler.getTask(outputWriter, fileManager, null, options, null, compilationUnits);
        return task.call();
    }
}
