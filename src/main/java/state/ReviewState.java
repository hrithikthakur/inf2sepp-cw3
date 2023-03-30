package state;

import model.Review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewState implements IReviewState{
    private List<Review> reviews;

    public ReviewState() {

        reviews = new ArrayList<>();
    }

    public ReviewState(IReviewState other) {
        ReviewState otherImpl = (ReviewState) other;
        reviews = new ArrayList<>(otherImpl.reviews);
    }

    public List<Review> findReviewsByEventTitle(String eventTitle) {
        List<Review> result = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getEventTitle().equals(eventTitle)) {
                result.add(review);
            }
        }
        return result;
    }

    public Map<String, Object> getReviewState() {
        Map<String, Object> result = new HashMap<>();
        result.put("reviews", reviews);
        return result;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }
}
