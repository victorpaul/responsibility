package com.sukinsan.responsibility

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
import com.sukinsan.responsibility.services.newWorkerManagerService
import com.sukinsan.responsibility.utils.newStorageUtils
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

        val storageUt = newStorageUtils(this)
        val workerSv = newWorkerManagerService(this, storageUt)

        val task = TaskEntity(
            "task id", 1, RemindRuleEnum.HORLY,
            "Drink water", Date(), null, null
        )
        val task2 = TaskEntity(
            "task id2", 2, RemindRuleEnum.HORLY,
            "Beber agua", Date(), null, null
        )

        // todo, workmanagerService
//

        workerSv.runRecurring(task)
        workerSv.runRecurring(task2)

    }
}
