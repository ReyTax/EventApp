package reytax.project.eventapp.utils.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class EventUploadManager {

    private static String eventType, title, participantsNumber, description, country, state, city, address, contact, dateStart, dateEnd;
    private static int currentParticipantsNumber;


    public static void initialization() {
        eventType = "";
        title = "";
        participantsNumber = "";
        currentParticipantsNumber = 0;
        description = "";
        country = "";
        state = "";
        city = "";
        address = "";
        contact = "";
        dateStart = "";
        dateEnd = "";
    }

    public static void setDataFirstFragment(String eventTypeLocal, String titleLocal, String participantsLimitLocal) {
        eventType = eventTypeLocal;
        title = titleLocal;
        participantsNumber = participantsLimitLocal;
    }

    public static void setDataSecondFragment(String descriptionLocal) {
        description = descriptionLocal;
    }

    public static void setDataThirdFragment(String countryLocal, String stateLocal, String cityLocal, String addressLocal, String contactLocal, String dataStartLocal, String dataEndLocal) {
        country = countryLocal;
        state = stateLocal;
        city = cityLocal;
        address = addressLocal;
        contact = contactLocal;
        dateStart = dataStartLocal;
        dateEnd = dataEndLocal;
    }

    public static void uploadEventToFirebase(){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Map<String,Object> event = new HashMap<>();
        String uid = UUID.randomUUID().toString();
        DocumentReference documentReferenceEvents = firebaseFirestore.collection("events").document(uid);
        firebaseFirestore.collection("users").document(FirebaseInitialization.getFirebaseUser().getUid()).update("eventscount", FieldValue.increment(1));
        event.put("eventType",eventType);
        event.put("title",title);
        event.put("participantsNumber",participantsNumber);
        event.put("currentParticipantsNumber",0);
        event.put("description",description);
        event.put("country",country);
        event.put("state",state);
        event.put("city",city);
        event.put("address",address);
        event.put("contact",contact);
        event.put("dateStart",dateStart);
        event.put("dateEnd",dateEnd);
        event.put("uid", FirebaseInitialization.getFirebaseUser().getUid());
        event.put("username", UserDataManager.getUsernamelocal());
        event.put("creationDate", Calendar.getInstance().getTime());
        event.put("uidEvent", uid);
        documentReferenceEvents.set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public static String getEventType() {
        return eventType;
    }

    public static String getTitle() {
        return title;
    }

    public static String getParticipantsNumber() {
        return participantsNumber;
    }

    public static String getDescription() {
        return description;
    }

    public static String getCountry() {
        return country;
    }

    public static String getState() {
        return state;
    }

    public static String getCity() {
        return city;
    }

    public static String getAddress() {
        return address;
    }

    public static String getContact() {
        return contact;
    }

    public static String getDateStart() {
        return dateStart;
    }

    public static String getDateEnd() {
        return dateEnd;
    }
}
