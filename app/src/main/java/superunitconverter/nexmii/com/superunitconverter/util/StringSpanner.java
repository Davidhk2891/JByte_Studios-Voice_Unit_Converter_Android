package superunitconverter.nexmii.com.superunitconverter.util;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;

/**
 * Spannable class helper. Singleton class that assist in manipulating the style of specific
 * parts of a String.
 */
public class StringSpanner {

    private static StringSpanner instance;

    private StringSpanner(){}

    public static synchronized StringSpanner getInstance(){
        if (instance == null)
            instance = new StringSpanner();
        return instance;
    }

    /**
     * Manipulates the passed string. It can change the color of a specific part of the string and
     * if desired, underline it.
     * @param string -> The string that is going to be manipulated
     * @param startIndex -> first letter of the string
     * @param endIndex -> last letter of the string
     * @param underlined -> if true, the passed string will be underlined, else no
     * @return -> returns the manipulated string
     * if endIndex is 0, the method will assume the end index is the last char in the string array.
     */
    public SpannableString coloredString(String string, String color, int startIndex, int endIndex, boolean underlined){
        SpannableString spannableString = new SpannableString(string);
        ForegroundColorSpan blue = new ForegroundColorSpan(Color.parseColor(color));
        if (endIndex == 0)
            spannableString.setSpan(blue, startIndex, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        else
            spannableString.setSpan(blue, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (underlined) {
            if (endIndex == 0)
                spannableString.setSpan(new UnderlineSpan(), startIndex, string.length(), 0);
            else
                spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, 0);
        }
        return spannableString;
    }

}
