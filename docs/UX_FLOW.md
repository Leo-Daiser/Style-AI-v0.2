# StyleAI UX Flow

## Navigation

Startup flow:

```text
splash
  -> onboarding, if onboarding is incomplete
  -> consent, if consent is incomplete
  -> main, if onboarding and consent are complete
```

Main dashboard tabs:

1. Home
2. Wardrobe
3. Decisions
4. Looks
5. Profile

Secondary routes:

- `upload`
- `shopping_check`
- `report_detail`
- `paywall`

## Home

Command center for the MVP:

- StyleAI header.
- Wardrobe/decision/credit status chips.
- Visual outfit preview using `soft-office-capsule`.
- CTA: build today's outfit, switches to Looks.
- CTA: check an item, opens `shopping_check`.
- Compact actions for Wardrobe, Upload, Profile, and unused items.
- Privacy note: shopping checks work without uploading face/body photos.

## Wardrobe

Uses real wardrobe assets from `VisualAssets.wardrobeItems`.

Required content:

- 2-column product grid.
- 1:1 product image area.
- Item title, category, color direction, versatility score, and outfit count.
- Detail modal with compatible items, outfit formulas, wear log, and mocked decisions.

## Decisions

User-facing shopping decision history, not a debug history log.

Tabs:

- All
- Buy
- Maybe
- Skip
- Wishlist

Cards show thumbnail, verdict, reason, outfit count, capsule impact, date, details, and delete actions.

## Looks

Visual outfit board using `VisualAssets.outfitBoards`.

Tabs:

- Today
- Office
- Weekend
- Date
- Travel
- Saved

Cards show outfit board image, occasion, season/formality chips, key items, Save, Wear today, and Create visual reference actions.

## Profile

Profile replaces Settings as the user-facing tab.

Includes:

- Optional style profile empty state.
- Palette/style rules when a report exists.
- Language controls.
- Privacy statement.
- Delete style data.
- Reset onboarding.
- Purge all local data.
