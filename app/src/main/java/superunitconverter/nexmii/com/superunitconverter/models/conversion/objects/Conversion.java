package superunitconverter.nexmii.com.superunitconverter.models.conversion.objects;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Conversion Object -> Created after a full conversion
 * Data object (POJO)
 */
@Entity
public class Conversion {

    //id
    @PrimaryKey(autoGenerate = true)
    private int id;

    //original unit
    private String originalUnit;

    //original amount
    private String originalAmount;

    //target unit
    private String targetUnit;

    //converted amount
    private String convertedAmount;

    //category
    private String category;

    //date(for currency)
    private String date;

    //ctor
    public Conversion(String originalUnit, String originalAmount, String targetUnit, String convertedAmount, String category, String date) {
        this.originalUnit = originalUnit;
        this.originalAmount = originalAmount;
        this.targetUnit = targetUnit;
        this.convertedAmount = convertedAmount;
        this.category = category;
        this.date = date;
    }

    //default ctor
    public Conversion(){

    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalUnit() {
        return originalUnit;
    }

    public void setOriginalUnit(String originalUnit) {
        this.originalUnit = originalUnit;
    }

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(String targetUnit) {
        this.targetUnit = targetUnit;
    }

    public String getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(String convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @NonNull
    @Override
    public String toString() {
        return "Conversion{" +
                "id=" + id +
                ", originalUnit='" + originalUnit + '\'' +
                ", originalAmount='" + originalAmount + '\'' +
                ", targetUnit='" + targetUnit + '\'' +
                ", convertedAmount='" + convertedAmount + '\'' +
                ", category='" + category + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
