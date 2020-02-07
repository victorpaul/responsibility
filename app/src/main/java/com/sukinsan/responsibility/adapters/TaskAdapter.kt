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

    class MyViewHolder(val itemRoot: View) : RecyclerView.ViewHolder(itemRoot) {
        val textDescription = itemView.findViewById<TextView>(R.id.txt_description)
        val textRules = itemView.findViewById<TextView>(R.id.txt_rules)
        val viewExpand = itemView.findViewById<View>(R.id.btn_expand)
        val layoutEditmode = itemView.findViewById<View>(R.id.layout_edit_mode)

        fun set(container: TaskContainerEntity) {
            updateView(container)
            itemRoot.setOnClickListener({
                container.editMode = !container.editMode
                updateView(container)
            })
        }

        fun updateView(container: TaskContainerEntity) {
            textDescription.setText(container.task.description)
            textRules.setText(container.task.describeAllRules(true))
            when (container.editMode) {
                true -> {
                    viewExpand.background.setLevel(1)
                    layoutEditmode.visibility = View.VISIBLE
                }
                else -> {
                    viewExpand.background.setLevel(0)
                    layoutEditmode.visibility = View.GONE
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_task, parent, false)

        return MyViewHolder(itemLayout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.set(data.get(position))
    }
}