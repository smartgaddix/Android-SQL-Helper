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
        noIdColumn=true, 
        orderBy={"mLongPrimitive", "mIntPrimitive"},
        unique={"mLong", "mInt"})
public class FullEntity {
    
    @PersistentField(
            columnName="LongPrimitive",
            columnType="INTEGER",
            key=true,
            nullable=false)
    private long mLongPrimitive;
    
    @PersistentField(
            columnName="Long",
            columnType="INTEGER",
            key=true,
            nullable=false)
    private Long mLong;
    
    @PersistentField(
            columnName="IntPrimitive",
            columnType="INTEGER",
            key=true,
            nullable=false)
    private int mIntPrimitive;
    
    @PersistentField(
            columnName="Int",
            columnType="INTEGER",
            key=true,
            nullable=false)
    private Integer mInt;
    
    @PersistentField(
            columnName="BytePrimitive",
            columnType="INTEGER",
            key=true,
            nullable=false)
    private byte mBytePrimitive;
    
    @PersistentField(
            columnName="Byte",
            columnType="INTEGER",
            key=true,
            nullable=false)
    private Byte mByte;
    
    @PersistentField(
            columnName="ShortPrimitive",
            columnType="INTEGER",
            key=true,
            nullable=false)
    private short mShortPrimitive;
    
    @PersistentField(
            columnName="Short",
            columnType="INTEGER",
            key=true,
            nullable=false)
    private Short mShort;
    
    @PersistentField(
            columnName="CharPrimitive",
            columnType="TEXT",
            key=true,
            nullable=false)
    private char mCharPrimitive;
    
    @PersistentField(
            columnName="Char",
            columnType="TEXT",
            key=true,
            nullable=false)
    private Character mChar;
    
    @PersistentField(
            columnName="BoolPrimitive",
            columnType="INTEGER",
            key=true,
            nullable=false)
    private boolean mBoolPrimitive;
    
    @PersistentField(
            columnName="Bool",
            columnType="INTEGER",
            key=true,
            nullable=false)
    private Boolean mBool;
    
    @PersistentField(
            columnName="FloatPrimitive",
            columnType="REAL",
            key=true,
            nullable=false)
    private float mFloatPrimitive;
    
    @PersistentField(
            columnName="Float",
            columnType="REAL",
            key=true,
            nullable=false)
    private Float mFloat;
    
    @PersistentField(
            columnName="DoublePrimitive",
            columnType="REAL",
            key=true,
            nullable=false)
    private double mDoublePrimitive;
    
    @PersistentField(
            columnName="Double",
            columnType="REAL",
            key=true,
            nullable=false)
    private Double mDouble;
    
    @PersistentField(
            columnName="String",
            columnType="TEXT",
            key=true,
            nullable=false)
    private String mString;
    
    @PersistentField(
            columnName="CharSeq",
            columnType="TEXT",
            key=true,
            nullable=false)
    private CharSequence mCharSeq;    
    
    @PersistentField(
            columnName="Date",
            columnType="INTEGER",
            key=true,
            nullable=false)
    private Date mDate;
    
    @PersistentField(
            columnName="Calendar",
            columnType="INTEGER",
            key=true,
            nullable=false)
    private GregorianCalendar mCalendar;
}
