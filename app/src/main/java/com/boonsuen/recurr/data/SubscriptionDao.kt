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

    @Query("SELECT * FROM subscription_table ORDER BY name")
    fun sortByNameAscending(): LiveData<List<SubscriptionData>>

    @Query("SELECT * FROM subscription_table ORDER BY name DESC")
    fun sortByNameDescending(): LiveData<List<SubscriptionData>>

    @Query("SELECT * FROM subscription_table ORDER BY CASE WHEN billingPeriod LIKE 'W%' THEN 1 WHEN billingPeriod LIKE 'M%' THEN 2 WHEN billingPeriod LIKE 'Y%' THEN 3 END")
    fun sortByBillingPeriodShortToLong(): LiveData<List<SubscriptionData>>

    @Query("SELECT * FROM subscription_table ORDER BY CASE WHEN billingPeriod LIKE 'Y%' THEN 1 WHEN billingPeriod LIKE 'M%' THEN 2 WHEN billingPeriod LIKE 'W%' THEN 3 END")
    fun sortByBillingPeriodLongToShort(): LiveData<List<SubscriptionData>>
}