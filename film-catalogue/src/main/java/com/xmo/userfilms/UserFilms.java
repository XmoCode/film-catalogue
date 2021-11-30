package com.xmo.userfilms;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

@Table(value = "films_by_user_and_filmId")
public class UserFilms {

    @PrimaryKey
    private UserFilmsPrimaryKey key;

    @Column("satus")
    @CassandraType(type = Name.TEXT)
    private String status;


    @Column("reaction")
    @CassandraType(type = Name.TEXT)
    private String reaction;


    @Column("rating")
    @CassandraType(type = Name.INT)
    private int rating;


    public UserFilmsPrimaryKey getKey() {
        return key;
    }


    public void setKey(UserFilmsPrimaryKey key) {
        this.key = key;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getReaction() {
        return reaction;
    }


    public void setReaction(String reaction) {
        this.reaction = reaction;
    }


    public int getRating() {
        return rating;
    }


    public void setRating(int rating) {
        this.rating = rating;
    }

    





    
    
}
