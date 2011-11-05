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

import javax.annotation.processing.Filer;

import com.sgxmobileapps.androidsqlhelper.processor.model.Schema;


/**
 * Interface for code generators of the sql helper classes from
 * a Schema definition.
 * 
 * @author Massimo Gaddini
 */
public interface CodeGenerator {
    
    /**
     * Generates Java source files from schema information using the filer
     * @param schema 
     * @param filer
     * @throws CodeGenerationException
     */
    public void generate(Schema schema, Filer filer) throws CodeGenerationException; 
}
