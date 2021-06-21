package reytax.project.eventapp.utils.firebase;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public abstract class ImageManager {

    public static void uploadProfileImage(byte[] bytes){

        if(CurrentUserData.getProfileimage().equals("true")){
            String userId = FirebaseInit.getFirebaseUser().getUid();
            StorageReference storageReference = FirebaseInit.getFirebaseStorage().getReference();
            StorageReference image = storageReference.child("images/"+userId);
            image.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    image.putBytes(bytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                            DocumentReference documentReference;
                            documentReference = firebaseFirestore.collection("users").document(FirebaseInit.getFirebaseUser().getUid());
                            Map<String,Object> user = new HashMap<>();
                            user.put("profileimage","true");
                            documentReference.set(user, SetOptions.merge());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        }
        else{
            String user = FirebaseInit.getFirebaseUser().getUid();
            StorageReference storageReference = FirebaseInit.getFirebaseStorage().getReference();
            StorageReference image = storageReference.child("images/"+user);
            image.putBytes(bytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    DocumentReference documentReference;
                    documentReference = firebaseFirestore.collection("users").document(FirebaseInit.getFirebaseUser().getUid());
                    Map<String,Object> user = new HashMap<>();
                    user.put("profileimage","true");
                    documentReference.set(user, SetOptions.merge());
                    CurrentUserData.setProfileimage("true");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });



        }
    }

    public static void loadProfileImage(ImageView imageViewProfileImage, byte[] bytes){
        imageViewProfileImage.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0, bytes.length));
        imageViewProfileImage.getLayoutParams().height = 400;
        imageViewProfileImage.getLayoutParams().width = 400;
    }
}
