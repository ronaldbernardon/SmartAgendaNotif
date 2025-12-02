# 📱 SmartAgenda Notif - Application Android

Application Android pour recevoir les notifications de SmartAgenda via votre réseau VPN interne.

## ✨ Fonctionnalités

- 🔔 **Notifications en temps réel** depuis SmartAgenda
- 🚀 **Démarrage automatique** au redémarrage du téléphone
- 🌙 **Mode nuit automatique** (22h-7h) - Pas de polling la nuit
- 📱 **Notifications full-screen** pour les événements importants
- ⏰ **Actions rapides** : Snooze (10 min) et Marquer comme terminé
- 🎨 **Couleurs par catégorie** : Travail, Personnel, Réunion, etc.
- 🔒 **100% sécurisé** : Communication uniquement via VPN interne

---

## 🛠️ Installation et Configuration

### Prérequis

- **Android Studio** : [Télécharger ici](https://developer.android.com/studio)
- **Téléphone Android** : Version 7.0 (API 24) minimum
- **VPN** configuré sur le téléphone pour accéder à votre serveur

### 1. Cloner le projet

```bash
git clone https://github.com/ronaldbernardon/SmartAgendaNotif.git
cd SmartAgendaNotif
```

### 2. Ouvrir dans Android Studio

1. Lancer Android Studio
2. File → Open → Sélectionner le dossier `SmartAgendaNotif`
3. Attendre la synchronisation Gradle (peut prendre quelques minutes)

### 3. Configurer l'URL du serveur

**Option A : Modifier directement dans le code**

Éditer `app/src/main/java/com/smartagenda/notif/ApiConfig.kt` :

```kotlin
const val SERVER_URL = "http://192.168.1.100:8086"  // Votre IP
```

**Option B : Via l'interface de l'app** (après installation)

L'app permet de configurer l'URL dans les paramètres.

### 4. Builder l'APK

**Debug APK (pour tests) :**
```
Build → Build Bundle(s) / APK(s) → Build APK(s)
```
APK généré dans : `app/build/outputs/apk/debug/app-debug.apk`

**Release APK (pour production) :**
```
Build → Generate Signed Bundle / APK
```
Suivre les étapes pour créer un keystore et signer l'APK.

### 5. Installer sur votre téléphone

**Via ADB :**
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

**Via transfert manuel :**
1. Copier l'APK sur le téléphone
2. Ouvrir avec le gestionnaire de fichiers
3. Installer (autoriser les sources inconnues si nécessaire)

---

## 📋 Configuration de l'app

### Première utilisation

1. Ouvrir l'app **SmartAgenda Notif**
2. Entrer l'URL de votre serveur : `http://votre-ip:8086`
3. Tester la connexion (bouton "Tester")
4. Activer les notifications

### Paramètres disponibles

- **URL du serveur** : Adresse de SmartAgenda
- **Intervalle de polling** : 30, 60, 120 secondes
- **Mode nuit** : Activer/désactiver (22h-7h par défaut)
- **Démarrage auto** : Lancer au boot du téléphone

---

## 🔧 Architecture de l'app

```
SmartAgendaNotif/
├── MainActivity.kt              # Écran principal
├── NotificationService.kt       # Service de polling
├── BootReceiver.kt             # Démarrage automatique
├── NotificationHelper.kt        # Gestion des notifications
├── FullScreenNotificationActivity.kt  # Pop-up plein écran
├── SettingsActivity.kt         # Paramètres
├── ApiConfig.kt                # Configuration API
└── models/
    └── SmartAgendaEvent.kt     # Modèle de données
```

---

## 🌙 Mode Nuit

Par défaut, l'app ne vérifie pas les notifications entre **22h et 7h** pour économiser la batterie.

Pour modifier les horaires, éditer `NotificationService.kt` :

```kotlin
private fun shouldCheckNotifications(): Boolean {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    
    // Modifier les heures ici
    return hour in 7..21  // 7h à 21h
}
```

---

## 🔔 Types de notifications

### Notification normale
- Icône dans la barre
- Son et vibration
- Actions rapides disponibles

### Notification full-screen
- Pop-up en plein écran
- S'affiche même téléphone verrouillé
- Allume l'écran automatiquement
- Pour les événements **imminents** (dans moins de 5 minutes)

---

## 🎨 Personnalisation

### Couleurs par catégorie

Éditer `NotificationHelper.kt` :

```kotlin
private fun getCategoryColor(category: String): Int {
    return when (category) {
        "Travail" -> Color.parseColor("#3498db")      // Bleu
        "Personnel" -> Color.parseColor("#27ae60")    // Vert
        "Réunion" -> Color.parseColor("#9b59b6")      // Violet
        "Loisirs" -> Color.parseColor("#f39c12")      // Orange
        "Santé" -> Color.parseColor("#e74c3c")        // Rouge
        else -> Color.parseColor("#3498db")
    }
}
```

### Vibration

Modifier le pattern de vibration dans `NotificationHelper.kt` :

```kotlin
vibrationPattern = longArrayOf(0, 500, 200, 500, 200, 500)
// Format : [délai, vibration, pause, vibration, pause, ...]
```

---

## 🐛 Dépannage

### L'app ne reçoit pas de notifications

1. **Vérifier la connexion VPN** : Votre téléphone doit être connecté au VPN
2. **Tester l'URL** : Dans l'app, aller dans Paramètres → Tester la connexion
3. **Vérifier les logs** :
   ```bash
   adb logcat | grep SmartAgenda
   ```

### Les notifications ne s'affichent pas

1. **Autorisations** : Paramètres Android → Apps → SmartAgenda Notif → Autorisations
2. **Canal de notification** : Vérifier que le canal n'est pas silencieux
3. **Batterie** : Désactiver l'optimisation de batterie pour l'app

### L'app ne démarre pas au boot

1. **Permission** : Vérifier que `RECEIVE_BOOT_COMPLETED` est accordée
2. **Batterie** : Désactiver l'optimisation de batterie
3. **Constructeur** : Sur certains téléphones (Xiaomi, Huawei), autoriser le démarrage auto

---

## 📱 Permissions requises

```xml
<!-- Connexion réseau -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<!-- Notifications -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.USE_FULL_SCREEN_INTENT" />

<!-- Démarrage auto -->
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

<!-- Foreground service -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
```

---

## 🔒 Sécurité

- ✅ **Pas d'authentification** : L'app communique uniquement via votre VPN privé
- ✅ **Pas de stockage sensible** : Aucune donnée personnelle stockée
- ✅ **Connexion locale** : Pas de communication avec Internet
- ✅ **Open Source** : Code disponible sur GitHub

---

## 🚀 Prochaines améliorations

- [ ] WebSocket pour push en temps réel (au lieu du polling)
- [ ] Historique des notifications
- [ ] Widget Android
- [ ] Support des thèmes sombre/clair
- [ ] Synchronisation avec le calendrier Android

---

## 📜 Licence

Ce projet est fourni tel quel pour un usage personnel.

---

## 🆘 Support

Pour toute question ou problème :
- GitHub Issues : https://github.com/ronaldbernardon/SmartAgendaNotif/issues
- Vérifier les logs : `adb logcat | grep SmartAgenda`

---

## 🎉 Remerciements

Développé pour fonctionner avec **SmartAgenda Pro** - Agenda web avec notifications Android.

**Bon développement ! 📱✨**
