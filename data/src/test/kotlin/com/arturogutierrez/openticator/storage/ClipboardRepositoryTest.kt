package com.arturogutierrez.openticator.storage

import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import com.arturogutierrez.openticator.RobolectricTestCase
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.robolectric.RuntimeEnvironment

class ClipboardRepositoryTest : RobolectricTestCase() {

  private lateinit var clipboardRepository: ClipboardRepositoryImpl
  private lateinit var clipboardManager: ClipboardManager

  companion object {
    val fakeLabel = "LABEL"
    val fakeText = "TEXT"
  }

  @Before
  fun setUp() {
    val context = RuntimeEnvironment.application
    clipboardRepository = ClipboardRepositoryImpl(context)
    clipboardManager = RuntimeEnvironment.application.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
  }

  @Test
  fun testTextIsCopiedToClipboard() {
    clipboardRepository.copy(fakeLabel, fakeText)

    val isPlainMimeType = clipboardManager.primaryClipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
    val label = clipboardManager.primaryClipDescription.label.toString()
    val clipData = clipboardManager.primaryClip.getItemAt(0)
    val text = clipData.text.toString()

    assertThat(isPlainMimeType, `is`(true))
    assertThat(label, `is`(fakeLabel))
    assertThat(text, `is`(fakeText))
  }
}
