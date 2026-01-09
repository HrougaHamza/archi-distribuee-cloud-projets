#!/bin/bash

# المسار ديال الـ JADE (تأكد من أنه صحيح في جهازك)
JADE_LIB="/home/bsaid05/Downloads/JADE-all-4.5.0/JADE-bin-4.5.0/jade/lib/jade.jar"

# 1. مسح الـ bin باش نكريريو كود جديد
rm -rf bin/*
mkdir -p bin

echo "--- Compilation du Projet 3... ---"
# 2. الكومبيلاسيون (رد البال لـ -d bin)
javac -d bin -cp .:$JADE_LIB src/agents/ResourceAgent.java

if [ $? -eq 0 ]; then
    echo "--- Lancement de JADE (Projet 3) ---"
    # 3. التشغيل مع تحديد الـ package (agents.ResourceAgent)
    java -cp bin:$JADE_LIB jade.Boot -gui -agents "resource:agents.ResourceAgent"
else
    echo "Erreur de compilation ! Vérifie le code source."
fi
