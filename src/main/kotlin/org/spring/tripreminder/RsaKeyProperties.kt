package org.spring.tripreminder

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Component
@ConfigurationProperties(prefix = "rsa")
class RsaKeyProperties {
    private lateinit var publicKey: RSAPublicKey
    private lateinit var privateKey: RSAPrivateKey

    fun getPublicKey(): RSAPublicKey {
        return publicKey
    }

    fun setPublicKey(publicKey: RSAPublicKey) {
        this.publicKey = publicKey
    }

    fun getPrivateKey(): RSAPrivateKey {
        return privateKey
    }

    fun setPrivateKey(privateKey: RSAPrivateKey) {
        this.privateKey = privateKey
    }
}
