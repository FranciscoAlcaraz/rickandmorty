package com.example.rickandmortycharacterlistapp.viewmodel.view.characterlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycharacterlistapp.domain.Result
import com.example.rickandmortycharacterlistapp.domain.model.Character
import com.example.rickandmortycharacterlistapp.domain.usecase.character.GetCharactersUseCase
import com.example.rickandmortycharacterlistapp.framework.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val useCase: GetCharactersUseCase) :
    ViewModel() {

    private val _characters = mutableStateOf(emptyList<Character>())
    val characters: State<List<Character>> get() = _characters

    private val _showError = SingleLiveEvent<Void>()
    val showError: LiveData<Void> get() = _showError

    private var name: String? = null
    private var status: String? = null
    private var species: String? = null
    private var type: String? = null
    private var gender: String? = null

    private var nextPage = 1
    private var hasMoreItems = true

    fun fetchItems() {
        if (hasMoreItems) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = useCase.invoke(nextPage, name, status, species, type, gender)
                withContext(Dispatchers.Main) {
                    when (result) {
                        is Result.Success -> {
                            hasMoreItems = result.data.hasMoreItems
                            nextPage++
                            val currentList = ArrayList<Character>(_characters.value)
                            currentList.addAll(result.data.items)
                            _characters.value = currentList
                        }
                        is Result.Fail -> _showError.call()
                    }
                }
            }
        }
    }

    fun onNameFilterChanged(label: String) {
        name = label
        doNewSearch()
    }

    fun onTypeFilterChanged(label: String) {
        type = label
        doNewSearch()
    }

    fun onSpecieFilterChanged(label: String) {
        species = label
        doNewSearch()
    }

    fun onStatusFilterChanged(label: String) {
        status = label
        doNewSearch()
    }

    fun onGenderFilterChanged(label: String) {
        gender = label
        doNewSearch()
    }

    private fun doNewSearch() {
        hasMoreItems = true
        _characters.value = emptyList()
        nextPage = 1
        fetchItems()
    }

    fun resetSearch() {
        name = null
        status = null
        species = null
        type = null
        gender = null
        doNewSearch()
    }

    fun clearGenderFilter() {
        gender = null
        doNewSearch()
    }

    fun clearStatusFilter() {
        status = null
        doNewSearch()
    }
}