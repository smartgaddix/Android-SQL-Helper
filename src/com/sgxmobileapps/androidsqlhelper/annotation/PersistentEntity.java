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
 * The <b>PersistentEntity</b> annotation declare the annotated class 
 * as persistent and tells to annotation processor to generate 
 * the SQL support code.
 * <br>
 * 
 * Parameter:
 * <li>
 * <B>tableName:</B> specifies the table name. 
 * The default table name is the uppercase class name 
 * </li>
 * <li>
 * <B>unique:</B> list of field names to use for creating a unique constraint for the table. 
 * The default value is an empty list (no unique constraint)
 * </li>
 * <li>
 * <B>orderBy:</B> list of field names for the default order by for the table. 
 * The default value is an empty list (default order by). 
 * </li>
 * <li>
 * <B>fieldPrefix:</B> set the field prefix to be skipped for column names. 
 * The default value is no prefix so the columns names will be the uppercase complete fields name
 * </li>
 * <li>
 * <B>noIdCol:</B> disable the creation of the standard android id column (integer autoincrement primary key). 
 * The default value is false, id column is generated
 * </li>
 * 
 * @author Massimo Gaddini
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface PersistentEntity {
    String tableName() default "";
    String[] unique() default {};
    String[] orderBy() default {};
    String fieldPrefix() default "";
    boolean noIdCol() default false;
}
