package com.example.roomdatabaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.roomdatabaseexample.database.ConactDatabase
import com.example.roomdatabaseexample.model.Contact
import kotlinx.android.synthetic.main.activity_add_record.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AddRecordActivity : AppCompatActivity() {
    lateinit var database: ConactDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_record)

        database = ConactDatabase.getDatabaseInstance(this)


        submit.setOnClickListener {
            if(validateInput()){
                addContact(inputName.text.toString(), inputMobile.text.toString(), inputGender.text.toString())
            }else{
                Toast.makeText( this@AddRecordActivity,"Please enter proper details", Toast.LENGTH_SHORT).show()
            }
        }
    }




    private fun validateInput():Boolean {
        //basic validation only
        return !(inputName.text.toString() == "" || inputGender.text.toString() == "" || inputMobile.text.toString() == "")
    }

    private fun addContact(name: String, mobile: String, gender: String){
        GlobalScope.launch(Dispatchers.IO) {
            database.getContactDao().insertContact(Contact(0, name, mobile, Date(), gender ))

            withContext(Dispatchers.Main){
                finish()
            }
        }
    }

    }