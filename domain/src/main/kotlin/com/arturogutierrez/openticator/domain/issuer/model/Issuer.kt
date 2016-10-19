package com.arturogutierrez.openticator.domain.issuer.model

enum class Issuer(val identifier: String) {

    UNKNOWN("unknown"),
    AWS("aws"),
    BITCOIN("bitcoin"),
    DIGITALOCEAN("digitalocean"),
    DROPBOX("dropbox"),
    EVERNOTE("evernote"),
    FACEBOOK("facebook"),
    GITHUB("github"),
    GOOGLE("google"),
    MICROSOFT("microsoft"),
    SLACK("slack"),
    WORDPRESS("wordpress");

    companion object {
        private val map = Issuer.values().associateBy(Issuer::identifier)
        fun fromString(identifier: String) = map[identifier]
    }
}
