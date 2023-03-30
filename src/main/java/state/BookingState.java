package state;

import model.Booking;
import model.Consumer;
import model.Event;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * {@link BookingState} is a concrete implementation of {@link IBookingState}.
 */
public class BookingState implements IBookingState {
    private List<Booking> bookings;
    private long nextBookingNumber;

    /**
     * Create a new BookingState that keeps track of the next booking number it will generate (starting from 1 and
     * incrementing by 1 each time a new booking number is needed), and an empty list of bookings
     */
    public BookingState() {
        nextBookingNumber = 1;
        bookings = new LinkedList<>();
    }

    /**
     * Copy constructor to make a deep copy of another BookingState instance
     *
     * @param other instance to copy
     */
    public BookingState(IBookingState other) {
        BookingState otherImpl = (BookingState) other;
        nextBookingNumber = otherImpl.nextBookingNumber;
        bookings = new LinkedList<>(otherImpl.bookings);
    }

    @Override
    public Booking findBookingByNumber(long bookingNumber) {
        return bookings.stream()
                .filter(booking -> booking.getBookingNumber() == bookingNumber)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Booking> findBookingsByEventNumber(long eventNumber) {
        return bookings.stream()
                .filter(booking -> booking.getEvent().getEventNumber() == eventNumber)
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findBookingsByCustomer(Consumer consumer) {
        return bookings.stream()
                .filter(booking -> booking.getBooker().equals(consumer))
                .collect(Collectors.toList());
    }

    @Override
    public Booking createBooking(Consumer booker, Event event, int numTickets) {
        long bookingNumber = nextBookingNumber;
        nextBookingNumber++;
        Booking booking = new Booking(bookingNumber,
                booker,
                event,
                numTickets,
                LocalDateTime.now());
        bookings.add(booking);
        return booking;
    }


    public Map<String, Object> getBookingState(){
        Map<String, Object> state = new HashMap<>();
        state.put("Bookings", bookings);
        state.put("Next Booking Number", nextBookingNumber);
        return state;
    }
}
