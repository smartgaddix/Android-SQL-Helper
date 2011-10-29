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
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JExpressionImpl;
import com.sun.codemodel.JFormatter;
import com.sun.codemodel.JGenerable;
import com.sun.codemodel.JStringLiteral;


/**
 * @author Massimo Gaddini
 *
 */
public class FormattedExpression extends JExpressionImpl {
 
    String op;
    JExpression left;
    JGenerable right;
    boolean insertBracket;
    boolean insertNewline;
    
    FormattedExpression(){
    }
    
    FormattedExpression(String op, JExpression left, JGenerable right, boolean bracket, boolean newline) {
        this.left = left;
        this.op = op;
        this.right = right;
        this.insertBracket = bracket;
        this.insertNewline = newline;
    }
    
    public FormattedExpression add(JExpression right, boolean bracket, boolean newline){
        return new FormattedExpression("+", this, right, bracket, newline);
    }
    
    public static FormattedExpression lit(String s, boolean bracket, boolean newline) {
        return new StringLiteral(s, bracket, newline);
    }

    /* 
     * @see com.sun.codemodel.JGenerable#generate(com.sun.codemodel.JFormatter)
     */
    @Override
    public void generate(JFormatter f) {
        if (insertBracket) {
            f.p('(');
        } 
        
        f.g(left).p(op);
        
        if (insertNewline){
            f.nl();
        }
        
        f.g(right);
        
        if (insertBracket) {
            f.p(')');
        }
    }
}
