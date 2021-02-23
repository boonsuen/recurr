package com.boonsuen.recurr.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.boonsuen.recurr.data.models.BillingPeriod

@Entity(tableName = "subscription_table")
data class SubscriptionData (
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var name: String,
        var amount: Float,
        var billingPeriod: BillingPeriod,
)