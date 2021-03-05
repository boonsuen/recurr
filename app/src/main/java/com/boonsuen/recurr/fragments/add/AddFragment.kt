package com.boonsuen.recurr.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.boonsuen.recurr.R
import com.boonsuen.recurr.data.models.BillingPeriod
import com.boonsuen.recurr.data.models.SubscriptionData
import com.boonsuen.recurr.data.viewmodel.SubscriptionViewModel
import com.boonsuen.recurr.databinding.FragmentAddBinding
import com.boonsuen.recurr.fragments.SharedViewModel
import java.lang.Float.parseFloat
import java.lang.Math.round
import kotlin.math.roundToLong

class AddFragment : Fragment() {

    private val mSubscriptionViewModel: SubscriptionViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.billingPeriodSpinner.onItemSelectedListener = mSharedViewModel.listener

        binding.buttonAdd.setOnClickListener{
            insertDataToDb()
        }

        return view
    }

    private fun insertDataToDb() {
        val mName = binding.nameEt.text.toString().trim()
        val mAmount = binding.amountEt.text.toString()
        val mBillingPeriod = binding.billingPeriodSpinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mName, mAmount)
        if (validation) {
            // Insert Data to Database
            round(3.14159265359 * 100) / 100
            val newData = SubscriptionData(
                    0,
                    mName,
                    (round(parseFloat(mAmount) * 100.0 ) / 100.0).toFloat(),
                    mSharedViewModel.parseBillingPeriod(mBillingPeriod)
            )
            mSubscriptionViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}