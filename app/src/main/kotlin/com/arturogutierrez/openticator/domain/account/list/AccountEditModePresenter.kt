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
        val issuerDecoratorFactory: IssuerDecoratorFactory) : DefaultSubscriber<Void>(), Presenter {

    private lateinit var view: AccountEditModeView

    fun setView(view: AccountEditModeView) {
        this.view = view
    }

    override fun resume() {

    }

    override fun pause() {

    }

    override fun destroy() {
        deleteAccountsInteractor.unsubscribe()
    }

    fun deleteAccounts(selectedAccounts: Set<Account>) {
        deleteAccountsInteractor.configure(selectedAccounts)
        deleteAccountsInteractor.execute(DeleteAccountsSubscriber())
    }

    fun onSelectedAccounts(selectedAccounts: Set<Account>) {
        if (selectedAccounts.size == 0) {
            view.dismissActionMode()
            return
        }

        view.showCategoryButton(selectedAccounts.size == 1)
        view.showEditButton(selectedAccounts.size == 1)
        view.showLogoButton(selectedAccounts.size == 1)
    }

    fun updateAccount(account: Account, newName: String) {
        if (newName.length > 0) {
            updateAccountInteractor.configure(account, newName)
            updateAccountInteractor.execute(UpdateAccountSubscriber())
        }

        view.dismissActionMode()
    }

    fun updateAccount(account: Account, issuer: Issuer) {
        updateAccountInteractor.configure(account, issuer)
        updateAccountInteractor.execute(UpdateAccountSubscriber())

        view.dismissActionMode()
    }

    fun pickCategoryForAccount(account: Account) {
        getCategoriesInteractor.execute(GetCategoriesSubscriber(account))
    }

    fun pickLogoForAccount(account: Account) {
        getIssuersInteractor.execute(GetIssuersSubscriber(account))
    }

    fun addAccountToCategory(category: Category, account: Account) {
        addAccountToCategoryInteractor.configure(category, account)
        addAccountToCategoryInteractor.execute(AddAccountToCategorySubscriber())

        view.dismissActionMode()
    }

    fun createCategory(categoryName: String, account: Account) {
        addCategoryInteractor.configure(categoryName, account)
        addCategoryInteractor.execute(CreateCategorySubscriber())

        view.dismissActionMode()
    }

    private inner class UpdateAccountSubscriber : DefaultSubscriber<Account>() {
        override fun onNext(item: Account) {
            view.dismissActionMode()
        }

        override fun onError(e: Throwable) {
            view.dismissActionMode()
        }
    }

    private inner class DeleteAccountsSubscriber : DefaultSubscriber<Void>() {
        override fun onNext(aVoid: Void) {
            view.dismissActionMode()
        }

        override fun onError(e: Throwable) {
            view.dismissActionMode()
        }
    }

    private inner class GetCategoriesSubscriber(private val account: Account) : DefaultSubscriber<List<Category>>() {

        override fun onNext(categories: List<Category>) {
            getCategoriesInteractor.unsubscribe()

            if (categories.size == 0) {
                view.showChooseEmptyCategory(account)
            } else {
                view.showChooseCategory(categories, account)
            }
        }

        override fun onError(e: Throwable) {
            view.dismissActionMode()
        }
    }

    private inner class CreateCategorySubscriber : DefaultSubscriber<Category>() {

        override fun onNext(category: Category) {
            view.dismissActionMode()
        }

        override fun onError(e: Throwable) {
            view.dismissActionMode()
        }
    }

    private inner class AddAccountToCategorySubscriber : DefaultSubscriber<Category>() {

        override fun onNext(category: Category) {
            view.dismissActionMode()
        }

        override fun onError(e: Throwable) {
            view.dismissActionMode()
        }
    }

    private inner class GetIssuersSubscriber(private val account: Account) : DefaultSubscriber<List<Issuer>>() {

        override fun onNext(issuers: List<Issuer>) {
            val issuerDecorators = issuerDecoratorFactory.create(issuers)
            view.showChooseLogo(issuerDecorators, account)
        }
    }
}
