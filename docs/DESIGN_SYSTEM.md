# StyleAI Design System

## Direction

StyleAI should feel premium, calm, editorial, warm, fashion-oriented, clean, practical, and mobile-first.

Avoid:

- emoji-like random icons;
- debug labels;
- technical wording in user-facing screens;
- fake finance metrics;
- heavy borders;
- cluttered cards;
- overly thick buttons.

## Navigation

Final bottom navigation:

- Home
- Wardrobe
- Decisions
- Looks
- Profile

Russian labels:

- Главная
- Гардероб
- Решения
- Образы
- Профиль

## Layout Rules

- Cards use restrained radius, normally 8dp.
- Product grid uses 2 columns on Android.
- Wardrobe image containers are 1:1.
- Outfit board containers are 4:5.
- Empty state images are 1:1.
- Wardrobe product images use `ContentScale.Fit`.
- Outfit boards may use `ContentScale.Crop` when the composition remains readable.

## Content Rules

- Use "Create visual reference · 1 credit" for the visual reference action.
- Settings are part of Profile.
- Report, Upload, and History are not main tabs.
- Shopping checks must state that face/body upload is not required.
