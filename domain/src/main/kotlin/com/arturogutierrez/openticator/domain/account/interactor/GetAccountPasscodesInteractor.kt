package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.interactor.GetAccountPasscodesInteractor.EmptyParams
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.model.AccountPasscode
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.domain.category.CategoryFactory
import com.arturogutierrez.openticator.domain.category.CategorySelector
import com.arturogutierrez.openticator.domain.otp.OneTimePasswordFactory
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.FlowableUseCase
import io.reactivex.Flowable

class GetAccountPasscodesInteractor(private val categorySelector: CategorySelector,
                                    private val categoryFactory: CategoryFactory,
                                    private val accountRepository: AccountRepository,
                                    private val oneTimePasswordFactory: OneTimePasswordFactory,
                                    threadExecutor: ThreadExecutor,
                                    postExecutionThread: PostExecutionThread)
  : FlowableUseCase<List<AccountPasscode>, EmptyParams>(threadExecutor, postExecutionThread) {

  override fun buildObservable(params: EmptyParams): Flowable<List<AccountPasscode>> {
    return categorySelector.selectedCategory.flatMap { category ->
      val emptyCategory = categoryFactory.createEmptyCategory()
      if (category == emptyCategory) {
        return@flatMap accountRepository.allAccounts
      }
      accountRepository.getAccounts(category)
    }.map { accountList ->
      calculatePasscodes(accountList)
    }
  }

  private fun calculatePasscodes(accountList: List<Account>): List<AccountPasscode> {
    return accountList.map { calculatePasscode(it) }
  }

  private fun calculatePasscode(account: Account): AccountPasscode {
    // TODO: Pick right delta offset time
    val oneTimePassword = oneTimePasswordFactory.createOneTimePassword(account, 0)
    val passcode = oneTimePassword.generate()
    return AccountPasscode(account, account.issuer, passcode)
  }

  object EmptyParams
}
