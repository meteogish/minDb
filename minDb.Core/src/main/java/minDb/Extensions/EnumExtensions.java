package minDb.Extensions;

/**
 * EnumExtensions
 */
public class EnumExtensions {
    public static <T extends Enum<?>> T parse(Class<T> enumeration, String strValue) {
        for (T each : enumeration.getEnumConstants()) {
            if (each.name().compareToIgnoreCase(strValue) == 0) {
                return each;
            }
        }
        return null;
    }
}