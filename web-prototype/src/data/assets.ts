export interface WardrobeAssetItem {
  id: string;
  titleEn: string;
  titleRu: string;
  category: string;
  colorDirection: string;
  season: string;
  versatilityScore: number;
  outfitCount: number;
  imageSrc: string;
}

export interface OutfitBoardAsset {
  id: string;
  titleEn: string;
  titleRu: string;
  occasion: string;
  season: string;
  formality: string;
  itemIds: string[];
  imageSrc: string;
  creditCost: number;
}

export interface EmptyStateAsset {
  id: string;
  titleEn: string;
  titleRu: string;
  imageSrc: string;
  usage: string;
}

export const wardrobeItems: WardrobeAssetItem[] = [
  {
    id: "cream-structured-blazer",
    titleEn: "Cream structured blazer",
    titleRu: "Кремовый структурированный жакет",
    category: "Outerwear",
    colorDirection: "Warm neutral",
    season: "Spring / Autumn",
    versatilityScore: 86,
    outfitCount: 6,
    imageSrc: "/assets/wardrobe/cream-structured-blazer.webp"
  },
  {
    id: "white-cotton-shirt",
    titleEn: "White cotton shirt",
    titleRu: "Белая хлопковая рубашка",
    category: "Tops",
    colorDirection: "Neutral",
    season: "All season",
    versatilityScore: 92,
    outfitCount: 8,
    imageSrc: "/assets/wardrobe/white-cotton-shirt.webp"
  },
  {
    id: "dark-blue-straight-jeans",
    titleEn: "Dark blue straight jeans",
    titleRu: "Темно-синие прямые джинсы",
    category: "Bottoms",
    colorDirection: "Neutral",
    season: "All season",
    versatilityScore: 89,
    outfitCount: 7,
    imageSrc: "/assets/wardrobe/dark-blue-straight-jeans.webp"
  },
  {
    id: "beige-tailored-trousers",
    titleEn: "Beige tailored trousers",
    titleRu: "Бежевые классические брюки",
    category: "Bottoms",
    colorDirection: "Warm neutral",
    season: "Spring / Autumn / Summer",
    versatilityScore: 82,
    outfitCount: 5,
    imageSrc: "/assets/wardrobe/beige-tailored-trousers.webp"
  },
  {
    id: "olive-midi-dress",
    titleEn: "Olive midi dress",
    titleRu: "Оливковое платье-миди",
    category: "Dresses",
    colorDirection: "Muted olive",
    season: "Spring / Summer / Autumn",
    versatilityScore: 75,
    outfitCount: 4,
    imageSrc: "/assets/wardrobe/olive-midi-dress.webp"
  },
  {
    id: "oatmeal-merino-knit",
    titleEn: "Oatmeal merino knit",
    titleRu: "Овсяный трикотажный пуловер",
    category: "Tops",
    colorDirection: "Warm neutral",
    season: "Autumn / Winter / Spring",
    versatilityScore: 88,
    outfitCount: 6,
    imageSrc: "/assets/wardrobe/oatmeal-merino-knit.webp"
  },
  {
    id: "camel-trench-coat",
    titleEn: "Camel trench coat",
    titleRu: "Песочный тренч",
    category: "Outerwear",
    colorDirection: "Warm neutral",
    season: "Spring / Autumn",
    versatilityScore: 85,
    outfitCount: 5,
    imageSrc: "/assets/wardrobe/camel-trench-coat.webp"
  },
  {
    id: "white-leather-sneakers",
    titleEn: "White leather sneakers",
    titleRu: "Белые кожаные кеды",
    category: "Shoes",
    colorDirection: "Neutral",
    season: "Spring / Summer / Autumn",
    versatilityScore: 95,
    outfitCount: 10,
    imageSrc: "/assets/wardrobe/white-leather-sneakers.webp"
  },
  {
    id: "black-leather-loafers",
    titleEn: "Black leather loafers",
    titleRu: "Черные кожаные лоферы",
    category: "Shoes",
    colorDirection: "Neutral",
    season: "Spring / Autumn",
    versatilityScore: 84,
    outfitCount: 6,
    imageSrc: "/assets/wardrobe/black-leather-loafers.webp"
  },
  {
    id: "tan-ankle-boots",
    titleEn: "Tan ankle boots",
    titleRu: "Рыжие ботильоны",
    category: "Shoes",
    colorDirection: "Warm neutral",
    season: "Autumn / Spring",
    versatilityScore: 78,
    outfitCount: 4,
    imageSrc: "/assets/wardrobe/tan-ankle-boots.webp"
  },
  {
    id: "brown-leather-handbag",
    titleEn: "Brown leather handbag",
    titleRu: "Коричневая кожаная сумка",
    category: "Accessories",
    colorDirection: "Warm neutral",
    season: "All season",
    versatilityScore: 90,
    outfitCount: 9,
    imageSrc: "/assets/wardrobe/brown-leather-handbag.webp"
  },
  {
    id: "brown-leather-belt",
    titleEn: "Brown leather belt",
    titleRu: "Коричневый кожаный ремень",
    category: "Accessories",
    colorDirection: "Warm neutral",
    season: "All season",
    versatilityScore: 94,
    outfitCount: 12,
    imageSrc: "/assets/wardrobe/brown-leather-belt.webp"
  }
];

export const outfitBoards: OutfitBoardAsset[] = [
  {
    id: "soft-office-capsule",
    titleEn: "Soft Office Capsule",
    titleRu: "Мягкая офисная капсула",
    occasion: "Office",
    season: "All season",
    formality: "Polished",
    itemIds: [
      "cream-structured-blazer",
      "white-cotton-shirt",
      "dark-blue-straight-jeans",
      "black-leather-loafers",
      "brown-leather-handbag"
    ],
    imageSrc: "/assets/outfits/soft-office-capsule.webp",
    creditCost: 1
  },
  {
    id: "weekend-minimal-casual",
    titleEn: "Weekend Minimal Casual",
    titleRu: "Минималистичный кэжуал выходного дня",
    occasion: "Weekend",
    season: "Spring / Summer",
    formality: "Relaxed",
    itemIds: [
      "white-cotton-shirt",
      "dark-blue-straight-jeans",
      "white-leather-sneakers",
      "brown-leather-belt",
      "brown-leather-handbag"
    ],
    imageSrc: "/assets/outfits/weekend-minimal-casual.webp",
    creditCost: 1
  },
  {
    id: "autumn-layered-look",
    titleEn: "Autumn Layered Look",
    titleRu: "Осенний многослойный образ",
    occasion: "City Walk",
    season: "Autumn",
    formality: "Smart Casual",
    itemIds: [
      "camel-trench-coat",
      "oatmeal-merino-knit",
      "dark-blue-straight-jeans",
      "tan-ankle-boots",
      "brown-leather-handbag"
    ],
    imageSrc: "/assets/outfits/autumn-layered-look.webp",
    creditCost: 1
  },
  {
    id: "date-night-classic",
    titleEn: "Date Night Classic",
    titleRu: "Классический образ для свидания",
    occasion: "Dinner",
    season: "Spring / Autumn",
    formality: "Elevated",
    itemIds: [
      "cream-structured-blazer",
      "olive-midi-dress",
      "black-leather-loafers",
      "brown-leather-handbag"
    ],
    imageSrc: "/assets/outfits/date-night-classic.webp",
    creditCost: 1
  },
  {
    id: "travel-capsule-outfit",
    titleEn: "Travel Capsule Outfit",
    titleRu: "Образ для путешествий",
    occasion: "Travel",
    season: "All season",
    formality: "Comfortable",
    itemIds: [
      "camel-trench-coat",
      "white-cotton-shirt",
      "beige-tailored-trousers",
      "white-leather-sneakers",
      "brown-leather-handbag"
    ],
    imageSrc: "/assets/outfits/travel-capsule-outfit.webp",
    creditCost: 1
  },
  {
    id: "smart-casual-everyday",
    titleEn: "Smart Casual Everyday",
    titleRu: "Повседневный смарт-кэжуал",
    occasion: "Everyday",
    season: "All season",
    formality: "Smart Casual",
    itemIds: [
      "cream-structured-blazer",
      "oatmeal-merino-knit",
      "beige-tailored-trousers",
      "black-leather-loafers",
      "brown-leather-handbag"
    ],
    imageSrc: "/assets/outfits/smart-casual-everyday.webp",
    creditCost: 1
  },
  {
    id: "creative-workspace-blend",
    titleEn: "Creative Workspace Blend",
    titleRu: "Креативный офисный стиль",
    occasion: "Office / Creative",
    season: "All season",
    formality: "Smart Casual",
    itemIds: [
      "white-cotton-shirt",
      "dark-blue-straight-jeans",
      "white-leather-sneakers",
      "brown-leather-belt"
    ],
    imageSrc: "/assets/outfits/creative-workspace-blend.webp",
    creditCost: 1
  },
  {
    id: "gallery-evening-look",
    titleEn: "Gallery Evening Look",
    titleRu: "Вечерний образ для галереи",
    occasion: "Gallery / Dinner",
    season: "Autumn / Winter",
    formality: "Polished",
    itemIds: [
      "cream-structured-blazer",
      "oatmeal-merino-knit",
      "beige-tailored-trousers",
      "black-leather-loafers",
      "brown-leather-handbag"
    ],
    imageSrc: "/assets/outfits/gallery-evening-look.webp",
    creditCost: 1
  }
];

export const emptyStateAssets: EmptyStateAsset[] = [
  {
    id: "empty-wardrobe",
    titleEn: "No items in wardrobe",
    titleRu: "В гардеробе пока нет вещей",
    imageSrc: "/assets/empty/empty-wardrobe.webp",
    usage: "Displayed on Wardrobe Screen when no items have been added."
  },
  {
    id: "empty-looks",
    titleEn: "No saved outfits yet",
    titleRu: "У вас пока нет сохраненных образов",
    imageSrc: "/assets/empty/empty-looks.webp",
    usage: "Displayed on Looks Screen when the user has not generated or saved any outfits."
  },
  {
    id: "empty-decisions",
    titleEn: "No shopping decisions made yet",
    titleRu: "Решения о покупках еще не приняты",
    imageSrc: "/assets/empty/empty-looks.webp", // Fallback since empty-decisions is missing
    usage: "Displayed on Decisions Screen when the user has no history or saved item shopping checks."
  }
];
