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
import com.arturogutierrez.openticator.interactor.DefaultCompletableObserver
import com.arturogutierrez.openticator.interactor.DefaultFlowableObserver
import com.arturogutierrez.openticator.interactor.DefaultSingleObserver
import com.arturogutierrez.openticator.view.presenter.Presenter
import javax.inject.Inject

class AccountEditModePresenter @Inject constructor(
    private val updateAccountInteractor: UpdateAccountInteractor,
    private val deleteAccountsInteractor: DeleteAccountsInteractor,
    private val getCategoriesInteractor: GetCategoriesInteractor,
    private val addCategoryInteractor: AddCategoryInteractor,
    private val addAccountToCategoryInteractor: AddAccountToCategoryInteractor,
    private val getIssuersInteractor: GetIssuersInteractor,
    private val issuerDecoratorFactory: IssuerDecoratorFactory)
  : Presenter<AccountEditModeView>() {

  override fun destroy() {
    deleteAccountsInteractor.unsubscribe()
    getCategoriesInteractor.dispose()
    addCategoryInteractor.dispose()
    addAccountToCategoryInteractor.dispose()
    getIssuersInteractor.dispose()
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

  private inner class UpdateAccountSubscriber : DefaultCompletableObserver() {

    override fun onComplete() {
      view?.dismissActionMode()
    }

    override fun onError(e: Throwable) {
      view?.dismissActionMode()
    }
  }

  private inner class DeleteAccountsSubscriber : DefaultCompletableObserver() {

    override fun onComplete() {
      view?.dismissActionMode()
    }

    override fun onError(e: Throwable) {
      view?.dismissActionMode()
    }
  }

  private inner class GetCategoriesSubscriber(val account: Account) : DefaultFlowableObserver<List<Category>>() {

    override fun onNext(t: List<Category>) {
      getCategoriesInteractor.clear()

      if (t.isEmpty()) {
        view?.showChooseEmptyCategory(account)
      } else {
        view?.showChooseCategory(t, account)
      }
    }

    override fun onError(e: Throwable) {
      view?.dismissActionMode()
    }
  }

  private inner class CreateCategorySubscriber : DefaultSingleObserver<Category>() {

    override fun onSuccess(t: Category) {
      view?.dismissActionMode()
    }

    override fun onError(e: Throwable) {
      view?.dismissActionMode()
    }
  }

  private inner class AddAccountToCategorySubscriber : DefaultSingleObserver<Category>() {

    override fun onSuccess(t: Category) {
      view?.dismissActionMode()
    }

    override fun onError(e: Throwable) {
      view?.dismissActionMode()
    }
  }

  private inner class GetIssuersSubscriber(private val account: Account) : DefaultSingleObserver<List<Issuer>>() {

    override fun onSuccess(t: List<Issuer>) {
      val issuerDecorators = issuerDecoratorFactory.create(t)
      view?.showChooseLogo(issuerDecorators, account)
    }
  }
}
