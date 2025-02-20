#!/bin/bash 

javac HelbAquarium.java
java HelbAquarium
find . -type f -name "*.class" -delete
