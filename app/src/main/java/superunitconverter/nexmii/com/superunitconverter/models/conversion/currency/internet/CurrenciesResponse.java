package superunitconverter.nexmii.com.superunitconverter.models.conversion.currency.internet;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Retrofit's processes response class.
 * Needed in the Retrofit's flow of getting data from the internet
 */
public class CurrenciesResponse {

    //properties:
    private final Map<String, String> symbols = new HashMap<>();

    //empty ctor:
    public CurrenciesResponse(){

    }

    //getter
    public Map<String, String> getSymbols() {
        return symbols;
    }

    //toString
    @NonNull
    @Override
    public String toString() {
        return "CurrenciesResponse{" +
                "currencies=" + symbols +
                '}';
    }
}
