package com.asusoft.roomdatabaseexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asusoft.roomdatabaseexample.adapter.ContactAdapter
import com.asusoft.roomdatabaseexample.room.Contact
import com.asusoft.roomdatabaseexample.viewmodel.ContactViewModel
import com.asusoft.roomdatabaseexample.viewmodel.ContactViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val contactViewModel: ContactViewModel by viewModels {
        ContactViewModelFactory((application as ContactApplication).repository)
    }

    private lateinit var getResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview = findViewById<RecyclerView>(R.id.contact_recyclerview)
        val adapter = ContactAdapter()
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        contactViewModel.contacts.observe(this) { contacts ->
            contacts.let { adapter.submitList(it) }
        }

        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val name = it.data?.getStringExtra("name").toString()
                val number = it.data?.getStringExtra("number").toString()

                val contact = Contact.create(name, number)
                contactViewModel.insert(contact)
            } else {
                Toast.makeText(applicationContext, "empty not saved", Toast.LENGTH_SHORT).show()
            }
        }

        val addButton = findViewById<FloatingActionButton>(R.id.add_button)
        addButton.setOnClickListener {
            val intent = Intent(this, NewContactActivity::class.java)
            getResult.launch(intent)
        }
    }
}