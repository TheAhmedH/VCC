package com.veryable.android

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.veryable.android.model.PayTypeEnum
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_payouts_list.testTextInAppBar
import kotlinx.android.synthetic.main.fragment_detail.btDetailDone
import kotlinx.android.synthetic.main.fragment_detail.ivDetailSource
import kotlinx.android.synthetic.main.fragment_detail.tvDetailSourceCompany
import kotlinx.android.synthetic.main.fragment_detail.tvDetailSourceTitle


@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24)
            setDisplayHomeAsUpEnabled(true)
        }
        (requireActivity() as PayoutsListActivity).testTextInAppBar.text =
            getString(R.string.DETAILS)

        //Receive value from bank frag
        val payoutReceived = args.account

        when (payoutReceived.account?.account_type) {
            PayTypeEnum.BANK.value -> {
                ivDetailSource.setImageResource(R.drawable.baseline_account_balance_black)
                tvDetailSourceCompany.text = payoutReceived.account?.desc
                tvDetailSourceTitle.text = payoutReceived.account?.account_name
            }
            PayTypeEnum.CARD.value -> {
                ivDetailSource.setImageResource(R.drawable.baseline_credit_card_black)
                tvDetailSourceCompany.text = payoutReceived.account?.desc
                tvDetailSourceTitle.text = payoutReceived.account?.account_name
            }
        }

        btDetailDone.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}