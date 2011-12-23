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
package com.sgxmobileapps.androidsqlhelper.test.entities;

import com.sgxmobileapps.androidsqlhelper.annotation.PersistentEntity;
import com.sgxmobileapps.androidsqlhelper.annotation.PersistentField;

/**
 * @author Massimo Gaddini
 *
 */
@PersistentEntity(tableName="FIRSTENTITY", fieldPrefix="m", noIdCol=true, pk={"FieldString", "FieldLong"}, unique={"FieldString", "FieldLong"})
public class TableTestEntity3 {

    @PersistentField
    public String mFieldString;

    @PersistentField
    public long mFieldLong;
    
    @PersistentField
    public boolean mFieldBool;
    
    @PersistentField
    public Boolean mFieldBoolean;

    
    public String getFieldString() {
        return mFieldString;
    }

    
    public void setFieldString(String fieldString) {
        mFieldString = fieldString;
    }

    
    public long getFieldLong() {
        return mFieldLong;
    }

    
    public void setFieldLong(long fieldLong) {
        mFieldLong = fieldLong;
    }
    
    public boolean isFieldBool() {
        return mFieldBool;
    }

    
    public void setFieldBool(boolean fieldBool) {
        mFieldBool = fieldBool;
    }
    
    public Boolean isFieldBoolean() {
        return mFieldBoolean;
    }

    
    public void setFieldBoolean(Boolean fieldBoolean) {
        mFieldBoolean = fieldBoolean;
    }
}
