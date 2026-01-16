package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * ViewModel (ビューモデル)
 * 画面（UI）に表示するためのデータを保持し、管理するクラスです。
 * 画面が回転したりしてもデータが消えないように保護する役割も持っています。
 */
class CardViewModel(private val repository: CardRepository) : ViewModel() {

    /**
     * すべてのカードデータを LiveData として保持します。
     * FlowをLiveDataに変換することで、Activity側でデータの変化を簡単に「観察（Observe）」できるようになります。
     */
    val allCards: LiveData<List<CardItem>> = repository.allCards.asLiveData()

    /**
     * 新しいカードを保存する処理。
     * viewModelScope を使うことで、ViewModelが破棄された時に実行中の処理も自動でキャンセルされる安全な設計になります。
     */
    fun insert(card: CardItem) = viewModelScope.launch {
        repository.insert(card)
    }
}

/**
 * ViewModelFactory (ビューモデルファクトリー)
 * CardViewModelは引数（repository）を持つため、これを使ってインスタンスを作成する必要があります。
 * 言わば、ViewModel専用の「作成説明書」のようなものです。
 */
class CardViewModelFactory(private val repository: CardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
