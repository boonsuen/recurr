package com.boonsuen.recurr.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.boonsuen.recurr.R
import com.boonsuen.recurr.data.models.BillingPeriod
import com.boonsuen.recurr.data.models.SubscriptionData
import com.boonsuen.recurr.data.viewmodel.SubscriptionViewModel
import com.boonsuen.recurr.databinding.FragmentAddBinding
import com.boonsuen.recurr.databinding.FragmentUpdateBinding
import com.boonsuen.recurr.fragments.SharedViewModel
import java.lang.Float.parseFloat

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mSubscriptionViewModel: SubscriptionViewModel by viewModels()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args
        val view = binding.root

        // Set Menu
        setHasOptionsMenu(true)

        // Spinner Item Selected Listener
        binding.currentBillingPeriodSpinner.onItemSelectedListener = mSharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val name = binding.currentNameEt.text.toString().trim()
        val amount = binding.currentAmountEt.text.toString()
        val getBillingPeriod = binding.currentBillingPeriodSpinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(name, amount)
        if (validation) {
            // Update Current Item
            val updatedItem = SubscriptionData(
                    args.currentItem.id,
                    name,
                    (Math.round(parseFloat(amount) * 100.0) / 100.0).toFloat(),
                    mSharedViewModel.parseBillingPeriod(getBillingPeriod)
            )
            mSubscriptionViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
            // Navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }
    
    // Show AlertDialog to confirm item removal
    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mSubscriptionViewModel.deleteItem(args.currentItem)
            Toast.makeText(
                    requireContext(),
                    "Successfully removed: ${args.currentItem.name}",
                    Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete '${args.currentItem.name}'?")
        builder.setMessage("Are you sure you want to remove '${args.currentItem.name}'?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}