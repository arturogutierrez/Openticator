package com.arturogutierrez.openticator.domain.issuer.repository

import com.arturogutierrez.openticator.domain.issuer.model.Issuer

import rx.Observable

interface IssuerRepository {

    val issuers: Observable<List<Issuer>>
}
