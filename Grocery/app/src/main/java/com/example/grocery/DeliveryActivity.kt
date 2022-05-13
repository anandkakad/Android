package com.example.grocery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class DeliveryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)

        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = "Delivery"
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, CartActivity ::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_right)
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, CartActivity ::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_right)
    }
}