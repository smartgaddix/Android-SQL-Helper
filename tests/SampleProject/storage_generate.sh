#!/bin/bash

echo 'Cleaning bin ang gen folders'

rm -Rfv gen/*
rm -Rfv bin/*

echo 'Compiling...'

find src -name '*.java' > classes

javac @options @classes 

echo '...done'