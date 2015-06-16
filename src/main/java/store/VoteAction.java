package store;

@FunctionalInterface
interface VoteAction {
    void onVote(int winner, int looser);
}