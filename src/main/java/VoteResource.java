import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.Votes;

import static java.util.stream.IntStream.range;
import static store.Votes.CARPET_COUNT;

public class VoteResource {
    private final Logger log = LoggerFactory.getLogger(VoteResource.class);

    private final Votes votes;

    public VoteResource(Votes votes) {
        this.votes = votes;
    }

    @Get("/carpets")
    public Carpet[] carpets() {
        return range(0, CARPET_COUNT)
            .mapToObj(index -> new Carpet(index, votes.score(index), votes.votes(index)))
            .toArray(Carpet[]::new);
    }

    @Post("/votes/:winner/:looser")
    public void vote(int winner, int looser) {
        log.info("MATCH;{};{};{};{};{};{}", winner, looser, votes.score(winner), votes.votes(winner), votes.score(looser), votes.votes(looser));

        votes.vote(winner, looser);
    }
}
