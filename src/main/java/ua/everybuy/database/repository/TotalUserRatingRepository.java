package ua.everybuy.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.everybuy.database.entity.TotalUserRating;

@Repository
public interface TotalUserRatingRepository extends JpaRepository<TotalUserRating, Long> {
}
