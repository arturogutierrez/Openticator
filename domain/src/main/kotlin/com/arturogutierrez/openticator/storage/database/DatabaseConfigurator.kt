package com.arturogutierrez.openticator.storage.database

interface DatabaseConfigurator {
  fun configure(passwordInBase64: String)
}
