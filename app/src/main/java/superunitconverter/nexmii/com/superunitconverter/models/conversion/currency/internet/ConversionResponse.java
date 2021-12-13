package superunitconverter.nexmii.com.superunitconverter.models.conversion.currency.internet;

import androidx.annotation.NonNull;

/**
 * Response from fixer.io API call for the currency conversion.
 */
public class ConversionResponse {

    //properties
    //result cannot be final!
    @SuppressWarnings("FieldMayBeFinal")
    private double result = 0.0;

    //empty ctor
    public ConversionResponse(){

    }

    //getters
    public double getResult() {
        return result;
    }

    //toString
    @NonNull
    @Override
    public String toString() {
        return "ConversionResponse{" +
                "result=" + result +
                '}';
    }
}
