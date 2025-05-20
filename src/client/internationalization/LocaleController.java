package client.internationalization;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleController {
    private Locale currentLocale;
    private String nameOfCurrentLocale;

    public LocaleController(Locales locale){
        currentLocale = locale.getLocale();
        nameOfCurrentLocale = locale.getNameInLocale();
    }
    
    public ResourceBundle getResourceBundle(String nameOfBundle){
        return ResourceBundle.getBundle("client/internationalization/resources/" + nameOfBundle, currentLocale);
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(Locales locale) {
        currentLocale = locale.getLocale();
        nameOfCurrentLocale = locale.getNameInLocale();
    }

    public String getNameOfCurrentLocale() {
        return nameOfCurrentLocale;
    }
}
