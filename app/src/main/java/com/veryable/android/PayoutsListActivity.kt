package com.veryable.android

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.veryable.android.databinding.ActivityPayoutsListBinding
import com.veryable.android.viewmodel.PayoutsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_payouts_list.appbarWidgetBank2


@AndroidEntryPoint
class PayoutsListActivity : AppCompatActivity() {

    val viewModel: PayoutsViewModel by viewModels()

    lateinit var binding: ActivityPayoutsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayoutsListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(appbarWidgetBank2)
        binding.testTextInAppBar.text = getString(R.string.ACCOUNTS)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        binding.testTextInAppBar.text = getString(R.string.ACCOUNTS)
        return true
    }
}