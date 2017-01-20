package com.arturogutierrez.openticator.domain.welcome.view

import com.arturogutierrez.openticator.view.LoadDataView

interface WelcomeView : LoadDataView {

  fun navigateToNextScreen()

  fun showLoginError()
}
