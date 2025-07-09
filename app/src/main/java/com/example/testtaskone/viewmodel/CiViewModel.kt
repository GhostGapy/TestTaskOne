package com.example.testtaskone.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapppart1.JobItem
import com.example.testtaskone.api.ApiClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CiViewModel : ViewModel() {

    val jobList = MutableLiveData<List<JobItem>>() // seznam jobov
    val alarmTrigger = MutableLiveData<Unit>() //sprozi alarm
    val newlyRedJobs = MutableLiveData<Set<String>>() //seznam novih rdecih jobov
    var testToggle = false
    private var previousJobColors: Map<String, String> = emptyMap() //mapiramo prejsnje barve jobov
    val title = MutableLiveData<String>()


    init {
        //zacnemo s pollingom takoj ko se ViewModel ustvari
        startPolling()
    }

    private fun startPolling() {
        viewModelScope.launch {
            while (true) {
                try {
                    //poslemo request
                    val result = ApiClient.service.getJobs()

                    //to je tesni primer za testiranje delovanja rdecih polj
                    /*val result = JobResponse(
                        name = "Jobs Test",
                        jobs = listOf(
                            JobItem(name = "Job1", url = "http://example.com/job1", color = if (testToggle) "red" else "blue"),
                            JobItem(name = "Job2", url = "http://example.com/job2", color = if (testToggle) "blue" else "green")
                        )
                    )
                    testToggle = !testToggle*/


                    title.postValue(result.name)

                    val currentJobs = result.jobs

                    val currentColors = currentJobs.associate { it.name to it.color }

                    //poiscemo ce je kaksen NEW rdec job
                    val newRedJobs = currentJobs.filter { job ->
                        val previousColor = previousJobColors[job.name]
                        job.color.contains("red", ignoreCase = true) && previousColor != null && !previousColor.contains("red", ignoreCase = true)
                    }.map { it.name }.toSet()

                    //ce so nasli kaksen rdec job, sprozimo alarm
                    if (newRedJobs.isNotEmpty()) {
                        Log.d("TEST", "Rdeči job zaznan: $newRedJobs")
                        alarmTrigger.postValue(Unit)
                        newlyRedJobs.postValue(newRedJobs)
                    }

                    //posodobimo seznam za naslednje preverjanje
                    previousJobColors = currentColors
                    jobList.postValue(currentJobs)

                } catch (e: Exception) {
                    Log.e("TEST", "Napaka: ${e.message}")
                }

                delay(30_000)
            }
        }
    }
}