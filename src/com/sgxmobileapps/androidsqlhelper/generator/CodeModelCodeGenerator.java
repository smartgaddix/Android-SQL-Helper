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


import com.sgxmobileapps.androidsqlhelper.processor.model.Field;
import com.sgxmobileapps.androidsqlhelper.processor.model.Schema;
import com.sgxmobileapps.androidsqlhelper.processor.model.Table;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;

import java.io.File;
import java.io.IOException;


/**
 * @author Massimo Gaddini
 * 16/giu/2011
 */
public class CodeModelCodeGenerator implements CodeGenerator {
    private static final String METADATA_CLASS_JAVADOC = "This class defines some metadata costants used by datastore. Contains table names and column names.";
    private static final String METADATA_DATABASE_NAME_FIELD = "DATABASE_NAME";
    private static final String METADATA_DATABASE_VERSION_FIELD = "DATABASE_VERSION";
    private static final String METADATA_ENTITY_TABLE_NAME_SUFFIX = "_TABLE_NAME";
    private static final String METADATA_ENTITY_DEFAULT_ORDER_SUFFIX = "_DEFAULT_ORDER";
    private static final String METADATA_ENTITY_COL_NAME_SUFFIX = "_COL";
    private static final String METADATA_ENTITY_COL_IDX_SUFFIX = "_IDX";
    /**
     * 
     */
    public CodeModelCodeGenerator() {
    }

    @Override
    public void generate(Schema schema) throws CodeGenerationException {
       try {
            JCodeModel root = new JCodeModel();
            
            generateMetadataClass(root, schema);
            generateDbAdapterClass(root, schema);
            
            File outPath = new File(schema.getOutFolder());
            outPath.mkdirs();
            HeaderFileCodeWriter codeWriter = new HeaderFileCodeWriter(outPath, schema);
            root.build(codeWriter);
        } catch (JClassAlreadyExistsException e ){
            throw new CodeGenerationException(e);
        } catch (IOException e) {
            throw new CodeGenerationException(e);
        }
        
    }

    private void generateMetadataClass(JCodeModel root, Schema schema) throws JClassAlreadyExistsException {
        JPackage pckg = root._package(schema.getPackage());
        JDefinedClass metadataClass = pckg._class(schema.getMetadataClassName());
             
        generateMetadataJavaDoc(metadataClass, schema);
        generateMetadataConstants(metadataClass, schema);
      
        for (Table table: schema.getTables()) {
            generateEntityMetadataClass(metadataClass, table);
        }
    }
    
    private void generateMetadataJavaDoc(JDefinedClass metadataClass, Schema schema) throws JClassAlreadyExistsException {
        JDocComment doc = metadataClass.javadoc();
        doc.add(METADATA_CLASS_JAVADOC);
        if (!schema.getAuthor().isEmpty())
            doc.add("\n\n@author " + schema.getAuthor());
    }
    
    private void generateMetadataConstants(JDefinedClass metadataClass, Schema schema) throws JClassAlreadyExistsException {
        metadataClass.field(JMod.STATIC|JMod.FINAL, String.class, METADATA_DATABASE_NAME_FIELD, JExpr.lit(schema.getDbName()));
        metadataClass.field(JMod.STATIC|JMod.FINAL, int.class, METADATA_DATABASE_VERSION_FIELD, JExpr.lit(Integer.parseInt(schema.getDbVersion())));
    }

    private void generateEntityMetadataClass(JDefinedClass metadataClass, Table table) throws JClassAlreadyExistsException {
        JDefinedClass entityClass = metadataClass._class(JMod.PUBLIC|JMod.STATIC|JMod.FINAL, table.getEntityName());
        
        generateEntityMetadataConstants(entityClass, table);
       
        int i = 0;
        for (Field field: table.getFields()) {
            generateEntityMetadataColumnFields(entityClass, table.getTableName(), field.getFieldName().toUpperCase(), field.getColumnName(), i);
            i++;
        }
    }
    
    private void generateEntityMetadataConstants(JDefinedClass entityClass, Table table) throws JClassAlreadyExistsException {
        entityClass.field(JMod.PUBLIC|JMod.STATIC|JMod.FINAL, String.class, table.getTableName() + METADATA_ENTITY_TABLE_NAME_SUFFIX, JExpr.lit(table.getTableName()));
        
        if ((table.getOrderBy() == null)  || (table.getOrderBy().isEmpty())) {
            entityClass.field(JMod.PUBLIC|JMod.STATIC|JMod.FINAL, String.class, table.getTableName() + METADATA_ENTITY_DEFAULT_ORDER_SUFFIX, JExpr._null());
        } else {
            entityClass.field(JMod.PUBLIC|JMod.STATIC|JMod.FINAL, String.class, table.getTableName() + METADATA_ENTITY_DEFAULT_ORDER_SUFFIX, JExpr.lit(table.getOrderBy()));
        }
    }
    
    private void generateEntityMetadataColumnFields(JDefinedClass entityClass, String tableName, String columnName, String columnNameValue, int index) throws JClassAlreadyExistsException {
        entityClass.field(JMod.PUBLIC|JMod.STATIC|JMod.FINAL, String.class, tableName + "_" + columnName + METADATA_ENTITY_COL_NAME_SUFFIX, JExpr.lit(columnNameValue));
        entityClass.field(JMod.PROTECTED|JMod.STATIC|JMod.FINAL, int.class, tableName + "_" + columnName + METADATA_ENTITY_COL_IDX_SUFFIX, JExpr.lit(index));
    }
    
    private void generateDbAdapterClass(JCodeModel root, Schema schema) throws JClassAlreadyExistsException{
        //JDefinedClass dbAdapterClass = root._class(buildFullClassName(schema.getPackage(), schema.getDbAdapterClassName()));
    }
    
    
    
}
