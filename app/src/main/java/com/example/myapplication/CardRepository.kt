package com.example.myapplication

import kotlinx.coroutines.flow.Flow

/**
 * Repository (リポジトリ)
 * データベース（DAO）とViewModelの間に立ち、データの取得元を管理するクラスです。
 * アプリが大きくなった時に、データの取得先（ローカルDBかサーバーか）をここ一箇所で切り替えられるようにします。
 */
class CardRepository(private val cardDao: CardDao) {

    // すべてのカードをFlowとして保持します（DAOからそのまま流します）
    val allCards: Flow<List<CardItem>> = cardDao.getAllCards()

    /**
     * 新しいカードをデータベースに挿入します。
     * suspend キーワードにより、非同期（裏側）で実行されることを保証します。
     */
    suspend fun insert(card: CardItem) {
        cardDao.insert(card)
    }
}
