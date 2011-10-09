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

import com.sgxmobileapps.androidsqlhelper.annotation.PersistentField;

import java.util.Arrays;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;


/**
 * The Field class contains the field information used by the annotation
 * processor for the generation of SQL helper code.
 * 
 * @author Massimo Gaddini
 */
public class Field implements Visitable {
    protected String mFieldName;
    protected String mColumnName;
    protected String mColumnType;
    protected boolean mNullable;
    protected Table mTable;
    protected String mCustomColumnDefinition;
    protected int mIndex;
    
    /**
     * Builds a Field instance from an PersistentField annotation and
     * the annotated member Element 
     * @param annotation the annotation
     * @param member the member
     * @return new Field instance
     * @throws UnsupportedFieldTypeException if the field's type is unsupported
     */
    public static Field buildField(PersistentField annotation, Element member, Table table) throws UnsupportedFieldTypeException{
        Field field = new Field();
        
        String fieldName = member.getSimpleName().toString();
        if (fieldName.startsWith(table.getFieldPrefix())){
            fieldName = fieldName.substring(table.getFieldPrefix().length());
        }
        
        field.mFieldName = fieldName;
       
        if (annotation.columnName().isEmpty()) {
            field.mColumnName = fieldName.toUpperCase();
        } else {
            field.mColumnName = annotation.columnName().toUpperCase();
        }
        
        List<String> unique = Arrays.asList(table.getUniqueConstraint());
        field.mNullable = unique.contains(field.mFieldName)?false:annotation.nullable();

        field.mCustomColumnDefinition = annotation.customColumnDefinition();
        
        field.mTable = table;
                
        if (!annotation.columnType().isEmpty())
            field.mColumnType = annotation.columnType();
        else {
            switch(member.asType().getKind()) {
            case BOOLEAN:
            case BYTE:
            case LONG:
            case INT:
            case SHORT:
                field.mColumnType = "INTEGER";
                break;
            case FLOAT:
            case DOUBLE:
                field.mColumnType = "REAL";
                break;
            case CHAR:
                field.mColumnType = "TEXT";
                break;
                
            case DECLARED:
                // TODO
                //change declared type management
             
                String declaredName =  ((DeclaredType)member.asType()).asElement().toString();
                
                if (declaredName.equals("java.lang.Character") || 
                    declaredName.equals("java.lang.String") || 
                    declaredName.equals("java.lang.CharSequence")) {
                
                    field.mColumnType = "TEXT";
                } else if (declaredName.equals("java.lang.Byte") || 
                        declaredName.equals("java.lang.Boolean") || 
                        declaredName.equals("java.lang.Long") || 
                        declaredName.equals("java.lang.Integer") || 
                        declaredName.equals("java.lang.Short")) {
                    
                    field.mColumnType = "INTEGER";
                } else if (declaredName.equals("java.lang.Double") || 
                        declaredName.equals("java.lang.Float")) {
                    
                    field.mColumnType = "REAL";
                } else if (declaredName.equals("java.util.Date") || 
                        declaredName.equals("java.util.GregorianCalendar") || 
                        declaredName.equals("java.util.Calendar")) {
                    /* FixMe */
                    field.mColumnType = "INTEGER";  
                } else {
                    throw new UnsupportedFieldTypeException("Declared type " + declaredName + " unsupported");
                }
                break;
                
            default:
                throw new UnsupportedFieldTypeException("Kind " + member.asType().getKind() + " unsupported");
            }     
        }
        
        return field;
    }
    
    public static Field buildIdField(Table table){
        Field field = new Field();
        
        field.mIndex = 0;
        field.mFieldName = "id";
        field.mColumnName = "_id";
        field.mNullable = false;
        field.mTable = table;
        field.mColumnType = "INTEGER";
        field.mCustomColumnDefinition = "INTEGER PRIMARY KEY AUTOINCREMENT";
        
        return field;
    }
    
    /* 
     * @see com.sgxmobileapps.androidsqlhelper.processor.model.Visitable#accept(com.sgxmobileapps.androidsqlhelper.processor.model.GeneratorVisitor)
     */
    @Override
    public void accept(Visitor visitor) throws VisitorException {
        visitor.visit(this);
    }
    
    /**
     * @return the columnName
     */
    public String getColumnName() {
        return mColumnName;
    }
    
    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        mColumnName = columnName;
    }
    
    /**
     * @return the columnType
     */
    public String getColumnType() {
        return mColumnType;
    }
    
    /**
     * @param columnType the columnType to set
     */
    public void setColumnType(String columnType) {
        mColumnType = columnType;
    }
    
    /**
     * @return the nullable
     */
    public boolean isNullable() {
        return mNullable;
    }
    
    /**
     * @param nullable the nullable to set
     */
    public void setNullable(boolean nullable) {
        mNullable = nullable;
    }
    
    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return mFieldName;
    }
    
    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        mFieldName = fieldName;
    }
    
    /**
     * @return the table
     */
    public Table getTable() {
        return mTable;
    }
    
    /**
     * @param table the table to set
     */
    public void setTable(Table table) {
        mTable = table;
    }
    
    /**
     * @return the customColumnDefinition
     */
    public String getCustomColumnDefinition() {
        return mCustomColumnDefinition;
    }
    
    /**
     * @param customColumnDefinition the customColumnDefinition to set
     */
    public void setCustomColumnDefinition(String customColumnDefinition) {
        mCustomColumnDefinition = customColumnDefinition;
    }

    
    /**
     * @return the index
     */
    public int getIndex() {
        return mIndex;
    }

    
    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        mIndex = index;
    }

    /* 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Field [");
        if (mColumnName != null) {
            builder.append("mColumnName=");
            builder.append(mColumnName);
            builder.append(", ");
        }
        if (mColumnType != null) {
            builder.append("mColumnType=");
            builder.append(mColumnType);
            builder.append(", ");
        }
        if (mCustomColumnDefinition != null) {
            builder.append("mCustomColumnDefinition=");
            builder.append(mCustomColumnDefinition);
            builder.append(", ");
        }
        if (mFieldName != null) {
            builder.append("mFieldName=");
            builder.append(mFieldName);
            builder.append(", ");
        }
        builder.append("mIndex=");
        builder.append(mIndex);
        builder.append(", mNullable=");
        builder.append(mNullable);
        builder.append("]");
        return builder.toString();
    }

 }
