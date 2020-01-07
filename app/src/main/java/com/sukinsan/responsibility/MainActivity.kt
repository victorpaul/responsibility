package com.sukinsan.responsibility

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sukinsan.responsibility.entities.TaskEntity
import com.sukinsan.responsibility.enums.RemindRuleEnum
import com.sukinsan.responsibility.services.newReminderService
import com.sukinsan.responsibility.utils.newStorageUtils
import com.sukinsan.responsibility.utils.newTU
import java.util.*

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

        val tu = newTU()
        val storageUt = newStorageUtils(this, tu)
        val workerSv = newReminderService(this, storageUt)

        val task = TaskEntity(
            "task id", RemindRuleEnum.HOURLY,
            "Drink water", Date(), null, null
        )
        val task2 = TaskEntity(
            "task id2", RemindRuleEnum.HOURLY,
            "Beber agua", Date(), null, null
        )
        val task3 = TaskEntity(
            "task id3", RemindRuleEnum.HOURLY,
            "Eat healthy food", Date(), null, null
        )
        val task4 = TaskEntity(
            "task id4", RemindRuleEnum.HOURLY,
            "Do sport", Date(), null, null
        )

        workerSv.runRecurring(task)
        workerSv.runRecurring(task2)
        workerSv.runRecurring(task3)
        workerSv.runRecurring(task4)

    }
}
