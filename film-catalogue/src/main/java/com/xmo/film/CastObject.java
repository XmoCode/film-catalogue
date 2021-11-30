package com.xmo.film;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CastObject {

    private String name;
    private List<String> characters;
    private String roles;
    private String nameAndRole;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }

    @JsonIgnore
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @JsonIgnore
    public String getNameAndRole() {
        return nameAndRole;
    }

    public void setNameAndRole(String nameAndRole) {
        this.nameAndRole = nameAndRole;
    }

}
