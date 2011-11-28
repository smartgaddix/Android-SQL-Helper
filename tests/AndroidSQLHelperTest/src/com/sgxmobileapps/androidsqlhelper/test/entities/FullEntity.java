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
        unique={"Long", "Int"},
        fieldPrefix="m")
public class FullEntity {
    
    @PersistentField(
            columnName="LONGPRIV",
            nullable=true)
    private long mLongPrimitive;
    
    @PersistentField(
            columnName="Long",
            nullable=true)
    private Long mLong;
    
    @PersistentField(
            columnName="IntPrimitive",
            nullable=true, 
            customColumnDefinition="REAL UNIQUE NOT NULL")
    private int mIntPrimitive;
    
    @PersistentField(
            columnName="Int",
            nullable=false)
    private Integer mInt;
    
    @PersistentField(
            columnName="BytePrimitive",
            nullable=false)
    private byte mBytePrimitive;
    
    @PersistentField(
            columnName="Byte",
            nullable=false)
    private Byte mByte;
    
    @PersistentField(
            columnName="ShortPrimitive",
            nullable=false)
    private short mShortPrimitive;
    
    @PersistentField(
            columnName="Short",
            nullable=false)
    private Short mShort;
    
    @PersistentField(
            columnName="CharPrimitive",
            nullable=false)
    private char mCharPrimitive;
    
    @PersistentField(
            columnName="Char",
            nullable=false)
    private Character mChar;
    
    @PersistentField(
            columnName="BoolPrimitive",
            nullable=false)
    private boolean mBoolPrimitive;
    
    @PersistentField(
            columnName="Bool",
            nullable=false)
    private Boolean mBool;
    
    @PersistentField(
            columnName="FloatPrimitive",
            nullable=false)
    private float mFloatPrimitive;
    
    @PersistentField(
            columnName="Float",
            nullable=false)
    private Float mFloat;
    
    @PersistentField(
            columnName="DoublePrimitive",
            nullable=false)
    private double mDoublePrimitive;
    
    @PersistentField(
            columnName="Double",
            nullable=false)
    private Double mDouble;
    
    @PersistentField(
            columnName="String",
            nullable=false)
    private String mString;
    
    @PersistentField(
            columnName="CharSeq",
            nullable=false)
    private CharSequence mCharSeq;    
    
    @PersistentField(
            columnName="Date",
            nullable=false)
    private Date mDate;
    
    @PersistentField(
            columnName="Calendar",
            nullable=false)
    private GregorianCalendar mCalendar;

    
    public long getLongPrimitive() {
        return mLongPrimitive;
    }

    
    public void setLongPrimitive(long longPrimitive) {
        mLongPrimitive = longPrimitive;
    }

    
    public Long getLong() {
        return mLong;
    }

    
    public void setLong(Long l) {
        mLong = l;
    }

    
    public int getIntPrimitive() {
        return mIntPrimitive;
    }

    
    public void setIntPrimitive(int intPrimitive) {
        mIntPrimitive = intPrimitive;
    }

    
    public Integer getInt() {
        return mInt;
    }

    
    public void setInt(Integer i) {
        mInt = i;
    }

    
    public byte getBytePrimitive() {
        return mBytePrimitive;
    }

    
    public void setBytePrimitive(byte bytePrimitive) {
        mBytePrimitive = bytePrimitive;
    }

    
    public Byte getByte() {
        return mByte;
    }

    
    public void setByte(Byte b) {
        mByte = b;
    }

    
    public short getShortPrimitive() {
        return mShortPrimitive;
    }

    
    public void setShortPrimitive(short shortPrimitive) {
        mShortPrimitive = shortPrimitive;
    }

    
    public Short getShort() {
        return mShort;
    }

    
    public void setShort(Short s) {
        mShort = s;
    }

    
    public char getCharPrimitive() {
        return mCharPrimitive;
    }

    
    public void setCharPrimitive(char charPrimitive) {
        mCharPrimitive = charPrimitive;
    }

    
    public Character getChar() {
        return mChar;
    }

    
    public void setChar(Character c) {
        mChar = c;
    }

    
    public boolean isBoolPrimitive() {
        return mBoolPrimitive;
    }

    
    public void setBoolPrimitive(boolean boolPrimitive) {
        mBoolPrimitive = boolPrimitive;
    }

    
    public Boolean isBool() {
        return mBool;
    }

    
    public void setBool(Boolean bool) {
        mBool = bool;
    }

    
    public float getFloatPrimitive() {
        return mFloatPrimitive;
    }

    
    public void setFloatPrimitive(float floatPrimitive) {
        mFloatPrimitive = floatPrimitive;
    }

    
    public Float getFloat() {
        return mFloat;
    }

    
    public void setFloat(Float f) {
        mFloat = f;
    }

    
    public double getDoublePrimitive() {
        return mDoublePrimitive;
    }

    
    public void setDoublePrimitive(double doublePrimitive) {
        mDoublePrimitive = doublePrimitive;
    }

    
    public Double getDouble() {
        return mDouble;
    }

    
    public void setDouble(Double d) {
        mDouble = d;
    }

    
    public String getString() {
        return mString;
    }

    
    public void setString(String string) {
        mString = string;
    }

    
    public CharSequence getCharSeq() {
        return mCharSeq;
    }

    
    public void setCharSeq(CharSequence charSeq) {
        mCharSeq = charSeq;
    }

    
    public Date getDate() {
        return mDate;
    }

    
    public void setDate(Date date) {
        mDate = date;
    }

    
    public GregorianCalendar getCalendar() {
        return mCalendar;
    }

    
    public void setCalendar(GregorianCalendar calendar) {
        mCalendar = calendar;
    }
}
