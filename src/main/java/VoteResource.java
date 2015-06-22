import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Post;

import java.util.Random;

import static java.util.stream.IntStream.range;

public class VoteResource {
    private final Random random;
    private final Votes votes;

    public VoteResource(Random random, Votes votes) {
        this.random = random;
        this.votes = votes;
    }

    @Get("/match")
    public int[] match() {
        int first;
        int second;
        do {
            first = randomIndex();
            second = randomIndex();
        } while (first == second);

        return new int[]{first, second};
    }

    @Get("/carpets")
    public Carpet[] carpets() {
        return range(0, Votes.CARPET_COUNT)
            .mapToObj(index -> new Carpet(index, votes.score(index), votes.votes(index)))
            .toArray(Carpet[]::new);
    }

    @Post("/votes/:winner/:looser")
    public void vote(int winner, int looser) {
        votes.vote(winner, looser);
    }

    private int randomIndex() {
        return random.nextInt(Votes.CARPET_COUNT);
    }

    private Carpet carpet(int index) {
        return new Carpet(index, votes.score(index), votes.votes(index));
    }
}
