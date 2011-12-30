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
package com.sgxmobileapps.androidsqlhelper.processor;

import com.sgxmobileapps.androidsqlhelper.annotation.PersistentEntity;
import com.sgxmobileapps.androidsqlhelper.annotation.PersistentField;
import com.sgxmobileapps.androidsqlhelper.generator.CodeGenerationException;
import com.sgxmobileapps.androidsqlhelper.generator.CodeGenerator;
import com.sgxmobileapps.androidsqlhelper.generator.CodeGeneratorFactory;
import com.sgxmobileapps.androidsqlhelper.processor.model.Field;
import com.sgxmobileapps.androidsqlhelper.processor.model.Table;
import com.sgxmobileapps.androidsqlhelper.processor.model.Schema;
import com.sgxmobileapps.androidsqlhelper.processor.model.UnsupportedFieldTypeException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.StandardLocation;


/**
 * Processor of annotation {@link PersistentEntity} and {@link PersistentField}.
 * The processor is invoked at compile time from javac tool, it parse the annotated 
 * classes and fields and generates a schema model and then the source file 
 * for SQLite support on Android.
 * Processor settings are read from a properties file named "schema.properties".
 * 
 * @author Massimo Gaddini
 *
 */
@SupportedAnnotationTypes(
{
    "com.sgxmobileapps.androidsqlhelper.annotation.PersistentEntity",
    "com.sgxmobileapps.androidsqlhelper.annotation.PersistentField"
}
)
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class AnnotationProcessor extends AbstractProcessor {
    
    protected Schema mSchema = new Schema();
    protected Properties mProperties = new Properties();
    
    /**
     * 
     */
    public AnnotationProcessor() {
    }
    
    protected void printMessage(Kind kind, String format, Object...arguments){
        String message = String.format(format, arguments);
        processingEnv.getMessager().printMessage(kind, message);
    }
    
    protected void printMessage(Kind kind, Element element, String format, Object...arguments){
        String message = String.format(format, arguments);
        processingEnv.getMessager().printMessage(kind, message, element);
    }
    
    protected void printMessage(Kind kind, Element element, AnnotationMirror annotation, String format, Object...arguments){
        String message = String.format(format, arguments);
        processingEnv.getMessager().printMessage(kind, message, element, annotation);
    }
    
    protected void printMessage(Kind kind, Element element, AnnotationMirror annotation, AnnotationValue annotationValue, String format, Object...arguments){
        String message = String.format(format, arguments);
        processingEnv.getMessager().printMessage(kind, message, element, annotation, annotationValue);
    }
    
    protected void processEntity(Element entity, Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        printMessage(Kind.NOTE, "Processing entity: %s", entity);
                
        PersistentEntity entityAnnotation = entity.getAnnotation(PersistentEntity.class);
        if (entityAnnotation == null)
            return;
        
        printMessage(Kind.NOTE, "Entity annotation: %s", entityAnnotation);
        
        Table table = Table.buildTable(entityAnnotation, entity, mSchema);
        mSchema.addTable(table);
        
        Iterator<? extends Element> entityElements = entity.getEnclosedElements().iterator();
        while (entityElements.hasNext()) {
            Element memberElement = entityElements.next();
            
            if (memberElement.getKind().isField()) {
                PersistentField fieldAnnotation = memberElement.getAnnotation(PersistentField.class);
                if (fieldAnnotation != null) {
                    printMessage(Kind.NOTE, "Field annotation: %s", fieldAnnotation);
                    
                    try{
                        Field field = Field.buildField(fieldAnnotation, memberElement, table);
                        table.addField(field);
                    } catch(UnsupportedFieldTypeException ufte){
                        printMessage(Kind.ERROR, memberElement, "Failed processing entity %s, field %s: %s", 
                                entity, memberElement, ufte.getMessage());
                    }
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see javax.annotation.processing.AbstractProcessor#process(java.util.Set, javax.annotation.processing.RoundEnvironment)
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    	InputStream is = null;
    	try {
        	FileObject fo = processingEnv.getFiler().getResource(StandardLocation.CLASS_PATH, "", Schema.SCHEMA_PROPERTIES_FILE);
        	if (fo != null) {
        		is = fo.openInputStream();
        	}
        	printMessage(Kind.NOTE, "Opened properties file from class path");
        } catch (Exception e) {
            printMessage(Kind.WARNING, "Opening properties file from class path failed: %s", e.getMessage());
        }
    	
    	if (is == null) {
        	try {
                is = new FileInputStream(Schema.SCHEMA_PROPERTIES_FILE);
                printMessage(Kind.NOTE, "Opened properties file from working dir");
            } catch (Exception e) {
                printMessage(Kind.ERROR, "Opening properties file from working dir failed: %s", e.getMessage());
                return false;
            }
    	}
        
    	try {
        	mProperties.load(is);
            mSchema.loadSchemaPropeties(mProperties);
        } catch (IOException e) {
            printMessage(Kind.ERROR, "Reading properties file failed: %s", e.getMessage());
            return false;
        }
        
        boolean found = false;
        Iterator<? extends Element> it = roundEnv.getElementsAnnotatedWith(PersistentEntity.class).iterator();
        while (it.hasNext()) {
            Element entity = (Element) it.next();
            processEntity(entity, annotations, roundEnv);
            found = true;
        }
        
        if (found) {
            printMessage(Kind.NOTE, "Resulting Schema: %s", mSchema);
            
            try {
                CodeGenerator generator = CodeGeneratorFactory.getCodeGenerator();
                generator.generate(mSchema, processingEnv.getFiler());
            } catch (CodeGenerationException e) {
                printMessage(Kind.ERROR, "Code generation failed: %s", e.getMessage());
            } 
        }

        return true;
    }
}
