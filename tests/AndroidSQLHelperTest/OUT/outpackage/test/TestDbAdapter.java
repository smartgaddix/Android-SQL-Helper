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
package outpackage.test;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * This class contains utility methods and classes for the application datastore.
 * 
 * @author smartgaddix
 * 
 */
public abstract class TestDbAdapter {

    /**
     * Database open/upgrade helper
     * 
     */
    private TestDbAdapter.DbHelper mDbHelper;
    /**
     * The database instance
     * 
     */
    private SQLiteDatabase mDb;
    private final static String SQL_SIMPLEENTITY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+ outpackage.test.TestDbMetadata.SimpleEntity.SIMPLEENTITY_TABLE_NAME +" ("+
    outpackage.test.TestDbMetadata.SimpleEntity.SIMPLEENTITY_ID_COL +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
    outpackage.test.TestDbMetadata.SimpleEntity.SIMPLEENTITY_FIELDSTRING_COL +" TEXT, "+
    outpackage.test.TestDbMetadata.SimpleEntity.SIMPLEENTITY_FIELDLONG_COL +" INTEGER"+" );";
    private final static String SQL_SIMPLEENTITY_DROP_TABLE = "DROP TABLE IF EXISTS "+ outpackage.test.TestDbMetadata.SimpleEntity.SIMPLEENTITY_TABLE_NAME +";";
    private final static String SQL_SIMPLEENTITY2_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+ outpackage.test.TestDbMetadata.SimpleEntity2 .SIMPLEENTITY2_TABLE_NAME +" ("+
    outpackage.test.TestDbMetadata.SimpleEntity2 .SIMPLEENTITY2_ID_COL +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
    outpackage.test.TestDbMetadata.SimpleEntity2 .SIMPLEENTITY2_FIELDSTRING_COL +" TEXT, "+
    outpackage.test.TestDbMetadata.SimpleEntity2 .SIMPLEENTITY2_FIELDLONG_COL +" INTEGER, "+
    outpackage.test.TestDbMetadata.SimpleEntity2 .SIMPLEENTITY2_FIELDDATE_COL +" INTEGER"+" );";
    private final static String SQL_SIMPLEENTITY2_DROP_TABLE = "DROP TABLE IF EXISTS "+ outpackage.test.TestDbMetadata.SimpleEntity2 .SIMPLEENTITY2_TABLE_NAME +";";

    /**
     * Initializes the data store
     * 
     * @param context
     *     the context
     */
    public TestDbAdapter(Context context) {
        mDbHelper = new TestDbAdapter.DbHelper(context, DATABASE_NAME, null, DATABASE_VERSION, this);
        return this;
    }

    /**
     * Opens a connection with the underlying data base
     * 
     * @return
     *     the Datastore instance
     * @throws SQLException
     *     if error occurred
     */
    public TestDbAdapter open()
        throws SQLException
    {
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    /**
     * Closes the connection with the underlying data base
     * 
     */
    public void close() {
        if (mDb!= null) {
            mDb.close();
        }
    }

    /**
     * Called from Datastore during the upgrading phase of the data base schema. Derived classes can override this method if they need to do some work during the upgrade process
     * 
     * @param db
     *     the SQLiteDatabase
     * @param newVersion
     *     schema's new version
     * @param oldVersion
     *     schema's old version
     * @return
     *     if the method return false the old schema will be destroyed and the new schema will be created. If return true the upgrade process terminates
     */
    @Override
    public Boolean onDatastoreUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return false;
    }

    private static class DbHelper
        extends SQLiteOpenHelper
    {

        private TestDbAdapter mDatastore;

        public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, TestDbAdapter datastore) {
            super(context, name, factory, version);
            mDatastore = datastore;
        }

        /**
         * Called when no database exists in disk and the helper class needs to create a new one
         * 
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            execSQL(SQL_SIMPLEENTITY_CREATE_TABLE);
            execSQL(SQL_SIMPLEENTITY2_CREATE_TABLE);
        }

        /**
         * Called when there is a database version mismatch meaning that the version of the database on disk needs to be upgraded to the current version
         * 
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (mDatastore.onDatastoreUpgrade(db, oldVersion, newVersion)) {
                return ;
            }
            // Upgrade the existing database to conform to the new version. Multiple previous versions can be handled by comparing _oldVersion and _newVersion values
            // The simplest case is to drop the old table and create a new one.
            execSQL(SQL_SIMPLEENTITY_DROP_TABLE);
            execSQL(SQL_SIMPLEENTITY2_DROP_TABLE);
            // Create a new one.
            onCreate(db);
        }

    }

}
