package superunitconverter.nexmii.com.superunitconverter.models.conversion.objects;

/**
 * Temperature Interface. Holds the single method from which all Conversion classes must implement
 * except for Currency conversions
 *
 */
public interface ConversionStrategy {

    /**
     *
     * @param number -> The number from the String captured by the Notes-to-text logic
     * @param currentUnit -> The unit detected from the String captured by the Notes-to-text logic
     * @return converted number to the Temperature class that called it
     */
    double convertTo(double number, String currentUnit);

}
