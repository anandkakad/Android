package com.example.grocery.model

data class CategoryResponse(val categoryDOList:List<Category>){
    class Category (val categoryseq:Int,
                    val catimage:String,
                    val categoryname:String)

}
