package com.xmo.user;

import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmsByUserRepository extends CassandraRepository<FilmsByUser, String> {

    Slice<FilmsByUser> findAllById(String id, CassandraPageRequest cassandraPageRequest);

}
