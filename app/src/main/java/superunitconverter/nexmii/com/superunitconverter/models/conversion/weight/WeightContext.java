package superunitconverter.nexmii.com.superunitconverter.models.conversion.weight;

import superunitconverter.nexmii.com.superunitconverter.models.ConversionContext;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;

/**
 * Context class for the weight strategy pattern environment.
 * necessary for getting the correct weight class which implements a common interface
 */
public class WeightContext extends ConversionContext {

    public WeightContext(ConversionStrategy conversionStrategy) {
        super(conversionStrategy);
    }

    @Override
    public double executeStrategy(double number, String currentUnit) {
        return super.executeStrategy(number, currentUnit);
    }
}
