package superunitconverter.nexmii.com.superunitconverter.models.conversion.currency;

import android.os.Handler;
import android.os.Looper;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import superunitconverter.nexmii.com.superunitconverter.helpers.ErrorLogMessages;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.currency.internet.ConversionResponse;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.currency.internet.FixerDataSource;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.currency.internet.CurrenciesResponse;
import superunitconverter.nexmii.com.superunitconverter.util.Logger;

/**
 * Currency repository class -> Singleton class
 * Will be used to collect the user input from the CurrencyTextManager.
 * The user input, goes through KeywordsExtractor, returning the HashMap with:
 * - The full original unit (Ex: USD)
 * - The full target unit (Ex: ILS)
 * - The amount that needs conversion (Ex: 23)
 * Keywords extractor needs to have separate extractor engine for currencies
 * CurrencyRepo will communicate with Keywords extractor to get the data needed to compare
 * the user input against the currencies list fetched from Fixer.
 * Once it does that, it will extract what was mentioned above.
 */
public class CurrencyRepo {

    private static CurrencyRepo instance;

    private final Handler uiThread = new Handler(Looper.getMainLooper());

    private final Logger logger;

    private CurrencyRepo(){
        logger = Logger.getLoggerInstance();
    }

    public synchronized static CurrencyRepo getInstance(){
        if (instance == null){
            instance = new CurrencyRepo();
        }
        return instance;
    }

    /**
     * Callback interface for the available currencies method
     */
    public interface CurrenciesCallback{
        void onResponse(Map<String, String> currencies, Exception e);
        void onFailure(String message);
    }

    /**
     * Callback intergace for the conversion method
     */
    public interface ConversionCallBack{
        void onResponse(double result, String eCode);
    }

    /**
     * Method for executing the requested conversion against the API
     * @param sNumber the amount of money to be converted
     * @param from original currency
     * @param to target currency
     * @param cc callback for executing actions when a response is returned
     */
    public void getConversion(String sNumber, String from, String to, ConversionCallBack cc){
        /*
         * -policy:
         */
        double number = 0.0;
        try {
            number = Double.parseDouble(sNumber);
            logger.infoLog("CONVERSION_NUMBER:", "IS: " + number);
        }catch(NumberFormatException e){
            cc.onResponse(0, e.getMessage());
        }
        FixerDataSource.getInstance().getConversionResult(from, to, number, new Callback<ConversionResponse>() {
            @Override
            public void onResponse(Call<ConversionResponse> call, Response<ConversionResponse> response) {
                ConversionResponse res = response.body();
                if (res != null) {
                    logger.infoLog("CONVERSION_CALL:", "IS: " + call.request());
                    logger.infoLog("CONVERSION_RESPONSE:", "IS: " + res);
                    logger.infoLog("CONVERSION_RESULT:", "IS: " + res.getResult());
                } else {
                    logger.infoLog("CONVERSION_RESULT:", "NULL");
                }
                uiThread.post(()->{
                    if (response.isSuccessful()){
                        //good call
                        if (res != null)
                            cc.onResponse(res.getResult(), null);
                    }else{
                        //bad call
                        switch(response.code()){
                            case 404:
                                cc.onResponse(0, ErrorLogMessages.USER_NOT_FOUND_404);
                                break;
                            case 400:
                                cc.onResponse(0, ErrorLogMessages.BAD_REQUEST_400);
                                break;
                            case 500:
                                cc.onResponse(0, ErrorLogMessages.SERVER_BROKEN_500);
                                break;
                            case 501:
                                cc.onResponse(0, ErrorLogMessages.REQUEST_NOT_IMPLEMENTED_501);
                                break;
                            case 502:
                                cc.onResponse(0, ErrorLogMessages.BAD_GATEWAY_502);
                                break;
                            case 503:
                                cc.onResponse(0, ErrorLogMessages.SERVICE_UNAVAILABLE_503);
                                break;
                            default:
                                cc.onResponse(0, ErrorLogMessages.UNKNOWN_ERROR);
                                break;
                        }
                        cc.onResponse(0, null);
                    }
                });
            }

            @Override
            public void onFailure(Call<ConversionResponse> call, Throwable t) {
                logger.infoLog("CONVERSION_RESULT_2:", "ERROR IS: " + t.getMessage());
                cc.onResponse(0, t.getMessage());
            }
        });
    }

    /**
     * Method for executing currently available currencies to be used to convert to and from
     * @param callback callback for executing actions when a response is returned
     */
    public void getCurrencies(CurrenciesCallback callback){

        /*
        * - policy: get currencies from the internet as long as there is connectivity.
        *           if there is no connection(for any reason), get the currencies from db.
        *
        * - policy: last date of update: if 2 weeks have not passed, get the currencies from db.
         */
        FixerDataSource.getInstance().getAvailableCurrencies(new Callback<CurrenciesResponse>() {
            @Override
            public void onResponse(Call<CurrenciesResponse> call, Response<CurrenciesResponse> response) {
                CurrenciesResponse res = response.body();
                if (res != null)
                    logger.infoLog("TEST_CURRENCY_BODY2", String.valueOf(res.getCurrencies()));

                uiThread.post(()->{
                    //Bad call:
                    if (res == null){
                        callback.onResponse(null, null);
                        return;
                    }

                    //Good call:
                    //Not null, there is internet - new data arrived
                    callback.onResponse(res.getCurrencies(), null);

                });

            }

            @Override
            public void onFailure(Call<CurrenciesResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
                logger.infoLog("TEST_CURRENCY", "ERROR IS: " + t.getMessage());
                t.printStackTrace();

            }
        });
    }

}
