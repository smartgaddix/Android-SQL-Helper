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
    private final static String SQL_FULL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+ TestDbMetadata.FullEntity.FULL_TABLE_NAME +" ("+
    TestDbMetadata.FullEntity.FULL_LONGPRIMITIVE_COL +" INTEGER NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_LONG_COL +" INTEGER NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_INTPRIMITIVE_COL +" INTEGER NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_INT_COL +" INTEGER NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_BYTEPRIMITIVE_COL +" INTEGER NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_BYTE_COL +" INTEGER NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_SHORTPRIMITIVE_COL +" INTEGER NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_SHORT_COL +" INTEGER NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_CHARPRIMITIVE_COL +" TEXT NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_CHAR_COL +" TEXT NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_BOOLPRIMITIVE_COL +" INTEGER NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_BOOL_COL +" INTEGER NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_FLOATPRIMITIVE_COL +" REAL NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_FLOAT_COL +" REAL NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_DOUBLEPRIMITIVE_COL +" REAL NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_DOUBLE_COL +" REAL NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_STRING_COL +" TEXT NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_CHARSEQ_COL +" TEXT NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_DATE_COL +" INTEGER NOT NULL, "+
    TestDbMetadata.FullEntity.FULL_CALENDAR_COL +" INTEGER NOT NULL"+" );";
    private final static String SQL_FULL_DROP_TABLE = "DROP TABLE IF EXISTS "+ TestDbMetadata.FullEntity.FULL_TABLE_NAME +";";

    /**
     * Initializes the data store
     * 
     * @param context
     *     the context
     */
    public TestDbAdapter(Context context) {
        mDbHelper = new TestDbAdapter.DbHelper(context, TestDbMetadata.DATABASE_NAME, null, TestDbMetadata.DATABASE_VERSION, this);
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
            db.execSQL(SQL_FULL_CREATE_TABLE);
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
            db.execSQL(SQL_FULL_DROP_TABLE);
            // Create a new one.
            onCreate(db);
        }

    }

}
