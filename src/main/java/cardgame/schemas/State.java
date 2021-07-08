package cardgame.schemas;

import lombok.ToString;

@ToString
public enum State {
    PENDING, IN_PROGRESS, TIE_BREAKER, OVER
}
