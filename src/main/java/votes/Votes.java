package votes;

import com.google.common.util.concurrent.AtomicLongMap;

import java.util.Arrays;

public class Votes {
    public static final int MAX_CARPET = 5;
    private static final int START_SCORE = 1000;

    private final AtomicLongMap<Integer> votes = AtomicLongMap.create();
    private final int[] playedPerIndex;
    private final int[] scorePerIndex;
    private final VotesRepository votesRepository;

    public Votes() {
        if ("true".equals(System.getenv("DATASTORE"))) {
            this.votesRepository = new VotesCloudRepository();
        } else {
            this.votesRepository = new VotesInMemoryRepository();
        }

        playedPerIndex = new int[MAX_CARPET + 1];
        scorePerIndex = new int[MAX_CARPET + 1];
        Arrays.fill(playedPerIndex, 0);
        Arrays.fill(scorePerIndex, START_SCORE);

        votesRepository.reload(this::computeVote);
    }

    public synchronized int score(int index) {
        return scorePerIndex[index];
    }

    public int votes(int index) {
        return (int) votes.get(index);
    }

    public synchronized void vote(int winner, int looser) {
        votesRepository.vote(winner, looser);
        computeVote(winner, looser);
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

        votes.incrementAndGet(winner);
        scorePerIndex[winner] = r1;
        scorePerIndex[looser] = r2;
    }

    private int k(int score, int played) {
        return played < 30 ? 25 : score < 2400 ? 15 : 10;
    }
}
