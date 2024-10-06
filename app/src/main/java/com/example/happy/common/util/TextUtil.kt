package com.example.happy.common.util

fun String.isKorean(): Boolean {
    return if(isEmpty()) false else any { it in '\uAC00'..'\uD7AF' }
}

fun String.isEnglish(): Boolean {
    return if(isEmpty()) false else any { it in 'A'..'Z' || it in 'a'..'z' }
}