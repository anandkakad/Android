@file:Suppress("DEPRECATION")

package com.example.grocery

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery.adapter.ItemAdapter
import com.example.grocery.adapter.SubCategoryAdapter
import com.example.grocery.model.Item
import com.example.grocery.model.ItemResponse
import com.example.grocery.model.SubCategoryResponse
import com.example.grocery.retrofit.ApiClient
import com.example.grocery.roomdb.ItemRoomDatabase
import com.example.grocery.util.Config
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemsActivity : AppCompatActivity(){

    lateinit var recyclerView1: RecyclerView
    lateinit var recyclerView2: RecyclerView
    lateinit var textCartItemCount:TextView
    lateinit var txt_empty_list:TextView
    lateinit var subCategoryAdapter:SubCategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(Config.SHARED_PREFRENCE_NAME,0)
        val categorySeq: Int = sharedPreferences.getInt(Config.CATEGORY_SEQ,0)
        val categoryName: String? = sharedPreferences.getString(Config.CATEGORY_NAME,"")
//        val subcategoryseq: Int = sharedPreferences.getInt(Config.SUBCATEGORY_SEQ,0)
//        val subcategoryname: String? = sharedPreferences.getString(Config.SUBCATEGORY_NAME,"")

        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = categoryName
        toolbar.setNavigationOnClickListener {
            onBackPressed()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_right)
        }

        recyclerView1 = findViewById<RecyclerView>(R.id.rv_subcategory)
        recyclerView1.layoutManager=LinearLayoutManager(this@ItemsActivity,LinearLayoutManager.HORIZONTAL,false)

        txt_empty_list=findViewById<TextView>(R.id.txt_empty_list)

        recyclerView2 = findViewById<RecyclerView>(R.id.rv_items)
        recyclerView2.layoutManager=LinearLayoutManager(this@ItemsActivity)

        getSubCategoryList(categorySeq)
        //getAllItemList(categorySeq)
    }

    private fun getSubCategoryList(categorySeq: Int) {

        val progressDialog = ProgressDialog(this)
        progressDialog.setProgressStyle(0)
        progressDialog.show()

        ApiClient.getClient().getSubCategory(categorySeq).enqueue(object :
            Callback<SubCategoryResponse> {
            override fun onFailure(call: Call<SubCategoryResponse>, t: Throwable) {

            }
            override fun onResponse(call: Call<SubCategoryResponse>, response: Response<SubCategoryResponse>)
            {
                Log.e("subRespons","Response : ${Gson().toJson(response.body())}")
                Log.e("subRespons", "Response status : ${response.toString()}")

                val status:Boolean = response.body()!!.status
                var subCategoryList: List<SubCategoryResponse.SubCategory> = response.body()!!.subCategoryDOList

                subCategoryAdapter = SubCategoryAdapter(this@ItemsActivity,subCategoryList,this@ItemsActivity)
                recyclerView1.adapter=subCategoryAdapter

                var itemList: List<Item> = response.body()!!.itemDOList

                if(!status){
                    recyclerView2.visibility=View.GONE
                    txt_empty_list.visibility=View.VISIBLE

                }else{
                    txt_empty_list.visibility=View.GONE
                    recyclerView2.visibility=View.VISIBLE
                    getListDB(itemList)
                }
                progressDialog.dismiss()


            }
        })

    }

    private fun getListDB(
       itemList: List<Item>
   ) {

       GlobalScope.launch {
               val itemDBList  = ItemRoomDatabase.getDatabase(this@ItemsActivity).itemDao().getAll()
               Log.e("itemDBList",itemDBList.toString())

               runOnUiThread{
                   val itemAdapter: ItemAdapter = ItemAdapter(itemList,this@ItemsActivity,itemDBList)
                   recyclerView2.adapter= itemAdapter
               }
       }

       }


    fun getItems(
        subcategoryseq: Int
    ) {
        ApiClient.getClient().getSubCategorySeq(subcategoryseq).enqueue(object: Callback<ItemResponse>{
            override fun onFailure(call: Call<ItemResponse>, t: Throwable) {
            }
            override fun onResponse(call: Call<ItemResponse>, response: Response<ItemResponse>) {

                Log.e("ItemResponse","Response : ${Gson().toJson(response.body())}")
               // Log.e("ItemR", "Response status : ${response.toString()}")

                val status:Boolean = response.body()!!.status
                val itemList: List<Item> = response.body()!!.itemDOList
                if(!status){
                    recyclerView2.visibility=View.GONE
                    txt_empty_list.visibility=View.VISIBLE

                }else{
                    txt_empty_list.visibility=View.GONE
                    recyclerView2.visibility=View.VISIBLE
                    getListDB(itemList)
                }

            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)

        val menuItem = menu!!.findItem(R.id.action_cart)
        val actionView: View = MenuItemCompat.getActionView(menuItem)
        textCartItemCount = actionView.findViewById(R.id.cart_badge)

        getCartCount()

        actionView.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }
        return true
    }

    private fun getCartCount() {

        GlobalScope.launch {

            val count: Int = ItemRoomDatabase.getDatabase(this@ItemsActivity).itemDao().countItem()

            runOnUiThread {

                val temp:Int
                if (textCartItemCount.text.toString().isEmpty()) {
                    textCartItemCount.visibility = View.GONE
                    temp = 0
                } else {
                    textCartItemCount.visibility = View.VISIBLE
                    temp = textCartItemCount.text.toString().toInt()
                }


                if (count == 0) {
                      //textCartItemCount.visibility=View.GONE
                } else {
                    if(count>99){


                        textCartItemCount.setText(("99+").toString())
                        textCartItemCount.visibility = View.VISIBLE

                    }else{

                        textCartItemCount.setText((temp+count).toString())
                        textCartItemCount.visibility = View.VISIBLE
                    }

                }

            }
        }

    }


    @SuppressLint("SetTextI18n")
    fun setupBadge(btn: String) {
        val inputVal: Int

        if (textCartItemCount.text.toString().isEmpty()) {
            textCartItemCount.visibility = View.GONE
            inputVal = 0
        } else {
            textCartItemCount.visibility = View.VISIBLE
            inputVal = textCartItemCount.text.toString().toInt()
        }

        if (btn.contentEquals("add")) {

            val updateCount: Int = inputVal + 1
            if(updateCount>99){
                textCartItemCount.visibility = View.VISIBLE
                textCartItemCount.setText("99+")
            }else{
                textCartItemCount.visibility = View.VISIBLE
                textCartItemCount.setText(updateCount.toString())
            }

        } else if (btn.contentEquals("remove")) {

            val updateCount: Int = inputVal - 1
            if(updateCount!=0) {
                if(updateCount>99){
                    textCartItemCount.visibility = View.VISIBLE
                    textCartItemCount.setText("99+")
                }else{
                    textCartItemCount.visibility = View.VISIBLE
                    textCartItemCount.setText(updateCount.toString())
                }

            }else{
                textCartItemCount.visibility=View.GONE
                textCartItemCount.setText("")

            }

        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_cart) {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent = Intent(this,MainActivity ::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_right)
    }


}