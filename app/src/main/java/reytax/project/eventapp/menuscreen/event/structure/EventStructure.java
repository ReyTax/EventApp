package reytax.project.eventapp.menuscreen.event.structure;

public class EventStructure {

    private String address;
    private String city;
    private String contact;
    private String country;
    private String date;
    private String description;
    private String eventType;
    private String participantsNumber;
    private String title;
    private String uid;
    private String username;

    private EventStructure() {
    }

    private EventStructure(String address, String city, String contact, String country, String date, String description, String eventType, String participantsNumber, String title) {
        this.address = address;
        this.city = city;
        this.contact = contact;
        this.country = country;
        this.date = date;
        this.description = description;
        this.eventType = eventType;
        this.participantsNumber = participantsNumber;
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(String participantsNumber) {
        this.participantsNumber = participantsNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
