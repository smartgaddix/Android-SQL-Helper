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

package com.sgxmobileapps.androidsqlhelper.processor.model;

import com.sgxmobileapps.androidsqlhelper.annotation.PersistentEntity;

import java.util.Arrays;
import java.util.Vector;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;

/**
 * The Table class contains the table information used by the annotation
 * processor for the generation of SQL helper code.
 * 
 * @author Massimo Gaddini
 */
public class Table implements Visitable {

    protected String        mFullyQualifiedClassName;
    protected String        mEntityName;
    protected String        mTableName;
    protected String[]      mPKConstraint;
    protected String[]      mUniqueConstraint;
    protected String        mOrderBy;
    protected String        mFieldPrefix;
    protected Vector<Field> mFields = new Vector<Field>();
    protected Field         mIdField;
    protected Schema        mSchema;
    protected boolean       mNoIdColumn;
    protected boolean       mHasIdField;

    /**
     * Builds a Table instance from an PersistentEntity annotation and the
     * annotated class Element
     * 
     * @param annotation
     *            the annotation
     * @param entity
     *            the entity
     * @return new Table instance
     */
    public static Table buildTable(PersistentEntity annotation, Element entity, Schema schema) {
        Table table = new Table();

        table.mFullyQualifiedClassName = ( (DeclaredType) entity.asType() ).asElement().toString();

        table.mEntityName = entity.getSimpleName().toString();

        if (annotation.tableName().isEmpty())
            table.mTableName = entity.getSimpleName().toString().toUpperCase();
        else
            table.mTableName = annotation.tableName().toUpperCase();

        table.mPKConstraint = annotation.pk();
        table.mUniqueConstraint = annotation.unique();
        table.mOrderBy = annotation.orderBy();
        table.mNoIdColumn = annotation.noIdCol();
        table.mHasIdField = annotation.idField() && !annotation.noIdCol();
        table.mFieldPrefix = annotation.fieldPrefix();

        table.mSchema = schema;

        if (!table.isNoIdColumn()) {
            table.mIdField = Field.buildIdField(table);
            table.addField(table.mIdField);
        }

        return table;
    }
    
    /**
     * Returns the entity name to use in method name as getter and setter
     * @return the entity name
     */
    public String getEntityNameForMethod(){
        return mEntityName.substring(0,1).toUpperCase() + mEntityName.substring(1);
    }
    
    /**
     * Returns the entity name to use in variable name
     * @return the entity name
     */
    public String getEntityNameForVar(){
        return mEntityName.substring(0,1).toLowerCase() + mEntityName.substring(1);
    }

    /*
     * @see
     * com.sgxmobileapps.androidsqlhelper.processor.model.Visitable#accept(com
     * .sgxmobileapps.androidsqlhelper.processor.model.GeneratorVisitor)
     */
    @Override
    public void accept(Visitor visitor) throws VisitorException {
        visitor.visit(this);
        for (Field field : mFields) {
            field.accept(visitor);
        }
    }

    /**
     * @return the fully qualified class name
     */
    public String getFullyQualifiedClassName() {
        return mFullyQualifiedClassName;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return mTableName;
    }
    
    /**
     * @return the pkConstraint
     */
    public String[] getPKConstraint() {
        return mPKConstraint;
    }

    /**
     * @return the uniqueConstraint
     */
    public String[] getUniqueConstraint() {
        return mUniqueConstraint;
    }

    /**
     * @return the orderBy
     */
    public String getOrderBy() {
        return mOrderBy;
    }

    /**
     * @return the fieldPrefix
     */
    public String getFieldPrefix() {
        return mFieldPrefix;
    }

    /**
     * @return the fields
     */
    public Vector<Field> getFields() {
        return mFields;
    }
    
    /**
     * @return the id field
     */
    public Field getIdField() {
        return mIdField;
    }

    /**
     * @param field
     *            the field to add
     */
    public void addField(Field field) {
        field.setIndex(mFields.size());
        mFields.add(field.getIndex(), field);
    }

    /**
     * @return the entityName
     */
    public String getEntityName() {
        return mEntityName;
    }

    /**
     * @return the noIdColumn
     */
    public boolean isNoIdColumn() {
        return mNoIdColumn;
    }
    
    /**
     * @return the hasIdField
     */
    public boolean hasIdField() {
        return mHasIdField;
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Table [");
        if (mEntityName != null) {
            builder.append("mEntityName=");
            builder.append(mEntityName);
            builder.append(", ");
        }
        if (mFieldPrefix != null) {
            builder.append("mFieldPrefix=");
            builder.append(mFieldPrefix);
            builder.append(", ");
        }
        if (mFields != null) {
            builder.append("mFields=");
            builder.append(mFields);
            builder.append(", ");
        }
        if (mIdField != null) {
            builder.append("mIdField=");
            builder.append(mIdField);
            builder.append(", ");
        }
        builder.append("mNoIdColumn=");
        builder.append(mNoIdColumn);
        builder.append(", ");
        builder.append("mHasIdField=");
        builder.append(mHasIdField);
        builder.append(", ");
        if (mOrderBy != null) {
            builder.append("mOrderBy=");
            builder.append(mOrderBy);
            builder.append(", ");
        }
        if (mTableName != null) {
            builder.append("mTableName=");
            builder.append(mTableName);
            builder.append(", ");
        }
        if (mPKConstraint != null) {
            builder.append("mPKConstraint=");
            builder.append(Arrays.toString(mPKConstraint));
        }
        if (mUniqueConstraint != null) {
            builder.append("mUniqueConstraint=");
            builder.append(Arrays.toString(mUniqueConstraint));
        }
        builder.append("]");
        return builder.toString();
    }
}
