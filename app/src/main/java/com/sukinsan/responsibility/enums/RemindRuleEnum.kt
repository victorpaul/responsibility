package com.sukinsan.responsibility.enums

enum class RemindRuleEnum {
    HOURLY,
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY,
    TODO;

    fun getNotificationId(): Int {
        return values().indexOf(this)
    }
}