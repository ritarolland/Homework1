package com.example.homework1

import com.example.homework1.data.LastKnownRevisionRepository
import okhttp3.Interceptor
import okhttp3.Response

class LastKnownRevisionInterceptor(
    private val lastKnownRevisionRepository: LastKnownRevisionRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader(
            "X-Last-Known-Revision",
            lastKnownRevisionRepository.lastKnownRevision.toString()
        )
        return chain.proceed(requestBuilder.build())
    }
}