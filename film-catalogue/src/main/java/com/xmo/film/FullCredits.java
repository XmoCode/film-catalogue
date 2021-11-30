package com.xmo.film;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FullCredits {

    private String id;
    private List<CastObject> cast;
    private Map<String, List<Director>> crew;
    private String stars;
    private String director;
    private String writers;

    @JsonIgnore
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CastObject> getCast() {
        return cast;
    }

    public void setCast(List<CastObject> cast) {
        this.cast = cast;
    }

    @JsonIgnore
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @JsonIgnore
    public String getWriters() {
        return writers;
    }

    public void setWriters(String writers) {
        this.writers = writers;
    }

    @JsonIgnore
    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public Map<String, List<Director>> getCrew() {
        return crew;
    }

    public void setCrew(Map<String, List<Director>> crew) {
        this.crew = crew;
    }

}
