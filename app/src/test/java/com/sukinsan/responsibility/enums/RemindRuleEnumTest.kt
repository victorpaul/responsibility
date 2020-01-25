package com.sukinsan.responsibility.enums

import org.junit.Assert.assertEquals
import org.junit.Test

class RemindRuleEnumTest {

    @Test
    fun succes_get_notification_id_according_to_reminder_rule() {
        assertEquals(0, RemindRuleEnum.WEEKLY_DAYS.getNotificationId())
        assertEquals(1, RemindRuleEnum.MONTHLY_DAYS.getNotificationId())
    }
}