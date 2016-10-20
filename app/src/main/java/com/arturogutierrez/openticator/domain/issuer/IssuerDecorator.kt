package com.arturogutierrez.openticator.domain.issuer

import com.arturogutierrez.openticator.domain.issuer.model.Issuer

data class IssuerDecorator(val issuer: Issuer, val name: String, val imageResource: Int)
