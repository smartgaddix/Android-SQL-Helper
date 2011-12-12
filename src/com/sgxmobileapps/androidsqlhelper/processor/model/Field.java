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
    protected boolean mUnique;
    protected boolean mNullable;
    protected String mColumnType;
    protected Table mTable;
    protected String mCustomColumnDefinition;
    protected int mIndex;
    protected String mGetterMethod;
    protected String mSetterMethod;
    protected String mGetterConvMethod;
    protected String mSetterConvMethod;
    protected String mCursorGetterMethod;
    protected Class<?> mClazz;
    protected Class<?> mClazzCast;
    
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
        
        field.mUnique = annotation.unique();
        
        List<String> unique = Arrays.asList(table.getUniqueConstraint());
        List<String> pk = Arrays.asList(table.getPKConstraint());
        field.mNullable = !unique.contains(field.mFieldName) && 
                          !pk.contains(field.mFieldName) && 
                          !field.mUnique &&
                          annotation.nullable();

        field.mCustomColumnDefinition = annotation.customColumnDefinition();
        
        field.mTable = table;
         
        String fieldNameForGetter = field.getFieldNameForMethod();
        field.mGetterMethod = "get" + fieldNameForGetter;
        field.mSetterMethod = "set" + fieldNameForGetter;
        field.mGetterConvMethod = null;
        field.mSetterConvMethod = null; 
        
        switch(member.asType().getKind()) {
        case BOOLEAN:
            field.mGetterMethod = "is" + fieldNameForGetter;
            field.mColumnType = "INTEGER";
            field.mCursorGetterMethod = "getLong";
            field.mClazz = boolean.class;
            field.mClazzCast = boolean.class;
            break;
        case BYTE:
            field.mColumnType = "INTEGER";
            field.mCursorGetterMethod = "getLong";
            field.mClazz = byte.class;
            field.mClazzCast = byte.class;
            break;
        case LONG:
            field.mColumnType = "INTEGER";
            field.mCursorGetterMethod = "getLong";
            field.mClazz = long.class;
            field.mClazzCast = null;
            break;
        case INT:
            field.mColumnType = "INTEGER";
            field.mCursorGetterMethod = "getLong";
            field.mClazz = int.class;
            field.mClazzCast = int.class;
            break;
        case SHORT:
            field.mColumnType = "INTEGER";
            field.mCursorGetterMethod = "getLong";
            field.mClazz = short.class;
            field.mClazzCast = short.class;
            break;
        case FLOAT:
            field.mColumnType = "REAL";
            field.mCursorGetterMethod = "getDouble";
            field.mClazz = float.class;
            field.mClazzCast = float.class;
            break;
        case DOUBLE:
            field.mColumnType = "REAL";
            field.mCursorGetterMethod = "getDouble";
            field.mClazz = double.class;
            field.mClazzCast = null;
            break;
            
        case DECLARED:
            // TODO
            //change declared type management
         
            String javaClass =  ((DeclaredType)member.asType()).asElement().toString();
            
            try {
                field.mClazz = Class.forName(javaClass);
            } catch (ClassNotFoundException e) {
                throw new UnsupportedFieldTypeException("Declared type " + javaClass + " not found", e);
            }
            
            if (javaClass.equals("java.lang.String")) {
                
                field.mColumnType = "TEXT";
                field.mCursorGetterMethod = "getString";
                field.mClazzCast = null;
            } else if ( javaClass.equals("java.lang.CharSequence") ) {
                
                field.mColumnType = "TEXT";
                field.mGetterConvMethod = "toString";
                field.mCursorGetterMethod = "getString";
                field.mClazzCast = null;
            } else if (javaClass.equals("java.lang.Boolean")) {
                
                field.mColumnType = "INTEGER";
                field.mGetterMethod = "is" + fieldNameForGetter;
                field.mCursorGetterMethod = "getLong";
                field.mClazzCast = boolean.class;
            } else if (javaClass.equals("java.lang.Byte")) {
                
                field.mColumnType = "INTEGER";
                field.mCursorGetterMethod = "getLong";
                field.mClazzCast = byte.class; 
            } else if (javaClass.equals("java.lang.Long")) {
                
                field.mColumnType = "INTEGER";
                field.mCursorGetterMethod = "getLong";
                field.mClazzCast = null;
            } else if (javaClass.equals("java.lang.Integer")) {
                
                field.mColumnType = "INTEGER";
                field.mCursorGetterMethod = "getLong";
                field.mClazzCast = int.class;
            } else if (javaClass.equals("java.lang.Short")) {
                
                field.mColumnType = "INTEGER";
                field.mCursorGetterMethod = "getLong";
                field.mClazzCast = short.class;
            } else if (javaClass.equals("java.lang.Double")) {
                
                field.mColumnType = "REAL";
                field.mCursorGetterMethod = "getDouble";
                field.mClazzCast = null;
            } else if (javaClass.equals("java.lang.Float")) {
                
                field.mColumnType = "REAL";
                field.mCursorGetterMethod = "getDouble";
                field.mClazzCast = float.class;
            } else if (javaClass.equals("java.util.Date")) {
                
                /* FixMe */
                field.mColumnType = "INTEGER";
                field.mGetterConvMethod = "getTime";
                field.mSetterConvMethod = "setTime";
                field.mCursorGetterMethod = "getLong";
                field.mClazzCast = null;
            } else if ( javaClass.equals("java.util.GregorianCalendar") ) {
                
                /* FixMe */
                field.mColumnType = "INTEGER";
                field.mGetterConvMethod = "getTimeInMillis";
                field.mSetterConvMethod = "setTimeInMillis";
                field.mCursorGetterMethod = "getLong";
                field.mClazzCast = null;
            } else {
                throw new UnsupportedFieldTypeException("Declared type " + javaClass + " unsupported");
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
        field.mUnique = false;
        field.mNullable = false;
        field.mTable = table;
        field.mColumnType = "INTEGER";
        field.mCustomColumnDefinition = "INTEGER PRIMARY KEY AUTOINCREMENT";
        field.mGetterMethod = "get" + field.getFieldNameForMethod();
        field.mSetterMethod = "set" + field.getFieldNameForMethod();
        field.mCursorGetterMethod = "getLong";
        field.mGetterConvMethod = null;
        field.mSetterConvMethod = null;
        field.mClazz = long.class;
        field.mClazzCast = null;
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
        
        if (mUnique) {
            definition += " UNIQUE";
        }
        
        return definition;
    }
    
    /**
     * Returns the field name to use in method name as getter and setter
     * @return the field name
     */
    public String getFieldNameForMethod(){
        return mFieldName.substring(0,1).toUpperCase() + mFieldName.substring(1);
    }
    
    /**
     * Returns the field name to use in variable name
     * @return the field name
     */
    public String getFieldNameForVar(){
        return mFieldName.substring(0,1).toLowerCase() + mFieldName.substring(1);
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
     * @return the setterMethod
     */
    public String getSetterMethod() {
        return mSetterMethod;
    }

    /**
     * @return the getterConvMethod
     */
    public String getGetterConvMethod() {
        return mGetterConvMethod;
    }
      
    /**
     * @return the setterConvMethod
     */
    public String getSetterConvMethod() {
        return mSetterConvMethod;
    }
  
    /**
     * @return the cursorGetterMethod
     */
    public String getCursorGetterMethod() {
        return mCursorGetterMethod;
    }
    
    /**
     * @return the clazz
     */
    public Class<?> getClazz() {
        return mClazz;
    }
    
    /**
     * @return the clazzCast
     */
    public Class<?> getClazzCast() {
        return mClazzCast;
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
        builder.append(", mUnique=");
        builder.append(mUnique);
        builder.append(", mNullable=");
        builder.append(mNullable);
        builder.append(", mIdField=");
        builder.append(mIdField);
        builder.append(", mGetterMethod=");
        builder.append(mGetterMethod);
        builder.append(", mSetterMethod=");
        builder.append(mSetterMethod);
        builder.append(", mGetterConvMethod=");
        builder.append(mGetterConvMethod);
        builder.append(", mSetterConvMethod=");
        builder.append(mSetterConvMethod);
        builder.append(", mCursorGetterMethod=");
        builder.append(mCursorGetterMethod);
        builder.append(", mClazz=");
        builder.append(mClazz);
        builder.append(", mClazzCast=");
        builder.append(mClazzCast);
        builder.append("]");
        return builder.toString();
    }

 }
