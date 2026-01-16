package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerViewにデータを表示するための橋渡し役（アダプター）クラス
 */
class CardAdapter(
    private var items: List<CardItem>,
    private val onItemClick: (CardItem) -> Unit
) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    /**
     * 1つのリスト項目のView（見た目）を保持するクラス
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.itemTitle)
        val description: TextView = view.findViewById(R.id.itemDescription)
    }

    /**
     * リスト項目の見た目（レイアウト）を作成する処理
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // item_card.xml を読み込んで、1つ分の項目を作成します
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    /**
     * 作成されたViewに実際のデータを流し込む（結びつける）処理
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.description.text = item.description
        holder.itemView.setOnClickListener{
            onItemClick(item)
        }
    }

    /**
     * リストの総件数を返します
     */
    override fun getItemCount() = items.size

    /**
     * データを更新するための関数
     * データベースの中身が変わった時にこれを呼び出します
     */
    fun updateData(newItems: List<CardItem>) {
        items = newItems
        // 画面を再描画するように通知します
        notifyDataSetChanged()
    }
}
