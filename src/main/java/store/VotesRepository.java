package store;

public interface VotesRepository {
    void init();

    void reload(VoteAction action);

    void vote(int winner, int looser);

    @FunctionalInterface
    interface VoteAction {
        void onVote(int winner, int looser);
    }
}
