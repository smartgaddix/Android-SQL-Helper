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



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;


/**
 * The Schema class contains the SQL schema information and tables definition 
 * used by the annotation processor for the generation of SQL helper code.
 * 
 * @author Massimo Gaddini
 */
public class Schema implements Visitable {    
    protected String    KEY_PACKAGE = "package";
    protected String    DEFAULT_PACKAGE = "";
    
    protected String    KEY_ADAPTER_CLASS_NAME = "dbadapterclassname";
    protected String    DEFAULT_ADAPTER_CLASS_NAME = "DbAdapter";
    
    protected String    KEY_METADATA_CLASS_NAME = "metadataclassname";
    protected String    DEFAULT_METADATA_CLASS_NAME = "DbMetadata";
    
    protected String    KEY_DB_NAME = "dbname";
    protected String    DEFAULT_DB_NAME = "App.db";
    
    protected String    KEY_DB_VERSION = "dbversion";
    protected String    DEFAULT_DB_VERSION = "1";
    
    protected String    KEY_AUTHOR = "author";
    protected String    DEFAULT_AUTHOR = "";
    
    protected String    KEY_LICENSE = "license";
    protected String    DEFAULT_LICENSE = "";
    
    protected String    KEY_LICENSE_FILE = "licensefile";
    protected String    DEFAULT_LICENSE_FILE = "";

    
    protected Vector<Table> mTables = new Vector<Table>();
    protected String mPackage = DEFAULT_PACKAGE;
    protected String mDbAdapterClassName = DEFAULT_ADAPTER_CLASS_NAME;
    protected String mMetadataClassName = DEFAULT_METADATA_CLASS_NAME;
    protected String mDbName = DEFAULT_DB_NAME;
    protected String mDbVersion = DEFAULT_DB_VERSION;
    protected String mAuthor = DEFAULT_AUTHOR;
    protected String mLicense = DEFAULT_LICENSE;
    protected String mLicenseFile = DEFAULT_LICENSE_FILE;
    
    /**
     * Loads properties values from a Properties class
     * @param props the properties class
     * @throws IOException 
     */
    public void loadSchemaPropeties(Properties props) throws IOException{
        mPackage = props.getProperty(KEY_PACKAGE, DEFAULT_PACKAGE);
        mDbAdapterClassName = props.getProperty(KEY_ADAPTER_CLASS_NAME, DEFAULT_ADAPTER_CLASS_NAME);
        mMetadataClassName = props.getProperty(KEY_METADATA_CLASS_NAME, DEFAULT_METADATA_CLASS_NAME);
        mDbName = props.getProperty(KEY_DB_NAME, DEFAULT_DB_NAME);
        mDbVersion = props.getProperty(KEY_DB_VERSION, DEFAULT_DB_VERSION);
        mAuthor = props.getProperty(KEY_AUTHOR, DEFAULT_AUTHOR);
        mLicense = props.getProperty(KEY_LICENSE, DEFAULT_LICENSE);
        mLicenseFile = props.getProperty(KEY_LICENSE_FILE, DEFAULT_LICENSE_FILE);
        
        if (!mLicenseFile.isEmpty()) {
        	FileInputStream file = null;
            try{
            	byte[] buffer = new byte[(int) new File(mLicenseFile).length()];
            	file = new FileInputStream(mLicenseFile);
                file.read(buffer);
                mLicense = new String(buffer);
            } finally{
            	file.close();
            }
        }
    }
    
    /* 
     * @see com.sgxmobileapps.androidsqlhelper.processor.model.Visitable#accept(com.sgxmobileapps.androidsqlhelper.processor.model.GeneratorVisitor)
     */
    @Override
    public void accept(Visitor visitor) throws VisitorException {
        visitor.visit(this);
        for (Table table: mTables) {
            table.accept(visitor);
        }
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
     * @return the dbVersion
     */
    public String getDbVersion() {
        return mDbVersion;
    }
    
    /**
     * @param dbVersion the dbVersion to set
     */
    public void setDbVersion(String dbVersion) {
        mDbVersion = dbVersion;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        mAuthor = author;
    }

    /**
     * @return the license
     */
    public String getLicense() {
        return mLicense;
    }

    /**
     * @param license the license to set
     */
    public void setLicense(String license) {
        mLicense = license;
    }

    /**
     * @return the tables
     */
    public Vector<Table> getTables() {
        return mTables;
    }
    
    /**
     * @param tables the tables to set
     */
    public void setTables(Vector<Table> tables) {
        mTables = tables;
    }
    
    /**
     * @param table the table to add
     */
    public void addTable(Table table) {
        mTables.add(table);
    }

    /* 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Schema [");
        if (mAuthor != null) {
            builder.append("mAuthor=");
            builder.append(mAuthor);
            builder.append(", ");
        }
        if (mDbAdapterClassName != null) {
            builder.append("mDbAdapterClassName=");
            builder.append(mDbAdapterClassName);
            builder.append(", ");
        }
        if (mDbName != null) {
            builder.append("mDbName=");
            builder.append(mDbName);
            builder.append(", ");
        }
        if (mDbVersion != null) {
            builder.append("mDbVersion=");
            builder.append(mDbVersion);
            builder.append(", ");
        }
        if (mLicense != null) {
            builder.append("mLicense=");
            builder.append(mLicense);
            builder.append(", ");
        }
        if (mLicenseFile != null) {
            builder.append("mLicenseFile=");
            builder.append(mLicenseFile);
            builder.append(", ");
        }
        if (mMetadataClassName != null) {
            builder.append("mMetadataClassName=");
            builder.append(mMetadataClassName);
            builder.append(", ");
        }
        if (mPackage != null) {
            builder.append("mPackage=");
            builder.append(mPackage);
            builder.append(", ");
        }
        if (mTables != null) {
            builder.append("mTables=");
            builder.append(mTables);
        }
        builder.append("]");
        return builder.toString();
    }

}
