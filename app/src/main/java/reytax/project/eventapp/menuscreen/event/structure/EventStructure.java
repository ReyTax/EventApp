package reytax.project.eventapp.menuscreen.event.structure;

public class EventStructure {

    private String address;
    private String city;
    private String contact;
    private String country;
    private String dateStart;
    private String dateEnd;
    private String description;
    private String eventType;
    private String participantsNumber;
    private int currentParticipantsNumber;
    private String title;
    private String uid;
    private String uidEvent;
    private String username;
    private String state;

    private EventStructure() {
    }

    private EventStructure(String address, String city, String contact, String country, String state, String dateStart, String dateEnd, String description, String eventType, String participantsNumber, int currentParticipantsNumber, String title, String uidEvent) {
        this.address = address;
        this.city = city;
        this.contact = contact;
        this.country = country;
        this.state = state;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.description = description;
        this.eventType = eventType;
        this.participantsNumber = participantsNumber;
        this.currentParticipantsNumber = currentParticipantsNumber;
        this.title = title;
        this.uidEvent = uidEvent;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getCurrentParticipantsNumber() {
        return currentParticipantsNumber;
    }

    public void setCurrentParticipantsNumber(int currentParticipantsNumber) {
        this.currentParticipantsNumber = currentParticipantsNumber;
    }

    public String getUidEvent() {
        return uidEvent;
    }

    public void setUidEvent(String uidEvent) {
        this.uidEvent = uidEvent;
    }
}
