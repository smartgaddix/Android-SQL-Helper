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
package com.sgxmobileapps.androidsqlhelper.example.entities;

import com.sgxmobileapps.androidsqlhelper.annotation.PersistentEntity;
import com.sgxmobileapps.androidsqlhelper.annotation.PersistentField;

import java.util.Date;


/**
 * @author Massimo Gaddini
 */
@PersistentEntity(
        tableName="USER",
        fieldPrefix="m",
        orderBy="USERNAME ASC")
public class AppUser {
    
    @PersistentField(nullable=false,unique=true)
    private String mUsername;
    
    @PersistentField()
    private String mFirstName;
    
    @PersistentField()
    private String mSurname;
    
    @PersistentField()
    private Date mCreationDate;
    
    @PersistentField(nullable=false,unique=true)
    private String mProfileId;

    
    /**
     * @return the username
     */
    public String getUsername() {
        return mUsername;
    }

    
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        mUsername = username;
    }

    
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return mFirstName;
    }

    
    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    
    /**
     * @return the surname
     */
    public String getSurname() {
        return mSurname;
    }

    
    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        mSurname = surname;
    }

    
    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return mCreationDate;
    }

    
    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        mCreationDate = creationDate;
    }


    
    /**
     * @return the profileId
     */
    public String getProfileId() {
        return mProfileId;
    }


    
    /**
     * @param profileId the profileId to set
     */
    public void setProfileId(String profileId) {
        mProfileId = profileId;
    }
}
