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

import org.junit.After;
import org.junit.Before;
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
    
    protected static ArrayList<String> mSourceFiles = null;
    
    protected static File mOutputDir;
    
    protected static Writer mOutputWriter; 
          
    static {
        mSourceFiles = new ArrayList<String>();
        mSourceFiles.add("src/com/sgxmobileapps/androidsqlhelper/test/entities/SampleEntity.java");
    }
        
    protected boolean compileFiles(String testTag, List<String> additionalOptions) {
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
                
        if (additionalOptions != null)
            options.addAll(additionalOptions);
        
        // Get the list of java file objects
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(mSourceFiles);
        CompilationTask task = compiler.getTask(mOutputWriter, fileManager, null, options, null, compilationUnits);
        return task.call();
    }
    
    @Before
    public void beforeTests() throws IOException{
        mOutputDir = new File("tests_output");
        mOutputDir.mkdirs();
      
        mOutputWriter = new FileWriter(new File(mOutputDir, OUT_FILE_NAME));
    }

    @After
    public void afterTests() throws IOException{
        mOutputWriter.close();
    }
    
    @Test
    public void compileTest1(){
        compileFiles("BaseCompile", null);
    }

}
