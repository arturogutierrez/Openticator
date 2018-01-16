package com.arturogutierrez.openticator.domain.issuer.interactor

import com.arturogutierrez.openticator.domain.issuer.interactor.GetIssuersInteractor.EmptyParams
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import com.arturogutierrez.openticator.domain.issuer.repository.IssuerRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.SingleUseCase
import io.reactivex.Single

class GetIssuersInteractor(private val issuerRepository: IssuerRepository,
                           threadExecutor: ThreadExecutor,
                           postExecutionThread: PostExecutionThread)
  : SingleUseCase<List<Issuer>, EmptyParams>(threadExecutor, postExecutionThread) {

  override fun buildObservable(params: EmptyParams): Single<List<Issuer>> {
    return issuerRepository.issuers
  }

  object EmptyParams
}
