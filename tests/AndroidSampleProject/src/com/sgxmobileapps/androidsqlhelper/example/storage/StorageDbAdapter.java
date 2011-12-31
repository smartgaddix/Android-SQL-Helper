
package com.sgxmobileapps.androidsqlhelper.example.storage;

import java.util.Date;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * This class contains utility methods and classes for the application datastore.
 * 
 * @author smartgaddix
 * 
 */
public class StorageDbAdapter {

    /**
     * Database open/upgrade helper
     * 
     */
    private StorageDbAdapter.DbHelper mDbHelper;
    /**
     * The database instance
     * 
     */
    private SQLiteDatabase mDb;
    private final static String SQL_APPUSER_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+ StorageDbMetadata.AppUser.APPUSER_TABLE_NAME +" ("+
    StorageDbMetadata.AppUser.APPUSER_ID_COL +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
    StorageDbMetadata.AppUser.APPUSER_USERNAME_COL +" TEXT NOT NULL UNIQUE, "+
    StorageDbMetadata.AppUser.APPUSER_FIRSTNAME_COL +" TEXT, "+
    StorageDbMetadata.AppUser.APPUSER_SURNAME_COL +" TEXT, "+
    StorageDbMetadata.AppUser.APPUSER_CREATIONDATE_COL +" INTEGER, "+
    StorageDbMetadata.AppUser.APPUSER_PROFILEID_COL +" TEXT NOT NULL UNIQUE"+" );";
    private final static String SQL_APPUSER_DROP_TABLE = "DROP TABLE IF EXISTS "+ StorageDbMetadata.AppUser.APPUSER_TABLE_NAME +";";
    private final static String SQL_APPUSERPROFILE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+ StorageDbMetadata.AppUserProfile.APPUSERPROFILE_TABLE_NAME +" ("+
    StorageDbMetadata.AppUserProfile.APPUSERPROFILE_PROFILEID_COL +" TEXT NOT NULL, "+
    StorageDbMetadata.AppUserProfile.APPUSERPROFILE_AUTOCONNECT_COL +" INTEGER, "+
    StorageDbMetadata.AppUserProfile.APPUSERPROFILE_LASTLOGIN_COL +" INTEGER"+", "+
    "PRIMARY KEY ("+ StorageDbMetadata.AppUserProfile.APPUSERPROFILE_PROFILEID_COL +")"+" );";
    private final static String SQL_APPUSERPROFILE_DROP_TABLE = "DROP TABLE IF EXISTS "+ StorageDbMetadata.AppUserProfile.APPUSERPROFILE_TABLE_NAME +";";

    /**
     * Initializes the data store
     * 
     * @param context
     *     the context
     */
    public StorageDbAdapter(Context context) {
        mDbHelper = new StorageDbAdapter.DbHelper(context, StorageDbMetadata.DATABASE_NAME, null, StorageDbMetadata.DATABASE_VERSION, this);
    }

    /**
     * Opens a connection with the underlying data base
     * 
     * @return
     *     the Datastore instance
     * @throws SQLException
     *     if error occurred
     */
    public StorageDbAdapter open()
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

    private ContentValues getContentValues(com.sgxmobileapps.androidsqlhelper.example.entities.AppUser appUser) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StorageDbMetadata.AppUser.APPUSER_USERNAME_COL, appUser.getUsername());
        contentValues.put(StorageDbMetadata.AppUser.APPUSER_FIRSTNAME_COL, appUser.getFirstName());
        contentValues.put(StorageDbMetadata.AppUser.APPUSER_SURNAME_COL, appUser.getSurname());
        contentValues.put(StorageDbMetadata.AppUser.APPUSER_CREATIONDATE_COL, appUser.getCreationDate().getTime());
        contentValues.put(StorageDbMetadata.AppUser.APPUSER_PROFILEID_COL, appUser.getProfileId());
        return contentValues;
    }

    private com.sgxmobileapps.androidsqlhelper.example.entities.AppUser fillFromCursorAppUser(Cursor cursor) {
        com.sgxmobileapps.androidsqlhelper.example.entities.AppUser appUser = new com.sgxmobileapps.androidsqlhelper.example.entities.AppUser();
        appUser.setUsername(cursor.getString(StorageDbMetadata.AppUser.APPUSER_USERNAME_IDX));
        appUser.setFirstName(cursor.getString(StorageDbMetadata.AppUser.APPUSER_FIRSTNAME_IDX));
        appUser.setSurname(cursor.getString(StorageDbMetadata.AppUser.APPUSER_SURNAME_IDX));
        Date creationDate = new Date();
        creationDate.setTime(cursor.getLong(StorageDbMetadata.AppUser.APPUSER_CREATIONDATE_IDX));
        appUser.setCreationDate(creationDate);
        appUser.setProfileId(cursor.getString(StorageDbMetadata.AppUser.APPUSER_PROFILEID_IDX));
        return appUser;
    }

    /**
     * Inserts a AppUser to database
     * 
     * @param appUser
     *     The AppUser to insert
     * @return
     *     the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long addAppUser(com.sgxmobileapps.androidsqlhelper.example.entities.AppUser appUser) {
        return mDb.insert(StorageDbMetadata.AppUser.APPUSER_TABLE_NAME, null, getContentValues(appUser));
    }

    /**
     * Updates a AppUser in database
     * 
     * @param id
     *     The id of the AppUser to update
     * @param appUser
     *     The AppUser to update
     * @return
     *     the number of rows affected (1 or 0)
     */
    public long updateAppUser(long id, com.sgxmobileapps.androidsqlhelper.example.entities.AppUser appUser) {
        String where = StorageDbMetadata.AppUser.APPUSER_ID_COL +" = "+ id;
        return mDb.update(StorageDbMetadata.AppUser.APPUSER_TABLE_NAME, getContentValues(appUser), where, null);
    }

    /**
     * Deletes a AppUser from database
     * 
     * @param id
     *     The id of the AppUser to delete
     * @return
     *     the number of rows affected (1 or 0)
     */
    public long deleteAppUser(long id) {
        String where = StorageDbMetadata.AppUser.APPUSER_ID_COL +" = "+ id;
        return mDb.delete(StorageDbMetadata.AppUser.APPUSER_TABLE_NAME, where, null);
    }

    /**
     * Deletes all AppUser from database
     * 
     * @return
     *     the number of rows deleted
     */
    public long deleteAllAppUser() {
        return mDb.delete(StorageDbMetadata.AppUser.APPUSER_TABLE_NAME, "1", null);
    }

    /**
     * Reads a AppUser from database with given id
     * 
     * @param id
     *     The id of the AppUser to read
     * @return
     *     the entity read or null if one entity with the specified id doesn't exist
     */
    public com.sgxmobileapps.androidsqlhelper.example.entities.AppUser getAppUser(long id) {
        String where = StorageDbMetadata.AppUser.APPUSER_ID_COL +" = "+ id;
        String[] cols = new String[] {StorageDbMetadata.AppUser.APPUSER_ID_COL, StorageDbMetadata.AppUser.APPUSER_USERNAME_COL, StorageDbMetadata.AppUser.APPUSER_FIRSTNAME_COL, StorageDbMetadata.AppUser.APPUSER_SURNAME_COL, StorageDbMetadata.AppUser.APPUSER_CREATIONDATE_COL, StorageDbMetadata.AppUser.APPUSER_PROFILEID_COL };
        Cursor cursor;
        cursor = mDb.query(StorageDbMetadata.AppUser.APPUSER_TABLE_NAME, cols, where, null, null, null, StorageDbMetadata.AppUser.APPUSER_DEFAULT_ORDER);
        if (cursor == null) {
            return null;
        }
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        com.sgxmobileapps.androidsqlhelper.example.entities.AppUser res;
        res = fillFromCursorAppUser(cursor);
        cursor.close();
        return res;
    }

    /**
     * Returns a cursor for AppUser
     * 
     * @return
     *     the cursor
     */
    public Cursor getCursorAppUser() {
        String[] cols = new String[] {StorageDbMetadata.AppUser.APPUSER_ID_COL, StorageDbMetadata.AppUser.APPUSER_USERNAME_COL, StorageDbMetadata.AppUser.APPUSER_FIRSTNAME_COL, StorageDbMetadata.AppUser.APPUSER_SURNAME_COL, StorageDbMetadata.AppUser.APPUSER_CREATIONDATE_COL, StorageDbMetadata.AppUser.APPUSER_PROFILEID_COL };
        return mDb.query(StorageDbMetadata.AppUser.APPUSER_TABLE_NAME, cols, null, null, null, null, StorageDbMetadata.AppUser.APPUSER_DEFAULT_ORDER);
    }

    /**
     * Returns the array of AppUser fetched from database
     * 
     * @return
     *     the array of AppUser
     */
    public com.sgxmobileapps.androidsqlhelper.example.entities.AppUser[] getAllAppUser() {
        Cursor cursor = getCursorAppUser();
        if (cursor == null) {
            return new com.sgxmobileapps.androidsqlhelper.example.entities.AppUser[ 0 ] ;
        }
        com.sgxmobileapps.androidsqlhelper.example.entities.AppUser[] entities = new com.sgxmobileapps.androidsqlhelper.example.entities.AppUser[cursor.getCount()] ;
        if (cursor.moveToFirst()) {
            do {
                entities[cursor.getPosition()] = fillFromCursorAppUser(cursor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return entities;
    }

    private ContentValues getContentValues(com.sgxmobileapps.androidsqlhelper.example.entities.AppUserProfile appUserProfile) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StorageDbMetadata.AppUserProfile.APPUSERPROFILE_PROFILEID_COL, appUserProfile.getProfileId());
        contentValues.put(StorageDbMetadata.AppUserProfile.APPUSERPROFILE_AUTOCONNECT_COL, appUserProfile.isAutoConnect());
        contentValues.put(StorageDbMetadata.AppUserProfile.APPUSERPROFILE_LASTLOGIN_COL, appUserProfile.getLastLogin().getTime());
        return contentValues;
    }

    private com.sgxmobileapps.androidsqlhelper.example.entities.AppUserProfile fillFromCursorAppUserProfile(Cursor cursor) {
        com.sgxmobileapps.androidsqlhelper.example.entities.AppUserProfile appUserProfile = new com.sgxmobileapps.androidsqlhelper.example.entities.AppUserProfile();
        appUserProfile.setProfileId(cursor.getString(StorageDbMetadata.AppUserProfile.APPUSERPROFILE_PROFILEID_IDX));
        appUserProfile.setAutoConnect((cursor.getLong(StorageDbMetadata.AppUserProfile.APPUSERPROFILE_AUTOCONNECT_IDX)> 0));
        Date lastLogin = new Date();
        lastLogin.setTime(cursor.getLong(StorageDbMetadata.AppUserProfile.APPUSERPROFILE_LASTLOGIN_IDX));
        appUserProfile.setLastLogin(lastLogin);
        return appUserProfile;
    }

    /**
     * Inserts a AppUserProfile to database
     * 
     * @param appUserProfile
     *     The AppUserProfile to insert
     * @return
     *     the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long addAppUserProfile(com.sgxmobileapps.androidsqlhelper.example.entities.AppUserProfile appUserProfile) {
        return mDb.insert(StorageDbMetadata.AppUserProfile.APPUSERPROFILE_TABLE_NAME, null, getContentValues(appUserProfile));
    }

    /**
     * Updates a AppUserProfile in database
     * 
     * @param appUserProfile
     *     The AppUserProfile to update
     * @return
     *     the number of rows affected (1 or 0)
     */
    public long updateAppUserProfile(com.sgxmobileapps.androidsqlhelper.example.entities.AppUserProfile appUserProfile) {
        String where = StorageDbMetadata.AppUserProfile.APPUSERPROFILE_PROFILEID_COL +" = '"+ appUserProfile.getProfileId()+"'";
        return mDb.update(StorageDbMetadata.AppUserProfile.APPUSERPROFILE_TABLE_NAME, getContentValues(appUserProfile), where, null);
    }

    /**
     * Deletes a AppUserProfile from database
     * 
     * @param appUserProfile
     *     The AppUserProfile to delete
     * @return
     *     the number of rows affected (1 or 0)
     */
    public long deleteAppUserProfile(com.sgxmobileapps.androidsqlhelper.example.entities.AppUserProfile appUserProfile) {
        String where = StorageDbMetadata.AppUserProfile.APPUSERPROFILE_PROFILEID_COL +" = '"+ appUserProfile.getProfileId()+"'";
        return mDb.delete(StorageDbMetadata.AppUserProfile.APPUSERPROFILE_TABLE_NAME, where, null);
    }

    /**
     * Deletes all AppUserProfile from database
     * 
     * @return
     *     the number of rows deleted
     */
    public long deleteAllAppUserProfile() {
        return mDb.delete(StorageDbMetadata.AppUserProfile.APPUSERPROFILE_TABLE_NAME, "1", null);
    }

    /**
     * Reads a AppUserProfile from database with given key fields
     * 
     * @param profileId
     *     The ProfileId field
     * @return
     *     the entity read or null if one entity with the specified key doesn't exist
     */
    public com.sgxmobileapps.androidsqlhelper.example.entities.AppUserProfile getAppUserProfile(String profileId) {
        String where = StorageDbMetadata.AppUserProfile.APPUSERPROFILE_PROFILEID_COL +" = '"+ profileId +"'";
        String[] cols = new String[] {StorageDbMetadata.AppUserProfile.APPUSERPROFILE_PROFILEID_COL, StorageDbMetadata.AppUserProfile.APPUSERPROFILE_AUTOCONNECT_COL, StorageDbMetadata.AppUserProfile.APPUSERPROFILE_LASTLOGIN_COL };
        Cursor cursor;
        cursor = mDb.query(StorageDbMetadata.AppUserProfile.APPUSERPROFILE_TABLE_NAME, cols, where, null, null, null, StorageDbMetadata.AppUserProfile.APPUSERPROFILE_DEFAULT_ORDER);
        if (cursor == null) {
            return null;
        }
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        com.sgxmobileapps.androidsqlhelper.example.entities.AppUserProfile res;
        res = fillFromCursorAppUserProfile(cursor);
        cursor.close();
        return res;
    }

    /**
     * Returns a cursor for AppUserProfile
     * 
     * @return
     *     the cursor
     */
    public Cursor getCursorAppUserProfile() {
        String[] cols = new String[] {StorageDbMetadata.AppUserProfile.APPUSERPROFILE_PROFILEID_COL, StorageDbMetadata.AppUserProfile.APPUSERPROFILE_AUTOCONNECT_COL, StorageDbMetadata.AppUserProfile.APPUSERPROFILE_LASTLOGIN_COL };
        return mDb.query(StorageDbMetadata.AppUserProfile.APPUSERPROFILE_TABLE_NAME, cols, null, null, null, null, StorageDbMetadata.AppUserProfile.APPUSERPROFILE_DEFAULT_ORDER);
    }

    /**
     * Returns the array of AppUserProfile fetched from database
     * 
     * @return
     *     the array of AppUserProfile
     */
    public com.sgxmobileapps.androidsqlhelper.example.entities.AppUserProfile[] getAllAppUserProfile() {
        Cursor cursor = getCursorAppUserProfile();
        if (cursor == null) {
            return new com.sgxmobileapps.androidsqlhelper.example.entities.AppUserProfile[ 0 ] ;
        }
        com.sgxmobileapps.androidsqlhelper.example.entities.AppUserProfile[] entities = new com.sgxmobileapps.androidsqlhelper.example.entities.AppUserProfile[cursor.getCount()] ;
        if (cursor.moveToFirst()) {
            do {
                entities[cursor.getPosition()] = fillFromCursorAppUserProfile(cursor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return entities;
    }

    private static class DbHelper
        extends SQLiteOpenHelper
    {

        private StorageDbAdapter mDatastore;

        public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, StorageDbAdapter datastore) {
            super(context, name, factory, version);
            mDatastore = datastore;
        }

        /**
         * Called when no database exists in disk and the helper class needs to create a new one
         * 
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_APPUSER_CREATE_TABLE);
            db.execSQL(SQL_APPUSERPROFILE_CREATE_TABLE);
        }

        /**
         * Called when there is a database version mismatch meaning that the version of the database on disk needs to be upgraded to the current version
         * 
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("StorageDbAdapter", "Upgrading from version "+ oldVersion +" to "+ newVersion +", which will destroy all old data");
            if (mDatastore.onDatastoreUpgrade(db, oldVersion, newVersion)) {
                return ;
            }
            // Upgrade the existing database to conform to the new version. Multiple previous versions can be handled by comparing _oldVersion and _newVersion values
            db.execSQL(SQL_APPUSER_DROP_TABLE);
            db.execSQL(SQL_APPUSERPROFILE_DROP_TABLE);
            // The simplest case is to drop the old table and create a new one.
            // Create a new one.
            onCreate(db);
        }

    }

}
