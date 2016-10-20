package com.arturogutierrez.openticator.view.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.application.OpenticatorApplication
import com.arturogutierrez.openticator.di.component.ApplicationComponent
import com.arturogutierrez.openticator.di.module.ActivityModule
import com.arturogutierrez.openticator.domain.navigator.Navigator
import org.jetbrains.anko.findOptional
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

  protected val toolbar by lazy { findOptional<Toolbar>(R.id.toolbar) }

  @Inject
  lateinit var navigator: Navigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(layoutResource)

    applicationComponent.inject(this)

    configureToolbar()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      finish()
      return true
    }

    return super.onOptionsItemSelected(item)
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
