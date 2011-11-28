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
    protected boolean mIdField;
    protected String mFieldName;
    protected String mColumnName;
    protected boolean mNullable;
    protected String mColumnType;
    protected Table mTable;
    protected String mCustomColumnDefinition;
    protected int mIndex;
    protected String mGetterMethod;
    protected String mGetterConvMethod;
    protected boolean mStringfy;
    
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
        
        field.mIdField = false;
        
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
         
        String fieldNameForGetter = field.mFieldName.substring(0,1).toUpperCase() + field.mFieldName.substring(1);
        field.mGetterMethod = "get" + fieldNameForGetter;
        field.mStringfy = false;
        
        switch(member.asType().getKind()) {
        case BOOLEAN:
            field.mGetterMethod = "is" + fieldNameForGetter;
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
            field.mStringfy = true;
            field.mColumnType = "TEXT";
            break;
            
        case DECLARED:
            // TODO
            //change declared type management
         
            String declaredName =  ((DeclaredType)member.asType()).asElement().toString();
            
            if (declaredName.equals("java.lang.String")) {
                
                field.mColumnType = "TEXT";
            } else if (declaredName.equals("java.lang.Character") ||                  
                declaredName.equals("java.lang.CharSequence")) {
            
                field.mGetterConvMethod = "toString";
                field.mColumnType = "TEXT";
            } else if (declaredName.equals("java.lang.Boolean")) {
                
                field.mGetterMethod = "is" + fieldNameForGetter;
                field.mColumnType = "INTEGER";
            } else if (declaredName.equals("java.lang.Byte") || 
                    declaredName.equals("java.lang.Long") || 
                    declaredName.equals("java.lang.Integer") || 
                    declaredName.equals("java.lang.Short")) {
                
                field.mColumnType = "INTEGER";
            } else if (declaredName.equals("java.lang.Double") || 
                    declaredName.equals("java.lang.Float")) {
                
                field.mColumnType = "REAL";
            } else if (declaredName.equals("java.util.Date")) {
                
                /* FixMe */
                field.mColumnType = "INTEGER";
                field.mGetterConvMethod = "getTime";
            } else if ( declaredName.equals("java.util.GregorianCalendar") || 
                        declaredName.equals("java.util.Calendar")) {
                
                /* FixMe */
                field.mColumnType = "INTEGER";
                field.mGetterConvMethod = "getTimeInMillis";
            } else {
                throw new UnsupportedFieldTypeException("Declared type " + declaredName + " unsupported");
            }
            break;
            
        default:
            throw new UnsupportedFieldTypeException("Kind " + member.asType().getKind() + " unsupported");
        }     
        
        return field;
    }
    
    public static Field buildIdField(Table table){
        Field field = new Field();
        
        field.mIdField = true;
        field.mIndex = 0;
        field.mFieldName = "Id";
        field.mColumnName = "_id";
        field.mNullable = false;
        field.mTable = table;
        field.mColumnType = "INTEGER";
        field.mCustomColumnDefinition = "INTEGER PRIMARY KEY AUTOINCREMENT";
        field.mGetterMethod = "get" + field.mFieldName;
        
        return field;
    }
    
    /**
     * Returns the column definition using other fields
     * @return the column definition
     */
    public String getColumnDefinition() {
        if (!mCustomColumnDefinition.isEmpty())  {
            return mCustomColumnDefinition;
        }
        
        String definition = mColumnType;
        if (!mNullable) {
            definition += " NOT NULL";
        }
        
        List<String> unique = Arrays.asList(mTable.getUniqueConstraint());
        if (unique.contains(mFieldName)) {
            definition += " UNIQUE";
        }
        
        return definition;
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
     * @return the fieldName
     */
    public String getFieldName() {
        return mFieldName;
    }
    
    /**
     * @return the table
     */
    public Table getTable() {
        return mTable;
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

    
    /**
     * @return the idField
     */
    public boolean isIdField() {
        return mIdField;
    }
    
    /**
     * @return the getterMethod
     */
    public String getGetterMethod() {
        return mGetterMethod;
    }

    /**
     * @return the getterConvMethod
     */
    public String getGetterConvMethod() {
        return mGetterConvMethod;
    }
    
    /**
     * @return the stringfy
     */
    public boolean isStringfy() {
        return mStringfy;
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
        builder.append(", mIdField=");
        builder.append(mIdField);
        builder.append(", mGetterMethod=");
        builder.append(mGetterMethod);
        builder.append(", mGetterConvMethod=");
        builder.append(mGetterConvMethod);
        builder.append(", mStringfy=");
        builder.append(mStringfy);
        builder.append("]");
        return builder.toString();
    }

 }
