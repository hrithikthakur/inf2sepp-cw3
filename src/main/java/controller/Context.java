package controller;

//import external.MapSystem;

import external.MockPaymentSystem;
import external.PaymentSystem;
import state.*;

import java.io.*;

/**
 * {@link Context} is a wrapper around the entire app state. It keeps references to the internal states:
 * {@link IUserState}, {@link IEventState}, and {@link IBookingState}.
 * The state classes are kept as interfaces, so that other classes using the context cannot depend on their
 * implementation details.
 */
public class Context implements AutoCloseable {
    private final String orgName;
    private final String orgAddress;
    private final String orgEmail;
    private final String orgSecret;
//    private final MapSystem mapSystem;
    private final PaymentSystem paymentSystem;
    private final IUserState userState;
    private final IEventState eventState;
    private final IBookingState bookingState;
    private final IReviewState reviewState;

    /**
     * Initialises all the state members with default constructors of the concrete implementations:
     * {@link UserState}, {@link EventState}, and {@link BookingState}.
     * @param orgName Name of the organisation that owns this application instance
     * @param orgAddress Address of the organisation that owns this application instance
     * @param orgEmail Email address of the organisation, used for payment transactions
     * @param orgSecret Secret passcode only known to organisation staff, required for registering new Staff accounts
     */
    public Context(String orgName, String orgAddress, String orgEmail, String orgSecret) {
        this.orgName = orgName;
        this.orgAddress = orgAddress;
        this.orgEmail = orgEmail;
        this.orgSecret = orgSecret;
        this.paymentSystem = new MockPaymentSystem();
//        this.mapSystem = new OfflineMapSystem();
        this.userState = new UserState();
        this.eventState = new EventState();
        this.bookingState = new BookingState();
        this.reviewState = new ReviewState();
    }

    /**
     * Copy constructor, makes a deep copy of another {@link Context}.
     *
     * @param other context to copy
     */
    public Context(Context other) {
        // Strings are immutable in Java, hence no need to re-create a copy
        orgName = other.orgName;
        orgAddress = other.orgAddress;
        orgEmail = other.orgEmail;
        orgSecret = other.orgSecret;
        paymentSystem = new MockPaymentSystem((MockPaymentSystem) other.paymentSystem);
//        mapSystem = new MapSystem(other.mapSystem);
        userState = new UserState(other.userState);
        eventState = new EventState(other.eventState);
        bookingState = new BookingState(other.bookingState);
        reviewState = new ReviewState(other.reviewState);

    }

    public String getOrgName() { return orgName; }

    public String getOrgAddress() { return orgAddress; }

    public String getOrgEmail() {
        return orgEmail;
    }

    public String getOrgSecret() { return orgSecret; }

    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    public IUserState getUserState() {
        return userState;
    }

    public IBookingState getBookingState() {
        return bookingState;
    }

    public IEventState getEventState() {
        return eventState;
    }

//    public MapSystem getMapSystem() {
//        return mapSystem;
//    }

    public IReviewState getReviewState() {
        return reviewState;
    }

    public void saveAppState(String filename) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(new AppState(userState, bookingState, eventState, reviewState));
        }
    }

    public void loadAppState(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(filename);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        AppState appState = (AppState) objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
        bookingState = new BookingState(appState.getBookingState());
        eventState = appState.getEventState();
        reviewState = appState.getReviewState();
    }
    
    

    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     */
    @Override
    public void close() throws Exception {
        paymentSystem.close();
//        mapSystem.close();
    }
}
