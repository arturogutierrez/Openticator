package com.arturogutierrez.openticator.domain.issuer.interactor

import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import com.arturogutierrez.openticator.domain.issuer.repository.IssuerRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.Interactor
import rx.Observable

class GetIssuersInteractor(val issuerRepository: IssuerRepository,
                           val threadExecutor: ThreadExecutor,
                           val postExecutionThread: PostExecutionThread) : Interactor<List<Issuer>>(threadExecutor, postExecutionThread) {

  override fun createObservable(): Observable<List<Issuer>> {
    return issuerRepository.issuers
  }
}
