#!/bin/bash
# Script de build APK en ligne de commande pour SmartAgenda Notif
# Sans Android Studio nécessaire

set -e  # Arrêter en cas d'erreur

echo "🚀 BUILD APK - SmartAgenda Notif"
echo "=================================="
echo ""

# Vérifier si on est dans le bon répertoire
if [ ! -f "settings.gradle" ]; then
    echo "❌ Erreur: Ce script doit être exécuté depuis le répertoire racine du projet"
    echo "   Utilisez: cd SmartAgendaNotif && ./build_apk.sh"
    exit 1
fi

# Vérifier si Gradle Wrapper existe
if [ ! -f "gradlew" ]; then
    echo "📥 Téléchargement de Gradle Wrapper..."
    gradle wrapper --gradle-version 8.0
fi

# Rendre le wrapper exécutable
chmod +x gradlew

# Nettoyer les builds précédents
echo "🧹 Nettoyage des builds précédents..."
./gradlew clean

# Construire l'APK de debug
echo ""
echo "🔨 Construction de l'APK debug..."
echo "   (Cela peut prendre quelques minutes...)"
echo ""
./gradlew assembleDebug

# Vérifier si le build a réussi
if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    echo ""
    echo "✅ BUILD RÉUSSI !"
    echo "=================================="
    echo ""
    echo "📱 APK généré :"
    echo "   $(pwd)/app/build/outputs/apk/debug/app-debug.apk"
    echo ""
    echo "📊 Taille :"
    ls -lh app/build/outputs/apk/debug/app-debug.apk | awk '{print "   " $5}'
    echo ""
    echo "📋 Pour installer sur votre téléphone :"
    echo "   Option 1 (ADB) : adb install app/build/outputs/apk/debug/app-debug.apk"
    echo "   Option 2 (Manuel) : Copier l'APK sur le téléphone et l'installer"
    echo ""
else
    echo ""
    echo "❌ ERREUR : L'APK n'a pas été généré"
    echo "   Vérifiez les logs ci-dessus pour plus de détails"
    exit 1
fi
