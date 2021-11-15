package com.example.kotlin_romdatabase

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_romdatabase.sqlite.DatabaseBuilder
import com.example.kotlin_romdatabase.table.user
import com.example.kotlin_romdatabase.utils.Status
import com.example.kotlin_romdatabase.viewmodel.MainActivityViewModel
import com.example.kotlin_romdatabase.viewmodel.ViewModelFactory
import android.graphics.Color

import android.widget.LinearLayout

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.Button
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView


class ShowUseDetailsActivity : AppCompatActivity() {
    lateinit var recyclerview :RecyclerView
    lateinit var customAdapter: CustomAdapter
    lateinit var viewModel : MainActivityViewModel
    lateinit var dialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_use_details)
        setupViewModel()

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false) // if you want user to wait for some process to finish,
        builder.setView(R.layout.processdialog)
        dialog= builder.create()


        recyclerview=findViewById(R.id.recyclerview)
        val layoutManager =LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true)
        layoutManager.stackFromEnd=true


        recyclerview.layoutManager=layoutManager


        getAllUserDetails()

    }

    private fun getAllUserDetails() {
        viewModel.getAllUserDetails().observe(this,{
            if(it.status== Status.LOADING){
                dialog.show()
            }else if(it.status== Status.ERROR){
                dialog.dismiss()
                applicationContext.showShortToast("error")
            }else{
                try {
                    dialog.dismiss()
                    Log.w("response", it.data.toString())
                    val userdata : List<user>? =it.data
                    userdata?.let {
                        customAdapter =CustomAdapter(it,object :CustomAdapter.CallBackInterface{
                            override fun getUserId(userid: Int) {
                                getAndShowUserDetails(userid)
                            }
                            override fun longPressGetUser(usermodel: user) {
                                deleteUser(usermodel)
                            }
                        })
                        recyclerview.adapter=customAdapter
                    }
                    customAdapter.notifyDataSetChanged()
                }catch (e : Exception){
                    application.showShortToast(e.message.toString())
                }
            }
        })
    }

    private fun deleteUser(usermodel: user) {
        val customDialog = Dialog(this)
        customDialog.requestWindowFeature(1)
        customDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setContentView(R.layout.dialog_layout)
        customDialog.setCanceledOnTouchOutside(false)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(customDialog.window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        customDialog.show()
        customDialog.window?.attributes = lp


        val del_Button : Button =customDialog.findViewById(R.id.btn_delete)
        val update_Button : Button =customDialog.findViewById(R.id.btn_update)
        val main_Layout : LinearLayout = customDialog.findViewById(R.id.main_Layout)
        val textview :TextView =customDialog.findViewById(R.id.textview)
        val updatebtn:Button =customDialog.findViewById(R.id.button)
        val username:EditText=customDialog.findViewById(R.id.username)
        val userpassword : EditText = customDialog.findViewById(R.id.userpassword)

        textview.text="Update Details"
        main_Layout.visibility= View.GONE
        username.setText(usermodel.username)
        userpassword.setText(usermodel.userpassword)

        del_Button.setOnClickListener{
            customDialog.dismiss()
            viewModel.deleteUserByModel(usermodel).observe(this,{
                if(it.status== Status.LOADING){
                    dialog.show()
                }else if(it.status== Status.ERROR){
                    dialog.dismiss()
                    applicationContext.showShortToast("error")
                }else{
                    try {
                        dialog.dismiss()
                        application.showShortToast(it.data.toString())
                        getAllUserDetails()

                    }catch (e : Exception){
                        application.showShortToast(e.message.toString())
                    }
                }
            })
        }

        update_Button.setOnClickListener {
            main_Layout.visibility=View.VISIBLE
            update_Button.visibility=View.GONE
        }

        updatebtn.setOnClickListener {
            customDialog.dismiss()
            usermodel.username=username.text.toString()
            usermodel.userpassword=userpassword.text.toString()
            viewModel.updateUserByModel(usermodel).observe(this,{
                if(it.status== Status.LOADING){
                    dialog.show()
                }else if(it.status== Status.ERROR){
                    dialog.dismiss()
                    applicationContext.showShortToast("error")
                }else{
                    try {
                        dialog.dismiss()
                        application.showShortToast(it.data.toString())
                        getAllUserDetails()

                    }catch (e : Exception){
                        application.showShortToast(e.message.toString())
                    }
                }
            })
        }


    }

    private fun getAndShowUserDetails(userid: Int) {
        viewModel.getUserById(userid).observe(this,{
            if(it.status== Status.LOADING){
                dialog.show()
            }else if(it.status== Status.ERROR){
                dialog.dismiss()
                applicationContext.showShortToast("error")
            }else{
                try {
                    dialog.dismiss()
                    Log.w("response", it.data.toString())
                    it.data?.let {
                        application.showShortToast(it.id.toString() + " | "+ it.userpassword + " | " + it.username)
                    }
                }catch (e : Exception){
                    application.showShortToast(e.message.toString())
                }
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(DatabaseBuilder.getInstance(applicationContext))
        ).get(MainActivityViewModel::class.java)
    }
}