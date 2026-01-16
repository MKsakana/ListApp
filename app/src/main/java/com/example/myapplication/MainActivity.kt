package com.example.myapplication

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // ViewModel の初期化
    private val cardViewModel: CardViewModel by viewModels {
        val database = CardDatabase.getDatabase(this)
        val repository = CardRepository(database.cardDao())
        CardViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // RecyclerViewの設定
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // アダプター作成時に、タップされた時の処理（showDetailDialogの呼び出し）を記述
        val adapter = CardAdapter(emptyList()) { clickedItem ->
            showDetailDialog(clickedItem)
        }
        recyclerView.adapter = adapter

        // データベースの変更を監視してリストを更新
        cardViewModel.allCards.observe(this) { cards ->
            adapter.updateData(cards)
        }

        // ボタンの設定
        val addButton = findViewById<View>(R.id.addButton)
        val subButton1 = findViewById<View>(R.id.subButton1)
        val subButton2 = findViewById<View>(R.id.subButton2)

        var isMenuOpen = false

        addButton.setOnClickListener {
            if (!isMenuOpen) {
                showButton(subButton1, 1f)
                showButton(subButton2, 1f)
                addButton.animate().rotation(45f).setDuration(300).start()
            } else {
                hideButton(subButton1)
                hideButton(subButton2)
                addButton.animate().rotation(0f).setDuration(300).start()
            }
            isMenuOpen = !isMenuOpen
        }

        subButton1.setOnClickListener {
            closeMenu(addButton, subButton1, subButton2)
            isMenuOpen = false
        }

        subButton2.setOnClickListener {
            showAddCardDialog()
            closeMenu(addButton, subButton1, subButton2)
            isMenuOpen = false
        }
    }

    /**
     * ★ここが重要：詳細ダイアログを表示する関数
     */
    private fun showDetailDialog(item: CardItem) {
        val message = """
        ユーザー名: ${item.userName}
        リンク: ${item.webLink}
        備考: ${item.description}
    """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle(item.title)
            .setMessage(message)
            .setPositiveButton("閉じる", null)
            .show()
    }

    /**
     * 新しいカードを追加するための入力ダイアログ
     */
    private fun showAddCardDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("カードをつくる")

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        // 各入力欄の作成
        val titleInput = EditText(this).apply { hint = "サイト名・タイトル" }
        val urlInput = EditText(this).apply { hint = "Webリンク (URL)" }
        val userInput = EditText(this).apply { hint = "ユーザー名 / ID" }
        val descInput = EditText(this).apply { hint = "備考・メモ" }

        layout.addView(titleInput)
        layout.addView(urlInput)
        layout.addView(userInput)
        layout.addView(descInput)
        builder.setView(layout)

        builder.setPositiveButton("完成！") { _, _ ->
            val title = titleInput.text.toString()
            if (title.isNotEmpty()) {
                val newCard = CardItem(
                    title = title,
                    webLink = urlInput.text.toString(),
                    userName = userInput.text.toString(),
                    description = descInput.text.toString()
                )
                cardViewModel.insert(newCard)
            }
        }
        builder.setNegativeButton("キャンセル", null)
        builder.show()
    }


    private fun closeMenu(mainBtn: View, sub1: View, sub2: View) {
        hideButton(sub1)
        hideButton(sub2)
        mainBtn.animate().rotation(0f).setDuration(300).start()
    }

    private fun showButton(view: View, alpha: Float) {
        view.visibility = View.VISIBLE
        view.alpha = 0f
        view.translationY = 50f
        view.animate().alpha(alpha).translationY(0f).setDuration(300).start()
    }

    private fun hideButton(view: View) {
        view.animate().alpha(0f).translationY(50f).setDuration(300)
            .withEndAction { view.visibility = View.GONE }.start()
    }
}