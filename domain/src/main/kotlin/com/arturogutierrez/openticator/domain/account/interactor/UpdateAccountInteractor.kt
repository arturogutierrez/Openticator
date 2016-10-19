package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.AccountFactory
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable

class UpdateAccountInteractor(val accountRepository: AccountRepository,
                              val accountFactory: AccountFactory,
                              val threadExecutor: ThreadExecutor,
                              val postExecutionThread: PostExecutionThread) : Interactor<Account>(threadExecutor, postExecutionThread) {
    private lateinit var account: Account

    fun configure(account: Account, newName: String) {
        this.account = accountFactory.createAccount(account, newName)
    }

    fun configure(account: Account, newIssuer: Issuer) {
        this.account = accountFactory.createAccount(account, newIssuer)
    }

    override fun createObservable(): Observable<Account> {
        return accountRepository.update(account)
    }
}
