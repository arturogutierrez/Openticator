package com.arturogutierrez.openticator.domain.account.add

interface AddAccountFromCameraView {

  fun showLoading()

  fun hideLoading()

  fun openCaptureCode()

  fun dismissScreen()

  fun showQRError()
}
