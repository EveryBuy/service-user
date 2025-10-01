package ua.everybuy.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "UserRating")
@Getter
@Setter
@NoArgsConstructor
public class UserRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "rater_id")
    private long raterId;

    @Column(name = "rating", nullable = false)
    private Short rating;

    public UserRating (long userId, long raterId, Short rating) {
        this.userId = userId;
        this.raterId = raterId;
        this.rating = rating;
    }
}
