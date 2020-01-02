package com.sukinsan.responsibility

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.enums.RemindRuleEnum
import com.sukinsan.responsibility.workmanagers.ReminderWorker
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        val task = TaskEntity(
            "task id", RemindRuleEnum.DAILY,
            "Drink water", Date(), null, null
        )

        // todo, SaveUtils to save data
        val sharedPref = getSharedPreferences("PairTasksIdJson", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(task.id, task.toJson())
            commit()
        }

        // todo, workmanagerService
//        val remindHelloOnce = OneTimeWorkRequestBuilder<ReminderWorker>()
//            .setInputData(workDataOf(Pair("taskId", task.id)))
//            .setInitialDelay(3,TimeUnit.SECONDS)
//            .build()
//        WorkManager.getInstance(this).enqueue(remindHelloOnce)

        val cronJob =
            PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.HOURS)
                .setInputData(workDataOf(Pair("taskId", task.id)))
                .setInitialDelay(5,TimeUnit.SECONDS)
                .build()

        WorkManager.getInstance(this).enqueue(cronJob)



    }
}
