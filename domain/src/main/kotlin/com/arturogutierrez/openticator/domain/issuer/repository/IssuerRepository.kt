package com.arturogutierrez.openticator.domain.issuer.repository

import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IssuerRepository @Inject constructor() {

  val issuers: Single<List<Issuer>>
    get() {
      val allIssuers = Issuer.values().asList().filter { it != Issuer.UNKNOWN }
      return Single.just(allIssuers)
    }
}
