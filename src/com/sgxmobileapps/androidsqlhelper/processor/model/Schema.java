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
import java.util.Set;


/**
 * The Schema class contains the SQL schema information and table information 
 * used by the annotation processor for the generation of SQL helper code.
 * 
 * @author Massimo Gaddini
 * Jun 4, 2011
 */
public class Schema {
    protected Set<Table> mTables = new HashSet<Table>();
    
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
