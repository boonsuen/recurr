package com.boonsuen.recurr.data.repository

import androidx.lifecycle.LiveData
import com.boonsuen.recurr.data.SubscriptionDao
import com.boonsuen.recurr.data.models.SubscriptionData

class SubscriptionRepository(private val subscriptionDao: SubscriptionDao) {

    val getAllData: LiveData<List<SubscriptionData>> = subscriptionDao.getAllData()
    val sortByNameAscending: LiveData<List<SubscriptionData>> = subscriptionDao.sortByNameAscending()
    val sortByNameDescending: LiveData<List<SubscriptionData>> = subscriptionDao.sortByNameDescending()
    val sortByAmountLowToHigh: LiveData<List<SubscriptionData>> = subscriptionDao.sortByAmountLowToHigh()
    val sortByAmountHighToLow: LiveData<List<SubscriptionData>> = subscriptionDao.sortByAmountHighToLow()
    val sortByBillingPeriodShortToLong: LiveData<List<SubscriptionData>> = subscriptionDao.sortByBillingPeriodShortToLong()
    val sortByBillingPeriodLongToShort: LiveData<List<SubscriptionData>> = subscriptionDao.sortByBillingPeriodLongToShort()

    suspend fun insertData(subscriptionData: SubscriptionData) {
        subscriptionDao.insertData(subscriptionData)
    }

    suspend fun updateData(subscriptionData: SubscriptionData) {
        subscriptionDao.updateData(subscriptionData)
    }

    suspend fun deleteItem(subscriptionData: SubscriptionData) {
        subscriptionDao.deleteItem(subscriptionData)
    }

    suspend fun deleteAll() {
        subscriptionDao.deleteAll()
    }

}