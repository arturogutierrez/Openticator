package com.arturogutierrez.openticator.domain.account.list

import com.arturogutierrez.openticator.domain.account.interactor.DeleteAccountsInteractor
import com.arturogutierrez.openticator.domain.account.interactor.UpdateAccountInteractor
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.category.interactor.AddAccountToCategoryInteractor
import com.arturogutierrez.openticator.domain.category.interactor.AddCategoryInteractor
import com.arturogutierrez.openticator.domain.category.interactor.GetCategoriesInteractor
import com.arturogutierrez.openticator.domain.category.model.Category
import com.arturogutierrez.openticator.domain.issuer.IssuerDecoratorFactory
import com.arturogutierrez.openticator.domain.issuer.interactor.GetIssuersInteractor
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import com.arturogutierrez.openticator.interactor.DefaultSubscriber
import com.arturogutierrez.openticator.view.presenter.Presenter
import javax.inject.Inject

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class AccountEditModePresenter @Inject constructor(
    val updateAccountInteractor: UpdateAccountInteractor,
    val deleteAccountsInteractor: DeleteAccountsInteractor,
    val getCategoriesInteractor: GetCategoriesInteractor,
    val addCategoryInteractor: AddCategoryInteractor,
    val addAccountToCategoryInteractor: AddAccountToCategoryInteractor,
    val getIssuersInteractor: GetIssuersInteractor,
    val issuerDecoratorFactory: IssuerDecoratorFactory) : Presenter<AccountEditModeView>() {

  override fun destroy() {
    deleteAccountsInteractor.unsubscribe()
  }

  fun deleteAccounts(selectedAccounts: List<Account>) {
    val params = DeleteAccountsInteractor.Params(selectedAccounts)
    deleteAccountsInteractor.execute(params, DeleteAccountsSubscriber())
  }

  fun onSelectedAccounts(selectedAccounts: List<Account>) {
    if (selectedAccounts.isEmpty()) {
      view?.dismissActionMode()
      return
    }

    view?.apply {
      showCategoryButton(selectedAccounts.size == 1)
      showEditButton(selectedAccounts.size == 1)
      showLogoButton(selectedAccounts.size == 1)
    }
  }

  fun updateAccount(account: Account, newName: String) {
    if (newName.isNotEmpty()) {
      val params = UpdateAccountInteractor.Params(account.copy(name = newName))
      updateAccountInteractor.execute(params, UpdateAccountSubscriber())
    }

    view?.dismissActionMode()
  }

  fun updateAccount(account: Account, issuer: Issuer) {
    val params = UpdateAccountInteractor.Params(account.copy(issuer = issuer))
    updateAccountInteractor.execute(params, UpdateAccountSubscriber())

    view?.dismissActionMode()
  }

  fun pickCategoryForAccount(account: Account) {
    getCategoriesInteractor.execute(GetCategoriesInteractor.EmptyParams, GetCategoriesSubscriber(account))
  }

  fun pickLogoForAccount(account: Account) {
    getIssuersInteractor.execute(GetIssuersInteractor.EmptyParams, GetIssuersSubscriber(account))
  }

  fun addAccountToCategory(category: Category, account: Account) {
    val params = AddAccountToCategoryInteractor.Params(category, account)
    addAccountToCategoryInteractor.execute(params, AddAccountToCategorySubscriber())

    view?.dismissActionMode()
  }

  fun createCategory(categoryName: String, account: Account) {
    val params = AddCategoryInteractor.Params(categoryName, account)
    addCategoryInteractor.execute(params, CreateCategorySubscriber())

    view?.dismissActionMode()
  }

  private inner class UpdateAccountSubscriber : DefaultSubscriber<Account>() {
    override fun onNext(item: Account) {
      view?.dismissActionMode()
    }

    override fun onError(e: Throwable) {
      view?.dismissActionMode()
    }
  }

  private inner class DeleteAccountsSubscriber : DefaultSubscriber<Unit>() {
    override fun onNext(aVoid: Unit) {
      view?.dismissActionMode()
    }

    override fun onError(e: Throwable) {
      view?.dismissActionMode()
    }
  }

  private inner class GetCategoriesSubscriber(val account: Account) : DefaultSubscriber<List<Category>>() {

    override fun onNext(categories: List<Category>) {
      getCategoriesInteractor.unsubscribe()

      if (categories.isEmpty()) {
        view?.showChooseEmptyCategory(account)
      } else {
        view?.showChooseCategory(categories, account)
      }
    }

    override fun onError(e: Throwable) {
      view?.dismissActionMode()
    }
  }

  private inner class CreateCategorySubscriber : DefaultSubscriber<Category>() {

    override fun onNext(category: Category) {
      view?.dismissActionMode()
    }

    override fun onError(e: Throwable) {
      view?.dismissActionMode()
    }
  }

  private inner class AddAccountToCategorySubscriber : DefaultSubscriber<Category>() {

    override fun onNext(category: Category) {
      view?.dismissActionMode()
    }

    override fun onError(e: Throwable) {
      view?.dismissActionMode()
    }
  }

  private inner class GetIssuersSubscriber(private val account: Account) : DefaultSubscriber<List<Issuer>>() {

    override fun onNext(issuers: List<Issuer>) {
      val issuerDecorators = issuerDecoratorFactory.create(issuers)
      view?.showChooseLogo(issuerDecorators, account)
    }
  }
}
