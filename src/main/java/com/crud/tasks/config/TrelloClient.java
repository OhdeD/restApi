package com.crud.tasks.config;

        import com.crud.tasks.domain.TrelloBoardDto;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.stereotype.Component;
        import org.springframework.web.client.RestTemplate;
        import org.springframework.web.util.UriComponentsBuilder;

        import javax.swing.text.html.Option;
        import java.net.URI;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;
        import java.util.Optional;

@Component
public class TrelloClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.username}")
    private String username;

    private URI prepareUri() {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + username + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name, id")
                .queryParam("lists", "all").build().encode().toUri();
        return url;

    }

    public List<TrelloBoardDto> getTrelloBoards() {
        TrelloBoardDto[] trelloBoards = restTemplate.getForObject(prepareUri(), TrelloBoardDto[].class);
        return  Arrays.asList(Optional.ofNullable(trelloBoards).orElse(new TrelloBoardDto[0]));
    }
}
