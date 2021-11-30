package com.xmo.home;

import java.util.List;
import java.util.stream.Collectors;

import com.xmo.user.FilmsByUser;
import com.xmo.user.FilmsByUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    FilmsByUserRepository filmsByUserRepository;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {

        if (principal == null || principal.getAttribute("login") == null)
            return "index";

        String userId = principal.getAttribute("login");

        Slice<FilmsByUser> filmsSlice = filmsByUserRepository.findAllById(userId, CassandraPageRequest.of(0, 200));

        List<FilmsByUser> filmsByUser = filmsSlice.getContent();

        filmsByUser = filmsByUser.stream().distinct().collect(Collectors.toList());

        model.addAttribute("films", filmsByUser);

        return "home";
    }

}
