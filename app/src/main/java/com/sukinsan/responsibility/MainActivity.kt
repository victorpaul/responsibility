package com.sukinsan.responsibility

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.entities.createEveryHourDaily
import com.sukinsan.responsibility.entities.createEveryHourWeekly
import com.sukinsan.responsibility.enums.RemindRuleEnum
import com.sukinsan.responsibility.providers.newSharedPrefDB
import com.sukinsan.responsibility.services.newNotificationService
import com.sukinsan.responsibility.services.newReminderService

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

        val storageUt = newSharedPrefDB(this)
        val workerSv = newReminderService(this, storageUt)
        val notfService = newNotificationService(this)

        notfService.registerChannel()

        val task = TaskEntity(
            "task id",
            RemindRuleEnum.WEEKLY_DAYS,
            listOf(8, 10, 12, 14, 16, 17, 20, 22),
            listOf(1, 2, 3, 4, 5, 6, 7),
            emptyList(),
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
            null,
            "Drink water", null, mutableListOf(), mutableListOf()
        )
        val task3 = TaskEntity(
            "task id3",
            RemindRuleEnum.WEEKLY_DAYS,
            listOf(10, 12, 15, 17, 19, 21, 23),
            listOf(1, 2, 3, 4, 5, 6, 7),
            emptyList(),
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
            null,
            "Eat healthy food", null, mutableListOf(), mutableListOf()
        )
        val task5 = TaskEntity(
            "task id5",
            RemindRuleEnum.WEEKLY_DAYS,
            listOf(8, 10, 12, 14, 16, 17, 20, 22),
            listOf(1, 2, 3, 4, 5, 6, 7),
            emptyList(),
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
            null,
            "Age is just a number", null, mutableListOf(), mutableListOf()
        )

        storageUt.write {
            it.save(task)
            it.save(task3)
            it.save(task5)
            return@write true
        }

//        workerSv.runRecurringWorker(task) // todo, think about this worker
//        workerSv.runRecurringWorker(task3)
//        workerSv.runRecurringWorker(task4)

        workerSv.runRecurringAlarm()
    }
}
