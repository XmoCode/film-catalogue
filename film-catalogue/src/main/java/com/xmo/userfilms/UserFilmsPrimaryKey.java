package com.xmo.userfilms;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class UserFilmsPrimaryKey {

    @PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED )
    private String userId;


    @PrimaryKeyColumn(name = "film_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String filmId;


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getFilmId() {
        return filmId;
    }


    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    

    
}
