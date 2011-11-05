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

    public final static String DATABASE_NAME = "test.db";
    public final static int DATABASE_VERSION = 1;

    public final static class FullEntity {

        public final static String FULL_TABLE_NAME = "FULL";
        public final static String FULL_DEFAULT_ORDER = "LONGPRIMITIVE ASC, INTPRIMITIVE DESC";
        public final static String FULL_LONGPRIMITIVE_COL = "LONGPRIMITIVE";
        protected final static int FULL_LONGPRIMITIVE_IDX = 0;
        public final static String FULL_LONG_COL = "LONG";
        protected final static int FULL_LONG_IDX = 1;
        public final static String FULL_INTPRIMITIVE_COL = "INTPRIMITIVE";
        protected final static int FULL_INTPRIMITIVE_IDX = 2;
        public final static String FULL_INT_COL = "INT";
        protected final static int FULL_INT_IDX = 3;
        public final static String FULL_BYTEPRIMITIVE_COL = "BYTEPRIMITIVE";
        protected final static int FULL_BYTEPRIMITIVE_IDX = 4;
        public final static String FULL_BYTE_COL = "BYTE";
        protected final static int FULL_BYTE_IDX = 5;
        public final static String FULL_SHORTPRIMITIVE_COL = "SHORTPRIMITIVE";
        protected final static int FULL_SHORTPRIMITIVE_IDX = 6;
        public final static String FULL_SHORT_COL = "SHORT";
        protected final static int FULL_SHORT_IDX = 7;
        public final static String FULL_CHARPRIMITIVE_COL = "CHARPRIMITIVE";
        protected final static int FULL_CHARPRIMITIVE_IDX = 8;
        public final static String FULL_CHAR_COL = "CHAR";
        protected final static int FULL_CHAR_IDX = 9;
        public final static String FULL_BOOLPRIMITIVE_COL = "BOOLPRIMITIVE";
        protected final static int FULL_BOOLPRIMITIVE_IDX = 10;
        public final static String FULL_BOOL_COL = "BOOL";
        protected final static int FULL_BOOL_IDX = 11;
        public final static String FULL_FLOATPRIMITIVE_COL = "FLOATPRIMITIVE";
        protected final static int FULL_FLOATPRIMITIVE_IDX = 12;
        public final static String FULL_FLOAT_COL = "FLOAT";
        protected final static int FULL_FLOAT_IDX = 13;
        public final static String FULL_DOUBLEPRIMITIVE_COL = "DOUBLEPRIMITIVE";
        protected final static int FULL_DOUBLEPRIMITIVE_IDX = 14;
        public final static String FULL_DOUBLE_COL = "DOUBLE";
        protected final static int FULL_DOUBLE_IDX = 15;
        public final static String FULL_STRING_COL = "STRING";
        protected final static int FULL_STRING_IDX = 16;
        public final static String FULL_CHARSEQ_COL = "CHARSEQ";
        protected final static int FULL_CHARSEQ_IDX = 17;
        public final static String FULL_DATE_COL = "DATE";
        protected final static int FULL_DATE_IDX = 18;
        public final static String FULL_CALENDAR_COL = "CALENDAR";
        protected final static int FULL_CALENDAR_IDX = 19;

    }

}
