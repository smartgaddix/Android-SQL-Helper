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

import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFormatter;


/**
 * @author Massimo Gaddini
 *
 */
public class StringLiteral extends FormattedExpression {
    String str = "";
    
    public StringLiteral(String s, boolean bracket, boolean newline){
        str = s;
        insertBracket = bracket;
        insertNewline = newline;
    }
    
    /* 
     * @see com.sun.codemodel.JGenerable#generate(com.sun.codemodel.JFormatter)
     */
    @Override
    public void generate(JFormatter f) {
        if (insertBracket) {
            f.p('(').p(JExpr.quotify('"', str)).p(')'); 
        } else {
            f.p(JExpr.quotify('"', str));
        }
        
        if (insertNewline){
            f.nl();
        }
    }
}
