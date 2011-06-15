Android SQL Helper - Code generation tool for SQLite in Android
===============================================================

(C) 2011 Massimo Gaddini ( massimo.gaddini[at]gmail.com )


Overview
--------

Android SQL Helper is a Java tool that generates db adapter and some other classes for the 
SQLite interaction under Android environment.

Android SQL Helper is inspired by [Android sql lite helper](http://github.com/fedepaol/Android-sql-lite-helper). 
The big difference is that the Android SQL Helper takes the tables and columns type settings directly from the 
application Java classes used for persistent data.


License Notice
--------------

Android SQL Helper is licensed under [Apache License v. 2.0] (http://www.apache.org/licenses/LICENSE-2.0.txt)


Requirements
------------

JDK >= 1.6


Getting Started
---------------

Briefly, to use the Android SQL Helper you must:

1. Define persistent Java Beans
2. Define generation properties
3. Compile the classes with the Android SQL Helper's jar in the project build path



**Persistent Java Beans**

The Android SQL Helper does a direct mapping between Java Bean and SQL tables and between Java Bean's Fields and SQL table's columns.

In order to define a Java Bean as persistent you must annotate the class with the PersistentEntity annotation and annotate some or all 
fields with the annotation PersistentField.

For default the class name and field names will be used for the corresponding SQL table name and columns.
The user can specify custom names with annotation's properties tableName (for PersistentEntity annotation) and columnName 
(for PersistentField annotation).

At the moment only the Java base types are supported: primitive types, String, Date.

See annotation's javadoc for details.


**Generation properties**

The Android SQL Helper load a properties file `schema.properties` where the user should specify some settings as the output folder, 
the java package for the generated classes, etc.


**Compiling and generating**

To generate the helper classes just compile the persistent beans with the `androidsqlhelper.jar` in the build path.
The generation process is executed for each compilation. So the user can choose if generate the helper classes from 
a standalone project or from the android application project itself. 

The generation process works only if the compilation is done with Java 6 or upper.
