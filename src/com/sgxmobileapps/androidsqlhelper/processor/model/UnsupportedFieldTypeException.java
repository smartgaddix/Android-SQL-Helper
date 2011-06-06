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
package com.sgxmobileapps.androidsqlhelper.processor.model;


/**
 * Indicates that an unknown kind of type was encountered when processing 
 * persistent fields.
 * @author Massimo Gaddini
 * 06/giu/2011
 */
public class UnsupportedFieldTypeException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 7545134408251960837L;

    /**
     * 
     */
    public UnsupportedFieldTypeException() {
    }

    /**
     * @param message
     */
    public UnsupportedFieldTypeException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public UnsupportedFieldTypeException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public UnsupportedFieldTypeException(String message, Throwable cause) {
        super(message, cause);
    }

}
