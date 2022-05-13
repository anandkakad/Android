@file:Suppress("DEPRECATION")

package com.example.grocery

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.grocery.retrofit.ApiClient.getClient
import com.example.grocery.util.Config
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialog_profile_verify.*
import kotlinx.android.synthetic.main.dialog_profile_verify.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {


    companion object {
        private const val EMAIL_REGEX = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"

        private const val MOBIL_REGEX = "[0-9]{10}"

        fun isEmailValid(email: String): Boolean
        { return EMAIL_REGEX.toRegex().matches(email) }

        fun isMobileValid(mobile: String):Boolean
        { return MOBIL_REGEX.toRegex().matches(mobile) }
    }


//    var sharedPreferences: SharedPreferences = TODO()
//    val editor:SharedPreferences.Editor =  sharedPreferences.edit()


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        sharedPreferences = this.getSharedPreferences(Config.SHARED_PREFRENCE_NAME,0)


        //APP VERSION
        val app_version = BuildConfig.VERSION_NAME
        Log.e("MyAPPversion",app_version)

        //DEVIC NAME
        val device_name=android.os.Build.MODEL
        Log.e("MYDEVIC",device_name)


        //CLICK ON LOGIN BUTTON
        btn_login.setOnClickListener {

         /*   val dialogView = LayoutInflater.from(this@LoginActivity)
                .inflate(R.layout.dialog_profile_verify, null)

            val edit_txt_full_name = dialogView.findViewById<EditText>(R.id.edit_txt_full_name)
            val edit_txt_mob = dialogView.findViewById<EditText>(R.id.edit_txt_mob)
            val edit_txt_alter_mob = dialogView.findViewById<EditText>(R.id.edit_txt_alter_mob)
            val edit_txt_pincode = dialogView.findViewById<EditText>(R.id.edit_txt_pincode)
            val edit_txt_addr1 = dialogView.findViewById<EditText>(R.id.edit_txt_addr1)
            val edit_txt_addr2 = dialogView.findViewById<EditText>(R.id.edit_txt_addr2)
            val edit_txt_addr3 = dialogView.findViewById<EditText>(R.id.edit_txt_addr3)
            val edit_txt_addr4 = dialogView.findViewById<EditText>(R.id.edit_txt_addr4)
            val edit_txt_addr5 = dialogView.findViewById<EditText>(R.id.edit_txt_addr5)
            val btn_save = dialogView.findViewById<Button>(R.id.btn_save)
            val btn_cancel = dialogView.findViewById<Button>(R.id.btn_cancel)

            edit_txt_mob.setText("0987654321")
            edit_txt_addr5.setText("Maharashtra,India")
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this@LoginActivity)
                .setView(dialogView)

            //show dialog
            val mAlertDialog = mBuilder.show()
            btn_cancel.setOnClickListener {
                mAlertDialog.dismiss()
            }

            btn_save.setOnClickListener {

                if(edit_txt_full_name.text.isEmpty() &&
                    edit_txt_alter_mob.text.isEmpty() &&
                    edit_txt_pincode.text.isEmpty() &&
                    edit_txt_addr1.text.isEmpty() &&
                    edit_txt_addr2.text.isEmpty() &&
                    edit_txt_addr3.text.isEmpty() &&
                    edit_txt_addr4.text.isEmpty()
                    ){

                    dialogView.textInputLayout_full_name.error="empty field"

                }else{

                    val intent:Intent=Intent(this@LoginActivity,DeliveryActivity::class.java)
                    startActivity(intent)
                }

            }*/


            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
//
//            val login_data = edit_text_login_data.text.toString().trim()
//            Log.e("logindata",login_data)

            /*   if(edit_text_login_data.text.isNotEmpty())
                {
                    textInputLayout.isErrorEnabled=false
                    if(login_data.contains("@"))
                    {
                        if(! isEmailValid(login_data))
                        {
                        textInputLayout.error="Invalid Email"
                        }
                        else
                        {
                        val login_type:String="email"
                        textInputLayout.isErrorEnabled=false
                        Log.e("Email call",login_data)
                        getCall(app_version,device_name,login_data,login_type)
                        }
                    }else{
                        if (! isMobileValid(login_data))
                        {
                        textInputLayout.error="Invalid Mobile"
                        }
                        else
                        {
                        val login_type:String="mobile"
                        textInputLayout.isErrorEnabled=false
                        Log.e("Mobile call",login_data)
                        getCall(app_version,device_name,login_data,login_type)
                        }
                    }
                }else{
                    textInputLayout.error="Empty field"
                }*/
        }


    }

    //NETWORK CALL FOR LOGIN USER
    private fun getCall(appVersion: String,deviceName: String,loginData: String,loginType: String)
    {
       val progressDialog = ProgressDialog(this@LoginActivity)
        //progressDialog.setMessage("Processing")
        progressDialog.setProgressStyle(0)
        progressDialog.show()
        getClient().addUser(appVersion,deviceName,loginData,loginType).enqueue(object : Callback<JsonObject>{

            override fun onResponse(call: Call<JsonObject>,response: Response<JsonObject>) {
                Log.e("Responses","Response data : ${Gson().toJson(response.body())}")
                Log.e("Responses", "Response status : ${response.toString()}")

                val usertoken:String= response.body()?.get("usertoken").toString()
                Log.e("usertoken",usertoken)
                val userseq:String= response.body()?.get("userseq").toString()
                Log.e("userseq",userseq)


             /*   editor.putString(Config.USER_TOKEN,usertoken)
                editor.putString(Config.USER_SEQ,userseq)
                editor.apply()
                editor.commit()*/
                // {"usertoken":"650516","userseq":"3"}

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()

                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("failRes","Response : ${Gson().toJson(t)}")

            }
        })



    }




}