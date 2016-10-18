package com.arturogutierrez.openticator.domain.issuer.repository

import com.arturogutierrez.openticator.domain.issuer.model.Issuer
import rx.Observable
import javax.inject.Inject

class IssuerRepositoryImpl @Inject constructor() : IssuerRepository {

    override val issuers: Observable<List<Issuer>>
        get() {
            val allIssuers = Issuer.values().asList().filter { it != Issuer.UNKNOWN }
            return Observable.just<List<Issuer>>(allIssuers)
        }
}
