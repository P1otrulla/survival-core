package pl.crafthype.core.feature.weding.request;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WeddingRequestService {

    private final Map<UUID, WeddingRequest> requests = new HashMap<>();

    public WeddingRequest findRequest(UUID target) {
        return this.getRequests()
            .stream()
            .filter(request -> request.receiver().equals(target))
            .findFirst()
            .orElse(null);
    }

    public void addRequest(WeddingRequest weddingRequest) {
        this.requests.put(weddingRequest.sender(), weddingRequest);
    }

    public void removeRequest(UUID sender) {
        this.requests.remove(sender);
    }

    public Collection<WeddingRequest> getRequests() {
        return Collections.unmodifiableCollection(this.requests.values());
    }

    public boolean existsRequestBetween(UUID playerA, UUID playerB) {
        WeddingRequest request = this.findRequest(playerA);

        if (request != null) {
            return request.sender().equals(playerB);
        }

        return false;
    }
}
