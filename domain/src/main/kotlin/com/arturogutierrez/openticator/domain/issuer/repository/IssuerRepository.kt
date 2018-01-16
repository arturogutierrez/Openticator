package com.arturogutierrez.openticator.domain.issuer.repository

import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import io.reactivex.Single

interface IssuerRepository {

  val issuers: Single<List<Issuer>>
    get() {
      val allIssuers = Issuer.values().asList().filter { it != Issuer.UNKNOWN }
      return Single.just(allIssuers)
    }
}
