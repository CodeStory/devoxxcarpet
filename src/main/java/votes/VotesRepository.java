package votes;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class VotesRepository {
  private final List<Vote> allVotes = new CopyOnWriteArrayList<>();

  public int size() {
    return allVotes.size();
  }

  public void vote(int winner, int looser) {
    allVotes.add(new Vote(winner, looser));
  }
}
