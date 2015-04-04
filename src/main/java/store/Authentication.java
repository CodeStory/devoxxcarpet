package store;

import com.google.api.client.util.SecurityUtils;
import com.google.gcloud.AuthCredentials;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

public class Authentication {
    private static final String ACCOUNT = "865873303807-2dshpt3j2bd4l47iuli1139jj9jdln0r@developer.gserviceaccount.com";
    private static final String PRIVATE_KEY = "key.p12";
    private static final String STORE_PASS = "notasecret";
    private static final String ALIAS = "privatekey";
    private static final String KEY_PASS = "notasecret";

    public static AuthCredentials get() {
        return AuthCredentials.createFor(ACCOUNT, readPrivateKey(PRIVATE_KEY));
    }

    private static PrivateKey readPrivateKey(String name) {
        try (InputStream input = ClassLoader.getSystemResourceAsStream(name)) {
            return SecurityUtils.loadPrivateKeyFromKeyStore(SecurityUtils.getPkcs12KeyStore(), input, STORE_PASS, ALIAS, KEY_PASS);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Unable to read private key", e);
        }
    }
}
