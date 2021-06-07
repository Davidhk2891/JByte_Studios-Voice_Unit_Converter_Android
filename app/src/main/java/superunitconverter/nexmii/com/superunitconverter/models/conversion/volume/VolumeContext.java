package superunitconverter.nexmii.com.superunitconverter.models.conversion.volume;

import superunitconverter.nexmii.com.superunitconverter.models.ConversionContext;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;

/**
 * Context class for the volume strategy pattern environment.
 * necessary for getting the correct volume class which implements a common interface
 */
public class VolumeContext extends ConversionContext {

    public VolumeContext(ConversionStrategy conversionStrategy) {
        super(conversionStrategy);
    }

    @Override
    public double executeStrategy(double number, String currentUnit) {
        return super.executeStrategy(number, currentUnit);
    }
}
