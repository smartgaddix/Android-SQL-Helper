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
package com.sgxmobileapps.androidsqlhelper.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sgxmobileapps.androidsqlhelper.example.entities.AppUser;
import com.sgxmobileapps.androidsqlhelper.example.entities.AppUserProfile;
import com.sgxmobileapps.androidsqlhelper.example.storage.StorageDbAdapter;

import java.util.Date;

public class AndroidSampleProjectActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button storeButton = (Button) findViewById(R.id.button_store);
        storeButton.setOnClickListener(this);
        
        Button usersButton = (Button) findViewById(R.id.button_view_users);
        usersButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                UserListActivity.launch(AndroidSampleProjectActivity.this);
            }
        });
        
        Button profilesButton = (Button) findViewById(R.id.button_view_profiles);
        profilesButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                UserProfileListActivity.launch(AndroidSampleProjectActivity.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        StorageDbAdapter db = new StorageDbAdapter(this);
        db.open();
        
        AppUser user = new AppUser();
        AppUserProfile userProfile = new AppUserProfile();
        for (int i = 0; i < 100; i++){
            
            long id = getNewId();
            user.setUsername("user" + id);
            user.setFirstName("name" + id);
            user.setSurname("surname" + id);
            user.setCreationDate(new Date());
            user.setProfileId("profile" + id);
            
            userProfile.setLastLogin(new Date());
            userProfile.setProfileId("profile" + id);
            userProfile.setAutoConnect(true);
            
            db.addAppUser(user);
            db.addAppUserProfile(userProfile);
        }

        db.close();
    }
    
    static long currentId = System.currentTimeMillis();
    static private long getNewId(){
        return currentId++;
    }
    
    
}