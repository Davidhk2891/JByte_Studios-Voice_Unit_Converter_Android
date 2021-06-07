package superunitconverter.nexmii.com.superunitconverter.models;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;

/**
 * Conversion context parent class. Used by all Context classes.
 * Part of the Strategy design pattern
 */
public abstract class ConversionContext {

    private final ConversionStrategy mConversionStrategy;

    public ConversionContext(ConversionStrategy conversionStrategy){
        this.mConversionStrategy = conversionStrategy;
    }

    public double executeStrategy(double number, String currentUnit) {
        return mConversionStrategy.convertTo(number, currentUnit);
    }

}
