package com.xmo.userfilms;

import com.xmo.user.FilmsByUser;
import com.xmo.user.FilmsByUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserFilmsController {

    @Autowired
    UserFilmsRepository userFilmsRepository;

    @Autowired
    FilmsByUserRepository filmsByUserRepository;

    @PostMapping("/addUserFilm")
    public ModelAndView addFilmForUser(@RequestBody MultiValueMap<String, String> formData,
            @AuthenticationPrincipal OAuth2User principal) {

        if (principal == null || principal.getAttribute("login") == null)
            return null;

        // 1st job - fill up table that stores form inputs
        UserFilmsPrimaryKey key = new UserFilmsPrimaryKey();
        String userId = principal.getAttribute("login");
        key.setUserId(userId);
        String filmId = formData.getFirst("filmId");
        key.setFilmId(filmId);

        UserFilms userFilms = new UserFilms();
        userFilms.setKey(key);
        userFilms.setStatus(formData.getFirst("status"));
        userFilms.setReaction(formData.getFirst("reaction"));
        userFilms.setRating(Integer.parseInt(formData.getFirst("rating")));

        userFilmsRepository.save(userFilms);

        // 2nd job - fill up table that is a basis for Home page
        FilmsByUser filmsByUser = new FilmsByUser();
        filmsByUser.setId(userId);
        filmsByUser.setFilmId(filmId);
        filmsByUser.setStatus(formData.getFirst("status"));
        filmsByUser.setReaction(formData.getFirst("reaction"));
        filmsByUser.setRating(Integer.parseInt(formData.getFirst("rating")));
        filmsByUser.setTitle(formData.getFirst("title"));
        filmsByUser.setDirectors(formData.getFirst("director"));
        filmsByUser.setImageUrl(formData.getFirst("imageUrl"));

        filmsByUserRepository.save(filmsByUser);

        return new ModelAndView("redirect:/film/" + filmId);
    }

}
