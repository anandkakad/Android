package com.example.grocery.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(ItemTable::class),version = 1,exportSchema = false)
public abstract class ItemRoomDatabase: RoomDatabase() {

    abstract fun itemDao():ItemDao


    companion object{
        @Volatile
        var INSTANCE: ItemRoomDatabase?=null
        fun getDatabase(context: Context):ItemRoomDatabase{
            val tempInstance= INSTANCE

            if(tempInstance!=null) {
                return tempInstance
            }

            synchronized(this)
            {
                val instance= Room.databaseBuilder(context.applicationContext,
                    ItemRoomDatabase::class.java,
                    "itemDB")
                    .build()

                INSTANCE=instance
                return instance
            }
        }
    }
}
