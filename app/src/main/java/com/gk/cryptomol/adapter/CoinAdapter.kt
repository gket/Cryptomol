package com.gk.cryptomol.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gk.cryptomol.R
import com.gk.cryptomol.data.CoinItem

class CoinAdapter() : RecyclerView.Adapter<CoinAdapter.ViewHolder>() {

    private var items = listOf<CoinItem>()

    fun setData(coinItems: List<CoinItem>) {
        items = coinItems
        notifyDataSetChanged()
    }

    lateinit var onCoinClicked: ((String) -> Unit)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewCoinName: TextView = itemView.findViewById(R.id.textViewCoinName)
        val textViewCoinSymbol: TextView = itemView.findViewById(R.id.textViewCoinSymbol)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coin, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewCoinName.text = items[position].coinName
        holder.textViewCoinSymbol.text = items[position].coinSymbol
        holder.itemView.setOnClickListener {
            onCoinClicked.invoke(items[position].coinId)
        }
    }
}