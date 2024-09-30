package pl.crafthype.core.feature.weding.request;

import java.time.Instant;
import java.util.UUID;

public class WeddingRequest {

    private final UUID sender;
    private final UUID receiver;

    private final Instant expirationDate = Instant.now().plusSeconds(80);

    private WeddingRequest(UUID sender, UUID receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public static WeddingRequest create(UUID sender, UUID receiver) {
        return new WeddingRequest(sender, receiver);
    }

    public UUID sender() {
        return this.sender;
    }

    public UUID receiver() {
        return this.receiver;
    }

    public Instant expirationDate() {
        return this.expirationDate;
    }
}
