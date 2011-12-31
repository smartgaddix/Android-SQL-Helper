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

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

import com.sgxmobileapps.androidsqlhelper.example.storage.StorageDbAdapter;
import com.sgxmobileapps.androidsqlhelper.example.storage.StorageDbMetadata;

/**
 * @author Massimo Gaddini
 * 
 */
public class UserListActivity extends ListActivity {
    
    StorageDbAdapter mDbAdapter = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
        mDbAdapter = new StorageDbAdapter(this);
    }
    
    static public void launch(Context launcher) {
        Intent i = new Intent(launcher, UserListActivity.class);
        launcher.startActivity(i);
    }

    /* 
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        mDbAdapter.open();
        
        Cursor cursor = mDbAdapter.getCursorAppUser();
        startManagingCursor(cursor);

        ListAdapter adapter = new SimpleCursorAdapter(
                this, 
                android.R.layout.two_line_list_item,  
                cursor,
                new String[] {StorageDbMetadata.AppUser.APPUSER_FIRSTNAME_COL, StorageDbMetadata.AppUser.APPUSER_SURNAME_COL},
                new int[] {android.R.id.text1, android.R.id.text2});  

        setListAdapter(adapter);
    }

    /* 
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
        mDbAdapter.close();
    }
}
