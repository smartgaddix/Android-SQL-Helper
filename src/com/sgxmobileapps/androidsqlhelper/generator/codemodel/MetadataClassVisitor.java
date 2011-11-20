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

import com.sgxmobileapps.androidsqlhelper.generator.CodeGenerationConstants;
import com.sgxmobileapps.androidsqlhelper.processor.model.Field;
import com.sgxmobileapps.androidsqlhelper.processor.model.Schema;
import com.sgxmobileapps.androidsqlhelper.processor.model.Table;
import com.sgxmobileapps.androidsqlhelper.processor.model.Visitor;
import com.sgxmobileapps.androidsqlhelper.processor.model.VisitorContext;
import com.sgxmobileapps.androidsqlhelper.processor.model.VisitorException;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMod;


/**
 * @author Massimo Gaddini
 *
 */
public class MetadataClassVisitor implements Visitor {
    
    protected CodeModelVisitorContext ctx = null;
    
    public void startVisit(Schema schema, VisitorContext context) throws VisitorException {
        if (!(context instanceof CodeModelVisitorContext)) {
            throw new VisitorException("Invalid visitor context");
        }
        ctx = (CodeModelVisitorContext)context;    
        schema.accept(this);
    }
    
    /* 
     * @see com.sgxmobileapps.androidsqlhelper.processor.model.Visitor#visit(com.sgxmobileapps.androidsqlhelper.processor.model.Schema)
     */
    public void visit(Schema schema) throws VisitorException {
        ctx.mPckg = ctx.mCMRoot._package(schema.getPackage());
           
        try {
            ctx.mMetadataInfo.mClass = ctx.mPckg._class(schema.getMetadataClassName());
            generateMetadataJavaDoc(schema);
            generateMetadataConstants(schema);
        } catch (JClassAlreadyExistsException e) {
            throw new VisitorException("Visiting schema exception", e);
        }
    }

    /* 
     * @see com.sgxmobileapps.androidsqlhelper.processor.model.Visitor#visit(com.sgxmobileapps.androidsqlhelper.processor.model.Table)
     */
    public void visit(Table table) throws VisitorException {
        CodeModelVisitorContext.MetaTableInfo mti = ctx.getMetaTableInfo(table.getEntityName());
        try{
            mti.mClass = ctx.mMetadataInfo.mClass._class(JMod.PUBLIC|JMod.STATIC|JMod.FINAL, 
                    table.getEntityName());
            generateEntityMetadataConstants(mti, table);
        } catch (JClassAlreadyExistsException e) {
            throw new VisitorException("Visiting table " + table.getEntityName() + " exception", e);
        }
    }

    /* 
     * @see com.sgxmobileapps.androidsqlhelper.processor.model.Visitor#visit(com.sgxmobileapps.androidsqlhelper.processor.model.Field)
     */
    public void visit(Field field) throws VisitorException {
        CodeModelVisitorContext.MetaTableInfo mti = 
                ctx.getMetaTableInfo(field.getTable().getEntityName());
        CodeModelVisitorContext.MetaFieldInfo mfi = 
                ctx.getMetaFieldInfo(field.getTable().getEntityName(), field.getFieldName());
        
        generateEntityMetadataField(mti, mfi, field); 
    }
    
    private void generateMetadataJavaDoc(Schema schema) throws JClassAlreadyExistsException {
        JDocComment doc = ctx.mMetadataInfo.mClass.javadoc();
        doc.add(CodeGenerationConstants.METADATA_CLASS_JAVADOC);
        if (!schema.getAuthor().isEmpty())
            doc.add("\n\n@author " + schema.getAuthor());
    }
    
    private void generateMetadataConstants(Schema schema) throws JClassAlreadyExistsException {
        ctx.mMetadataInfo.mDbNameField = 
            ctx.mMetadataInfo.mClass.field(JMod.PUBLIC|JMod.STATIC|JMod.FINAL, 
                    String.class, 
                    CodeGenerationConstants.METADATA_DATABASE_NAME_FIELD, 
                    JExpr.lit(schema.getDbName()));
        
        ctx.mMetadataInfo.mDbVerField = 
            ctx.mMetadataInfo.mClass.field(JMod.PUBLIC|JMod.STATIC|JMod.FINAL, 
                    int.class, 
                    CodeGenerationConstants.METADATA_DATABASE_VERSION_FIELD, 
                    JExpr.lit(Integer.parseInt(schema.getDbVersion())));
    }
    
    private void generateEntityMetadataConstants(CodeModelVisitorContext.MetaTableInfo mti, Table table) throws JClassAlreadyExistsException {
        mti.mTableNameField = mti.mClass.field(JMod.PUBLIC|JMod.STATIC|JMod.FINAL, 
                String.class, 
                table.getEntityName().toUpperCase() + CodeGenerationConstants.METADATA_ENTITY_TABLE_NAME_SUFFIX, 
                JExpr.lit(table.getTableName()));
        
        if ((table.getOrderBy() == null) || (table.getOrderBy().isEmpty())) {
            mti.mDefOrderField = mti.mClass.field(JMod.PUBLIC|JMod.STATIC|JMod.FINAL, 
                    String.class, 
                    table.getEntityName().toUpperCase() + CodeGenerationConstants.METADATA_ENTITY_DEFAULT_ORDER_SUFFIX, 
                    JExpr._null());
        } else {
            mti.mDefOrderField = mti.mClass.field(JMod.PUBLIC|JMod.STATIC|JMod.FINAL, 
                    String.class, 
                    table.getEntityName().toUpperCase() + CodeGenerationConstants.METADATA_ENTITY_DEFAULT_ORDER_SUFFIX, 
                    JExpr.lit(table.getOrderBy()));
        }
    }
    
    private void generateEntityMetadataField(CodeModelVisitorContext.MetaTableInfo mti, CodeModelVisitorContext.MetaFieldInfo mfi, Field field) {
        mfi.mColNameField = mti.mClass.field(JMod.PUBLIC|JMod.STATIC|JMod.FINAL, 
                String.class, 
                field.getTable().getEntityName().toUpperCase() + "_" + field.getFieldName().toUpperCase() + CodeGenerationConstants.METADATA_ENTITY_COL_NAME_SUFFIX, 
                JExpr.lit(field.getColumnName()));
        mfi.mColIdxField = mti.mClass.field(JMod.PROTECTED|JMod.STATIC|JMod.FINAL, 
                int.class, 
                field.getTable().getEntityName().toUpperCase() + "_" + field.getFieldName().toUpperCase() + CodeGenerationConstants.METADATA_ENTITY_COL_IDX_SUFFIX, 
                JExpr.lit(field.getIndex()));
    }
}
