package ua.everybuy.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TotalUserRating {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "rating_sum", nullable = false)
    @Getter
    @Setter
    private Long ratingSum;

    @Column(name = "rating_count", nullable = false)
    @Getter
    @Setter
    private Long ratingCount;

    @Column(name = "average_rating")
    @Getter
    @Setter
    private Double averageRating;
}
