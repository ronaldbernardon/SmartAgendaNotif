# 🚀 Build APK avec GitHub Actions (Sans rien installer !)

## 🎯 Concept

GitHub Actions va **builder l'APK automatiquement** sur les serveurs de GitHub.
Vous n'avez **rien à installer** sur votre machine !

---

## ✅ Avantages

- 🚫 **Aucune installation locale** (pas de Java, pas d'Android SDK, pas d'Android Studio)
- ☁️ **Build dans le cloud** (sur les serveurs GitHub)
- 🆓 **Gratuit** (2000 minutes/mois pour les repos publics)
- 📦 **APK téléchargeable** directement depuis GitHub
- 🔄 **Automatique** à chaque push
- ⚡ **Rapide** (2-3 minutes par build)

---

## 📋 Étapes

### 1. Pousser le projet sur GitHub

```bash
cd /mnt/user-data/outputs/SmartAgendaNotif

# Initialiser Git
git init

# Configurer l'URL du serveur AVANT de commit
nano app/src/main/java/com/smartagenda/notif/ApiConfig.kt
# Modifier : const val DEFAULT_SERVER_URL = "http://VOTRE_IP:8086"

# Ajouter tous les fichiers
git add .

# Premier commit
git commit -m "Initial commit - SmartAgenda Notif Android App"

# Ajouter le remote
git remote add origin https://github.com/ronaldbernardon/SmartAgendaNotif.git

# Pousser
git branch -M main
git push -u origin main
```

### 2. Activer GitHub Actions (automatique)

Dès que vous poussez, GitHub détecte le fichier `.github/workflows/build-apk.yml` et lance automatiquement le build !

### 3. Voir le build en cours

1. Aller sur : https://github.com/ronaldbernardon/SmartAgendaNotif
2. Cliquer sur l'onglet **Actions**
3. Vous verrez le build en cours : "Build APK"

### 4. Télécharger l'APK

Une fois le build terminé (2-3 minutes) :

1. Onglet **Actions**
2. Cliquer sur le dernier workflow réussi (✅ vert)
3. Descendre à la section **Artifacts**
4. Cliquer sur **smartagenda-notif-debug**
5. Un fichier ZIP sera téléchargé
6. Extraire le ZIP → vous avez votre **app-debug.apk** !

---

## 🎮 Déclencher un build manuellement

Vous pouvez aussi déclencher un build sans faire de push :

1. Aller sur l'onglet **Actions**
2. Sélectionner le workflow **Build APK**
3. Cliquer sur **Run workflow** (à droite)
4. Sélectionner la branche **main**
5. Cliquer sur **Run workflow** (vert)

---

## 🏷️ Créer une release avec l'APK

Pour avoir l'APK directement dans les releases :

```bash
# Créer un tag
git tag v1.0.0
git push origin v1.0.0
```

GitHub Actions va automatiquement :
1. Builder l'APK
2. Créer une release
3. Attacher l'APK à la release

Vous pourrez le télécharger depuis : https://github.com/ronaldbernardon/SmartAgendaNotif/releases

---

## 📊 Ce que fait GitHub Actions

Le workflow `.github/workflows/build-apk.yml` :

1. ✅ **Checkout** : Récupère votre code
2. ☕ **Install Java** : Installe JDK 11
3. 📦 **Cache Gradle** : Accélère les builds suivants
4. 🔨 **Build APK** : Compile l'application
5. 📤 **Upload APK** : Met l'APK disponible en téléchargement
6. 🏷️ **Release** : Si tag, crée une release

**Temps total : 2-4 minutes**

---

## 🔧 Personnalisation du workflow

### Build automatique seulement sur tag

Modifier `.github/workflows/build-apk.yml` :

```yaml
on:
  push:
    tags:
      - 'v*'  # Déclenche seulement sur tags v1.0.0, v1.1.0, etc.
```

### Build Release APK (signé)

Pour builder un APK signé pour le Play Store, il faut ajouter le keystore en secret GitHub.

1. **Créer un keystore localement :**
```bash
keytool -genkey -v -keystore smartagenda.keystore \
  -alias smartagenda -keyalg RSA -keysize 2048 -validity 10000
```

2. **Encoder le keystore en base64 :**
```bash
base64 smartagenda.keystore > smartagenda.keystore.b64
```

3. **Ajouter les secrets GitHub :**
   - Aller sur Settings → Secrets → Actions
   - Ajouter :
     - `KEYSTORE_FILE` : contenu du fichier .b64
     - `KEYSTORE_PASSWORD` : mot de passe du keystore
     - `KEY_ALIAS` : smartagenda
     - `KEY_PASSWORD` : mot de passe de la clé

4. **Modifier le workflow** pour assembleRelease

---

## 📱 Installer l'APK sur le téléphone

### Méthode 1 : Téléchargement direct

1. Sur le téléphone, aller sur GitHub
2. Télécharger l'APK depuis les Artifacts ou Releases
3. Installer

### Méthode 2 : Via ordinateur

1. Télécharger l'APK depuis GitHub sur l'ordinateur
2. Transférer sur le téléphone (USB, email, cloud...)
3. Installer

### Méthode 3 : Via ADB

```bash
# Télécharger l'APK depuis GitHub
unzip smartagenda-notif-debug.zip

# Installer via ADB
adb install app-debug.apk
```

---

## 🐛 Dépannage

### Le workflow ne se déclenche pas

Vérifier que le fichier est bien dans `.github/workflows/build-apk.yml`

```bash
ls -la .github/workflows/
```

### Le build échoue

1. Aller sur Actions → Cliquer sur le build raté
2. Lire les logs pour voir l'erreur
3. Les erreurs courantes :
   - Syntaxe Kotlin invalide
   - Dépendances manquantes
   - Problème dans build.gradle

### L'APK n'apparaît pas dans les Artifacts

Vérifier que le build a réussi (✅ vert). L'artifact n'est créé que si le build réussit.

### Limite de minutes dépassée

Les repos publics ont 2000 minutes/mois gratuites.
Chaque build prend ~3 minutes.
Vous pouvez faire ~666 builds/mois !

---

## 📈 Statistiques

Vous pouvez voir :
- **Nombre de builds**
- **Temps de build**
- **Minutes consommées**

Sur : https://github.com/ronaldbernardon/SmartAgendaNotif/actions

---

## 🎯 Workflow complet

```bash
# 1. Configurer l'URL dans le code
nano app/src/main/java/com/smartagenda/notif/ApiConfig.kt

# 2. Commit et push
git add .
git commit -m "Update: Configuration serveur"
git push

# 3. Attendre 2-3 minutes

# 4. Aller sur GitHub → Actions → Télécharger l'APK

# 5. Installer sur le téléphone

# C'est tout ! 🎉
```

---

## ✅ Résumé

**Sans rien installer sur votre machine** :

1. ✅ Pousser le code sur GitHub
2. ✅ GitHub compile automatiquement l'APK
3. ✅ Télécharger l'APK depuis GitHub
4. ✅ Installer sur le téléphone

**C'est magique ! ✨**

---

## 🔗 Liens utiles

- **Repository** : https://github.com/ronaldbernardon/SmartAgendaNotif
- **Actions** : https://github.com/ronaldbernardon/SmartAgendaNotif/actions
- **Releases** : https://github.com/ronaldbernardon/SmartAgendaNotif/releases

**Bon build automatique ! 🚀📱**
