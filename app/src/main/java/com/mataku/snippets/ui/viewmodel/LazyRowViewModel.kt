package com.mataku.snippets.ui.viewmodel

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LazyRowViewModel : ViewModel() {
  private val _uiState = MutableStateFlow(UiState())
  val uiState = _uiState.asStateFlow()

  private var selected: MutableList<TagState> = mutableListOf()
  private var unselected: MutableList<TagState> = TagState.generateInitialization().toMutableList()

  private var selectedItemPlacement: MutableList<TagState> = mutableListOf()
  private var unselectedItemPlacement: MutableList<TagState> =
    TagState.generateInitialization().toMutableList()

  fun onTagClickItemPlacement(tag: TagState) {
    if (tag.selected) {
      selectedItemPlacement.remove(tag)
      unselectedItemPlacement.add(tag.copy(selected = false))
    } else {
      selectedItemPlacement.add(tag.copy(selected = true))
      unselectedItemPlacement.remove(tag)
    }
    _uiState.update {
      it.copy(
        tagListItemPlacement = (selectedItemPlacement + unselectedItemPlacement).toImmutableList()
      )
    }
  }

  fun onTagClick(tag: TagState) {
    viewModelScope.launch {
      _uiState.update {
        it.copy(
          shouldShowTagList = false,
          canClearAllSelection = selected.isNotEmpty()
        )
      }
      delay(200L)

      if (tag.selected) {
        selected.remove(tag)
        unselected.add(tag.copy(selected = false))
        _uiState.update {
          it.copy(
            tagList = (selected + unselected).toImmutableList(),
          )
        }
      } else {
        unselected.remove(tag)
        selected.add(tag.copy(selected = true))
        _uiState.update {
          it.copy(
            tagList = (selected + unselected).toImmutableList(),
          )
        }
        // Demonstrate Network request await
        delay(500L)
        val newRecommendationList = TagState.generateNewRecommendationList(currentList = selected)
        unselected = newRecommendationList.toMutableList()
        _uiState.update {
          it.copy(
            tagList = (selected + unselected).toImmutableList()
          )
        }
        delay(50L)
        unselected = newRecommendationList.toMutableList()
        _uiState.update {
          it.copy(
            tagList = (selected + unselected).toImmutableList()
          )
        }
      }
      _uiState.update {
        it.copy(
          shouldShowTagList = true,
          canClearAllSelection = selected.isNotEmpty()
        )
      }
    }
  }

  fun clearAllSelection() {
    val newList = unselected + selected.map { it.copy(selected = false) }
    unselected = newList.toMutableList()
    selected = mutableListOf()
    _uiState.update {
      it.copy(
        tagList = newList.toImmutableList(),
        canClearAllSelection = false
      )
    }
  }

  data class UiState(
    val tagList: ImmutableList<TagState> = TagState.generateInitialization().toImmutableList(),
    val canClearAllSelection: Boolean = false,
    val tagListItemPlacement: ImmutableList<TagState> = TagState.generateInitialization()
      .toImmutableList(),
    val shouldShowTagList: Boolean = true
  )
}

data class TagState(
  val label: String,
  val selected: Boolean,
) {

  companion object {
    private val defaultList = listOf(
      TagState("J-Tracks", false),
      TagState("K-POP", false),
      TagState("Pop", false),
      TagState("Hip-Hop", false),
      TagState("Charts", false),
      TagState("Dance", false),
    )

    fun generateInitialization(): List<TagState> {
      return listOf(
        TagState("J-Tracks", false),
        TagState("K-POP", false),
        TagState("Pop", false),
        TagState("Hip-Hop", false),
        TagState("Charts", false),
        TagState("Dance", false),
      )
    }

    fun generateNewRecommendationList(currentList: List<TagState>): List<TagState> {
      return defaultList.filter { state ->
        currentList.find { it.label == state.label } == null
      }.shuffled()
    }

    fun generateRandom(count: Int = 6): List<TagState> {
      return LoremIpsum(count).values.map {
        TagState(it, false)
      }.toList()
    }
  }
}
