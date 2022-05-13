package com.example.grocery.`interface`


import com.example.grocery.model.CategoryResponse
import com.example.grocery.model.SubCategoryResponse

public interface OnItemClickListener {

    //fun onCategoryItemClicked(category: CategoryResponse.Category)
    fun onSubItemClicked(subCategory: SubCategoryResponse.SubCategory)


}
