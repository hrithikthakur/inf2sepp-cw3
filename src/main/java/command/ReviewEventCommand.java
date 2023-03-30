package command;

import controller.Context;
import model.BookingStatus;
import model.Consumer;
import model.Review;
import view.IView;

import java.time.LocalDateTime;
import java.util.Map;

public class ReviewEventCommand implements ICommand<Review> {
    private Review reviewResult;
    private long eventNumber;
    private String content;

    public ReviewEventCommand(long eventNumber, String content) {
        this.eventNumber = eventNumber;
        this.content = content;
        this.reviewResult = null;
    }


    @Override
    public void execute(Context context, IView view) {
        if (context.getEventState().findEventByNumber(eventNumber) != null) {
            view.displayFailure(
                    "ReviewEventCommand",
                    LogStatus.REVIEW_EVENT_EVENT_DOES_NOT_EXIST,
                    Map.of("eventNumber", eventNumber)
            );
            reviewResult = null;
        }
        else if (context.getEventState().findEventByNumber(eventNumber).getEndDateTime().isBefore(LocalDateTime.now())
) {
            view.displayFailure(
                    "ReviewEventCommand",
                    LogStatus.REVIEW_EVENT_EVENT_NOT_ENDED,
                    Map.of("eventNumber", eventNumber)
            );
            reviewResult = null;
        }
        else if (!context.getBookingState().findBookingsByCustomer(
                        (Consumer) context.getUserState().
                        getCurrentUser()).stream().
                anyMatch(booking -> booking.getEvent().
                        getEventNumber() == eventNumber && booking.getStatus() != BookingStatus.CancelledByConsumer))
        {
            view.displayFailure(
                    "ReviewEventCommand",
                    ReviewEventCommand.LogStatus.REVIEW_EVENT_CUSTOMER_HAS_NO_BOOKING,
                    Map.of("eventNumber", eventNumber)
            );
            reviewResult = null;
        }
        else {
            Review review = new Review(
                    content,
                    (Consumer) context.getUserState().getCurrentUser(),
                    context.getEventState().findEventByNumber(eventNumber)
            );

            context.getReviewState().addReview(review);
        }
        
    }

    @Override
    public Review getResult() {
        return reviewResult;
    }


    private enum LogStatus {
        REVIEW_EVENT_EVENT_DOES_NOT_EXIST,
        REVIEW_EVENT_EVENT_NOT_ENDED,
        REVIEW_EVENT_CUSTOMER_HAS_NO_BOOKING
    }
}