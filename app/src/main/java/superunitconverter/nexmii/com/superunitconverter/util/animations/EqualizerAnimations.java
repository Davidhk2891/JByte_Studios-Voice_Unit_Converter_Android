package superunitconverter.nexmii.com.superunitconverter.util.animations;

import android.view.animation.Animation;

import com.airbnb.lottie.LottieAnimationView;

/**
 * Singleton Class for handling all animation processes for the fake sound equalizer.
 */
public class EqualizerAnimations {

    public static int INTRO_IN = 1;
    public static int INTRO_OUT = 30;
    public static int LOOP_IN = 31;
    public static int LOOP_OUT = 170;
    public static int OUTRO_IN = 171;
    public static int OUTRO_OUT = 200;


    private static EqualizerAnimations instance;

    private EqualizerAnimations(){

    }

    public static EqualizerAnimations getInstance(){
        if (instance == null) instance = new EqualizerAnimations();
        return instance;
    }

    public void PlayAnimation(LottieAnimationView lav){
        lav.playAnimation();
        lav.setRepeatCount(Animation.INFINITE);
    }

    public void stopAnimation(LottieAnimationView lav){
        lav.cancelAnimation();
        lav.setRepeatCount(Animation.ABSOLUTE);
        lav.setFrame(OUTRO_IN);
        lav.resumeAnimation();
    }

}
