package com.daniel.codetest

import com.daniel.codetest.enums.Environment
import com.daniel.codetest.utils.CommonUtils

/**
 * Singleton container for application constants and globals
 */
object Globals {

    // Base URLs by Environment
    private const val BASE_URL_PROD = "https://61e947967bc0550017bc61bf.mockapi.io/api/v1/"
    private const val BASE_URL_QA = "https://61e947967bc0550017bc61bf.mockapi.io/api/v1/"
    private const val BASE_URL_DEV = "https://61e947967bc0550017bc61bf.mockapi.io/api/v1/"

    val BASE_URL = when (CommonUtils.environment) {
        Environment.PRODUCTION -> BASE_URL_PROD
        Environment.QA -> BASE_URL_QA
        Environment.DEVELOPMENT -> BASE_URL_DEV
    }
}
