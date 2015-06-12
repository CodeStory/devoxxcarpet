package resources;

import model.Carpet;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Prefix;
import store.Votes;

import java.util.Random;

import static java.util.stream.IntStream.range;
import static store.Votes.CARPET_COUNT;

@Prefix("/carpets")
public class CarpetsResource {
    private final Random random;
    private final Votes votes;

    public CarpetsResource(Random random, Votes votes) {
        this.random = random;
        this.votes = votes;
    }

    @Get("")
    public Carpet[] carpets() {
        return range(0, CARPET_COUNT).mapToObj(this::carpet).toArray(Carpet[]::new);
    }

    @Get("match")
    public Carpet[] match() {
        int first;
        int second;
        do {
            first = randomIndex();
            second = randomIndex();
        } while (first == second);

        return new Carpet[]{carpet(first), carpet(second)};
    }

    private int randomIndex() {
        return random.nextInt(CARPET_COUNT);
    }

    private Carpet carpet(int index) {
        return new Carpet(index, votes.score(index), votes.votes(index));
    }
}
