package com.xmo.film;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.stream.Collectors;

import com.xmo.userfilms.UserFilms;
import com.xmo.userfilms.UserFilmsPrimaryKey;
import com.xmo.userfilms.UserFilmsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Controller
public class FilmController {

    private final WebClient webClient1;
    private final WebClient webClient2;

    @Autowired
    UserFilmsRepository userFilmsRepository;

    public FilmController(WebClient.Builder webClientBuilder1, WebClient.Builder webClientBuilder2) {
        this.webClient1 = webClientBuilder1
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build())
                .baseUrl("https://imdb8.p.rapidapi.com/title/get-overview-details")
                .defaultHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .defaultHeader("x-rapidapi-key", "442115e7b9msha768466b0ef82e3p1cf428jsna70d17536efa").build();

        this.webClient2 = webClientBuilder2
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build())
                .baseUrl("https://imdb8.p.rapidapi.com/title/get-full-credits")
                .defaultHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .defaultHeader("x-rapidapi-key", "442115e7b9msha768466b0ef82e3p1cf428jsna70d17536efa").build();

    }

    @GetMapping(value = "/film/{filmId}")
    public String getFilmOverview(@PathVariable String filmId, Model model,
            @AuthenticationPrincipal OAuth2User principal) {
        Mono<FilmDetails> resultMono = this.webClient1.get().uri("?tconst={filmId}", filmId).retrieve()
                .bodyToMono(FilmDetails.class);

        Optional<FilmDetails> optional = resultMono.blockOptional();
        if (!optional.isPresent())
            return "title-not-found";

        FilmDetails filmDetails = optional.get();

        filmDetails.setId(filmId);
        if (filmDetails.getTitle().get("runningTimeInMinutes") != null)
            filmDetails.setRunningTimeInMinutes(filmDetails.getTitle().get("runningTimeInMinutes"));
        else
            filmDetails.setRunningTimeInMinutes("unknown");

        filmDetails.setFilmTitle(filmDetails.getTitle().get("title"));
        filmDetails.setTitleType(filmDetails.getTitle().get("titleType"));
        filmDetails.setYear(filmDetails.getTitle().get("year"));

        if (filmDetails.getRatings().get("rating") != null)
            filmDetails.setRating(filmDetails.getRatings().get("rating").toString());

        if (filmDetails.getRatings().get("ratingCount") != null)
            filmDetails.setRatingCount(Integer.parseInt(filmDetails.getRatings().get("ratingCount").toString()));

        if (filmDetails.getGenres() == null)
            return "details-not-found";
        filmDetails.setAllGenres(filmDetails.getGenres().stream().toString());

        if (filmDetails.getReleaseDate() != null)
            filmDetails.setReleasedOn(filmDetails.getReleaseDate());
        else
            filmDetails.setReleasedOn("unknown");

        if (filmDetails.getTitle().get("numberOfEpisodes") != null)
            filmDetails.setNumberOfEpisodes(" " + filmDetails.getTitle().get("numberOfEpisodes") + " episodes");
        else
            filmDetails.setNumberOfEpisodes("");

        if (filmDetails.getPlotOutline() != null)
            filmDetails.setSynopsis(filmDetails.getPlotOutline().get("text"));

        if (filmDetails.getPlotSummary() != null)
            filmDetails.setPlot(filmDetails.getPlotSummary().get("text"));
        else
            filmDetails.setPlot("unknown");

        if (filmDetails.getTitle().get("image") != null) {
            @SuppressWarnings("unchecked")
            Map<String, String> imageLink = (Map<String, String>) filmDetails.getTitle().get("image");
            filmDetails.setImageUrl(imageLink.get("url"));
        }

        else
            filmDetails.setImageUrl("/images/no-image.png");

        model.addAttribute("film", filmDetails);

        // 2nd call ..............................................................
        Mono<FullCredits> creditstMono = this.webClient2.get().uri("?tconst={filmId}", filmId).retrieve()
                .bodyToMono(FullCredits.class);

        Optional<FullCredits> optionalCredits = creditstMono.blockOptional();
        if (!optionalCredits.isPresent())
            return "title-not-found";

        FullCredits fullCredits = optionalCredits.get();

        fullCredits.setId(filmId);

        if (fullCredits.getCast() != null) {
            List<CastObject> cast = fullCredits.getCast().stream()
                    .limit(6).map(obj -> {
                        StringBuilder roles = new StringBuilder();

                        if (obj.getCharacters() != null) {
                            obj.getCharacters().forEach(c -> roles.append(c).append(", "));

                            obj.setRoles("( " + roles.toString().replaceAll(", $", "") + " )");
                        }

                    else {
                            obj.setRoles("");
                        }
                        obj.setNameAndRole(obj.getName() + " " + obj.getRoles());
                        return obj;
                    }).collect(Collectors.toList());

            String stars = cast.stream()
                    .map(obj -> obj.getNameAndRole().toString()).collect(Collectors.joining(" • "));

            fullCredits.setStars(stars);
        }

        if (fullCredits.getCrew().get("director") != null) {
            List<Director> dList = fullCredits.getCrew().get("director");
            String directors = dList.stream().map(obj -> obj.getName()).collect(Collectors.joining(" • "));

            fullCredits.setDirector(directors.toString());

        }

        if (fullCredits.getCrew().get("writer") != null) {
            String writers = fullCredits.getCrew().get("writer").stream().map(obj -> {

                StringBuilder nameJob = new StringBuilder();

                if (obj.getJob() != null)
                    nameJob.append(obj.getName() + " (" + obj.getJob() + ")");

                else
                    nameJob.append(obj.getName());

                return nameJob.toString();

            }).collect(Collectors.joining(" • "));

            fullCredits.setWriters(writers);
        }

        model.addAttribute("credits", fullCredits);

        // check if user is logged in - if yes extract & pass user id to the form in
        // film page and show form fields

        if (principal != null && principal.getAttribute("login") != null) {
            String userId = principal.getAttribute("login");
            model.addAttribute("loginId", userId);

            UserFilmsPrimaryKey key = new UserFilmsPrimaryKey();
            key.setUserId(userId);
            key.setFilmId(filmId);

            // the form will recive a UserFilms object with data or empty one
            Optional<UserFilms> userFilms = userFilmsRepository.findById(key);
            if (userFilms.isPresent())
                model.addAttribute("userFilms", userFilms.get());

            else
                model.addAttribute("userFilms", new UserFilms());

        }

        return "film";

    }

}
