package com.boonsuen.recurr.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.boonsuen.recurr.data.models.SubscriptionData

@Dao
interface SubscriptionDao {

    @Query("SELECT * FROM subscription_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<SubscriptionData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(subscriptionData: SubscriptionData)

}