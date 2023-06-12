package com.asusoft.roomdatabaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import com.asusoft.roomdatabaseexample.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    var live: LiveData<MutableList<User>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        binding.add.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val users = AppDatabase.getUserDao(applicationContext).getLast()
                val count = if (users.isEmpty()) 1 else users[0].uid + 1
                val user = User(count, "$count", "$count")
                AppDatabase.getUserDao(applicationContext).insertAll(user)
            }
        }

        binding.select.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val users = AppDatabase.getUserDao(applicationContext).getAll()

                for (user in users) {
                    Log.i(TAG, user.toString())
                }
            }
        }

        binding.delete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                AppDatabase.getDb(applicationContext).clearAllTables()
            }
        }

        binding.update.setOnClickListener {
            val live = live ?: return@setOnClickListener
            Log.i(TAG, live.value.toString())
            val data = live.value?.get(0) ?: return@setOnClickListener
            Log.i(TAG, data.toString())
            data.firstName = "2"

            CoroutineScope(Dispatchers.IO).launch {
                AppDatabase.getUserDao(applicationContext).insertAll(data)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            live = AppDatabase.getUserDao(applicationContext).getAllLive()

            CoroutineScope(Dispatchers.Main).launch {
                live?.observe(this@MainActivity) {
                    Log.i(TAG, it.toString())
                }
            }
        }
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName ?: "MainActivity"
    }
}