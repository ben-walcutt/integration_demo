package co.newlabs.client.tracking;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TrackingClient {
    private final String url;
    private final RestTemplate restTemplate;

    public TrackingClient(@Value("${urls.tracking}") String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public TrackingDTO getTrackingDetailsByOrderId(final long id) {
        ResponseEntity<TrackingDTO> response = restTemplate.getForEntity(url + id, TrackingDTO.class);
        return response.getBody();
    }
}
