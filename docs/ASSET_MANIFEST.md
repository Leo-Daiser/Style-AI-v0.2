# StyleAI Asset Manifest

Central manifest for Android and web visual assets.

## Naming Rules

- Web: lowercase kebab-case, stored under `web-prototype/public/assets/`.
- Android: lowercase snake_case, stored under `android/app/src/main/res/drawable-nodpi/`.
- Wardrobe cutouts: 1:1, `ContentScale.Fit`.
- Outfit boards: 4:5, `ContentScale.Crop` or `Fit` depending on composition.
- Empty states: 1:1.

## Wardrobe Assets

| ID | Web path | Android drawable |
|---|---|---|
| cream-structured-blazer | `/assets/wardrobe/cream-structured-blazer.webp` | `R.drawable.cream_structured_blazer` |
| white-cotton-shirt | `/assets/wardrobe/white-cotton-shirt.webp` | `R.drawable.white_cotton_shirt` |
| dark-blue-straight-jeans | `/assets/wardrobe/dark-blue-straight-jeans.webp` | `R.drawable.dark_blue_straight_jeans` |
| beige-tailored-trousers | `/assets/wardrobe/beige-tailored-trousers.webp` | `R.drawable.beige_tailored_trousers` |
| olive-midi-dress | `/assets/wardrobe/olive-midi-dress.webp` | `R.drawable.olive_midi_dress` |
| oatmeal-merino-knit | `/assets/wardrobe/oatmeal-merino-knit.webp` | `R.drawable.oatmeal_merino_knit` |
| camel-trench-coat | `/assets/wardrobe/camel-trench-coat.webp` | `R.drawable.camel_trench_coat` |
| white-leather-sneakers | `/assets/wardrobe/white-leather-sneakers.webp` | `R.drawable.white_leather_sneakers` |
| black-leather-loafers | `/assets/wardrobe/black-leather-loafers.webp` | `R.drawable.black_leather_loafers` |
| tan-ankle-boots | `/assets/wardrobe/tan-ankle-boots.webp` | `R.drawable.tan_ankle_boots` |
| brown-leather-handbag | `/assets/wardrobe/brown-leather-handbag.webp` | `R.drawable.brown_leather_handbag` |
| brown-leather-belt | `/assets/wardrobe/brown-leather-belt.webp` | `R.drawable.brown_leather_belt` |

## Outfit Board Assets

| ID | Web path | Android drawable |
|---|---|---|
| soft-office-capsule | `/assets/outfits/soft-office-capsule.webp` | `R.drawable.soft_office_capsule` |
| weekend-minimal-casual | `/assets/outfits/weekend-minimal-casual.webp` | `R.drawable.weekend_minimal_casual` |
| autumn-layered-look | `/assets/outfits/autumn-layered-look.webp` | `R.drawable.autumn_layered_look` |
| date-night-classic | `/assets/outfits/date-night-classic.webp` | `R.drawable.date_night_classic` |
| travel-capsule-outfit | `/assets/outfits/travel-capsule-outfit.webp` | `R.drawable.travel_capsule_outfit` |
| smart-casual-everyday | `/assets/outfits/smart-casual-everyday.webp` | `R.drawable.smart_casual_everyday` |
| creative-workspace-blend | `/assets/outfits/creative-workspace-blend.webp` | `R.drawable.creative_workspace_blend` |
| gallery-evening-look | `/assets/outfits/gallery-evening-look.webp` | `R.drawable.gallery_evening_look` |

## Empty State Assets

| ID | Web path | Android drawable |
|---|---|---|
| empty-wardrobe | `/assets/empty/empty-wardrobe.webp` | `R.drawable.empty_wardrobe` |
| empty-looks | `/assets/empty/empty-looks.webp` | `R.drawable.empty_looks` |

## Missing Assets

The following empty-state file is still absent from both actual asset folders:

- `empty-decisions.webp` / `empty_decisions.webp`

Current behavior:

- `empty-decisions` uses a UI-only/fallback empty state and must not fail the build.
