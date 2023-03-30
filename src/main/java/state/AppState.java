package state;

public class AppState {
    private IUserState userState;
    private IBookingState bookingState;
    private IEventState eventState;
    private IReviewState reviewState;

    public AppState(IUserState userState, IBookingState bookingState, IEventState eventState, IReviewState reviewState) {
        this.userState = userState;
        this.bookingState = bookingState;
        this.eventState = eventState;
        this.reviewState = reviewState;
    }

    public IUserState getUserState() {
        return userState;
    }

    public void setUserState(IUserState userState) {
        this.userState = userState;
    }

    public IBookingState getBookingState() {
        return bookingState;
    }

    public void setBookingState(IBookingState bookingState) {
        this.bookingState = bookingState;
    }

    public IEventState getEventState() {
        return eventState;
    }

    public void setEventState(IEventState eventState) {
        this.eventState = eventState;
    }

    public IReviewState getReviewState() {
        return reviewState;
    }

    public void setReviewState(IReviewState reviewState) {
        this.reviewState = reviewState;
    }

   
}
