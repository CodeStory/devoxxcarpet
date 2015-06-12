package resources;

import net.codestory.http.annotations.Post;
import net.codestory.http.annotations.Prefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.Votes;

@Prefix("/votes")
public class VoteResource {
    private final Logger log = LoggerFactory.getLogger(VoteResource.class);

    private final Votes votes;

    public VoteResource(Votes votes) {
        this.votes = votes;
    }

    @Post(":winner/:looser")
    public void vote(int winner, int looser) {
        log.info("MATCH;{};{};{};{};{};{}", winner, looser, votes.score(winner), votes.votes(winner), votes.score(looser), votes.votes(looser));

        votes.vote(winner, looser);
    }
}
