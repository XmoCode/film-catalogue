package com.xmo.userfilms;

import org.springframework.stereotype.Repository;
import org.springframework.data.cassandra.repository.CassandraRepository;

@Repository
public interface UserFilmsRepository extends CassandraRepository <UserFilms, UserFilmsPrimaryKey> {
    
}
