package com.sukinsan.responsibility

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sukinsan.responsibility.entities.createEveryHourDaily
import com.sukinsan.responsibility.entities.createEveryHourWeekly
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

        val task = createEveryHourWeekly("task id", "Drink water")
        val task3 = createEveryHourWeekly("task id3", "Eat healthy food")
        val task5 = createEveryHourDaily("task id5", "Age is just a number")

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
