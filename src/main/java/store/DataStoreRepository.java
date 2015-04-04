package store;

import com.google.api.client.util.SecurityUtils;
import com.google.gcloud.AuthCredentials;
import com.google.gcloud.datastore.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

public class DataStoreRepository implements VotesRepository {
    private static final String ACCOUNT = "865873303807-2dshpt3j2bd4l47iuli1139jj9jdln0r@developer.gserviceaccount.com";
    private static final String PRIVATE_KEY = "key.p12";
    private static final String STORE_PASS = "notasecret";
    private static final String ALIAS = "privatekey";
    private static final String KEY_PASS = "notasecret";

    private final DatastoreService dataStore;
    private final KeyFactory keyFactory;

    public DataStoreRepository() {
        this.dataStore = createDataStore();
        this.keyFactory = dataStore.newKeyFactory().kind("Match");
    }

    private DatastoreService createDataStore() {
        try {
            DatastoreServiceOptions options = DatastoreServiceOptions.builder()
                    .authCredentials(AuthCredentials.createFor(ACCOUNT, readPrivateKey(PRIVATE_KEY)))
                    .dataset("devoxxcarpet")
                    .build();

            return DatastoreServiceFactory.getDefault(options);
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect to DataStore", e);
        }
    }

    private static PrivateKey readPrivateKey(String name) {
        try (InputStream input = ClassLoader.getSystemResourceAsStream(name)) {
            return SecurityUtils.loadPrivateKeyFromKeyStore(SecurityUtils.getPkcs12KeyStore(), input, STORE_PASS, ALIAS, KEY_PASS);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Unable to read private key", e);
        }
    }

    @Override
    public void reload(VoteAction action) {
        StructuredQuery<Entity> query = GqlQuery.entityQueryBuilder()
                .kind("Match")
                .build();

        try {
            dataStore.run(query).forEachRemaining(match -> {
                match.getLong("winner");
                match.getLong("looser");
            });
        } catch (Exception e) {
            // First try fails. I don't know why
            System.out.println(e);
        }

        dataStore.run(query).forEachRemaining(match -> {
            int winner = (int) match.getLong("winner");
            int looser = (int) match.getLong("looser");

            action.onVote(winner, looser);
        });
    }

    @Override
    public void vote(int winner, int looser) {
        Key key = dataStore.allocateId(keyFactory.newKey());

        dataStore.put(Entity
                .builder(key)
                .set("date", DateTime.now())
                .set("winner", winner)
                .set("looser", looser)
                .build());
    }
}
