package superunitconverter.nexmii.com.superunitconverter.models.commonconv;

import androidx.annotation.NonNull;

/**
 * CommonConv data object (POJO).
 */
public class CommonConv {

    //Properties
    private final String category;
    private final String originalNumber;
    private final String originalUnit;
    private final String targetNumber;
    private final String targetUnit;
    private final String date;

    //Constructor for list of common conversions with result
    public CommonConv(String category, String originalNumber, String originalUnit, String targetNumber, String targetUnit, String date) {
        this.category = category;
        this.originalNumber = originalNumber;
        this.originalUnit = originalUnit;
        this.targetNumber = targetNumber;
        this.targetUnit = targetUnit;
        this.date = date;
    }

    public String getTargetNumber(){
        return targetNumber;
    }

    public String getCategory() {
        return category;
    }

    public String getOriginalNumber() {
        return originalNumber;
    }

    public String getOriginalUnit() {
        return originalUnit;
    }

    public String getTargetUnit() {
        return targetUnit;
    }

//    public String getDate() {
//        return date;
//    }

    @NonNull
    @Override
    public String toString() {
        return "CommonConv{" +
                "category='" + category + '\'' +
                ", originalNumber='" + originalNumber + '\'' +
                ", originalUnit='" + originalUnit + '\'' +
                ", targetUnit='" + targetUnit + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
