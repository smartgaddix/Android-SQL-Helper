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
 * <B>tableName:</B> specifies the SQL table name. 
 * The default SQL table name is the upper case class name. Use this attribute to specify a custom SQL table name
 * </li>
 * <li>
 * <B>fieldPrefix:</B> set the field prefix to skip for column names. 
 * The default value is no prefix, so the column names will be the upper case complete field names
 * </li>
 * <li>
 * <B>unique:</B> list of field names to use for creating a unique constraint for the table. 
 * The default value is an empty list (no unique constraint). The specified field names must be without prefix
 * </li>
 * <li>
 * <B>pk:</B> list of field names to use for creating a primary key constraint for the table. 
 * The default value is an empty list (no constraint). The specified field names must be without prefix. If specify a 
 * pk constraint and noIdCol is false, the constraint is ignored. 
 * </li>
 * <li>
 * <B>orderBy:</B> order by SQL statement (without ORDER BY clause) for the default ordering of the table. 
 * The default value is an empty string (default order by). The specified ordering is used into query methods.
 * </li>
 * <li>
 * <B>noIdCol:</B> disable the creation of the standard SQLite id column (the "_id integer autoincrement primary key" column). 
 * The default value is false, id column is generated. If true must be specified a pk constraint.
 * </li>
 * <li>
 * <B>idField:</B> if true the Java class must contains getter and setter for id field: long getId() and void setId(long) 
 * The default value is false, id field doesn't exist. If true the query methods fill the class field (invoking the setter).
 * </li>
 * 
 * @author Massimo Gaddini
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface PersistentEntity {
    String tableName() default "";
    String[] pk() default {};
    String[] unique() default {};
    String orderBy() default "";
    String fieldPrefix() default "";
    boolean noIdCol() default false;
    boolean idField() default false;
}
