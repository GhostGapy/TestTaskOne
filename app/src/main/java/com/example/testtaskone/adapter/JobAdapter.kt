package com.example.testapppart1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskone.R

class JobAdapter(
    private var jobs: List<JobItem>,
    private var newlyRedJobs: Set<String> = emptySet()
) : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    //to je viewHolder za posamezno vrstico
    class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val jobName: TextView = itemView.findViewById(R.id.jobName)
        val jobColor: TextView = itemView.findViewById(R.id.jobColor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_job, parent, false)
        return JobViewHolder(view)
    }

    // tukaj povezemo podatke iz jobs seznama
    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobs[position]
        holder.jobName.text = job.name
        holder.jobColor.text = job.color

        //ce je job postal rdec se ozadje obarva
        if (newlyRedJobs.contains(job.name)) {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_light)
            )
        } else {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, android.R.color.transparent)
            )
        }
    }

    override fun getItemCount() = jobs.size

    //posodobimo seznam in prikaz
    fun updateJobs(newJobs: List<JobItem>) {
        this.jobs = newJobs
        notifyDataSetChanged()
    }

    //tukaj pa posodobimo seznam novih rdecih jobov
    fun updateNewlyRedJobs(redJobs: Set<String>) {
        this.newlyRedJobs = redJobs
        notifyDataSetChanged()
    }
}
