package com.sukinsan.responsibility.adapters;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sukinsan.responsibility.R
import com.sukinsan.responsibility.entities.TaskContainerEntity

class TaskAdapter(var data: List<TaskContainerEntity>) :
    RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    class MyViewHolder(itemRoot: View) : RecyclerView.ViewHolder(itemRoot) {
        val textView = itemView.findViewById<TextView>(R.id.txt_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.adapter_task, parent, false)

        return MyViewHolder(itemLayout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.setText(data.get(position).task.description)
    }
}