# StyleAI

StyleAI is a privacy-first wardrobe operating system and shopping decision assistant.

Main promise: buy fewer wrong clothes, build a stronger wardrobe, and know what to wear.

## Monorepo Structure

```text
style-ai/
  android/        Native Android MVP, Kotlin and Jetpack Compose
  web-prototype/  Local Vite React prototype for UX preview
  docs/           Product, UX, asset, Android handoff, localization, and privacy docs
```

## Product Sections

The final main navigation is:

1. Home
2. Wardrobe
3. Decisions
4. Looks
5. Profile

`Settings` lives inside Profile. `Upload`, `Report`, and `History` are not main tabs.

## Android

Build from the repository root:

```bash
./gradlew :android:app:assembleDebug
```

Windows PowerShell:

```powershell
.\gradlew.bat :android:app:assembleDebug
```

Android asset mappings are centralized in:

```text
android/app/src/main/java/com/example/styleai/data/mock/VisualAssets.kt
android/app/src/main/res/drawable-nodpi/
```

The Android MVP intentionally has no `INTERNET` permission.

## Web Prototype

Build from `web-prototype/`:

```bash
cd web-prototype
npm install
npm run build
```

Web asset mappings are centralized in:

```text
web-prototype/src/data/assets.ts
web-prototype/public/assets/
```

The web prototype is a local UI prototype. It does not require `GEMINI_API_KEY`.

## Privacy And Safety Constraints

- No real AI calls.
- No real billing.
- No network calls in the Android MVP.
- No `INTERNET` permission in Android.
- No free-text prompts for image generation or styling requests.
- Style profile is optional.
- Shopping checks work without uploading face/body photos.
- No mandatory photo upload.
- No accounts or cloud sync.

## Assets

Wardrobe product images use 1:1 composition.
Outfit boards use 4:5 composition.
Empty states use 1:1 composition.

See [docs/ASSET_MANIFEST.md](docs/ASSET_MANIFEST.md) for the complete mapping.

## Current Status

The repository contains a partially merged Android app and web prototype. The merge target is a coherent local MVP around Home, Wardrobe, Decisions, Looks, and Profile.
