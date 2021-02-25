package com.boonsuen.recurr.fragments

import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.boonsuen.recurr.R
import com.boonsuen.recurr.data.models.BillingPeriod
import com.boonsuen.recurr.data.models.SubscriptionData
import com.boonsuen.recurr.fragments.list.ListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }
        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            when (emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:parseBillingPeriodToInt")
        @JvmStatic
        fun parseBillingPeriodToInt(view: Spinner, billingPeriod: BillingPeriod) {
            when (billingPeriod) {
                BillingPeriod.MONTHLY -> { view.setSelection(0) }
                BillingPeriod.WEEKLY -> { view.setSelection(1) }
                BillingPeriod.YEARLY -> { view.setSelection(2) }
            }
        }

        @BindingAdapter("android:parseBillingPeriodColor")
        @JvmStatic
        fun parseBillingPeriodColor(cardView: CardView, billingPeriod: BillingPeriod) {
            when (billingPeriod) {
                BillingPeriod.MONTHLY -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.red)) }
                BillingPeriod.WEEKLY -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow)) }
                BillingPeriod.YEARLY -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green)) }
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, currentItem: SubscriptionData) {
            view.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }

        // Used in fragment_update for amount EditText
        @BindingAdapter("android:textFloat")
        @JvmStatic
        fun textFloat(view: EditText, textFloat: Float) {
            view.setText(textFloat.toString())
        }

        // Used in row_layout for amount_txt TextView
        @BindingAdapter("android:rowLayoutAmount", "android:rowLayoutBillingPeriod")
        @JvmStatic
        fun amountText(view: TextView, amount: Float, billingPeriod: BillingPeriod) {
            val amountStr = amount.toString()
            when (billingPeriod) {
                BillingPeriod.MONTHLY -> {
                    view.setText("$${amountStr} per month")
                }
                BillingPeriod.WEEKLY -> {
                    view.setText("$${amountStr} per week")
                }
                BillingPeriod.YEARLY -> {
                    view.setText("$${amountStr} per year")
                }
            }
        }

    }

}