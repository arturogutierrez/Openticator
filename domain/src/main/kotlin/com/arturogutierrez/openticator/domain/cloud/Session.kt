package com.arturogutierrez.openticator.domain.cloud

import com.arturogutierrez.openticator.domain.cloud.Session.Status.ACTIVE
import com.arturogutierrez.openticator.domain.cloud.Session.Status.INACTIVE

data class Session(private val status: Status) {

  companion object {
    val Active = Session(ACTIVE)
    val Invalid = Session(INACTIVE)
  }

  enum class Status {
    ACTIVE, INACTIVE
  }

  val isValid = status != INACTIVE
}
