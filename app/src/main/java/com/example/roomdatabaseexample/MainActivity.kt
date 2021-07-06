package com.example.roomdatabaseexample


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabaseexample.adapter.ContactsAdapter
import com.example.roomdatabaseexample.adapter.IContactAdapter
import com.example.roomdatabaseexample.database.ConactDatabase
import com.example.roomdatabaseexample.model.Contact

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity(), IContactAdapter {
    lateinit var database: ConactDatabase
    lateinit var contactsAdapter: ContactsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = ConactDatabase.getDatabaseInstance(this)
        setRV()

        database.getContactDao().getContacts()
            .observe(this, androidx.lifecycle.Observer { response ->
                contactsAdapter.contactList = response

            })
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddRecordActivity::class.java)
            startActivity(intent)
        }


    }

    private fun setRV() {
        contactsAdapter = ContactsAdapter(this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = contactsAdapter
        }
    }

    override fun onItemClicked(contact: Contact) {
        val builder = AlertDialog.Builder(this@MainActivity)
            .setTitle("Confirm Dialog")
            .setMessage("Do you really want to delete the selected contact?")
            .setCancelable(false)
            .setPositiveButton("Delete") { dialogInterface, which ->
                GlobalScope.launch {
                    database.getContactDao().deleteContact(contact)
                }
            }
            .setNegativeButton("Cancel") { dialogInterface, which ->

            }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()

    }
}