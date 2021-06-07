package superunitconverter.nexmii.com.superunitconverter.models.conversion.currency.internet;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencyService {

    /**
     * End-point: convert
     * With every request, the api key needs to be sent
     * This endpoint allows for performing the conversion
     */
    @GET("convert")
    Call<ConversionResponse> getConversionResult(@Query("access_key") String api_key
            , @Query("from") String from, @Query("to") String to, @Query("amount") double amount);

    /**
     * End-point: symbols
     * With every request, the api key needs to be sent
     * This endpoint fetches the available conversions. It is needed for understanding
     * what the user wants to convert to and from.
     */
    @GET("symbols")
    Call<CurrenciesResponse> getAllCurrencies(@Query("access_key") String api_key);

}
