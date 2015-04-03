package votes;

import java.util.Objects;

public class Carpet implements Comparable<Carpet> {
  public final int index;
  public final int score;
  public final int votes;

  public Carpet(int index, int score, int votes) {
    this.index = index;
    this.score = score;
    this.votes = votes;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Carpet) {
      Carpet other = (Carpet) o;
      return (index == other.index) && (score == other.score) && (votes == other.votes);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(index, score, votes);
  }

  @Override
  public int compareTo(Carpet other) {
    int diff = Integer.compare(score, other.score);
    if (diff != 0) {
      return diff;
    }

    diff = Integer.compare(votes, other.votes);
    if (diff != 0) {
      return diff;
    }

    diff = Integer.compare(index, other.index);
    if (diff != 0) {
      return diff;
    }

    return 0;
  }
}
