package reytax.project.eventapp.utils.firebase;

import android.graphics.BitmapFactory;
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

    public static void uploadProfileImage(byte[] bytes) {

        if (UserDataManager.getProfileimagelocal().equals("true")) {
            String userId = FirebaseInitialization.getFirebaseUser().getUid();
            StorageReference storageReference = FirebaseInitialization.getFirebaseStorage().getReference();
            StorageReference image = storageReference.child("images/" + userId);
            image.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    image.putBytes(bytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                            DocumentReference documentReference;
                            documentReference = firebaseFirestore.collection("users").document(FirebaseInitialization.getFirebaseUser().getUid());
                            Map<String, Object> user = new HashMap<>();
                            user.put("profileimage", "true");
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
        } else {
            String user = FirebaseInitialization.getFirebaseUser().getUid();
            StorageReference storageReference = FirebaseInitialization.getFirebaseStorage().getReference();
            StorageReference image = storageReference.child("images/" + user);
            image.putBytes(bytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    DocumentReference documentReference;
                    documentReference = firebaseFirestore.collection("users").document(FirebaseInitialization.getFirebaseUser().getUid());
                    Map<String, Object> user = new HashMap<>();
                    user.put("profileimage", "true");
                    documentReference.set(user, SetOptions.merge());
                    UserDataManager.setProfileimagelocal("true");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        }
    }

    public static void loadProfileImage(ImageView imageViewProfileImage, byte[] bytes) {
        if (bytes != null)
            imageViewProfileImage.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        imageViewProfileImage.getLayoutParams().height = 400;
        imageViewProfileImage.getLayoutParams().width = 400;
    }
}
