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


/**
 * The Field class contains the filed information used by the annotation
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
