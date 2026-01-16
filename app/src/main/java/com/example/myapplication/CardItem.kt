package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * データベースの「テーブル（表）」の構造を定義するクラス
 */
@Entity(tableName = "card_table")
data class CardItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    /** カードのタイトル */
    val title: String,
    /** カードの説明文（備考） */
    val description: String,
    /** Webリンク (追加) */
    val webLink: String = "",
    /** ユーザー名 (追加) */
    val userName: String = ""
)
