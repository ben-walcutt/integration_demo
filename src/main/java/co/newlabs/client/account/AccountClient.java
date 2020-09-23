package co.newlabs.client.account;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountClient {
    private final String url;
    private final RestTemplate restTemplate;

    public AccountClient(@Value("${urls.account}") String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public AccountDTO getAccountById(final long id) {
        ResponseEntity<AccountDTO> response = restTemplate.getForEntity(url + id, AccountDTO.class);
        return response.getBody();
    }
}
