package com.crud.tasks.trello.client;

        import com.crud.tasks.domain.TrelloBoardDto;
        import com.crud.tasks.domain.TrelloCardDto;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.stereotype.Component;
        import org.springframework.web.client.RestClientException;
        import org.springframework.web.client.RestTemplate;
        import org.springframework.web.util.UriComponentsBuilder;

        import java.net.URI;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;
        import java.util.Optional;

@Component
public class TrelloClient {
    private static final Logger LOGGER = LoggerFactory.getLogger (TrelloClient.class);
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


    private URI prepareUriToGetBoards() {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + username + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name, id")
                .queryParam("lists", "all").build().encode().toUri();
        return url;
    }

    public List<TrelloBoardDto> getTrelloBoards() {
        try {
            TrelloBoardDto[] trelloBoards = restTemplate.getForObject(prepareUriToGetBoards(), TrelloBoardDto[].class);
            return Arrays.asList(Optional.ofNullable(trelloBoards).orElse(new TrelloBoardDto[0]));
        }catch (RestClientException e){
            LOGGER.error(e.getMessage(),e);
            return new ArrayList<>();
        }
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto){
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId()).build().encode().toUri();
        return restTemplate.postForObject(url, null, CreatedTrelloCard.class);

    }


}
