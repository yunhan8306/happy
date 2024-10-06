package com.example.happy.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.io.IOException

class CustomLoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // 요청 로그 출력
        val requestLog = String.format("Sending request %s on %s%n%s",
            request.url, chain.connection(), request.headers)
        Log.d("CustomLog", "Request:\n$requestLog")

        // 요청 바디 출력 (필요할 경우)
        val requestBody = request.body?.let { bodyToString(it) }
        requestBody?.let {
            Log.d("CustomLog", "Request Body:\n$it")
        }

        // 응답 처리
        val response = chain.proceed(request)
        val responseBody = response.body?.string() ?: "No Response Body"

        // 응답 로그 출력
        val responseLog = String.format("Received response for %s in %.1fms%n%s",
            response.request.url, (System.nanoTime() - System.nanoTime()) / 1e6, response.headers)
        Log.d("CustomLog", "Response:\n$responseLog\n$responseBody")

        // 응답 바디 재생성
        return response.newBuilder()
            .body(responseBody.toResponseBody(response.body?.contentType()))
            .build()
    }

    // 요청 바디를 문자열로 변환하는 함수
    private fun bodyToString(requestBody: RequestBody): String {
        return try {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "Error converting body to string"
        }
    }
}
