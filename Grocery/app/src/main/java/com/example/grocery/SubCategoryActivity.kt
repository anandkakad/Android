package com.example.grocery

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery.adapter.SubCategoryAdapter
import com.example.grocery.model.SubCategoryResponse
import com.example.grocery.retrofit.ApiClient
import com.example.grocery.util.Config
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubCategoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    lateinit var subCategoryAdapter: SubCategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(Config.SHARED_PREFRENCE_NAME,0)
        val categorySeq: Int = sharedPreferences.getInt(Config.CATEGORY_SEQ,0)
        val categoryname: String? = sharedPreferences.getString(Config.CATEGORY_NAME,"")

        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = categoryname
        toolbar.setNavigationOnClickListener {

            val intent = Intent(this, MainActivity ::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_right)
        }

        recyclerView = findViewById<RecyclerView>(R.id.rv_subcategory)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        //initView(categorySeq)

    }

    private fun initView(categorySeq: Int) {

        val progressDialog = ProgressDialog(this@SubCategoryActivity)
        progressDialog.setProgressStyle(0)
        progressDialog.show()

        ApiClient.getClient().getSubCategory(categorySeq).enqueue(object :
            Callback<SubCategoryResponse> {
            override fun onFailure(call: Call<SubCategoryResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<SubCategoryResponse>,response: Response<SubCategoryResponse>)
            {
                Log.e("subRespons","Response : ${Gson().toJson(response.body())}")
                Log.e("subRespons", "Response status : ${response.toString()}")

                var subCategoryList: List<SubCategoryResponse.SubCategory>  = response.body()!!.subCategoryDOList
               // subCategoryAdapter = SubCategoryAdapter(this@SubCategoryActivity,subCategoryList)
               // recyclerView.adapter=subCategoryAdapter
                progressDialog.dismiss()

            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity ::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_right)
    }

}