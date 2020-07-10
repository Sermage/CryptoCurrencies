package com.sermage.cryptocurrencies.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sermage.cryptocurrencies.R
import com.sermage.cryptocurrencies.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_coin_info.view.*

class CoinListAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinListAdapter.CoinViewHolder>() {
    inner class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCoinSymbols: TextView = itemView.tvCoinSymbols
        val tvValue: TextView = itemView.tvValue
        val tvLastUpdate: TextView = itemView.tvLastUpdate
        val ivCoinLogo: ImageView = itemView.ivCoinLogo
    }
    var onCoinClickListener:OnCoinClickListener?=null

     var listOfCoins = listOf<CoinPriceInfo>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
        return CoinViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfCoins.size
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = listOfCoins[position]
        val symbolsTemplate = context.resources.getString(R.string.symbols_template)
        val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
        with(holder) {
            tvCoinSymbols.text =
                String.format(symbolsTemplate, coin.fromSymbol, coin.toSymbol)
            tvValue.text = coin.price.toString()
            tvLastUpdate.text = String.format(lastUpdateTemplate, coin.getFormattedTime())
            Picasso.get().load(coin.getFullImageUrl()).into(ivCoinLogo)
            itemView.setOnClickListener {
                onCoinClickListener?.onCoinClick(coin)
            }
        }
    }

    interface OnCoinClickListener{
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}