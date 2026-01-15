package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // エッジトゥエッジ（画面一杯に表示する設定）を有効にします
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        // システムバー（ステータスバーなど）との重なりを調整します
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // RecyclerView（リスト表示の部品）の設定
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // リストを縦方向に並べる設定にします
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 表示するためのサンプルデータを作成します
        val itemList = listOf(
            CardItem("項目 1", "これは1番目のカードの説明です。"),
            CardItem("項目 2", "これは2番目のカードの説明です。"),
            CardItem("項目 3", "これは3番目のカードの説明です。"),
            CardItem("項目 4", "これは4番目のカードの説明です。"),
            CardItem("項目 5", "これは5番目のカードの説明です。"),
            CardItem("項目 6", "これは6番目のカードの説明です。"),
            CardItem("項目 7", "これは7番目のカードの説明です。"),
            CardItem("項目 8", "これは8番目のカードの説明です。")
        )

        // アダプターを介してデータをRecyclerViewにセットします
        recyclerView.adapter = CardAdapter(itemList)
    }
}