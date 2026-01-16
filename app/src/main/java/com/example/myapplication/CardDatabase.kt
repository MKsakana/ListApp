package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * データベース全体を管理する抽象クラス
 * @Database で、どの Entity を使うか、バージョンはいくつか、を指定します
 */
@Database(entities = [CardItem::class], version = 1, exportSchema = false)
abstract class CardDatabase : RoomDatabase() {

    /**
     * 先ほど作った命令セット（DAO）を取得するための関数
     */
    abstract fun cardDao(): CardDao

    companion object {
        /**
         * データベースのインスタンス（実体）を保持します。
         * 複数作ってしまうとメモリを無駄遣いするため、1つだけ作るようにします（シングルトン）。
         */
        @Volatile
        private var INSTANCE: CardDatabase? = null

        /**
         * データベースを取得するための関数
         */
        fun getDatabase(context: Context): CardDatabase {
            // INSTANCE が null でない場合はそれを返し、null の場合は新しく作ります
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CardDatabase::class.java,
                    "card_database" // データベースのファイル名
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}