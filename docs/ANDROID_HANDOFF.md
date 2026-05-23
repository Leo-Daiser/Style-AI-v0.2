# Android Handoff

## Build

From repository root:

```bash
./gradlew :android:app:assembleDebug
```

Windows:

```powershell
.\gradlew.bat :android:app:assembleDebug
```

The environment must provide Android SDK via `ANDROID_HOME`, `ANDROID_SDK_ROOT`, or `local.properties` with `sdk.dir=...`.

## Current Architecture

```text
android/app/src/main/java/com/example/styleai/
  MainActivity.kt
  core/
  data/
    mock/
      VisualAssets.kt
      MockData.kt
    repository/
  domain/
  feature/
    home/
    wardrobe/
    decisions/
    visualization/  # user-facing Looks tab
    profile/
    onboarding/
    upload/
    report/
    paywall/
```

## Routes

- `splash`
- `onboarding`
- `consent`
- `main`
- `upload`
- `shopping_check`
- `report_detail`
- `paywall`

## Main Tabs

`DashboardHostScreen` owns five tabs:

1. Home
2. Wardrobe
3. Decisions
4. Looks
5. Profile

Do not reintroduce History or Settings as bottom navigation tabs.

## Privacy Constraints

- No `INTERNET` permission.
- No real AI calls.
- No network calls.
- No real billing.
- No free-text prompts.
- Style profile remains optional.
- Shopping checks must work without face/body upload.

## Assets

Android drawables live in:

```text
android/app/src/main/res/drawable-nodpi/
```

Mappings live in:

```text
android/app/src/main/java/com/example/styleai/data/mock/VisualAssets.kt
```
