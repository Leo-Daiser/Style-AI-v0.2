# StyleAI UX Prototype & Web Simulator

Welcome to the **StyleAI UX Prototype & Specification Dashboard**. This repository is configured strictly as a high-fidelity interactive web-based simulator, serving as a playground for copywriting, screen flow validation, structural interaction, and hand-off specs for the upcoming native Android app launch.

> [!IMPORTANT]
> **Primary Repository Scope:** 
> This is a **Vite + React + Tailwind CSS** prototype. It is **NOT** a compiled native Android project. There are no direct Gradle files, Kotlin compilations, or `.apk` build outputs generated in this repository. 
> All Jetpack Compose Kotlin structures (managed under files like `/src/kotlinFiles.ts`) are stored purely as **reference blueprints and specifications** to instruct native Android engineers.

---

## 💎 Core Values & Design Direction

StyleAI is a **privacy-first wardrobe and shopping decision assistant** designed to help users solve actual, real-world utility gaps without overwhelming them with unnecessary AI slop, cloud syncing, or biometric violations.

### 🛡️ What StyleAI Is:
1. **Purchase Decision Support:** An immediate, logical checklist flow answering the critical question: *"Should I buy this?"* before it ends up forgotten in a closet.
2. **Wardrobe Utility Maximizer:** Answers *"What should I wear?"* utilizing saved logic, capsule blueprints, and deterministic style guidelines.
3. **Optional Style Profiler:** Color palette matching, body-shaming-free silhouette advice, and capsule suggestions are fully optional. Users can complete direct decision checks immediately without uploading photos.
4. **Strict Privacy MVP:** Features zero internet configurations, zero telemetry logging, and zero permanent face/body photo captures. All pixel scaling and palette parses occur Transiently in-memory.

### 🚫 What StyleAI is NOT:
* **NOT** a generic AI chat box or LLM prompt wrapper.
* **NOT** an unrestricted image generator with free-text entries.
* **NOT** a biometric virtual try-on app or virtual model editor.
* **NOT** an adult, lingerie, or swimwear modeling sandbox.

---

## 🛠️ Project Structure

* **`/src/App.tsx`:** The core UX Engine. It renders the high-fidelity responsive layout simulating a physical smartphone overlay with real-time state manipulation and bilingual Russian-English support.
* **`/src/kotlinFiles.ts`:** Reference Kotlin snippets (Jetpack Compose, MVVM ViewModels, Domain Models) representing the layout and logic mapping for the upcoming Android client.
* **`/docs/`:** Developer UX Specifications and production handoff blueprints:
  * **`UX_FLOW.md`**: Navigation hierarchy, screen state contracts.
  * **`ANDROID_HANDOFF.md`**: Native packages, Compose contracts, and business logic.
  * **`LOCALIZATION_TABLE.md`**: Full bilingual translations directory.
  * **`SAFETY_PRIVACY_SPEC.md`**: Guardrails, offline policies, and validation protocols.
  * **`DESIGN_SYSTEM.md`**: Material guidelines, custom typography guidelines.

---

## 🌐 Running the Web UX Simulator

Install dependencies and boot up the design preview server locally to iterate on the screens:

```bash
# Install required modules
npm install

# Run the local Vite preview server (served on port 3000)
npm run dev
```

Open the local address in your web browser of choice. Use the responsive controls to toggle the language between **English** and **Русский**, switch color palettes, and run modular buying checkpoints.
