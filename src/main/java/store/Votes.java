package store;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Votes {
    public static final int MAX_CARPET = 5;
    private static final int START_SCORE = 1000;

    private final int[] playedPerIndex;
    private final int[] scorePerIndex;
    private final int[] votesPerIndex;
    private final VotesRepository votesRepository;
    private final Executor executor;

    public Votes() {
        this.votesRepository = createVotesRepository();
        this.playedPerIndex = new int[MAX_CARPET];
        this.scorePerIndex = new int[MAX_CARPET];
        this.votesPerIndex = new int[MAX_CARPET];
        this.executor = Executors.newSingleThreadExecutor();

        Arrays.fill(playedPerIndex, 0);
        Arrays.fill(scorePerIndex, START_SCORE);
        Arrays.fill(votesPerIndex, 0);
        votesRepository.init();
        votesRepository.reload(this::computeVote);
    }

    private VotesRepository createVotesRepository() {
        if ("true".equals(System.getenv("DATASTORE"))) {
            return new DataStoreRepository();
        } else {
            return new InMemoryRepository();
        }
    }

    public synchronized int score(int index) {
        return scorePerIndex[index];
    }

    public synchronized int votes(int index) {
        return votesPerIndex[index];
    }

    public synchronized void vote(int winner, int looser) {
        computeVote(winner, looser);
        executor.execute(() -> {
            votesRepository.vote(winner, looser);
        });
    }

    void computeVote(int winner, int looser) {
        int score1 = scorePerIndex[winner];
        int score2 = scorePerIndex[looser];
        int d = Math.min(400, Math.abs(score1 - score2));
        float p = 1f / (1f + (float) Math.pow(10, -d / 400f));
        int k1 = k(score1, ++playedPerIndex[winner]);
        int k2 = k(score2, ++playedPerIndex[looser]);

        int r1 = Math.round(score1 + (k1 * (1 - p)));
        int r2 = Math.round(score2 + (k2 * (0 - p)));

        votesPerIndex[winner]++;
        scorePerIndex[winner] = r1;
        scorePerIndex[looser] = r2;
    }

    private int k(int score, int played) {
        return played < 30 ? 25 : score < 2400 ? 15 : 10;
    }
}
