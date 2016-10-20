package com.arturogutierrez.openticator.domain.account.add.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.domain.account.add.AddAccountFromCameraPresenter
import com.arturogutierrez.openticator.domain.account.add.AddAccountFromCameraView
import com.arturogutierrez.openticator.domain.account.add.di.AddAccountComponent
import com.arturogutierrez.openticator.domain.navigator.Navigator
import com.arturogutierrez.openticator.view.fragment.BaseFragment
import org.jetbrains.anko.support.v4.find
import javax.inject.Inject

class AddAccountFromCameraFragment : BaseFragment(), AddAccountFromCameraView {

  private companion object {
    private val REQUEST_CODE = 0x10
  }

  @Inject
  internal lateinit var presenter: AddAccountFromCameraPresenter

  @Inject
  internal lateinit var navigator: Navigator
  private val pbLoading by lazy { find<View>(R.id.pb_loading) }

  private val llScanQR by lazy { find<View>(R.id.ll_scan_qr) }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    initialize()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode != REQUEST_CODE) {
      super.onActivityResult(requestCode, resultCode, data)
      return
    }

    presenter.onScannedQR(data)
  }

  override fun onResume() {
    super.onResume()
    presenter.resume()
  }

  override fun onPause() {
    super.onPause()
    presenter.pause()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.destroy()
  }

  override val layoutResource: Int
    get() = R.layout.fragment_account_add_from_camera

  private fun initialize() {
    initializeInjector()
    presenter.setView(this)
    llScanQR.setOnClickListener { presenter.onScanQR() }
  }

  private fun initializeInjector() {
    getComponent(AddAccountComponent::class.java).inject(this)
  }

  override fun showLoading() {
    pbLoading.visibility = View.VISIBLE
    llScanQR.visibility = View.GONE
  }

  override fun hideLoading() {
    pbLoading.visibility = View.GONE
    llScanQR.visibility = View.VISIBLE
  }

  override fun openCaptureCode() {
    navigator.goToCaptureCode(activity, this, REQUEST_CODE)
  }

  override fun dismissScreen() {
    val activity = activity
    activity?.finish()
  }

  override fun showQRError() {
    Toast.makeText(context, R.string.unable_to_decode_qr, Toast.LENGTH_SHORT).show()
  }
}
