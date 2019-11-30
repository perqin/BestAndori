package io.github.bestandori.ui.page.cards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import io.github.bestandori.data.model.CardModel
import io.github.bestandori.data.repository.CardsRepository
import io.github.bestandori.util.Resource
import io.github.bestandori.util.ResourceLiveData
import kotlinx.coroutines.launch

class CardsViewModel(application: Application) : AndroidViewModel(application) {
    private val cards = ResourceLiveData {
        CardsRepository.getAllCards()
    }
    fun getCards(): LiveData<Resource<List<Card>>> = cards

    fun reload() {
        viewModelScope.launch {
            cards.reload()
        }
    }
}

class Card(title: String, character: String) : CardModel(title, character)
