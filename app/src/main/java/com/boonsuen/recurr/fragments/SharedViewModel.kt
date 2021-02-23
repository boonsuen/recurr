package com.boonsuen.recurr.fragments

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import com.boonsuen.recurr.data.models.BillingPeriod

class SharedViewModel(application: Application): AndroidViewModel(application) {

    fun verifyDataFromUser(name: String, amount: String): Boolean {
        return if (TextUtils.isEmpty(name) || TextUtils.isEmpty(amount)) {
            false
        } else !(name.isEmpty() || amount.isEmpty())
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