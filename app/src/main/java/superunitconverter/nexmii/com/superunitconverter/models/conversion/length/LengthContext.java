package superunitconverter.nexmii.com.superunitconverter.models.conversion.length;

import superunitconverter.nexmii.com.superunitconverter.models.ConversionContext;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;

/**
 * Context class for the Length Strategy pattern environment.
 * Necessary for getting the correct Length class which implements a common interface
 */
public class LengthContext extends ConversionContext {

    public LengthContext(ConversionStrategy conversionStrategy) {
        super(conversionStrategy);
    }

    @Override
    public double executeStrategy(double number, String currentUnit) {
        return super.executeStrategy(number, currentUnit);
    }
}
