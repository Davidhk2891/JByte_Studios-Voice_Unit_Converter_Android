package superunitconverter.nexmii.com.superunitconverter.util.cloudstorage;

/**
 * Class for holding constants used for Firebase's Firestore db different operations
 */
public class FirestoreConstants {

    public static final String COLLECTION_USER_INPUT = "RecordedUserInput";
    //DOCUMENT_BY_ID is used directly in the method that calls it so it generates a fresh ID everytime
    //public static final String DOCUMENT_BY_ID = RandomStringGenerator.getInstance().generateRandomString(10);
    public static final String FIELD_USER_INPUT = "userInput";
    public static final String FIELD_LOCATION = "location";
    public static final String FIELD_PHONE_MODEL = "phoneModel";
    public static final String FIELD_ANDROID_OS = "androidOS";
    public static final String FIELD_IS_CONVERSION_OK = "conversionOK";

}
