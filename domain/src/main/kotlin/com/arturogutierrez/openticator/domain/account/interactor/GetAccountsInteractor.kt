package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable

class GetAccountsInteractor(val accountRepository: AccountRepository,
                            val threadExecutor: ThreadExecutor,
                            val postExecutionThread: PostExecutionThread) : Interactor<List<Account>>(threadExecutor, postExecutionThread) {

    override fun createObservable(): Observable<List<Account>> {
        return accountRepository.allAccounts
    }
}
