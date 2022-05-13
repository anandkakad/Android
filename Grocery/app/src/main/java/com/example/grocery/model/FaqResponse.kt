package com.example.grocery.model

data class FaqResponse(val faqDOList:List<Faq>){
    class Faq (val faqseq:Int,
                    val question:String,
                    val answer:String)

}