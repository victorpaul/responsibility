package com.sukinsan.responsibility.enums

enum class RemindRuleEnum {
    WEEKLY_DAYS,
    MONTHLY_DAYS,
    EXACT_DAY;

    fun getNotificationId(): Int {
        return values().indexOf(this)
    }
}