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
import com.sgxmobileapps.androidsqlhelper.generator.CodeGenerator;
import com.sgxmobileapps.androidsqlhelper.generator.HeaderFileCodeWriter;
import com.sgxmobileapps.androidsqlhelper.processor.model.Schema;
import com.sgxmobileapps.androidsqlhelper.processor.model.VisitorException;
import com.sun.codemodel.JCodeModel;

import java.io.File;
import java.io.IOException;


/**
 * @author Massimo Gaddini
 */
public class CodeModelCodeGenerator implements CodeGenerator {
 
    @Override
    public void generate(Schema schema) throws CodeGenerationException {
        try {
            CodeModelVisitorContext ctx = new CodeModelVisitorContext();
            ctx.mCMRoot = new JCodeModel();
            
            (new MetadataClassVisitor()).startVisit(schema, ctx);
            (new DbAdapterClassVisitor()).startVisit(schema, ctx);
            
            File outPath = new File(schema.getOutFolder());
            outPath.mkdirs();
            HeaderFileCodeWriter codeWriter = new HeaderFileCodeWriter(outPath, schema);
            ctx.mCMRoot.build(codeWriter);
        } catch (IOException e) {
            throw new CodeGenerationException(e);
        } catch (VisitorException e) {
            throw new CodeGenerationException(e);
        } 
        
    }
}
