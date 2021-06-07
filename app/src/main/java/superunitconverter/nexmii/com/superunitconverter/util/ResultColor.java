package superunitconverter.nexmii.com.superunitconverter.util;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import superunitconverter.nexmii.com.superunitconverter.R;

/**
 * Singleton class for setting the background color of the result depending on its category.
 */
public class ResultColor {

    private static ResultColor instance;

    private ResultColor(){

    }

    public static ResultColor getInstance(){
        if (instance == null) {
            instance = new ResultColor();
        }
        return instance;
    }

    /**
     * General purpose color assigner which returns the appropiate color depending on the category passed to the
     * method's argument.
     * @param context -> Activity context
     * @param category -> The category for which the method will determine its color
     * @return -> Returns the color that belongs to such category
     */
    public int generalColorSelector(Context context, String category){
        int color;
        switch(category){
            case "temperature":
                color = ContextCompat.getColor(context, R.color.app_green);
                break;
            case "length":
                color = ContextCompat.getColor(context, R.color.app_purple);
                break;
            case "weight":
                color = ContextCompat.getColor(context, R.color.app_red);
                break;
            case "volume":
                color = ContextCompat.getColor(context, R.color.app_orange);
                break;
            case "currency":
                color = ContextCompat.getColor(context, R.color.app_blue);
                break;
            default:
                color = ContextCompat.getColor(context, R.color.white);
        }
        return color;
    }

    /**
     * Sets the background for the result from the conversion. Depending on the category the conversion comes from, it will generate a corresponding color.
     * Relevant to the whole app. For example, Temperature will be green all across the app and its screens
     * @param category the conversion category from the five available:
     *                 - Temperature -> green
     *                 - Length -> purple
     *                 - Weight -> red
     *                 - Volume -> orange
     *                 - Currency -> blue
     */
    public void convertToBgColor(Context context, String category, LinearLayout convertToData){
        switch (category){
            case "temperature":
                convertToData.setBackgroundColor(ContextCompat.getColor(context, R.color.app_green));
                break;
            case "length":
                convertToData.setBackgroundColor(ContextCompat.getColor(context, R.color.app_purple));
                break;
            case "weight":
                convertToData.setBackgroundColor(ContextCompat.getColor(context, R.color.app_red));
                break;
            case "volume":
                convertToData.setBackgroundColor(ContextCompat.getColor(context, R.color.app_orange));
                break;
            case "currency":
                convertToData.setBackgroundColor(ContextCompat.getColor(context, R.color.app_blue));
                break;
        }
    }

    /**
     * Color selector that works specifically to change the ImageView resource file depending on the conversion
     * category that was passed as a parameter. For the time being it is only used for the crossed arrows color(different files)
     * for the CommonConvFragment.
     * @param category -> The category for which the correct image will be selected
     */
    public int convertImageViewFromColor(String category){
        int imageResource = 0;
        switch (category){
            case "temperature":
                imageResource = R.drawable.convarrowgreen;
                break;
            case "length":
                imageResource = R.drawable.convarrowpurple;
                break;
            case "weight":
                imageResource = R.drawable.convarrowred;
                break;
            case "volume":
                imageResource = R.drawable.convarroworange;
                break;
            case "currency":
                imageResource = R.drawable.convarrowblue;
                break;
        }
        return imageResource;
    }

}
