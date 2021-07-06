package com.example.roomdatabaseexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabaseexample.model.Contact
import com.example.roomdatabaseexample.R
import java.text.SimpleDateFormat
import java.util.*

class ContactsAdapter(private val listener:IContactAdapter) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
        val gender = itemView.findViewById<TextView>(R.id.gender)
        val mobile = itemView.findViewById<TextView>(R.id.mobile)
        val date = itemView.findViewById<TextView>(R.id.date)
        val delete = itemView.findViewById<ImageView>(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val contactViewHolder =  ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        )
        contactViewHolder.delete.setOnClickListener {
            listener.onItemClicked(contactList[contactViewHolder.adapterPosition])
        }
        return contactViewHolder
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.name.text = contact.name
        holder.gender.text = "Gender: "+ contact.gender
        holder.mobile.text = contact.mobile
        holder.date.text = formatDate(contact.date)

    }

    override fun getItemCount() = contactList.size


    private fun formatDate(date: Date): String {
        return SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date)
    }

    //updating List
    private val differCallback = object : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.name == newItem.name
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)
    var contactList: List<Contact>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }


}

interface IContactAdapter {
    fun onItemClicked(contact: Contact)

}