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
    /* Metadata class */
    public static final String METADATA_CLASS_JAVADOC = "This class defines some metadata costants used by datastore. Contains table names and column names.";
    public static final String METADATA_DATABASE_NAME_FIELD = "DATABASE_NAME";
    public static final String METADATA_DATABASE_VERSION_FIELD = "DATABASE_VERSION";
    public static final String METADATA_ENTITY_TABLE_NAME_SUFFIX = "_TABLE_NAME";
    public static final String METADATA_ENTITY_DEFAULT_ORDER_SUFFIX = "_DEFAULT_ORDER";
    public static final String METADATA_ENTITY_COL_NAME_SUFFIX = "_COL";
    public static final String METADATA_ENTITY_COL_IDX_SUFFIX = "_IDX";
    
    /* DbAdapter class */
    public static final String DBADAPTER_CLASS_JAVADOC = "This class contains utility methods and classes for the application datastore.";
    public static final String DBADAPTER_DBHELPER_MEMBER_NAME = "mDbHelper";
    public static final String DBADAPTER_DBHELPER_MEMBER_JAVADOC = "Database open/upgrade helper";
    public static final String DBADAPTER_DB_MEMBER_NAME = "mDb";
    public static final String DBADAPTER_DB_MEMBER_JAVADOC = "The database instance";
    public static final String DBADAPTER_SQL_ENTITY_CREATE_TABLE_PREFIX = "SQL_";
    public static final String DBADAPTER_SQL_ENTITY_CREATE_TABLE_SUFFIX = "_CREATE_TABLE";
    public static final String DBADAPTER_SQL_ENTITY_DROP_TABLE_PREFIX = "SQL_";
    public static final String DBADAPTER_SQL_ENTITY_DROP_TABLE_SUFFIX = "_DROP_TABLE";
    public static final String DBADAPTER_SQL_CREATE_TABLE_PREFIX = "CREATE TABLE IF NOT EXISTS";
    public static final String DBADAPTER_SQL_DROP_TABLE_PREFIX = "DROP TABLE IF EXISTS";
    
    /* DbAdapter methods */
    public static final String DBADAPTER_CONSTRUCTOR_METHOD_JAVADOC = "Initializes the data store";
    public static final String DBADAPTER_CONSTRUCTOR_METHOD_PARAM_CTX_NAME = "context";
    public static final String DBADAPTER_CONSTRUCTOR_METHOD_PARAM_CTX_JAVADOC = "the context";
    public static final String DBADAPTER_OPEN_METHOD_NAME = "open";
    public static final String DBADAPTER_OPEN_METHOD_JAVADOC = "Opens a connection with the underlying data base";
    public static final String DBADAPTER_OPEN_METHOD_RETURN_JAVADOC = "the Datastore instance";
    public static final String DBADAPTER_OPEN_METHOD_EXCP_JAVADOC = "if error occurred";
    public static final String DBADAPTER_CLOSE_METHOD_NAME = "close";
    public static final String DBADAPTER_CLOSE_METHOD_JAVADOC = "Closes the connection with the underlying data base";
    public static final String DBADAPTER_ONUPGRADE_METHOD_NAME = "onDatastoreUpgrade";
    public static final String DBADAPTER_ONUPGRADE_METHOD_JAVADOC = "Called from Datastore during the upgrading phase of the data base schema. Derived classes can override this method if they need to do some work during the upgrade process";
    public static final String DBADAPTER_ONUPGRADE_METHOD_PARAM_DB_NAME = "db";
    public static final String DBADAPTER_ONUPGRADE_METHOD_PARAM_DB_JAVADOC = "the SQLiteDatabase";
    public static final String DBADAPTER_ONUPGRADE_METHOD_PARAM_OLDV_NAME = "oldVersion";
    public static final String DBADAPTER_ONUPGRADE_METHOD_PARAM_OLDV_JAVADOC = "schema's old version";
    public static final String DBADAPTER_ONUPGRADE_METHOD_PARAM_NEWV_NAME = "newVersion";
    public static final String DBADAPTER_ONUPGRADE_METHOD_PARAM_NEWV_JAVADOC = "schema's new version";
    public static final String DBADAPTER_ONUPGRADE_METHOD_RETURN_JAVADOC = "if the method return false the old schema will be destroyed and the new schema will be created. If return true the upgrade process terminates";
      
    /* DbHelper class */
    public static final String DBADAPTER_DBHELPER_CLASS_NAME = "DbHelper";
    public static final String DBADAPTER_DBHELPER_DATASTORE_MEMBER_NAME = "mDatastore";
    public static final String DBADAPTER_DBHELPER_CONSTRUCTOR_METHOD_PARAM_CTX_NAME = "context";
    public static final String DBADAPTER_DBHELPER_CONSTRUCTOR_METHOD_PARAM_NAME_NAME = "name";
    public static final String DBADAPTER_DBHELPER_CONSTRUCTOR_METHOD_PARAM_FACTORY_NAME = "factory";
    public static final String DBADAPTER_DBHELPER_CONSTRUCTOR_METHOD_PARAM_VERSION_NAME = "version";
    public static final String DBADAPTER_DBHELPER_CONSTRUCTOR_METHOD_PARAM_DATASTORE_NAME = "datastore";
    public static final String DBADAPTER_DBHELPER_ONCREATE_METHOD_NAME = "onCreate";
    public static final String DBADAPTER_DBHELPER_ONCREATE_METHOD_JAVADOC = "Called when no database exists in disk and the helper class needs to create a new one";
    public static final String DBADAPTER_DBHELPER_ONCREATE_METHOD_PARAM_DB_NAME = "db";
    public static final String DBADAPTER_DBHELPER_ONUPGRADE_METHOD_NAME = "onUpgrade";
    public static final String DBADAPTER_DBHELPER_ONUPGRADE_METHOD_JAVADOC = "Called when there is a database version mismatch meaning that the version of the database on disk needs to be upgraded to the current version";
    public static final String DBADAPTER_DBHELPER_ONUPGRADE_METHOD_PARAM_DB_NAME = "db";
    public static final String DBADAPTER_DBHELPER_ONUPGRADE_METHOD_PARAM_OLDV_NAME = "oldVersion";
    public static final String DBADAPTER_DBHELPER_ONUPGRADE_METHOD_PARAM_NEWV_NAME = "newVersion";
    public static final String DBADAPTER_DBHELPER_ONUPGRADE_METHOD_CODE_COMMENT1 = "// Upgrade the existing database to conform to the new version. Multiple previous versions can be handled by comparing _oldVersion and _newVersion values";
    public static final String DBADAPTER_DBHELPER_ONUPGRADE_METHOD_CODE_COMMENT2 = "// The simplest case is to drop the old table and create a new one.";
    public static final String DBADAPTER_DBHELPER_ONUPGRADE_METHOD_CODE_COMMENT3 = "// Create a new one.";
    
    /* DbAdapter helper functions */
    public static final String DBADAPTER_HELPER_METHOD_GETCONTENTVALUES_NAME = "getContentValues";
    public static final String DBADAPTER_HELPER_METHOD_FILLFROMCURSOR_NAME_PREFIX = "fillFromCursor";
    public static final String DBADAPTER_HELPER_METHOD_ADDENTITY_NAME_PREFIX = "add";
    public static final String DBADAPTER_HELPER_METHOD_GETENTITY_NAME_PREFIX = "get";
    public static final String DBADAPTER_HELPER_METHOD_UPDATEENTITY_NAME_PREFIX = "update";
    public static final String DBADAPTER_HELPER_METHOD_REMOVEENTITY_NAME_PREFIX = "delete";
    public static final String DBADAPTER_HELPER_METHOD_REMOVEALL_NAME_PREFIX = "deleteAll";} 
