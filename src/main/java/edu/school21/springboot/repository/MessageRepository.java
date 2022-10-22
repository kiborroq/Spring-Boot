package edu.school21.springboot.repository;

import edu.school21.springboot.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	@Query(value = "SELECT * " +
			"FROM message m " +
			"WHERE m.film_id = :filmId " +
			"ORDER BY m.date_time_create DESC LIMIT :limit OFFSET :offset",
			nativeQuery = true)
	List<Message> findAllByFilm(@Param("filmId") Long filmId, @Param("offset") Integer offset, @Param("limit") Integer limit);

}
