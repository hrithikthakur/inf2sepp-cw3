package model;
import java.time.LocalDateTime;

public class Review {
    private String content;
    private Consumer author;
    private Event event;
    private LocalDateTime creationDateTime;


    public Review(String content, Consumer author, Event event) {
        this.content = content;
        this.author = author;
        this.event = event;
        this.creationDateTime = LocalDateTime.now();
    }


    public String getEventTitle() {
        return event.getTitle();
    }


    public String getContent() {
        return content;
    }

    public Consumer getAuthor() {
        return author;
    }

    public Event getEvent() {
        return event;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }



    
}
