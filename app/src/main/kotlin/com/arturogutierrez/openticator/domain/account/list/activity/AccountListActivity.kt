package com.arturogutierrez.openticator.domain.account.list.activity

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.di.HasComponent
import com.arturogutierrez.openticator.domain.account.list.di.AccountListComponent
import com.arturogutierrez.openticator.domain.account.list.di.AccountListModule
import com.arturogutierrez.openticator.domain.account.list.di.DaggerAccountListComponent
import com.arturogutierrez.openticator.domain.account.list.view.AccountListFragment
import com.arturogutierrez.openticator.domain.navigator.drawer.NavigationDrawer
import com.arturogutierrez.openticator.view.activity.BaseActivity
import com.github.clans.fab.FloatingActionMenu
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.EmptyPermissionListener
import org.jetbrains.anko.find
import javax.inject.Inject

class AccountListActivity : BaseActivity(), HasComponent<AccountListComponent> {

  @Inject
  lateinit var navigationDrawer: NavigationDrawer
  private val floatingActionMenu by lazy { find<FloatingActionMenu>(R.id.fab_menu) }

  override val component by lazy { buildComponent() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initializeActivity(savedInstanceState)
  }

  override fun onResume() {
    super.onResume()
    navigationDrawer.onResume()
  }

  override fun onPause() {
    navigationDrawer.onPause()
    super.onPause()
  }

  override fun onDestroy() {
    navigationDrawer.onDestroy()
    super.onDestroy()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onBackPressed() {
    if (!navigationDrawer.onBackPressed()) {
      super.onBackPressed()
    }
  }

  override val layoutResource: Int
    get() = R.layout.activity_toolbar_drawer

  private fun initializeActivity(savedInstanceState: Bundle?) {
    configureInjector()
    configureViews()
    navigationDrawer.onCreate(savedInstanceState)

    if (savedInstanceState == null) {
      showAccountListFragment()
    }
  }

  private fun configureInjector() {
    component.inject(this)
  }

  private fun configureViews() {
    find<View>(R.id.fab_add_manually).setOnClickListener { onAddManuallyClicked() }
    find<View>(R.id.fab_add_from_camera).setOnClickListener { onAddFromCamera() }
  }

  private fun onAddManuallyClicked() {
    floatingActionMenu.close(false)
    navigator.goToAddAccountManually(this)
  }

  private fun onAddFromCamera() {
    checkCameraPermission()
  }

  private fun buildComponent(): AccountListComponent {
    return DaggerAccountListComponent.builder()
        .applicationComponent(applicationComponent)
        .activityModule(activityModule)
        .accountListModule(AccountListModule())
        .build()
  }

  private fun showAccountListFragment() {
    val accountListFragment = AccountListFragment()
    addFragment(R.id.content_frame, accountListFragment)
  }

  private fun checkCameraPermission() {
    val permissionListener = object : EmptyPermissionListener() {
      override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        floatingActionMenu.close(false)
        navigator.goToAddAccountFromCamera(this@AccountListActivity)
      }

      override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        showPermissionNotGranted()
      }
    }

    Dexter.checkPermission(permissionListener, Manifest.permission.CAMERA)
  }

  private fun showPermissionNotGranted() {
    AlertDialog.Builder(this)
        .setTitle(R.string.camera_permission)
        .setMessage(R.string.camera_permission_needed_for_scan_qr)
        .setPositiveButton(android.R.string.ok) { dialog, which -> dialog.dismiss() }
        .show()
  }
}
