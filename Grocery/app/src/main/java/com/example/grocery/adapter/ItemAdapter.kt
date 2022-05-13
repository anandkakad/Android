package com.example.grocery.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.grocery.ItemsActivity
import com.example.grocery.R
import com.example.grocery.model.Item
import com.example.grocery.model.ItemResponse
import com.example.grocery.roomdb.ItemRoomDatabase
import com.example.grocery.roomdb.ItemTable
import kotlinx.android.synthetic.main.single_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ItemAdapter(
    private val itemList: List<Item>,
    private val itemsActivity: ItemsActivity,
    private val itemDBList: MutableList<ItemTable>
):RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val img_item = v.img_item
        val txt_item_name = v.txt_item_name
        val txt_item_amount = v.txt_item_amount
        val btn_minu = v.btn_minu
        val btn_plus = v.btn_plus
        val txt_item_quantity = v.txt_item_quantity
        val btn_add = v.btn_add
        val btn_remove = v.btn_remove
        val view = v.view


        @SuppressLint("SetTextI18n")
        fun bind(
            item:Item,
            myDate: String,
            myTime: String,
            itemsActivity: ItemsActivity
        ) {


            Glide.with(itemsActivity).load(item.itemimageurl).placeholder(R.drawable.ic_food)
                .into(img_item)
            txt_item_name.text = item.itemname + " - " + item.quantitytypename
            // txt_item_quantity.text="0"
            txt_item_amount.text = item.currency + " " + item.price
            //  val quantity:String=txt_item_quantity.toString()
            if (txt_item_quantity.text.toString().contentEquals("0")) {
                btn_minu.isEnabled = false

            }


            btn_plus.setOnClickListener {

                val quantityfinal: Int = txt_item_quantity.text.toString().toInt()
                if (quantityfinal == 0) {
                    itemsActivity.setupBadge("add")
                    txt_item_quantity.text = (quantityfinal + 1).toString()
                    btn_minu.isEnabled = true
                    btn_plus.isEnabled = true
                    val qty: String = txt_item_quantity.text.toString()
                    txt_item_amount.text = item.currency + " " + item.price
                    btn_add.visibility = View.GONE
                    btn_remove.visibility = View.VISIBLE
                    getInsert(itemsActivity, item, qty, myDate, myTime)


                } else if (quantityfinal in 1..4) {


                    if (quantityfinal == 4) {
                        btn_plus.isEnabled = false
                        btn_minu.isEnabled = true

                        txt_item_quantity.text = (quantityfinal + 1).toString()
                        val qty: String = txt_item_quantity.text.toString()
                        txt_item_amount.text = item.currency + " " + item.price
                        btn_add.visibility = View.GONE
                        btn_remove.visibility = View.VISIBLE
                        getInsert(itemsActivity, item, qty, myDate, myTime)

                    } else {
                        btn_plus.isEnabled = true
                        btn_minu.isEnabled = true

                        txt_item_quantity.text = (quantityfinal + 1).toString()
                        val qty: String = txt_item_quantity.text.toString()
                        txt_item_amount.text = item.currency + " " + item.price
                        btn_add.visibility = View.GONE
                        btn_remove.visibility = View.VISIBLE
                        getInsert(itemsActivity, item, qty, myDate, myTime)
                    }
                }


            }

            btn_minu.setOnClickListener {
                val quantityfinal: Int = txt_item_quantity.text.toString().toInt()

                if ((quantityfinal == 0)) {
                    btn_minu.isEnabled = false
                    btn_plus.isEnabled = true
                    txt_item_quantity.text = "0"
                    val qty: String = txt_item_quantity.text.toString()
                    btn_remove.visibility = View.GONE
                    btn_add.visibility = View.VISIBLE

                    getDelete(itemsActivity, item, qty, myDate, myTime)

                } else if (quantityfinal in 1..5) {

                    if (quantityfinal == 1) {
                        itemsActivity.setupBadge("remove")
                        txt_item_quantity.text = (quantityfinal - 1).toString()
                        btn_minu.isEnabled = false
                        btn_plus.isEnabled = true

                        btn_remove.visibility = View.GONE
                        btn_add.visibility = View.VISIBLE
                        txt_item_amount.text = item.currency + " " + item.price
                        val qty: String = txt_item_quantity.text.toString()
                        getDelete(itemsActivity, item, qty, myDate, myTime)


                    } else {
                        txt_item_quantity.text = (quantityfinal - 1).toString()
                        btn_minu.isEnabled = true
                        btn_plus.isEnabled = true
                        btn_add.visibility = View.GONE
                        btn_remove.visibility = View.VISIBLE
                        txt_item_amount.text = item.currency + " " + item.price
                        //roomdb
                        val qty: String = txt_item_quantity.text.toString()
                        getInsert(itemsActivity, item, qty, myDate, myTime)
                    }


                }

            }


            btn_add.setOnClickListener {

                btn_minu.isEnabled=true
                btn_plus.isEnabled=true
                val quantityfinal: Int = txt_item_quantity.text.toString().toInt()
                txt_item_quantity.text = (quantityfinal + 1).toString()
                val qty: String = txt_item_quantity.text.toString()
                btn_add.visibility = View.GONE
                btn_remove.visibility = View.VISIBLE

                getInsert(itemsActivity, item, qty, myDate, myTime)
                itemsActivity.setupBadge("add")

            }

            btn_remove.setOnClickListener {

                txt_item_quantity.text = "0"
                val qty: String = txt_item_quantity.text.toString()
                btn_remove.visibility = View.GONE
                btn_add.visibility = View.VISIBLE

                getDelete(itemsActivity, item, qty, myDate, myTime)
                itemsActivity.setupBadge("remove")
            }

        }

        private fun getInsert(
            itemsActivity: ItemsActivity,
            item:Item,
            qty: String,
            myDate: String,
            myTime: String
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                // get the dao and call insertMultipleImages() on it
                ItemRoomDatabase.getDatabase(itemsActivity).itemDao()
                    .insert(
                        itemtable = ItemTable(
                            item.itemseq,
                            item.quantitytypename,
                            qty,
                            item.itemname,
                            item.itemimageurl,
                            item.currency,
                            item.price,
                            myDate,
                            myTime
                        )
                    )
                // Log.e("count", count.toString())
            }


        }

        private fun getDelete(
            itemsActivity: ItemsActivity,
            item:Item,
            qty: String,
            myDate: String,
            myTime: String
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                // get the dao and call insertMultipleImages() on it
                ItemRoomDatabase.getDatabase(itemsActivity).itemDao()
                    .delete(
                        itemtable = ItemTable(
                            item.itemseq,
                            item.quantitytypename,
                            qty,
                            item.itemname,
                            item.itemimageurl,
                            item.currency,
                            item.price,
                            myDate,
                            myTime
                        )
                    )
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_item, parent, false)
        return ItemAdapter.ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = itemList.get(position)
        val myTime: String =
            java.text.SimpleDateFormat("h:mm a", Locale.getDefault()).format(java.util.Date())
        val myDate: String =
             java.text.SimpleDateFormat(" dd MMM yyyy", Locale.getDefault()).format(java.util.Date())


        if (itemDBList.isNotEmpty()){
            val itemListDBlen:Int=itemDBList.size
            var x = 0
            while (x < itemListDBlen) {
                val itemDB = itemDBList.get(x)
                if (itemDB.id == item.itemseq)
                {
                    holder.txt_item_quantity.text = itemDB.quantity
                    holder.btn_remove.visibility = View.VISIBLE
                    holder.btn_add.visibility = View.GONE
                    holder.bind(item, myDate, myTime, itemsActivity)
                    break
              }
                x++ // Same as x += 1

            }
            holder.bind(item, myDate, myTime, itemsActivity)

        }else{
            holder.bind(item, myDate, myTime, itemsActivity)

        }



    /*    if (itemDBList.isNotEmpty())
        {
            val itemDB = itemDBList.get(position)
            if (itemDB.id == item.itemseq)
            {
                holder.txt_item_quantity.text = itemDB.quantity
                holder.btn_remove.visibility = View.VISIBLE
                holder.btn_add.visibility = View.GONE
            }
        }

        holder.bind(item, myDate, myTime, itemsActivity)*/
    }
}