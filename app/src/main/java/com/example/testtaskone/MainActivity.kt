package com.example.testtaskone

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskone.viewmodel.CiViewModel
import com.example.testapppart1.JobAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val titleTextView = findViewById<TextView>(R.id.titleTextView)

        // init viewModel z povezavo na lifecycle
        viewModel = ViewModelProvider(this)[CiViewModel::class.java]

        /*viewModel.jobList.observe(this) { jobs ->
            jobs.forEach {
                Log.d("TEST", "${it.name}: ${it.color}")
            }
        }*/

        // predvaja zvok ce zazna spremembo
        viewModel.alarmTrigger.observe(this) {
            val mediaPlayer = MediaPlayer.create(this, R.raw.doh1_y)
            mediaPlayer.start()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.jobRecyclerView)
        val adapter = JobAdapter(emptyList())
        recyclerView.adapter = adapter


        // cakamo da observer zazna spremembe v seznamu delovnih mest
        viewModel.jobList.observe(this) { jobs ->
            adapter.updateJobs(jobs)
        }

        // preberemo naslov, tudi ce se slucajno spremeni
        viewModel.title.observe(this) { title ->
            titleTextView.text = title
        }

        // Cakamo na spremembe v seznamu rdecih delovnih mest
        viewModel.newlyRedJobs.observe(this) { redJobs ->
            adapter.updateNewlyRedJobs(redJobs)
        }
    }
}