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

import java.util.Date;


/**
 * @author Massimo Gaddini
 *
 */
@PersistentEntity(fieldPrefix="m")
public class SimpleEntity2 {
    
    @PersistentField
    public String mFieldString;
    
    @PersistentField
    public Long mFieldLong;
    
    public Long mFieldLongNP;
    
    @PersistentField
    public Date mFieldDate;

    
    public String getFieldString() {
        return mFieldString;
    }

    
    public void setFieldString(String fieldString) {
        mFieldString = fieldString;
    }

    
    public Long getFieldLong() {
        return mFieldLong;
    }

    
    public void setFieldLong(Long fieldLong) {
        mFieldLong = fieldLong;
    }

    
    public Long getFieldLongNP() {
        return mFieldLongNP;
    }

    
    public void setFieldLongNP(Long fieldLongNP) {
        mFieldLongNP = fieldLongNP;
    }

    
    public Date getFieldDate() {
        return mFieldDate;
    }

    
    public void setFieldDate(Date fieldDate) {
        mFieldDate = fieldDate;
    }
}
