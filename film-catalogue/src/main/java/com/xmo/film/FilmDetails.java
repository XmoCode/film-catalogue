package com.xmo.film;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FilmDetails {

    private String id;

    private Map<String, Object> title;
    private Map<String, String> image;
    private String imageUrl;
    private Object runningTimeInMinutes;
    private Object numberOfEpisodes;
    private Object filmTitle;
    private Object titleType;
    private Object year;

    private Map<String, Object> ratings;
    private String rating;
    private int ratingCount;

    private List<String> genres;
    private String allGenres;

    private String releaseDate;
    private String releasedOn;

    private Map<String, String> plotOutline;
    private String synopsis;

    private Map<String, String> plotSummary;
    private String plot;

    @JsonIgnore
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getTitle() {
        return title;
    }

    public void setTitle(Map<String, Object> title) {
        this.title = title;
    }

    public Map<String, String> getImage() {
        return image;
    }

    public void setImage(Map<String, String> image) {
        this.image = image;
    }

    @JsonIgnore
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonIgnore
    public Object getRunningTimeInMinutes() {
        return runningTimeInMinutes;
    }

    public void setRunningTimeInMinutes(Object runningTimeInMinutes) {
        this.runningTimeInMinutes = runningTimeInMinutes;
    }

    @JsonIgnore
    public Object getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(Object filmTitle) {
        this.filmTitle = filmTitle;
    }

    @JsonIgnore
    public Object getTitleType() {
        return titleType;
    }

    public void setTitleType(Object titleType) {
        this.titleType = titleType;
    }

    @JsonIgnore
    public Object getYear() {
        return year;
    }

    public void setYear(Object year) {
        this.year = year;
    }

    public Map<String, Object> getRatings() {
        return ratings;
    }

    public void setRatings(Map<String, Object> ratings) {
        this.ratings = ratings;
    }

    @JsonIgnore
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @JsonIgnore
    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Map<String, String> getPlotOutline() {
        return plotOutline;
    }

    public void setPlotOutline(Map<String, String> plotOutline) {
        this.plotOutline = plotOutline;
    }

    @JsonIgnore
    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Map<String, String> getPlotSummary() {
        return plotSummary;
    }

    public void setPlotSummary(Map<String, String> plotSummary) {
        this.plotSummary = plotSummary;
    }

    @JsonIgnore
    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    @JsonIgnore
    public String getAllGenres() {
        return allGenres;
    }

    public void setAllGenres(String allGenres) {
        this.allGenres = allGenres;
    }

    @JsonIgnore
    public String getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(String releasedOn) {
        this.releasedOn = releasedOn;
    }

    @JsonIgnore
    public Object getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(Object numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

}
