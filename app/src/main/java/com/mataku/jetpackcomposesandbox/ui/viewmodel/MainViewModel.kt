package com.mataku.jetpackcomposesandbox.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class MainViewModel : ViewModel() {

  private var _uiState = MutableStateFlow<UIState>(UIState())
  val uiState: StateFlow<UIState> = _uiState

  fun onTapWeeklyTab(selectedIndex: Int) {
    _uiState.update {
      it.copy(selectedIndex = selectedIndex)
    }
  }

  data class UIState(
    val selectedIndex: Int = 0
  )
}
