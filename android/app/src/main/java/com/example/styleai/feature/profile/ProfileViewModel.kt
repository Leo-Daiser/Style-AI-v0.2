package com.example.styleai.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.model.StyleReport
import com.example.styleai.domain.model.UserConsentState
import com.example.styleai.domain.repository.StyleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val styleRepository: StyleRepository
) : ViewModel() {
    val selectedLanguage: StateFlow<AppLanguage> = styleRepository.getSelectedLanguage()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppLanguage.EN)

    val activeReport: StateFlow<StyleReport?> = styleRepository.getActiveReport()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    fun changeLanguage(language: AppLanguage) {
        viewModelScope.launch { styleRepository.saveSelectedLanguage(language) }
    }

    fun deleteStyleData() {
        viewModelScope.launch {
            styleRepository.clearAllLocalData()
            _message.value = "Style data deleted locally."
        }
    }

    fun resetOnboarding(onCompleted: () -> Unit) {
        viewModelScope.launch {
            styleRepository.saveOnboardingCompleted(false)
            styleRepository.saveConsentState(UserConsentState())
            onCompleted()
        }
    }

    fun purgeAllLocalData(onCompleted: () -> Unit) {
        viewModelScope.launch {
            styleRepository.clearAllLocalData()
            styleRepository.saveOnboardingCompleted(false)
            styleRepository.saveConsentState(UserConsentState())
            onCompleted()
        }
    }

    fun dismissMessage() {
        _message.value = null
    }
}
