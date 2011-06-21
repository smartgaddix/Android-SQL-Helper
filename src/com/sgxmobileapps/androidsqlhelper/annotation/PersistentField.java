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
package com.sgxmobileapps.androidsqlhelper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The <b>PersistentField</b> annotation declare the annotated field 
 * as persistent and tells to annotation processor to generate 
 * the SQL support code.
 * <br>
 * 
 * Parameter:
 * <li>
 * <B>columnName:</B> specifies the column name for the field. 
 * The default column name is the uppercase field name 
 * </li>
 * <li>
 * <B>columnType:</B> SQL type for the column 
 * The default column type depend on the java type of the field.
 * </li>
 * <li>
 * <B>nullable:</B> declare the column for this field as nullable 
 * The dafault value is true (column nullable). For key fields the 
 * column is declared as not nullable.
 * </li>
 * <li>
 * <B>customColumnDefinition:</B> declare a custom column definition code
 * that will be appended directly after the column name. 
 * The default value is an empty string.
 * </li>
 * 
 * @author Massimo Gaddini
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface PersistentField {
    String columnName() default "";
    String columnType() default "";
    boolean nullable() default true;
    String customColumnDefinition() default "";
}
