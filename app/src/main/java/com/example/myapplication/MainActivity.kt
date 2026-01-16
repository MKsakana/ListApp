package com.example.myapplication

import android.os.Bundle
import android.view.View
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

        // onCreate内に追加
        val addButton = findViewById<View>(R.id.addButton)
        val subButton1 = findViewById<View>(R.id.subButton1)
        val subButton2 = findViewById<View>(R.id.subButton2)

        var isMenuOpen = false // メニューが開いているかどうかのフラグ

        addButton.setOnClickListener {
            if (!isMenuOpen) {
                // メニューを開くアニメーション
                showButton(subButton1, 1f)
                showButton(subButton2, 1f)
                // メインボタンを45度回転させて「×」に見せる（お好みで）
                addButton.animate().rotation(45f).setDuration(300).start()
            } else {
                // メニューを閉じるアニメーション
                hideButton(subButton1)
                hideButton(subButton2)
                addButton.animate().rotation(0f).setDuration(300).start()
            }
            isMenuOpen = !isMenuOpen
        }
    }
    // ボタンをふわっと出す関数
    private fun showButton(view: View, alpha: Float) {
        view.visibility = View.VISIBLE
        view.alpha = 0f
        view.translationY = 50f // 下から上に上がる動き
        view.animate()
            .alpha(alpha)
            .translationY(0f)
            .setDuration(300)
            .start()
    }

    // ボタンを隠す関数
    private fun hideButton(view: View) {
        view.animate()
            .alpha(0f)
            .translationY(50f)
            .setDuration(300)
            .withEndAction { view.visibility = View.GONE }
            .start()
    }
}