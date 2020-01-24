package com.sukinsan.responsibility.enums

enum class RemindRuleEnum {
    SOFT_REMIND,
    REQUIRE_ACTION;

    fun getNotificationId(): Int {
        return values().indexOf(this)
    }
}