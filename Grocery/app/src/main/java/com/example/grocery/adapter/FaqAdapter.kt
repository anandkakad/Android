package com.example.grocery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery.R
import com.example.grocery.model.FaqResponse
import kotlinx.android.synthetic.main.single_faq_item.view.*

class FaqAdapter(
    private val faqList: List<FaqResponse.Faq>
): RecyclerView.Adapter<FaqAdapter.ViewHolder>() {

    class ViewHolder(v: View):RecyclerView.ViewHolder(v) {

        private val txtQuestion=v.txt_question
        private val txtAnswer=v.txt_answer

        fun bind(faq: FaqResponse.Faq) {

            txtQuestion.text=faq.question.toString()
            txtAnswer.text=faq.answer.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_faq_item,parent,false)
        return FaqAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return faqList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val faq = faqList[position]
        holder.bind(faq)
    }

}
