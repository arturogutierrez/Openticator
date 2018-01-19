package com.arturogutierrez.openticator

import com.arturogutierrez.openticator.data.BuildConfig
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, application = ApplicationStub::class, manifest = Config.NONE, sdk = [23])
abstract class RobolectricTestCase
