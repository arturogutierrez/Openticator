package com.arturogutierrez.openticator.view.presenter

abstract class Presenter<V : Presenter.View> {

  protected var view: V? = null

  fun attachView(view: V) {
    this.view = view
  }

  fun detachView() {
    this.view = null
  }

  open fun resume() {

  }

  open fun pause() {

  }

  open fun destroy() {

  }

  interface View {

  }
}