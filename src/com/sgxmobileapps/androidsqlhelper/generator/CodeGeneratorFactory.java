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

import java.util.ServiceLoader;


/**
 * Factory for {@link CodeGenerator} implementations.
 * 
 * @author Massimo Gaddini
 */
public class CodeGeneratorFactory {
    private static ServiceLoader<CodeGenerator> codeGeneratorLoader;

    
    /**
     * Returns the first code generator service provider declared into 
     * the file META-INF/services/com.sgxmobileapps.androidsqlhelper.generator.CodeGenerator
     * @return the CodeGenerator implementations
     */
    public static CodeGenerator getCodeGenerator(){
        if (codeGeneratorLoader == null)
            codeGeneratorLoader = ServiceLoader.load(CodeGenerator.class, CodeGenerator.class.getClassLoader()); 
        
        for (CodeGenerator cg : codeGeneratorLoader) {
            if (cg != null)
                return cg;
        }
        //return new CodeModelCodeGenerator();
        return null;
    }
}
