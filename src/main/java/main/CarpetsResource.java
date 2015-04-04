package main;

import model.Carpet;
import model.RankedCarpet;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Prefix;
import store.Votes;

import java.util.*;

@Prefix("/carpets")
public class CarpetsResource {
    private final Random random;
    private final Votes votes;

    public CarpetsResource(Random random, Votes votes) {
        this.random = random;
        this.votes = votes;
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

    @Get("top")
    public Map<Integer, RankedCarpet> top() {
        List<Carpet> all = new ArrayList<>();
        for (int i = 0; i < Votes.MAX_CARPET; i++) {
            all.add(carpet(i));
        }

        Collections.sort(all, (l, r) -> r.score - l.score);

        Map<Integer, RankedCarpet> ranked = new LinkedHashMap<>();
        int rank = 0;
        for (Carpet carpet : all) {
            ranked.put(carpet.index, new RankedCarpet(rank++, carpet));
        }
        return ranked;
    }

    private int randomIndex() {
        return random.nextInt(Votes.MAX_CARPET);
    }

    private Carpet carpet(int index) {
        return new Carpet(index, votes.score(index), votes.votes(index));
    }
}
