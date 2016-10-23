package com.arturogutierrez.openticator.view

import com.arturogutierrez.openticator.view.presenter.Presenter

interface LoadDataView : Presenter.View {

  fun showLoading()

  fun hideLoading()
}
