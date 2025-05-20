package client.internationalization;

import java.util.Locale;

public enum Locales {
    ENGLISH("en", "US", "English"),
    RUSSIAN("ru", "RU", "Русский"),
    PORTUGUESE("pt", "PT", "Português"),
    ALBANIAN("sq", "AL", "Shqip"),
    SPANISH_COLOMBIA("es", "CO", "Español colombiano");


    private final Locale locale;
    private final String nameInLocale;

    Locales(String language, String country, String nameInLocale){
        locale = new Locale(language, country);
        this.nameInLocale = nameInLocale;
    }

    public Locale getLocale(){
        return locale;
    }

    public String getNameInLocale(){
        return nameInLocale;
    }
}
