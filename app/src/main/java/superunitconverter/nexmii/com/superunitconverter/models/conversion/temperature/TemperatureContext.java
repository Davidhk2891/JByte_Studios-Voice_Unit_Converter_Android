package superunitconverter.nexmii.com.superunitconverter.models.conversion.temperature;

import superunitconverter.nexmii.com.superunitconverter.models.ConversionContext;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;

/**
 * Context class for the Temperature Strategy pattern environment.
 * Necessary for getting the correct Temperature class which implements a common interface
 */
public class TemperatureContext extends ConversionContext {

    public TemperatureContext(ConversionStrategy conversionStrategy) {
        super(conversionStrategy);
    }

    @Override
    public double executeStrategy(double number, String currentUnit) {
        return super.executeStrategy(number, currentUnit);
    }
}
