package com.arturogutierrez.openticator.storage

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.arturogutierrez.openticator.storage.clipboard.ClipboardRepository
import javax.inject.Inject

class ClipboardRepositoryImpl @Inject constructor(val context: Context) : ClipboardRepository {

  private val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

  override fun copy(label: String, text: String) {
    val clipData = ClipData.newPlainText(label, text)
    clipboardManager.primaryClip = clipData
  }
}
