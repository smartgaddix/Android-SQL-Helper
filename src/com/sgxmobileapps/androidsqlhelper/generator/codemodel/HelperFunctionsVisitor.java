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
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JOp;
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
            dbati.mClass = ctx.mCMRoot.ref(table.getFullyQualifiedClassName());
            generateMethodGetContentValues(mti, dbati, table);
            generateMethodFillFromCursor(mti, dbati, table);
            generateMethodAddEntity(mti, dbati, table);
            generateMethodUpdateEntityById(mti, dbati, table);
            generateMethodUpdateEntityByKey(mti, dbati, table);
            generateMethodRemoveEntityById(mti, dbati, table);
            generateMethodRemoveAllEntity(mti, dbati, table);
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
        
        JVar entityParam = dbati.mGetContentValuesMethod.param(dbati.mClass, table.getEntityNameForVar());
        
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
            } 
            
            methodBody.invoke(valuesVar, "put")
                .arg(mti.mClass.staticRef(mfi.mColNameField))
                .arg(getterExpression);
        }
        
        methodBody._return(valuesVar);
    }
    
    
    private void generateMethodFillFromCursor(CodeModelVisitorContext.MetaTableInfo mti, CodeModelVisitorContext.DbAdapterTableInfo dbati, Table table) throws JClassAlreadyExistsException {
        dbati.mFillFromCursorMethod = 
                ctx.mDbAdapterInfo.mClass.method(JMod.PRIVATE, 
                        dbati.mClass, 
                        CodeGenerationConstants.DBADAPTER_HELPER_METHOD_FILLFROMCURSOR_NAME_PREFIX + table.getEntityNameForMethod());
        
        JVar cursorParam = dbati.mFillFromCursorMethod.param(ctx.mCMRoot._ref(android.database.Cursor.class), "cursor");
        
        JBlock methodBody = dbati.mFillFromCursorMethod.body();
        JVar entityVar = methodBody.decl(dbati.mClass, 
                table.getEntityNameForVar(), 
                JExpr._new(dbati.mClass));
       
        for(int i = 0; i < table.getFields().size(); i++){
            Field field = table.getFields().get(i);
            if (field.isIdField()) {
                continue;
            }
            
            CodeModelVisitorContext.MetaFieldInfo mfi = ctx.getMetaFieldInfo(table.getEntityName(), field.getFieldName());
            
            JExpression cursorGetterExpression = 
                    JExpr.invoke(cursorParam, field.getCursorGetterMethod())
                    .arg(mti.mClass.staticRef(mfi.mColIdxField));
            
            if (field.getSetterConvMethod() != null) {
                JVar var = methodBody.decl(
                        ctx.mCMRoot.ref(field.getClazz().getName()), 
                        field.getFieldNameForVar(), 
                        JExpr._new(ctx.mCMRoot.ref(field.getClazz().getName())));
                
                methodBody.invoke(var, field.getSetterConvMethod()).arg(cursorGetterExpression);
                cursorGetterExpression = var;
            } else if (field.getClazz().equals(boolean.class) || field.getClazz().equals(Boolean.class)) {
                cursorGetterExpression = cursorGetterExpression.gt(JExpr.lit(0));
            } else if (field.getClazzCast() != null) {
                cursorGetterExpression = JExpr.cast(ctx.mCMRoot._ref(field.getClazzCast()), cursorGetterExpression);
            }
            
            methodBody.invoke(entityVar, field.getSetterMethod())
                .arg(cursorGetterExpression);
        }
        
        methodBody._return(entityVar);
    }
    
    private void generateMethodAddEntity(CodeModelVisitorContext.MetaTableInfo mti, CodeModelVisitorContext.DbAdapterTableInfo dbati, Table table) throws JClassAlreadyExistsException {
        
        JMethod addMethod = ctx.mDbAdapterInfo.mClass.method(JMod.PUBLIC, 
                long.class, 
                CodeGenerationConstants.DBADAPTER_HELPER_METHOD_ADDENTITY_NAME_PREFIX + table.getEntityNameForMethod());
        
        JVar entityParam = addMethod.param(dbati.mClass, table.getEntityNameForVar());
        
        JDocComment jdoc = addMethod.javadoc();
        jdoc.add("Inserts a " + table.getEntityName() + " to database");
        jdoc.addParam(entityParam).add("The " + table.getEntityName() + " to insert");
        jdoc.addReturn().add("the row ID of the newly inserted row, or -1 if an error occurred");
        
        JInvocation insertInvocation = ctx.mDbAdapterInfo.mDbField.invoke("insert")
                .arg(mti.mClass.staticRef(mti.mTableNameField)).arg(JExpr._null())
                .arg(JExpr.invoke(dbati.mGetContentValuesMethod).arg(entityParam));
        
        addMethod.body()._return(insertInvocation);
    }
    
    private void generateMethodUpdateEntityById(CodeModelVisitorContext.MetaTableInfo mti, CodeModelVisitorContext.DbAdapterTableInfo dbati, Table table) throws JClassAlreadyExistsException {
        
        if (table.isNoIdColumn()) {
            return;
        }
        
        Field idField = table.getIdField();
        CodeModelVisitorContext.MetaFieldInfo mfi = ctx.getMetaFieldInfo(table.getEntityName(), idField.getFieldName());
        
        /* update method */
        JMethod updateMethod = ctx.mDbAdapterInfo.mClass.method(JMod.PUBLIC, 
                long.class, 
                CodeGenerationConstants.DBADAPTER_HELPER_METHOD_UPDATEENTITY_NAME_PREFIX + table.getEntityNameForMethod());
        
        JVar idParam = updateMethod.param(idField.getClazz(), idField.getFieldNameForVar());
        JVar entityParam = updateMethod.param(dbati.mClass, table.getEntityNameForVar());
                
        JDocComment jdoc = updateMethod.javadoc();
        jdoc.add("Updates a " + table.getEntityName() + " in database");
        jdoc.addParam(idParam).add("The id of the " + table.getEntityName() + " to update");
        jdoc.addParam(entityParam).add("The " + table.getEntityName() + " to update");
        jdoc.addReturn().add("the number of rows affected (1 or 0)");
        
        JBlock updateBody = updateMethod.body();
        JExpression whereExpression = 
                FormattedExpression.plus(mti.mClass.staticRef(mfi.mColNameField), JExpr.lit(" = "), false, false)
                .add(idParam, false, false);
        JVar whereVar = updateBody.decl(ctx.mCMRoot._ref(String.class), "where", whereExpression);
   
        
        JInvocation updateInvocation = ctx.mDbAdapterInfo.mDbField.invoke("update")
                .arg(mti.mClass.staticRef(mti.mTableNameField))
                .arg(JExpr.invoke(dbati.mGetContentValuesMethod).arg(entityParam))
                .arg(whereVar)
                .arg(JExpr._null());
        
        updateMethod.body()._return(updateInvocation);
    }
    
    private void generateMethodUpdateEntityByKey(CodeModelVisitorContext.MetaTableInfo mti, CodeModelVisitorContext.DbAdapterTableInfo dbati, Table table) throws JClassAlreadyExistsException {
        
        String[] keyFields = null;
        if (table.getPKConstraint().length > 0) {
            keyFields = table.getPKConstraint();
        } else if (table.getUniqueConstraint().length > 0){
            keyFields = table.getUniqueConstraint();
        }
        
        if (keyFields == null){
            return;
        }

        /* update method */
        JMethod updateMethod = ctx.mDbAdapterInfo.mClass.method(JMod.PUBLIC, 
                long.class, 
                CodeGenerationConstants.DBADAPTER_HELPER_METHOD_UPDATEENTITY_NAME_PREFIX + table.getEntityNameForMethod());
        
        JVar entityParam = updateMethod.param(dbati.mClass, table.getEntityNameForVar());
                
        JDocComment jdoc = updateMethod.javadoc();
        jdoc.add("Updates a " + table.getEntityName() + " in database");
        jdoc.addParam(entityParam).add("The " + table.getEntityName() + " to update");
        jdoc.addReturn().add("the number of rows affected (1 or 0)");
        
        
        
        JExpression whereExpression = null;
        for(int i = 0; i < keyFields.length; i++){
            CodeModelVisitorContext.MetaFieldInfo mfi = ctx.getMetaFieldInfo(table.getEntityName(), keyFields[i]);
            
            whereExpression = FormattedExpression.plus(whereExpression, mti.mClass.staticRef(mfi.mColNameField), false, false);
            
            if (mfi.mField.isNumberField()) {
                whereExpression = FormattedExpression.plus(whereExpression, JExpr.lit(" = "), false, false);
            } else {
                whereExpression = FormattedExpression.plus(whereExpression, JExpr.lit(" = '"), false, false);
            }

            
            JExpression getterExpression = JExpr.invoke(entityParam, mfi.mField.getGetterMethod());
            if (mfi.mField.getGetterConvMethod() != null){
                getterExpression = JExpr.invoke(getterExpression, mfi.mField.getGetterConvMethod());
            } 
            
            if (mfi.mField.getClazz().equals(boolean.class) || mfi.mField.getClazz().equals(Boolean.class)) {
                
                if (mfi.mField.getClazzCast() != null) {
                    getterExpression = JExpr.cast(ctx.mCMRoot._ref(mfi.mField.getClazzCast()), getterExpression);
                }
                getterExpression = JOp.cond(getterExpression, JExpr.lit(1), JExpr.lit(0));
            }
            
            whereExpression = FormattedExpression.plus(whereExpression, getterExpression, false, false);
            
            String endStr = "";
            if (!mfi.mField.isNumberField()) {
                endStr += "'";
            }
            
            if (i < (keyFields.length - 1)) {
                endStr += " AND ";
            }
            
            if (!endStr.isEmpty()) {
                whereExpression = FormattedExpression.plus(whereExpression, JExpr.lit(endStr), false, false);
            }
        }
        
        JBlock updateBody = updateMethod.body();
        JVar whereVar = updateBody.decl(ctx.mCMRoot._ref(String.class), "where", whereExpression);
   
        
        JInvocation updateInvocation = ctx.mDbAdapterInfo.mDbField.invoke("update")
                .arg(mti.mClass.staticRef(mti.mTableNameField))
                .arg(JExpr.invoke(dbati.mGetContentValuesMethod).arg(entityParam))
                .arg(whereVar)
                .arg(JExpr._null());
        
        updateMethod.body()._return(updateInvocation);
    }
      
    private void generateMethodRemoveEntityById(CodeModelVisitorContext.MetaTableInfo mti, CodeModelVisitorContext.DbAdapterTableInfo dbati, Table table) throws JClassAlreadyExistsException {
        
        if (table.isNoIdColumn()) {
            return;
        }
        
        Field idField = table.getIdField();
        CodeModelVisitorContext.MetaFieldInfo mfi = ctx.getMetaFieldInfo(table.getEntityName(), idField.getFieldName());
        
        /* remove method */
        JMethod removeMethod = ctx.mDbAdapterInfo.mClass.method(JMod.PUBLIC, 
                long.class, 
                CodeGenerationConstants.DBADAPTER_HELPER_METHOD_REMOVEENTITY_NAME_PREFIX + table.getEntityNameForMethod());
        
        JVar idParam = removeMethod.param(idField.getClazz(), idField.getFieldNameForVar());
                
        JDocComment jdoc = removeMethod.javadoc();
        jdoc.add("Deletes a " + table.getEntityName() + " from database");
        jdoc.addParam(idParam).add("The id of the " + table.getEntityName() + " to delete");
        jdoc.addReturn().add("the number of rows affected (1 or 0)");
        
        JBlock removeBody = removeMethod.body();
        JExpression whereExpression = 
                FormattedExpression.plus(mti.mClass.staticRef(mfi.mColNameField), JExpr.lit(" = "), false, false)
                .add(idParam, false, false);
        JVar whereVar = removeBody.decl(ctx.mCMRoot._ref(String.class), "where", whereExpression);
   
        
        JInvocation removeInvocation = ctx.mDbAdapterInfo.mDbField.invoke("delete")
                .arg(mti.mClass.staticRef(mti.mTableNameField))
                .arg(whereVar)
                .arg(JExpr._null());
        
        removeMethod.body()._return(removeInvocation);
    } 
    
    private void generateMethodRemoveAllEntity(CodeModelVisitorContext.MetaTableInfo mti, CodeModelVisitorContext.DbAdapterTableInfo dbati, Table table) throws JClassAlreadyExistsException {
        /* remove all method */
        JMethod removeAllMethod = ctx.mDbAdapterInfo.mClass.method(JMod.PUBLIC, 
                long.class, 
                CodeGenerationConstants.DBADAPTER_HELPER_METHOD_REMOVEALL_NAME_PREFIX + table.getEntityNameForMethod());
                
        JDocComment jdoc = removeAllMethod.javadoc();
        jdoc.add("Deletes all " + table.getEntityName() + " from database");
        jdoc.addReturn().add("the number of rows deleted");

        JInvocation removeAllInvocation = ctx.mDbAdapterInfo.mDbField.invoke("delete")
                .arg(mti.mClass.staticRef(mti.mTableNameField))
                .arg(JExpr.lit("1"))
                .arg(JExpr._null());
        
        removeAllMethod.body()._return(removeAllInvocation);
    } 
}
