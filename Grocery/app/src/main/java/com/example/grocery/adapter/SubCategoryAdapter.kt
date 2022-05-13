package com.example.grocery.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery.ItemsActivity
import com.example.grocery.R
import com.example.grocery.model.SubCategoryResponse
import kotlinx.android.synthetic.main.single_subcategory_item.view.*

class SubCategoryAdapter(
    private val context: Context,
    private val subCategoryList: List<SubCategoryResponse.SubCategory>,
    private val itemsActivity: ItemsActivity
): RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>()  {
    var row_index:Int=-1

    class ViewHolder(v: View):RecyclerView.ViewHolder(v) {

        val txt_subcategory_name=v.txt_subcategory_name

/*        fun bind(
            subCategory: SubCategoryResponse.SubCategory,
            context: Context,
            itemsActivity: ItemsActivity,
            position: Int
        ) {
            var row_index:Int=-1
            txt_subcategory_name.text = subCategory.subcategoryname
             var txt_colo= txt_subcategory_name.currentTextColor
            txt_subcategory_name.setTextColor(Color.parseColor("#CBF44134"))

            itemView.setOnClickListener {

                row_index=position;
                notifyDataSetChanged();

                if(oldPosition==position){

                    txt_subcategory_name.setTextColor(Color.parseColor("#F21F2D81"))
                }else{

                    //Log.e("subCategoryName",subCategory.subcategoryname)
                    val subcategoryseq:Int=subCategory.subcategoryseq
                    txt_subcategory_name.setTextColor(Color.parseColor("#CBF44134"))
                    itemsActivity.getItems(subcategoryseq)
                }
                oldPosition=position

            }

        }*/

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubCategoryAdapter.ViewHolder {

        val v=LayoutInflater.from(parent.context).inflate(R.layout.single_subcategory_item,parent,false)
        return SubCategoryAdapter.ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return subCategoryList.size
    }

    override fun onBindViewHolder(holder: SubCategoryAdapter.ViewHolder, position: Int) {

        val subCategory = subCategoryList.get(position)
        //holder.bind(subCategory,context,itemsActivity,position)


        holder.txt_subcategory_name.text = subCategory.subcategoryname

        holder.txt_subcategory_name.setOnClickListener {

            row_index=position
            notifyDataSetChanged()
            //Log.e("subCategoryName",subCategory.subcategoryname)
            val subcategoryseq:Int=subCategory.subcategoryseq
            holder.txt_subcategory_name.setTextColor(Color.parseColor("#CBF44134"))
            itemsActivity.getItems(subcategoryseq)
        }

        if (row_index == position)
            holder.txt_subcategory_name.setTextColor(Color.parseColor("#CBF44134"));
        else
            holder.txt_subcategory_name.setTextColor(Color.parseColor("#F21F2D81"));
    }

}