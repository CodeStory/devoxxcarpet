package store;

import com.google.gcloud.datastore.*;

public class DataStoreRepository implements VotesRepository {
    private final DatastoreService dataStore;
    private final KeyFactory keyFactory;

    public DataStoreRepository() {
        this.dataStore = createDataStore();
        this.keyFactory = dataStore.newKeyFactory().kind("Match");
    }

    private DatastoreService createDataStore() {
        return DatastoreServiceFactory.getDefault(
                DatastoreServiceOptions
                        .builder()
                        .dataset("devoxxcarpet")
                        .authCredentials(Authentication.get())
                        .build());
    }

    @Override
    public void reload(VoteAction action) {
        StructuredQuery<Entity> query = GqlQuery
                .entityQueryBuilder()
                .kind("Match")
                .build();

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

    @Override
    public void init() {
        // First try fails. I don't know why
        try {
            StructuredQuery<Entity> query = GqlQuery
                    .entityQueryBuilder()
                    .kind("Match")
                    .build();

            dataStore.run(query).forEachRemaining(match -> {
                match.getLong("winner");
                match.getLong("looser");
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
