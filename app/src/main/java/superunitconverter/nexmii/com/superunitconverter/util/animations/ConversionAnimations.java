package superunitconverter.nexmii.com.superunitconverter.util.animations;

import android.content.Context;
import android.view.View;

/**
 * This class arranges all animations from AppAnimations class into the proper sequence for displaying
 * the conversion result, resetting the process, etc.
 */
public class ConversionAnimations {

    private final AppAnimations mAppAnimations;

    public ConversionAnimations(Context ctx){
        mAppAnimations = new AppAnimations(ctx);
    }

    public void outputInstantFadeOut(View view){
        mAppAnimations.instantFadeOut(view);
    }

    public void animateResult(View inputView, View outputView){
        mAppAnimations.fadeOut(inputView, ()->{
            mAppAnimations.fadeIn(outputView);
            mAppAnimations.moveUp(outputView);
            mAppAnimations.vibrateShort();
        });
    }

    public void animateResult(View outputView){
        mAppAnimations.fadeIn(outputView);
        mAppAnimations.vibrateShort();
    }

    public void animateRevertBackResult(View inputView, View outputView){
        outputView.animate().alpha(0f).translationYBy(100f).setDuration(1);
        inputView.setAlpha(1f);
    }

    public void animateRevertBackResult(View outputView){
        outputView.animate().alpha(0f).setDuration(1);
    }

    public void animateError(View inputView, View outputView){
        mAppAnimations.fadeOut(inputView, ()->{
            mAppAnimations.fadeIn(outputView);
            mAppAnimations.moveUp(outputView);
            mAppAnimations.vibrateShort();
        });
    }

    public void animateError(View outputView){
        mAppAnimations.fadeIn(outputView);
        mAppAnimations.vibrateShort();
    }

    public void animateRevertBackError(View inputView, View outputView){
        outputView.animate().alpha(0f).translationYBy(100f).setDuration(1);
        inputView.setAlpha(1f);
    }
}
