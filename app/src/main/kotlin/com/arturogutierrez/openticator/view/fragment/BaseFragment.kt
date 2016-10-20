package com.arturogutierrez.openticator.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arturogutierrez.openticator.di.HasComponent

abstract class BaseFragment : Fragment() {

  protected fun <C> getComponent(componentType: Class<C>): C {
    return componentType.cast((activity as HasComponent<*>).component)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val fragmentView = inflater!!.inflate(layoutResource, container, false)

    configureUI()

    return fragmentView
  }

  protected abstract val layoutResource: Int

  protected open fun configureUI() {
    // Nothing to do here
  }
}
