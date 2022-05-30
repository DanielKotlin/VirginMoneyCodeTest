package com.daniel.codetest

import com.daniel.codetest.enums.Environment
import com.daniel.codetest.utils.CommonUtils

/**
 * Singleton container for application constants and globals
 */
object Globals {

    // Internal project name
    const val APPLICATION_NAME = "CodeTest"

    // Base URLs by Environment
    private const val BASE_URL_PROD = "https://gorest.co.in/public/v2/"
    private const val BASE_URL_QA = "https://gorest.co.in/public/v2/"
    private const val BASE_URL_DEV = "https://gorest.co.in/public/v2/"

    val BASE_URL = when (CommonUtils.environment) {
        Environment.PRODUCTION -> BASE_URL_PROD
        Environment.QA -> BASE_URL_QA
        Environment.DEVELOPMENT -> BASE_URL_DEV
    }
}
