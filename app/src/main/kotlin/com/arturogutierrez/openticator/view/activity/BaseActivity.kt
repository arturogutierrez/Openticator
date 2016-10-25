package com.arturogutierrez.openticator.view.activity

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.application.OpenticatorApplication
import com.arturogutierrez.openticator.di.component.ApplicationComponent
import com.arturogutierrez.openticator.di.module.ActivityModule
import com.arturogutierrez.openticator.domain.navigator.Navigator
import com.arturogutierrez.openticator.helpers.consume
import org.jetbrains.anko.find
import org.jetbrains.anko.findOptional
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

  protected val toolbar by lazy { findOptional<Toolbar>(R.id.toolbar) }
  val coordinatorLayout by lazy { find<CoordinatorLayout>(R.id.coordinator_layout) }

  @Inject
  lateinit var navigator: Navigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(layoutResource)

    applicationComponent.inject(this)

    configureToolbar()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      android.R.id.home -> consume { finish() }
      else -> return super.onOptionsItemSelected(item)
    }
  }

  protected val applicationComponent: ApplicationComponent
    get() = (application as OpenticatorApplication).applicationComponent

  protected val activityModule: ActivityModule
    get() = ActivityModule(this)

  protected abstract val layoutResource: Int

  protected fun addFragment(containerViewId: Int, fragment: Fragment) {
    val fragmentTransaction = this.supportFragmentManager.beginTransaction()
    fragmentTransaction.add(containerViewId, fragment)
    fragmentTransaction.commit()
  }

  private fun configureToolbar() {
    toolbar.let { setSupportActionBar(it) }
  }
}
