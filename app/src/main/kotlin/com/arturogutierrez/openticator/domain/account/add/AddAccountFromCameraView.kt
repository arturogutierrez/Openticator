package com.arturogutierrez.openticator.domain.account.add

import com.arturogutierrez.openticator.view.LoadDataView

interface AddAccountFromCameraView : LoadDataView {

  fun openCaptureCode()

  fun dismissScreen()

  fun showQRError()
}
