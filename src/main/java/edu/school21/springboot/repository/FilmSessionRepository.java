package edu.school21.springboot.repository;


import edu.school21.springboot.model.FilmSession;
import edu.school21.springboot.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmSessionRepository extends JpaRepository<FilmSession, Long> {

	List<FilmSession> findAllByHall(Hall hall);

	@Query("SELECT session " +
			"FROM FilmSession session JOIN session.film film " +
			"WHERE LOWER(film.title) LIKE %:loweSearchStr% " +
			"ORDER BY session.sessionDateTimeFrom ASC ")
	List<FilmSession> findAllByFilmTitle(@Param("loweSearchStr") String loweSearchStr);

}
