#!/bin/bash

echo 'Cleaning src ang bin folders'

find -E src -regex '.*/storage/.*' -name '*.java' -exec rm -v -- {} +
find -E bin/classes -regex '.*/storage/.*' -name '*.class' -exec rm -v -- {} +
find -E bin/classes -regex '.*/entities/.*' -name '*.class' -exec rm -v -- {} +

echo 'Compiling...'

find -E src -regex '.*/entities/.*' -name '*.java' > classes

javac @options @classes 

echo '...done'