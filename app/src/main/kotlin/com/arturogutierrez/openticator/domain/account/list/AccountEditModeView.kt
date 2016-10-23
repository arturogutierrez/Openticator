package com.arturogutierrez.openticator.domain.account.list

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.domain.issuer.IssuerDecorator
import com.arturogutierrez.openticator.view.presenter.Presenter

interface AccountEditModeView : Presenter.View {

  fun dismissActionMode()

  fun showCategoryButton(isVisible: Boolean)

  fun showEditButton(isVisible: Boolean)

  fun showLogoButton(isVisible: Boolean)

  fun showChooseEmptyCategory(account: Account)

  fun showChooseCategory(categories: List<Category>, account: Account)

  fun showChooseLogo(issuers: List<IssuerDecorator>, account: Account)
}
