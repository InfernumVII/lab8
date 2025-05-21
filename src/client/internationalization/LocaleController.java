package client.internationalization;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleController {
    private static Locale currentLocale;
    private static String nameOfCurrentLocale;

    // public LocaleController(Locales locale){
    //     currentLocale = locale.getLocale();
    //     nameOfCurrentLocale = locale.getNameInLocale();
    // }
    
    public static ResourceBundle getResourceBundle(String nameOfBundle){
        return ResourceBundle.getBundle("client/internationalization/resources/" + nameOfBundle, currentLocale);
    }

    public static NumberFormat getNumberFormat() {
        return NumberFormat.getNumberInstance(currentLocale);
    }

    

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static void setCurrentLocale(Locales locale) {
        currentLocale = locale.getLocale();
        nameOfCurrentLocale = locale.getNameInLocale();
    }

    public static String getNameOfCurrentLocale() {
        return nameOfCurrentLocale;
    }
}
