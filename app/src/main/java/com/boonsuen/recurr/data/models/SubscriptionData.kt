package com.boonsuen.recurr.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "subscription_table")
@Parcelize
data class SubscriptionData (
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var name: String,
        var amount: Float,
        var billingPeriod: BillingPeriod,
): Parcelable