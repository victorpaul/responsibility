package com.sukinsan.responsibility.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sukinsan.responsibility.R
import com.sukinsan.responsibility.adapters.TaskAdapter
import com.sukinsan.responsibility.entities.TaskContainerEntity
import com.sukinsan.responsibility.providers.newSharedPrefDB
import com.sukinsan.responsibility.utils.newTU

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        context?.let {
            val storageUt = newSharedPrefDB(it)
            val taskContainers =
                storageUt.read().getTasksList().map { t -> TaskContainerEntity(t, false) }
            viewAdapter = TaskAdapter(taskContainers)
        }

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)


        root.findViewById<RecyclerView>(R.id.tasks_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(this@HomeFragment.context, RecyclerView.VERTICAL, false)
            adapter = viewAdapter
        }

        homeViewModel.text.observe(this, Observer {
//            textView.text = it
        })
        return root
    }

}