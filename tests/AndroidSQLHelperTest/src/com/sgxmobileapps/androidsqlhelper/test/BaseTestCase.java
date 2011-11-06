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

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import java.io.File;
import java.io.IOException;
import java.io.Writer;


/**
 * @author Massimo Gaddini
 *
 */
public class BaseTestCase {
    protected static File mInDir;
    protected static Writer mOutputWriter; 
    
    @Rule
    public TestName name = new TestName();
    
    @Rule
    public TestWatchman mWatcher= new TestWatchman() {
        @Override
        public void failed(Throwable e, FrameworkMethod method) {
            try {
                TestUtil.printEndTest(mOutputWriter, method.getName(), false);
            } catch (IOException e1) {
            }
        }
    
        @Override
        public void succeeded(FrameworkMethod method) {
            try {
                TestUtil.printEndTest(mOutputWriter, method.getName(), true);
            } catch (IOException e1) {
            }
        }
    };
    
    
    @BeforeClass
    public static void beforeTests() throws IOException{
        mOutputWriter = TestUtil.openSummary("compiler");
    }

    @AfterClass
    public static void afterTests() throws IOException{
        TestUtil.closeSummary(mOutputWriter);
    }
    
    @Before
    public void beforeTest() throws IOException {
        TestUtil.printStartTest(mOutputWriter, name.getMethodName());
        
        mInDir = TestUtil.getInDir(name.getMethodName());
    }
}
