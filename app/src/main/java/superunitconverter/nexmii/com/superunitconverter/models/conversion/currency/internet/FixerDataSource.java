package superunitconverter.nexmii.com.superunitconverter.models.conversion.currency.internet;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton class for handling the Fixer API source. Has lazy initialization.
 */
public class FixerDataSource {

    private static FixerDataSource instance;

    private FixerDataSource(){

    }

    public synchronized static FixerDataSource getInstance(){
        if (instance == null){
            instance = new FixerDataSource();
        }
        return instance;
    }

    /**
     * Retrofit property initialization. Sets up the base URL to work with
     */
    private final Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(FixerConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    /**
     * CurrencyService interface property
     */
    private final CurrencyService mService = mRetrofit.create(CurrencyService.class);

    public void getConversionResult(String from, String to, double number, Callback<ConversionResponse> callback){
        mService.getConversionResult(FixerConstants.API_KEY, from, to, number).enqueue(callback);
    }

    public void getAvailableCurrencies(Callback<CurrenciesResponse> callback){
        mService.getAllCurrencies(FixerConstants.API_KEY).enqueue(callback);
    }
}
