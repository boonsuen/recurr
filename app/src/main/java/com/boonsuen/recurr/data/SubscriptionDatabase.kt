package com.boonsuen.recurr.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.boonsuen.recurr.data.models.SubscriptionData

@Database(entities = [SubscriptionData::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class SubscriptionDatabase : RoomDatabase() {

    abstract fun subscriptionDao(): SubscriptionDao

    companion object {
        @Volatile
        private var INSTANCE: SubscriptionDatabase? = null

        fun getDatabase(context: Context): SubscriptionDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        SubscriptionDatabase::class.java,
                        "subscription_database").build()
                INSTANCE = instance
                return instance
            }
        }
    }

}