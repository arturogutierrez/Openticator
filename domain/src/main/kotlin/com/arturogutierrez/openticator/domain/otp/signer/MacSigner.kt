package com.arturogutierrez.openticator.domain.otp.signer

import javax.crypto.Mac

class MacSigner(val mac: Mac) {

    fun sign(dataToSign: ByteArray): ByteArray {
        return mac.doFinal(dataToSign)
    }
}
