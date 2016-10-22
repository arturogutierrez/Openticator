package com.arturogutierrez.openticator.view.presenter

interface Presenter<V : Presenter.View> {

  var view: V

  fun resume() {

  }

  fun pause() {

  }

  fun destroy() {

  }

  interface View {

  }
}
