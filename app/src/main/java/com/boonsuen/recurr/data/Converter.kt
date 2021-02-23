package com.boonsuen.recurr.data

import androidx.room.TypeConverter
import com.boonsuen.recurr.data.models.BillingPeriod

class Converter {

    @TypeConverter
    fun fromBillingPeriod(billingPeriod: BillingPeriod): String {
        return billingPeriod.name
    }

    @TypeConverter
    fun toBillingPeriod(billingPeriod: String): BillingPeriod {
        return BillingPeriod.valueOf(billingPeriod)
    }

}