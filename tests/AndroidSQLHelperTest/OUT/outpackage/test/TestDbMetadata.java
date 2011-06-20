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



/**
 * This class defines some metadata costants used by datastore. Contains table names and column names.
 * 
 * @author smartgaddix
 * 
 */
public class TestDbMetadata {

    final static String DATABASE_NAME = "test.db";
    final static int DATABASE_VERSION = 1;

    public final static class SimpleEntity {

        public final static String SIMPLEENTITY_TABLE_NAME = "SIMPLEENTITY";
        public final static String SIMPLEENTITY__id_COL = "_id";
        protected final static int SIMPLEENTITY__id_IDX = 0;
        public final static String SIMPLEENTITY_MFIELDSTRING_COL = "MFIELDSTRING";
        protected final static int SIMPLEENTITY_MFIELDSTRING_IDX = 1;
        public final static String SIMPLEENTITY_MFIELDLONG_COL = "MFIELDLONG";
        protected final static int SIMPLEENTITY_MFIELDLONG_IDX = 2;

    }

    public final static class SimpleEntity2 {

        public final static String SIMPLEENTITY2_TABLE_NAME = "SIMPLEENTITY2";
        public final static String SIMPLEENTITY2_MFIELDSTRING_COL = "MFIELDSTRING";
        protected final static int SIMPLEENTITY2_MFIELDSTRING_IDX = 0;
        public final static String SIMPLEENTITY2_MFIELDDATE_COL = "MFIELDDATE";
        protected final static int SIMPLEENTITY2_MFIELDDATE_IDX = 1;
        public final static String SIMPLEENTITY2__id_COL = "_id";
        protected final static int SIMPLEENTITY2__id_IDX = 2;
        public final static String SIMPLEENTITY2_MFIELDLONG_COL = "MFIELDLONG";
        protected final static int SIMPLEENTITY2_MFIELDLONG_IDX = 3;

    }

}
