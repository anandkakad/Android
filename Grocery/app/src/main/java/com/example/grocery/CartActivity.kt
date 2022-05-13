package com.example.grocery

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery.adapter.CartAdapter
import com.example.grocery.fragment.ProfileFragment
import com.example.grocery.roomdb.ItemRoomDatabase
import com.example.grocery.roomdb.ItemTable
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.dialog_profile_verify.*
import kotlinx.android.synthetic.main.dialog_profile_verify.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//import com.example.grocery.roomdb.ItemRoomDatabase
//import com.example.grocery.roomdb.ItemTable
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch


class CartActivity : AppCompatActivity() {

      private lateinit var recyclerView: RecyclerView
      lateinit var cartAdapter: CartAdapter
      lateinit var cartList: MutableList<ItemTable>
      lateinit var txt_empty_cart: TextView
      lateinit var bottom_frame: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = "MyCart"
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, ItemsActivity ::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_right)
           // onBackPressed()
        }


        txt_empty_cart = findViewById<TextView>(R.id.txt_empty_cart)
        bottom_frame = findViewById<FrameLayout>(R.id.bottom_frame)
        recyclerView = findViewById<RecyclerView>(R.id.rv_cart)
        recyclerView.layoutManager= LinearLayoutManager(this)

        initView()

        btn_checkout.setOnClickListener{

            showVerificationDialog()
        }

    }

    private fun showVerificationDialog() {


        AlertDialog.Builder(this).apply {
                val dialogView = this@CartActivity.layoutInflater.inflate(R.layout.dialog_profile_verify, null)

                val edit_txt_full_name = dialogView.findViewById<EditText>(R.id.edit_txt_full_name)
                val edit_txt_mob = dialogView.findViewById<EditText>(R.id.edit_txt_mob)
                val edit_txt_alter_mob = dialogView.findViewById<EditText>(R.id.edit_txt_alter_mob)
                val edit_txt_pincode = dialogView.findViewById<EditText>(R.id.edit_txt_pincode)
                val edit_txt_addr1 = dialogView.findViewById<EditText>(R.id.edit_txt_addr1)
                val edit_txt_addr2 = dialogView.findViewById<EditText>(R.id.edit_txt_addr2)
                val edit_txt_addr3 = dialogView.findViewById<EditText>(R.id.edit_txt_addr3)
                val edit_txt_addr4 = dialogView.findViewById<EditText>(R.id.edit_txt_addr4)
                val edit_txt_addr5 = dialogView.findViewById<EditText>(R.id.edit_txt_addr5)
                val btn_save = dialogView.findViewById<Button>(R.id.btn_save)
                val btn_cancel = dialogView.findViewById<Button>(R.id.btn_cancel)

                setView(dialogView)

            }.create().show()

        btn_cancel.setOnClickListener {  }

    }

    @SuppressLint("SetTextI18n")
    public fun getTotalAmount(totalAmt:Int) {

        val finalvalue:String= "Rs $totalAmt"

        if(totalAmt==0){
            bottom_frame.visibility= View.GONE
           // rv_cart.visibility=View.GONE
            txt_empty_cart.visibility=View.VISIBLE

        }else{
            txt_empty_cart.visibility=View.GONE
           // rv_cart.visibility=View.VISIBLE
            bottom_frame.visibility= View.VISIBLE
            txt_total_amt.text= "Total Amount : $finalvalue"
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initView() {


        CoroutineScope(Dispatchers.IO).launch {
           // val count = ItemRoomDatabase.getDatabase(this@CartActivity).itemDao().countItem()
            //Log.e("count", count.toString())
            cartList = ItemRoomDatabase.getDatabase(this@CartActivity).itemDao().getAll()

            if(cartList.isEmpty()){
                bottom_frame.visibility= View.GONE
                rv_cart.visibility=View.GONE
                txt_empty_cart.visibility=View.VISIBLE

            }else{
                txt_empty_cart.visibility=View.GONE
                rv_cart.visibility=View.VISIBLE
                cartAdapter = CartAdapter(this@CartActivity,cartList)
                recyclerView.adapter=cartAdapter
                bottom_frame.visibility= View.VISIBLE
            }

        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, ItemsActivity ::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_right)
    }
}