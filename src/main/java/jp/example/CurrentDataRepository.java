package jp.example;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Resistory interface for managing CurrentData entities in the database.
 */
public interface CurrentDataRepository extends JpaRepository<CurrentData, Long> {

}
