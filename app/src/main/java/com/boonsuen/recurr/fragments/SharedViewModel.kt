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
import java.lang.Float.parseFloat

class SharedViewModel(application: Application): AndroidViewModel(application) {

    /** ============== List Fragment ============== */

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(subscriptionData: List<SubscriptionData>) {
        emptyDatabase.value = subscriptionData.isEmpty()
    }

    fun verifyDataFromUser(name: String, amount: String): Boolean {
        try {
            parseFloat(amount)
        } catch (e: Exception) {
            return false
        }

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