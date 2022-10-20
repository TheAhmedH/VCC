package com.veryable.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.veryable.android.adapters.AccountAdapter
import com.veryable.android.databinding.FragmentBankBinding
import com.veryable.android.viewmodel.PayoutsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_bank.rvBankAccounts

@AndroidEntryPoint
class BankFragment : Fragment(R.layout.fragment_bank) {

    private val viewModel: PayoutsViewModel by viewModels()

    @Inject
    lateinit var adapterAll: AccountAdapter

    private var _binding: FragmentBankBinding? = null
    private  val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBankBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
        }


        viewModel.getAccountDetails()

        setupAllRecyclerview()

        viewModel.accountsResponse.observe(viewLifecycleOwner, Observer { response ->
            adapterAll.diff.submitList(response)
        })

        adapterAll.setOnItemClickListener {
            val bankBundle = Bundle().apply {
                putSerializable("account", it)
            }
            findNavController().navigate(R.id.action_bankFragment_to_detailFragment, bankBundle)
        }

    }

    private fun setupAllRecyclerview() {
        rvBankAccounts.adapter = adapterAll
        rvBankAccounts.layoutManager = LinearLayoutManager(activity)
    }

}