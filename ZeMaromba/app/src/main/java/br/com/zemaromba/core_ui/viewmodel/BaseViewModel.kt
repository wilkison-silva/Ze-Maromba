package br.com.zemaromba.core_ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

open class BaseViewModel : ViewModel() {

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}