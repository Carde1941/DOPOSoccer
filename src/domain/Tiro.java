package domain;

import java.io.Serializable;

public interface Tiro extends Serializable{
    void impulsar(Balon balon, int sentido);
}
