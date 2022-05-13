package com.example.grocery.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery.R
import com.example.grocery.adapter.FaqAdapter
import com.example.grocery.model.FaqResponse

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FaqFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FaqFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            return FaqFragment()
        }
    }

    private lateinit var recyclerView: RecyclerView
    lateinit var faqAdapter: FaqAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view: View = inflater.inflate(R.layout.fragment_faq, container, false)
        recyclerView = view.findViewById(R.id.rv_faq)
        //val activity = activity as Context
        recyclerView.layoutManager = LinearLayoutManager(activity)
        initView()
        return view

    }

    private fun initView() {

        val faqList=listOf(FaqResponse.Faq(1,"how to cancel the order after 1 day?","You can cancel items or orders by visiting the Your Orders section in Your Account. Go to Your Orders. Click Cancel Items. If you plan to cancel multiple items, select the check box next to each item you wish to remove from the order."),
                                               FaqResponse.Faq(2,"how to cancel the order after 1 day?","You can cancel items or orders by visiting the Your Orders section in Your Account. Go to Your Orders. Click Cancel Items. If you plan to cancel multiple items, select the check box next to each item you wish to remove from the order."),
                                               FaqResponse.Faq(3,"how to cancel the order after 1 day?","You can cancel items or orders by visiting the Your Orders section in Your Account. Go to Your Orders. Click Cancel Items. If you plan to cancel multiple items, select the check box next to each item you wish to remove from the order."))

        faqAdapter = FaqAdapter (faqList)
        recyclerView.adapter=faqAdapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //view.textTitle.text=getString(R.string.faq)
    }
}