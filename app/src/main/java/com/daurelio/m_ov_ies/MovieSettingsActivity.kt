package com.daurelio.m_ov_ies

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.daurelio.m_ov_ies.databinding.SettingsBinding

class MovieSettingsActivity : AppCompatActivity() {

    private lateinit var binding: SettingsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        writeSettingsToUI()
        val settings: SharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)

        binding.btnSaveSettings.setOnClickListener {
            writeSettingsIntoLocalStorage()
            if (checkSettings(settings)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "Please select a country and at least 1 service",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onBackPressed() {
        val settings: SharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)
        if (checkSettings(settings)) {
            super.onBackPressed()
        } else {
            Toast.makeText(
                this,
                "Please save your selection",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun checkSettings(settings: SharedPreferences): Boolean {

        val radioSettings = returnRadio(settings)
        val checkboxSettings = returnCheckboxes(settings)


        if (!radioSettings.containsValue(true)) {
            return false
        }

        if (!checkboxSettings.containsValue(true)) {
            return false
        }

        return true
    }

    private fun returnCheckboxes(settings: SharedPreferences): MutableMap<String, Boolean> {
        val checkboxSettings =
            mutableMapOf("checkboxNetflix" to settings.getBoolean("checkboxNetflix", false))

        checkboxSettings["checkboxPrime"] = settings.getBoolean("checkboxPrime", false)
        checkboxSettings["checkboxDisney"] = settings.getBoolean("checkboxDisney", false)
        checkboxSettings["checkboxHBO"] = settings.getBoolean("checkboxHBO", false)
        checkboxSettings["checkboxHulu"] = settings.getBoolean("checkboxHulu", false)
        checkboxSettings["checkboxPeacock"] = settings.getBoolean("checkboxPeacock", false)
        checkboxSettings["checkboxParamount"] = settings.getBoolean("checkboxParamount", false)
        checkboxSettings["checkboxStarz"] = settings.getBoolean("checkboxStarz", false)
        checkboxSettings["checkboxShowtime"] = settings.getBoolean("checkboxShowtime", false)
        checkboxSettings["checkboxApple"] = settings.getBoolean("checkboxApple", false)
        checkboxSettings["checkboxMubi"] = settings.getBoolean("checkboxMubi", false)

        return checkboxSettings
    }

    private fun returnRadio(settings: SharedPreferences): MutableMap<String, Boolean> {

        val radioSettings =
            mutableMapOf("radioButtonGermany" to settings.getBoolean("radioButtonGermany", false))
        radioSettings["radioButtonUSA"] = settings.getBoolean("radioButtonUSA", false)


        return radioSettings
    }

    private fun writeSettingsIntoLocalStorage() {
        val settings = getSharedPreferences("Settings", MODE_PRIVATE)

        with(settings.edit()) {
            putBoolean("radioButtonGermany", binding.radioGermany.isChecked)
            putBoolean("radioButtonUSA", binding.radioUsa.isChecked)
            putBoolean("checkboxNetflix", binding.checkboxNetflix.isChecked)
            putBoolean("checkboxPrime", binding.checkboxPrime.isChecked)
            putBoolean("checkboxDisney", binding.checkboxDisney.isChecked)
            putBoolean("checkboxHBO", binding.checkboxHbo.isChecked)
            putBoolean("checkboxHulu", binding.checkboxHulu.isChecked)
            putBoolean("checkboxPeacock", binding.checkboxPeacock.isChecked)
            putBoolean("checkboxParamount", binding.checkboxParamount.isChecked)
            putBoolean("checkboxStarz", binding.checkboxStarz.isChecked)
            putBoolean("checkboxShowtime", binding.checkboxShowtime.isChecked)
            putBoolean("checkboxApple", binding.checkboxApple.isChecked)
            putBoolean("checkboxMubi", binding.checkboxMubi.isChecked)
            apply()
        }
    }

    private fun writeSettingsToUI() {
        val settings = getSharedPreferences("Settings", MODE_PRIVATE)

        binding.radioGermany.isChecked = settings.getBoolean("radioButtonGermany", false)
        binding.radioUsa.isChecked = settings.getBoolean("radioButtonUSA", false)
        binding.checkboxNetflix.isChecked = settings.getBoolean("checkboxNetflix", false)
        binding.checkboxPrime.isChecked = settings.getBoolean("checkboxPrime", false)
        binding.checkboxDisney.isChecked = settings.getBoolean("checkboxDisney", false)
        binding.checkboxHbo.isChecked = settings.getBoolean("checkboxHbo", false)
        binding.checkboxHulu.isChecked = settings.getBoolean("checkboxHulu", false)
        binding.checkboxPeacock.isChecked = settings.getBoolean("checkboxPeacock", false)
        binding.checkboxParamount.isChecked = settings.getBoolean("checkboxParamount", false)
        binding.checkboxStarz.isChecked = settings.getBoolean("checkboxStarz", false)
        binding.checkboxShowtime.isChecked = settings.getBoolean("checkboxShowtime", false)
        binding.checkboxApple.isChecked = settings.getBoolean("checkboxApple", false)
        binding.checkboxMubi.isChecked = settings.getBoolean("checkboxMubi", false)


    }
}