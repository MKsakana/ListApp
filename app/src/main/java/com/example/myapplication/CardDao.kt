package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object)
 * データベースへの具体的な命令（保存、取得、削除など）を定義するインターフェースです。
 */
@Dao
interface CardDao {

    /**
     * すべてのカードをデータベースから取得します。
     * Flowを使うことで、データベースの中身が更新された時に、画面に通知を送ることができます。
     */
    @Query("SELECT * FROM card_table ORDER BY id DESC")
    fun getAllCards(): Flow<List<CardItem>>

    /**
     * 新しいカードをデータベースに保存します。
     * @param card 保存したいカードの情報
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(card: CardItem)

    /**
     * すべてのカードを削除します（テスト用や初期化用）。
     */
    @Query("DELETE FROM card_table")
    suspend fun deleteAll()
}
