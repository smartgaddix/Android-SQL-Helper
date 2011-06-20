
package outpackage;

import android.provider.BaseColumns;


/**
 * This class defines some metadata costants used by datastore. Contains table names and column names.
 * @author smartgaddix
 * 
 */
public class DbMetadata {

    final static String DATABASE_NAME = "App.db";
    final static int DATABASE_VERSION = 1;

    public final static class SIMPLEENTITY
        extends BaseColumns
    {

        public final static String SIMPLEENTITY_TABLE_NAME = "SIMPLEENTITY";
        public final static String SIMPLEENTITY_ID_COL = (BaseColumns._ID);
        protected final static int SIMPLEENTITY_ID_IDX = 0;
        public final static String SIMPLEENTITY_MFIELDLONG_COL = "MFIELDLONG";
        protected final static int SIMPLEENTITY_MFIELDLONG_IDX = 1;
        public final static String SIMPLEENTITY_MFIELDSTRING_COL = "MFIELDSTRING";
        protected final static int SIMPLEENTITY_MFIELDSTRING_IDX = 2;

    }

    public final static class SIMPLEENTITY2
        extends BaseColumns
    {

        public final static String SIMPLEENTITY2_TABLE_NAME = "SIMPLEENTITY2";
        public final static String SIMPLEENTITY2_ID_COL = (BaseColumns._ID);
        protected final static int SIMPLEENTITY2_ID_IDX = 0;
        public final static String SIMPLEENTITY2_MFIELDSTRING_COL = "MFIELDSTRING";
        protected final static int SIMPLEENTITY2_MFIELDSTRING_IDX = 1;
        public final static String SIMPLEENTITY2_MFIELDLONG_COL = "MFIELDLONG";
        protected final static int SIMPLEENTITY2_MFIELDLONG_IDX = 2;
        public final static String SIMPLEENTITY2_MFIELDDATE_COL = "MFIELDDATE";
        protected final static int SIMPLEENTITY2_MFIELDDATE_IDX = 3;

    }

}
