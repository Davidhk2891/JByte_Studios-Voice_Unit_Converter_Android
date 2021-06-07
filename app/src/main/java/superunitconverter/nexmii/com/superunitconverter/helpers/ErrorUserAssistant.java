package superunitconverter.nexmii.com.superunitconverter.helpers;

/**
 * Singleton class which aims to provide the user with a suggestion to takes them to the manual
 * conversion screen if they fail to convert something for the second time during an app session.
 * The way to do this is by simply
 */
public final class ErrorUserAssistant {

    private int errorsCommited = 0;

    private static ErrorUserAssistant instance;

    private ErrorUserAssistant(){}

    public static synchronized ErrorUserAssistant getInstance(){
        if (instance == null)
            instance = new ErrorUserAssistant();
        return instance;
    }

    /**
     * Adds up errors to the errorsCommited counter
     */
    public void addErrorsCommited(){
        errorsCommited++;
    }

    /**
     * Gets the total current amount of errors commited
     */
    public int getErrorsCommited(){
        return errorsCommited;
    }
}
