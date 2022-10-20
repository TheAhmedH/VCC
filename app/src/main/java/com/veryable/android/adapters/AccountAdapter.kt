package com.veryable.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.veryable.android.R
import com.veryable.android.model.AccountsResponseSorted
import com.veryable.android.model.PayTypeEnum
import kotlinx.android.synthetic.main.item_info_preview.view.ivSource
import kotlinx.android.synthetic.main.item_info_preview.view.tvSourceCompany
import kotlinx.android.synthetic.main.item_info_preview.view.tvSourceTitle
import kotlinx.android.synthetic.main.item_info_preview.view.tvSourceType
import kotlinx.android.synthetic.main.item_type_preview.view.tvBankTitle

class AccountAdapter : RecyclerView.Adapter<ViewHolder>() {

    class BankHolder(itemView: View) : ViewHolder(itemView) {
    }

    class CardHolder(itemView: View) : ViewHolder(itemView) {
    }

    //Differentiate between items coming in
    override fun getItemViewType(position: Int): Int {
        return when (diff.currentList[position].account_heading) {
            PayTypeEnum.BANK_ACCOUNTS.value -> {
                return 0
            }
            PayTypeEnum.CARDS.value -> {
                return 1
            }
            PayTypeEnum.CARD_ITEM.value -> {
                return 3
            }
            PayTypeEnum.BANK_ITEM.value -> {
                return 4
            }
            else -> {
                return 5
            }
        }
    }

    //Setup diff
    private val diffCallback = object : DiffUtil.ItemCallback<AccountsResponseSorted>() {
        override fun areItemsTheSame(
            oldItem: AccountsResponseSorted,
            newItem: AccountsResponseSorted
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: AccountsResponseSorted,
            newItem: AccountsResponseSorted
        ): Boolean {
            return oldItem.account_heading == newItem.account_heading && oldItem.account?.id == newItem.account?.id
        }
    }

    val diff = AsyncListDiffer(this, diffCallback)

    //Provide holder based on view type
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 0 || viewType == 1) {
            CardHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_type_preview, parent, false)
            )
        } else {
            BankHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_info_preview, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = diff.currentList[position]

        when (currentItem.account_heading) {
            PayTypeEnum.BANK_ACCOUNTS.value -> {
                holder.itemView.tvBankTitle.text = currentItem.account_heading
            }

            PayTypeEnum.CARDS.value -> {
                holder.itemView.tvBankTitle.text = currentItem.account_heading
            }

            PayTypeEnum.CARD_ITEM.value -> {
                holder.itemView.apply {
                    ivSource.setImageResource(R.drawable.baseline_credit_card_black)
                    tvSourceTitle.text = currentItem.account?.account_name
                    tvSourceCompany.text = currentItem.account?.desc
                    tvSourceType.text = currentItem.process
                    setOnClickListener {
                        itemClickListener?.let {
                            it(currentItem)
                        }
                    }
                }
            }

            PayTypeEnum.BANK_ITEM.value -> {
                holder.itemView.apply {
                    ivSource.setImageResource(R.drawable.baseline_account_balance_black)
                    tvSourceTitle.text = currentItem.account?.account_name
                    tvSourceCompany.text = currentItem.account?.desc
                    tvSourceType.text = currentItem.process
                    setOnClickListener {
                        itemClickListener?.let {
                            it(currentItem)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    //Click listener
    private var itemClickListener: ((AccountsResponseSorted) -> Unit)? = null
    fun setOnItemClickListener(listener: (AccountsResponseSorted) -> Unit) {
        itemClickListener = listener
    }

}