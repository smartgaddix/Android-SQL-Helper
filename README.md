Android SQL Helper - Code generation tool for SQLite in Android
===============================================================

(C) 2011 Massimo Gaddini ( massimo.gaddini[at]gmail.com )


Overview
--------

Android SQL Helper is a Java tool that generates some useful Java classes for the 
SQLite interaction under Android environment.

Android SQL Helper is inspired by [Android sql lite helper](http://github.com/fedepaol/Android-sql-lite-helper). 
The big difference is that the Android SQL Helper takes the table and column type definitions directly from the 
application Java classes used to model persistent data.

License Notice
--------------

Android SQL Helper is licensed under [Apache License v. 2.0] (http://www.apache.org/licenses/LICENSE-2.0.txt)

Requirements
------------

JDK >= 1.6
Codemodel >= 2.4

See Also
--------

* [Codemodel](http://codemodel.java.net/)
* [Android sql lite helper](http://github.com/fedepaol/Android-sql-lite-helper)


Getting Started
---------------

Briefly, to use the Android SQL Helper you must:

1. Define persistent data model using JavaBeans 
2. Define generation properties
3. Compile the JavaBeans with the Android SQL Helper's jar in the project build path
4. Use the generated Java classes


### Persistent JavaBeans

The Android SQL Helper does a direct mapping between JavaBean and SQL tables and between JavaBean's fields and SQL table's columns.

In order to define a JavaBean as persistent you must annotate the class with the `PersistentEntity` annotation and annotate some or all 
fields with the annotation `PersistentField`.

In this document 'persistent entity', 'JavaBean' and 'table' are pseudonyms like 'persistent field', 'field' and 'column'.

For default the class name and field names will be used for the corresponding SQL table name and columns.
The user can specify custom names with annotation's property `tableName` (for PersistentEntity annotation) and `columnName` 
(for PersistentField annotation).

At the moment only few Java types are supported: primitive types, String, Date.
Following is the mapping between Java and SQL types adopted by the Android SQL Helper:

<table border="1" width="500" align="center">
    <tr align="center" bgcolor="#00F1A0">
        <th>Java</th>
        <th width="20%">SQL</th>
        <th width="50%">Note</th>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>int/java.lang.Integer</td>
        <td>INTEGER</td>
        <td></td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>long/java.lang.Long</td>
        <td>INTEGER</td>
        <td></td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>byte/java.lang.Byte</td>
        <td>INTEGER</td>
        <td></td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>short/java.lang.Short</td>
        <td>INTEGER</td>
        <td></td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>boolean/java.lang.Boolean</td>
        <td>INTEGER</td>
        <td>0: false, >0: true</td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>double/java.lang.Double</td>
        <td>REAL</td>
        <td></td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>float/java.lang.Float</td>
        <td>REAL</td>
        <td></td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>java.lang.String</td>
        <td>TEXT</td>
        <td></td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>java.lang.CharSequence</td>
        <td>TEXT</td>
        <td></td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>java.util.Date</td>
        <td>INTEGER</td>
        <td>Number of milliseconds since Jan. 1, 1970, midnight GMT as returned by method java.util.Date.getTime()</td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>java.util.GregorianCalendar</td>
        <td>INTEGER</td>
        <td>Number of milliseconds since Jan. 1, 1970, midnight GMT as returned by method java.util.Calendar.getTimeInMillis()</td>
    </tr>
</table>

See annotation's javadoc for details.

### Generated Java classes

The tool generates two main Java class:

* a `DbMetadata` class
* a `DbAdapter` class

#### DbMetadata class

The `DbMetadata` class contains the set of common constants of the Db schema as he Db version and Db name. Besides these the `DbMetadata`
class contains one inner class for each persistent entity with table specific constants like column names and index, default order. 
The names of inner classes are the names of the persistent entities.

#### DbAdapter class

The `DbAdapter` class is the most important class and manages creation and upgrade of the database and provides the methods to manipulate the data. 
For each persistent entity, it contains:

* the create/drop table statements
* get record given the id (if present) or given the primary key (if specified)
* get all records as JavaBean array or as Cursor
* add/update/delete record given the id (if present) or given the primary key (if specified)
* delete all records

Moreover it contains the methods for open/close the database and a method invoked in case of upgrade of the database 
and useful for make some upgrade operations. The method is the following:

`public Boolean onDatastoreUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)`

The default upgrade process drops all tables and creates all the new tables. 

### Generation properties

The Android SQL Helper loads a properties file `schema.properties` where the user should specify some settings as the output java package 
for the generated classes, etc.

The properties file must be placed in one of the following two places:

* working directory of the compiler (e.g. project folder)
* in the class path (e.g. with the source to compile) with empty package

The tool search the properties file first in the class path then in the working directory of the compiler.
Following are listed the recognized properties and their meaning:


<table border="1" width="700" align="center">
    <tr align="center" bgcolor="#00F1A0">
        <th>Property</th>
        <th width="40%">Description</th>
        <th>Default</th>
        <th width="40%">Example</th>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>package</td>
        <td>Java package of generated classes</td>
        <td>Empty</td>
        <td>package = com.mypackage</td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>dbadapterclassname</td>
        <td>Java class name of adapter class</td>
        <td>"DbAdapter"</td>
        <td>dbadapterclassname = DbAdapter</td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>metadataclassname</td>
        <td>Java class name of metadata class</td>
        <td>"DbMetadata"</td>
        <td>metadataclassname = DbMetadata</td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>dbname</td>
        <td>Name of the db file saved by android application</td>
        <td>"App.db"</td>
        <td>dbname = test.db</td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>dbversion</td>
        <td>Version number of the db schema. The version can be used to do same work during the upgrade process of the db schema. Must be a number</td>
        <td>1</td>
        <td>dbversion=1</td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>author</td>
        <td>Name of the author of the source file and inserted in the @author java doc tag</td>
        <td>Empty</td>
        <td>author=myname</td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>license</td>
        <td>Custom short string inserted as comment at the top of each generated file</td>
        <td>Empty</td>
        <td>license=Short license information</td>
    </tr>
    <tr align="center" bgcolor="#FFFFA0">
        <td>licensefile</td>
        <td>File name of the text file to be inserted at the top of each generated file. This property has higher priority of the 'license' property</td>
        <td>Empty</td>
        <td>licensefile=LICENSEHEADER</td>
    </tr>
</table>

Following is an example of `schema.properties` file:

`package = com.mypackage`<br>
`dbadapterclassname = DbAdapter`<br>
`metadataclassname = DbMetadata`<br>
`dbname = App.db`<br>
`licensefile = LICENSEHEADER`<br>
`author = myname`<br>
`dbversion = 1`<br>


### Compiling and generating

To generate the helper classes just compile the persistent beans with the `androidsqlhelper.jar`, `android.jar` and `codemodel-2.5-SNAPSHOT.jar` 
in the class path.
The generation process is executed for each compilation. The user can choose to generate the helper classes from 
a standalone project or from the Android application project itself using an Ant script.

The generation process works only if the compilation is done with Java 6 or upper.


### Use of generated classes

->>>>>>> jar solo con annotations
->>>>>>> Descrizione delle classi metadata e dbadapter
->>>>>>> Descrizione metodi generating
->>>>>>> descrizione metodo per upgrade
->>>>>>> 
->>>>>>> Estensione se modifica o aggiunta