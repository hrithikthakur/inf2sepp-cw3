package command;

import controller.Context;
import model.Review;
import view.IView;

import java.util.List;

public class ListEventReviewsCommand implements ICommand<List<Review>> {
    private List<Review> reviewsResult;
    private String eventTitle;

    public ListEventReviewsCommand(String eventTitle) {
        this.eventTitle = eventTitle;
    }


    //both staff  and consumer can access it and, providing eventTitle. The system merges reviews from all
    //events with the same title and returns them in a list.

    @Override
    public void execute(Context context, IView view) {
        //TODO consider all the failure cases, and display the appropriate message
        reviewsResult = context.getReviewState().findReviewsByEventTitle(eventTitle);
    }

    @Override
    public List<Review> getResult() {
        return reviewsResult;
    }
}