package com.sermage.cryptocurrencies.screens

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.sermage.cryptocurrencies.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_details.*

class CoinDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_details)
        if (!intent.hasExtra(EXTRA_FSYM)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FSYM)
        val viewModel: CoinViewModel by viewModels()
        viewModel.getCoinInfo(fromSymbol).observe(this, Observer {
                tvPrice.text=it.price.toString()
                tvMinPrice.text=it.lowDay.toString()
                tvMaxPrice.text=it.highDay.toString()
                tvLastBargain.text=it.lastMarket
                tvUpdate.text=it.getFormattedTime()
                tvFromSymbol.text=it.fromSymbol
                tvToSymbol.text=it.toSymbol
                Picasso.get().load(it.getFullImageUrl()).into(ivCoinLogoInDetails)

        })
    }


    companion object {
        private const val EXTRA_FSYM = "fSym"

        fun newIntent(context: Context, fSym: String): Intent {
            val intent = Intent(context, CoinDetailsActivity::class.java)
            intent.putExtra(EXTRA_FSYM, fSym)
            return intent
        }
    }
}