package com.boonsuen.recurr.fragments

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.boonsuen.recurr.R
import com.boonsuen.recurr.data.models.BillingPeriod
import com.boonsuen.recurr.data.models.SubscriptionData

class SharedViewModel(application: Application): AndroidViewModel(application) {

    /** ============== List Fragment ============== */

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(subscriptionData: List<SubscriptionData>) {
        emptyDatabase.value = subscriptionData.isEmpty()
    }

    /** ============== Add/Update Fragment ============== */

    val listener: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                when (position) {
                    0 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red)) }
                    1 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow)) }
                    2 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green)) }
                }
            }
        }

    fun verifyDataFromUser(name: String, amount: String): Boolean {
        return !(TextUtils.isEmpty(name) || TextUtils.isEmpty(amount))
    }

    fun parseBillingPeriod(billingPeriod: String): BillingPeriod {
        return when (billingPeriod) {
            "Monthly" -> {
                BillingPeriod.MONTHLY}
            "Weekly" -> {
                BillingPeriod.WEEKLY}
            "Yearly" -> {
                BillingPeriod.YEARLY}
            else -> BillingPeriod.MONTHLY
        }
    }

}