package com.example.cognizantdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.cognizantdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val libuLiveData get() = this@MainActivity.isNetworkAvailable().asLiveData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        libuLiveData.observe(this) {
            val isAvailable = if (it) "Network is Available" else "NOT AVAILABLE"
            binding.txtSummary.text = isAvailable
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}