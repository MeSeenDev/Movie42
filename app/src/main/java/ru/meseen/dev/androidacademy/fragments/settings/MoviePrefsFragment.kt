package ru.meseen.dev.androidacademy.fragments.settings

import android.os.Bundle
import androidx.preference.*
import ru.meseen.dev.androidacademy.R

class MoviePrefsFragment : PreferenceFragmentCompat()  {

    companion object{
        const val TAG = "MoviePrefs"
    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_prefs, rootKey)
    }



}