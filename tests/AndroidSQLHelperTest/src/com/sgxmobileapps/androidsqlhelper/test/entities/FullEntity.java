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
package com.sgxmobileapps.androidsqlhelper.test.entities;

import com.sgxmobileapps.androidsqlhelper.annotation.PersistentEntity;
import com.sgxmobileapps.androidsqlhelper.annotation.PersistentField;

import java.util.Date;
import java.util.GregorianCalendar;


/**
 * @author Massimo Gaddini
 * Jun 9, 2011
 */
@PersistentEntity(
        tableName="FULL", 
        noIdCol=true, 
        orderBy="LONGPRIMITIVE ASC, INTPRIMITIVE DESC",
        unique={"mLong", "mInt"},
        fieldPrefix="m")
@SuppressWarnings(value="unused")
public class FullEntity {
    
    @PersistentField(
            columnName="LongPrimitive",
            columnType="INTEGER",
            nullable=false)
    private long mLongPrimitive;
    
    @PersistentField(
            columnName="Long",
            columnType="INTEGER",
            nullable=false)
    private Long mLong;
    
    @PersistentField(
            columnName="IntPrimitive",
            columnType="INTEGER",
            nullable=false)
    private int mIntPrimitive;
    
    @PersistentField(
            columnName="Int",
            columnType="INTEGER",
            nullable=false)
    private Integer mInt;
    
    @PersistentField(
            columnName="BytePrimitive",
            columnType="INTEGER",
            nullable=false)
    private byte mBytePrimitive;
    
    @PersistentField(
            columnName="Byte",
            columnType="INTEGER",
            nullable=false)
    private Byte mByte;
    
    @PersistentField(
            columnName="ShortPrimitive",
            columnType="INTEGER",
            nullable=false)
    private short mShortPrimitive;
    
    @PersistentField(
            columnName="Short",
            columnType="INTEGER",
            nullable=false)
    private Short mShort;
    
    @PersistentField(
            columnName="CharPrimitive",
            columnType="TEXT",
            nullable=false)
    private char mCharPrimitive;
    
    @PersistentField(
            columnName="Char",
            columnType="TEXT",
            nullable=false)
    private Character mChar;
    
    @PersistentField(
            columnName="BoolPrimitive",
            columnType="INTEGER",
            nullable=false)
    private boolean mBoolPrimitive;
    
    @PersistentField(
            columnName="Bool",
            columnType="INTEGER",
            nullable=false)
    private Boolean mBool;
    
    @PersistentField(
            columnName="FloatPrimitive",
            columnType="REAL",
            nullable=false)
    private float mFloatPrimitive;
    
    @PersistentField(
            columnName="Float",
            columnType="REAL",
            nullable=false)
    private Float mFloat;
    
    @PersistentField(
            columnName="DoublePrimitive",
            columnType="REAL",
            nullable=false)
    private double mDoublePrimitive;
    
    @PersistentField(
            columnName="Double",
            columnType="REAL",
            nullable=false)
    private Double mDouble;
    
    @PersistentField(
            columnName="String",
            columnType="TEXT",
            nullable=false)
    private String mString;
    
    @PersistentField(
            columnName="CharSeq",
            columnType="TEXT",
            nullable=false)
    private CharSequence mCharSeq;    
    
    @PersistentField(
            columnName="Date",
            columnType="INTEGER",
            nullable=false)
    private Date mDate;
    
    @PersistentField(
            columnName="Calendar",
            columnType="INTEGER",
            nullable=false)
    private GregorianCalendar mCalendar;
}
