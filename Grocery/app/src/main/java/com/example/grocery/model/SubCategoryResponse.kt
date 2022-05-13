package com.example.grocery.model

data class SubCategoryResponse (val status:Boolean,val subCategoryDOList:List<SubCategory>,val itemDOList :List<Item>){

    class SubCategory(val subcatimage :String,
                      val subcategoryname: String,
                      val subcategoryseq:Int)

}