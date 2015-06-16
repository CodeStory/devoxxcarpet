package store;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.util.SecurityUtils;
import com.google.api.services.datastore.client.DatastoreHelper;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

import static com.google.api.services.datastore.client.DatastoreOptions.SCOPES;

public class Authentication {
    private static final String ACCOUNT = "629405951430-p15o1ticj2m0fmbvo3v85htc35q5rban@developer.gserviceaccount.com";
    private static final String PRIVATE_KEY = "key.p12";
    private static final String STORE_PASS = "notasecret";
    private static final String ALIAS = "privatekey";
    private static final String KEY_PASS = "notasecret";

    public static Credential get() {
        try {
            return DatastoreHelper.getServiceAccountCredential(
                ACCOUNT,
                readPrivateKey(PRIVATE_KEY),
                SCOPES);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static PrivateKey readPrivateKey(String name) {
        try (InputStream input = ClassLoader.getSystemResourceAsStream(name)) {
            return SecurityUtils.loadPrivateKeyFromKeyStore(SecurityUtils.getPkcs12KeyStore(), input, STORE_PASS, ALIAS, KEY_PASS);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Unable to read private key", e);
        }
    }
}
