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
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;



/**
 * @author Massimo Gaddini
 *
 */
public class HelperFunctionsVisitor implements Visitor {

    protected CodeModelVisitorContext ctx = null;

    /*
     * @see com.sgxmobileapps.androidsqlhelper.processor.model.Visitor#startVisit(com.sgxmobileapps.androidsqlhelper.processor.model.Schema, com.sgxmobileapps.androidsqlhelper.processor.model.VisitorContext)
     */
    @Override
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
    @Override
    public void visit(Schema schema) throws VisitorException {
    }

    /*
     * @see com.sgxmobileapps.androidsqlhelper.processor.model.Visitor#visit(com.sgxmobileapps.androidsqlhelper.processor.model.Table)
     */
    @Override
    public void visit(Table table) throws VisitorException {
        CodeModelVisitorContext.DbAdapterTableInfo dbati = ctx.getDbAdapterTableInfo(table.getEntityName());
        CodeModelVisitorContext.MetaTableInfo mti = ctx.getMetaTableInfo(table.getEntityName());
        
        try {
            generateMethodGetContentValues(mti, dbati, table);
        } catch (JClassAlreadyExistsException e) {
            throw new VisitorException("Visiting table " + table.getEntityName() + " exception", e);
        } 
    }

    /*
     * @see com.sgxmobileapps.androidsqlhelper.processor.model.Visitor#visit(com.sgxmobileapps.androidsqlhelper.processor.model.Field)
     */
    @Override
    public void visit(Field field) throws VisitorException {
    }
    
    private void generateMethodGetContentValues(CodeModelVisitorContext.MetaTableInfo mti, CodeModelVisitorContext.DbAdapterTableInfo dbati, Table table) throws JClassAlreadyExistsException {
        JType contentValuesType = ctx.mCMRoot._ref(android.content.ContentValues.class);
        dbati.mGetContentValuesMethod = 
                ctx.mDbAdapterInfo.mClass.method(JMod.PRIVATE, 
                        contentValuesType, 
                        CodeGenerationConstants.DBADAPTER_HELPER_METHOD_GETCONTENTVALUES_NAME);
        
        dbati.mClass = ctx.mCMRoot.ref(table.getFullyQualifiedClassName());
        JVar entityParam = dbati.mGetContentValuesMethod.param(dbati.mClass, "entity");
        
        JBlock methodBody = dbati.mGetContentValuesMethod.body();
        JVar valuesVar = methodBody.decl(contentValuesType, 
                "contentValues", 
                JExpr._new(contentValuesType));
       
        for(int i = 0; i < table.getFields().size(); i++){
            Field field = table.getFields().get(i);
            if (field.isIdField()) {
                continue;
            }
            
            CodeModelVisitorContext.MetaFieldInfo mfi = ctx.getMetaFieldInfo(table.getEntityName(), field.getFieldName());
            JExpression getterExpression = JExpr.invoke(entityParam, field.getGetterMethod());
            if (field.getGetterConvMethod() != null){
                getterExpression = JExpr.invoke(getterExpression, field.getGetterConvMethod());
            } else if (field.isStringfy()) {
                getterExpression = JExpr.lit("").plus(getterExpression);
            }
            
            methodBody.invoke(valuesVar, "put")
                .arg(mti.mClass.staticRef(mfi.mColNameField))
                .arg(getterExpression);
        }
        
        methodBody._return(valuesVar);
    }
        
}
