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

import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.Element;


/**
 * The Table class contains the table information used by the annotation
 * processor for the generation of SQL helper code.
 * 
 * @author Massimo Gaddini
 * Jun 4, 2011
 */
public class Table {
    protected String mTableName;
    protected String[] mUniqueConstraint;
    protected String[] mOrderBy;
    protected boolean mNoIdColumn;
    protected Set<Field> mFields = new HashSet<Field>();
    
    /**
     * Builds a Table instance from an PersistentEntity annotation and
     * the annotated class Element 
     * @param annotation the annotation
     * @param entity the entity
     * @return new Table instance
     */
    public static Table buildTable(PersistentEntity annotation, Element entity){
        return new Table();
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
    public String[] getOrderBy() {
        return mOrderBy;
    }
    
    /**
     * @param orderBy the orderBy to set
     */
    public void setOrderBy(String[] orderBy) {
        mOrderBy = orderBy;
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
    
    /**
     * @return the fields
     */
    public Set<Field> getFields() {
        return mFields;
    }
    
    /**
     * @param fields the fields to set
     */
    public void setFields(Set<Field> fields) {
        mFields = fields;
    }

    /**
     * @param field the field to add
     */
    public void addField(Field field) {
        mFields.add(field);
    }
}
