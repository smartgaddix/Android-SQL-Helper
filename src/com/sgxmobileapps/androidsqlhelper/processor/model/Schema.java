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
package com.sgxmobileapps.androidsqlhelper.processor.model;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


/**
 * The Schema class contains the SQL schema information and tables definition 
 * used by the annotation processor for the generation of SQL helper code.
 * 
 * @author Massimo Gaddini
 * Jun 4, 2011
 */
public class Schema {
    
    protected String    KEY_OUT_FOLDER = "outfolder";
    protected String    DEFAULT_OUT_FOLDER = "OUT";
    
    protected String    KEY_PACKAGE = "package";
    protected String    DEFAULT_PACKAGE = "";
    
    protected String    KEY_ADAPTER_CLASS_NAME = "dbadapterclassname";
    protected String    DEFAULT_ADAPTER_CLASS_NAME = "DbAdapter";
    
    protected String    KEY_METADATA_CLASS_NAME = "metadataclassname";
    protected String    DEFAULT_METADATA_CLASS_NAME = "DbMetadata";
    
    protected String    KEY_DB_NAME = "dbname";
    protected String    DEFAULT_DB_NAME = "App.db";
    
    protected Set<Table> mTables = new HashSet<Table>();
    protected String mOutFolder = DEFAULT_OUT_FOLDER;
    protected String mPackage = DEFAULT_PACKAGE;
    protected String mDbAdapterClassName = DEFAULT_ADAPTER_CLASS_NAME;
    protected String mMetadataClassName = DEFAULT_METADATA_CLASS_NAME;
    protected String mDbName = DEFAULT_DB_NAME;

    
    
    /**
     * Loads properties values from a Properties class
     * @param props the properties class
     */
    public void loadSchemaPropeties(Properties props){
        mOutFolder = props.getProperty(KEY_OUT_FOLDER, DEFAULT_OUT_FOLDER);
        mPackage = props.getProperty(KEY_PACKAGE, DEFAULT_PACKAGE);
        mDbAdapterClassName = props.getProperty(KEY_ADAPTER_CLASS_NAME, DEFAULT_ADAPTER_CLASS_NAME);
        mMetadataClassName = props.getProperty(KEY_METADATA_CLASS_NAME, DEFAULT_METADATA_CLASS_NAME);
        mDbName = props.getProperty(KEY_DB_NAME, DEFAULT_DB_NAME);
    }
    
    /**
     * @return the outFolder
     */
    public String getOutFolder() {
        return mOutFolder;
    }
   
    /**
     * @param outFolder the outFolder to set
     */
    public void setOutFolder(String outFolder) {
        mOutFolder = outFolder;
    }
        
    /**
     * @return the package
     */
    public String getPackage() {
        return mPackage;
    }
    
    /**
     * @param package1 the package to set
     */
    public void setPackage(String package1) {
        mPackage = package1;
    }
   
    /**
     * @return the dbAdapterClassName
     */
    public String getDbAdapterClassName() {
        return mDbAdapterClassName;
    }
    
    /**
     * @param dbAdapterClassName the dbAdapterClassName to set
     */
    public void setDbAdapterClassName(String dbAdapterClassName) {
        mDbAdapterClassName = dbAdapterClassName;
    }
   
    /**
     * @return the metadataClassName
     */
    public String getMetadataClassName() {
        return mMetadataClassName;
    }
   
    /**
     * @param metadataClassName the metadataClassName to set
     */
    public void setMetadataClassName(String metadataClassName) {
        mMetadataClassName = metadataClassName;
    }
   
    /**
     * @return the dbName
     */
    public String getDbName() {
        return mDbName;
    }
   
    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        mDbName = dbName;
    }

    /**
     * @return the tables
     */
    public Set<Table> getTables() {
        return mTables;
    }
    
    /**
     * @param tables the tables to set
     */
    public void setTables(Set<Table> tables) {
        mTables = tables;
    }
    
    /**
     * @param table the table to add
     */
    public void addTable(Table table) {
        mTables.add(table);
    }
}
