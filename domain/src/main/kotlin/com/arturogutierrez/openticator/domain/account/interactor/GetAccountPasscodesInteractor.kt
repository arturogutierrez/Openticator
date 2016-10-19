package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.domain.category.CategoryFactory
import com.arturogutierrez.openticator.domain.category.CategorySelector
import com.arturogutierrez.openticator.domain.otp.OneTimePasswordFactory
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable
import java.util.*

class GetAccountPasscodesInteractor(val categorySelector: CategorySelector,
                                    val categoryFactory: CategoryFactory,
                                    val accountRepository: AccountRepository,
                                    val oneTimePasswordFactory: OneTimePasswordFactory,
                                    val threadExecutor: ThreadExecutor,
                                    val postExecutionThread: PostExecutionThread) : Interactor<List<AccountPasscode>>(threadExecutor, postExecutionThread) {

    override fun createObservable(): Observable<List<AccountPasscode>> {
        return categorySelector.selectedCategory.flatMap { category ->
            val emptyCategory = categoryFactory.createEmptyCategory()
            if (category == emptyCategory) {
                accountRepository.allAccounts
            }
            accountRepository.getAccounts(category)
        }.map { accountList -> calculatePasscodes(accountList) }
    }

    private fun calculatePasscodes(accountList: List<Account>): List<AccountPasscode> {
        val accountPasscodeList = ArrayList<AccountPasscode>(accountList.size)
        for (account in accountList) {
            val accountPasscode = calculatePasscode(account)
            accountPasscodeList.add(accountPasscode)
        }
        return accountPasscodeList
    }

    private fun calculatePasscode(account: Account): AccountPasscode {
        // TODO: Pick right delta offset time
        val oneTimePassword = oneTimePasswordFactory.createOneTimePassword(account, 0)
        val passcode = oneTimePassword.generate()
        return AccountPasscode(account, account.issuer, passcode)
    }
}
