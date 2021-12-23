package com.daurelio.m_ov_ies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.daurelio.m_ov_ies.databinding.SettingsBinding

class MovieSettingsActivity : AppCompatActivity() {

    private lateinit var binding: SettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}