package votes;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public class Vote implements Serializable {
    public final int winner;
    public final int looser;

    public Vote(int winner, int looser) {
        this.winner = winner;
        this.looser = looser;
    }

    public byte[] toBytes() {
        return SerializationUtils.serialize(new Vote(winner, looser));
    }

    public static Vote parse(byte[] bytes) {
        return (Vote) SerializationUtils.deserialize(bytes);
    }
}
