package com.boonsuen.recurr.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.boonsuen.recurr.data.SubscriptionDatabase
import com.boonsuen.recurr.data.models.SubscriptionData
import com.boonsuen.recurr.data.repository.SubscriptionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscriptionViewModel(application: Application): AndroidViewModel(application) {

    private val subscriptionDao = SubscriptionDatabase.getDatabase(application).subscriptionDao()
    private val repository: SubscriptionRepository

    val getAllData: LiveData<List<SubscriptionData>>
    val sortByNameAscending: LiveData<List<SubscriptionData>>
    val sortByNameDescending: LiveData<List<SubscriptionData>>
    val sortByBillingPeriodShortToLong: LiveData<List<SubscriptionData>>
    val sortByBillingPeriodLongToShort: LiveData<List<SubscriptionData>>

    init {
        repository = SubscriptionRepository(subscriptionDao)
        getAllData = repository.getAllData
        sortByNameAscending = repository.sortByNameAscending
        sortByNameDescending = repository.sortByNameDescending
        sortByBillingPeriodShortToLong = repository.sortByBillingPeriodShortToLong
        sortByBillingPeriodLongToShort = repository.sortByBillingPeriodLongToShort
    }

    fun insertData(subscriptionData: SubscriptionData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(subscriptionData)
        }
    }

    fun updateData(subscriptionData: SubscriptionData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(subscriptionData)
        }
    }

    fun deleteItem(subscriptionData: SubscriptionData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(subscriptionData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

}