import { useState, useEffect } from "react";
import { 
  Camera, Upload, Palette, CheckSquare, History, Settings, 
  ShieldCheck, AlertTriangle, CreditCard, Lock, RefreshCw, 
  Sliders, Download, Sparkles, Trash2, ArrowRight, Eye, BookOpen,
  Wifi, Battery, ShieldAlert, Heart, CheckCircle, Info, ChevronRight, ChevronLeft, HelpCircle, Laptop, Home,
  Archive, X, Layers, User
} from "lucide-react";
import { kotlinFiles } from "./kotlinFiles";

// Structured translations
const STRINGS = {
  EN: {
    appTitle: "StyleAI",
    splashSubtitle: "Your Private AI Style Assistant",
    skipOnboarding: "Skip Onboarding",
    continue: "Continue",
    getStarted: "Get Started",
    onboardingTitle1: "Discover your personal style",
    onboardingDesc1: "Get a tailored style report card generated based on your real facial pigmentations, silhouette structures, and individual aesthetic choices.",
    onboardingTitle2: "Build better outfits",
    onboardingDesc2: "Identify perfect garment shapes, curated blazers, safe combination color spectrums, and close high-priority wardrobe gaps effortlessly.",
    onboardingTitle3: "Privacy-First by design",
    onboardingDesc3: "Your uploaded pixels are parsed purely in-memory. They are not recorded, not transmitted, and not shared. Safe, reliable, and secure.",
    privacyConsent: "Privacy & Consent",
    consentSubtitle: "To maintain strict legal safety protocols, please verify and consent to the operations below:",
    consentCheck1: "I confirm that the physical uploaded image is of myself or that I possess explicit legal permission from the subject.",
    consentCheck2: "I acknowledge that the style report contains clothing matches and references, not medical or body therapy advice.",
    consentCheck3: "Raw photos are not stored by default. In-memory photo transient data minimizer.",
    confirmAndContinue: "Confirm and Continue",
    uploadTitle: "Create Style Profile",
    uploadSubtitle: "Upload photos only if you want a personal palette, silhouette guide, and capsule recommendations.",
    uploadOptionalNote: "This step is optional. You can use shopping checks without creating a style profile.",
    demoPhotoStatesLabel: "Demo photo states",
    selfieLabel: "1. Face/Selfie Photo",
    fullBodyLabel: "2. Full Body Contour",
    startAnalysis: "Initiate Style Analysis",
    warningBlurry: "Poor lighting detected. Please stand closer to a window, turning face to natural sun vectors for optimal palette evaluations.",
    validationError: "Safety filter trigger: Blocked. Only single adult portraits are permitted.",
    loadingStep1: "Photo Safety & Moderation Checks...",
    loadingStep2: "Scanning skin pigments & season colors...",
    loadingStep3: "Measuring shoulder alignments & fits...",
    loadingStep4: "Curating Soft Classic style paths...",
    loadingStep5: "Cross-referencing wardrobe gaps lists...",
    loadingStep6: "Assembling recommendations report...",
    reportTitle: "Style Analysis Report",
    colorPaletteSection: "Recommended Color Palette",
    silhouettesSection: "Suggested Silhouettes",
    styleDirectionsSection: "Style Directions",
    wardrobeGapsSection: "Wardrobe Gaps Checklist",
    shoppingListSection: "Priority Shopping List",
    whatToAvoidSection: "Avoid Guide (Safety Note)",
    bodySafetyNotice: "We avoid labels or body-shaming terms. Style targets personal geometry.",
    outfitBoard: "Outfit Board",
    generateLayoutsSafely: "Generate reference layouts safely",
    occasionLabel: "Occasion",
    styleLabel: "Style",
    seasonLabel: "Season",
    formalityLabel: "Formality",
    colorDirectionLabel: "Color Direction",
    visSafetyNotice: "Visualizations are style references, not exact clothing fit simulations. Unsafe or non-consensual image use is not allowed.",
    renderOutfit: "Create visual reference · 1 credit",
    unlockPremium: "Unlock StyleAI Premium",
    unlockSubtitle: "Unlock full seasonal guides and custom styling boards",
    paywallCompleteReport: "Complete Style Report",
    paywallSubPlan: "Pro Monthly Plan",
    paywallCreditsPack: "Buy 10 Outfit Credits",
    goBackToOutfits: "Go Back to Outfits",
    historyTitle: "Decisions",
    historySubtitle: "Your saved shopping decisions and wardrobe choices.",
    activeReports: "Shopping decisions",
    savedLooks: "Saved Looks list",
    emptyHistory: "No saved shopping decisions yet.",
    appSettings: "App Settings",
    settingsSubtitle: "Fine-tune privacy thresholds, data exports, and cache minimizations.",
    settingsLanguageSection: "Language / Язык",
    settingsBtnWipeLooks: "Delete Style Data Only",
    settingsBtnResetOnboarding: "Reset App Onboarding",
    settingsBtnWipeAll: "Purge All Local Data & Reset",
    settingsExportButton: "Export Style Report (JSON)",
    settingsExporting: "Generating Export Pack...",
    settingsDismiss: "Dismiss",
    settingsPurgePrompt: "Purge Local Cache?",
    settingsPurgeDesc: "This will completely discard all saved color swatches, wardrobe checklist milestones, saved reports and outfits. This is irreversible.",
    confirmPurge: "Confirm Purge",

    // Part 1, 2, 3 EN
    styleSummaryTitle: "Personal Style Summary",
    styleDiagnosisText: "Diagnosis: Balanced Neutral Geometry with Soft Autumn undertones. Your posture and facial structure benefit from relaxed structural styling rather than rigid contrasts.",
    recsLabel: "3 Key Recommendations",
    recsList: ["Focus on tailored layering to create elegant dimension", "Use soft tone-on-tone color transitions", "Prioritize premium, mid-weight natural textiles (e.g. linen, wool blends)"],
    risksLabel: "3 Style Risks to Avoid",
    risksList: ["Stark absolute black-and-white high-contrast zones close to face", "Oversized, completely structureless elements that hide posture", "Busy, geometric neon patterns that clash with pastel skin tones"],
    paletteNameLabel: "Palette Name",
    primaryColorsLabel: "6 Primary Colors",
    accentColorsLabel: "4 Accent Colors",
    carefulColorsLabel: "3 Colors to Use Carefully",
    paletteExplanationText: "Explanation: Soft, organic earth tones highlight your profile lines seamlessly. Bold neon elements should details outer trims only.",
    shapesTitle: "Recommended Silhouette Shapes",
    shapesText: "Fluid drape lines, gentle high-rise contours, and structured shoulder blazers.",
    layeringAdviceLabel: "Layering Advice",
    layeringAdviceText: "Wear tonal inner layers under an open mid-weight outer trench to stretch your silhouette line.",
    necklineAdviceLabel: "Neckline Advice",
    necklineAdviceText: "Opt for open lapels, balanced boatnecks, or relaxed cowl-necks to support shoulders.",
    trouserSuggestionsLabel: "Garment Suggestions",
    trouserSuggestionsText: "Choose high-waisted straight trousers, pleated wrap midi skirts, and balanced column dresses.",
    capsuleKitLabel: "Capsule Starter Kit",
    itemPriorityHigh: "HIGH",
    itemPriorityMed: "MEDIUM",
    itemPriorityOpt: "OPTIONAL",
    outfitFormulasLabel: "Aesthetic Outfit Formulas",
    formula1: "Relaxed Blazer + Straight Jeans + Oatmeal Knit + Minimal Sneakers",
    formula2: "Pleated Midi Skirt + Soft Drape Top + Structured Cropped Jacket",
    formula3: "Monochrome Tailored Trouser Base + Contrasting Silk Scarves",
    shoppingRulesTitle: "Smart Shopping Rules",
    rule1: "Buy only if it strictly matches your Soft Autumn color spectrum.",
    rule2: "Buy only if it seamlessly creates at least 3 discrete outfits with your current capsule.",
    rule3: "Avoid purchasing if it duplicates any high-use item already in your wardrobe.",
    rule4: "Avoid buying if it only performs well for a single, rare special occasion.",
    nextActionsLabel: "Actions Checklist",
    actionSaveReport: "Save Style Report",
    actionOpenBoard: "Open Visual Board",
    actionCreateCapsule: "Create Capsule Setup",
    actionAddWardrobe: "Check Wardrobe Gap Items",
    actionViewPaywall: "Review Billing Plans",

    shouldIBuyNav: "Should I Buy?",
    shouldIBuyTitle: "Should I Buy This?",
    shouldIBuySubtitle: "Evaluate any prospective garment photo or screenshot against your custom Style Report profile.",
    uploadGarmentBtn: "Select Garment Screenshot",
    categoryLabel: "Select Clothing Category",
    analyzeGarmentMsg: "Initiating Match Analysis...",
    verdictLabel: "Verdict",
    verdictGood: "GOOD MATCH",
    verdictMaybe: "MAYBE (PROCEED CAREFULLY)",
    verdictSkip: "SKIP (POOR MATCH)",
    whyLabel: "Match Evaluation Highlights",
    whyColor: "Color Synergy: Aligns perfectly with Soft Autumn earthy coordinates.",
    whySilhouette: "Silhouette Fit: Matches relaxed straight structures for your body geometry.",
    whyCapsule: "Capsule Utility: Fills high-priority wool jacket gaps gracefully.",
    whyVersatility: "Versatility Index: Supports at least 4 independent outfit styles.",
    outfitCountText: "This could create 4–6 outfits with your existing capsule.",
    shoppingAdviceLabel: "Shopping Wisdom Notes",
    shopAdviceText1: "Buy only if the material composition (wool/cotton weave) and size fit satisfy high quality standards.",
    shopAdviceText2: "Skip if you already own a similar shade outerwear in your active inventory.",

    paywallWarningCredits: "🔑 Visualizations use credits.",
    paywallWarningUnlimited: "⚠️ No unlimited AI image generation supported.",
    paywallWarningSafety: "🛡️ Credits are instantly refunded if a visualization fails automated safety checks.",
    paywallFreePlanLabel: "Free Preview",
    paywallFreePlanDesc: "Basic palette preview, 1 style direction, limited outfits, local history.",
    paywallFullPlanLabel: "Full Style Report ($9.99)",
    paywallFullPlanDesc: "Full color palette, silhouette guide, 3 style directions, capsule kit, 6 safe visualizations.",
    paywallProPlanLabel: "Pro Monthly Subscription",
    paywallProPlanDesc: "Daily styles, wardrobe planner, 'Should I Buy' analysis, 30 bonus credits/month.",
    paywallCreditPlanLabel: "Credit Package Boosters",
    paywallCreditPlanDesc: "Purchase 10 visualizations for style experimentation.",
    paywallDemoDisclaimer: "Developer MVP Note: All purchases are simulated and free.",
    fullReportUnlockedMsg: "✓ One-Time Full Style Report unlocked! Palette, Silhouette, and Starter Kit details are now expanded.",
    proUnlockedMsg: "✓ Pro Features Unlocked! 'Should I Buy This' checker is now active with monthly bonuses.",
    tenCreditsPurchasedMsg: "✓ 10 Outfit reference credits added to balance.",
    insufficientCreditsMsg: "⚠️ Credits depleted. Please activate Pro or buy a Credit Pack to render and save outfits.",
    // New Home feature localization parameters
    homeTitle: "StyleAI",
    homeSubtitle: "Make better wardrobe decisions.",
    homePrimaryTitle: "Should I buy this?",
    homePrimarySubtitle: "Check if an item is worth buying before it becomes another unused piece.",
    homePrimaryBtn: "Check an item",
    homeWearTitle: "What should I wear?",
    homeWearSubtitle: "Get outfit ideas from your saved looks and style rules.",
    homeWearBtn: "Get outfit ideas",
    homeSecTitleNoProfile: "Create style profile",
    homeSecSubtitleNoProfile: "Optional: unlock palette, silhouettes, and capsule recommendations.",
    homeSecBtnNoProfile: "Create profile",
    homeSecTitleProfileReady: "Your style profile is ready",
    homeSecSubtitleProfileReady: "Your Soft Classic palette and silhouette matrix are active.",
    homeSecBtnProfileReady: "Open report",
    quickPalette: "My palette",
    quickOutfits: "Outfit ideas",
    quickLooks: "Saved looks",
    quickCredits: "Credits",
    quickPrivacy: "Privacy",
    privacyModeTitle: "Privacy-first MVP",
    privacyModeText: "No internet permission. Raw photos are not stored by default. You can use shopping checks without uploading face/body photos.",
    placeholderTitle: "Should I buy this?",
    placeholderSubtitle: "This will help you check if an item fits your capsule and shopping goals.",
    placeholderText: "Mock shopping check flow will be added next.",
    placeholderBtn: "Back to Home",

    // --- Wardrobe screen ---
    wardrobeNav: "Wardrobe",
    wardrobeTitle: "My Wardrobe",
    wardrobeStats: "48 items · 12 outfits · 7 rarely worn",
    wardrobeEmptyTitle: "Your wardrobe is empty",
    wardrobeEmptyDesc: "Add a few items to start building outfits.",
    filterAll: "All",
    filterTops: "Tops",
    filterBottoms: "Bottoms",
    filterDresses: "Dresses",
    filterOuterwear: "Outerwear",
    filterShoes: "Shoes",
    filterBags: "Bags",
    filterAccessories: "Accessories",
    filterUnused: "Unused",
    filterWishlist: "Wishlist",
    addBtnScan: "Scan item",
    addBtnManual: "Add manually",
    addBtnScreenshot: "Import screenshot",
    addManualTitle: "Add Item Manually",
    itemNamePlaceholder: "Item Name (e.g., Silk shirt)",
    itemColorPlaceholder: "Color (e.g., Ivory Cream)",
    addManualSubmitBtn: "Add to Closet",
    addManualCloseBtn: "Cancel",
    
    // --- Item Detail screen ---
    itemDetailTitle: "Item Analysis",
    worksWithTitle: "Works Perfectly With",
    outfitFormulasTitle: "Curated Outfit Formulas",
    wearLogTitle: "Wear Log History",
    wornCountLabel: "Worn count",
    lastWornLabel: "Last worn",
    plannedLooksLabel: "Planned looks",
    deleteItemBtn: "Delete item",
    roleCore: "Core item",
    roleAccent: "Accent item",
    roleOccasion: "Occasion piece",
    roleUnused: "Low versatility",
    roleDuplicate: "Duplicate check",
    decisionLabelSet: "Usage Decision Status",
    descKeep: "Keep item active",
    descReplace: "Maybe replace",
    descSell: "Sell or donate",
    descWish: "Wishlist similar",

    // --- Decisions screen ---
    decisionsNav: "Decisions",
    emptyHistoryText: "No decisions recorded inside your active profile database yet.",
    statsSkipped: "You skipped 2 low-compatibility items.",
    statsSaved: "3 shopping decisions saved this week.",

    // --- Profile screen ---
    profileNav: "Profile",
    profileTitle: "Style Rule System",
    completedStatusReady: "Active Profile Complete",
    completedStatusNotReady: "Style Profile Not Completed",
    palSeasonLabel: "Suggested Season",
    bestNeutralsLabel: "Best Neutral Shades",
    colorsCarefullyLabel: "Colors to Use Carefully",
    silhouetteRulesTitle: "Active Silhouette Rules",
    shoppingRulesTabTitle: "Interactive Shopping Rules",
    noProfileDataText: "Style profile not completed yet. You can upload self-portraits to unlock personalized style parameters, or proceed with shopping checks directly."
  },
  RU: {
    appTitle: "StyleAI",
    splashSubtitle: "Ваш приватный AI-стилист",
    skipOnboarding: "Пропустить",
    continue: "Продолжить",
    getStarted: "Начать",
    onboardingTitle1: "Откройте свой персональный стиль",
    onboardingDesc1: "Получите индивидуальную карту стиля на основе цветотипа лица, особенностей силуэта и ваших личных эстетических предпочтений.",
    onboardingTitle2: "Составляйте лучшие образы",
    onboardingDesc2: "Легко подбирайте идеальные фасоны, жакеты, гармоничные цветовые схемы и восполняйте пробелы в гардеробе.",
    onboardingTitle3: "Конфиденциальность на первом месте",
    onboardingDesc3: "Ваши фотографии обрабатываются исключительно в оперативной памяти. Они не сохраняются, не передаются и не передаются третьим лицам. Безопасно и надежно.",
    privacyConsent: "Конфиденциальность и согласие",
    consentSubtitle: "Для соблюдения строгих правил безопасности, пожалуйста, подтвердите согласие со следующими пунктами:",
    consentCheck1: "Я подтверждаю, что загружаемое изображение принадлежит мне или у меня есть явное разрешение от владельца.",
    consentCheck2: "Я согласен с тем, что стилистический отчет содержит рекомендации по одежде и сочетаниям, а не медицинские рекомендации.",
    consentCheck3: "Исходные фотографии по умолчанию не сохраняются. Минимизация данных и обработка только в оперативной памяти.",
    confirmAndContinue: "Подтвердить и продолжить",
    uploadTitle: "Создать стиль-профиль",
    uploadSubtitle: "Загрузите фото только если хотите получить персональную палитру, рекомендации по силуэтам и капсуле.",
    uploadOptionalNote: "Этот шаг необязателен. Вы можете использовать проверку покупок без создания стиль-профиля.",
    demoPhotoStatesLabel: "Демо-состояния фото",
    selfieLabel: "1. Фото лица / Селфи",
    fullBodyLabel: "2. Силуэт в полный рост",
    startAnalysis: "Начать анализ стиля",
    warningBlurry: "Обнаружено плохое освещение. Пожалуйста, встаньте ближе к окну, повернувшись к естественному свету, для лучшего анализа палитры.",
    validationError: "Проверка безопасности: Найден неподдерживаемый контент. Разрешены только одиночные взрослые портреты.",
    loadingStep1: "Проверка безопасности и модерация...",
    loadingStep2: "Анализ пигментов и сезонных цветов...",
    loadingStep3: "Измерение пропорций и линий плеч...",
    loadingStep4: "Разработка стиля Soft Classic...",
    loadingStep5: "Составление списков гардероба...",
    loadingStep6: "Формирование персонального отчета...",
    reportTitle: "Отчет по стилю",
    colorPaletteSection: "Рекомендуемая палитра цветов",
    silhouettesSection: "Рекомендуемые силуэты",
    styleDirectionsSection: "Стилистические направления",
    wardrobeGapsSection: "Пробелы в гардеробе",
    shoppingListSection: "Приоритетный список покупок",
    whatToAvoidSection: "Чего рекомендуется избегать (Заметка безопасности)",
    bodySafetyNotice: "Мы не используем ярлыки и критику фигуры. Стилизация направлена на гармонию геометрии.",
    outfitBoard: "Визуализации образов",
    generateLayoutsSafely: "Создавайте мудборды стиля на основе своего отчета",
    occasionLabel: "Событие",
    styleLabel: "Стиль",
    seasonLabel: "Сезон",
    formalityLabel: "Формальность",
    colorDirectionLabel: "Направление цвета",
    visSafetyNotice: "Визуализации показывают стилевое направление, а не точную посадку одежды. Небезопасное использование изображений и обработка фото без согласия запрещены.",
    renderOutfit: "Создать визуальный референс · 1 кредит",
    unlockPremium: "Разблокировать StyleAI Premium",
    unlockSubtitle: "Разблокируйте полные сезонные гиды и интерактивные подборы",
    paywallCompleteReport: "Полный отчет по стилю",
    paywallSubPlan: "Ежемесячная подписка Pro",
    paywallCreditsPack: "Купить 10 кредитов образов",
    goBackToOutfits: "Вернуться к образам",
    historyTitle: "Локальная история",
    historySubtitle: "Управление кешем устройства и сохраненными образами",
    activeReports: "Коллекция активных отчетов",
    savedLooks: "Сохраненные образы",
    emptyHistory: "Сохраненные отчеты отсутствуют. Загрузите фото для генерации.",
    appSettings: "Настройки приложения",
    settingsSubtitle: "Параметры конфиденциальности, экспорта данных и очистки кеша.",
    settingsLanguageSection: "Язык / Language",
    settingsBtnWipeLooks: "Удалить только данные о стиле",
    settingsBtnResetOnboarding: "Сбросить онбординг приложения",
    settingsBtnWipeAll: "Удалить все данные и очистить кеш",
    settingsExportButton: "Экспортировать отчет (JSON)",
    settingsExporting: "Экспорт пакета...",
    settingsDismiss: "Скрыть",
    settingsPurgePrompt: "Очистить локальный кеш?",
    settingsPurgeDesc: "Это полностью удалит сохраненные палитры, пробелы гардероба и отчеты. Действие необратимо.",
    confirmPurge: "Подтвердить удаление",

    styleSummaryTitle: "Персональное резюме стиля",
    styleDiagnosisText: "Диагноз: Сбалансированная нейтральная геометрия с мягкими осенними полутонами. Вашей осанке и чертам лица лучше всего подходит мягкая полуструктурированная одежда, а не жесткие контрасты.",
    recsLabel: "3 Ключевые рекомендации",
    recsList: ["Сделайте упор на элегантную многослойность для создания глубины", "Используйте мягкие цветовые переходы в рамках одной палитры", "Выбирайте качественные натуральные ткани средней плотности (лен, шерсть и т.д.)"],
    risksLabel: "3 Ошибки, которых следует избегать",
    risksList: ["Резкие контрастные черно-белые зоны близко к лицу", "Оверсайз без четкой формы, полностью скрывающий силуэт", "Яркие неоновые геометрические паттерны, конфликтующие с тоном кожи"],
    paletteNameLabel: "Название палитры",
    primaryColorsLabel: "6 Основных цветов",
    accentColorsLabel: "4 Акцентных цвета",
    carefulColorsLabel: "3 Цвета для осторожного использования",
    paletteExplanationText: "Пояснение: Мягкие землистые оттенки подчеркнут ваши природные черты. Неоновые тона допустимы только на мелких деталях или фурнитуре.",
    shapesTitle: "Рекомендуемые фасоны и силуэты",
    shapesText: "Плавные струящиеся линии, мягкая завышенная посадка и жакеты со структурированной линией плеч.",
    layeringAdviceLabel: "Советы по многослойности",
    layeringAdviceText: "Носите однотонные внутренние слои под распахнутым пальто средней плотности для визуального удлинения силуэта.",
    necklineAdviceLabel: "Рекомендации по вырезу",
    necklineAdviceText: "Глубокие воротники с отворотами, вырез лодочкой или мягкие воротники-хомуты отлично гармонируют с плечами.",
    trouserSuggestionsLabel: "Рекомендуемые элементы",
    trouserSuggestionsText: "Носите прямые брюки с высокой талией, плиссированные миди-юбки с запахом и платья-колонны.",
    capsuleKitLabel: "Капсульный гардероб для старта",
    itemPriorityHigh: "ВЫСОКИЙ",
    itemPriorityMed: "СРЕДНИЙ",
    itemPriorityOpt: "ДОПОЛНИТЕЛЬНО",
    outfitFormulasLabel: "Формулы эстетичных образов",
    formula1: "Свободный жакет + Прямые джинсы + Джемпер из шерсти + Минималистичные кеды",
    formula2: "Плиссированная юбка-миди + Струящийся топ + Жилет со структурой",
    formula3: "Монохромная база из брюк + Контрастный шелковый платок",
    shoppingRulesTitle: "Правила осознанного шопинга",
    rule1: "Покупайте вещь только если она строго вписывается в вашу палитру Мягкой Осени.",
    rule2: "Покупайте только если вещь может составить минимум 3 разных образа с вашей текущей капсулой.",
    rule3: "Избегайте покупки, если вещь дублирует другие уже имеющиеся у вас предметы гардероба.",
    rule4: "Не берите вещь, если она подходит только для одного редкого праздничного события.",
    nextActionsLabel: "Список следующих шагов",
    actionSaveReport: "Сохранить отчет по стилю",
    actionOpenBoard: "Открыть мудборд образов",
    actionCreateCapsule: "Создать капсулу стиля",
    actionAddWardrobe: "Проверить пробелы гардероба",
    actionViewPaywall: "Посмотреть премиум-планы",

    shouldIBuyNav: "Стоит купить?",
    shouldIBuyTitle: "Стоит ли покупать?",
    shouldIBuySubtitle: "Проверьте совместимость любой одежды по фото или снимку экрана с вашим профилем отчета по стилю.",
    uploadGarmentBtn: "Выбрать скриншот одежды",
    categoryLabel: "Выберите категорию одежды",
    analyzeGarmentMsg: "Запуск экспресс-анализа совместимости...",
    verdictLabel: "Вердикт",
    verdictGood: "РЕКОМЕНДУЕМАЯ ПОКУПКА",
    verdictMaybe: "ПОД ВОПРОСОМ (ОСТОРОЖНО)",
    verdictSkip: "ПРОПУСТИТЬ (НЕ ПОДХОДИТ)",
    whyLabel: "Основные аргументы оценки",
    whyColor: "Синергия цвета: отлично гармонирует с палитрой Мягкой Осени.",
    whySilhouette: "Соответствие силуэту: поддерживает мягкую геометрию вашего тела.",
    whyCapsule: "Польза для капсулы: прекрасно закрывает дефицит плотного верхвого слоя.",
    whyVersatility: "Индекс сочетаемости: позволяет составить от 4 независимых образов.",
    outfitCountText: "Этот элемент позволит составить около 4-6 образов с вашей текущей капсулой.",
    shoppingAdviceLabel: "Советы перед покупкой",
    shopAdviceText1: "Покупайте только при уверенности в высоком качестве швов, кроя и состава ткани.",
    shopAdviceText2: "Откажитесь от покупки, если у вас уже есть верхняя одежда похожего землистого цвета.",

    paywallWarningCredits: "🔑 Визуализации расходуют кредиты образов.",
    paywallWarningUnlimited: "⚠️ Безлимитная визуализация по свободному описанию не поддерживается.",
    paywallWarningSafety: "🛡️ Кредиты возвращаются на баланс, если запрос не прошел внутренний фильтр безопасности.",
    paywallFreePlanLabel: "Бесплатная версия",
    paywallFreePlanDesc: "Ограниченный просмотр палитры, 1 направление стиля, базовые образы, локальная история.",
    paywallFullPlanLabel: "Полный отчет по стилю",
    paywallFullPlanDesc: "Полная палитра, гид по силуэтам, 3 направления стиля, стартовая капсула, 6 визуализаций.",
    paywallProPlanLabel: "Месячная подписка Pro",
    paywallProPlanDesc: "Ежедневные идеи образов, аналитика покупок 'Стоит купить?', планировщик, 30 кредитов/мес.",
    paywallCreditPlanLabel: "Пакеты дополнительных кредитов",
    paywallCreditPlanDesc: "Разовые пополнения на 10 визуализаций для творческих поисков стиля.",
    paywallDemoDisclaimer: "Примечание разработчика: Все покупки бесплатны, балансы зачисляются мгновенно.",
    fullReportUnlockedMsg: "✓ Полный отчет разблокирован! Расширенные списки палитр, силуэтов и капсулы теперь видны.",
    proUnlockedMsg: "✓ Функции Pro активированы! Сервис проверки одежды перед покупкой успешно открыт.",
    tenCreditsPurchasedMsg: "✓ Добавлено 10 кредитов на визуализации образов.",
    insufficientCreditsMsg: "⚠️ Недостаточно кредитов. Перейдите в раздел планов для пополнения баланса.",
    // New Home feature localization parameters
    homeTitle: "StyleAI",
    homeSubtitle: "Принимайте лучшие решения для гардероба.",
    homePrimaryTitle: "Стоит ли покупать?",
    homePrimarySubtitle: "Проверьте вещь до покупки, чтобы она не стала очередной ненужной покупкой.",
    homePrimaryBtn: "Проверить вещь",
    homeWearTitle: "Что надеть?",
    homeWearSubtitle: "Получите идеи образов на основе сохраненных решений и правил стиля.",
    homeWearBtn: "Подобрать образ",
    homeSecTitleNoProfile: "Создать стиль-профиль",
    homeSecSubtitleNoProfile: "Необязательно: откройте палитру, силуэты и рекомендации по капсуле.",
    homeSecBtnNoProfile: "Создать профиль",
    homeSecTitleProfileReady: "Ваш стиль-профиль готов",
    homeSecSubtitleProfileReady: "Ваша палитра Мягкой Осени и матрица силуэтов активны.",
    homeSecBtnProfileReady: "Открыть отчет",
    quickPalette: "Моя палитра",
    quickOutfits: "Идеи образов",
    quickLooks: "Сохраненные образы",
    quickCredits: "Кредиты",
    quickPrivacy: "Приватность",
    privacyModeTitle: "Приватный MVP",
    privacyModeText: "Нет доступа к интернету. Исходные фото по умолчанию не сохраняются. Проверку покупок можно использовать без загрузки лица и тела.",
    placeholderTitle: "Стоит ли покупать?",
    placeholderSubtitle: "Эта функция поможет понять, подходит ли вещь вашей капсуле и целям гардероба.",
    placeholderText: "Демо-проверка покупки будет добавлена следующей.",
    placeholderBtn: "На главную",

    // --- Wardrobe screen ---
    wardrobeNav: "Гардероб",
    wardrobeTitle: "Мой гардероб",
    wardrobeStats: "48 предметов · 12 образов · 7 редко надеваемых",
    wardrobeEmptyTitle: "Ваш гардероб пуст",
    wardrobeEmptyDesc: "Добавьте несколько вещей, чтобы начать составлять образы.",
    filterAll: "Все",
    filterTops: "Верх",
    filterBottoms: "Низ",
    filterDresses: "Платья",
    filterOuterwear: "Верхняя одежда",
    filterShoes: "Обувь",
    filterBags: "Сумки",
    filterAccessories: "Аксессуары",
    filterUnused: "Неиспользуемые",
    filterWishlist: "Желания",
    addBtnScan: "Сканировать вещь",
    addBtnManual: "Вручную",
    addBtnScreenshot: "Скриншот одежды",
    addManualTitle: "Добавить вещь вручную",
    itemNamePlaceholder: "Название вещи (например, Шелковая рубашка)",
    itemColorPlaceholder: "Цвет (например, Кремовый)",
    addManualSubmitBtn: "Добавить в шкаф",
    addManualCloseBtn: "Отмена",
    
    // --- Item Detail screen ---
    itemDetailTitle: "Паспорт предмета",
    worksWithTitle: "Идеально сочетается с",
    outfitFormulasTitle: "Рекомендуемые формулы",
    wearLogTitle: "История носки",
    wornCountLabel: "Надето раз",
    lastWornLabel: "В последний раз",
    plannedLooksLabel: "Запланировано выходов",
    deleteItemBtn: "Удалить вещь",
    roleCore: "Базовая основа",
    roleAccent: "Акцентная деталь",
    roleOccasion: "Редкий случай",
    roleUnused: "Малая полезность",
    roleDuplicate: "Уведомление о дубликатах",
    decisionLabelSet: "Статус решения по использованию",
    descKeep: "Оставить активной в шкафу",
    descReplace: "Заменить со временем",
    descSell: "Продать или подарить",
    descWish: "Хочу похожую",

    // --- Decisions screen ---
    decisionsNav: "Решения",
    emptyHistoryText: "Решения о покупках не сохранены в вашем текущем профиле.",
    statsSkipped: "Вы пропустили 2 малополезные вещи.",
    statsSaved: "3 сохраненных решения за неделю.",

    // --- Profile screen ---
    profileNav: "Профиль",
    profileTitle: "Система правил стиля",
    completedStatusReady: "Профиль Стиля Активен",
    completedStatusNotReady: "Профиль Стиля не Заполнен",
    palSeasonLabel: "Рекомендуемый Цветотип",
    bestNeutralsLabel: "Лучшие Нейтральные Оттенки",
    colorsCarefullyLabel: "Цвета для Осторожного Использования",
    silhouetteRulesTitle: "Правила Силуэта и Кроя",
    shoppingRulesTabTitle: "Правила Умных Покупок",
    noProfileDataText: "Профиль стиля еще не заполнен полностью. Загрузите селфи и силуэт для разблокировки персональных рекомендаций, либо сразу приступайте к проверке покупок."
  }
};

const INITIAL_OUTFITS = [
  { id: "outfit_1", title: "Effortless City Uniform", occasion: "Everyday", style: "Minimal", season: "Autumn", formality: "Polished", colorDir: "Neutral", items: ["Camel blazer", "Cream ribbed crewneck", "Tapered oatmeal pants"], colors: ["#DCA494", "#E6D5C3", "#8D5B4C"], isSaved: false },
  { id: "outfit_2", title: "Gallery Gala Evening", occasion: "Event", style: "Feminine", season: "Spring", formality: "Formal", colorDir: "Warm", items: ["Midi ribbed silk dress in terracotta", "Light beige structured trench", "Nude flats"], colors: ["#CD8D7A", "#E6D5C3", "#CDB49E"], isSaved: false },
  { id: "outfit_3", title: "Creative Workspace Blend", occasion: "Office", style: "Smart casual", season: "Autumn", formality: "Polished", colorDir: "Soft", items: ["Wide-leg dark denim", "Slightly structured terracotta shirt", "Minimal white sneakers"], colors: ["#3A4D39", "#8D5B4C", "#E6D5C3"], isSaved: false },
  { id: "outfit_4", title: "Timeless Boulevard", occasion: "Travel", style: "Classic", season: "Winter", formality: "Formal", colorDir: "Cool", items: ["Camel drape wool coat", "Fitted black highneck", "Classic indigo denim"], colors: ["#5C6B5E", "#CDB49E", "#3A4D39"], isSaved: false },
  { id: "outfit_5", title: "Casual Coffee Encounter", occasion: "Everyday", style: "Streetwear", season: "Summer", formality: "Casual", colorDir: "Contrast", items: ["Relaxed beige short pants", "Sage green cotton tee", "Minimal cream sliders"], colors: ["#E6D5C3", "#5C6B5E", "#8D5B4C"], isSaved: false },
  { id: "outfit_6", title: "Bistro Candlelit Dinner", occasion: "Date", style: "Feminine", season: "Autumn", formality: "Polished", colorDir: "Warm", items: ["Deep olive structured dress", "Tailored tan leather belt", "Muted brown ankle boots"], colors: ["#3A4D39", "#8D5B4C", "#CDB49E"], isSaved: false }
];

export default function App() {
  // Manual App Language Switcher
  const [appLang, setAppLang] = useState<"EN" | "RU">("EN");
  const s = STRINGS[appLang];

  // Mobile Device screens state
  const [currentScreen, setCurrentScreen] = useState<
    "splash" | "onboarding" | "consent" | "home" | "upload" | "analysis" | "report" | "looks" | "decisions" | "settings" | "paywall" | "shopping_check" | "wardrobe" | "profile" | "item_detail"
  >("splash");

  const [onboardingCompleted, setOnboardingCompleted] = useState(false);
  const [homeToast, setHomeToast] = useState<string | null>(null);

  // --- MY WARDROBE STATES ---
  const [wardrobeItems, setWardrobeItems] = useState([
    {
      id: "w_1",
      name: "Cream structured blazer",
      category: "Outerwear",
      color: "Cream",
      colorDir: "Warm",
      season: "Spring/Autumn",
      versatilityScore: 86,
      outfitsCount: 6,
      rarelyWorn: false,
      isWishlist: false,
      imageEmoji: "🧥",
      hexColor: "#E6D5C3"
    },
    {
      id: "w_2",
      name: "Olive draped midi dress",
      category: "Dress",
      color: "Olive",
      colorDir: "Cool",
      season: "Winter/Autumn",
      versatilityScore: 55,
      outfitsCount: 3,
      rarelyWorn: false,
      isWishlist: false,
      imageEmoji: "👗",
      hexColor: "#5C6B5E"
    },
    {
      id: "w_3",
      name: "Deep charcoal wide-leg trouser",
      category: "Bottom",
      color: "Charcoal",
      colorDir: "Neutral",
      season: "Autumn/Winter",
      versatilityScore: 92,
      outfitsCount: 8,
      rarelyWorn: false,
      isWishlist: false,
      imageEmoji: "👖",
      hexColor: "#2C3E50"
    },
    {
      id: "w_4",
      name: "Oatmeal merino knit sweater",
      category: "Top",
      color: "Oatmeal",
      colorDir: "Soft",
      season: "Autumn/Winter",
      versatilityScore: 78,
      outfitsCount: 5,
      rarelyWorn: false,
      isWishlist: false,
      imageEmoji: "🧶",
      hexColor: "#CDB49E"
    },
    {
      id: "w_5",
      name: "White leather classic sneakers",
      category: "Shoes",
      color: "White",
      colorDir: "Neutral",
      season: "Spring/Summer",
      versatilityScore: 95,
      outfitsCount: 12,
      rarelyWorn: false,
      isWishlist: false,
      imageEmoji: "👟",
      hexColor: "#FFFFFF"
    },
    {
      id: "w_6",
      name: "Terracotta linen shirt",
      category: "Top",
      color: "Terracotta",
      colorDir: "Warm",
      season: "Summer",
      versatilityScore: 68,
      outfitsCount: 4,
      rarelyWorn: false,
      isWishlist: false,
      imageEmoji: "👕",
      hexColor: "#A87C66"
    },
    {
      id: "w_7",
      name: "Black leather saddle bag",
      category: "Bag",
      color: "Black",
      colorDir: "Neutral",
      season: "All seasons",
      versatilityScore: 90,
      outfitsCount: 9,
      rarelyWorn: false,
      isWishlist: false,
      imageEmoji: "👜",
      hexColor: "#111111"
    },
    {
      id: "w_8",
      name: "Gold chunky chain necklace",
      category: "Accessory",
      color: "Gold",
      colorDir: "Contrast",
      season: "All seasons",
      versatilityScore: 88,
      outfitsCount: 15,
      rarelyWorn: false,
      isWishlist: false,
      imageEmoji: "👑",
      hexColor: "#D4AF37"
    },
    {
      id: "w_9",
      name: "Pink silk slip skirt",
      category: "Bottom",
      color: "Pink",
      colorDir: "Soft",
      season: "Summer",
      versatilityScore: 45,
      outfitsCount: 2,
      rarelyWorn: true,
      isWishlist: false,
      imageEmoji: "👗",
      hexColor: "#FFC0CB"
    },
    {
      id: "w_10",
      name: "Neon green puffer jacket",
      category: "Outerwear",
      color: "Neon Green",
      colorDir: "Contrast",
      season: "Winter",
      versatilityScore: 20,
      outfitsCount: 1,
      rarelyWorn: true,
      isWishlist: false,
      imageEmoji: "🧥",
      hexColor: "#39FF14"
    },
    {
      id: "w_11",
      name: "Camel drape wool coat",
      category: "Outerwear",
      color: "Camel",
      colorDir: "Warm",
      season: "Autumn/Winter",
      versatilityScore: 85,
      outfitsCount: 7,
      rarelyWorn: false,
      isWishlist: true,
      imageEmoji: "🧥",
      hexColor: "#C19A6B"
    },
    {
      id: "w_12",
      name: "Suede leather loafers",
      category: "Shoes",
      color: "Suede Brown",
      colorDir: "Soft",
      season: "Spring/Autumn",
      versatilityScore: 74,
      outfitsCount: 5,
      rarelyWorn: false,
      isWishlist: true,
      imageEmoji: "👞",
      hexColor: "#8B5A2B"
    }
  ]);

  const [selectedWardrobeItem, setSelectedWardrobeItem] = useState<any | null>(null);
  const [wardrobeFilter, setWardrobeFilter] = useState("All");
  const [showAddItemModal, setShowAddItemModal] = useState(false);

  // New item form state
  const [newItmName, setNewItmName] = useState("");
  const [newItmCategory, setNewItmCategory] = useState<"Top" | "Bottom" | "Dress" | "Outerwear" | "Shoes" | "Bag" | "Accessory">("Top");
  const [newItmColor, setNewItmColor] = useState("");
  const [newItmColorDir, setNewItmColorDir] = useState<"Neutral" | "Warm" | "Cool" | "Soft" | "Contrast">("Neutral");
  const [newItmWishlist, setNewItmWishlist] = useState(false);
  const [newItmStyleRole, setNewItmStyleRole] = useState<"Core" | "Accent" | "Occasion">("Core");


  // --- SHOULD I BUY THIS / SHOPPING CHECK NEW STATE ---
  const [shoppingStep, setShoppingStep] = useState<"Input" | "Loading" | "Result">("Input");
  const [selectedCategory, setSelectedCategory] = useState<"Top" | "Bottom" | "Dress" | "Outerwear" | "Shoes" | "Bag" | "Accessory">("Top");
  const [selectedGarmentContext, setSelectedGarmentContext] = useState<"Basic" | "Trend" | "Occasion" | "Similar" | "Unsure">("Basic");
  const [selectedGarmentColorDir, setSelectedGarmentColorDir] = useState<"Neutral" | "Warm" | "Cool" | "Soft" | "Contrast">("Neutral");
  const [selectedMockPhotoOpt, setSelectedMockPhotoOpt] = useState<"basic" | "trend" | "low_versatility">("basic");
  const [shoppingLoadingProgress, setShoppingLoadingProgress] = useState(0);
  const [shoppingLoadingStepText, setShoppingLoadingStepText] = useState("");
  const [shoppingResult, setShoppingResult] = useState<{
    verdict: "Good match" | "Maybe" | "Skip";
    explanationColor: string;
    explanationSilhouette: string;
    explanationCapsule: string;
    explanationVersatility: string;
    outfitCountEstimate: string;
    shoppingAdvice: string;
  } | null>(null);

  const [freeChecksCount, setFreeChecksCount] = useState(3);
  const [shoppingHistory, setShoppingHistory] = useState<{
    id: string;
    category: string;
    context: string;
    colorDir: string;
    verdict: "Good match" | "Maybe" | "Skip";
    date: string;
    reasonEn: string;
    reasonRu: string;
    outfitsCount: number;
  }[]>([
    {
      id: "dec_1",
      category: "Outerwear",
      context: "Basic",
      colorDir: "Neutral",
      verdict: "Good match",
      date: "Today",
      reasonEn: "Perfect charcoal gray blazer that blends with 80% of your current wardrobe colors. Great fabric composition.",
      reasonRu: "Идеальный серый блейзер, который сочетается с 80% цветов вашего гардероба. Отличный состав ткани.",
      outfitsCount: 6
    },
    {
      id: "dec_2",
      category: "Shoes",
      context: "Similar",
      colorDir: "Contrast",
      verdict: "Skip",
      date: "Yesterday",
      reasonEn: "Redundant purchase. You already own similar white sneakers. Skipped to prevent wardrobe clutter.",
      reasonRu: "Избыточная покупка. У вас уже есть похожие белые кеды. Отклонено во избежание захламления.",
      outfitsCount: 0
    },
    {
      id: "dec_3",
      category: "Dress",
      context: "Occasion",
      colorDir: "Warm",
      verdict: "Maybe",
      date: "3 days ago",
      reasonEn: "Fleeting trend item. High risk of becoming another single-use apparel piece. High wear-care requirement.",
      reasonRu: "Вещь-однодневка. Высокий риск того, что она так и останется надетой лишь один раз. Требует сложного ухода.",
      outfitsCount: 1
    }
  ]);
  
  const [onboardingSlide, setOnboardingSlide] = useState(0);
  const [consentState, setConsentState] = useState({
    permission: false,
    disclaimer: false,
    noStore: false
  });

  // Photo state
  const [selfiePhoto, setSelfiePhoto] = useState<{ uri: string; type: "safe" | "blurry" | "restricted" } | null>(null);
  const [bodyPhoto, setBodyPhoto] = useState<{ uri: string; type: "safe" | "blurry" | "restricted" } | null>(null);
  const [photoValidationError, setPhotoValidationError] = useState<string | null>(null);
  const [photoValidationWarnings, setPhotoValidationWarnings] = useState<string[]>([]);

  // Stepper calculations
  const [isAnalyzing, setIsAnalyzing] = useState(false);
  const [analysisProgress, setAnalysisProgress] = useState(0);
  const [analysisStepText, setAnalysisStepText] = useState("");
  const [hasGeneratedReport, setHasGeneratedReport] = useState(false);
  
  const [creditBalance, setCreditBalance] = useState({ credits: 3, isPro: false });
  const [outfits, setOutfits] = useState(INITIAL_OUTFITS);
  const [savedLooks, setSavedLooks] = useState<{ id: string; title: string; color: string; date: string }[]>([]);
  const [selectedDecisionForDetails, setSelectedDecisionForDetails] = useState<any | null>(null);
  
  // CONTROLLED SELECTORS FOR VISUAL BOARD (No free-text prompt as requested)
  const [selectedOccasion, setSelectedOccasion] = useState<"Everyday" | "Office" | "Date" | "Travel" | "Event">("Everyday");
  const [selectedStyle, setSelectedStyle] = useState<"Minimal" | "Classic" | "Smart casual" | "Feminine" | "Streetwear">("Minimal");
  const [selectedSeason, setSelectedSeason] = useState<"Spring" | "Summer" | "Autumn" | "Winter">("Autumn");
  const [selectedFormality, setSelectedFormality] = useState<"Casual" | "Polished" | "Formal">("Polished");
  const [selectedColorDir, setSelectedColorDir] = useState<"Neutral" | "Warm" | "Cool" | "Soft" | "Contrast">("Neutral");

  const [generationSuccessAlert, setGenerationSuccessAlert] = useState(false);

  // Settings feed triggers
  const [settingsFeedback, setSettingsFeedback] = useState<string | null>(null);

  // Confirmation dialog state controllers
  const [dialogOpen, setDialogOpen] = useState<"none" | "styleData" | "onboarding">("none");

  // App version clicks for the hidden developer view
  const [versionTapCount, setVersionTapCount] = useState(0);
  const [devReadinessVisible, setDevReadinessVisible] = useState(false);

  // Code Tab state for visual syncing
  const [activeCodeKey, setActiveCodeKey] = useState<string>("models");
  const [simulatorThemeMode, setSimulatorThemeMode] = useState<"light" | "dark">("light");

  const allConsented = consentState.permission && consentState.disclaimer && consentState.noStore;

  // Splash auto timing jump
  useEffect(() => {
    if (currentScreen === "splash") {
      const timer = setTimeout(() => {
        if (!onboardingCompleted) {
          setCurrentScreen("onboarding");
        } else if (!allConsented) {
          setCurrentScreen("consent");
        } else {
          setCurrentScreen("home");
        }
      }, 2200);
      return () => clearTimeout(timer);
    }
  }, [currentScreen, onboardingCompleted, allConsented]);

  // Sync simulator screen coordinates to code viewer tabs
  useEffect(() => {
    if (currentScreen === "onboarding" || currentScreen === "consent") {
      setActiveCodeKey("localization");
    } else if (currentScreen === "home") {
      setActiveCodeKey("homeScreen");
    } else if (currentScreen === "upload" || currentScreen === "analysis") {
      setActiveCodeKey("privacy");
    } else if (currentScreen === "report") {
      setActiveCodeKey("models");
    } else if (currentScreen === "looks") {
      setActiveCodeKey("visualization");
    } else if (currentScreen === "decisions") {
      setActiveCodeKey("decisionsScreen");
    } else if (currentScreen === "settings") {
      setActiveCodeKey("settings");
    } else if (currentScreen === "shopping_check") {
      setActiveCodeKey("shoppingCheckScreen");
    } else if (currentScreen === "wardrobe" || currentScreen === "item_detail") {
      setActiveCodeKey("wardrobe");
    } else if (currentScreen === "profile") {
      setActiveCodeKey("models");
    }
  }, [currentScreen]);

  // Mock Photo selector rules
  const handleSelectPhoto = (photoTarget: "selfie" | "body", optionType: "safe" | "blurry" | "restricted") => {
    setPhotoValidationError(null);
    setPhotoValidationWarnings([]);

    const uriText = photoTarget === "selfie" 
      ? (optionType === "safe" ? "selfie_portrait_highres.jpg" : optionType === "blurry" ? "selfie_dark_room.jpg" : "selfie_restricted_minor.jpg")
      : (optionType === "safe" ? "fullbody_alignment_chic.jpg" : optionType === "blurry" ? "fullbody_shaky_focus.jpg" : "fullbody_group_bathing.jpg");

    const photoObject = { uri: uriText, type: optionType };

    if (photoTarget === "selfie") {
      setSelfiePhoto(photoObject);
      runValidationSimulation(photoObject);
    } else {
      setBodyPhoto(photoObject);
      runValidationSimulation(photoObject);
    }
  };

  const runValidationSimulation = (photo: { uri: string; type: "safe" | "blurry" | "restricted" }) => {
    if (photo.type === "blurry") {
      setPhotoValidationWarnings([s.warningBlurry]);
    } else if (photo.type === "restricted") {
      setPhotoValidationError(s.validationError);
    }
  };

  // Stepper processor
  const runAnalysisSimulation = () => {
    if (!selfiePhoto || !bodyPhoto || photoValidationError) return;
    setCurrentScreen("analysis");
    setAnalysisProgress(0);
    
    const steps = [
      { text: s.loadingStep1, ms: 600 },
      { text: s.loadingStep2, ms: 1200 },
      { text: s.loadingStep3, ms: 1800 },
      { text: s.loadingStep4, ms: 2400 },
      { text: s.loadingStep5, ms: 3000 },
      { text: s.loadingStep6, ms: 3600 }
    ];

    steps.forEach((step, idx) => {
      setTimeout(() => {
        setAnalysisStepText(step.text);
        setAnalysisProgress(Math.floor(((idx + 1) * 100) / steps.length));
        if (idx === steps.length - 1) {
          setTimeout(() => {
            setHasGeneratedReport(true);
            setCurrentScreen("report");
          }, 600);
        }
      }, step.ms);
    });
  };

  // Toggle saving
  const toggleSaveOutfit = (id: string) => {
    setOutfits(current => current.map(item => {
      if (item.id === id) {
        const nextSaved = !item.isSaved;
        if (nextSaved) {
          setSavedLooks(prev => {
            const filtered = prev.filter(look => look.id !== `look_${item.id}`);
            return [
              { id: `look_${item.id}`, title: item.title, color: item.colors[0], date: "Today" },
              ...filtered
            ];
          });
        } else {
          setSavedLooks(prev => prev.filter(look => look.id !== `look_${item.id}`));
        }
        return { ...item, isSaved: nextSaved };
      }
      return item;
    }));
  };

  // Controlled render triggers
  const triggerSafeVisualization = () => {
    if (!creditBalance.isPro && creditBalance.credits <= 0) {
      setCurrentScreen("paywall");
      return;
    }

    setCreditBalance(prev => ({ ...prev, credits: Math.max(0, prev.credits - 1) }));
    setGenerationSuccessAlert(true);

    // Formulate a clean matching outfit idea based solely on safe selected parameters
    const generatedOutfit = {
      id: `outfit_gen_${Date.now()}`,
      title: `${selectedStyle} ${selectedOccasion} Outfit`,
      occasion: selectedOccasion,
      style: selectedStyle,
      season: selectedSeason,
      formality: selectedFormality,
      colorDir: selectedColorDir,
      items: [`Curated ${selectedStyle} piece`, `${selectedColorDir} harmonized jacket`, "Structured matching footwear"],
      colors: selectedColorDir === "Warm" ? ["#8D5B4C", "#E6D5C3"] : ["#3A4D39", "#CDB49E"],
      isSaved: false
    };

    setOutfits(prev => [generatedOutfit, ...prev]);
    setTimeout(() => setGenerationSuccessAlert(false), 4000);
  };

  // --- SHOULD I BUY THIS / SHOPPING CHECK DETERMINISTIC EVALUATION METRICS ---
  const evaluateShoppingCheck = (
    category: "Top" | "Bottom" | "Dress" | "Outerwear" | "Shoes" | "Bag" | "Accessory",
    context: "Basic" | "Trend" | "Occasion" | "Similar" | "Unsure",
    colorDir: "Neutral" | "Warm" | "Cool" | "Soft" | "Contrast",
    hasReport: boolean,
    lang: "EN" | "RU"
  ) => {
    let verdict: "Good match" | "Maybe" | "Skip" = "Maybe";
    let explanationColor = "";
    let explanationSilhouette = "";
    let explanationCapsule = "";
    let explanationVersatility = "";
    let outfitCountEstimate = "";
    let shoppingAdvice = "";

    // 1. Verdict Determination (Strictly deterministic rule mapping)
    if (context === "Similar") {
      verdict = "Skip";
    } else if (context === "Basic" && (colorDir === "Neutral" || colorDir === "Warm" || colorDir === "Soft")) {
      verdict = "Good match";
    } else if (context === "Trend" && colorDir === "Contrast") {
      verdict = "Maybe";
    } else if (context === "Occasion") {
      verdict = (colorDir === "Neutral" || colorDir === "Warm" || colorDir === "Soft") ? "Maybe" : "Skip";
    } else if (context === "Trend" || context === "Unsure") {
      verdict = "Maybe";
    }

    // Adjust for accessory items with neutral style paths
    if ((category === "Shoes" || category === "Bag" || category === "Accessory") && colorDir === "Neutral") {
      verdict = (context === "Similar") ? "Skip" : "Good match";
    }

    // 2. Bilingual outputs
    if (lang === "EN") {
      if (verdict === "Good match") {
        explanationColor = hasReport 
          ? "Color Synergy: Aligns perfectly with your master Soft Autumn palette guidelines."
          : "Color Synergy: Neutral and soft coordinates provide safe base matching versatility.";
        explanationSilhouette = "Silhouette Fit: Standard straight construction balances and flatters your form.";
        explanationCapsule = "Capsule Utility: Fills high-priority structural gaps in your wardrobe checklist.";
        explanationVersatility = "Versatility: Supports multiple styles. Beautifully coordinates with office tailoring or relaxed jeans.";
        outfitCountEstimate = "Fits beautifully! This could create 4–6 outfits with your existing capsule.";
        shoppingAdvice = "Highly recommended purchase. Built on functional color logic rather than trend hype. Ensure size coordinates are exact.";
      } else if (verdict === "Maybe") {
        explanationColor = hasReport
          ? "Color Check: Tolerable tone, but does not strictly feature Soft Autumn warmth."
          : "Color Check: Vivid chromatic tone that requires careful surrounding layers.";
        explanationSilhouette = "Silhouette Fit: Normal geometry. Could overwhelm your proportions if worn oversized.";
        explanationCapsule = "Capsule Utility: Medium priority. Best added only if you are fully lacking this category.";
        explanationVersatility = "Versatility: Slightly limited combination capacity. Best reserved for predefined outfits.";
        outfitCountEstimate = "Can be styled! This could create 2–3 outfits with your capsule.";
        shoppingAdvice = "Proceed with caution. Double check if you can build at least three distinct fits. Check returns policy first.";
      } else {
        explanationColor = "Color Clash: High friction contrast that disrupts your natural aesthetic harmony.";
        explanationSilhouette = "Silhouette Fit: Bulky or uncomfortable tailoring that restricts movement.";
        explanationCapsule = "Capsule Utility: Redundant purchase. Adds clutter to your active wardrobe.";
        explanationVersatility = "Versatility: Low index. Extremely hard to pair with basic everyday items.";
        outfitCountEstimate = "Likely low versatility. Fits into 1 specific outfit max.";
        shoppingAdvice = "Skip this purchase. Strongly advisable to pass. This item is likely to remain unworn in storage drawers.";
      }
    } else {
      // RU LOCALE DEFINED DETAILED TEXTS
      if (verdict === "Good match") {
        explanationColor = hasReport
          ? "Синергия цвета: отлично гармонирует с палитрой Мягкой Осени в вашем отчете."
          : "Синергия цвета: нейтральные полутона обеспечивают легкую сочетаемость из коробки.";
        explanationSilhouette = "Соответствие силуэту: классический силуэт прекрасно уравновешивает плечи и бедра.";
        explanationCapsule = "Польза для капсулы: решает проблему дефицита базовых слоев в вашем шкафу.";
        explanationVersatility = "Индекс сочетаемости: максимально универсален. Будет отлично смотреться как с брюками, так и с денимом.";
        outfitCountEstimate = "Прекрасный мэтч! Позволит составить около 4-6 образов с вашей текущей капсулой.";
        shoppingAdvice = "Определенно рекомендуем к покупке. Это разумная инвестиция в ваш персональный стиль, а не импульсивный тренд.";
      } else if (verdict === "Maybe") {
        explanationColor = hasReport
          ? "Проверка цвета: оттенок приемлем, но не является идеальным для вашей теплой палитры мягких цветов."
          : "Проверка цвета: достаточно яркий оттенок, потребует грамотной калибровки аксессуаров.";
        explanationSilhouette = "Соответствие силуэту: стандартное прилегание; может скрывать осанку при неверном выборе длины.";
        explanationCapsule = "Польза для капсулы: умеренная. Полезно только при условии реальной нехватки вещей данной категории.";
        explanationVersatility = "Индекс сочетаемости: средний. Придется прорабатывать готовые костюмные ансамбли.";
        outfitCountEstimate = "Хороший акцент! Позволит собрать 2-3 специфических образа.";
        shoppingAdvice = "Принимайте решение взвешенно. Берите только при идеальной посадке и уверенности в качестве материалов.";
      } else {
        explanationColor = "Цветовая гамма: слишком контрастные или перенасыщенные тона, спорящие с вашей базой.";
        explanationSilhouette = "Соответствие силуэту: низкая эргономика, будет визуально утяжелять фигуру.";
        explanationCapsule = "Польза для капсулы: дублирует уже имеющиеся фасоны или создает лишний визуальный шум.";
        explanationVersatility = "Индекс сочетаемости: крайне низкий. Вещь сложна в стилизации и требует покупки дополнительной одежды.";
        outfitCountEstimate = "Низкая практичность. Подходит максимум под 1 образ на один выход.";
        shoppingAdvice = "Рекомендуем пропустить. Отложите эту покупку во избежание захламления шкафа и импульсивных трат.";
      }
    }

    return {
      verdict,
      explanationColor,
      explanationSilhouette,
      explanationCapsule,
      explanationVersatility,
      outfitCountEstimate,
      shoppingAdvice
    };
  };

  const runShoppingCheckSimulation = () => {
    if (!creditBalance.isPro && freeChecksCount <= 0) {
      return;
    }

    setShoppingStep("Loading");
    setShoppingLoadingProgress(0);

    const steps = [
      { text: appLang === "EN" ? "Checking color compatibility..." : "Проверка цветовой совместимости...", ms: 400 },
      { text: appLang === "EN" ? "Estimating capsule usefulness..." : "Оценка полезности для капсулы...", ms: 800 },
      { text: appLang === "EN" ? "Checking outfit versatility..." : "Проверка индекса сочетаемости...", ms: 1200 },
      { text: appLang === "EN" ? "Preparing recommendation..." : "Формирование финальных советов...", ms: 1600 }
    ];

    steps.forEach((step, idx) => {
      setTimeout(() => {
        setShoppingLoadingStepText(step.text);
        setShoppingLoadingProgress(Math.floor(((idx + 1) * 100) / steps.length));
        if (idx === steps.length - 1) {
          setTimeout(() => {
            const finalResult = evaluateShoppingCheck(
              selectedCategory,
              selectedGarmentContext,
              selectedGarmentColorDir,
              hasGeneratedReport,
              appLang
            );
            setShoppingResult(finalResult);
            if (!creditBalance.isPro) {
              setFreeChecksCount(prev => Math.max(0, prev - 1));
            }
            setShoppingStep("Result");
          }, 400);
        }
      }, step.ms);
    });
  };

  // Reset operations
  const handleWipeStyleDataOnly = () => {
    // Keeps language and payments intact
    setSelfiePhoto(null);
    setBodyPhoto(null);
    setHasGeneratedReport(false);
    setSavedLooks([]);
    setOutfits(INITIAL_OUTFITS);
    setSettingsFeedback(appLang === "EN" ? "Deleted local style profiles, wardrobe checklists, and saved looks cache." : "Данные о стиле, галочки гардероба и кеш сохраненных образов удалены.");
    setTimeout(() => setSettingsFeedback(null), 4000);
  };

  const handleResetAppOnboardingOnly = () => {
    setConsentState({ permission: false, disclaimer: false, noStore: false });
    setOnboardingCompleted(false);
    setSelfiePhoto(null);
    setBodyPhoto(null);
    setPhotoValidationError(null);
    setPhotoValidationWarnings([]);
    setHasGeneratedReport(false);
    setCurrentScreen("onboarding");
    setOnboardingSlide(0);
    setSettingsFeedback(appLang === "EN" ? "Onboarding sliders and privacy gate consents reset completely." : "Слайды онбординга и соглашения конфиденциальности полностью сброшены.");
    setTimeout(() => setSettingsFeedback(null), 4000);
  };

  const handleFullReset = () => {
    setAppLang("EN");
    setConsentState({ permission: false, disclaimer: false, noStore: false });
    setOnboardingCompleted(false);
    setSelfiePhoto(null);
    setBodyPhoto(null);
    setPhotoValidationError(null);
    setPhotoValidationWarnings([]);
    setHasGeneratedReport(false);
    setSavedLooks([]);
    setCreditBalance({ credits: 3, isPro: false });
    setOutfits(INITIAL_OUTFITS);
    setCurrentScreen("onboarding");
    setOnboardingSlide(0);
    setVersionTapCount(0);
    setDevReadinessVisible(false);
    setSettingsFeedback("Application state wipe & local database simulator fully emptied.");
    setTimeout(() => setSettingsFeedback(null), 4000);
  };

  const handleVersionTapped = () => {
    const nextCount = versionTapCount + 1;
    setVersionTapCount(nextCount);
    if (nextCount >= 5) {
      setDevReadinessVisible(true);
    }
  };

  return (
    <div className="min-h-screen bg-slate-900 text-slate-100 font-sans flex flex-col antialiased">
      {/* Upper Navigation Header Bar */}
      <header className="border-b border-slate-800 bg-slate-950 px-6 py-4 flex items-center justify-between shadow-lg shrink-0">
        <div className="flex items-center gap-3">
          <Palette className="h-6 w-6 text-emerald-400" />
          <div>
            <span className="font-mono text-[10px] text-slate-500 font-semibold tracking-widest uppercase">Android Architecture MVP</span>
            <h1 className="text-lg font-bold text-slate-200">StyleAI Developer Playground</h1>
          </div>
        </div>
        <div className="flex items-center gap-4">
          <div className="hidden sm:flex items-center gap-2 bg-slate-800/60 px-3 py-1.5 rounded-md border border-slate-700/50">
            <Lock className="text-emerald-400 h-3.5 w-3.5" />
            <span className="text-xs text-slate-300 font-mono">Bilingual Safe Mode</span>
          </div>
          <div className="flex gap-1.5 bg-slate-850 p-1 rounded-lg border border-slate-755">
            <button 
              onClick={() => setAppLang("EN")} 
              className={`px-2.5 py-1 text-xs font-bold rounded transition ${appLang === "EN" ? "bg-emerald-500 text-slate-950" : "text-slate-400 hover:text-slate-200"}`}
            >
              EN
            </button>
            <button 
              onClick={() => setAppLang("RU")} 
              className={`px-2.5 py-1 text-xs font-bold rounded transition ${appLang === "RU" ? "bg-emerald-500 text-slate-950" : "text-slate-400 hover:text-slate-200"}`}
            >
              RU
            </button>
          </div>
        </div>
      </header>

      {/* Main Split Layout Panel */}
      <main className="flex-1 overflow-hidden flex flex-col lg:flex-row">
        
        {/* LEFT COLUMN: Physical Android Device Emulator Canvas */}
        <section className="flex-1 bg-slate-950 p-4 lg:p-6 flex flex-col items-center justify-start overflow-y-auto border-r border-slate-800 scrollbar-none">
          
          {/* Controls: Device mode and shortcuts */}
          <div className="w-full max-w-[385px] mb-3 flex items-center justify-between text-xs bg-slate-900 p-2.5 rounded-lg border border-slate-800">
            <div className="flex gap-2">
              <span className="text-slate-400 font-medium">Device Theme:</span>
              <button 
                onClick={() => setSimulatorThemeMode("light")} 
                className={`px-2.5 py-0.5 rounded transition font-bold ${simulatorThemeMode === "light" ? "bg-emerald-400/20 text-emerald-400 border border-emerald-500/30" : "text-slate-500"}`}
              >
                Light
              </button>
              <button 
                onClick={() => setSimulatorThemeMode("dark")} 
                className={`px-2.5 py-0.5 rounded transition font-bold ${simulatorThemeMode === "dark" ? "bg-emerald-400/20 text-emerald-400 border border-emerald-500/30" : "text-slate-500"}`}
              >
                Dark
              </button>
            </div>
            
            <div className="text-slate-500 font-mono text-[10px]">
              SCREEN: {currentScreen.toUpperCase()}
            </div>
          </div>

          {/* Android Pixel Phone Housing */}
          <div className="relative w-[370px] h-[730px] rounded-[48px] border-[10px] border-slate-800 bg-slate-950 shadow-[0_20px_50px_-15px_rgba(0,0,0,0.9)] overflow-hidden shrink-0 flex flex-col outline outline-1 outline-slate-700">
            
            {/* Phone Speaker Notch & Camera Dot */}
            <div className="absolute top-0 inset-x-0 h-6 flex items-center justify-center z-40 pointer-events-none">
              <div className="w-24 h-4.5 bg-slate-800 rounded-b-xl flex items-center justify-center gap-1.5">
                <div className="w-1.5 h-1.5 rounded-full bg-slate-900"></div>
                <div className="w-8 h-0.5 bg-slate-900 rounded-full"></div>
              </div>
            </div>

            {/* Android Status Bar */}
            <div className={`pt-5 px-6 pb-1.5 flex items-center justify-between text-[10px] font-mono select-none z-30 shrink-0 ${
              simulatorThemeMode === "light" ? "bg-[#F3EFE9] text-slate-700" : "bg-[#262625] text-slate-300"
            }`}>
              <span>09:39</span>
              <div className="flex items-center gap-1.5">
                <Wifi className="h-2.5 w-2.5 text-emerald-500" />
                <span className="text-[8px] font-black">LOCAL_DB</span>
                <Battery className="h-3 w-3" />
              </div>
            </div>

            {/* PHYSICAL APP SCREEN VIEWPORT */}
            <div className={`flex-1 flex flex-col overflow-y-auto relative ${
              simulatorThemeMode === "light" ? "bg-[#F3EFE9] text-[#262626]" : "bg-[#262626] text-[#F3EFE9]"
            }`}>
              
              {/* SCREEN 1: SPLASH SCREEN */}
              {currentScreen === "splash" && (
                <div className="flex-1 flex flex-col items-center justify-center p-6 text-center animate-fade-in bg-[#262625] text-slate-100">
                  <div className="relative mb-4">
                    <Palette className="h-14 w-14 text-emerald-400 animate-pulse" />
                    <Sparkles className="absolute -top-1 -right-1 h-5 w-5 text-amber-500 animate-bounce" />
                  </div>
                  <h1 className="text-3xl font-extrabold tracking-tight text-emerald-400">StyleAI</h1>
                  <p className="mt-1 text-xs font-mono tracking-widest uppercase text-slate-400">{s.splashSubtitle}</p>
                  
                  <button 
                    onClick={() => {
                      if (!onboardingCompleted) {
                        setCurrentScreen("onboarding");
                      } else if (!allConsented) {
                        setCurrentScreen("consent");
                      } else {
                        setCurrentScreen("home");
                      }
                    }}
                    className="absolute bottom-10 bg-slate-800/80 text-white text-[10px] font-mono px-3 py-1 rounded-full border border-slate-700 hover:bg-slate-700"
                  >
                    Skip Splash Timer
                  </button>
                </div>
              )}

              {/* SCREEN 2: ONBOARDING PAGES */}
              {currentScreen === "onboarding" && (
                <div className="flex-1 p-5 flex flex-col justify-between animate-fade-in bg-slate-50 text-slate-800 text-left">
                  <div className="text-right">
                    <button 
                      onClick={() => {
                        setOnboardingCompleted(true);
                        setCurrentScreen("consent");
                      }}
                      className="text-[10px] font-semibold text-slate-400 hover:text-[#8D5B4C]"
                    >
                      {s.skipOnboarding}
                    </button>
                  </div>

                  <div className="my-auto px-2 text-center">
                    {onboardingSlide === 0 && (
                      <div className="animate-fade-in">
                        <div className="w-14 h-14 bg-[#8D5B4C]/10 rounded-full flex items-center justify-center mx-auto mb-5 text-[#8D5B4C]">
                          <Palette className="h-7 w-7" />
                        </div>
                        <h2 className="text-lg font-bold tracking-tight text-slate-800">{s.onboardingTitle1}</h2>
                        <p className="mt-2.5 text-xs text-slate-500 leading-relaxed">{s.onboardingDesc1}</p>
                      </div>
                    )}
                    {onboardingSlide === 1 && (
                      <div className="animate-fade-in">
                        <div className="w-14 h-14 bg-[#8D5B4C]/10 rounded-full flex items-center justify-center mx-auto mb-5 text-[#8D5B4C]">
                          <CheckSquare className="h-7 w-7" />
                        </div>
                        <h2 className="text-lg font-bold tracking-tight text-slate-800">{s.onboardingTitle2}</h2>
                        <p className="mt-2.5 text-xs text-slate-500 leading-relaxed">{s.onboardingDesc2}</p>
                      </div>
                    )}
                    {onboardingSlide === 2 && (
                      <div className="animate-fade-in">
                        <div className="w-14 h-14 bg-[#8D5B4C]/10 rounded-full flex items-center justify-center mx-auto mb-5 text-[#8D5B4C]">
                          <ShieldCheck className="h-7 w-7" />
                        </div>
                        <h2 className="text-lg font-bold tracking-tight text-slate-800">{s.onboardingTitle3}</h2>
                        <p className="mt-2.5 text-xs text-slate-500 leading-relaxed">{s.onboardingDesc3}</p>
                      </div>
                    )}
                  </div>

                  <div className="space-y-3">
                    <div className="flex justify-center gap-1.5">
                      {[0, 1, 2].map(idx => (
                        <div 
                          key={idx} 
                          className={`h-1.5 rounded-full transition-all duration-300 ${onboardingSlide === idx ? "w-5 bg-[#8D5B4C]" : "w-1.5 bg-slate-200"}`}
                        ></div>
                      ))}
                    </div>

                    <button
                      onClick={() => {
                        if (onboardingSlide < 2) {
                          setOnboardingSlide(prev => prev + 1);
                        } else {
                          setOnboardingCompleted(true);
                          setCurrentScreen("consent");
                        }
                      }}
                      className="w-full bg-[#8D5B4C] hover:bg-[#8D5B4C]/95 text-white font-black py-3 rounded-xl text-xs tracking-wider uppercase transition-all flex items-center justify-center gap-1"
                    >
                      {onboardingSlide === 2 ? s.getStarted : s.continue}
                      <ArrowRight className="h-3.5 w-3.5" />
                    </button>
                  </div>
                </div>
              )}

              {/* SCREEN 3: PRIVACY CONSENT GATE */}
              {currentScreen === "consent" && (
                <div className="flex-1 p-5 flex flex-col justify-between animate-fade-in bg-slate-50 text-slate-800 text-left">
                  <div className="space-y-4">
                    <div>
                      <h2 className="text-lg font-extrabold text-[#8D5B4C]">{s.privacyConsent}</h2>
                      <p className="text-xs text-slate-400 mt-0.5">{s.consentSubtitle}</p>
                    </div>

                    <div className="space-y-3">
                      <label className="flex items-start gap-2.5 p-3 rounded-lg border border-slate-300/60 bg-white/40 cursor-pointer shadow-sm select-none">
                        <input 
                          type="checkbox" 
                          checked={consentState.permission}
                          onChange={(e) => setConsentState(prev => ({ ...prev, permission: e.target.checked }))}
                          className="mt-0.5 accent-[#8D5B4C]" 
                        />
                        <span className="text-[11px] text-slate-600 leading-normal">{s.consentCheck1}</span>
                      </label>

                      <label className="flex items-start gap-2.5 p-3 rounded-lg border border-slate-300/60 bg-white/40 cursor-pointer shadow-sm select-none">
                        <input 
                          type="checkbox" 
                          checked={consentState.disclaimer}
                          onChange={(e) => setConsentState(prev => ({ ...prev, disclaimer: e.target.checked }))}
                          className="mt-0.5 accent-[#8D5B4C]" 
                        />
                        <span className="text-[11px] text-slate-600 leading-normal">{s.consentCheck2}</span>
                      </label>

                      <label className="flex items-start gap-2.5 p-3 rounded-lg border border-slate-300/60 bg-white/40 cursor-pointer shadow-sm select-none">
                        <input 
                          type="checkbox" 
                          checked={consentState.noStore}
                          onChange={(e) => setConsentState(prev => ({ ...prev, noStore: e.target.checked }))}
                          className="mt-0.5 accent-[#8D5B4C]" 
                        />
                        <span className="text-[11px] text-slate-600 leading-normal font-medium">{s.consentCheck3}</span>
                      </label>
                    </div>

                    <div className="bg-[#8D5B4C]/10 border border-[#8D5B4C]/20 p-2.5 rounded-lg flex items-start gap-2">
                      <Lock className="h-4 w-4 text-[#8D5B4C] shrink-0 mt-0.5" />
                      <span className="text-[10px] text-slate-650 leading-snug font-medium">
                        {appLang === "EN" ? "GDPR COMPLIANCE: Raw photos are never stored. Exif data is stripped. Purely in-memory session." : "СОГЛАСИЕ GDPR: Исходные фото не хранятся. EXIF-данные стираются. Сессия хранится в памяти."}
                      </span>
                    </div>
                  </div>

                  <button
                    disabled={!allConsented}
                    onClick={() => {
                      setOnboardingCompleted(true);
                      setCurrentScreen("home");
                    }}
                    className="w-full bg-[#8D5B4C] text-white disabled:bg-slate-300 disabled:text-slate-500 font-bold py-3 rounded-xl text-xs tracking-wider uppercase transition-all"
                  >
                    {s.confirmAndContinue}
                  </button>
                </div>
              )}

              {/* SCREEN: HOME SCREEN */}
              {currentScreen === "home" && (
                <div className="flex-1 p-5 flex flex-col justify-between bg-slate-50 overflow-y-auto animate-fade-in text-slate-800 text-left">
                  <div className="space-y-4">
                    {/* Header */}
                    <div className="flex justify-between items-center pb-2 border-b border-rose-100/30">
                      <div>
                        <h2 className="text-xl font-extrabold text-[#8D5B4C] tracking-tight">{s.homeTitle}</h2>
                        <p className="text-[10px] text-slate-400 font-medium mt-0.5">{s.homeSubtitle}</p>
                      </div>
                      <div className="flex items-center gap-1 px-2.25 py-1 bg-[#8D5B4C]/10 rounded-full text-[9px] font-mono text-[#8D5B4C] font-extrabold select-none">
                        <Sparkles className="h-2.5 w-2.5 text-[#8D5B4C]" />
                        <span>{creditBalance.isPro ? "PRO" : `${creditBalance.credits} CR`}</span>
                      </div>
                    </div>

                    {/* Toast alert if any */}
                    {homeToast && (
                      <div className="bg-amber-500 text-slate-950 font-bold text-[9px] px-3 py-1.5 rounded-lg text-center animate-fade-in leading-snug shadow-sm">
                        {homeToast}
                      </div>
                    )}

                    {/* Primary Card: Should I Buy This? */}
                    <div className="bg-white border border-[#8D5B4C]/25 rounded-2xl p-4 text-left shadow-xs flex flex-col gap-2">
                      <div className="flex items-center gap-1.5">
                        <div className="p-1 rounded-md bg-[#8D5B4C]/10 text-[#8D5B4C]">
                          <CheckSquare className="h-4 w-4" />
                        </div>
                        <span className="text-[13px] font-black text-slate-800">{s.homePrimaryTitle}</span>
                      </div>
                      <p className="text-[9.5px] text-slate-500 leading-relaxed">
                        {s.homePrimarySubtitle}
                      </p>
                      <button 
                        onClick={() => {
                          setShoppingStep("Input");
                          setCurrentScreen("shopping_check");
                        }}
                        className="w-full bg-[#8D5B4C] hover:bg-[#8D5B4C]/95 text-white font-bold py-2 px-3 rounded-xl text-[10px] transition-all flex items-center justify-center gap-1 group mt-1"
                      >
                        {s.homePrimaryBtn}
                        <ArrowRight className="h-3 w-3 group-hover:translate-x-0.5 transition-transform" />
                      </button>
                    </div>

                    {/* Secondary Card: What should I wear? */}
                    <div className="bg-white border border-slate-200 rounded-2xl p-4 text-left shadow-xs flex flex-col gap-2">
                      <div className="flex items-center gap-1.5">
                        <div className="p-1 rounded-md bg-indigo-50 text-indigo-600">
                          <Sparkles className="h-4 w-4" />
                        </div>
                        <span className="text-[13px] font-black text-slate-800">{s.homeWearTitle}</span>
                      </div>
                      <p className="text-[9.5px] text-slate-500 leading-relaxed">
                        {s.homeWearSubtitle}
                      </p>
                      <button 
                        onClick={() => {
                          setCurrentScreen("looks");
                        }}
                        className="w-full bg-slate-900 hover:bg-slate-850 text-white font-bold py-2 px-3 rounded-xl text-[10px] transition-all flex items-center justify-center gap-1 mt-1 font-sans"
                      >
                        {s.homeWearBtn}
                      </button>
                    </div>

                    {/* Profile Card: Show 'Create' or 'Ready' */}
                    <div className="bg-white border border-slate-200 rounded-2xl p-4 text-left shadow-xs flex flex-col gap-2">
                      {!hasGeneratedReport ? (
                        <>
                          <div className="flex items-center gap-1.5">
                            <div className="p-1 rounded-md bg-slate-100 text-slate-500">
                              <Palette className="h-4 w-4 text-slate-400" />
                            </div>
                            <span className="text-[12px] font-bold text-slate-700">{s.homeSecTitleNoProfile}</span>
                          </div>
                          <p className="text-[9.5px] text-slate-450 leading-relaxed">
                            {s.homeSecSubtitleNoProfile}
                          </p>
                          <button 
                            onClick={() => setCurrentScreen("upload")}
                            className="w-full bg-slate-100 hover:bg-slate-200 border border-slate-250 text-slate-600 font-bold py-2 px-3 rounded-xl text-[10px] transition-all"
                          >
                            + {s.homeSecBtnNoProfile}
                          </button>
                        </>
                      ) : (
                        <>
                          <div className="flex items-center gap-1.5">
                            <div className="p-1 rounded-md bg-emerald-500/10 text-emerald-600">
                              <Palette className="h-4 w-4 animate-pulse" />
                            </div>
                            <span className="text-[12px] font-bold text-slate-700">{s.homeSecTitleProfileReady}</span>
                          </div>
                          <p className="text-[9.5px] text-[#8D5B4C] font-semibold mt-0.5">
                            💎 {s.homeSecSubtitleProfileReady}
                          </p>
                          <button 
                            onClick={() => setCurrentScreen("report")}
                            className="w-full bg-[#8D5B4C]/10 text-[#8D5B4C] hover:bg-[#8D5B4C]/15 font-bold py-2 px-3 rounded-xl text-[10px] transition"
                          >
                            {s.homeSecBtnProfileReady}
                          </button>
                        </>
                      )}
                    </div>

                    {/* Wardrobe Smart Stats Summary Card */}
                    <div className="bg-[#FAF5F2] border border-[#8D5B4C]/20 rounded-2xl p-4 text-left shadow-xs">
                      <span className="text-[9px] font-extrabold text-[#8D5B4C] tracking-wider uppercase block mb-1">
                        📊 {appLang === "EN" ? "Wardrobe Smart Stats" : "Статистика умного гардероба"}
                      </span>
                      <div className="grid grid-cols-2 gap-2 mt-2 border-b border-dashed border-slate-200/60 pb-2 mb-2">
                        <div className="bg-white p-2 rounded-xl text-center border border-slate-100">
                          <span className="block text-xs font-black text-slate-700">{shoppingHistory.length}</span>
                          <span className="text-[7.5px] text-slate-400 leading-none block mt-0.5">
                            {appLang === "EN" ? "Decisions Tracked" : "Решений в базе"}
                          </span>
                        </div>
                        <div className="bg-white p-2 rounded-xl text-center border border-slate-100">
                          <span className="block text-xs font-black text-emerald-600">
                            {shoppingHistory.filter(d => d.verdict === "Skip").length || 2}
                          </span>
                          <span className="text-[7.5px] text-slate-400 leading-none block mt-0.5">
                            {appLang === "EN" ? "Skipped items" : "Пропущено вещей"}
                          </span>
                        </div>
                      </div>
                      <p className="text-[9.5px] font-medium text-slate-650 leading-relaxed text-center">
                        🛡️ {appLang === "EN" 
                          ? `You skipped ${shoppingHistory.filter(d => d.verdict === "Skip").length} low-versatility items.`
                          : `Вы пропустили ${shoppingHistory.filter(d => d.verdict === "Skip").length} малополезные вещи.`}
                      </p>
                    </div>

                    {/* Privacy reassurance mode */}
                    <div className="bg-slate-100 border border-slate-200/50 p-3 rounded-2xl flex gap-1.5 items-start text-left text-slate-650">
                      <Lock className="h-3.5 w-3.5 text-slate-400 shrink-0 mt-0.5" />
                      <div>
                        <span className="text-[9.5px] font-bold block leading-none">{s.privacyModeTitle}</span>
                        <p className="text-[8px] text-slate-450 mt-1 block leading-normal">{s.privacyModeText}</p>
                      </div>
                    </div>
                  </div>
                </div>
              )}



              {/* SCREEN 4: PHOTO UPLOADS */}
              {currentScreen === "upload" && (
                <div className="flex-1 p-5 flex flex-col justify-between animate-fade-in bg-slate-50 text-slate-800">
                  <div className="space-y-4 text-slate-800">
                    <button 
                      onClick={() => setCurrentScreen("home")}
                      className="inline-flex items-center gap-1.5 text-xs text-[#8D5B4C] font-bold hover:underline mb-1"
                    >
                      <ChevronLeft className="h-3.5 w-3.5" />
                      {appLang === "EN" ? "Back to Home" : "Назад на главную"}
                    </button>

                    <div>
                      <h2 className="text-md font-extrabold text-[#8D5B4C]">{s.uploadTitle}</h2>
                      <p className="text-[10px] text-slate-400 mt-0.5">{s.uploadSubtitle}</p>
                    </div>

                    {/* Optional step explanation reassurance */}
                    <div className="bg-[#8D5B4C]/5 border border-[#8D5B4C]/10 p-2.5 rounded-xl flex items-start gap-2 text-left">
                      <Lock className="h-4 w-4 text-[#8D5B4C] shrink-0 mt-0.5" />
                      <span className="text-[9.5px] text-slate-500 leading-normal font-medium text-left">
                        {s.uploadOptionalNote}
                      </span>
                    </div>

                    {/* Demo State Control Panel containing mock selectors */}
                    <div className="bg-[#8D5B4C]/5 border border-dashed border-[#8D5B4C]/20 p-3 rounded-xl space-y-4 text-left">
                      <div className="text-[9.5px] font-extrabold uppercase tracking-widest text-[#8D5B4C] block font-mono">
                        ⚙️ {s.demoPhotoStatesLabel}
                      </div>

                      {/* Selfie slot selector */}
                      <div className="space-y-1.5">
                        <span className="text-[10px] font-bold text-slate-500 block">{s.selfieLabel}</span>
                        {selfiePhoto ? (
                          <div className="bg-white border border-slate-200 p-2 rounded-lg flex items-center justify-between shadow-xs">
                            <div className="flex items-center gap-1.5">
                              <Camera className="h-3.5 w-3.5 text-slate-400 shrink-0" />
                              <span className="text-[9px] text-slate-600 font-semibold truncate max-w-[150px]">{selfiePhoto.uri}</span>
                            </div>
                            <button onClick={() => setSelfiePhoto(null)} className="text-rose-600 text-[10px] font-bold">Clear</button>
                          </div>
                        ) : (
                          <div className="flex gap-1.5">
                            <button 
                              onClick={() => handleSelectPhoto("selfie", "safe")}
                              className="flex-1 bg-white border border-slate-200 hover:border-[#8D5B4C] p-1.5 rounded-md text-[9.5px] font-medium transition shadow-xs"
                            >
                              ✓ Clean Portrait
                            </button>
                            <button 
                              onClick={() => handleSelectPhoto("selfie", "blurry")}
                              className="bg-amber-50 hover:bg-amber-100 p-1.5 rounded-md text-[9.5px] font-medium transition border border-amber-200 text-amber-800"
                            >
                              Blurry Light
                            </button>
                            <button 
                              onClick={() => handleSelectPhoto("selfie", "restricted")}
                              className="bg-rose-50 hover:bg-rose-100 p-1.5 rounded-md text-[9.5px] font-medium transition border border-rose-100 text-rose-700"
                            >
                              Unsafe
                            </button>
                          </div>
                        )}
                      </div>

                      {/* Body slot selector */}
                      <div className="space-y-1.5">
                        <span className="text-[10px] font-bold text-slate-500 block">{s.fullBodyLabel}</span>
                        {bodyPhoto ? (
                          <div className="bg-white border border-slate-200 p-2 rounded-lg flex items-center justify-between shadow-xs">
                            <div className="flex items-center gap-1.5">
                              <Upload className="h-3.5 w-3.5 text-slate-400 shrink-0" />
                              <span className="text-[9px] text-slate-600 font-semibold truncate max-w-[150px]">{bodyPhoto.uri}</span>
                            </div>
                            <button onClick={() => setBodyPhoto(null)} className="text-rose-600 text-[10px] font-bold">Clear</button>
                          </div>
                        ) : (
                          <div className="flex gap-1.5">
                            <button 
                              onClick={() => handleSelectPhoto("body", "safe")}
                              className="flex-1 bg-white border border-slate-200 hover:border-[#8D5B4C] p-1.5 rounded-md text-[9.5px] font-medium transition shadow-xs"
                            >
                              ✓ Structured Stand
                            </button>
                            <button 
                              onClick={() => handleSelectPhoto("body", "blurry")}
                              className="bg-amber-50 hover:bg-amber-100 p-1.5 rounded-md text-[9.5px] font-medium transition border border-amber-200 text-amber-800"
                            >
                              Shaky Focus
                            </button>
                            <button 
                              onClick={() => handleSelectPhoto("body", "restricted")}
                              className="bg-rose-50 hover:bg-rose-100 p-1.5 rounded-md text-[9.5px] font-medium transition border border-rose-100 text-rose-700"
                            >
                              Banned
                            </button>
                          </div>
                        )}
                      </div>
                    </div>

                    {/* Feedback warnings or blocks */}
                    {photoValidationError && (
                      <div className="bg-rose-50 border border-rose-200 text-rose-900 rounded-lg p-3 text-[10px] flex items-start gap-1.5 leading-snug shadow-sm">
                        <AlertTriangle className="h-4 w-4 text-rose-600 shrink-0" />
                        <span>{photoValidationError}</span>
                      </div>
                    )}

                    {photoValidationWarnings.length > 0 && (
                      <div className="bg-amber-50 border border-amber-250 text-amber-900 rounded-lg p-3 text-[10px] flex items-start gap-1.5 leading-snug">
                        <Info className="h-4 w-4 text-amber-600 shrink-0" />
                        <span>{photoValidationWarnings[0]}</span>
                      </div>
                    )}
                  </div>

                  <button
                    disabled={!selfiePhoto || !bodyPhoto || !!photoValidationError}
                    onClick={runAnalysisSimulation}
                    className="w-full bg-[#8D5B4C] hover:bg-[#8D5B4C]/90 text-white disabled:bg-slate-355 disabled:text-slate-550 font-bold py-3.5 rounded-xl text-xs tracking-wider uppercase transition-all shadow-md"
                  >
                    {s.startAnalysis}
                  </button>
                </div>
              )}

              {/* SCREEN 5: ANALYSIS STEPPER LOADING */}
              {currentScreen === "analysis" && (
                <div className="flex-1 p-6 flex flex-col justify-center items-center bg-[#262625] text-white text-center">
                  <div className="relative mb-6">
                    <RefreshCw className="h-12 w-12 text-emerald-400 animate-spin" />
                  </div>
                  <h3 className="text-md font-bold tracking-tight">
                    {appLang === "EN" ? "Executing Local Analysis" : "Выполнение локального анализа"}
                  </h3>
                  
                  {/* Progress bar */}
                  <div className="w-full bg-slate-800 rounded-full h-1.5 mt-4 max-w-[200px]">
                    <div 
                      className="bg-emerald-500 h-1.5 rounded-full transition-all duration-300"
                      style={{ width: `${analysisProgress}%` }}
                    ></div>
                  </div>

                  <p className="mt-4 text-[10px] text-slate-400 font-mono tracking-wide px-3 leading-relaxed">
                    {analysisStepText}
                  </p>
                </div>
              )}

              {/* SCREEN 6: STYLE REPORT CARD */}
              {currentScreen === "report" && (
                <div className="flex-1 p-4 space-y-3.5 text-slate-800 overflow-y-auto">
                  <div className="flex justify-between items-center">
                    <div>
                      <span className="text-[9px] font-semibold text-slate-500 tracking-widest uppercase block">Personal Analysis Card</span>
                      <h2 className="text-md font-extrabold text-[#8D5B4C] leading-none mt-0.5">{s.reportTitle}</h2>
                    </div>
                  </div>

                  {/* Primary Feature CTA mapping: Should I Buy This */}
                  <div 
                    onClick={() => {
                      setShoppingStep("Input");
                      setCurrentScreen("shopping_check");
                    }}
                    className="bg-gradient-to-br from-[#8D5B4C] to-[#a47364] hover:to-[#8D5B4C] text-white p-3.5 rounded-2xl cursor-pointer shadow-md transition-all border border-[#8D5B4C]/25 flex flex-col gap-1.5 text-left relative overflow-hidden active:scale-[0.98] duration-200"
                  >
                    <div className="flex items-center justify-between z-10">
                      <div className="flex items-center gap-1.5 bg-white/25 px-2 py-0.5 rounded-full">
                        <Sparkles className="h-3 w-3 text-amber-300 animate-pulse" />
                        <span className="text-[8.5px] font-mono tracking-widest uppercase font-bold">{appLang === "EN" ? "NEW FEATURE" : "НОВАЯ ФУНКЦИЯ"}</span>
                      </div>
                      <ChevronRight className="h-4 w-4 opacity-75" />
                    </div>
                    
                    <div className="z-10 mt-0.5">
                      <h3 className="text-xs font-black font-sans tracking-wide">{s.shouldIBuyTitle}</h3>
                      <p className="text-[9.5px] text-orange-50/90 font-medium leading-relaxed mt-0.5">{appLang === "EN" ? "Instantly verify clothes buy-readiness before purchase checkout." : "Мгновенная проверка сочетаемости одежды перед кассой."}</p>
                    </div>
                  </div>

                  {/* recommended palette */}
                  <div className="bg-white border border-slate-100 p-3 rounded-xl shadow-sm text-left">
                    <strong className="text-xs text-[#8D5B4C] block mb-1">{s.colorPaletteSection}</strong>
                    <p className="text-[10px] text-slate-400 leading-normal mb-2">Soft Autumn: featuring rich moss and warm terracotta notes.</p>
                    <div className="grid grid-cols-6 gap-1">
                      {["#E6D5C3", "#CDB49E", "#8D5B4C", "#5C6B5E", "#3A4D39", "#A87C66"].map((hex, i) => (
                        <div key={i} style={{ backgroundColor: hex }} className="h-6 rounded shadow-inner" />
                      ))}
                    </div>
                  </div>

                  {/* silhouettes */}
                  <div className="bg-white border border-slate-100 p-3 rounded-xl shadow-sm space-y-1.5 text-left">
                    <strong className="text-xs text-[#8D5B4C] block">{s.silhouettesSection}</strong>
                    <div className="space-y-1 text-[10px] text-slate-500">
                      <div>• <span className="font-bold text-slate-700">High-Waisted Trousers:</span> Creates visual vertical flow.</div>
                      <div>• <span className="font-bold text-slate-700">Structured Blazers:</span> Establishes balanced shoulder structure.</div>
                    </div>
                  </div>

                  {/* style directions */}
                  <div className="bg-white border border-slate-100 p-3 rounded-xl shadow-sm space-y-1.5 text-left">
                    <strong className="text-xs text-[#8D5B4C] block">{s.styleDirectionsSection}</strong>
                    <div className="space-y-1 text-[10px] text-slate-500">
                      <div>• <span className="font-bold text-slate-700">Minimal Chic:</span> Premium neutral caching and clean layouts.</div>
                      <div>• <span className="font-bold text-slate-700">Soft Classic:</span> Symmetric traditional alignments.</div>
                    </div>
                  </div>

                  {/* Gaps checklist */}
                  <div className="bg-white border border-slate-100 p-3 rounded-xl shadow-sm space-y-2 text-left">
                    <strong className="text-xs text-[#8D5B4C] block">{s.wardrobeGapsSection}</strong>
                    <div className="space-y-1 text-[10px] text-slate-600">
                      <label className="flex items-center gap-2">
                        <input type="checkbox" defaultChecked className="accent-emerald-600" />
                        <span>Neutral Blazer (Camel/Oat)</span>
                      </label>
                      <label className="flex items-center gap-2">
                        <input type="checkbox" className="accent-emerald-600" />
                        <span>Cream Silk Button-Down</span>
                      </label>
                    </div>
                  </div>

                  {/* Body safe disclaimer */}
                  <div className="bg-slate-50 border border-slate-150 p-2.5 rounded-lg flex items-start gap-1.5 leading-snug">
                    <ShieldCheck className="h-4 w-4 text-emerald-600 shrink-0 mt-0.5" />
                    <span className="text-[9px] text-slate-400 font-mono">
                      {s.bodySafetyNotice}
                    </span>
                  </div>
                </div>
              )}

              {/* SCREEN 7: VISUALIZATION BOARD SCREEN WITH SELECTORS ENUM ONLY */}
              {currentScreen === "looks" && (
                <div className="flex-1 flex flex-col overflow-hidden text-slate-800 animate-fade-in scrollbar-none">
                  <div className="p-4.5 bg-white border-b border-slate-100 flex items-center justify-between sticky top-0 z-15 shadow-xs shrink-0">
                    <div>
                      <h2 className="text-sm font-extrabold text-[#8D5B4C]">{s.outfitBoard}</h2>
                      <p className="text-[9px] text-slate-405 leading-none mt-0.5">{s.generateLayoutsSafely}</p>
                    </div>

                    <button 
                      onClick={() => setCurrentScreen("paywall")}
                      className="bg-[#F9EFEA] text-[9.5px] text-[#8D5B4C] font-semibold px-2 py-0.5 rounded-md border border-[#8D5B4C]/20"
                    >
                      {creditBalance.isPro ? "★ Pro" : `${creditBalance.credits} Cr ⏵`}
                    </button>
                  </div>

                  <div className="flex-1 overflow-y-auto p-3.5 space-y-3.5 scrollbar-thin">
                    
                    {/* Banned warnings block */}
                    <div className="bg-slate-50 border border-slate-150 p-2.5 rounded-xl space-y-1">
                      <span className="text-[10px] text-slate-500 leading-relaxed font-semibold">
                        {s.visSafetyNotice}
                      </span>
                    </div>

                    {/* SELECTORS GRID (Zero free-text, preventing prompt injection) */}
                    <div className="bg-white border border-slate-100/80 p-3 rounded-2xl space-y-2.5 shadow-xs text-slate-800 text-left">
                      
                      {/* 1. Occasion */}
                      <div>
                        <span className="text-[9px] font-bold text-slate-400 uppercase tracking-wider block mb-1">1. {s.occasionLabel}</span>
                        <div className="flex gap-1 overflow-x-auto pb-0.5 select-none scrollbar-none">
                          {["Everyday", "Office", "Date", "Travel", "Event"].map(o => (
                            <button 
                              key={o}
                              onClick={() => setSelectedOccasion(o as any)}
                              className={`px-2.5 py-1 rounded text-[9.5px] border shrink-0 font-medium ${
                                selectedOccasion === o ? "bg-[#8D5B4C] text-white border-[#8D5B4C]" : "bg-slate-50 text-slate-500 border-slate-150"
                              }`}
                            >
                              {o}
                            </button>
                          ))}
                        </div>
                      </div>

                      {/* 2. Style */}
                      <div>
                        <span className="text-[9px] font-bold text-slate-400 uppercase tracking-wider block mb-1">2. {s.styleLabel}</span>
                        <div className="flex gap-1 overflow-x-auto pb-0.5 select-none scrollbar-none">
                          {["Minimal", "Classic", "Smart casual", "Feminine", "Streetwear"].map(st => (
                            <button 
                              key={st}
                              onClick={() => setSelectedStyle(st as any)}
                              className={`px-2.5 py-1 rounded text-[9.5px] border shrink-0 font-medium ${
                                selectedStyle === st ? "bg-[#8D5B4C] text-white border-[#8D5B4C]" : "bg-slate-50 text-slate-500 border-slate-150"
                              }`}
                            >
                              {st}
                            </button>
                          ))}
                        </div>
                      </div>

                      {/* 3. Season */}
                      <div>
                        <span className="text-[9px] font-bold text-slate-400 uppercase tracking-wider block mb-1">3. {s.seasonLabel}</span>
                        <div className="flex gap-1 overflow-x-auto pb-0.5 select-none scrollbar-none">
                          {["Spring", "Summer", "Autumn", "Winter"].map(se => (
                            <button 
                              key={se}
                              onClick={() => setSelectedSeason(se as any)}
                              className={`px-2.5 py-1 rounded text-[9.5px] border shrink-0 font-medium ${
                                selectedSeason === se ? "bg-[#8D5B4C] text-white border-[#8D5B4C]" : "bg-slate-50 text-slate-500 border-slate-150"
                              }`}
                            >
                              {se}
                            </button>
                          ))}
                        </div>
                      </div>

                      {/* 4. Formality */}
                      <div>
                        <span className="text-[9px] font-bold text-slate-400 uppercase tracking-wider block mb-1">4. {s.formalityLabel}</span>
                        <div className="flex gap-1 overflow-x-auto pb-0.5 select-none scrollbar-none">
                          {["Casual", "Polished", "Formal"].map(f => (
                            <button 
                              key={f}
                              onClick={() => setSelectedFormality(f as any)}
                              className={`px-2.5 py-1 rounded text-[9.5px] border shrink-0 font-medium ${
                                selectedFormality === f ? "bg-[#8D5B4C] text-white border-[#8D5B4C]" : "bg-slate-50 text-slate-500 border-slate-150"
                              }`}
                            >
                              {f}
                            </button>
                          ))}
                        </div>
                      </div>

                      {/* 5. Color Direction */}
                      <div>
                        <span className="text-[9px] font-bold text-slate-400 uppercase tracking-wider block mb-1">5. {s.colorDirectionLabel}</span>
                        <div className="flex gap-1 overflow-x-auto pb-0.5 select-none scrollbar-none">
                          {["Neutral", "Warm", "Cool", "Soft", "Contrast"].map(cd => (
                            <button 
                              key={cd}
                              onClick={() => setSelectedColorDir(cd as any)}
                              className={`px-2.5 py-1 rounded text-[9.5px] border shrink-0 font-medium ${
                                selectedColorDir === cd ? "bg-[#8D5B4C] text-white border-[#8D5B4C]" : "bg-slate-50 text-slate-500 border-slate-150"
                              }`}
                            >
                              {cd}
                            </button>
                          ))}
                        </div>
                      </div>

                      <button 
                        onClick={triggerSafeVisualization}
                        className="w-full bg-[#8D5B4C] hover:bg-[#8D5B4C]/90 text-white font-black py-2.5 rounded-xl text-xs tracking-wider transition-all shadow-xs block mt-3"
                      >
                        {s.renderOutfit}
                      </button>

                      {generationSuccessAlert && (
                        <div className="bg-emerald-50 border border-emerald-200 text-emerald-800 rounded-lg p-2 text-[9.5px] text-center font-bold">
                          ✓ {appLang === "EN" ? "Outfit reference generated successfully! 1 Credit spent." : "Образ сгенерирован успешно! Списан 1 кредит."}
                        </div>
                      )}
                    </div>

                    {/* Outfit Ideas Grid Lists */}
                    <div className="grid grid-cols-2 gap-2 text-left">
                      {outfits
                        .map(outfit => (
                          <div key={outfit.id} className="bg-white border border-slate-205 rounded-xl overflow-hidden shadow-xs flex flex-col justify-between">
                            <div 
                              style={{ backgroundColor: outfit.colors[0] }} 
                              className="h-12 relative flex items-center justify-center border-b border-slate-100 shadow-inner"
                            >
                              <span className="text-[8px] font-bold text-white bg-slate-900/40 px-2 py-0.5 rounded-full">
                                {outfit.occasion}
                              </span>
                            </div>

                            <div className="p-2 space-y-1 flex-1 flex flex-col justify-between">
                              <div>
                                <h4 className="text-[10px] font-bold text-slate-800 leading-tight line-clamp-1">{outfit.title}</h4>
                                <div className="space-y-0.5 mt-1">
                                  {outfit.items.slice(0, 2).map((itm, i) => (
                                    <span key={i} className="text-[8.5px] text-slate-400 block truncate">• {itm}</span>
                                  ))}
                                </div>
                              </div>

                              <div className="flex items-center justify-between pt-1 border-t border-slate-100">
                                <span className="text-[8.5px] text-[#8D5B4C] font-semibold">{outfit.style}</span>
                                <button 
                                  onClick={() => toggleSaveOutfit(outfit.id)}
                                  className={`text-[8.5px] px-2 py-0.5 rounded font-bold border transition ${
                                    outfit.isSaved 
                                      ? "bg-emerald-500 text-white border-emerald-500" 
                                      : "bg-slate-50 text-slate-600 border-slate-201 hover:bg-slate-100"
                                  }`}
                                >
                                  {outfit.isSaved ? "Saved" : "Save"}
                                </button>
                              </div>
                            </div>
                          </div>
                        ))}
                    </div>
                  </div>
                </div>
              )}

              {/* SCREEN 8: PAYWALL */}
              {currentScreen === "paywall" && (
                <div className="flex-1 p-5 flex flex-col justify-between overflow-y-auto animate-fade-in text-slate-800">
                  <div className="space-y-3.5 text-left">
                    <div className="text-center">
                      <h2 className="text-lg font-extrabold text-[#8D5B4C]">{s.unlockPremium}</h2>
                      <p className="text-[10px] text-slate-400 mt-0.5">{s.unlockSubtitle}</p>
                    </div>

                    <div 
                      onClick={() => {
                        setCreditBalance({ credits: 20, isPro: true });
                        setSettingsFeedback("Plan Complete Style Report credited.");
                        setCurrentScreen("looks");
                      }}
                      className="border border-[#8D5B4C]/30 bg-[#F9EFEA]/30 p-3 rounded-xl cursor-pointer hover:border-[#8D5B4C]"
                    >
                      <div className="flex justify-between items-center text-xs">
                        <strong className="text-[#8D5B4C]">{s.paywallCompleteReport}</strong>
                        <span className="text-xs font-black text-[#8D5B4C]">$9.99</span>
                      </div>
                    </div>

                    <div 
                      onClick={() => {
                        setCreditBalance(prev => ({ ...prev, isPro: true }));
                        setSettingsFeedback("Pro Subscription credited.");
                        setCurrentScreen("looks");
                      }}
                      className="border border-slate-205 bg-white p-3 rounded-xl cursor-pointer hover:border-emerald-500"
                    >
                      <div className="flex justify-between items-center text-xs">
                        <strong className="text-emerald-800">{s.paywallSubPlan}</strong>
                        <span className="text-xs font-black text-emerald-800">$9.99/mo</span>
                      </div>
                    </div>

                    <div 
                      onClick={() => {
                        setCreditBalance(prev => ({ ...prev, credits: prev.credits + 10 }));
                        setSettingsFeedback("10 extra Outfit credits added.");
                        setCurrentScreen("looks");
                      }}
                      className="border border-slate-205 bg-white p-3 rounded-xl cursor-pointer hover:border-emerald-500"
                    >
                      <div className="flex justify-between items-center text-xs">
                        <strong className="text-slate-800">{s.paywallCreditsPack}</strong>
                        <span className="text-xs font-black text-slate-800">$4.99</span>
                      </div>
                    </div>
                  </div>

                  <button 
                    onClick={() => setCurrentScreen("looks")}
                    className="w-full bg-slate-200 text-slate-600 font-bold py-2 rounded-xl text-xs uppercase"
                  >
                    {s.goBackToOutfits}
                  </button>
                </div>
              )}

              {/* SCREEN 8.5: SHOULD I BUY THIS / SHOPPING CHECK SCREEN */}
              {currentScreen === "shopping_check" && (
                <div className="flex-1 p-4.5 flex flex-col justify-between overflow-y-auto animate-fade-in text-slate-800 text-left scrollbar-thin">
                  
                  {/* Header Bar */}
                  <div className="flex items-center gap-2 pb-3.5 border-b border-slate-100 shrink-0">
                    <button 
                      onClick={() => {
                        setShoppingStep("Input");
                        if (hasGeneratedReport) {
                          setCurrentScreen("report");
                        } else {
                          setCurrentScreen("upload");
                        }
                      }}
                      className="text-[#8D5B4C] hover:opacity-75 transition"
                    >
                      <ChevronLeft className="h-5 w-5" />
                    </button>
                    <div>
                      <h2 className="text-[13.5px] font-black text-[#8D5B4C] leading-none">{s.shouldIBuyTitle}</h2>
                      <p className="text-[9px] text-slate-400 mt-0.5 leading-normal">{s.shouldIBuySubtitle}</p>
                    </div>
                  </div>

                  {/* SCREEN FLOW CONTROL */}
                  {!creditBalance.isPro && freeChecksCount <= 0 ? (
                    /* Soft Paywall Block inside the screen */
                    <div className="my-auto space-y-4 py-8 text-center animate-fade-in">
                      <div className="w-14 h-14 bg-rose-50 rounded-full flex items-center justify-center mx-auto border border-rose-100 shadow-sm">
                        <Lock className="h-6 w-6 text-rose-500" />
                      </div>
                      <div className="space-y-1.5">
                        <h3 className="text-sm font-black text-slate-800">
                          {appLang === "EN" ? "More shopping checks are part of Pro." : "Дополнительные проверки покупок доступны в Pro."}
                        </h3>
                        <p className="text-[10px] text-slate-400 max-w-[260px] mx-auto leading-relaxed">
                          {appLang === "EN" 
                            ? "You reached the limit of 3 free wardrobe checks. Upgrade to create unlimited evaluations." 
                            : "Вы исчерпали лимит из 3 бесплатных проверок вещей. Перейдите на Pro для безлимитного анализа."}
                        </p>
                      </div>
                      <button 
                        onClick={() => setCurrentScreen("paywall")}
                        className="bg-[#8D5B4C] hover:bg-[#8D5B4C]/90 text-white font-bold py-2.5 px-6 rounded-xl text-[10.5px] tracking-wider uppercase transition shadow-sm"
                      >
                        {appLang === "EN" ? "View Pro Upgrade" : "Посмотреть Pro тарифы"}
                      </button>
                    </div>
                  ) : (
                    /* Active check content depending on state */
                    <div className="flex-1 flex flex-col justify-between mt-3 space-y-4">
                      
                      {shoppingStep === "Input" && (
                        <div className="space-y-4 animate-fade-in flex-1">
                          
                          {/* Limit indicator badges */}
                          <div className="flex items-center justify-between text-[9px] font-mono bg-slate-50 border border-slate-150 p-2 rounded-lg">
                            <span className="text-slate-500 uppercase tracking-wider font-semibold">
                              {appLang === "EN" ? "Evaluation Tier:" : "Канал анализа:"}
                            </span>
                            {creditBalance.isPro ? (
                              <span className="text-emerald-600 font-bold">★ {appLang === "EN" ? "UNLIMITED PRO" : "ПРО БЕЗЛИМИТ"}</span>
                            ) : (
                              <span className="text-[#8D5B4C] font-semibold">{freeChecksCount} {appLang === "EN" ? "Checks Left" : "проверки осталось"}</span>
                            )}
                          </div>

                          {/* MOCK GARMENT PHOTO SELECTOR */}
                          <div className="space-y-1.5 bg-white border border-slate-100 p-3 rounded-xl shadow-xs">
                            <span className="text-[10.5px] font-bold text-slate-700 block mb-1">
                              {appLang === "EN" ? "Select Garment Photo Source:" : "Выберите источник фото вещи:"}
                            </span>
                            
                            <div className="grid grid-cols-3 gap-1.5 select-none">
                              <button 
                                onClick={() => {
                                  setSelectedMockPhotoOpt("basic");
                                  setSelectedCategory("Outerwear");
                                  setSelectedGarmentContext("Basic");
                                  setSelectedGarmentColorDir("Warm");
                                }}
                                className={`p-2 rounded-lg border text-[8px] font-bold text-center leading-normal transition flex flex-col items-center gap-1 ${
                                  selectedMockPhotoOpt === "basic" 
                                    ? "bg-[#F9EFEA] border-[#8D5B4C] text-[#8D5B4C]" 
                                    : "bg-slate-50 border-slate-155 text-slate-500 hover:bg-slate-100"
                                }`}
                              >
                                <span className="block text-[11px]">🧥</span>
                                {appLang === "EN" ? "Tan Knit Outfile" : "Базовый свитер"}
                              </button>

                              <button 
                                onClick={() => {
                                  setSelectedMockPhotoOpt("trend");
                                  setSelectedCategory("Outerwear");
                                  setSelectedGarmentContext("Trend");
                                  setSelectedGarmentColorDir("Contrast");
                                }}
                                className={`p-2 rounded-lg border text-[8px] font-bold text-center leading-normal transition flex flex-col items-center gap-1 ${
                                  selectedMockPhotoOpt === "trend" 
                                    ? "bg-[#F9EFEA] border-[#8D5B4C] text-[#8D5B4C]" 
                                    : "bg-slate-50 border-slate-155 text-slate-500 hover:bg-slate-100"
                                }`}
                              >
                                <span className="block text-[11px]">🧥</span>
                                {appLang === "EN" ? "Neon Pink Puffer" : "Розовый пуховик"}
                              </button>

                              <button 
                                onClick={() => {
                                  setSelectedMockPhotoOpt("low_versatility");
                                  setSelectedCategory("Dress");
                                  setSelectedGarmentContext("Occasion");
                                  setSelectedGarmentColorDir("Contrast");
                                }}
                                className={`p-2 rounded-lg border text-[8px] font-bold text-center leading-normal transition flex flex-col items-center gap-1 ${
                                  selectedMockPhotoOpt === "low_versatility" 
                                    ? "bg-[#F9EFEA] border-[#8D5B4C] text-[#8D5B4C]" 
                                    : "bg-slate-50 border-slate-155 text-slate-500 hover:bg-slate-100"
                                }`}
                              >
                                <span className="block text-[11px]">👗</span>
                                {appLang === "EN" ? "Sequin Mini Skirt" : "Блестящая мини"}
                              </button>
                            </div>

                            {/* Simulated Photo Preview */}
                            <div className="mt-2.5 p-2 bg-slate-50 border border-slate-150 rounded-lg flex items-center gap-3">
                              <div className="w-9 h-9 rounded bg-[#8D5B4C]/15 flex items-center justify-center text-xs shadow-inner shrink-0">
                                {selectedMockPhotoOpt === "basic" && "🟫"}
                                {selectedMockPhotoOpt === "trend" && "🟥"}
                                {selectedMockPhotoOpt === "low_versatility" && "⬜"}
                              </div>
                              <div className="overflow-hidden">
                                <span className="text-[9.5px] font-bold text-slate-600 block leading-tight truncate">
                                  {selectedMockPhotoOpt === "basic" ? "garment_basic_tan_knit.jpg" : selectedMockPhotoOpt === "trend" ? "garment_fluorescent_puffer.jpg" : "garment_sequin_party_skirt.jpg"}
                                </span>
                                <span className="text-[8px] text-slate-400 block font-mono leading-none mt-0.5">Size: 412 KB (SAFE CACHE)</span>
                              </div>
                            </div>
                          </div>

                          {/* SELECTION CONTROL 1: CATEGORY */}
                          <div className="space-y-1.5">
                            <span className="text-[10px] font-bold text-slate-400 uppercase tracking-wider block">1. {s.categoryLabel}</span>
                            <div className="flex gap-1 overflow-x-auto pb-1 scrollbar-none select-none">
                              {(["Top", "Bottom", "Dress", "Outerwear", "Shoes", "Bag", "Accessory"] as const).map(cat => (
                                <button 
                                  key={cat}
                                  onClick={() => setSelectedCategory(cat)}
                                  className={`px-3 py-1 rounded text-[9.5px] border shrink-0 font-medium transition ${
                                    selectedCategory === cat 
                                      ? "bg-[#8D5B4C] border-[#8D5B4C] text-white shadow-inner" 
                                      : "bg-slate-55 border-slate-155 text-slate-600 hover:bg-slate-100"
                                  }`}
                                >
                                  {appLang === "EN" ? cat : {
                                    Top: "Верх", Bottom: "Низ", Dress: "Платье", Outerwear: "Куртка", Shoes: "Обувь", Bag: "Сумка", Accessory: "Очки"
                                  }[cat]}
                                </button>
                              ))}
                            </div>
                          </div>

                          {/* SELECTION CONTROL 2: CONTEXT */}
                          <div className="space-y-1.5">
                            <span className="text-[10px] font-bold text-slate-400 uppercase tracking-wider block">
                              {appLang === "EN" ? "2. Item Context Guide" : "2. Назначение и контекст"}
                            </span>
                            <div className="grid grid-cols-2 gap-1.5 select-none">
                              {(["Basic", "Trend", "Occasion", "Similar", "Unsure"] as const).map(ctx => (
                                <button
                                  key={ctx}
                                  onClick={() => setSelectedGarmentContext(ctx)}
                                  className={`px-2.5 py-1.5 rounded-lg text-[9px] font-semibold text-left border transition leading-snug ${
                                    selectedGarmentContext === ctx
                                      ? "bg-[#8D5B4C]/10 border-[#8D5B4C] text-[#8D5B4C]"
                                      : "bg-white border-slate-155 text-slate-600 hover:bg-slate-50"
                                  }`}
                                >
                                  🌱 {appLang === "EN" ? {
                                    Basic: "Basic wardrobe item",
                                    Trend: "Trend item",
                                    Occasion: "Occasion-only item",
                                    Similar: "Similar to something I own",
                                    Unsure: "Low-versatility item"
                                  }[ctx] : {
                                    Basic: "Базовая вещь",
                                    Trend: "Трендовая вещь",
                                    Occasion: "Вещь для редкого случая",
                                    Similar: "Похожа на то, что уже есть",
                                    Unsure: "Малополезная вещь"
                                  }[ctx]}
                                </button>
                              ))}
                            </div>
                          </div>

                          {/* SELECTION CONTROL 3: COLOR DIRECTION */}
                          <div className="space-y-1.5">
                            <span className="text-[10px] font-bold text-slate-400 uppercase tracking-wider block">
                              {appLang === "EN" ? "3. Garment Color Aura" : "3. Цветовой оттенок"}
                            </span>
                            <div className="flex gap-1 overflow-x-auto pb-1 scrollbar-none select-none">
                              {(["Neutral", "Warm", "Cool", "Soft", "Contrast"] as const).map(colorD => (
                                <button
                                  key={colorD}
                                  onClick={() => setSelectedGarmentColorDir(colorD)}
                                  className={`px-3 py-1 rounded text-[9.5px] border shrink-0 font-medium transition ${
                                    selectedGarmentColorDir === colorD
                                      ? "bg-[#8D5B4C] border-[#8D5B4C] text-white shadow-inner"
                                      : "bg-slate-55 border-slate-155 text-slate-600 hover:bg-slate-100"
                                  }`}
                                >
                                  🎨 {appLang === "EN" ? colorD : {
                                    Neutral: "Нейтральный", Warm: "Теплый", Cool: "Холодный", Soft: "Мягкий", Contrast: "Контрастный"
                                  }[colorD]}
                                </button>
                              ))}
                            </div>
                          </div>

                          <button 
                            onClick={runShoppingCheckSimulation}
                            className="w-full bg-[#8D5B4C] hover:bg-[#8D5B4C]/95 text-white font-black py-3 rounded-xl text-xs tracking-wider uppercase transition-all shadow-md block mt-4"
                          >
                            {appLang === "EN" ? "Verify Purchase Readiness" : "Оценить готовность к покупке"}
                          </button>
                        </div>
                      )}

                      {/* STATE 2: LOADING */}
                      {shoppingStep === "Loading" && (
                        <div className="flex-1 flex flex-col justify-center items-center py-10 text-center animate-fade-in space-y-5">
                          <div className="relative">
                            <RefreshCw className="h-10 w-10 text-[#8D5B4C] animate-spin" />
                          </div>
                          
                          <div className="space-y-1">
                            <h3 className="text-xs font-black tracking-wider uppercase text-[#8D5B4C]">{s.analyzeGarmentMsg}</h3>
                            <p className="text-[10px] text-slate-400 font-mono tracking-wide">
                              {shoppingLoadingStepText}
                            </p>
                          </div>

                          {/* Linear progress metric */}
                          <div className="w-full bg-slate-100 rounded-full h-1 mt-2 max-w-[160px]">
                            <div 
                              className="bg-[#8D5B4C] h-1 rounded-full transition-all duration-200"
                              style={{ width: `${shoppingLoadingProgress}%` }}
                            ></div>
                          </div>
                        </div>
                      )}

                      {/* STATE 3: RESULT */}
                      {shoppingStep === "Result" && shoppingResult && (
                        <div className="flex-1 animate-fade-in space-y-3.5">
                          
                          {/* VERDICT COLOR-CODED HEAD STRIP */}
                          <div className={`p-3 rounded-xl border flex items-center justify-between text-left ${
                            shoppingResult.verdict === "Good match" 
                              ? "bg-emerald-500/10 border-emerald-500/25 text-emerald-800" 
                              : shoppingResult.verdict === "Maybe" 
                                ? "bg-amber-500/10 border-amber-500/25 text-amber-800" 
                                : "bg-rose-500/10 border-rose-500/25 text-rose-800"
                          }`}>
                            <div>
                              <span className="text-[8.5px] uppercase font-mono tracking-wider block font-bold">
                                {s.verdictLabel} / {appLang === "EN" ? "Verdict Evaluation" : "Паспорт вещи"}
                              </span>
                              <strong className="text-xs font-bold font-mono tracking-tight block mt-0.5 uppercase">
                                {shoppingResult.verdict === "Good match" && s.verdictGood}
                                {shoppingResult.verdict === "Maybe" && s.verdictMaybe}
                                {shoppingResult.verdict === "Skip" && s.verdictSkip}
                              </strong>
                            </div>
                            <span className="text-lg">
                              {shoppingResult.verdict === "Good match" && "✓"}
                              {shoppingResult.verdict === "Maybe" && "⚠️"}
                              {shoppingResult.verdict === "Skip" && "❌"}
                            </span>
                          </div>

                          {/* EVALUATION BULLETS */}
                          <div className="bg-white border border-slate-100 p-3 rounded-xl space-y-2 text-left text-xs leading-relaxed shadow-xs">
                            <strong className="text-[10px] text-[#8D5B4C] uppercase tracking-wider block border-b border-slate-100 pb-1">
                              {s.whyLabel}
                            </strong>
                            
                            <div className="space-y-2 text-[9.5px] text-slate-600">
                              <div>🧬 <span className="font-bold text-slate-700">{appLang === "EN" ? "Color Synergy:" : "Цветовая гармония:"}</span> {shoppingResult.explanationColor}</div>
                              <div>📐 <span className="font-bold text-slate-700">{appLang === "EN" ? "Form Geometry:" : "Геометрия формы:"}</span> {shoppingResult.explanationSilhouette}</div>
                              <div>📦 <span className="font-bold text-slate-700">{appLang === "EN" ? "Capsule Necessity:" : "Необходимость в гардеробе:"}</span> {shoppingResult.explanationCapsule}</div>
                              <div>💫 <span className="font-bold text-slate-700">{appLang === "EN" ? "Versatility Multiplier:" : "Прогноз сочетаемости:"}</span> {shoppingResult.explanationVersatility}</div>
                              <div className="bg-[#F9EFEA] border border-[#8D5B4C]/15 text-[#8D5B4C] p-2 rounded-lg font-bold">
                                ↳ {shoppingResult.outfitCountEstimate}
                              </div>
                            </div>
                          </div>

                          {/* ADVICE CRADLE */}
                          <div className="bg-slate-50 border border-slate-150 p-2.5 rounded-xl text-left">
                            <strong className="text-[9.5px] text-slate-500 block uppercase mb-1">{s.shoppingAdviceLabel}</strong>
                            <p className="text-[9.5px] text-slate-450 leading-normal">{shoppingResult.shoppingAdvice}</p>
                          </div>

                          {/* ACTION BUTTON CHEST */}
                          <div className="space-y-1.5 mt-3 select-none shrink-0">
                            <button 
                              onClick={() => {
                                const newLog = {
                                  id: `check_${Date.now()}`,
                                  category: selectedCategory,
                                  context: selectedGarmentContext,
                                  colorDir: selectedGarmentColorDir,
                                  verdict: shoppingResult.verdict,
                                  date: "Today"
                                };
                                setShoppingHistory(prev => [newLog, ...prev]);
                                setSettingsFeedback(appLang === "EN" ? "Decision saved inside local in-memory style history log." : "Паспорт вещи занесен во локальный архив истории.");
                                setTimeout(() => setSettingsFeedback(null), 3000);
                              }}
                              className="w-full bg-[#8D5B4C] hover:bg-[#8D5B4C]/95 text-white font-bold py-2.5 rounded-xl text-[10px] flex items-center justify-center gap-1.5 transition leading-none shadow-xs"
                            >
                              📂 {appLang === "EN" ? "Save to local history" : "Сохранить результат"}
                            </button>

                            <button
                              onClick={() => setShoppingStep("Input")}
                              className="w-full bg-slate-100 hover:bg-slate-200 text-slate-705 font-bold py-2 rounded-xl text-[10px] transition"
                            >
                              🔄 {appLang === "EN" ? "Check another garment" : "Проверить другой предмет"}
                            </button>

                            <button 
                              onClick={() => {
                                setShoppingStep("Input");
                                if (hasGeneratedReport) {
                                  setCurrentScreen("report");
                                } else {
                                  setCurrentScreen("upload");
                                }
                              }}
                              className="w-full bg-slate-200 hover:bg-slate-250 text-slate-600 font-bold py-2 rounded-xl text-[10px] transition"
                            >
                              🏠 {appLang === "EN" ? "Go back to Home" : "Вернуться на главную"}
                            </button>
                          </div>
                        </div>
                      )}

                    </div>
                  )}

                </div>
              )}

              {/* SCREEN 9: DECISIONS SCREEN */}
              {currentScreen === "decisions" && (
                <div className="flex-1 p-5 flex flex-col justify-between overflow-y-auto animate-fade-in text-slate-800 text-left scrollbar-thin">
                  <div className="space-y-4">
                    <div>
                      <h2 className="text-md font-extrabold text-[#8D5B4C]">{s.historyTitle}</h2>
                      <p className="text-[10px] text-slate-400 mt-0.5">{s.historySubtitle}</p>
                    </div>

                    {/* Stats Panel */}
                    <div className="bg-[#FAF5F2] border border-[#8D5B4C]/20 p-3 rounded-2xl space-y-1.5 shadow-xs">
                      <span className="text-[9.5px] font-extrabold text-[#8D5B4C] tracking-wider uppercase block">
                        📊 {appLang === "EN" ? "Interactive Wardrobe Statistics" : "Статистика сохраненных решений"}
                      </span>
                      <div className="text-[10px] text-slate-700 leading-normal space-y-1 font-medium">
                        <div className="flex items-center gap-1.5">
                          <CheckCircle className="h-3 w-3 text-emerald-500 shrink-0" />
                          <span>
                            {appLang === "EN" 
                              ? "You skipped 2 low-versatility items." 
                              : "Вы пропустили 2 малополезные вещи."}
                          </span>
                        </div>
                        <div className="flex items-center gap-1.5">
                          <CheckCircle className="h-3 w-3 text-emerald-500 shrink-0" />
                          <span>
                            {appLang === "EN" 
                              ? "3 saved buying decisions this week." 
                              : "3 сохраненных решения о покупках за неделю."}
                          </span>
                        </div>
                      </div>
                    </div>

                    <div className="space-y-2">
                      <span className="text-[9px] font-bold text-slate-400 uppercase tracking-widest block">{s.activeReports}</span>
                      {hasGeneratedReport ? (
                        <div className="bg-white border border-slate-205 p-3 rounded-xl shadow-xs text-slate-700 space-y-2">
                          <div className="flex justify-between items-start text-xs">
                            <div>
                              <strong className="text-[#8D5B4C]">{appLang === "EN" ? "Soft Autumn Report" : "Отчет: Мягкая Осень"}</strong>
                              <span className="text-[9px] text-slate-400 block font-mono">Size matches: 4 item counts</span>
                            </div>
                            <button 
                              onClick={() => setCurrentScreen("report")}
                              className="bg-slate-100 text-[#8D5B4C] hover:bg-slate-200 font-bold px-2 py-1 rounded text-[9px]"
                            >
                              {appLang === "EN" ? "View" : "Открыть"}
                            </button>
                          </div>
                        </div>
                      ) : (
                        <div className="bg-slate-100 border border-slate-200 border-dashed p-4 rounded-xl text-center text-[10px] text-slate-400 leading-snug">
                          {s.emptyHistory}
                        </div>
                      )}
                    </div>

                    {/* Saved Looks List */}
                    <div className="space-y-2">
                      <span className="text-[9px] font-bold text-slate-400 uppercase tracking-widest block">{s.savedLooks} ({savedLooks.length})</span>
                      {savedLooks.length > 0 ? (
                        <div className="space-y-1.5 max-h-[130px] overflow-y-auto pr-1">
                          {savedLooks.map(look => (
                            <div key={look.id} className="bg-white border border-slate-150 p-2 rounded-xl flex items-center justify-between text-slate-700 shadow-xs">
                              <div className="flex items-center gap-2">
                                <span className="text-base">🧥</span>
                                <span className="text-[10px] font-semibold truncate max-w-[150px]">{look.title}</span>
                              </div>
                              <button 
                                onClick={() => {
                                  setSavedLooks(prev => prev.filter(l => l.id !== look.id));
                                  setOutfits(prev => prev.map(o => o.id === look.id.replace("look_", "") ? { ...o, isSaved: false } : o));
                                }}
                                className="text-rose-650 text-[10px] font-extrabold hover:underline"
                              >
                                {appLang === "EN" ? "Delete" : "Удалить"}
                              </button>
                            </div>
                          ))}
                        </div>
                      ) : (
                        <div className="bg-slate-100 border border-slate-200 border-dashed p-4 rounded-xl text-center text-[10px] text-slate-400 leading-snug">
                          {appLang === "EN" ? "No saved outfit profiles yet." : "Сохраненные образы отсутствуют."}
                        </div>
                      )}
                    </div>

                    {/* Shopping Checks History List */}
                    <div className="space-y-2 pt-2 border-t border-slate-150">
                      <span className="text-[9px] font-bold text-slate-400 uppercase tracking-widest block">
                        {appLang === "EN" ? "Shopping Checks" : "Проверки покупок"} ({shoppingHistory.length})
                      </span>
                      {shoppingHistory.length > 0 ? (
                        <div className="space-y-3 pr-1">
                          {shoppingHistory.map(item => (
                            <div key={item.id} className="bg-white border border-slate-200 p-3 rounded-2xl space-y-2.5 shadow-xs relative text-left">
                              <div className="flex items-start justify-between">
                                <div className="space-y-0.5">
                                  <div className="flex items-center gap-1.5 flex-wrap">
                                    <span className="text-[11.5px] font-bold text-slate-800">
                                      {appLang === "EN" ? item.category : {
                                        Top: "Верх", Bottom: "Низ", Dress: "Платье", Outerwear: "Верхняя одежда", Shoes: "Обувь", Bag: "Сумка", Accessory: "Аксессуар"
                                      }[item.category as any] || item.category}
                                    </span>
                                    <span className="text-[8px] text-slate-400 font-medium">({item.date === "Today" ? (appLang === "EN" ? "Today" : "Сегодня") : item.date === "Yesterday" ? (appLang === "EN" ? "Yesterday" : "Вчера") : (appLang === "EN" ? "3 days ago" : "3 дня назад")})</span>
                                  </div>
                                  <span className="text-[8.5px] text-slate-400 block leading-none">
                                    {appLang === "EN" ? "Context: " : "Тип: "} {appLang === "EN" ? item.context : {
                                      Basic: "Базовая вещь", Trend: "Трендовая вещь", Occasion: "Вещь для редкого случая", Similar: "Похожа на то, что уже есть", Unsure: "Малополезная вещь"
                                    }[item.context as any] || item.context}
                                  </span>
                                </div>

                                <span className={`text-[8.5px] font-black px-2 py-0.5 rounded-md font-mono shrink-0 uppercase tracking-wide border ${
                                  item.verdict === "Good match" 
                                    ? "bg-emerald-50 text-emerald-800 border-emerald-200" 
                                    : item.verdict === "Maybe" 
                                      ? "bg-amber-50 text-amber-800 border-amber-200" 
                                      : "bg-rose-50 text-rose-800 border-rose-200"
                                }`}>
                                  {item.verdict === "Good match" && (appLang === "EN" ? s.verdictGood : s.verdictGood)}
                                  {item.verdict === "Maybe" && (appLang === "EN" ? "MAYBE" : s.verdictMaybe)}
                                  {item.verdict === "Skip" && (appLang === "EN" ? s.verdictSkip : s.verdictSkip)}
                                </span>
                              </div>

                              <p className="text-[10px] text-slate-600 leading-relaxed italic bg-slate-50 p-2 rounded-lg">
                                "{appLang === "EN" ? item.reasonEn : item.reasonRu}"
                              </p>

                              <div className="flex items-center justify-between text-[9px] text-[#8D5B4C] font-semibold border-t border-slate-100 pt-2 bg-white grow flex-wrap gap-1.5">
                                <span className="font-mono">
                                  ⚡ {appLang === "EN" ? `Creates up to ${item.outfitsCount} outfits` : `Создает до ${item.outfitsCount} образов`}
                                </span>
                                <div className="flex items-center gap-2">
                                  <button 
                                    onClick={() => setSelectedDecisionForDetails(item)}
                                    className="text-slate-500 hover:text-slate-800 underline font-extrabold uppercase text-[8.5px] tracking-wider"
                                  >
                                    {appLang === "EN" ? "Details" : "Инфо"}
                                  </button>
                                  <button 
                                    onClick={() => setShoppingHistory(prev => prev.filter(h => h.id !== item.id))}
                                    className="text-rose-600 hover:underline font-extrabold uppercase text-[8.5px] tracking-wider"
                                  >
                                    {appLang === "EN" ? "Delete" : "Удалить"}
                                  </button>
                                </div>
                              </div>
                            </div>
                          ))}
                        </div>
                      ) : (
                        <div className="bg-slate-100 border border-slate-200 border-dashed p-3 rounded-xl text-center text-[9.5px] text-slate-400 leading-snug">
                          {appLang === "EN" ? "No checked garments in history yet." : "Вы еще не сохраняли отзывы о вещах в историю."}
                        </div>
                      )}
                    </div>

                  </div>
                </div>
              )}

              {/* SCREEN 10: SETTINGS SCREEN */}
              {currentScreen === "settings" && (
                <div className="flex-1 p-5 flex flex-col justify-between overflow-y-auto animate-fade-in text-slate-800 text-left">
                  <div className="space-y-4">
                    <div>
                      <h2 className="text-md font-extrabold text-[#8D5B4C]">{s.appSettings}</h2>
                      <p className="text-[10px] text-slate-400 mt-0.5">{s.settingsSubtitle}</p>
                    </div>

                    {settingsFeedback && (
                      <div className="bg-emerald-50 border border-emerald-250 text-emerald-800 rounded-lg p-2 text-[9.5px] text-center font-bold">
                        {settingsFeedback}
                      </div>
                    )}

                    {/* Choose Language */}
                    <div className="bg-white rounded-xl border border-slate-150 p-3 space-y-2">
                      <span className="text-[9px] font-bold text-slate-450 uppercase tracking-wider block">{s.settingsLanguageSection}</span>
                      <div className="flex gap-2">
                        <button 
                          onClick={() => setAppLang("EN")}
                          className={`flex-1 font-bold py-1.5 text-xs rounded transition ${appLang === "EN" ? "bg-emerald-500 text-slate-950" : "bg-slate-150 text-slate-600"}`}
                        >
                          English
                        </button>
                        <button 
                          onClick={() => setAppLang("RU")}
                          className={`flex-1 font-bold py-1.5 text-xs rounded transition ${appLang === "RU" ? "bg-emerald-500 text-slate-950" : "bg-slate-150 text-slate-600"}`}
                        >
                          Русский
                        </button>
                      </div>
                    </div>

                    <div className="bg-white rounded-xl border border-slate-150 p-3 space-y-2.5">
                      <div className="space-y-0.5">
                        <strong className="text-xs text-slate-700 block">{appLang === "EN" ? "GDPR Compliance Rules" : "Защита данных по GDPR"}</strong>
                        <p className="text-[9.5px] text-slate-500 leading-normal">
                          {appLang === "EN" 
                            ? "All user-captured silhouettes, colors, and shape variables exist exclusively in volatile GPU buffers." 
                            : "Все пользовательские силуэты, параметры цвета и формы хранятся исключительно во временной памяти."}
                        </p>
                      </div>
                    </div>

                    {/* Reset options with confirmations */}
                    <div className="space-y-2">
                      <button 
                        onClick={() => {
                          setSettingsFeedback("Style data exported: StyleReport-SoftAutumn-Export.json");
                          setTimeout(() => setSettingsFeedback(null), 3000);
                        }}
                        className="w-full bg-[#8D5B4C] hover:bg-[#8D5B4C]/90 text-white font-bold py-2 rounded-lg text-[10px] flex items-center justify-center gap-1 shadow-xs"
                      >
                        <Download className="h-3.5 w-3.5" />
                        {s.settingsExportButton}
                      </button>

                      {/* 1. Clear style data button */}
                      <button 
                        onClick={() => setDialogOpen("styleData")}
                        className="w-full bg-slate-100 hover:bg-slate-200 text-slate-700 font-bold py-2 rounded-lg text-[10px]"
                      >
                        {s.settingsBtnWipeLooks}
                      </button>

                      {/* 2. Reset app onboarding button */}
                      <button 
                        onClick={() => setDialogOpen("onboarding")}
                        className="w-full bg-rose-50 hover:bg-rose-100 text-rose-800 font-bold py-2 rounded-lg text-[10px]"
                      >
                        {s.settingsBtnResetOnboarding}
                      </button>
                    </div>

                    {/* Hidden Developer Checklist categories */}
                    {devReadinessVisible && (
                      <div className="bg-slate-900 text-slate-100 p-3 rounded-xl border border-slate-800 text-left space-y-2 mt-4 select-text">
                        <strong className="text-xs text-amber-400 block">🛠️ Developer MVP Readiness Checklist</strong>
                        
                        <div className="text-[9.5px] text-slate-400 space-y-1">
                          <div className="font-bold text-slate-350">1. Native Android:</div>
                          <div>✓ Jetpack Compose screen counterparts for all screens</div>
                          <div>✓ ViewModel state flows mapped securely</div>
                          <div>✓ Decoupled clean StyleRepository implementations</div>

                          <div className="font-bold text-slate-355 mt-1">2. AI Safety:</div>
                          <div>✓ Forced user-consent disclaimers enabled</div>
                          <div>✓ Free-text prompt removed; controlled enums only</div>
                          <div>✓ Banned words checked at initialization model level</div>

                          <div className="font-bold text-slate-355 mt-1">3. GDPR & Privacy:</div>
                          <div>✓ EXIF metadata stripper code defined</div>
                          <div>✓ Raw photo cache wipes automatically on complete</div>
                          <div>✓ Zero plain file logs (using SafeLogger)</div>
                        </div>

                        <button 
                          onClick={() => {
                            setVersionTapCount(0);
                            setDevReadinessVisible(false);
                          }}
                          className="bg-slate-800 text-slate-400 text-[9px] px-2 py-0.5 rounded"
                        >
                          Hide Dev Mode
                        </button>
                      </div>
                    )}
                  </div>

                  {/* Footer clickable version tapper */}
                  <div className="text-center space-y-0.5 pt-4">
                    <span 
                      onClick={handleVersionTapped}
                      className="text-[10px] text-slate-400 block cursor-pointer select-none"
                    >
                      App Version: 1.0.0-MVP (Build-26 - Tap {5 - Math.min(5, versionTapCount)} times for Dev Checklist)
                    </span>
                    <span className="text-[9px] text-slate-400 block">StyleAI Studio Copyright © 2026</span>
                  </div>
                </div>
              )}

              {/* SCREEN 11: MY WARDROBE SCREEN */}
              {currentScreen === "wardrobe" && (
                <div className="flex-1 p-5 flex flex-col justify-between overflow-y-auto animate-fade-in text-slate-800 text-left scrollbar-thin">
                  <div className="space-y-4">
                    {/* Header with stats counters */}
                    <div className="flex justify-between items-start">
                      <div>
                        <h2 className="text-md font-extrabold text-[#8D5B4C]">{s.wardrobeTitle}</h2>
                        <p className="text-[10px] text-slate-400 mt-0.5">
                          {appLang === "EN" 
                            ? `${wardrobeItems.filter(i => !i.isWishlist).length} active items · ${wardrobeItems.filter(i => i.isWishlist).length} wishlist gaps`
                            : `${wardrobeItems.filter(i => !i.isWishlist).length} активных вещей · ${wardrobeItems.filter(i => i.isWishlist).length} в списке желаний`}
                        </p>
                      </div>
                      <button 
                        onClick={() => setShowAddItemModal(true)}
                        className="bg-[#8D5B4C] text-white hover:bg-[#8D5B4C]/95 font-bold px-3 py-1.5 rounded-xl text-[10px] shadow-xs flex items-center gap-1"
                      >
                        <span>+</span> {appLang === "EN" ? "Add Item" : "Добавить"}
                      </button>
                    </div>

                    {/* Filters bar */}
                    <div className="flex gap-2.5 overflow-x-auto pb-1 scrollbar-none text-[9.5px] font-bold">
                      {["All", "Top", "Bottom", "Dress", "Outerwear", "Shoes", "Bag", "Accessory", "Rarely Worn", "Wishlist"].map(cat => {
                        const isSelected = wardrobeFilter === cat;
                        return (
                          <button
                            key={cat}
                            onClick={() => setWardrobeFilter(cat)}
                            className={`px-3 py-1 rounded-full whitespace-nowrap transition border ${
                              isSelected
                                ? "bg-[#8D5B4C] border-[#8D5B4C] text-white"
                                : "bg-white border-slate-200 text-slate-600 hover:border-slate-300"
                            }`}
                          >
                            {appLang === "EN" ? cat : {
                              All: "Все", Top: "Верх", Bottom: "Низ", Dress: "Платья", Outerwear: "Верхняя одежда", Shoes: "Обувь", Bag: "Сумки", Accessory: "Аксессуары", "Rarely Worn": "Редкие вещи", Wishlist: "Желания"
                            }[cat] || cat}
                          </button>
                        );
                      })}
                    </div>

                    {/* Wardrobe Grid */}
                    {(() => {
                      const filtered = wardrobeItems.filter(item => {
                        if (wardrobeFilter === "All") return true;
                        if (wardrobeFilter === "Rarely Worn") return item.rarelyWorn === true;
                        if (wardrobeFilter === "Wishlist") return item.isWishlist === true;
                        return item.category === wardrobeFilter;
                      });

                      if (filtered.length === 0) {
                        return (
                          <div className="bg-slate-50 border border-slate-200 border-dashed p-6 text-center rounded-2xl space-y-2 mt-4">
                            <Archive className="h-8 w-8 text-slate-300 mx-auto" />
                            <h3 className="text-xs font-bold text-slate-600">
                              {appLang === "EN" ? "No items match your category" : "Нет вещей в этой категории"}
                            </h3>
                            <p className="text-[9.5px] text-slate-400">
                              {appLang === "EN" ? "Try selecting a different filter or create items manually." : "Выберите другой фильтр или добавьте новую вещь."}
                            </p>
                          </div>
                        );
                      }

                      return (
                        <div className="grid grid-cols-2 gap-3 mt-1">
                          {filtered.map(item => {
                            return (
                              <div
                                key={item.id}
                                onClick={() => {
                                  setSelectedWardrobeItem(item);
                                  setCurrentScreen("item_detail");
                                }}
                                className="bg-white border border-slate-200 rounded-2xl p-3 flex flex-col justify-between hover:border-[#8D5B4C]/45 transition cursor-pointer shadow-xs select-none relative group"
                              >
                                {item.isWishlist && (
                                  <span className="absolute top-2 right-2 bg-amber-500/10 text-amber-700 text-[7px] font-black px-1.5 py-0.5 rounded uppercase border border-amber-500/20 font-mono">
                                    {appLang === "EN" ? "Wishlist" : "В вишлисте"}
                                  </span>
                                )}
                                {item.rarelyWorn && (
                                  <span className="absolute top-2 right-2 bg-rose-50 text-rose-600 text-[7px] font-bold px-1.5 py-0.5 rounded uppercase border border-rose-250 font-mono">
                                    ⚠️ {appLang === "EN" ? "Unused" : "Неизвестная ценность"}
                                  </span>
                                )}
                                <div className="space-y-2">
                                  {/* Thumbnail placeholder with matching hex color */}
                                  <div className="w-full h-20 rounded-xl flex items-center justify-center text-4xl shadow-xxs border border-slate-100" style={{ backgroundColor: `${item.hexColor}25` }}>
                                    <span>{item.imageEmoji}</span>
                                  </div>
                                  <div className="space-y-0.5 text-left">
                                    <span className="text-[10px] font-extrabold text-[#8D5B4C] uppercase tracking-wider block font-mono">
                                      {appLang === "EN" ? item.category : {
                                        Top: "Верх", Bottom: "Низ", Dress: "Платье", Outerwear: "Верхняя одежда", Shoes: "Обувь", Bag: "Саквояж", Accessory: "Украшение"
                                      }[item.category] || item.category}
                                    </span>
                                    <h3 className="text-[11.5px] font-extrabold text-slate-800 tracking-tight leading-tight line-clamp-1 group-hover:text-[#8D5B4C]">
                                      {item.name}
                                    </h3>
                                    <div className="flex items-center gap-1 mt-1 text-[8px] font-mono text-slate-450">
                                      <span className="inline-block w-1.5 h-1.5 rounded-full" style={{ backgroundColor: item.hexColor }}></span>
                                      <span>{item.color} · Score {item.versatilityScore}</span>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            );
                          })}
                        </div>
                      );
                    })()}
                  </div>
                </div>
              )}

              {/* SCREEN 12: WARDROBE ITEM DETAIL SCREEN */}
              {currentScreen === "item_detail" && selectedWardrobeItem && (
                <div className="flex-1 p-5 flex flex-col justify-between overflow-y-auto animate-fade-in text-slate-800 text-left scrollbar-thin">
                  <div className="space-y-4">
                    {/* Header bar */}
                    <div className="flex items-center gap-2 pb-2 border-b border-rose-100/30">
                      <button 
                        onClick={() => {
                          setSelectedWardrobeItem(null);
                          setCurrentScreen("wardrobe");
                        }}
                        className="p-1 rounded-lg text-slate-500 hover:text-slate-800 bg-slate-100"
                      >
                        <ChevronLeft className="h-4 w-4" />
                      </button>
                      <div>
                        <h2 className="text-sm font-extrabold text-slate-800 leading-tight">{s.itemDetailTitle}</h2>
                        <span className="text-[9px] font-mono text-[#8D5B4C] uppercase font-black">{selectedWardrobeItem.name}</span>
                      </div>
                    </div>

                    {/* Card Layout Banner */}
                    <div className="bg-white border border-slate-200 rounded-2xl p-4 flex flex-col items-center gap-3 relative shadow-xs text-center">
                      <div className="absolute top-3 left-3 flex items-center gap-1 text-[8.5px] font-mono text-slate-450 bg-slate-50 px-2 py-0.5 rounded-md border border-slate-150">
                        <span className="w-1.5 h-1.5 rounded-full inline-block" style={{ backgroundColor: selectedWardrobeItem.hexColor }}></span>
                        <span>{selectedWardrobeItem.color}</span>
                      </div>
                      <div className="absolute top-3 right-3">
                        <span className={`text-[8px] font-black font-mono border px-2 py-0.5 rounded-md uppercase tracking-wider ${
                          selectedWardrobeItem.versatilityScore >= 80 
                            ? "bg-emerald-50 text-emerald-600 border-emerald-250" 
                            : selectedWardrobeItem.versatilityScore >= 50 
                              ? "bg-amber-50 text-amber-600 border-amber-250" 
                              : "bg-rose-50 text-rose-600 border-rose-250"
                        }`}>
                          Score {selectedWardrobeItem.versatilityScore}
                        </span>
                      </div>

                      <div className="w-24 h-24 rounded-2xl flex items-center justify-center text-6xl shadow-xxs border border-slate-100 mt-3" style={{ backgroundColor: `${selectedWardrobeItem.hexColor}25` }}>
                        <span>{selectedWardrobeItem.imageEmoji}</span>
                      </div>

                      <div className="space-y-1">
                        <span className="text-[9px] font-mono text-[#8D5B4C] uppercase tracking-widest font-extrabold block">
                          Category: {selectedWardrobeItem.category}
                        </span>
                        <h3 className="text-md font-extrabold text-slate-800 tracking-tight leading-snug">
                          {selectedWardrobeItem.name}
                        </h3>
                        <p className="text-[9px] text-slate-450">
                          {appLang === "EN" ? `Suggested temperature: ${selectedWardrobeItem.colorDir} / Recommended Season: ${selectedWardrobeItem.season}` : `Совместимость оттенков: ${selectedWardrobeItem.colorDir === "Warm" ? "Теплая палитра" : selectedWardrobeItem.colorDir === "Cool" ? "Холодная палитра" : "Нейтральная палитра"} / Сезон: ${selectedWardrobeItem.season}`}
                        </p>
                      </div>
                    </div>

                    {/* Synergy stats list */}
                    <div className="bg-[#FAF5F2] border border-[#8D5B4C]/20 rounded-2xl p-4 text-left shadow-xs space-y-3">
                      <span className="text-[9px] font-extrabold text-[#8D5B4C] tracking-widest uppercase block mb-1">
                        🌟 {appLang === "EN" ? "Style Evaluation Matrix" : "Матрица оценки стиля"}
                      </span>
                      <div className="space-y-2.5 text-[10px] text-slate-700 leading-normal font-medium">
                        <div className="flex gap-2">
                          <span className="text-emerald-600 shrink-0 font-bold">✓</span>
                          <div>
                            <strong className="text-slate-800 block text-[10.5px]">
                              {appLang === "EN" ? "Aesthetic Compatibility" : "Совместимость с гардеробом"}
                            </strong>
                            <p className="text-[9px] text-slate-500">
                              {selectedWardrobeItem.versatilityScore >= 80 
                                ? (appLang === "EN" ? "High-performance wardrobe component. Links natively to more than 6 active styles." : "Высокоэффективный компонент. Совместим с большинством вещей в шкафу.")
                                : (appLang === "EN" ? "Trend component. Recommended to wear in specialized combinations." : "Трендовый элемент. Рекомендуется носить в точечных сочетаниях.")}
                            </p>
                          </div>
                        </div>

                        <div className="flex gap-2 pt-2 border-t border-dashed border-slate-200">
                          <span className="text-[#8D5B4C] shrink-0 font-bold">💎</span>
                          <div>
                            <strong className="text-slate-800 block text-[10.5px]">
                              {appLang === "EN" ? "Wardrobe Role Type" : "Роль в гардеробе"}
                            </strong>
                            <div className="flex gap-1.5 mt-1">
                              {selectedWardrobeItem.versatilityScore >= 80 ? (
                                <span className="bg-emerald-50 text-emerald-700 text-[8px] font-bold px-2 py-0.5 rounded border border-emerald-200">
                                  {s.roleCore}
                                </span>
                              ) : selectedWardrobeItem.isWishlist ? (
                                <span className="bg-amber-50 text-amber-700 text-[8px] font-bold px-2 py-0.5 rounded border border-amber-200">
                                  {s.roleDuplicate}
                                </span>
                              ) : (
                                <span className="bg-rose-50 text-rose-700 text-[8px] font-bold px-2 py-0.5 rounded border border-[#8D5B4C]/20">
                                  {s.roleAccent}
                                </span>
                              )}
                              {selectedWardrobeItem.rarelyWorn && (
                                <span className="bg-red-50 text-red-700 text-[8px] font-bold px-2 py-0.5 rounded border border-red-200">
                                  {s.roleUnused}
                                </span>
                              )}
                            </div>
                          </div>
                        </div>

                        <div className="flex gap-2 pt-2 border-t border-dashed border-slate-200">
                          <span className="text-[#8D5B4C] shrink-0 font-bold">📋</span>
                          <div>
                            <strong className="text-slate-800 block text-[10.5px]">
                              {s.outfitFormulasTitle}
                            </strong>
                            <p className="text-[9px] text-slate-500 italic mt-0.5">
                              {appLang === "EN" 
                                ? `Formula: 1. ${selectedWardrobeItem.name} + Clean Straight denim + White Sneakers` 
                                : `Формула: 1. ${selectedWardrobeItem.name} + Прямой деним + Светлая обувь`}
                            </p>
                          </div>
                        </div>
                      </div>
                    </div>

                    {/* Smart Suggestion Decision list */}
                    <div className="bg-slate-50 border border-slate-200 p-3.5 rounded-2xl flex items-start gap-2.5 text-left text-slate-650">
                      <Lock className="h-4 w-4 text-slate-400 shrink-0 mt-0.5" />
                      <div>
                        <span className="text-[10px] font-extrabold text-slate-700 uppercase tracking-wide block">
                          {s.decisionLabelSet}
                        </span>
                        <div className="flex gap-1.5 flex-wrap mt-2">
                          {["Keep", "Replace", "Archive", "Wish"].map(act => {
                            const isKeep = act === "Keep" && !selectedWardrobeItem.rarelyWorn && !selectedWardrobeItem.isWishlist;
                            const isReplace = act === "Replace" && selectedWardrobeItem.rarelyWorn;
                            const isArchive = act === "Archive" && selectedWardrobeItem.rarelyWorn;
                            const isWish = act === "Wish" && selectedWardrobeItem.isWishlist;
                            const isActive = isKeep || isReplace || isArchive || isWish;
                            return (
                              <button
                                key={act}
                                className={`text-[8px] font-bold px-2.5 py-1 rounded-full border ${
                                  isActive
                                    ? "bg-[#8D5B4C] text-white border-[#8D5B4C]"
                                    : "bg-white border-slate-200 text-slate-500"
                                }`}
                              >
                                {act === "Keep" ? s.descKeep : act === "Replace" ? s.descReplace : act === "Archive" ? s.descSell : s.descWish}
                              </button>
                            );
                          })}
                        </div>
                      </div>
                    </div>

                    {/* Delete item button */}
                    <button
                      onClick={() => {
                        setWardrobeItems(prev => prev.filter(i => i.id !== selectedWardrobeItem.id));
                        setSelectedWardrobeItem(null);
                        setCurrentScreen("wardrobe");
                        setSettingsFeedback(appLang === "EN" ? "Garment discarded from physical wardrobe list." : "Предмет исключен из вашего гардероба.");
                        setTimeout(() => setSettingsFeedback(null), 3500);
                      }}
                      className="w-full border border-rose-300 hover:bg-rose-50 text-rose-750 text-[10px] font-bold py-2 px-3 rounded-xl transition flex items-center justify-center gap-1.5 mt-2"
                    >
                      <Trash2 className="h-3.5 w-3.5" />
                      {s.deleteItemBtn}
                    </button>
                  </div>
                </div>
              )}

              {/* SCREEN 13: STYLE RULE SYSTEM (PROFILE) SCREEN */}
              {currentScreen === "profile" && (
                <div className="flex-1 p-5 flex flex-col justify-between overflow-y-auto animate-fade-in text-slate-800 text-left scrollbar-thin">
                  <div className="space-y-4">
                    {/* Header bar */}
                    <div>
                      <h2 className="text-md font-extrabold text-[#8D5B4C]">{s.profileTitle}</h2>
                      <p className="text-[10px] text-slate-400 mt-0.5">{s.historySubtitle}</p>
                    </div>

                    {/* User Profile Status Badge Header */}
                    <div className="bg-white border border-slate-200 p-4 rounded-2xl flex items-center justify-between shadow-xs">
                      <div className="flex items-center gap-2.5">
                        <div className="w-9 h-9 bg-[#8D5B4C]/10 text-[#8D5B4C] rounded-lg flex items-center justify-center text-lg shrink-0 font-bold font-mono">
                          SA
                        </div>
                        <div>
                          <h3 className="text-xs font-black text-slate-800">Style Profile</h3>
                          <span className={`text-[8.5px] font-bold font-mono uppercase tracking-wide flex items-center gap-1 leading-none mt-1 ${
                            hasGeneratedReport ? "text-emerald-600" : "text-amber-600"
                          }`}>
                            <span className="w-1.5 h-1.5 rounded-full inline-block animate-pulse" style={{ backgroundColor: hasGeneratedReport ? "#10B981" : "#F59E0B" }}></span>
                            {hasGeneratedReport ? s.completedStatusReady : s.completedStatusNotReady}
                          </span>
                        </div>
                      </div>
                      <div className="text-right">
                        <span className="text-[8px] font-bold text-slate-440 font-mono tracking-widest block leading-none">VERSION</span>
                        <strong className="text-[11px] font-mono text-slate-700 font-black block mt-1">v1.2-Smart</strong>
                      </div>
                    </div>

                    {/* Content depends on hasGeneratedReport */}
                    {hasGeneratedReport ? (
                      <div className="space-y-4">
                        {/* Interactive Seasonal Palette Rules */}
                        <div className="bg-white border border-slate-200 p-4 rounded-2xl space-y-3 text-slate-700 shadow-xs">
                          <span className="text-[9px] font-extrabold text-[#8D5B4C] tracking-wider uppercase block border-b border-rose-50 pb-1">
                            🎨 {appLang === "EN" ? "Seasonal Color Rules" : "Правила сезонной палитры"}
                          </span>
                          <div className="grid grid-cols-2 gap-2 text-[9.5px]">
                            <div className="bg-slate-50 p-2.5 rounded-xl border border-slate-100">
                              <span className="text-slate-400 block font-mono text-[8px] leading-none mb-1">RECOMMENDED SEASON</span>
                              <strong className="text-slate-700 font-bold block">Soft Autumn (Мягкая Осень)</strong>
                            </div>
                            <div className="bg-slate-50 p-2.5 rounded-xl border border-slate-100">
                              <span className="text-slate-400 block font-mono text-[8px] leading-none mb-1">COLOR TEMPERATURE</span>
                              <strong className="text-[#8D5B4C] font-bold block">Neutral Warm (Нейтрально-теплая)</strong>
                            </div>
                          </div>

                          <div className="space-y-2 pt-1">
                            <h4 className="text-[10px] font-extrabold text-[#8D5B4C]">{s.primaryColorsLabel}</h4>
                            <div className="flex gap-1.5 flex-wrap">
                              {["#8D5B4C", "#CDB49E", "#E6D5C3", "#5C6B5E", "#3A4D39", "#A87C66"].map(c => (
                                <div key={c} className="flex flex-col items-center gap-1 group">
                                  <div className="w-6 h-6 rounded-lg shadow-xxs border border-slate-200 transition-all group-hover:scale-105" style={{ backgroundColor: c }}></div>
                                  <span className="text-[6.5px] font-mono font-bold text-slate-400 uppercase tracking-tighter">{c}</span>
                                </div>
                              ))}
                            </div>
                          </div>

                          <div className="space-y-2 pt-1">
                            <h4 className="text-[10px] font-extrabold text-amber-700">{s.colorsCarefullyLabel}</h4>
                            <div className="flex gap-1.5 flex-wrap">
                              {["#39FF14", "#FF007F", "#00FFFF"].map(c => (
                                <span key={c} className="inline-flex items-center gap-1 px-2 py-0.5 rounded-lg bg-rose-50 border border-rose-200/50 text-[8.5px] font-mono text-rose-700">
                                  <span className="w-1.5 h-1.5 rounded-full inline-block" style={{ backgroundColor: c }}></span>
                                  {c === "#39FF14" ? (appLang === "EN" ? "Neons" : "Неоны") : c === "#FF007F" ? (appLang === "EN" ? "Hot Pink" : "Фуксия") : (appLang === "EN" ? "Cyan" : "Синий циан")}
                                </span>
                              ))}
                            </div>
                          </div>
                        </div>

                        {/* Silhouette and cut rules */}
                        <div className="bg-white border border-slate-200 p-4 rounded-2xl space-y-2.5 text-slate-705 shadow-xs text-left">
                          <span className="text-[9px] font-extrabold text-[#8D5B4C] tracking-wider uppercase block border-b border-rose-50 pb-1">
                            📏 {s.silhouetteRulesTitle}
                          </span>
                          <div className="text-[10px] leading-relaxed space-y-1.5 font-medium text-slate-650">
                            <div className="flex items-start gap-2">
                              <span className="text-emerald-600 font-bold shrink-0 mt-0.5">✓</span>
                              <span>{appLang === "EN" ? "Opt for Fluid drape lines and high-rise structures" : "Рекомендуются плавные линии кроя и высокая посадка"}</span>
                            </div>
                            <div className="flex items-start gap-2">
                              <span className="text-emerald-600 font-bold shrink-0 mt-0.5">✓</span>
                              <span>{appLang === "EN" ? "Incorporate tailored open blazers to align body coordinates" : "Структурированные жакеты поддерживают линию плеч"}</span>
                            </div>
                            <div className="flex items-start gap-2">
                              <span className="text-emerald-600 font-bold shrink-0 mt-0.5">✓</span>
                              <span>{appLang === "EN" ? "Prefer boatnecks and wide lapel structures" : "Вырез лодочкой и воротники с отворотами гармоничны"}</span>
                            </div>
                          </div>
                        </div>

                        {/* Interactive Smart Shopping Rules */}
                        <div className="bg-[#FAF5F2] border border-[#8D5B4C]/20 p-4 rounded-2xl space-y-2.5 text-slate-700 shadow-xs text-left">
                          <span className="text-[9px] font-extrabold text-[#8D5B4C] tracking-wider uppercase block border-b border-slate-200 pb-1">
                            🛡️ {s.shoppingRulesTitle}
                          </span>
                          <ul className="text-[9.5px] leading-relaxed space-y-2 font-medium text-slate-700">
                            <li className="flex items-start gap-1.5">
                              <strong className="text-[#8D5B4C] font-black shrink-0 font-mono">01/</strong>
                              <span>{s.rule1}</span>
                            </li>
                            <li className="flex items-start gap-1.5">
                              <strong className="text-[#8D5B4C] font-black shrink-0 font-mono">02/</strong>
                              <span>{s.rule2}</span>
                            </li>
                            <li className="flex items-start gap-1.5">
                              <strong className="text-[#8D5B4C] font-black shrink-0 font-mono">03/</strong>
                              <span>{s.rule3}</span>
                            </li>
                            <li className="flex items-start gap-1.5">
                              <strong className="text-[#8D5B4C] font-black shrink-0 font-mono">04/</strong>
                              <span>{s.rule4}</span>
                            </li>
                          </ul>
                        </div>
                      </div>
                    ) : (
                      <div className="bg-slate-50 border border-slate-200 border-dashed p-6 rounded-2xl text-center space-y-3 text-slate-650 mt-2">
                        <Palette className="h-8 w-8 text-slate-300 mx-auto" />
                        <h3 className="text-xs font-bold text-slate-700">
                          {appLang === "EN" ? "No style rules generated yet" : "Правила стиля пока отсутствуют"}
                        </h3>
                        <p className="text-[9.5px] text-slate-450 leading-relaxed px-4">
                          {s.noProfileDataText}
                        </p>
                        <button 
                          onClick={() => setCurrentScreen("upload")}
                          className="bg-[#8D5B4C] text-white hover:bg-[#8D5B4C]/95 font-bold py-1.5 px-4 rounded-xl text-[10px] shadow-xxs transition-all mt-2"
                        >
                          + {appLang === "EN" ? "Generate now" : "Сгенерировать правила"}
                        </button>
                      </div>
                    )}

                    {/* App Settings, Reset Controls & language within profile view */}
                    <div className="bg-white border border-slate-200 p-4 rounded-2xl space-y-3.5 shadow-xs text-left">
                      <span className="text-[9px] font-extrabold text-[#111] tracking-wider uppercase block border-b border-slate-100 pb-1">
                        🛠️ {appLang === "EN" ? "System Controls & Privacy" : "Управление приватностью"}
                      </span>

                      {/* Language selection switches inside Profile screen */}
                      <div className="flex justify-between items-center text-xs">
                        <span className="font-bold text-slate-700">{s.settingsLanguageSection}</span>
                        <div className="flex gap-1.5 bg-slate-100 p-0.5 rounded-lg border">
                          {["EN", "RU"].map(lang => (
                            <button
                              key={lang}
                              onClick={() => setAppLang(lang as any)}
                              className={`px-3 py-1 font-mono text-[9.5px] font-extrabold rounded-md transition-all ${
                                appLang === lang 
                                  ? "bg-white text-slate-800 shadow-xxs" 
                                  : "text-slate-450 hover:text-slate-700"
                              }`}
                            >
                              {lang}
                            </button>
                          ))}
                        </div>
                      </div>

                      {/* Action buttons list */}
                      <div className="grid grid-cols-1 gap-2 pt-1 text-[9px] font-bold">
                        <button 
                          onClick={handleWipeStyleDataOnly}
                          className="w-full bg-slate-50 hover:bg-slate-100 border border-slate-200 text-slate-600 py-1.5 px-3 rounded-lg text-left transition flex items-center justify-between"
                        >
                          <span>{s.settingsBtnWipeLooks}</span>
                          <span className="text-slate-400 font-normal">→</span>
                        </button>

                        <button 
                          onClick={handleResetAppOnboardingOnly}
                          className="w-full bg-slate-50 hover:bg-slate-100 border border-slate-200 text-slate-600 py-1.5 px-3 rounded-lg text-left transition flex items-center justify-between"
                        >
                          <span>{s.settingsBtnResetOnboarding}</span>
                          <span className="text-slate-400 font-normal">→</span>
                        </button>

                        <button 
                          onClick={handleFullReset}
                          className="w-full bg-rose-50 hover:bg-rose-100/50 border border-rose-200 text-rose-700 py-1.5 px-3 rounded-lg text-left transition flex items-center justify-between"
                        >
                          <span>{s.settingsBtnWipeAll}</span>
                          <Trash2 className="h-3 w-3 text-rose-450" />
                        </button>
                      </div>

                      {/* Toast report triggers */}
                      {settingsFeedback && (
                        <div className="bg-emerald-50 border border-emerald-250 text-emerald-750 p-2 text-[9px] rounded-lg text-center font-bold animate-fade-in leading-snug">
                          {settingsFeedback}
                        </div>
                      )}
                    </div>
                  </div>
                </div>
              )}

              {/* ADD WARDROBE ITEM MODAL OVERLAY */}
              {showAddItemModal && (
                <div className="absolute inset-0 bg-slate-900/60 backdrop-blur-xs flex items-center justify-center p-5 z-40 select-none animate-fade-in">
                  <div className="bg-white border border-[#8D5B4C]/20 w-full max-w-xs rounded-2xl p-4 text-left shadow-2xl relative animate-scale-up text-slate-800">
                    <div className="flex justify-between items-center pb-2 border-b border-rose-100/20">
                      <h3 className="text-xs font-black text-[#8D5B4C] uppercase tracking-wider">{s.addManualTitle}</h3>
                      <button 
                        onClick={() => setShowAddItemModal(false)}
                        className="p-1 text-slate-400 hover:text-slate-605 rounded-lg"
                      >
                        <X className="h-4 w-4" />
                      </button>
                    </div>

                    <form 
                      onSubmit={(e) => {
                        e.preventDefault();
                        if (!newItmName.trim()) return;

                        const generatedId = `w_custom_${Date.now()}`;
                        const randomScores = [72, 78, 81, 85, 90, 93];
                        const randomScore = randomScores[Math.floor(Math.random() * randomScores.length)];
                        const emojiMap = {
                          Top: "👕", Bottom: "👖", Dress: "👗", Outerwear: "🧥", Shoes: "👟", Bag: "👜", Accessory: "👑"
                        };
                        const hexMap = {
                          Top: "#8D5B4C", Bottom: "#2C3E50", Dress: "#5C6B5E", Outerwear: "#E6D5C3", Shoes: "#FFFFFF", Bag: "#111111", Accessory: "#D4AF37"
                        };

                        const newItem = {
                          id: generatedId,
                          name: newItmName,
                          category: newItmCategory,
                          color: newItmColor || (appLang === "EN" ? "Selected Shade" : "Выбранный оттенок"),
                          colorDir: newItmColorDir,
                          season: appLang === "EN" ? "All-season" : "Всесезонная",
                          versatilityScore: randomScore,
                          outfitsCount: Math.floor(randomScore / 10),
                          rarelyWorn: false,
                          isWishlist: newItmWishlist,
                          imageEmoji: emojiMap[newItmCategory] || "👕",
                          hexColor: hexMap[newItmCategory] || "#8D5B4C"
                        };

                        setWardrobeItems(prev => [newItem, ...prev]);
                        setShowAddItemModal(false);

                        // clear inputs
                        setNewItmName("");
                        setNewItmColor("");
                        setNewItmWishlist(false);

                        setSettingsFeedback(appLang === "EN" ? "Custom item created safely inside physical local wardrobe cache!" : "Новая вещь добавлена в физическую базу вашего гардероба!");
                        setTimeout(() => setSettingsFeedback(null), 3000);
                      }}
                      className="space-y-3 mt-3 text-slate-700 text-[10px]"
                    >
                      <div>
                        <label className="block text-[8px] font-bold text-slate-450 uppercase mb-1">Item Title</label>
                        <input 
                          type="text"
                          value={newItmName}
                          onChange={(e) => setNewItmName(e.target.value)}
                          placeholder={s.itemNamePlaceholder}
                          required
                          className="w-full px-3 py-2 bg-slate-50 border border-slate-200 rounded-xl focus:outline-none focus:border-[#8D5B4C] text-[10px] text-slate-800"
                        />
                      </div>

                      <div className="grid grid-cols-2 gap-2">
                        <div>
                          <label className="block text-[8px] font-bold text-slate-450 uppercase mb-1">Category</label>
                          <select
                            value={newItmCategory}
                            onChange={(e) => setNewItmCategory(e.target.value as any)}
                            className="w-full px-2 py-1.5 bg-slate-50 border border-slate-200 rounded-xl focus:outline-none focus:border-[#8D5B4C] text-[9.5px]"
                          >
                            {["Top", "Bottom", "Dress", "Outerwear", "Shoes", "Bag", "Accessory"].map(cat => (
                              <option key={cat} value={cat}>{cat}</option>
                            ))}
                          </select>
                        </div>

                        <div>
                          <label className="block text-[8px] font-bold text-slate-450 uppercase mb-1">Color Shade</label>
                          <input
                            type="text"
                            value={newItmColor}
                            onChange={(e) => setNewItmColor(e.target.value)}
                            placeholder={s.itemColorPlaceholder}
                            className="w-full px-2 py-1.5 bg-slate-50 border border-slate-200 rounded-xl focus:outline-none focus:border-[#8D5B4C] text-[9.5px] text-slate-805"
                          />
                        </div>
                      </div>

                      <div className="grid grid-cols-2 gap-2">
                        <div>
                          <label className="block text-[8px] font-bold text-slate-450 uppercase mb-1">Color Dir</label>
                          <select
                            value={newItmColorDir}
                            onChange={(e) => setNewItmColorDir(e.target.value as any)}
                            className="w-full px-2 py-1.5 bg-slate-50 border border-slate-200 rounded-xl focus:outline-none focus:border-[#8D5B4C] text-[9.5px]"
                          >
                            {["Neutral", "Warm", "Cool", "Soft", "Contrast"].map(dir => (
                              <option key={dir} value={dir}>{dir}</option>
                            ))}
                          </select>
                        </div>

                        <div className="flex flex-col justify-end">
                          <label className="flex items-center gap-1.5 font-bold text-slate-600 select-none py-1.5 cursor-pointer">
                            <input 
                              type="checkbox"
                              checked={newItmWishlist}
                              onChange={(e) => setNewItmWishlist(e.target.checked)}
                              className="rounded text-[#8D5B4C] focus:ring-[#8D5B4C] h-3.5 w-3.5 border-slate-250 cursor-pointer"
                            />
                            <span>{appLang === "EN" ? "Wishlist Gap" : "В вишлист"}</span>
                          </label>
                        </div>
                      </div>

                      <div className="flex gap-2 pt-2 border-t border-rose-100/10">
                        <button
                          type="button"
                          onClick={() => setShowAddItemModal(false)}
                          className="w-1/2 border border-slate-200 hover:bg-slate-50 text-slate-650 font-bold py-2 rounded-xl text-[10px] text-center"
                        >
                          {s.addManualCloseBtn}
                        </button>
                        <button
                          type="submit"
                          className="w-1/2 bg-[#8D5B4C] hover:bg-[#8D5B4C]/95 text-white font-bold py-2 rounded-xl text-[10px] text-center"
                        >
                          {s.addManualSubmitBtn}
                        </button>
                      </div>
                    </form>
                  </div>
                </div>
              )}

            </div>

            {/* ANDROID DEVICE BOTTOM NAV BAR STRIP */}
            {onboardingCompleted && ["home", "upload", "report", "looks", "decisions", "settings", "paywall", "shopping_check", "wardrobe", "profile", "item_detail"].includes(currentScreen) && (
              <div className={`py-2 px-3 shadow-2xl border-t select-none z-30 shrink-0 ${
                simulatorThemeMode === "light" 
                  ? "bg-white text-slate-600 border-slate-200" 
                  : "bg-[#1f1f1f] text-slate-300 border-[#2b2b2a]"
              }`}>
                <div className="flex justify-around items-center">
                  <button 
                    onClick={() => setCurrentScreen("home")}
                    className={`flex flex-col items-center gap-0.5 px-2 py-1 text-slate-400 transition hover:text-[#8D5B4C] ${currentScreen === "home" ? "text-[#8D5B4C] font-bold" : "opacity-90"}`}
                  >
                    <Home className="h-4 w-4" />
                    <span className="text-[8.5px] font-bold">{appLang === "EN" ? "Home" : "Главная"}</span>
                  </button>

                  <button 
                    onClick={() => setCurrentScreen("wardrobe")}
                    className={`flex flex-col items-center gap-0.5 px-2 py-1 text-slate-400 transition hover:text-[#8D5B4C] ${(currentScreen === "wardrobe" || currentScreen === "item_detail") ? "text-[#8D5B4C] font-bold" : "opacity-90"}`}
                  >
                    <Layers className="h-4 w-4" />
                    <span className="text-[8.5px] font-bold">{appLang === "EN" ? "Wardrobe" : "Гардероб"}</span>
                  </button>

                  <button 
                    onClick={() => setCurrentScreen("decisions")}
                    className={`flex flex-col items-center gap-0.5 px-2 py-1 text-slate-400 transition hover:text-[#8D5B4C] ${(currentScreen === "decisions" || currentScreen === "shopping_check") ? "text-[#8D5B4C] font-bold" : "opacity-90"}`}
                  >
                    <CheckSquare className="h-4 w-4" />
                    <span className="text-[8.5px] font-bold">{appLang === "EN" ? "Decisions" : "Решения"}</span>
                  </button>

                  <button 
                    onClick={() => setCurrentScreen("looks")}
                    className={`flex flex-col items-center gap-0.5 px-2 py-1 text-slate-400 transition hover:text-[#8D5B4C] ${currentScreen === "looks" ? "text-[#8D5B4C] font-bold" : "opacity-90"}`}
                  >
                    <Sparkles className="h-4 w-4" />
                    <span className="text-[8.5px] font-bold">{appLang === "EN" ? "Looks" : "Образы"}</span>
                  </button>

                  <button 
                    onClick={() => setCurrentScreen("profile")}
                    className={`flex flex-col items-center gap-0.5 px-2 py-1 text-slate-400 transition hover:text-[#8D5B4C] ${(currentScreen === "profile" || currentScreen === "settings" || currentScreen === "report") ? "text-[#8D5B4C] font-bold" : "opacity-90"}`}
                  >
                    <User className="h-4 w-4" />
                    <span className="text-[8.5px] font-bold">{appLang === "EN" ? "Profile" : "Профиль"}</span>
                  </button>
                </div>
              </div>
            )}

            {/* Android Screen System Pill Accent */}
            <div className={`pt-1 pb-2 flex items-center justify-center shrink-0 z-30 ${
              simulatorThemeMode === "light" ? "bg-white" : "bg-[#1f1f1f]"
            }`}>
              <div className="w-24 h-0.5 bg-slate-450 rounded-full"></div>
            </div>

          </div>
        </section>

        {/* RIGHT COLUMN: Senior Kotlin Code Inspector & Handoff Blueprints */}
        <section className="w-full lg:w-[460px] xl:w-[500px] bg-slate-900 overflow-y-auto flex flex-col border-t lg:border-t-0 border-slate-800 shrink-0 select-none scrollbar-thin">
          <div className="p-4 border-b border-slate-800 bg-slate-950 flex flex-col gap-2.5">
            <div className="flex items-center gap-1.5">
              <BookOpen className="h-5 w-5 text-emerald-400" />
              <h3 className="font-bold text-sm text-slate-200">Android Handoff Specification</h3>
            </div>
            <p className="text-xs text-slate-400 leading-relaxed text-left">
              These reference Kotlin snippets represent Jetpack Compose blueprints. Use them as design directives for building the real native Android layout and navigation.
            </p>
          </div>

          {/* Module Selector tabs */}
          <div className="bg-slate-950/40 p-2 flex gap-1.5 overflow-x-auto border-b border-slate-800 select-none scrollbar-none">
            {Object.keys(kotlinFiles).map((key) => {
              const file = kotlinFiles[key];
              const isActive = activeCodeKey === key;
              return (
                <button
                  key={key}
                  onClick={() => setActiveCodeKey(key)}
                  className={`px-3 py-1.5 rounded-lg text-xs font-mono whitespace-nowrap border transition ${
                    isActive 
                      ? "bg-slate-800 text-emerald-400 border-slate-700 shadow-inner" 
                      : "bg-slate-900/60 text-slate-500 border-slate-800 hover:bg-slate-850 hover:text-slate-400"
                  }`}
                >
                  {file.name}
                </button>
              );
            })}
          </div>

          {/* Active File Metadata */}
          <div className="p-4 bg-slate-850 border-b border-slate-850 flex flex-col gap-1 text-left">
            <div className="flex items-center gap-2">
              <span className="text-[10px] bg-slate-950 font-bold px-2.5 py-1 rounded text-slate-400 font-mono tracking-wider">
                /{kotlinFiles[activeCodeKey]?.path || ""}
              </span>
            </div>
            <p className="text-[11px] text-slate-350 leading-relaxed italic">
              ↳ {kotlinFiles[activeCodeKey]?.description || ""}
            </p>
          </div>

          {/* Code block viewer */}
          <div className="flex-1 p-4 bg-slate-950 font-mono text-[10.5px] text-slate-200 overflow-x-auto whitespace-pre select-text selection:bg-emerald-500 selection:text-slate-950 scrollbar-thin text-left">
            {kotlinFiles[activeCodeKey]?.code || ""}
          </div>

          {/* Architecture Checklist Footers */}
          <div className="p-4 bg-slate-950 border-t border-slate-850 space-y-3 shrink-0 text-left">
            <span className="text-[10px] font-bold text-slate-400 tracking-widest uppercase block mb-1">Architecture Compliance Matrix</span>
            
            <div className="grid grid-cols-2 gap-2 text-[10px] text-slate-500">
              <div className="flex items-center gap-1.5">
                <CheckCircle className="h-3.5 w-3.5 text-emerald-400 shrink-0" />
                <span>Type-Safe Selection Request</span>
              </div>
              <div className="flex items-center gap-1.5">
                <CheckCircle className="h-3.5 w-3.5 text-emerald-400 shrink-0" />
                <span>Exif Metadata Stripping</span>
              </div>
              <div className="flex items-center gap-1.5">
                <CheckCircle className="h-3.5 w-3.5 text-emerald-400 shrink-0" />
                <span>Dynamic RU/EN Locale state</span>
              </div>
              <div className="flex items-center gap-1.5">
                <CheckCircle className="h-3.5 w-3.5 text-emerald-400 shrink-0" />
                <span>Full SafeLogger Redacting</span>
              </div>
            </div>
          </div>
        </section>

      </main>

      {/* Confirmation Dialog 1: Clear local style data only */}
      {dialogOpen === "styleData" && (
        <div className="fixed inset-0 bg-slate-950/80 backdrop-blur-sm flex items-center justify-center z-50 p-4">
          <div className="bg-slate-900 border border-slate-800 rounded-2xl max-w-sm w-full p-6 space-y-4">
            <div className="flex items-center gap-2 text-rose-400">
              <AlertTriangle className="h-5 w-5" />
              <h3 className="font-bold text-md">{s.settingsPurgePrompt}</h3>
            </div>
            <p className="text-xs text-slate-300 leading-relaxed text-left">
              {s.settingsPurgeDesc}
            </p>
            <div className="flex justify-end gap-3 pt-2">
              <button 
                onClick={() => setDialogOpen("none")}
                className="px-4 py-2 bg-slate-800 hover:bg-slate-750 text-slate-300 rounded-lg text-xs font-bold"
              >
                {appLang === "EN" ? "Cancel" : "Отмена"}
              </button>
              <button 
                onClick={() => {
                  setDialogOpen("none");
                  handleWipeStyleDataOnly();
                }}
                className="px-4 py-2 bg-rose-600 hover:bg-rose-700 text-white rounded-lg text-xs font-bold"
              >
                {s.confirmPurge}
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Confirmation Dialog 2: Reset App Onboarding */}
      {dialogOpen === "onboarding" && (
        <div className="fixed inset-0 bg-slate-950/80 backdrop-blur-sm flex items-center justify-center z-50 p-4">
          <div className="bg-slate-900 border border-slate-800 rounded-2xl max-w-sm w-full p-6 space-y-4">
            <div className="flex items-center gap-2 text-rose-400">
              <RefreshCw className="h-5 w-5" />
              <h3 className="font-bold text-md">
                {appLang === "EN" ? "Reset Onboarding Sliders?" : "Сбросить онбординг?"}
              </h3>
            </div>
            <p className="text-xs text-slate-300 leading-relaxed text-left">
              {appLang === "EN"
                ? "This will wipe your active disclaimer state consents and return you to the onboarding screens. Confirm?"
                : "Это сбросит ваши согласия и вернет вас к экранам онбординга. Подтвердить?"}
            </p>
            <div className="flex justify-end gap-3 pt-2">
              <button 
                onClick={() => setDialogOpen("none")}
                className="px-4 py-2 bg-slate-800 hover:bg-slate-750 text-slate-300 rounded-lg text-xs font-bold"
              >
                {appLang === "EN" ? "Cancel" : "Отмена"}
              </button>
              <button 
                onClick={() => {
                  setDialogOpen("none");
                  handleResetAppOnboardingOnly();
                }}
                className="px-4 py-2 bg-rose-600 hover:bg-rose-700 text-white rounded-lg text-xs font-bold"
              >
                {appLang === "EN" ? "Reset" : "Сбросить"}
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Detailed Decision View Modal Overlay */}
      {selectedDecisionForDetails && (
        <div className="fixed inset-0 bg-slate-950/80 backdrop-blur-sm flex items-center justify-center z-50 p-4">
          <div className="bg-white border border-slate-205 rounded-2xl max-w-sm w-full p-5 space-y-4 shadow-xl overflow-y-auto max-h-[90vh]">
            <div className="flex justify-between items-start pb-2 border-b border-slate-100">
              <div>
                <h3 className="font-extrabold text-[#8D5B4C] text-[13px] uppercase tracking-wide">
                  🔍 {appLang === "EN" ? "Garment Decision Check" : "Проверка вещи"}
                </h3>
                <span className="text-[9px] text-slate-400 block mt-0.5 font-medium leading-none">
                  {appLang === "EN" ? "Category: " : "Категория: "}{" "}
                  {appLang === "EN" ? selectedDecisionForDetails.category : {
                    Top: "Верх", Bottom: "Низ", Dress: "Платье", Outerwear: "Верхняя одежда", Shoes: "Обувь", Bag: "Сумка", Accessory: "Аксессуар"
                  }[selectedDecisionForDetails.category as any] || selectedDecisionForDetails.category}
                </span>
              </div>
              <span className={`text-[8.5px] font-black px-2 py-0.5 rounded-md font-mono shrink-0 uppercase tracking-wide border ${
                selectedDecisionForDetails.verdict === "Good match" 
                  ? "bg-emerald-50 text-emerald-800 border-emerald-200" 
                  : selectedDecisionForDetails.verdict === "Maybe" 
                    ? "bg-amber-50 text-amber-800 border-amber-200" 
                    : "bg-rose-50 text-rose-800 border-rose-200"
              }`}>
                {selectedDecisionForDetails.verdict === "Good match" && (appLang === "EN" ? s.verdictGood : s.verdictGood)}
                {selectedDecisionForDetails.verdict === "Maybe" && (appLang === "EN" ? "MAYBE" : s.verdictMaybe)}
                {selectedDecisionForDetails.verdict === "Skip" && (appLang === "EN" ? s.verdictSkip : s.verdictSkip)}
              </span>
            </div>

            <div className="space-y-3 text-xs text-slate-700 text-left">
              {/* Context */}
              <div className="space-y-0.5 bg-slate-50 p-2.5 rounded-lg">
                <span className="text-[9.5px] font-extrabold uppercase tracking-wide text-slate-400 block">
                  {appLang === "EN" ? "Target Intent Context" : "Цель и Контекст покупки"}
                </span>
                <p className="text-[10px] leading-relaxed text-slate-700 font-medium">
                  {appLang === "EN" ? selectedDecisionForDetails.context : {
                    Basic: "Базовая вещь", Trend: "Трендовая вещь", Occasion: "Вещь для редкого случая", Similar: "Похожа на то, что уже есть", Unsure: "Малополезная вещь"
                  }[selectedDecisionForDetails.context as any] || selectedDecisionForDetails.context}
                </p>
              </div>

              {/* Color synergy */}
              <div className="space-y-0.5 bg-slate-50 p-2.5 rounded-lg">
                <span className="text-[9.5px] font-extrabold uppercase tracking-wide text-slate-400 block">
                  {appLang === "EN" ? "Color Synergy Analysis" : "Анализ синергии цвета"}
                </span>
                <p className="text-[10px] leading-relaxed text-slate-700">
                  {selectedDecisionForDetails.reasonEn && (selectedDecisionForDetails.reasonEn.toLowerCase().includes("charcoal") || selectedDecisionForDetails.reasonRu.toLowerCase().includes("серый блейзер")) ? (
                    appLang === "EN"
                      ? "Color Synergy: Aligns perfectly with your master Soft Autumn palette guidelines."
                      : "Синергия цвета: отлично гармонирует с палитрой Мягкой Осени в вашем отчете."
                  ) : selectedDecisionForDetails.reasonEn && (selectedDecisionForDetails.reasonEn.toLowerCase().includes("similar") || selectedDecisionForDetails.reasonRu.toLowerCase().includes("белые кеды")) ? (
                    appLang === "EN"
                      ? "Color Clash: High friction contrast that disrupts your natural aesthetic harmony."
                      : "Цветовая гамма: слишком контрастные или перенасыщенные тона, спорящие с вашей базой."
                  ) : (
                    appLang === "EN"
                      ? "Color Check: Vivid chromatic tone that requires careful surrounding layers."
                      : "Проверка цвета: достаточно яркий оттенок, потребует грамотной калибровки аксессуаров."
                  )}
                </p>
              </div>

              {/* Outfit count compatibility */}
              <div className="space-y-0.5 bg-amber-50/10 p-2.5 rounded-lg border border-amber-200/30">
                <span className="text-[9.5px] font-extrabold uppercase tracking-wide text-slate-400 block">
                  {appLang === "EN" ? "Estimated Outfit Combinations" : "Совместимые образы"}
                </span>
                <p className="text-[10px] leading-relaxed text-slate-800 font-semibold font-mono text-[#8D5B4C]">
                  ⚡ {appLang === "EN" 
                    ? `StyleAI projects ${selectedDecisionForDetails.outfitsCount} outfit matches with existing clothes` 
                    : `StyleAI оценивает до ${selectedDecisionForDetails.outfitsCount} вариантов сочетаний`}
                </p>
              </div>

              {/* Structured Reason */}
              <div className="space-y-0.5">
                <span className="text-[9.5px] font-extrabold uppercase tracking-wide text-slate-400 block">
                  {appLang === "EN" ? "Decision Justification" : "Обоснование совета"}
                </span>
                <p className="text-[10px] leading-relaxed italic text-slate-650 bg-slate-50 p-2 rounded-lg">
                  "{appLang === "EN" ? selectedDecisionForDetails.reasonEn : selectedDecisionForDetails.reasonRu}"
                </p>
              </div>
            </div>

            <div className="flex justify-end pt-2 border-t border-slate-100">
              <button 
                onClick={() => setSelectedDecisionForDetails(null)}
                className="w-full py-2 bg-[#8D5B4C] hover:bg-[#8D5B4C]/90 text-white rounded-lg text-xs font-bold transition"
              >
                {appLang === "EN" ? "Back to Decisions" : "Вернуться к решениям"}
              </button>
            </div>
          </div>
        </div>
      )}

    </div>
  );
}
