#!/bin/bash
JADE_LIB="/home/bsaid05/Downloads/JADE-all-4.5.0/JADE-bin-4.5.0/jade/lib/jade.jar"

pkill -9 java 2>/dev/null
rm -rf bin/*
mkdir -p bin

echo "--- Compilation Projet 5... ---"
javac -d bin -cp .:$JADE_LIB src/agents/*.java

if [ $? -eq 0 ]; then
    echo "--- Lancement (Lamport Clock Strategy) ---"
    java -cp bin:$JADE_LIB jade.Boot -gui -agents "resource:agents.ResourceAgent;s1:agents.StudentAgent;s2:agents.StudentAgent;s3:agents.StudentAgent"
else
    echo "Erreur de compilation !"
fi
