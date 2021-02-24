package com.boonsuen.recurr.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boonsuen.recurr.R
import com.boonsuen.recurr.data.viewmodel.SubscriptionViewModel
import com.boonsuen.recurr.fragments.SharedViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {

    private val mSubscriptionDataViewModel: SubscriptionViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        mSubscriptionDataViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })
        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseViews(it)
        })

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        // Set Menu
        setHasOptionsMenu(true)

        return view
    }

    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            view?.findViewById<ImageView>(R.id.no_data_imageView)?.visibility = View.VISIBLE
            view?.findViewById<TextView>(R.id.no_data_textView)?.visibility = View.VISIBLE
        } else {
            view?.findViewById<ImageView>(R.id.no_data_imageView)?.visibility = View.INVISIBLE
            view?.findViewById<TextView>(R.id.no_data_textView)?.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    // Show AlertDialog to confirm removal of all items from database table
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mSubscriptionDataViewModel.deleteAll()
            Toast.makeText(
                    requireContext(),
                    "Successfully removed everything!",
                    Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to remove everything?")
        builder.create().show()
    }
}