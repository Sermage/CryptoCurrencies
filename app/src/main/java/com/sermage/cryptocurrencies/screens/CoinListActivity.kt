package com.sermage.cryptocurrencies.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.sermage.cryptocurrencies.R
import com.sermage.cryptocurrencies.adapters.CoinListAdapter
import com.sermage.cryptocurrencies.pojo.CoinPriceInfo
import kotlinx.android.synthetic.main.activity_coin_list.*

class CoinListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_list)
        val adapter = CoinListAdapter(this)
        rvCoins.adapter = adapter
        val viewModel: CoinViewModel by viewModels()
        viewModel.priceList.observe(this, Observer {
            adapter.listOfCoins = it
        })
        adapter.onCoinClickListener = object : CoinListAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                val intent =
                    CoinDetailsActivity.newIntent(this@CoinListActivity, coinPriceInfo.fromSymbol)
                startActivity(intent)
            }
        }
    }

}
