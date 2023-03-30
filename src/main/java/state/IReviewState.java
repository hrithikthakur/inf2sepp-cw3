package state;

import model.Review;

import java.util.List;
import java.util.Map;


public interface IReviewState {
    List<Review> findReviewsByEventTitle(String eventTitle);

    Map<String, Object> getReviewState();

    void addReview(Review review);
}
