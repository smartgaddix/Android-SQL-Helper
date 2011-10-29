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
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;



/**
 * @author Massimo Gaddini
 *
 */
public class DbAdapterClassVisitor implements Visitor {

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
        ctx.mPckg = ctx.mCMRoot._package(schema.getPackage());

        try {
            generateDbAdapterClass(schema);
            generateDbAdapterJavaDoc(schema);
            generateDbHelperClass();
        } catch (JClassAlreadyExistsException e) {
            throw new VisitorException("Visiting schema exception", e);
        }
    }

    /*
     * @see com.sgxmobileapps.androidsqlhelper.processor.model.Visitor#visit(com.sgxmobileapps.androidsqlhelper.processor.model.Table)
     */
    @Override
    public void visit(Table table) throws VisitorException {
        CodeModelVisitorContext.DbAdapterTableInfo dbati = ctx.getDbAdapterTableInfo(table.getEntityName());
        CodeModelVisitorContext.MetaTableInfo mti = ctx.getMetaTableInfo(table.getEntityName());
        try{
            generateEntityCreateTable(mti, dbati, table);
            generateEntityDropTable(mti, dbati, table);
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

    private void generateDbAdapterClass(Schema schema) throws JClassAlreadyExistsException {
		ctx.mDbAdapterInfo.mClass = ctx.mPckg._class(JMod.ABSTRACT|JMod.PUBLIC, schema.getDbAdapterClassName());
        ctx.mDbAdapterInfo.mHelperClass = ctx.mDbAdapterInfo.mClass._class(JMod.PRIVATE|JMod.STATIC, CodeGenerationConstants.DBADAPTER_DBHELPER_CLASS_NAME);
        ctx.mDbAdapterInfo.mHelperClass._extends(ctx.mCMRoot.ref(android.database.sqlite.SQLiteOpenHelper.class));

		JFieldVar dbHelperField = ctx.mDbAdapterInfo.mClass.field(JMod.PRIVATE, ctx.mDbAdapterInfo.mHelperClass, CodeGenerationConstants.DBADAPTER_DBHELPER_MEMBER_NAME);
		dbHelperField.javadoc().add(CodeGenerationConstants.DBADAPTER_DBHELPER_MEMBER_JAVADOC);
		JFieldVar dbField = ctx.mDbAdapterInfo.mClass.field(JMod.PRIVATE, android.database.sqlite.SQLiteDatabase.class, CodeGenerationConstants.DBADAPTER_DB_MEMBER_NAME);
		dbField.javadoc().add(CodeGenerationConstants.DBADAPTER_DB_MEMBER_JAVADOC);
		
		JMethod cstr = ctx.mDbAdapterInfo.mClass.constructor(JMod.PUBLIC);
        JVar ctxVar = cstr.param(ctx.mCMRoot.ref(android.content.Context.class), CodeGenerationConstants.DBADAPTER_CONSTRUCTOR_METHOD_PARAM_CTX_NAME);
        cstr.javadoc().add(CodeGenerationConstants.DBADAPTER_CONSTRUCTOR_METHOD_JAVADOC);
        cstr.javadoc().addParam(ctxVar).add(CodeGenerationConstants.DBADAPTER_CONSTRUCTOR_METHOD_PARAM_CTX_JAVADOC);
        JBlock cstrBody = cstr.body();
        cstrBody.assign(dbHelperField, 
                JExpr._new(ctx.mDbAdapterInfo.mHelperClass)
                .arg(ctxVar)
                .arg(ctx.mMetadataInfo.mDbNameField)
                .arg(JExpr._null())
                .arg(ctx.mMetadataInfo.mDbVerField)
                .arg(JExpr._this()));
        cstrBody._return(JExpr._this());
        
        JMethod openMethod = ctx.mDbAdapterInfo.mClass.method(JMod.PUBLIC, ctx.mDbAdapterInfo.mClass, CodeGenerationConstants.DBADAPTER_OPEN_METHOD_NAME);
        openMethod._throws(android.database.SQLException.class);
        openMethod.javadoc().add(CodeGenerationConstants.DBADAPTER_OPEN_METHOD_JAVADOC);
        openMethod.javadoc().addThrows(android.database.SQLException.class).add(CodeGenerationConstants.DBADAPTER_OPEN_METHOD_EXCP_JAVADOC);
        openMethod.javadoc().addReturn().add(CodeGenerationConstants.DBADAPTER_OPEN_METHOD_RETURN_JAVADOC);
        JBlock openBody = openMethod.body();
        openBody.assign(dbField, JExpr.invoke(dbHelperField, "getWritableDatabase"));
        openBody._return(JExpr._this());

        JMethod closeMethod = ctx.mDbAdapterInfo.mClass.method(JMod.PUBLIC, ctx.mCMRoot.VOID, CodeGenerationConstants.DBADAPTER_CLOSE_METHOD_NAME);
        closeMethod.javadoc().add(CodeGenerationConstants.DBADAPTER_CLOSE_METHOD_JAVADOC);
        JBlock closeBody = closeMethod.body();
        closeBody._if(dbField.ne(JExpr._null()))._then().invoke(dbField, "close");
        
		ctx.mDbAdapterInfo.mOnUpgradeMethod = ctx.mDbAdapterInfo.mClass.method(JMod.PUBLIC, Boolean.class, CodeGenerationConstants.DBADAPTER_ONUPGRADE_METHOD_NAME);
		JVar dbVar = ctx.mDbAdapterInfo.mOnUpgradeMethod.param(android.database.sqlite.SQLiteDatabase.class, CodeGenerationConstants.DBADAPTER_ONUPGRADE_METHOD_PARAM_DB_NAME);
		JVar oldvVar = ctx.mDbAdapterInfo.mOnUpgradeMethod.param(ctx.mCMRoot.INT, CodeGenerationConstants.DBADAPTER_ONUPGRADE_METHOD_PARAM_OLDV_NAME);
		JVar newvVar = ctx.mDbAdapterInfo.mOnUpgradeMethod.param(ctx.mCMRoot.INT, CodeGenerationConstants.DBADAPTER_ONUPGRADE_METHOD_PARAM_NEWV_NAME);
		ctx.mDbAdapterInfo.mOnUpgradeMethod.annotate(Override.class);
		ctx.mDbAdapterInfo.mOnUpgradeMethod.javadoc().add(CodeGenerationConstants.DBADAPTER_ONUPGRADE_METHOD_JAVADOC);
		ctx.mDbAdapterInfo.mOnUpgradeMethod.javadoc().addParam(dbVar).add(CodeGenerationConstants.DBADAPTER_ONUPGRADE_METHOD_PARAM_DB_JAVADOC);
		ctx.mDbAdapterInfo.mOnUpgradeMethod.javadoc().addParam(oldvVar).add(CodeGenerationConstants.DBADAPTER_ONUPGRADE_METHOD_PARAM_OLDV_JAVADOC);
		ctx.mDbAdapterInfo.mOnUpgradeMethod.javadoc().addParam(newvVar).add(CodeGenerationConstants.DBADAPTER_ONUPGRADE_METHOD_PARAM_NEWV_JAVADOC);
		ctx.mDbAdapterInfo.mOnUpgradeMethod.javadoc().addReturn().add(CodeGenerationConstants.DBADAPTER_ONUPGRADE_METHOD_RETURN_JAVADOC);
		ctx.mDbAdapterInfo.mOnUpgradeMethod.body()._return(JExpr.FALSE);
	}

    private void generateDbAdapterJavaDoc(Schema schema) throws JClassAlreadyExistsException {
        JDocComment doc = ctx.mDbAdapterInfo.mClass.javadoc();
        doc.add(CodeGenerationConstants.DBADAPTER_CLASS_JAVADOC);
        if (!schema.getAuthor().isEmpty())
            doc.add("\n\n@author " + schema.getAuthor());
    }

    private void generateDbHelperClass() throws JClassAlreadyExistsException {

        JFieldVar dataStoreField = ctx.mDbAdapterInfo.mHelperClass.field(JMod.PRIVATE, ctx.mDbAdapterInfo.mClass, CodeGenerationConstants.DBADAPTER_DBHELPER_DATASTORE_MEMBER_NAME);

        JMethod cstr = ctx.mDbAdapterInfo.mHelperClass.constructor(JMod.PUBLIC);
        JVar ctxVar = cstr.param(ctx.mCMRoot.ref(android.content.Context.class), CodeGenerationConstants.DBADAPTER_DBHELPER_CONSTRUCTOR_METHOD_PARAM_CTX_NAME);
        JVar nameVar = cstr.param(ctx.mCMRoot.ref(String.class), CodeGenerationConstants.DBADAPTER_DBHELPER_CONSTRUCTOR_METHOD_PARAM_NAME_NAME);
        JVar factoryVar = cstr.param(ctx.mCMRoot.ref(android.database.sqlite.SQLiteDatabase.CursorFactory.class), CodeGenerationConstants.DBADAPTER_DBHELPER_CONSTRUCTOR_METHOD_PARAM_FACTORY_NAME);
        JVar verVar = cstr.param(ctx.mCMRoot.INT, CodeGenerationConstants.DBADAPTER_DBHELPER_CONSTRUCTOR_METHOD_PARAM_VERSION_NAME);
        JVar datastoreVar = cstr.param(ctx.mDbAdapterInfo.mClass, CodeGenerationConstants.DBADAPTER_DBHELPER_CONSTRUCTOR_METHOD_PARAM_DATASTORE_NAME);

        JBlock cstrBody = cstr.body();
        cstrBody.invoke("super").arg(ctxVar).arg(nameVar).arg(factoryVar).arg(verVar);
        cstrBody.assign(dataStoreField, datastoreVar);


        JMethod onCreateMethod = ctx.mDbAdapterInfo.mHelperClass.method(JMod.PUBLIC, ctx.mCMRoot.VOID, CodeGenerationConstants.DBADAPTER_DBHELPER_ONCREATE_METHOD_NAME);
        onCreateMethod.param(android.database.sqlite.SQLiteDatabase.class, CodeGenerationConstants.DBADAPTER_DBHELPER_ONCREATE_METHOD_PARAM_DB_NAME);
        onCreateMethod.annotate(Override.class);
        onCreateMethod.javadoc().add(CodeGenerationConstants.DBADAPTER_DBHELPER_ONCREATE_METHOD_JAVADOC);

        ctx.mDbAdapterInfo.mHelperOnCreateMethodBody = onCreateMethod.body();

        JMethod onUpgradeMethod = ctx.mDbAdapterInfo.mHelperClass.method(JMod.PUBLIC, ctx.mCMRoot.VOID, CodeGenerationConstants.DBADAPTER_DBHELPER_ONUPGRADE_METHOD_NAME);
        JVar dbVar = onUpgradeMethod.param(android.database.sqlite.SQLiteDatabase.class, CodeGenerationConstants.DBADAPTER_DBHELPER_ONUPGRADE_METHOD_PARAM_DB_NAME);
        JVar oldvVar = onUpgradeMethod.param(ctx.mCMRoot.INT, CodeGenerationConstants.DBADAPTER_DBHELPER_ONUPGRADE_METHOD_PARAM_OLDV_NAME);
        JVar newvVar = onUpgradeMethod.param(ctx.mCMRoot.INT, CodeGenerationConstants.DBADAPTER_DBHELPER_ONUPGRADE_METHOD_PARAM_NEWV_NAME);
        onUpgradeMethod.annotate(Override.class);
        onUpgradeMethod.javadoc().add(CodeGenerationConstants.DBADAPTER_DBHELPER_ONUPGRADE_METHOD_JAVADOC);

        ctx.mDbAdapterInfo.mHelperOnUpgradeMethodBody = onUpgradeMethod.body();
        ctx.mDbAdapterInfo.mHelperOnUpgradeMethodBody._if(dataStoreField.invoke(ctx.mDbAdapterInfo.mOnUpgradeMethod).arg(dbVar).arg(oldvVar).arg(newvVar))._then()._return();
        ctx.mDbAdapterInfo.mHelperOnUpgradeMethodBody.directStatement(CodeGenerationConstants.DBADAPTER_DBHELPER_ONUPGRADE_METHOD_CODE_COMMENT1);
        ctx.mDbAdapterInfo.mHelperOnUpgradeMethodBody.directStatement(CodeGenerationConstants.DBADAPTER_DBHELPER_ONUPGRADE_METHOD_CODE_COMMENT2);
        ctx.mDbAdapterInfo.mHelperOnUpgradeMethodBody.directStatement(CodeGenerationConstants.DBADAPTER_DBHELPER_ONUPGRADE_METHOD_CODE_COMMENT3);
        ctx.mDbAdapterInfo.mHelperOnUpgradeMethodBody.invoke(CodeGenerationConstants.DBADAPTER_DBHELPER_ONCREATE_METHOD_NAME).arg(dbVar);

        ctx.mDbAdapterInfo.mHelperOnUpgradeMethodBody.pos(3);  //prepare for drop statement insertion
    }

    private void generateEntityCreateTable(CodeModelVisitorContext.MetaTableInfo mti, CodeModelVisitorContext.DbAdapterTableInfo dbati, Table table) throws JClassAlreadyExistsException {
        FormattedExpression createTableExpr = FormattedExpression.lit(CodeGenerationConstants.DBADAPTER_SQL_CREATE_TABLE_PREFIX + " ", false, false);

        createTableExpr = createTableExpr.add(mti.mClass.staticRef(mti.mTableNameField), false, false);
        createTableExpr = createTableExpr.add(FormattedExpression.lit(" (", false, false), false, false);

        for(int i = 0; i < table.getFields().size(); i++){
            Field field = table.getFields().get(i);
            CodeModelVisitorContext.MetaFieldInfo mfi = ctx.getMetaFieldInfo(table.getEntityName(), field.getFieldName());
            createTableExpr = createTableExpr.add(mti.mClass.staticRef(mfi.mColNameField), false, true);
            createTableExpr = createTableExpr.add(JExpr.lit(" " + field.getColumnDefinition() + ((i < (table.getFields().size()-1))?", ":"")), false, false);
        }

        createTableExpr = createTableExpr.add(JExpr.lit(" );"), false, false);

        dbati.mCreateTableField =
            ctx.mDbAdapterInfo.mClass.field(JMod.PRIVATE|JMod.STATIC|JMod.FINAL, String.class,
                    CodeGenerationConstants.DBADAPTER_SQL_ENTITY_CREATE_TABLE_PREFIX + table.getTableName() + CodeGenerationConstants.DBADAPTER_SQL_ENTITY_CREATE_TABLE_SUFFIX,
                    createTableExpr );

        ctx.mDbAdapterInfo.mHelperOnCreateMethodBody.invoke(ctx.mDbAdapterInfo.mHelperOnCreateDbParam, "execSQL").arg(dbati.mCreateTableField);
    }

    private void generateEntityDropTable(CodeModelVisitorContext.MetaTableInfo mti, CodeModelVisitorContext.DbAdapterTableInfo dbati, Table table) throws JClassAlreadyExistsException {
        FormattedExpression dropTableExpr = FormattedExpression.lit(CodeGenerationConstants.DBADAPTER_SQL_DROP_TABLE_PREFIX + " ", false, false);

        dropTableExpr = dropTableExpr.add(mti.mClass.staticRef(mti.mTableNameField), false, false);
        dropTableExpr = dropTableExpr.add(FormattedExpression.lit(";", false, false), false, false);

        dbati.mDropTableField =
            ctx.mDbAdapterInfo.mClass.field(JMod.PRIVATE|JMod.STATIC|JMod.FINAL, String.class,
                    CodeGenerationConstants.DBADAPTER_SQL_ENTITY_DROP_TABLE_PREFIX + table.getTableName() + CodeGenerationConstants.DBADAPTER_SQL_ENTITY_DROP_TABLE_SUFFIX,
                    dropTableExpr );

        ctx.mDbAdapterInfo.mHelperOnUpgradeMethodBody.invoke(ctx.mDbAdapterInfo.mHelperOnUpgradeDbParam, "execSQL").arg(dbati.mDropTableField);
    }
}