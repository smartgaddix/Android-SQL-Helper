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
package com.sgxmobileapps.androidsqlhelper.generator;

import com.sgxmobileapps.androidsqlhelper.processor.model.Schema;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.writer.FileCodeWriter;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


/**
 * @author Massimo Gaddini
 */
public class HeaderFileCodeWriter extends FileCodeWriter {

    private Schema mSchema;
    
    /**
     * @param target
     * @throws IOException
     */
    public HeaderFileCodeWriter(File target, Schema schema) throws IOException {
        super(target);
        mSchema = schema;
    }

    /**
     * @param target
     * @param readOnly
     * @throws IOException
     */
    public HeaderFileCodeWriter(File target, boolean readOnly, Schema schema) throws IOException {
        super(target, readOnly);
        mSchema = schema;
    }

    /* 
     * @see com.sun.codemodel.writer.FileCodeWriter#openBinary(com.sun.codemodel.JPackage, java.lang.String)
     */
    @Override
    public OutputStream openBinary(JPackage pkg, String fileName) throws IOException {
        OutputStream out = super.openBinary(pkg, fileName);
        
        if (!mSchema.getLicense().isEmpty()) {
            out.write(mSchema.getLicense().getBytes());
        }
        
        return out;
    }
    
    
    
}
