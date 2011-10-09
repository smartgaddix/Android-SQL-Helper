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
import com.sgxmobileapps.androidsqlhelper.processor.model.Table;
import com.sgxmobileapps.androidsqlhelper.processor.model.VisitorException;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JPackage;

import java.io.File;
import java.io.IOException;


/**
 * @author Massimo Gaddini
 */
public class CodeModelCodeGenerator implements CodeGenerator {
 
    
    /**
     * 
     */
    public CodeModelCodeGenerator() {
    }
 
    @Override
    public void generate(Schema schema) throws CodeGenerationException {
        try {
            CodeModelVisitorContext ctx = new CodeModelVisitorContext();
            ctx.mCMRoot = new JCodeModel();
            
            (new MetadataClassVisitor()).startVisit(schema, ctx);

            //generateDbAdapterClass(ctx.cmRoot, schema);
            
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
    
    /*private void generateDbAdapterClass(JCodeModel root, Schema schema) throws JClassAlreadyExistsException{
        JPackage pckg = root._package(schema.getPackage());
        JDefinedClass dbAdapterClass = pckg._class(schema.getDbAdapterClassName());
             
        generateDbAdapterJavaDoc(dbAdapterClass, schema);
        generateDbAdapterCreateDropTables(dbAdapterClass, schema);
    }
    
    private void generateDbAdapterJavaDoc(JDefinedClass dbAdapterClass, Schema schema) throws JClassAlreadyExistsException {
        JDocComment doc = dbAdapterClass.javadoc();
        //doc.add(DBADAPTER_CLASS_JAVADOC);
        if (!schema.getAuthor().isEmpty())
            doc.add("\n\n@author " + schema.getAuthor());
    }   
    
    private void generateDbAdapterCreateDropTables(JDefinedClass dbAdapterClass, Schema schema) throws JClassAlreadyExistsException {
        for (Table table: schema.getTables()) {
            generateEntityCreateDropTable(dbAdapterClass, table);
        }
    }
    
    private void generateEntityCreateDropTable(JDefinedClass dbAdapterClass, Table table) throws JClassAlreadyExistsException {
        //dbAdapterClass.field(JMod.PRIVATE|JMod.STATIC|JMod.FINAL, String.class, 
        //        DBADAPTER_SQL_ENTITY_CREATE_TABLE_PREFIX + table.getTableName() + DBADAPTER_SQL_ENTITY_CREATE_TABLE_SUFFIX, 
                //JExpr.lit("CREATE TABLE IF NOT EXISTS ").plus(JExpr.lit(table.getTableName())).plus(JExpr.lit(" (")).plus(JExpr.) 
                
        
        
        //);
    }*/
    
    
    
    
    
}
