package reytax.project.eventapp.utils.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public abstract class CurrentUserData {
    private static String username="", firstname="", lastname="", country="", city="", phonenumber="", description="", profileimage="";


    private static byte[] bytesProfileImage;

    public static void loadData() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("users").document(FirebaseInit.getFirebaseUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(!documentSnapshot.getData().isEmpty()){
                    Map<String, Object> userData = documentSnapshot.getData();
                    setLocalProfileData(userData.get("username").toString(),userData.get("firstname").toString(),userData.get("lastname").toString(),userData.get("country").toString(),userData.get("city").toString(),userData.get("phonenumber").toString(),userData.get("description").toString());

                    profileimage = userData.get("profileimage").toString();
                    bytesProfileImage = null;

                    if(profileimage.equals("true")){
                        StorageReference image = FirebaseInit.getStorageReference().child("images/"+FirebaseInit.getFirebaseUser().getUid());
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

    public static void setLocalProfileData(String usernamelocal,String firstnamelocal, String lastnamelocal, String countrylocal, String citylocal, String phonenumberlocal, String descriptionlocal){
        username = usernamelocal;
        firstname = firstnamelocal;
        lastname = lastnamelocal;
        country = countrylocal;
        city = citylocal;
        phonenumber = phonenumberlocal;
        description = descriptionlocal;
    }

    public static void uploadProfileData(String username,String firstname, String lastname, String country, String city, String phonenumber, String description){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference;
        documentReference = firebaseFirestore.collection("users").document(FirebaseInit.getFirebaseUser().getUid());
        Map<String,Object> user = new HashMap<>();
        user.put("username",username);
        user.put("firstname",firstname);
        user.put("lastname",lastname);
        user.put("country",country);
        user.put("city",city);
        user.put("phonenumber",phonenumber);
        user.put("description",description);
        documentReference.set(user, SetOptions.merge());
        CurrentUserData.setLocalProfileData(username,firstname,lastname,country,city,phonenumber,description);
    }

    public static void setBytesProfileImage(byte[] bytes) {
        CurrentUserData.bytesProfileImage = bytes;
    }

    public static void setProfileimage(String profileimage) {
        CurrentUserData.profileimage = profileimage;
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

    public static String getPhonenumber() {
        return phonenumber;
    }

    public static String getDescription() {
        return description;
    }

    public static byte[] getBytesProfileImage() {
        return bytesProfileImage;
    }

    public static String getProfileimage() {
        return profileimage;
    }



}
