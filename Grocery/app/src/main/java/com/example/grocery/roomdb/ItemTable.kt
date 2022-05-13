package com.example.grocery.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ItemTable")
data class ItemTable(
    //@PrimaryKey(autoGenerate = true)
    //val id: Int,
    @PrimaryKey
    @ColumnInfo(name = "id")  val id:Int?,
    @ColumnInfo(name = "quantitytypename") val quantitytypename:String?,
    @ColumnInfo(name = "quantity") val quantity:String?,
    @ColumnInfo(name = "itemname") val itemname:String?,
    @ColumnInfo(name = "itemimageurl") val itemimageurl:String?,
    @ColumnInfo(name = "currency") val currency:String?,
    @ColumnInfo(name = "price")val price: String?,
    @ColumnInfo(name = "date")val date:String?,
    @ColumnInfo(name = "time")val time:String?




)