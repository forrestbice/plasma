package social.plasma.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import social.plasma.crypto.Bech32.bechToBytes
import social.plasma.crypto.Bech32.pubkeyCreate
import social.plasma.crypto.Bech32.toNpub

@RunWith(AndroidJUnit4::class)
class KeyConversionTest {
    @Test
    fun generatePubkeyFromSecretKey() {
        val privateKeyBytes = SECRET_KEY.bechToBytes()

        val pubKey = pubkeyCreate(privateKeyBytes).toNpub()

        Assert.assertEquals(PUBLIC_KEY, pubKey)
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidKey() {
        val privateKeyButes = INVALID_KEY.bechToBytes()

        pubkeyCreate(privateKeyButes).toNpub()
    }

    companion object {
        const val SECRET_KEY = "nsec16pzcl7krecytvxdj28wlzver8tuwfvfs6wytjn7plyyvp6acdkzq705x7t"
        const val PUBLIC_KEY = "npub1z2aauyjavy9kfau3jn4cq3u0uvadjhkngxzv0uzh0e3pfuexdjcql0pyy7"
        const val INVALID_KEY = "npub1z2aauyjavy9kfau3jn4cq3u0uvadjhkngxzv0uzh0e3pfuexdjcql0pyy"
    }
}
