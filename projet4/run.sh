#!/bin/bash

# تعريف المسارات
JADE_LIB="/home/bsaid05/Downloads/JADE-all-4.5.0/JADE-bin-4.5.0/jade/lib/jade.jar"
CP="bin:$JADE_LIB"

echo "--- Compilation en cours... ---"
javac -d bin -cp .:$JADE_LIB src/agents/*.java

if [ $? -eq 0 ]; then
    echo "--- Lancement de JADE... ---"
    java -cp $CP jade.Boot -gui -agents "resource:agents.ResourceAgent;student1:agents.StudentAgent;student2:agents.StudentAgent;student3:agents.StudentAgent"
else
    echo "Erreur de compilation !"
fi
