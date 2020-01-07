package com.sukinsan.responsibility.enums

import org.junit.Assert.assertEquals
import org.junit.Test

class RemindRuleEnumTest {

    @Test
    fun succes_get_notification_id_according_to_reminder_rule() {
        assertEquals(0, RemindRuleEnum.HOURLY.getNotificationId())
        assertEquals(1, RemindRuleEnum.DAILY.getNotificationId())
        assertEquals(2, RemindRuleEnum.WEEKLY.getNotificationId())
        assertEquals(3, RemindRuleEnum.MONTHLY.getNotificationId())
        assertEquals(4, RemindRuleEnum.YEARLY.getNotificationId())
        assertEquals(5, RemindRuleEnum.TODO.getNotificationId())

    }
}