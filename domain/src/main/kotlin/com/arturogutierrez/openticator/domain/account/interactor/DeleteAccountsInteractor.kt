package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable
import java.util.*

class DeleteAccountsInteractor(val accountRepository: AccountRepository,
                               val threadExecutor: ThreadExecutor,
                               val postExecutionThread: PostExecutionThread) : Interactor<Void>(threadExecutor, postExecutionThread) {
    private lateinit var accounts: Set<Account>

    fun configure(accounts: Set<Account>) {
        this.accounts = accounts
    }

    override fun createObservable(): Observable<Void> {
        if (accounts.size == 0) {
            throw IllegalStateException("You can't delete no one accounts")
        }

        val removeAccountObservables = ArrayList<Observable<Void>>(accounts.size)
        for (account in accounts) {
            removeAccountObservables.add(accountRepository.remove(account))
        }

        return Observable.merge(removeAccountObservables)
    }
}
