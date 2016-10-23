package com.arturogutierrez.openticator.storage.clipboard

interface ClipboardRepository {
  fun copy(label: String, text: String)
}
