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

    init {
        repository = SubscriptionRepository(subscriptionDao)
        getAllData = repository.getAllData
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

}