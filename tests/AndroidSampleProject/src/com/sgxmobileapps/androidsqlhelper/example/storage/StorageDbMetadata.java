
package com.sgxmobileapps.androidsqlhelper.example.storage;



/**
 * This class defines some metadata costants used by datastore. Contains table names and column names.
 * 
 * @author smartgaddix
 * 
 */
public class StorageDbMetadata {

    public final static String DATABASE_NAME = "Test.db";
    public final static int DATABASE_VERSION = 1;

    public final static class AppUser {

        public final static String APPUSER_TABLE_NAME = "USER";
        public final static String APPUSER_DEFAULT_ORDER = "USERNAME ASC";
        public final static String APPUSER_ID_COL = "_id";
        protected final static int APPUSER_ID_IDX = 0;
        public final static String APPUSER_USERNAME_COL = "USERNAME";
        protected final static int APPUSER_USERNAME_IDX = 1;
        public final static String APPUSER_FIRSTNAME_COL = "FIRSTNAME";
        protected final static int APPUSER_FIRSTNAME_IDX = 2;
        public final static String APPUSER_SURNAME_COL = "SURNAME";
        protected final static int APPUSER_SURNAME_IDX = 3;
        public final static String APPUSER_CREATIONDATE_COL = "CREATIONDATE";
        protected final static int APPUSER_CREATIONDATE_IDX = 4;
        public final static String APPUSER_PROFILEID_COL = "PROFILEID";
        protected final static int APPUSER_PROFILEID_IDX = 5;

    }

    public final static class AppUserProfile {

        public final static String APPUSERPROFILE_TABLE_NAME = "USERPROFILE";
        public final static String APPUSERPROFILE_DEFAULT_ORDER = null;
        public final static String APPUSERPROFILE_PROFILEID_COL = "PROFILEID";
        protected final static int APPUSERPROFILE_PROFILEID_IDX = 0;
        public final static String APPUSERPROFILE_AUTOCONNECT_COL = "AUTOCONNECT";
        protected final static int APPUSERPROFILE_AUTOCONNECT_IDX = 1;
        public final static String APPUSERPROFILE_LASTLOGIN_COL = "LASTLOGIN";
        protected final static int APPUSERPROFILE_LASTLOGIN_IDX = 2;

    }

}
