package com.asusoft.roomdatabaseexample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class NewContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        val nameEditText = findViewById<EditText>(R.id.new_name_edittext)
        val numberEditText = findViewById<EditText>(R.id.new_number_edittext)
        val saveButton = findViewById<Button>(R.id.new_save_button)

        saveButton.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(numberEditText.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name = nameEditText.text.toString()
                val number = numberEditText.text.toString()

                replyIntent.putExtra("name", name)
                replyIntent.putExtra("number", number)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
}