import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Prefix;
import votes.Carpet;
import votes.RankedCarpet;
import votes.Votes;

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
    int firstIndex;
    int secondIndex;
    do {
      firstIndex = randomIndex();
      secondIndex = randomIndex();
    } while (firstIndex == secondIndex);

    return new Carpet[]{carpet(firstIndex), carpet(secondIndex)};
  }

  @Get("top")
  public Map<Integer, RankedCarpet> top() {
    List<Carpet> all = new ArrayList<>();
    for (int i = 1; i <= Votes.MAX_CARPET; i++) {
      all.add(carpet(i));
    }

    Collections.sort(all);
    Collections.reverse(all);

    Map<Integer, RankedCarpet> ranked = new LinkedHashMap<>();
    int rank = 1;
    for (Carpet carpet : all) {
      ranked.put(carpet.index, new RankedCarpet(rank++, carpet));
    }
    return ranked;
  }

  private int randomIndex() {
    return 1 + random.nextInt(Votes.MAX_CARPET);
  }

  private Carpet carpet(int index) {
    return new Carpet(index, votes.score(index), votes.votes(index));
  }
}
