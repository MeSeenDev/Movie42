package ru.meseen.dev.androidacademy.fragments.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import ru.meseen.dev.androidacademy.R
import ru.meseen.dev.androidacademy.support.FragmentsTags

class ActivitySettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val toolbar = findViewById<Toolbar>(R.id.settings_toolbar)
        toolbar.title = getString(R.string.settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val actionBar = this.supportActionBar

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.settings_base_container,
                    MoviePrefsFragment(),
                    FragmentsTags.SETTINGS_FRAG_TAG.toString()
                ).commit()
        }
    }
}