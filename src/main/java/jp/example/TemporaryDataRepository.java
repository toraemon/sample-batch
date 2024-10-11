package jp.example;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing TemporaryData entities in the database.
 */
public interface TemporaryDataRepository extends JpaRepository<TemporaryData, Long> {

}
