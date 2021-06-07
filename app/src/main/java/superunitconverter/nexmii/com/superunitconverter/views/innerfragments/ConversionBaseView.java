package superunitconverter.nexmii.com.superunitconverter.views.innerfragments;

/**
 * Activity's BaseView for separating the View(VoiceConversionController) from its Controller
 * (ConversionController). That way an Activity class is not called in a non-activity class.
 */
public interface ConversionBaseView {

    void onUserInputClicked();

}
