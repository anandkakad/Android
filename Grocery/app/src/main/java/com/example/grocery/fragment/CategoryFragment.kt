@file:Suppress("DEPRECATION")

package com.example.grocery.fragment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery.R
import com.example.grocery.`interface`.OnItemClickListener
import com.example.grocery.adapter.CategoryAdapter
import com.example.grocery.model.CategoryResponse
import com.example.grocery.model.SubCategoryResponse
import com.example.grocery.retrofit.ApiClient
import com.example.grocery.SubCategoryActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            return CategoryFragment()
        }
    }

    private lateinit var recyclerView: RecyclerView
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var layoutManager:GridLayoutManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_category, container, false)
        //var view.rv_category = findViewById(R.id.rv_category) as RecyclerView
        recyclerView = view.findViewById<RecyclerView>(R.id.rv_category)
        val activity = activity as Context
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        initView(activity)
//        categoryAdapter=CategoryAdapter(this)
        return view
    }

    private fun initView(activity: Context) {

        val progressDialog = ProgressDialog(getActivity())
        progressDialog.setProgressStyle(0)
        progressDialog.show()

        ApiClient.getClient().getCategory().enqueue(object : Callback<CategoryResponse> {
            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.e("catfailRes","Response : ${Gson().toJson(t)}")
                t.printStackTrace()
            }

            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {

                Log.e("cMyRespons","Response : ${Gson().toJson(response.body())}")
                Log.e("CatRespons", "Response status : ${response.toString()}")

               // val songResponse : SongResponse? = response.body()
                val categoryList: List<CategoryResponse.Category>? = response.body()?.categoryDOList
                categoryAdapter = categoryList?.let { CategoryAdapter(activity, it) }!!
                recyclerView.adapter=categoryAdapter
                progressDialog.dismiss()

            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
