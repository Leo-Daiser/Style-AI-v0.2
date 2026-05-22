# StyleAI - Private AI Style Assistant (Native Android MVP)

Welcome to **StyleAI**, a privacy-first, highly polished native Android application built with **Kotlin and Jetpack Compose**. 

The app generates personal Style Reports (color palette diagnostic, silhouettes guide, starter capsule, prioritized shopping checklist, and styling avoid notes) using secure local calculations in-memory, bypassing long-term image storage constraints.

---

## 🚀 Key Architectural Features
1. **Primary Android Native Stack**: Built entirely as a modern native Android application utilizing Jetpack Compose, the Material 3 design system, Compose Navigation, and Coroutines.
2. **Preferences DataStore**: Saves selections for user-preferred language, onboarding/consent flags, and active mock credit balances securely using Android Jetpack DataStore Preferences. No old SharedPreferences are used.
3. **Double-Localization Support**: Fully translated and localized in **English (EN)** and **Russian (RU)**.
4. **Controlled Remote Style API Safety Model**: The Remote API defines concrete schemas for restricted attributes (occasions, formality, physical palette groups). It replaces free-text notes with type-safe, non-body-shaming selectors (`StylePrefFit` enum) preventing unauthorized prompts or text injections.
5. **Credit Mechanics**: Employs an asynchronous simulated checkout screen where users purchase credits (Free starts with 3, Pro includes an extra 30, and credit pack options are available). Generative rendering is strictly restricted by debits (even for Pro) to avoid unlimited image synthesis.
6. **Privacy Safeguard Standards**: 
   - Raw photos are strictly processed in-memory and are never stored or persisted on-device or on servers.
   - Private attributes (EXIF properties, image file system addresses, personal names) are never recorded or written into local application logs.
   - Pure Offline Operation: The application has **no INTERNET permission** configured in its manifest, guaranteeing complete offline sandbox local behavior where zero styling bytes or telemetry can leave the device.
   - Includes developer-visible diagnostics checklists for compliance verifications.

---

## 🛠️ Build and Assemble Instructions

To build the native Android application from the root of the repository, execute the standard Gradle build tool using the Gradle wrapper (`./gradlew` is required):

```bash
# General cleanup of tasks and caches
./gradlew clean

# Build the native Android app package (Debug APK)
./gradlew :android:app:assembleDebug
```

The compiled package (APK) will be built at:
`android/app/build/outputs/apk/debug/app-debug.apk`

*Note: The project is configured with a real Gradle binary wrapper (Gradle 8.5) and setting files to ensure successful reproducible builds.*

---

## 📱 Project Module Directory Structure

```
.
├── android/
│   └── app/
│       ├── build.gradle.kts (App level compile configurations)
│       └── src/
│           └── main/
│               ├── AndroidManifest.xml (App entry activity and internet access intents)
│               └── java/com/example/styleai/
│                   ├── MainActivity.kt (Compose central NavHost & tab panels)
│                   ├── core/
│                   │   ├── localization/ (AppLocalization classes)
│                   │   ├── privacy/ (Safe logger rules & EXIF strip guidelines)
│                   │   └── theme/ (SoftMoss colors & display geometry)
│                   ├── data/
│                   │   ├── mock/ (MockData datasets)
│                   │   ├── remote/ (MockRemoteStyleApi)
│                   │   └── repository/ (Repository storage implementations)
│                   ├── domain/
│                   │   ├── model/ (StyleModels: user, color palettes, silhouettes)
│                   │   └── repository/ (StyleRepository & BillingRepository interfaces)
│                   └── feature/
│                       ├── history/ (Cache log list screen)
│                       ├── onboarding/ (Slides & consent guidelines screen)
│                       ├── paywall/ (Mock Checkout screen)
│                       ├── report/ (Complete styling report list screen)
│                       ├── settings/ (Language switcher & developer compliance logs)
│                       └── upload/ (Photo select, validation mechanics screen)
├── src/ (Vite React Local Simulator layout)
├── settings.gradle.kts (Kotlin Gradle subproject includes at root)
├── build.gradle.kts (Root top-level build properties)
└── gradle.properties (JVM arguments cache size attributes)
```

---

## ⚠️ MVP Sandbox Mocks and Disclaimers

StyleAI operates strictly as a **mocked MVP sandbox layout**:
- **No Real AI Calls**: Styling analyses, tone seasons, and outfit renders use pre-generated matching matrices from `MockData.kt`. No remote AI model calls are performed.
- **No Real Billing**: Checkout options (complete report, monthly Pro plan, credit packs) are simulations. Selecting options updates preferred mock credit attributes without applying actual merchant charges.
- **No User Account registrations**: User profiles and records are managed locally on the device via Preferences DataStore.
- **No Cloud Synchronization**: Saved collections or reports are only cached in local secure DataStore files.
- **Strict User Intent Boundaries**: The application stays entirely within the explicit user-approved, non-body-shaming domain boundaries. Free-form text prompting of styling requests is disabled to ensure prompt safety and clean layout preservation.
