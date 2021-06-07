package superunitconverter.nexmii.com.superunitconverter.util.animations;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;

/**
 * Animations class for handling all native animations of the app.
 */
public class AppAnimations {

    private final Context mContext;

    private float visible = 1f;

    private float invisible = 0f;

    private int instantTime = 1;

    private int instantTimeFadeOut = instantTime;

//    private int instantTimeFadeIn = instantTime;

    private int fadeOutTime = 300;

    private int fadeInTime = 400;

    private float moveUpY = -100f;

    private float moveDownY = 100f;

    private int moveUpTime = 600;

//    private int moveDownTime = 300;

    private int vibrationTimeShort = 250;

    public AppAnimations(Context ctx){
        mContext = ctx;
    }

    public void instantFadeOut(View view){
        view.animate().alpha(invisible).setDuration(instantTimeFadeOut);
    }

    public void fadeOut(View view, AppAnimationInterface aai){
        view.animate().alpha(invisible).setDuration(fadeOutTime).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                aai.onAnimationFinished();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

//    public void instantFadeIn(View view){
//        view.animate().alpha(visible).setDuration(instantTimeFadeIn);
//    }

    public void fadeIn(View view){
        view.animate().alpha(visible).setDuration(fadeInTime);
    }

    public void moveUp(View view){
        view.animate().translationYBy(moveUpY).setDuration(moveUpTime);
    }

//    public void moveDown(View view){
//        view.animate().translationYBy(moveDownY).setDuration(moveDownTime);
//    }

    public void vibrateShort(){
        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        //vibrate once for 200 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(vibrationTimeShort, VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //deprecated in API 26
            v.vibrate(vibrationTimeShort);
        }
    }

    public interface AppAnimationInterface{
        void onAnimationFinished();
    }
}
