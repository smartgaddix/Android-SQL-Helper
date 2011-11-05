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
package com.sgxmobileapps.androidsqlhelper.generator.codemodel;

import com.sgxmobileapps.androidsqlhelper.generator.CodeGenerationException;
import com.sgxmobileapps.androidsqlhelper.processor.model.Schema;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JPackage;

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;


/**
 * @author Massimo Gaddini
 */
public class HeaderFileCodeWriter extends CodeWriter {

    private Schema mSchema;
    private Filer mFiler;
    
    /**
     * @param schema
     * @param filer
     * @throws IOException
     */
    public HeaderFileCodeWriter(Schema schema, Filer filer) throws CodeGenerationException {
        mSchema = schema;
        mFiler = filer;
        
        if ((mFiler==null) || (mSchema==null)) {
        	throw new CodeGenerationException("Filer or schems null");
        }
    }


    /* 
     * @see com.sun.codemodel.writer.CodeWriter#openBinary(com.sun.codemodel.JPackage, java.lang.String)
     */
    @Override
    public OutputStream openBinary(JPackage pkg, String fileName) throws IOException {
    	int lastDotIndex = fileName.lastIndexOf(".");
    	if (lastDotIndex == -1){
    		throw new IOException("Invalid file extension");
    	}
    	
    	String noExtFileName = fileName.substring(0, lastDotIndex);
    	JavaFileObject jfo = null;
    	if (fileName.substring(lastDotIndex).equals(".java")) {
    		jfo = mFiler.createSourceFile(pkg.name() + "." + noExtFileName);
    	} else if (fileName.substring(lastDotIndex).equals(".class")) {
    		jfo = mFiler.createClassFile(pkg.name() + "." + noExtFileName);
    	} 
    	
    	if (jfo == null){
    		throw new IOException("Unable to create JavaFileObject");
    	}
    	
    	OutputStream out = jfo.openOutputStream();
    	
        if (!mSchema.getLicense().isEmpty()) {
            out.write(mSchema.getLicense().getBytes());
        }
        
        return out;
    }

	@Override
	public void close() throws IOException {
	}
    
    
    
}
