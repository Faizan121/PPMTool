package io.faizanUddin.ppmtool.repositories;

import io.faizanUddin.ppmtool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {
}
