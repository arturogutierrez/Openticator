package com.arturogutierrez.openticator.domain.issuer.repository

import com.arturogutierrez.openticator.domain.issuer.model.Issuer

import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

class IssuerRepository @Inject constructor() {

  val issuers: Observable<List<Issuer>>
    get() {
      val allIssuers = Issuer.values().asList().filter { it != Issuer.UNKNOWN }
      return Observable.just<List<Issuer>>(allIssuers)
    }
}
