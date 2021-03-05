package com.boonsuen.recurr.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.boonsuen.recurr.R
import com.boonsuen.recurr.data.models.BillingPeriod
import com.boonsuen.recurr.data.models.SubscriptionData
import com.boonsuen.recurr.data.viewmodel.SubscriptionViewModel
import com.boonsuen.recurr.databinding.FragmentListBinding
import com.boonsuen.recurr.fragments.SharedViewModel
import com.boonsuen.recurr.utils.hideKeyboard
import com.robinhood.ticker.TickerUtils
import java.text.DecimalFormat
import java.text.NumberFormat

class ListFragment : Fragment() {

    private val mSubscriptionViewModel: SubscriptionViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private val adapter: ListAdapter by lazy { ListAdapter() }

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Data binding
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel
        val view = binding.root

        // Setup RecyclerView
        setupRecyclerView()

        binding.radioButtonMonthly.isChecked = true
       // Observe LiveData
        mSubscriptionViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)

            // Expenses Overview
            when (binding.radioGroupSelection.checkedRadioButtonId) {
                R.id.radioButtonWeekly -> updateExpensesOverviewAmount(data, "WEEKLY")
                R.id.radioButtonMonthly -> updateExpensesOverviewAmount(data, "MONTHLY")
                R.id.radioButtonYearly -> updateExpensesOverviewAmount(data, "YEARLY")
            }
            binding.radioGroupSelection.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radioButtonWeekly -> updateExpensesOverviewAmount(data, "WEEKLY")
                    R.id.radioButtonMonthly -> updateExpensesOverviewAmount(data, "MONTHLY")
                    R.id.radioButtonYearly -> updateExpensesOverviewAmount(data, "YEARLY")
                }
            }
        })

        // Set Menu
        setHasOptionsMenu(true)

        // Hide soft keyboard
        hideKeyboard(requireActivity())

        // Robinhood Ticker
        binding.tvAmount.setCharacterLists(TickerUtils.provideNumberList())
        binding.tvAmount.typeface = ResourcesCompat.getFont(requireContext(), R.font.inter_bold)

        return view
    }

    private fun updateExpensesOverviewAmount(data: List<SubscriptionData>, period: String) {

        fun parseBillingPeriod(billingPeriod: BillingPeriod): String {
            return when (billingPeriod) {
                BillingPeriod.MONTHLY -> "MONTHLY"
                BillingPeriod.WEEKLY -> "WEEKLY"
                BillingPeriod.YEARLY -> "YEARLY"
            }
        }

        var calcAmount = 0.0F
        listOf(data).map {
            for (item in it) {
                val itemBillingPeriod = parseBillingPeriod(item.billingPeriod)
                if (itemBillingPeriod == period) {
                    calcAmount += item.amount
                } else if (itemBillingPeriod == "WEEKLY" && period == "MONTHLY") {
                    calcAmount += item.amount * 13/3
                } else if (itemBillingPeriod == "WEEKLY" && period == "YEARLY") {
                    calcAmount += item.amount * 52
                } else if(itemBillingPeriod == "MONTHLY" && period == "WEEKLY") {
                    calcAmount += item.amount * 3/13
                } else if(itemBillingPeriod == "MONTHLY" && period == "YEARLY") {
                    calcAmount += item.amount * 12
                } else if(itemBillingPeriod == "YEARLY" && period == "WEEKLY") {
                    calcAmount += item.amount / 52
                } else if(itemBillingPeriod == "YEARLY" && period == "MONTHLY") {
                    calcAmount += item.amount / 12
                }
            }
        }
        val formatter: NumberFormat = DecimalFormat("#,##0.00")
        val formattedCalcAmount: String = formatter.format(calcAmount)
        binding.tvAmount.text = getString(R.string.with_dollar_sign, formattedCalcAmount)
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sortBy_name_ascending -> mSubscriptionViewModel.sortByNameAscending.observe(
                this,
                {
                    adapter.setData(
                        it
                    )
                })
            R.id.menu_sortBy_name_descending -> mSubscriptionViewModel.sortByNameDescending.observe(
                this,
                {
                    adapter.setData(
                        it
                    )
                })
            R.id.menu_sortBy_amount_low_to_high -> mSubscriptionViewModel.sortByAmountLowToHigh.observe(
                this,
                {
                    adapter.setData(
                        it
                    )
                })
            R.id.menu_sortBy_amount_high_to_low -> mSubscriptionViewModel.sortByAmountHighToLow.observe(
                this,
                {
                    adapter.setData(
                        it
                    )
                })
            R.id.menu_sortBy_billingPeriod_short_to_long -> mSubscriptionViewModel.sortByBillingPeriodShortToLong.observe(
                this,
                Observer {
                    adapter.setData(
                        it
                    )
                })
            R.id.menu_sortBy_billingPeriod_long_to_short -> mSubscriptionViewModel.sortByBillingPeriodLongToShort.observe(
                this,
                Observer {
                    adapter.setData(
                        it
                    )
                })
            R.id.menu_clear_sort -> mSubscriptionViewModel.getAllData.observe(this, {
                adapter.setData(
                    it
                )
            })
            R.id.menu_delete_all -> confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    // Show AlertDialog to confirm removal of all items from database table
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mSubscriptionViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Successfully removed everything!",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete all items?")
        builder.setMessage("Are you sure you want to remove everything?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}