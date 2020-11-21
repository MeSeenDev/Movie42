package ru.meseen.dev.androidacademy

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import ru.meseen.dev.androidacademy.adapter.MassiveAdapter
import ru.meseen.dev.androidacademy.data.DataKeys
import ru.meseen.dev.androidacademy.data.MovieData

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var navBack: MaterialTextView
    private lateinit var toolBarImage: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        val data: MovieData = intent.extras?.get(DataKeys.DATA_KEY.toString()) as MovieData
        navBack = findViewById(R.id.home)
        navBack.setOnClickListener {
            onBackPressed()
            Toast.makeText(this, "WORK", Toast.LENGTH_SHORT).show()
        }
        toolBarImage = findViewById(R.id.app_bar_image)
        val recyclerView = findViewById<RecyclerView>(R.id.detailRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = MassiveAdapter(listOf(data), null, application, DataKeys.DETAILS_TYPE)
        recyclerView.adapter = adapter

    }
}