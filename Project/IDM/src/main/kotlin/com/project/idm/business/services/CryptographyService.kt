package com.project.idm.business.services

import com.project.idm.business.interfaces.ICryptographyService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Service
class CryptographyService : ICryptographyService {

    @Value("\${secret.token.encryption.secret.thingy.thingy}")
    private lateinit var cryptoTokenSecret: String

    override fun encrypt(token: String): String {
        val secretKey: Key = SecretKeySpec(cryptoTokenSecret.toByteArray(Charsets.UTF_8), "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedValue = cipher.doFinal(token.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(encryptedValue)
    }

    override fun decrypt(value: String): String {
        val secretKey: Key = SecretKeySpec(cryptoTokenSecret.toByteArray(Charsets.UTF_8), "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decryptedValue = cipher.doFinal(Base64.getDecoder().decode(value))
        return String(decryptedValue, Charsets.UTF_8)
    }
}