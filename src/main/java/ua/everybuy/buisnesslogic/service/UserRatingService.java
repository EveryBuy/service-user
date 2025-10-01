package ua.everybuy.buisnesslogic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.everybuy.database.entity.UserRating;
import ua.everybuy.database.repository.UserRatingRepository;
import ua.everybuy.errorhandling.exception.impl.UserRatingAlreadyExistsException;
import ua.everybuy.routing.model.request.UserRatingRequest;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRatingService {
    private final TotalUserRatingService totalUserRatingService;
    private final UserRatingRepository userRatingRepository;

    public UserRating addUserRating(Principal principal, UserRatingRequest userRatingRequest) {
        long raterId = extractUserId(principal);
        long userId = userRatingRequest.userId();
        short userRating = userRatingRequest.userRating();
        Optional<UserRating> ratingOptional = findUserRatingByUserIdAndRaterId(userId, raterId);
        return ratingOptional.map(rating -> updateUserRating(rating, userRating))
                .orElseGet(() -> createUserRating(userId, raterId, userRating));
    }

    private Optional<UserRating> findUserRatingByUserIdAndRaterId(long userId, long raterId) {
        return userRatingRepository.findUserRatingByUserIdAndRaterId(userId, raterId);
    }

    private UserRating createUserRating(long userId, long raterId, short userRating){
        UserRating newUserRating = userRatingRepository.save(new UserRating(userId, raterId, userRating));
        totalUserRatingService.addTotalUserRating(userId, userRating);
        return newUserRating;
    }

    private UserRating updateUserRating(UserRating userRating, short newRating){
        short oldRating = userRating.getRating();
        userRating.setRating(newRating);
        UserRating updatedRating = userRatingRepository.save(userRating);
        totalUserRatingService.refreshTotalUserRating(userRating.getUserId(), oldRating, newRating);
        return updatedRating;
    }

    private long extractUserId(Principal principal) {
        return Long.parseLong(principal.getName());
    }
}
