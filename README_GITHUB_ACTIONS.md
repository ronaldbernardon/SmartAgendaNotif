# 🎉 SmartAgenda Notif - Build automatique avec GitHub Actions

## 🚀 Méthode SANS installation locale

**Vous n'avez RIEN à installer !** GitHub compile l'APK pour vous dans le cloud.

---

## 📋 Checklist rapide

### ✅ Avant de pousser sur GitHub

1. **Configurer l'URL du serveur** dans `app/src/main/java/com/smartagenda/notif/ApiConfig.kt` :
   ```kotlin
   const val DEFAULT_SERVER_URL = "http://192.168.X.X:8086"
   ```

2. **Vérifier que le workflow existe** :
   ```bash
   ls -la .github/workflows/build-apk.yml
   ```

### ✅ Pousser sur GitHub

```bash
git init
git add .
git commit -m "Initial commit - SmartAgenda Notif"
git remote add origin https://github.com/ronaldbernardon/SmartAgendaNotif.git
git branch -M main
git push -u origin main
```

### ✅ Attendre le build (2-3 minutes)

GitHub Actions va automatiquement :
- ☕ Installer Java
- 📦 Télécharger Gradle
- 🔨 Compiler l'APK
- 📤 Mettre l'APK disponible

### ✅ Télécharger l'APK

1. Aller sur : https://github.com/ronaldbernardon/SmartAgendaNotif/actions
2. Cliquer sur le dernier workflow (✅ vert)
3. Section **Artifacts** → **smartagenda-notif-debug**
4. Télécharger le ZIP
5. Extraire → vous avez votre APK !

---

## 🎯 Avantages

| Méthode | Installation locale | Temps | Complexité |
|---------|-------------------|-------|------------|
| **Android Studio** | ⚠️ 1+ GB | ⚠️ 10-15 min | ⚠️⚠️⚠️ |
| **Ligne de commande** | ⚠️ Java + SDK | ⚠️ 5-10 min | ⚠️⚠️ |
| **GitHub Actions** | ✅ Rien ! | ✅ 2-3 min | ✅ Simple |

---

## 📱 Workflow complet

```
1. Modifier ApiConfig.kt (URL serveur)
   ↓
2. git push
   ↓
3. GitHub Actions compile automatiquement (2-3 min)
   ↓
4. Télécharger l'APK depuis GitHub
   ↓
5. Installer sur le téléphone
   ↓
6. Configurer et utiliser l'app
```

---

## 🏷️ Créer des releases

Pour avoir l'APK directement en téléchargement public :

```bash
git tag v1.0.0
git push origin v1.0.0
```

L'APK sera disponible sur : https://github.com/ronaldbernardon/SmartAgendaNotif/releases

---

## 📖 Documentation complète

- **GITHUB_ACTIONS.md** - Guide complet GitHub Actions
- **BUILD_COMMAND_LINE.md** - Build en ligne de commande (si vous préférez)
- **BUILD_INSTRUCTIONS.md** - Build avec Android Studio (si vous préférez)

---

## 🎉 C'est tout !

**Aucune installation, GitHub fait tout pour vous !** ✨

