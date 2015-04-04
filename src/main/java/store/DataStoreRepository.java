package store;

import com.google.api.services.datastore.DatastoreV1;
import com.google.api.services.datastore.DatastoreV1.*;
import com.google.api.services.datastore.client.Datastore;
import com.google.api.services.datastore.client.DatastoreFactory;
import com.google.api.services.datastore.client.DatastoreOptions;
import com.google.protobuf.ByteString;

import java.util.Random;

import static com.google.api.services.datastore.DatastoreV1.KindExpression.newBuilder;
import static com.google.api.services.datastore.client.DatastoreHelper.*;

public class DataStoreRepository implements VotesRepository {
    private final Datastore dataStore;
    private final String serverId;

    private ByteString cursor;

    public DataStoreRepository() {
        this.dataStore = createDataStore();
        this.serverId = System.currentTimeMillis() + "-" + new Random().nextLong();
    }

    private Datastore createDataStore() {
        return DatastoreFactory.get().create(new DatastoreOptions.Builder()
                .dataset("devoxxcarpet")
                .credential(Authentication.get())
                .build());
    }

    @Override
    public void refresh(VoteAction action) {
        try {
            Query.Builder query = Query.newBuilder().addKind(newBuilder().setName("Match"));
            if (cursor != null) {
                query.setStartCursor(cursor);
            }

            QueryResultBatch batch = dataStore.runQuery(RunQueryRequest.newBuilder().setQuery(query).build()).getBatch();

            batch.getEntityResultList().forEach(result -> {
                Entity entity = result.getEntity();
                String server = entity.getProperty(2).getValue().getStringValue();

                if (!serverId.equals(server)) {
                    int winner = (int) entity.getProperty(0).getValue().getIntegerValue();
                    int looser = (int) entity.getProperty(1).getValue().getIntegerValue();

                    action.onVote(winner, looser);
                }
            });

            cursor = batch.getEndCursor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void vote(int winner, int looser) {
        try {
            BeginTransactionResponse tres = dataStore.beginTransaction(BeginTransactionRequest.newBuilder().build());

            CommitRequest.Builder creq = CommitRequest
                    .newBuilder()
                    .setTransaction(tres.getTransaction());

            creq.getMutationBuilder().addInsertAutoId(DatastoreV1.Entity
                    .newBuilder()
                    .setKey(makeKey("Match"))
                    .addProperty(makeProperty("server", makeValue(serverId)))
                    .addProperty(makeProperty("winner", makeValue(winner)))
                    .addProperty(makeProperty("looser", makeValue(looser))));

            dataStore.commit(creq.build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
