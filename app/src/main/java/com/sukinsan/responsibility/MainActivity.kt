package com.sukinsan.responsibility

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sukinsan.responsibility.entities.createEveryHourWeekly
import com.sukinsan.responsibility.providers.newSharedPrefDB
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

        val task = createEveryHourWeekly("task id", "Drink more water")
        val task3 = createEveryHourWeekly("task id3", "Eat healthy food")
        val task4 = createEveryHourWeekly("task id4", "Do sport")

        workerSv.runRecurringWorker(task)
        workerSv.runRecurringWorker(task3)
        workerSv.runRecurringWorker(task4)

        workerSv.runRecurringAlarm()

    }
}
