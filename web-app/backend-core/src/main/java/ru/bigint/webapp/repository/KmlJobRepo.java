package ru.bigint.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bigint.webapp.entity.KmlJob;

import java.util.List;
import java.util.Optional;

@Repository
public interface KmlJobRepo extends JpaRepository<KmlJob, String> {

    Optional<KmlJob> findFirstByStatusOrderByCreatedAtDesc(int status);

    @Query(value="SELECT id, created_at, completed_at, params, status, '' as result_kml FROM kml_jobs ORDER BY created_at DESC", nativeQuery = true)
    List<KmlJob> listAllOrderedByDate();

}