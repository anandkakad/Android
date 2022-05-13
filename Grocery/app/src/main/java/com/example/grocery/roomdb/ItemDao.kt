package com.example.grocery.roomdb

import androidx.room.*


@Dao
interface ItemDao {
    @Query("SELECT * FROM ItemTable")
    fun getAll(): MutableList<ItemTable>

    @Query("SELECT COUNT (*) from ItemTable")
    fun countItem(): Int

//    @Query("SELECT quantity FROM ItemTable WHERE ")
//    fun checkItem(itemseq:Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(itemtable: ItemTable)

    @Delete
    fun delete(itemtable: ItemTable)

    @Update
    fun update(itemtable: ItemTable)
}