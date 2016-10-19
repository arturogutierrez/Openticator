package com.arturogutierrez.openticator.domain

interface DatabaseConfigurator {
    fun configure(passwordInBase64: String)
}
