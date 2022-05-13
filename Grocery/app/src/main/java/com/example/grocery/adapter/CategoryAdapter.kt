package com.example.grocery.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.grocery.ItemsActivity
import com.example.grocery.R
import com.example.grocery.SubCategoryActivity
import com.example.grocery.`interface`.OnItemClickListener
import com.example.grocery.model.CategoryResponse.Category
import com.example.grocery.util.Config
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.android.synthetic.main.single_category_item.view.*

class CategoryAdapter(
    val context: Context,
    private val categoryList: List<Category>
):RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    class ViewHolder(v: View):RecyclerView.ViewHolder(v) {

        private val txt_category_name=v.txt_category_name
        private val img_category_item=v.img_category_item

        @SuppressLint("RestrictedApi")
        fun bind(
            category: Category,
            context: Context
        )
        {

            getActivity(context)?.let { Glide.with(it).load(category.catimage).placeholder(R.drawable.ic_food).into(img_category_item) }
            txt_category_name.text = category.categoryname

            itemView.setOnClickListener {
                //clickListener.onCategoryItemClicked(category)

                Log.i("SEQ", category.categoryseq.toString())
                Log.i("NAME",category.categoryname)

                val categoryseq:Int=category.categoryseq
                val categoryname:String=category.categoryname
                val sharedPreferences:SharedPreferences = context.getSharedPreferences(Config.SHARED_PREFRENCE_NAME,0)
                val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                editor.putInt(Config.CATEGORY_SEQ,categoryseq)
                editor.putString(Config.CATEGORY_NAME,categoryname)
                editor.remove(Config.SUBCATEGORY_SEQ)
                editor.remove(Config.SUBCATEGORY_NAME)
                editor.apply()

                val intent = Intent(context, ItemsActivity::class.java)
                context.startActivity(intent)

//                intent.putExtra("categoryseq",category.categoryseq)
//                intent.putExtra("categoryname",category.categoryname)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        //TODO("Not yet implemented")
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_category_item,parent,false)
        return ViewHolder(v)

    }

    override fun getItemCount(): Int {

        return categoryList.size
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val category =  categoryList.get(position)

       // val user = users.get(position)
       // myHolder.bind(users.get(position),itemClickListener)

        //val category:Category  = categoryList[position]
        //val img:String=category.img
//        Picasso.get()
//            .load("https://www.androidtutorialpoint.com/wp-content/uploads/2016/11/Aishwarya-Rai.jpg")
//            .fit().into(holder.img_category_item)
        //Picasso.with(getActivity(context)).load("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQuLrC7PzJsrq9r7tTU-Prf0MwE7bPVCDOqLpsJsn2Veysz631u&usqp=CAU").resize(70,70).into(holder.img_category_item);
        //Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQuLrC7PzJsrq9r7tTU-Prf0MwE7bPVCDOqLpsJsn2Veysz631u&usqp=CAU").resize(70,70).into(holder.img_category_item)
        //holder.img_category_item.setImageResource(category.img)

        //getActivity(context)?.let { Glide.with(it).load(img).placeholder(R.drawable.ic_food).into(holder.img_category_item) }
        //holder.txt_category_name.text=category.name



        holder.bind(category,context)
    }
}


/*private fun ImageView.setImageResource(img: String) {
    //
    Picasso.get().load(img).into(img_category_item)
}*/


