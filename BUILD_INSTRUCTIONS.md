# 🚀 Instructions de Build - SmartAgenda Notif

## Prérequis

- **Android Studio** : Version Arctic Fox ou supérieure
- **JDK** : Version 11 ou supérieure
- **Android SDK** : API Level 24 minimum (Android 7.0)

## Étapes

### 1. Ouvrir le projet

```bash
# Cloner le repository
git clone https://github.com/ronaldbernardon/SmartAgendaNotif.git
cd SmartAgendaNotif

# Ouvrir avec Android Studio
# File → Open → Sélectionner le dossier SmartAgendaNotif
```

### 2. Synchroniser Gradle

Android Studio va automatiquement détecter le projet et proposer de synchroniser Gradle.
Cliquer sur "Sync Now" si demandé.

### 3. Configurer l'URL du serveur

Éditer `app/src/main/java/com/smartagenda/notif/ApiConfig.kt` :

```kotlin
const val DEFAULT_SERVER_URL = "http://VOTRE_IP:8086"
```

### 4. Builder l'APK

**Option A : Debug APK (pour tests)**

```
Build → Build Bundle(s) / APK(s) → Build APK(s)
```

L'APK sera généré dans : `app/build/outputs/apk/debug/app-debug.apk`

**Option B : Release APK (pour production)**

1. Créer un keystore (première fois seulement) :
   ```
   Build → Generate Signed Bundle / APK → APK
   → Create new... → Remplir les informations
   ```

2. Signer et builder :
   ```
   Build → Generate Signed Bundle / APK → APK
   → Sélectionner le keystore → Release
   ```

### 5. Installer sur le téléphone

**Via ADB :**
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

**Via transfert manuel :**
1. Copier l'APK sur le téléphone
2. Ouvrir avec le gestionnaire de fichiers
3. Installer (autoriser les sources inconnues si nécessaire)

## Vérification

Après installation :
1. Ouvrir l'app
2. Aller dans Paramètres
3. Configurer l'URL du serveur
4. Tester la connexion avec le FAB (bouton flottant)
5. Si connexion OK : Démarrer le service

## Dépannage

### Erreur Gradle Sync

```bash
./gradlew clean
./gradlew build
```

### Erreur de permissions

Vérifier que toutes les permissions sont accordées dans les paramètres Android.

### L'app crash au démarrage

Vérifier les logs :
```bash
adb logcat | grep SmartAgenda
```

## 🎉 Bon build !
