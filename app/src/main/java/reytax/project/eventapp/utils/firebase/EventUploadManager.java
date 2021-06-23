package reytax.project.eventapp.utils.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class EventUploadManager {

    private static String eventType, title, participantsNumber, description, country, city, address, contact, date;


    public static void initialization() {
        eventType = "";
        title = "";
        participantsNumber = "";
        description = "";
        country = "";
        city = "";
        address = "";
        contact = "";
        date = "";
    }

    public static void setDataFirstFragment(String eventTypeLocal, String titleLocal, String participantsLimitLocal) {
        eventType = eventTypeLocal;
        title = titleLocal;
        participantsNumber = participantsLimitLocal;
    }

    public static void setDataSecondFragment(String descriptionLocal) {
        description = descriptionLocal;
    }

    public static void setDataThirdFragment(String countryLocal, String cityLocal, String addressLocal, String contactLocal, String dataLocal) {
        country = countryLocal;
        city = cityLocal;
        address = addressLocal;
        contact = contactLocal;
        date = dataLocal;
    }

    public static void uploadEventToFirebase(){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Map<String,Object> event = new HashMap<>();
        DocumentReference documentReference = firebaseFirestore.collection("events").document(UUID.randomUUID().toString());
        event.put("eventType",eventType);
        event.put("title",title);
        event.put("participantsNumber",participantsNumber);
        event.put("description",description);
        event.put("country",country);
        event.put("city",city);
        event.put("address",address);
        event.put("contact",contact);
        event.put("date",date);
        event.put("uid", FirebaseInitialization.getFirebaseUser().getUid());
        event.put("username", UserDataManager.getUsernamelocal());
        event.put("creationDate", Calendar.getInstance().getTime());
        documentReference.set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public static String getCity() {
        return city;
    }

    public static String getAddress() {
        return address;
    }

    public static String getContact() {
        return contact;
    }

    public static String getDate() {
        return date;
    }


}
