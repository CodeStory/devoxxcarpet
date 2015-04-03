package store;

import com.google.gcloud.datastore.*;

public class VotesCloudRepository implements VotesRepository {
    private final DatastoreService dataStore;

    public VotesCloudRepository() {
        dataStore = createDataStore();
    }

    private DatastoreService createDataStore() {
        DatastoreServiceOptions options = DatastoreServiceOptions.builder()
                .dataset("devoxxcarpet")
                .build();

        return DatastoreServiceFactory.getDefault(options);
    }

    @Override
    public void reload(VoteAction action) {
        StructuredQuery<Entity> query = GqlQuery.entityQueryBuilder().kind("Match").build();
        QueryResults<Entity> results = dataStore.run(query);

        while (results.hasNext()) {
            Entity next = results.next();

            int winner = (int) next.getLong("winner");
            int looser = (int) next.getLong("looser");

            action.onVote(winner, looser);
        }
    }

    @Override
    public void vote(int winner, int looser) {
        KeyFactory keyFactory = dataStore.newKeyFactory().kind("Match");

        Key key = keyFactory.newKey(System.currentTimeMillis()); // TODO

        Entity entity = Entity.builder(key).set("winner", winner).set("looser", looser).build();
        dataStore.put(entity);
    }
}
