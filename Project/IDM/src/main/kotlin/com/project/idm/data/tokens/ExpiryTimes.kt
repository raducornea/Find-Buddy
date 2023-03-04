package com.project.idm.data.tokens

enum class ExpiryTimes(val seconds: Long) {
    ONE_HOUR(60 * 60),
    ONE_DAY(24 * 60 * 60),
    SEVEN_DAYS(7 * 24 * 60 * 60)
}