package com.example.styleai.core.localization

import com.example.styleai.domain.model.AppLanguage

interface AppStrings {
    val appTitle: String
    val splashSubtitle: String
    val onboardingSkip: String
    val onboardingContinue: String
    val onboardingFinish: String
    val onboardingTitle1: String
    val onboardingDesc1: String
    val onboardingTitle2: String
    val onboardingDesc2: String
    val onboardingTitle3: String
    val onboardingDesc3: String
    
    val consentTitle: String
    val consentSubtitle: String
    val consentCheck1: String
    val consentCheck2: String
    val consentCheck3: String
    val consentButton: String

    val uploadTitle: String
    val uploadSubtitle: String
    val uploadSelfieLabel: String
    val uploadFullBodyLabel: String
    val uploadButton: String
    val uploadWarningBlurry: String
    val uploadValidationError: String

    val loadingStep1: String
    val loadingStep2: String
    val loadingStep3: String
    val loadingStep4: String
    val loadingStep5: String
    val loadingStep6: String

    val reportTitle: String
    val reportPaletteSection: String
    val reportSilhouettesSection: String
    val reportDirectionsSection: String
    val reportGapsSection: String
    val reportShoppingSection: String
    val reportAvoidSection: String
    val reportBodySafetyNotice: String

    val visTitle: String
    val visSubtitle: String
    val visOccasionLabel: String
    val visStyleLabel: String
    val visSeasonLabel: String
    val visFormalityLabel: String
    val visColorDirectionLabel: String
    val visSafetyWarning: String
    val visIntroButton: String

    val paywallTitle: String
    val paywallSubtitle: String
    val paywallButton: String
    val paywallCompleteReport: String
    val paywallSubPlan: String
    val paywallCreditsPack: String

    val historyTitle: String
    val historySubtitle: String
    val historyEmptyState: String
    val historyActiveReports: String
    val historySavedLooks: String

    val settingsTitle: String
    val settingsSubtitle: String
    val settingsLanguageSection: String
    val settingsMinimizationInfo: String
    val settingsSafetyInfo: String
    val settingsDeleteDataButton: String
    val settingsResetOnboardingButton: String
    val settingsExportButton: String
    val settingsDismiss: String

    // Home strings
    val homeTitle: String
    val homeSubtitle: String
    val homeCheckBuyTitle: String
    val homeCheckBuyDesc: String
    val homeCheckBuyBtn: String
    val homeCreateProfileTitle: String
    val homeCreateProfileDesc: String
    val homeCreateProfileBtn: String
    val homeReportActiveTitle: String
    val homeReportOpenBtn: String
    val homeReportDirectionsCount: String
    val homeReportGapsCount: String
    val homeQuickOutfitIdeas: String
    val homeQuickSavedLooks: String
    val homeQuickCredits: String
    val homeQuickPrivacy: String
    val homePrivacyReassuranceTitle: String
    val homePrivacyReassuranceDesc: String

    // Shopping Check strings
    val shopTitle: String
    val shopSubtitle: String
    val shopCategoryLabel: String
    val shopPhotoLabel: String
    val shopBtnAnalyze: String
    val shopResultTitle: String
    val shopVerdictGood: String
    val shopVerdictMaybe: String
    val shopVerdictSkip: String
    val shopSectionColor: String
    val shopSectionSilhouette: String
    val shopSectionCapsule: String
    val shopSectionVersatility: String
    val shopSectionOutfits: String
    val shopSectionAdvice: String
    val shopBtnBack: String
}

object AppStringsEn : AppStrings {
    override val appTitle = "StyleAI"
    override val splashSubtitle = "Your private AI style assistant"
    override val onboardingSkip = "Skip Onboarding"
    override val onboardingContinue = "Continue"
    override val onboardingFinish = "Get Started"
    override val onboardingTitle1 = "Discover your personal style"
    override val onboardingDesc1 = "Get a tailored style report card generated based on your real facial pigmentations, silhouette structures, and individual aesthetic choices."
    override val onboardingTitle2 = "Build better outfits"
    override val onboardingDesc2 = "Identify perfect garment shapes, curated blazers, safe combination color spectrums, and close high-priority wardrobe gaps effortlessly."
    override val onboardingTitle3 = "Privacy-First by design"
    override val onboardingDesc3 = "Your uploaded pixels are parsed purely in-memory. They are not recorded, not transmitted, and not shared. Safe, reliable, and secure."
    
    override val consentTitle = "Privacy & Consent"
    override val consentSubtitle = "To maintain strict legal safety protocols, please verify and consent to the operations below:"
    override val consentCheck1 = "I confirm that the physical uploaded image is of myself or that I possess explicit legal permission from the subject."
    override val consentCheck2 = "I acknowledge that the style report contains clothing matches and references, not medical or body therapy advice."
    override val consentCheck3 = "Raw photos are not stored by default. In-memory photo transient data minimizer."
    override val consentButton = "Confirm and Continue"

    override val uploadTitle = "Upload Photos"
    override val uploadSubtitle = "Upload selfies and body silhouettes to analyze style."
    override val uploadSelfieLabel = "1. Face/Selfie Photo"
    override val uploadFullBodyLabel = "2. Full Body Contour"
    override val uploadButton = "Initiate Style Analysis"
    override val uploadWarningBlurry = "Poor lighting detected. Please stand closer to a window, turning face to natural sun vectors for optimal palette evaluations."
    override val uploadValidationError = "Safety validation triggered: Underage filters or multiple subjects flagged. Only single adult portraits are permitted."

    override val loadingStep1 = "Photo Safety & Moderation classification checks..."
    override val loadingStep2 = "Scanning skin pigments & identifying season palettes..."
    override val loadingStep3 = "Measuring shoulder alignment & skeletal fits..."
    override val loadingStep4 = "Curating Soft Classic & Smart Casual style paths..."
    override val loadingStep5 = "Cross-referencing wardrobe lists & assembling gaps..."
    override val loadingStep6 = "Assembling custom recommendations report... Finalizing."

    override val reportTitle = "Style Analysis Report"
    override val reportPaletteSection = "Recommended Color Palette"
    override val reportSilhouettesSection = "Suggested Silhouettes"
    override val reportDirectionsSection = "Style Directions"
    override val reportGapsSection = "Wardrobe Gaps Checklist"
    override val reportShoppingSection = "Priority Shopping List"
    override val reportAvoidSection = "Avoid Guide (Safety Note)"
    override val reportBodySafetyNotice = "We avoid labels or body-shaming terms. Style targets personal geometry."

    override val visTitle = "Outfit Board"
    override val visSubtitle = "Generate style reference boards based on your report."
    override val visOccasionLabel = "Occasion"
    override val visStyleLabel = "Style"
    override val visSeasonLabel = "Season"
    override val visFormalityLabel = "Formality"
    override val visColorDirectionLabel = "Color direction"
    override val visSafetyWarning = "Visualizations are style references, not exact clothing fit simulations. Unsafe or non-consensual image use is not allowed."
    override val visIntroButton = "Create visual reference (1 Credit)"

    override val paywallTitle = "Unlock StyleAI Premium"
    override val paywallSubtitle = "Unlock full seasonal guides and custom styling boards"
    override val paywallButton = "Upgrade balance / Pro"
    override val paywallCompleteReport = "Complete Style Report ($9.99)"
    override val paywallSubPlan = "Pro Monthly Subscription ($9.99/mo)"
    override val paywallCreditsPack = "Buy 10 Outfit Credits ($4.99)"

    override val historyTitle = "Decisions"
    override val historySubtitle = "Your saved shopping decisions and outfit ideas"
    override val historyEmptyState = "No saved shopping decisions yet."
    override val historyActiveReports = "Style Profiles"
    override val historySavedLooks = "Saved Looks"

    override val settingsTitle = "App Settings"
    override val settingsSubtitle = "Fine-tune privacy thresholds, data exports, and cache minimizations."
    override val settingsLanguageSection = "Language / Язык"
    override val settingsMinimizationInfo = "StyleAI operates under strict Data Minimization rules. Your face profiles, color calculations, and silhouette geometry matrices are generated 100% locally in-memory. They are never transmitted or stored on server infrastructure."
    override val settingsSafetyInfo = "Our systems reject all queries matching non-consensual alterations, nudity, minors, or sexualized prompts. Reports prioritize supportive geometric balance, avoiding body-shaming or shape-labeling terms."
    override val settingsDeleteDataButton = "Delete Style Data Only"
    override val settingsResetOnboardingButton = "Reset App Onboarding"
    override val settingsExportButton = "Export Style Report (JSON)"
    override val settingsDismiss = "Dismiss"

    // Home strings implementation
    override val homeTitle = "StyleAI"
    override val homeSubtitle = "Choose what you need today."
    override val homeCheckBuyTitle = "Should I buy this?"
    override val homeCheckBuyDesc = "Check if a clothing item fits your style, capsule, and wardrobe goals."
    override val homeCheckBuyBtn = "Check an item"
    override val homeCreateProfileTitle = "Create your style profile"
    override val homeCreateProfileDesc = "Optional: upload photos to generate a personal palette, silhouettes, and capsule recommendations."
    override val homeCreateProfileBtn = "Create profile"
    override val homeReportActiveTitle = "Your Active Style Report"
    override val homeReportOpenBtn = "Open report"
    override val homeReportDirectionsCount = "Style directions available"
    override val homeReportGapsCount = "Wardrobe gaps identified"
    override val homeQuickOutfitIdeas = "Outfit ideas"
    override val homeQuickSavedLooks = "Saved looks"
    override val homeQuickCredits = "Credits"
    override val homeQuickPrivacy = "Privacy"
    override val homePrivacyReassuranceTitle = "Offline MVP mode"
    override val homePrivacyReassuranceDesc = "No internet permission. No raw photos are stored by default."

    // Shopping Check strings implementation
    override val shopTitle = "Should I Buy This?"
    override val shopSubtitle = "Verify if a garment fits your local style before purchase"
    override val shopCategoryLabel = "Select Garment Category"
    override val shopPhotoLabel = "Upload Item Photo (Optional)"
    override val shopBtnAnalyze = "Run Style Check"
    override val shopResultTitle = "Garment Compatibility Verdict"
    override val shopVerdictGood = "Excellent Styling Match"
    override val shopVerdictMaybe = "Conditional Match"
    override val shopVerdictSkip = "Skip / Avoid"
    override val shopSectionColor = "Color Palette Harmony"
    override val shopSectionSilhouette = "Silhouette & Fit"
    override val shopSectionCapsule = "Capsule Alignment"
    override val shopSectionVersatility = "Versatility Rate"
    override val shopSectionOutfits = "Assembled Combinations"
    override val shopSectionAdvice = "Expert Style Note"
    override val shopBtnBack = "Back to Home Screen"
}

object AppStringsRu : AppStrings {
    override val appTitle = "StyleAI"
    override val splashSubtitle = "Ваш приватный AI-стилист"
    override val onboardingSkip = "Пропустить"
    override val onboardingContinue = "Продолжить"
    override val onboardingFinish = "Начать"
    override val onboardingTitle1 = "Откройте свой персональный стиль"
    override val onboardingDesc1 = "Получите индивидуальную карту стиля на основе цветотипа лица, особенностей силуэта и ваших личных эстетических предпочтений."
    override val onboardingTitle2 = "Составляйте лучшие образы"
    override val onboardingDesc2 = "Легко подбирайте идеальные фасоны, жакеты, гармоничные цветовые схемы и восполняйте пробелы в гардеробе."
    override val onboardingTitle3 = "Конфиденциальность на первом месте"
    override val onboardingDesc3 = "Ваши фотографии обрабатываются исключительно в оперативной памяти. Они не сохраняются, не передаются и не передаются третьим лицам. Безопасно и надежно."
    
    override val consentTitle = "Конфиденциальность и согласие"
    override val consentSubtitle = "Для соблюдения строгих правил безопасности, пожалуйста, подтвердите согласие со следующими пунктами:"
    override val consentCheck1 = "Я подтверждаю, что загружаемое изображение принадлежит мне или у меня есть явное разрешение от владельца."
    override val consentCheck2 = "Я согласен с тем, что стилистический отчет содержит рекомендации по одежде и сочетаниям, а не медицинские рекомендации."
    override val consentCheck3 = "Исходные фотографии по умолчанию не сохраняются. Минимизация данных и обработка только в оперативной памяти."
    override val consentButton = "Подтвердить и продолжить"

    override val uploadTitle = "Загрузка фотографий"
    override val uploadSubtitle = "Загрузите селфи и силуэт тела для анализа стиля."
    override val uploadSelfieLabel = "1. Фото лица / Селфи"
    override val uploadFullBodyLabel = "2. Силуэт в полный рост"
    override val uploadButton = "Начать анализ стиля"
    override val uploadWarningBlurry = "Обнаружено плохое освещение. Пожалуйста, встаньте ближе к окну, повернувшись к естественному свету, для лучшего анализа палитры."
    override val uploadValidationError = "Сработала валидация безопасности: Найдено несоответствие требованиям модерации. Разрешены только одиночные взрослые портреты."

    override val loadingStep1 = "Проверка безопасности и модерация фотографий..."
    override val loadingStep2 = "Анализ пигментов кожи и определение цветовой палитры..."
    override val loadingStep3 = "Определение линий плеч и пропорций силуэта..."
    override val loadingStep4 = "Разработка индивидуального стиля..."
    override val loadingStep5 = "Составление списков гардероба..."
    override val loadingStep6 = "Формирование персонального отчета..."

    override val reportTitle = "Отчет по стилю"
    override val reportPaletteSection = "Рекомендуемая палитра цветов"
    override val reportSilhouettesSection = "Рекомендуемые силуэты"
    override val reportDirectionsSection = "Стилистические направления"
    override val reportGapsSection = "Пробелы в гардеробе"
    override val reportShoppingSection = "Приоритетный список покупок"
    override val reportAvoidSection = "Чего рекомендуется избегать (Заметка безопасности)"
    override val reportBodySafetyNotice = "Мы не используем ярлыки и критику фигуры. Стилизация направлена на гармонию геометрии."

    override val visTitle = "Визуализации образов"
    override val visSubtitle = "Создавайте мудборды стиля на основе своего отчета."
    override val visOccasionLabel = "Событие"
    override val visStyleLabel = "Стиль"
    override val visSeasonLabel = "Сезон"
    override val visFormalityLabel = "Формальность"
    override val visColorDirectionLabel = "Направление цвета"
    override val visSafetyWarning = "Визуализации показывают стилевое направление, а не точную посадку одежды. Небезопасное использование изображений и обработка фото без согласия запрещены."
    override val visIntroButton = "Создать визуализацию (1 Кредит)"

    override val paywallTitle = "Разблокировать StyleAI Premium"
    override val paywallSubtitle = "Разблокируйте полные сезонные гиды и интерактивные подборы"
    override val paywallButton = "Улучшить баланс / Pro"
    override val paywallCompleteReport = "Полный отчет по стилю ($9.99)"
    override val paywallSubPlan = "Ежемесячная подписка Pro ($9.99/мес)"
    override val paywallCreditsPack = "Купить 10 кредитов образов ($4.99)"

    override val historyTitle = "Решения"
    override val historySubtitle = "Ваши сохраненные решения о покупках и идеи образов"
    override val historyEmptyState = "Сохраненные решения отсутствуют."
    override val historyActiveReports = "Профили стиля"
    override val historySavedLooks = "Сохраненные образы"

    override val settingsTitle = "Настройки приложения"
    override val settingsSubtitle = "Параметры конфиденциальности, экспорта данных и очистки кеша."
    override val settingsLanguageSection = "Язык / Language"
    override val settingsMinimizationInfo = "StyleAI работает по строгим правилам минимизации данных. Ваши параметры лица, расчеты цвета и геометрия силуэта генерируются исключительно локально в памяти. Они никогда не передаются и не хранятся на серверах."
    override val settingsSafetyInfo = "Наши системы отклоняют любые запросы, связанные с несанкционированными изменениями, наготой или неуместными темами. Все отчеты ориентированы на гармонию геометрии."
    override val settingsDeleteDataButton = "Удалить только данные о стиле"
    override val settingsResetOnboardingButton = "Сбросить онбординг приложения"
    override val settingsExportButton = "Экспортировать отчет (JSON)"
    override val settingsDismiss = "Скрыть"

    // Home strings implementation (RU)
    override val homeTitle = "StyleAI"
    override val homeSubtitle = "Выберите, что вам нужно сегодня."
    override val homeCheckBuyTitle = "Стоит ли покупать?"
    override val homeCheckBuyDesc = "Проверьте, подходит ли вещь вашему стилю, капсуле и целям гардероба."
    override val homeCheckBuyBtn = "Проверить вещь"
    override val homeCreateProfileTitle = "Создать стиль-профиль"
    override val homeCreateProfileDesc = "Необязательно: загрузите фото, чтобы получить палитру, силуэты и рекомендации по капсуле."
    override val homeCreateProfileBtn = "Создать профиль"
    override val homeReportActiveTitle = "Ваш активный стиль-отчет"
    override val homeReportOpenBtn = "Открыть отчет"
    override val homeReportDirectionsCount = "Доступно стилей"
    override val homeReportGapsCount = "Пробелов в гардеробе найдено"
    override val homeQuickOutfitIdeas = "Идеи образов"
    override val homeQuickSavedLooks = "Сохраненные образы"
    override val homeQuickCredits = "Кредиты"
    override val homeQuickPrivacy = "Приватность"
    override val homePrivacyReassuranceTitle = "Офлайн-режим MVP"
    override val homePrivacyReassuranceDesc = "Нет доступа к интернету. Исходные фотографии по умолчанию не сохраняются."

    // Shopping Check strings implementation (RU)
    override val shopTitle = "Стоит ли покупать?"
    override val shopSubtitle = "Проверьте совместимость вещи с вашим стилем перед покупкой"
    override val shopCategoryLabel = "Выберите категорию вещи"
    override val shopPhotoLabel = "Загрузить фото вещи (Необязательно)"
    override val shopBtnAnalyze = "Проверить совместимость"
    override val shopResultTitle = "Вердикт совместимости вещи"
    override val shopVerdictGood = "Отличное соответствие"
    override val shopVerdictMaybe = "Условное соответствие"
    override val shopVerdictSkip = "Рекомендуется пропустить"
    override val shopSectionColor = "Гармония цветовой палитры"
    override val shopSectionSilhouette = "Силуэт и посадка"
    override val shopSectionCapsule = "Совместимость с капсулой"
    override val shopSectionVersatility = "Коэффициент сочетаемости"
    override val shopSectionOutfits = "Число комбинаций в гардеробе"
    override val shopSectionAdvice = "Совет стилиста"
    override val shopBtnBack = "Назад на главную"
}

object AppLocalization {
    fun getStrings(language: AppLanguage): AppStrings {
        return when (language) {
            AppLanguage.EN -> AppStringsEn
            AppLanguage.RU -> AppStringsRu
        }
    }
}
