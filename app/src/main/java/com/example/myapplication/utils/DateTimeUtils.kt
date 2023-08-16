package com.example.myapplication.utils

import timber.log.Timber
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {

    companion object {

        @Throws(ParseException::class)
        private fun convertStringToDate(dateString: String): Date {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            return formatter.parse(dateString)
        }

        fun dateToString(dateString: String): String? {
            return try {
                val date = convertStringToDate(dateString)
                val dateFormat = DateFormat.getDateTimeInstance(
                    DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault()
                )
                dateFormat.format(date)
            } catch (e: ParseException) {
                Timber.w(e)
                ""
            }
        }

        fun getTomorrowsDate(): Date? {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, +1)
            return cal.time
        }

        fun formatApiDateTime(date: Date?): String {
            val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return dateTimeFormat.format(date)
        }

        @Throws(ParseException::class)
        fun formatStringToDate(dateString: String?): Date? {
            val simpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            //simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
            return simpleDateFormat.parse(dateString)
        }

        @Throws(ParseException::class)
        fun formatLongToDate(date: Long?): Date? {
            val simpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val dateString = simpleDateFormat.format(date)

            return simpleDateFormat.parse(dateString)
        }

        @Throws(ParseException::class)
        fun formatReadableStringToDate(dateString: String?): Date? {
            val simpleDateFormat =
                SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
            return simpleDateFormat.parse(dateString)
        }

        fun getCurrentDate(): String {
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val date = Date()
            return formatter.format(date)
        }

        fun getDayOfWeek(date: Date?): String {
            val sdf = SimpleDateFormat("EEEE")
            val d = date
            return sdf.format(d)
        }
    }
}