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
package com.sgxmobileapps.androidsqlhelper.generator;


/**
 * @author Massimo Gaddini
 *
 */
public class CodeGenerationConstants {
    public static final String METADATA_CLASS_JAVADOC = "This class defines some metadata costants used by datastore. Contains table names and column names.";
    public static final String METADATA_DATABASE_NAME_FIELD = "DATABASE_NAME";
    public static final String METADATA_DATABASE_VERSION_FIELD = "DATABASE_VERSION";
    public static final String METADATA_ENTITY_TABLE_NAME_SUFFIX = "_TABLE_NAME";
    public static final String METADATA_ENTITY_DEFAULT_ORDER_SUFFIX = "_DEFAULT_ORDER";
    public static final String METADATA_ENTITY_COL_NAME_SUFFIX = "_COL";
    public static final String METADATA_ENTITY_COL_IDX_SUFFIX = "_IDX";
    
    public static final String DBADAPTER_CLASS_JAVADOC = "This class contains utility methods and classes for the application datastore.";
    public static final String DBADAPTER_SQL_ENTITY_CREATE_TABLE_PREFIX = "SQL_";
    public static final String DBADAPTER_SQL_ENTITY_CREATE_TABLE_SUFFIX = "_DROP_TABLE";
   
}
