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

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;


/**
 * The Field class contains the field information used by the annotation
 * processor for the generation of SQL helper code.
 * 
 * @author Massimo Gaddini
 * Jun 4, 2011
 */
public class Field {
    protected String mColumnName;
    protected String mColumnType;
    protected boolean mKey;
    protected boolean mNullable;
    
    /**
     * Builds a Field instance from an PersistentField annotation and
     * the annotated member Element 
     * @param annotation the annotation
     * @param member the member
     * @return new Field instance
     * @throws UnsupportedFieldTypeException if the field's type is unsupported
     */
    public static Field buildField(PersistentField annotation, Element member) throws UnsupportedFieldTypeException{
        Field field = new Field();
        
        if (annotation.columnName().isEmpty())
            field.mColumnName = member.getSimpleName().toString().toUpperCase();
        else
            field.mColumnName = annotation.columnName().toUpperCase();
        
        field.mNullable = (annotation.key())?false:annotation.nullable();
        field.mKey = annotation.key();
        
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
                /* TODO */
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
     * @return the key
     */
    public boolean isKey() {
        return mKey;
    }
    
    /**
     * @param key the key to set
     */
    public void setKey(boolean key) {
        mKey = key;
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
}
