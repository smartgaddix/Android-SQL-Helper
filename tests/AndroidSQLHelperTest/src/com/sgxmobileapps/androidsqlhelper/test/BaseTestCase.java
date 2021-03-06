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

import com.sgxmobileapps.androidsqlhelper.processor.model.Schema;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;


/**
 * @author Massimo Gaddini
 *
 */
public class BaseTestCase {

    private static final String TESTS_PATH = "tests";
    private static final String TESTS_IN_PATH = "in";
    private static final String TESTS_OUT_BUILD_PATH = "out" + File.separatorChar + "build";
    private static final String TESTS_OUT_SRC_PATH = "out" + File.separatorChar + "src";
    private static final String SUMMARY_FILE_NAME = "tests_summary";

    private static Writer mOutputWriter;

	protected static void openSummary(String testUnit) throws IOException {
		mOutputWriter = new FileWriter(new File(TESTS_PATH, SUMMARY_FILE_NAME + "_" + testUnit + ".log"));
	}

	protected static void closeSummary() throws IOException{
		mOutputWriter.close();
	}

	protected static void printStartTest(String testTag) throws IOException {
		mOutputWriter.write("--------------------------------------------------------------\n");
		mOutputWriter.write("----------------------[" + testTag + "]----------------------\n");
		mOutputWriter.write("--------------------------------------------------------------\n");
	}

	protected static void printEndTest(String testTag, boolean success) throws IOException {
		mOutputWriter.write("\n--------------------------------------------------------------\n");
		mOutputWriter.write("Test [" + testTag + "] " + (success?"SUCCESS":"FAILED") + "\n");
		mOutputWriter.write("--------------------------------------------------------------\n");
		mOutputWriter.write("--------------------------------------------------------------\n\n\n\n");
	}
	
	protected static void printToOutput(String msg) throws IOException{
	    mOutputWriter.write(msg);
	}

	protected static File getOutBuildDir() {
		File outputBuildDir = new File(TESTS_PATH, name.getMethodName());
		outputBuildDir.mkdirs();

		outputBuildDir = new File(outputBuildDir, TESTS_OUT_BUILD_PATH);
		outputBuildDir.mkdirs();

		return outputBuildDir;
	}

	protected static File getOutSrcDir() {
		File outputSrcDir = new File(TESTS_PATH, name.getMethodName());
		outputSrcDir.mkdirs();

		outputSrcDir = new File(outputSrcDir, TESTS_OUT_SRC_PATH);
		outputSrcDir.mkdirs();

		return outputSrcDir;
	}

	protected static File getInDir() {
		File inDir = new File(TESTS_PATH, name.getMethodName());
		inDir.mkdirs();

		inDir = new File(inDir, TESTS_IN_PATH);
		inDir.mkdirs();

		return inDir;
	}

	protected static boolean compileFiles(List<String> additionalOptions, ArrayList<String> sources) {
		// Get an instance of java compiler
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		if (compiler == null)
			return false;

		// Get a new instance of the standard file manager implementation
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null,null);

		List<String> options = new ArrayList<String>();
		options.add("-d");
		options.add(getOutBuildDir().getAbsolutePath());
		options.add("-s");
		options.add(getOutSrcDir().getAbsolutePath());
		options.add("-verbose");

		if (additionalOptions != null)
			options.addAll(additionalOptions);

		// Get the list of java file objects
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(sources);
		CompilationTask task = compiler.getTask(mOutputWriter, fileManager, null, options, null, compilationUnits);
		return task.call();
    }
	
	protected static void addStandardClassPath(ArrayList<String> options){
        options.add("-cp");
        options.add(getInDir().getAbsolutePath() + File.pathSeparator + "lib/codemodel-2.5-SNAPSHOT.jar" + File.pathSeparator + "lib/androidsqlhelper.jar" + File.pathSeparator + "lib/android.jar" );

	}
	
	protected static Schema getDefaultSchema() throws IOException{ 
        Schema schema = new Schema();
        schema.setPackage("outpackage.test");
        schema.setAuthor("smartgaddix");
        schema.setDbAdapterClassName("TestDbAdapter");
        schema.setDbName("test.db");
        schema.setDbVersion("1");
        schema.setLicense("short license");
        schema.setLicenseFile("LICENSEHEADER");
        schema.setMetadataClassName("TestDbMetadata");
        
        return schema;
    }
	
	protected static void writeSchema(Schema schema) throws IOException{ 
        schema.storeSchemaProperties(getInDir());
    }
	
	protected static void writeDefaultSchema() throws IOException{ 
        writeSchema(getDefaultSchema());
	}
	
	protected static File getGeneratedSourceFile(String relativePath){
	    File outSrcDir = getOutSrcDir();
        return new File(outSrcDir, relativePath);
	}
	
	protected static File getGeneratedBuildFile(String relativePath){
        File outBuildDir = getOutBuildDir();
        return new File(outBuildDir, relativePath);
    }
	
	protected static boolean checkGeneratedSource(String relativePath1, String relativePath2){
	    File dbAdpFile = getGeneratedSourceFile(relativePath1 + ".java");
        File dbMetadataFile = getGeneratedSourceFile(relativePath2 + ".java");
        File dbAdpClassFile = getGeneratedBuildFile(relativePath1 + ".class");
        File dbMetadataClassFile = getGeneratedBuildFile(relativePath2 + ".class");

        return dbAdpFile.exists() && dbMetadataFile.exists() && dbAdpClassFile.exists() && dbMetadataClassFile.exists();
	}
	
	protected static Class<?> loadGeneratedClass(String fqName) throws MalformedURLException, ClassNotFoundException{
	    URLClassLoader classLoader = new URLClassLoader(new URL[]{ getOutBuildDir().toURI().toURL(), (new File("lib/android.jar")).toURI().toURL()});    
        return classLoader.loadClass(fqName);
	}

    @Rule
    public static TestName name = new TestName();

    @Rule
    public TestWatchman mWatcher= new TestWatchman() {
        @Override
        public void failed(Throwable e, FrameworkMethod method) {
            try {
                printEndTest(method.getName(), false);
            } catch (IOException e1) {
            }
        }

        @Override
        public void succeeded(FrameworkMethod method) {
            try {
                printEndTest(method.getName(), true);
            } catch (IOException e1) {
            }
        }
    };
    
    @AfterClass
    public static void afterTests() throws IOException{
        closeSummary();
    }
    
    @Before
    public void beforeTest() throws IOException {
        printStartTest(name.getMethodName());
        getInDir();
        getOutSrcDir();
        getOutBuildDir();
    }
}
