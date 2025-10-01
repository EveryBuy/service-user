package ua.everybuy.buisnesslogic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.everybuy.database.entity.TotalUserRating;
import ua.everybuy.database.entity.UserRating;
import ua.everybuy.database.repository.TotalUserRatingRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TotalUserRatingService {
    private final TotalUserRatingRepository totalUserRatingRepository;

    public void addTotalUserRating(long userId, short userRating){
        updateOrCreateTotalUserRating(userId, userRating);
    }

    public void refreshTotalUserRating(long userId, short oldRating, short newRating){
        TotalUserRating ratingToUpdate = findTotalUserRatingByUserId(userId).get();
        ratingToUpdate.setRatingSum(ratingToUpdate.getRatingSum() - oldRating + newRating);
        ratingToUpdate.setAverageRating((double) ratingToUpdate.getRatingSum() / ratingToUpdate.getRatingCount());
        totalUserRatingRepository.save(ratingToUpdate);
    }

    private TotalUserRating updateOrCreateTotalUserRating(long userId, short userRating){
        Optional<TotalUserRating> totalUserRating = findTotalUserRatingByUserId((userId));
        return totalUserRating.map(rating ->
                updateTotalUserRating(rating, userRating)).orElseGet(() -> createTotalUserRating(userId, userRating));
    }

    private TotalUserRating createTotalUserRating(long userId, short userRating){
        return totalUserRatingRepository.save(new TotalUserRating(userId,
                (long) userRating,
                1L,
                (double) userRating));
    }

    private TotalUserRating updateTotalUserRating(TotalUserRating totalUserRating, short userRating){
        totalUserRating.setRatingCount(totalUserRating.getRatingCount() + 1);
        totalUserRating.setRatingSum(totalUserRating.getRatingSum() + userRating);
        totalUserRating.setAverageRating((double)totalUserRating.getRatingSum() / totalUserRating.getRatingCount());
        return totalUserRatingRepository.save(totalUserRating);
    }


    private Optional<TotalUserRating> findTotalUserRatingByUserId(long userId){
        return totalUserRatingRepository.findById(userId);
    }


}
