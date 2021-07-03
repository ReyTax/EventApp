package reytax.project.eventapp.utils.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.StorageReference;

import reytax.project.eventapp.authentication.MainActivity;
import reytax.project.eventapp.authentication.RegisterActivity;
import reytax.project.eventapp.authentication.ResetPasswordActivity;

import java.util.HashMap;
import java.util.Map;

public abstract class UserDataManager {


    private static String firstnamelocal = "";
    private static String lastnamelocal = "";
    private static String countrylocal = "";
    private static String statelocal = "";
    private static String citylocal = "";
    private static String phonenumberlocal = "";
    private static String descriptionlocal = "";
    private static String profileimagelocal = "";
    private static int eventscountLocal = 0;
    private static byte[] bytesProfileImagelocal;
    private static String usernamelocal = "";
    private static String username = "";
    private static String firstname = "";
    private static String lastname = "";
    private static String country = "";
    private static String state = "";
    private static String city = "";
    private static String description = "";
    private static String profileimage = "";
    private static int eventscount = 0;
    private static byte[] bytesProfileImage;


    public static void loadLocalUserData() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("users").document(FirebaseInitialization.getFirebaseUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (!documentSnapshot.getData().isEmpty()) {
                    Map<String, Object> userData = documentSnapshot.getData();
                    setLocalProfileData(userData.get("username").toString(), userData.get("firstname").toString(), userData.get("lastname").toString(), userData.get("country").toString(), userData.get("state").toString(), userData.get("city").toString(), userData.get("phonenumber").toString(), userData.get("description").toString(), Integer.parseInt(userData.get("eventscount").toString()));
                    profileimagelocal = userData.get("profileimage").toString();
                    bytesProfileImagelocal = null;

                    if (profileimagelocal.equals("true")) {
                        StorageReference image = FirebaseInitialization.getStorageReference().child("images/" + FirebaseInitialization.getFirebaseUser().getUid());
                        final long ONE_MEGABYTE = 1024 * 1024;
                        image.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                bytesProfileImagelocal = bytes;

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }
                }
            }
        });
    }

    public static void incrementEventsCountLocal() {
        eventscountLocal++;
    }

    public static void loadUserData(String uid) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        System.out.println(uid);
        firebaseFirestore.collection("users").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (!documentSnapshot.getData().isEmpty()) {
                    Map<String, Object> userData = documentSnapshot.getData();
                    setProfileData(userData.get("username").toString(), userData.get("firstname").toString(), userData.get("lastname").toString(), userData.get("country").toString(), userData.get("state").toString(), userData.get("city").toString(), userData.get("description").toString(), Integer.parseInt(userData.get("eventscount").toString()));

                    profileimage = userData.get("profileimage").toString();
                    bytesProfileImage = null;

                    if (profileimage.equals("true")) {
                        StorageReference image = FirebaseInitialization.getStorageReference().child("images/" + uid);
                        final long ONE_MEGABYTE = 1024 * 1024;
                        image.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                bytesProfileImage = bytes;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }
                }
            }
        });
    }

    public static void setLocalProfileData(String usernamelocal, String firstnamelocal, String lastnamelocal, String countrylocal, String statelocal, String citylocal, String phonenumberlocal, String descriptionlocal, int eventsCountLocal) {
        UserDataManager.usernamelocal = usernamelocal;
        UserDataManager.firstnamelocal = firstnamelocal;
        UserDataManager.lastnamelocal = lastnamelocal;
        UserDataManager.countrylocal = countrylocal;
        UserDataManager.statelocal = statelocal;
        UserDataManager.citylocal = citylocal;
        UserDataManager.phonenumberlocal = phonenumberlocal;
        UserDataManager.descriptionlocal = descriptionlocal;
        UserDataManager.eventscountLocal = eventsCountLocal;
    }

    public static void setProfileData(String usernamelocal, String firstnamelocal, String lastnamelocal, String countrylocal, String statelocal, String citylocal, String descriptionlocal, int eventsCountLocal) {
        UserDataManager.username = usernamelocal;
        UserDataManager.firstname = firstnamelocal;
        UserDataManager.lastname = lastnamelocal;
        UserDataManager.country = countrylocal;
        UserDataManager.state = statelocal;
        UserDataManager.city = citylocal;
        UserDataManager.description = descriptionlocal;
        UserDataManager.eventscount = eventsCountLocal;
    }

    public static void uploadLocalProfileData(String username, String firstname, String lastname, String country, String state, String city, String phonenumber, String description) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference;
        documentReference = firebaseFirestore.collection("users").document(FirebaseInitialization.getFirebaseUser().getUid());
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("firstname", firstname);
        user.put("lastname", lastname);
        user.put("country", country);
        user.put("state", state);
        user.put("city", city);
        user.put("phonenumber", phonenumber);
        user.put("description", description);
        documentReference.set(user, SetOptions.merge());
        UserDataManager.setLocalProfileData(username, firstname, lastname, country, state, city, phonenumber, description, eventscount);
    }

    public static void setBytesProfileImagelocal(byte[] bytes) {
        UserDataManager.bytesProfileImagelocal = bytes;
    }

    public static void setProfileimagelocal(String profileimagelocal) {
        UserDataManager.profileimagelocal = profileimagelocal;
    }

    public static String getUsernamelocal() {
        return usernamelocal;
    }

    public static String getFirstnamelocal() {
        return firstnamelocal;
    }

    public static String getLastnamelocal() {
        return lastnamelocal;
    }

    public static String getCountrylocal() {
        return countrylocal;
    }

    public static String getCitylocal() {
        return citylocal;
    }

    public static String getPhonenumberlocal() {
        return phonenumberlocal;
    }

    public static String getDescriptionlocal() {
        return descriptionlocal;
    }

    public static byte[] getBytesProfileImagelocal() {
        return bytesProfileImagelocal;
    }

    public static String getProfileimagelocal() {
        return profileimagelocal;
    }

    public static String getUsername() {
        return username;
    }

    public static String getFirstname() {
        return firstname;
    }

    public static String getLastname() {
        return lastname;
    }

    public static String getCountry() {
        return country;
    }

    public static String getCity() {
        return city;
    }

    public static String getDescription() {
        return description;
    }

    public static String getProfileimage() {
        return profileimage;
    }

    public static byte[] getBytesProfileImage() {
        return bytesProfileImage;
    }

    public static String getStatelocal() {
        return statelocal;
    }

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        UserDataManager.state = state;
    }

    public static int getEventscountLocal() {
        return eventscountLocal;
    }

    public static int getEventscount() {
        return eventscount;
    }
}
