package com.xmo.search;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Controller
public class SearchController {

    private final WebClient webClient;

    public SearchController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build())
                .baseUrl("https://imdb8.p.rapidapi.com/title/find")
                .defaultHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .defaultHeader("x-rapidapi-key", "442115e7b9msha768466b0ef82e3p1cf428jsna70d17536efa").build();

    }

    @GetMapping(value = "/search")
    public String getSearchResults(@RequestParam String query, Model model) {

        if (query.isEmpty())
            return "title-not-found";

        Mono<SearchResult> resultsMono = this.webClient.get().uri("?q={query}", query).retrieve()
                .bodyToMono(SearchResult.class);

        Optional<SearchResult> optionalResult = resultsMono.blockOptional();
        if (!optionalResult.isPresent())
            return "title-not-found";
        SearchResult result = optionalResult.get();

        try {
            List<FilmSearchResult> films = result.getResults().stream()
                    .filter(obj -> obj.getId().contains("/title/"))
                    .filter(obj -> !(obj.getTitleType().equals("tvEpisode")
                            || obj.getTitleType().equals("podcastEpisode") || obj.getTitleType().equals("short")
                            || obj.getTitleType().equals("tvShort") || obj.getTitleType().equals("video")
                            || obj.getTitleType().equals("tvMovie") || obj.getTitleType().equals("musicVideo")))
                    .filter(obj -> obj.getYear() != null)
                    .map(obj -> {
                        obj.setId(obj.getId().replace("/title/", "").replace("/", ""));

                        if (obj.getImage() != null && obj.getImage().get("url") != null) {
                            String imageLink = obj.getImage().get("url");
                            obj.setImageUrl(imageLink);

                        }

                    else
                            obj.setImageUrl("/images/no-image.png");

                        return obj;
                    }).sorted((obj1, obj2) -> obj2.getYear().compareTo(obj1.getYear())).collect(Collectors.toList());

            model.addAttribute("searchResults", films);

            if (films.isEmpty())
                return "title-not-found";

        } catch (Throwable e) {
            e.printStackTrace();
            return "title-not-found";
        }

        return "search";

    }

}
