package com.arturogutierrez.openticator.domain.issuer.interactor

import com.arturogutierrez.openticator.domain.issuer.interactor.GetIssuersInteractor.EmptyParams
import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import com.arturogutierrez.openticator.domain.issuer.repository.IssuerRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable

class GetIssuersInteractor(private val issuerRepository: IssuerRepository,
                           threadExecutor: ThreadExecutor,
                           postExecutionThread: PostExecutionThread) : Interactor<EmptyParams, List<Issuer>>(threadExecutor, postExecutionThread) {

  override fun createObservable(params: EmptyParams): Observable<List<Issuer>> {
    return issuerRepository.issuers
  }

  object EmptyParams
}
