package com.boonsuen.recurr.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.boonsuen.recurr.data.models.SubscriptionData

@Dao
interface SubscriptionDao {

    @Query("SELECT * FROM subscription_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<SubscriptionData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(subscriptionData: SubscriptionData)

    @Update
    suspend fun updateData(subscriptionData: SubscriptionData)

    @Delete
    suspend fun deleteItem(subscriptionData: SubscriptionData)

    @Query("DELETE FROM subscription_table")
    suspend fun deleteAll()
}