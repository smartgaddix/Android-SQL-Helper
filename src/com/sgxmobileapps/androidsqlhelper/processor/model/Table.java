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
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.lang.model.element.Element;


/**
 * The Table class contains the table information used by the annotation
 * processor for the generation of SQL helper code.
 * 
 * @author Massimo Gaddini
 * Jun 4, 2011
 */
public class Table {
    protected String mEntityName;
    protected String mTableName;
    protected String[] mUniqueConstraint;
    protected String mOrderBy;
    protected String mFieldPrefix;
    protected Vector<Field> mFields = new Vector<Field>();
    protected Schema mSchema;
    protected boolean mNoIdColumn;
    
    /**
     * Builds a Table instance from an PersistentEntity annotation and
     * the annotated class Element 
     * @param annotation the annotation
     * @param entity the entity
     * @return new Table instance
     */
    public static Table buildTable(PersistentEntity annotation, Element entity, Schema schema){
        Table table = new Table();
        
        table.mEntityName = entity.getSimpleName().toString();
        
        if (annotation.tableName().isEmpty())
            table.mTableName = entity.getSimpleName().toString().toUpperCase();
        else
            table.mTableName = annotation.tableName().toUpperCase();
        
        table.mUniqueConstraint = annotation.unique();
        table.mOrderBy = annotation.orderBy();
        table.mNoIdColumn = annotation.noIdCol();
        table.mFieldPrefix = annotation.fieldPrefix();
        
        table.mSchema = schema;
    
        if (!table.isNoIdColumn())
            table.addField(Field.buildIdField(table));
 
        return table;
    }
    
    /**
     * @return the tableName
     */
    public String getTableName() {
        return mTableName;
    }
    
    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        mTableName = tableName;
    }
    
    /**
     * @return the uniqueConstraint
     */
    public String[] getUniqueConstraint() {
        return mUniqueConstraint;
    }
   
    /**
     * @param uniqueConstraint the uniqueConstraint to set
     */
    public void setUniqueConstraint(String[] uniqueConstraint) {
        mUniqueConstraint = uniqueConstraint;
    }
    
    /**
     * @return the orderBy
     */
    public String getOrderBy() {
        return mOrderBy;
    }
    
    /**
     * @param orderBy the orderBy to set
     */
    public void setOrderBy(String orderBy) {
        mOrderBy = orderBy;
    }
    
    /**
     * @return the fieldPrefix
     */
    public String getFieldPrefix() {
        return mFieldPrefix;
    }
    
    /**
     * @param fieldPrefix the fieldPrefix to set
     */
    public void setFieldPrefix(String fieldPrefix) {
        mFieldPrefix = fieldPrefix;
    }
        
    /**
     * @return the fields
     */
    public Vector<Field> getFields() {
        return mFields;
    }
    
    /**
     * @param fields the fields to set
     */
    public void setFields(Vector<Field> fields) {
        mFields = fields;
    }

    /**
     * @param field the field to add
     */
    public void addField(Field field) {
        mFields.add(field);
    }   

    /**
     * Finds the field with the specified fieldname (witout prefix)
     * @param fieldName the field name without prefix
     * @return the Field class for the fieldName if exists. null otherwise.
     */
    public Field getFieldByFieldName(String fieldName) {
        for (Field field: mFields) {
            if (field.getFieldName().equals(fieldName))
                return field;
        }
        
        return null;
    }
    
    /**
     * @return the schema
     */
    public Schema getSchema() {
        return mSchema;
    }
    
    /**
     * @param schema the schema to set
     */
    public void setSchema(Schema schema) {
        mSchema = schema;
    }
    
    /**
     * @return the entityName
     */
    public String getEntityName() {
        return mEntityName;
    }
    
    /**
     * @param entityName the entityName to set
     */
    public void setEntityName(String entityName) {
        mEntityName = entityName;
    }
    
    /**
     * @return the noIdColumn
     */
    public boolean isNoIdColumn() {
        return mNoIdColumn;
    }
    
    /**
     * @param noIdColumn the noIdColumn to set
     */
    public void setNoIdColumn(boolean noIdColumn) {
        mNoIdColumn = noIdColumn;
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
        builder.append("mNoIdColumn=");
        builder.append(mNoIdColumn);
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
        if (mUniqueConstraint != null) {
            builder.append("mUniqueConstraint=");
            builder.append(Arrays.toString(mUniqueConstraint));
        }
        builder.append("]");
        return builder.toString();
    }



}
