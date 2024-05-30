package com.example.lifesharing.util

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class DateUtils {
    companion object {

        fun formatChatTimestamp(timestamp: String): String {
            if (timestamp == null) return ""
            try {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
                val dateTime = LocalDateTime.parse(timestamp, formatter)  // 서버에서 넘겨 온 시간 데이터 파싱
                val now = LocalDateTime.now()   // 현재 시간을 가져옴

                val timeFormatter = DateTimeFormatter.ofPattern("hh:mm")   //"hh:mm" 형식(12시간제)으로 시간을 포맷
                val timeString = dateTime.format(timeFormatter)
                val amPmString = if (dateTime.hour < 12) "오전" else "오후"   // dateTime.hour는 24시간 형식의 시간을 반환

                // 반환 조건
                return when {
                    dateTime.toLocalDate().isEqual(now.toLocalDate()) -> "$amPmString $timeString"
                    dateTime.toLocalDate().isEqual(now.toLocalDate().minusDays(1)) -> "어제"
                    else -> dateTime.format(DateTimeFormatter.ofPattern("MM-dd"))
                }
            }
            // 시간 파싱이나 포맷팅 과정에서 예외가 발생할 경우
            catch (e: Exception) {
                e.printStackTrace()
                return "시간 오류"
            }
        }
    }
}