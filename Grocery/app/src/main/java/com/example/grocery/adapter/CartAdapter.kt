package com.example.grocery.adapter

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.grocery.CartActivity
import com.example.grocery.R
import com.example.grocery.roomdb.ItemRoomDatabase
import com.example.grocery.roomdb.ItemTable
import kotlinx.android.synthetic.main.single_cart.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CartAdapter(
    private val cartActivity: CartActivity,
    private val cartList: MutableList<ItemTable>
):RecyclerView.Adapter<CartAdapter.ViewHolder>(){

       var tempTotal:Int=0
    class ViewHolder(v:View):RecyclerView.ViewHolder(v)  {

        val img_cart=v.img_cart
        val txt_cart_name=v.txt_cart_name
        val txt_cart_quantity=v.txt_cart_quantity
        val txt_cart_currency=v.txt_cart_currency
        val txt_cart_amount=v.txt_cart_amount
        val btn_cart_minu=v.btn_cart_minu
        val btn_cart_plus=v.btn_cart_plus
        val imgview_cart_delete=v.imgview_cart_delete

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_cart,parent,false)
        return CartAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val cart = cartList.get(position)
        val myTime: String = java.text.SimpleDateFormat("h:mm a", Locale.getDefault()).format(java.util.Date())
        val myDate: String = java.text.SimpleDateFormat(" dd MMM yyyy", Locale.getDefault()).format(java.util.Date())

        Glide.with(cartActivity).load(cart.itemimageurl).placeholder(R.drawable.ic_food).into(holder.img_cart)
        holder.txt_cart_name.text=cart.itemname+"-"+cart.quantitytypename
       // val qty:Int=cart.quantity.toString().toInt()
      // val amt:Int=cart.price.toString().toInt()
        holder.txt_cart_quantity.text=cart.quantity
       // holder.txt_cart_quantity.text=(qty*amt).toString()
        holder.txt_cart_currency.text=cart.currency
        holder.txt_cart_amount.text=(cart.quantity.toString().toInt() * cart.price.toString().toInt()).toString()
        tempTotal += holder.txt_cart_amount.text.toString().toInt()
        cartActivity.getTotalAmount(tempTotal)


        holder.btn_cart_plus.setOnClickListener {
            val qtyInt = holder.txt_cart_quantity.text.toString().toInt()
            if(qtyInt in 1..4) {

                holder.btn_cart_plus.isEnabled = qtyInt != 4
                holder.btn_cart_minu.isEnabled = true

                    holder.txt_cart_quantity.text = (qtyInt + 1).toString()
                    val amtInt: Int = cart.price.toString().toInt()
                    val qtyfinal: Int = holder.txt_cart_quantity.text.toString().toInt()
                    holder.txt_cart_amount.text = (amtInt * qtyfinal).toString()

                    val qty: String = holder.txt_cart_quantity.text.toString()
                    val price: String = holder.txt_cart_amount.text.toString()
                    //db
                    getUpdate(cart, qty, price, myDate, myTime, cartActivity)
                   tempTotal += amtInt
                   cartActivity.getTotalAmount(tempTotal)

            }






        }


        holder.btn_cart_minu.setOnClickListener {

            val qtyInt = holder.txt_cart_quantity.text.toString().toInt()

            if(qtyInt!=1)
            {
                holder.btn_cart_minu.isEnabled=true
                holder.txt_cart_quantity.text=(qtyInt-1).toString()

               val amtInt:Int=cart.price.toString().toInt()
               val priceInt:Int=holder.txt_cart_amount.text.toString().toInt()
               holder.txt_cart_amount.text=(priceInt-amtInt).toString()

                val qty:String=holder.txt_cart_quantity.text.toString()
                val price:String=holder.txt_cart_amount.text.toString()

                getUpdate(cart,qty,price,myDate,myTime,cartActivity)
                tempTotal -= amtInt
                cartActivity.getTotalAmount(tempTotal)
            }else{

                holder.btn_cart_minu.isEnabled=false

            }

        }

        holder.imgview_cart_delete.setOnClickListener {

            val builder = AlertDialog.Builder(cartActivity)
            builder.setCancelable(false);
            builder.setTitle("Are you sure you want to Delete ?");
            builder.setPositiveButton("Yes")
            {
                dialogInterface, which ->

                getDelete(cart,cartActivity)
                cartList.remove(cart)
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartList.size);
                tempTotal -= holder.txt_cart_amount.text.toString().toInt()
                cartActivity.getTotalAmount(tempTotal)
            }

            builder.setNegativeButton("No")
            {
                    dialogInterface, i ->
                    dialogInterface.dismiss()
            }

            builder.setOnKeyListener(DialogInterface.OnKeyListener {

                dialogInterface, i, keyEvent ->

                return@OnKeyListener i == KeyEvent.KEYCODE_BACK;

            })
            builder.show()
        }

    }

    private fun getUpdate(
        cart: ItemTable,
        qty: String,
        price: String,
        myDate: String,
        myTime: String,
        cartActivity: CartActivity
    ) {
        CoroutineScope(Dispatchers.IO).launch{
            ItemRoomDatabase.getDatabase(cartActivity)
                            .itemDao()
                            .update(ItemTable(cart.id,
                                cart.quantitytypename,
                                qty,
                                cart.itemname,
                                cart.itemimageurl,
                                cart.currency,
                                price,
                                myDate,
                                myTime))
        }
    }


    private fun getDelete(cart: ItemTable, cartActivity: CartActivity) {

        CoroutineScope(Dispatchers.IO).launch {
                ItemRoomDatabase.getDatabase(cartActivity).itemDao().delete(cart)
        }

    }

}
