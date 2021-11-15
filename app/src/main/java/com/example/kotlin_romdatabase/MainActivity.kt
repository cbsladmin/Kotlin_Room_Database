package com.example.kotlin_romdatabase

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_romdatabase.sqlite.DatabaseBuilder
import com.example.kotlin_romdatabase.table.user
import com.example.kotlin_romdatabase.utils.Status
import com.example.kotlin_romdatabase.viewmodel.MainActivityViewModel
import com.example.kotlin_romdatabase.viewmodel.ViewModelFactory


class MainActivity : AppCompatActivity() {
    lateinit var username : EditText
    lateinit var userpassowrd :EditText
    lateinit var button: Button

    lateinit var viewModel : MainActivityViewModel
    lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()


        username=findViewById(R.id.username)
        userpassowrd=findViewById(R.id.userpassword)
        button=findViewById(R.id.button)


        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false) // if you want user to wait for some process to finish,

        builder.setView(R.layout.processdialog)
        dialog= builder.create()


        button.setOnClickListener {
            if(userpassowrd.text.toString().isNotEmpty() && username.text.toString().isNotEmpty()){
                val userInsertData=user(username = username.text.toString() , userpassword = userpassowrd.text.toString())
                viewModel.insertUser(userInsertData).observe(this, Observer {
                    if(it.status== Status.LOADING){
                        dialog.show()
                    }else if(it.status== Status.ERROR){
                        dialog.dismiss()
                        applicationContext.showShortToast("error")
                    }else{
                        try {
                            dialog.dismiss()
                            applicationContext.showShortToast(it.data.toString())
                            startActivity(Intent(this,ShowUseDetailsActivity::class.java))
                        }catch (e : Exception){
                            applicationContext.showShortToast(e.message.toString())
                        }
                    }
                })

            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(DatabaseBuilder.getInstance(applicationContext))
        ).get(MainActivityViewModel::class.java)
    }
}