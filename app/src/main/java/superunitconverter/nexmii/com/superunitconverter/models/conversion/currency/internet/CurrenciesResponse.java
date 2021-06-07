package superunitconverter.nexmii.com.superunitconverter.models.conversion.currency.internet;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Retrofit's processes response class.
 * Needed in the Retrofit's flow of getting data from the internet
 */
public class CurrenciesResponse {

    //properties:
    @SerializedName("symbols")
    private final Map<String, String> currencies = new HashMap<>();

    //empty ctor:
    public CurrenciesResponse(){

    }

    //getter
    public Map<String, String> getCurrencies() {
        return currencies;
    }

    //toString
    @NonNull
    @Override
    public String toString() {
        return "CurrenciesResponse{" +
                "currencies=" + currencies +
                '}';
    }
}
