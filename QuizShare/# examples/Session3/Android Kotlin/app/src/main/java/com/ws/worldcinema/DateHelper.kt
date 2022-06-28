package com.ws.worldcinema

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    //TODO: пригодится для реализации чата
    fun getDateForChat(dateString: String): String {
        try {
            val date = SimpleDateFormat("yyyy-MM-dd").parse(dateString)
            val today = Date()
            val cal1 = Calendar.getInstance()
            val cal2 = Calendar.getInstance()
            cal1.time = date
            cal2.time = today
            val sameDay = cal1[Calendar.DAY_OF_YEAR] == cal2[Calendar.DAY_OF_YEAR] &&
                    cal1[Calendar.YEAR] == cal2[Calendar.YEAR]
            if (sameDay) return "Сегодня"
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateString
    }
}