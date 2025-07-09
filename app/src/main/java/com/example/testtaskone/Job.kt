package com.example.testapppart1

data class JobResponse(
    val name: String,
    val jobs: List<JobItem>
)

data class JobItem(
    val name: String,
    val url: String,
    val color: String
)
