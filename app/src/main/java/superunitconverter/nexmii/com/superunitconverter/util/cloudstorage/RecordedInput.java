package superunitconverter.nexmii.com.superunitconverter.util.cloudstorage;

import javax.annotation.Nonnull;

/**
 * RecorderInput is a POJO class for setting up the proper fields of the recorder input data
 * before sending it to the Firestore cloud
 */
public class RecordedInput {

    private final String userInput;
    private final String location;
    private final String phoneModel;
    private final String androidOS;
    private final boolean isConversionOk;

    public RecordedInput(String userInput, String location, String phoneModel, String androidOS
            , boolean isConversionOk) {
        this.userInput = userInput;
        this.location = location;
        this.phoneModel = phoneModel;
        this.androidOS = androidOS;
        this.isConversionOk = isConversionOk;
    }

    public String getUserInput() {
        return userInput;
    }

    public String getLocation() {
        return location;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public String getAndroidOS() {
        return androidOS;
    }

    public boolean isConversionOk() {
        return isConversionOk;
    }

    @Nonnull
    @Override
    public String toString() {
        return "RecordedInput{" +
                "userInput='" + userInput + '\'' +
                ", location='" + location + '\'' +
                ", phoneModel='" + phoneModel + '\'' +
                ", androidOS='" + androidOS + '\'' +
                ", isConversionOk=" + isConversionOk +
                '}';
    }

}
