# 🔨 Build APK en ligne de commande (Sans Android Studio)

## Prérequis

### 1. Java JDK 11 ou supérieur

**Vérifier si Java est installé :**
```bash
java -version
```

**Si pas installé :**

**Sur Fedora/RHEL :**
```bash
sudo dnf install java-11-openjdk-devel
```

**Sur Debian/Ubuntu :**
```bash
sudo apt install openjdk-11-jdk
```

**Sur Windows :**
Télécharger depuis : https://adoptium.net/

### 2. Android SDK Command Line Tools

**Option A : Installation manuelle**

1. Télécharger : https://developer.android.com/studio#command-tools
2. Extraire dans `~/android-sdk`
3. Configurer les variables d'environnement :

```bash
export ANDROID_HOME=~/android-sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin
export PATH=$PATH:$ANDROID_HOME/platform-tools
```

4. Installer les packages nécessaires :
```bash
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
```

**Option B : Utiliser Gradle seulement (plus simple)**

Gradle peut télécharger automatiquement les dépendances nécessaires.
Il suffit d'avoir Java installé !

---

## 🚀 Build rapide (Méthode recommandée)

### 1. Cloner le projet

```bash
git clone https://github.com/ronaldbernardon/SmartAgendaNotif.git
cd SmartAgendaNotif
```

### 2. Configurer l'URL du serveur

Éditer `app/src/main/java/com/smartagenda/notif/ApiConfig.kt` :

```kotlin
const val DEFAULT_SERVER_URL = "http://192.168.X.X:8086"  // Votre IP
```

### 3. Lancer le build

```bash
./build_apk.sh
```

C'est tout ! L'APK sera généré dans `app/build/outputs/apk/debug/app-debug.apk`

---

## 🔧 Build manuel (étape par étape)

### 1. Télécharger Gradle Wrapper (si nécessaire)

```bash
gradle wrapper --gradle-version 8.0
chmod +x gradlew
```

### 2. Nettoyer les builds précédents

```bash
./gradlew clean
```

### 3. Builder l'APK debug

```bash
./gradlew assembleDebug
```

### 4. Localiser l'APK généré

```bash
ls -lh app/build/outputs/apk/debug/app-debug.apk
```

---

## 📦 Types de build

### Debug APK (pour tests)

```bash
./gradlew assembleDebug
```

- Non optimisé
- Non signé (signature automatique debug)
- Plus gros
- ✅ Parfait pour tester

### Release APK (pour production)

```bash
# 1. Créer un keystore (première fois seulement)
keytool -genkey -v -keystore smartagenda.keystore \
  -alias smartagenda -keyalg RSA -keysize 2048 -validity 10000

# 2. Créer gradle.properties local
echo "RELEASE_STORE_FILE=../smartagenda.keystore" > gradle.properties
echo "RELEASE_STORE_PASSWORD=votre_password" >> gradle.properties
echo "RELEASE_KEY_ALIAS=smartagenda" >> gradle.properties
echo "RELEASE_KEY_PASSWORD=votre_password" >> gradle.properties

# 3. Builder
./gradlew assembleRelease
```

APK généré dans : `app/build/outputs/apk/release/app-release.apk`

---

## 📱 Installation sur le téléphone

### Méthode 1 : Via ADB

**Installer ADB :**

**Fedora :**
```bash
sudo dnf install android-tools
```

**Debian/Ubuntu :**
```bash
sudo apt install adb
```

**Activer le débogage USB sur le téléphone :**
1. Paramètres → À propos du téléphone
2. Tapoter 7 fois sur "Numéro de build"
3. Retour → Options développeur
4. Activer "Débogage USB"

**Installer l'APK :**
```bash
# Connecter le téléphone en USB
adb devices  # Vérifier que le téléphone est détecté

# Installer
adb install app/build/outputs/apk/debug/app-debug.apk

# Ou pour forcer la réinstallation
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Méthode 2 : Transfert manuel

1. Copier l'APK sur le téléphone (USB, email, cloud...)
2. Ouvrir le fichier avec le gestionnaire de fichiers
3. Autoriser l'installation depuis des sources inconnues si demandé
4. Installer

### Méthode 3 : Via réseau (ADB over WiFi)

```bash
# 1. Connecter le téléphone en USB une fois
adb tcpip 5555

# 2. Déconnecter l'USB et noter l'IP du téléphone
# (Paramètres → À propos → État → Adresse IP)

# 3. Connecter via WiFi
adb connect 192.168.X.X:5555

# 4. Installer
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## 🐛 Dépannage

### Erreur : "ANDROID_HOME not set"

Ajouter dans `~/.bashrc` ou `~/.zshrc` :
```bash
export ANDROID_HOME=~/android-sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin
```

Puis :
```bash
source ~/.bashrc
```

### Erreur : "Could not find or load main class org.gradle.wrapper.GradleWrapperMain"

Télécharger le wrapper :
```bash
gradle wrapper
```

### Erreur : "SDK location not found"

Créer `local.properties` :
```bash
echo "sdk.dir=$HOME/android-sdk" > local.properties
```

Ou laisser Gradle télécharger automatiquement.

### Build très lent

Activer le daemon Gradle :
```bash
echo "org.gradle.daemon=true" >> gradle.properties
echo "org.gradle.parallel=true" >> gradle.properties
```

### Erreur de mémoire

Augmenter la mémoire :
```bash
export GRADLE_OPTS="-Xmx2048m -XX:MaxMetaspaceSize=512m"
```

---

## 📊 Optimisations

### Réduire la taille de l'APK

Éditer `app/build.gradle` :

```gradle
android {
    buildTypes {
        release {
            minifyEnabled true
            shrinkResources true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt')
        }
    }
}
```

### Build plus rapide

```bash
# Build incrémental seulement
./gradlew assembleDebug --no-build-cache

# Utiliser tous les CPU
./gradlew assembleDebug --parallel --max-workers=4
```

---

## 🎯 Workflow complet

```bash
# 1. Cloner
git clone https://github.com/ronaldbernardon/SmartAgendaNotif.git
cd SmartAgendaNotif

# 2. Configurer l'URL
nano app/src/main/java/com/smartagenda/notif/ApiConfig.kt
# Modifier DEFAULT_SERVER_URL

# 3. Builder
./build_apk.sh

# 4. Installer
adb install app/build/outputs/apk/debug/app-debug.apk

# 5. Lancer l'app sur le téléphone
# Configurer l'URL dans les paramètres
# Tester la connexion
# Démarrer le service
```

---

## ✅ Vérification post-installation

Sur le téléphone :

1. Ouvrir **SmartAgenda Notif**
2. Aller dans **⚙️ Paramètres**
3. Vérifier/modifier l'URL
4. Enregistrer
5. Retour → Cliquer sur le **FAB** (bouton flottant)
6. Message **"✅ Connexion OK"** → tout fonctionne !
7. Cliquer sur **Démarrer**

---

## 📝 Notes

- Le build debug est suffisant pour un usage personnel
- Le build release est nécessaire pour publier sur Play Store
- Le keystore doit être conservé précieusement (impossible de mettre à jour l'app sans lui)
- L'APK debug fait environ 5-8 MB
- Le build prend 2-5 minutes la première fois (téléchargements)
- Les builds suivants sont plus rapides (cache)

---

## 🎉 Avantages du build en ligne de commande

- ✅ Pas besoin d'Android Studio (économie ~1 GB)
- ✅ Automatisable (scripts, CI/CD)
- ✅ Plus rapide que l'IDE
- ✅ Utilise moins de ressources
- ✅ Parfait pour les serveurs

**Bon build ! 🔨📱**
