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
package com.sgxmobileapps.androidsqlhelper.generator.codemodel;

import com.sgxmobileapps.androidsqlhelper.processor.model.VisitorContext;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JPackage;

import java.util.HashMap;


/**
 * @author Massimo Gaddini
 *
 */
public class CodeModelVisitorContext implements VisitorContext{
    class MetaFieldInfo {
        public String mFieldName;
        public JFieldVar mColNameField;
        public JFieldVar mColIdxField;
    }
    
    class MetaTableInfo {
        public String mEntityName;
        public JDefinedClass mClass;
        public JFieldVar mTableNameField;
        public JFieldVar mDefOrderField;
        HashMap<String, MetaFieldInfo> mFields = new HashMap<String, MetaFieldInfo>();
    }
    
    class MetadataClassInfo {
        public JDefinedClass mClass;
        public JFieldVar mDbNameField;
        public JFieldVar mDbVerField;
        public HashMap<String, MetaTableInfo> mTables = new HashMap<String, MetaTableInfo>();
    }
    
    public JCodeModel mCMRoot;
    public JPackage mPckg;
    public MetadataClassInfo mMetadataInfo = new MetadataClassInfo();  
    
    public MetaTableInfo getMetaTableInfo(String entityName) {
        MetaTableInfo mti = mMetadataInfo.mTables.get(entityName);
        if (mti == null) {
            mti = new MetaTableInfo();
            mti.mEntityName = entityName;
            mMetadataInfo.mTables.put(entityName, mti);
        }
        
        return mti;
    }
    
    public MetaFieldInfo getMetaFieldInfo(String entityName, String fieldName) {
        MetaTableInfo mti = getMetaTableInfo(entityName);
        MetaFieldInfo mfi = mti.mFields.get(fieldName);
        if (mfi == null) {
            mfi = new MetaFieldInfo();
            mfi.mFieldName = fieldName;
            mti.mFields.put(fieldName, mfi);
        }
        
        return mfi;
    }
}
