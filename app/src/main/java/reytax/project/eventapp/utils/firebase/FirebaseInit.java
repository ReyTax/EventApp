package reytax.project.eventapp.utils.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public abstract class FirebaseInit {

    private static FirebaseAuth firebaseAuth;
    private static FirebaseUser firebaseUser;
    private static FirebaseStorage firebaseStorage;
    private static StorageReference storageReference;

    public static FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public static FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public static FirebaseStorage getFirebaseStorage() {
        return firebaseStorage;
    }

    public static StorageReference getStorageReference() {
        return storageReference;
    }

    public static void initialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
    }

}
