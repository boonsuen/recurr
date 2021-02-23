package com.boonsuen.recurr.data.repository

import androidx.lifecycle.LiveData
import com.boonsuen.recurr.data.SubscriptionDao
import com.boonsuen.recurr.data.models.SubscriptionData

class SubscriptionRepository(private val subscriptionDao: SubscriptionDao) {

    val getAllData: LiveData<List<SubscriptionData>> = subscriptionDao.getAllData()

    suspend fun insertData(subscriptionData: SubscriptionData) {
        subscriptionDao.insertData(subscriptionData)
    }

}