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
 */
@PersistentEntity(
        tableName="USERPROFILE",
        fieldPrefix="m", 
        noIdCol=true,
        pk={"ProfileId"})
public class AppUserProfile {
    
    @PersistentField()
    private String mProfileId;
    
    @PersistentField()
    private boolean mAutoConnect;
    
    @PersistentField()
    private Date mLastLogin;

    
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

    
    /**
     * @return the autoConnect
     */
    public boolean isAutoConnect() {
        return mAutoConnect;
    }

    
    /**
     * @param autoConnect the autoConnect to set
     */
    public void setAutoConnect(boolean autoConnect) {
        mAutoConnect = autoConnect;
    }

    
    /**
     * @return the lastLogin
     */
    public Date getLastLogin() {
        return mLastLogin;
    }

    
    /**
     * @param lastLogin the lastLogin to set
     */
    public void setLastLogin(Date lastLogin) {
        mLastLogin = lastLogin;
    }
    
}
