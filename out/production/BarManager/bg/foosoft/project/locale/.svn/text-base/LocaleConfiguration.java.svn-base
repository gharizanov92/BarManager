package bg.foosoft.project.locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import java.util.Locale;
import java.util.Properties;

public class LocaleConfiguration extends ReloadableResourceBundleMessageSource {
    public Properties getAllProperties(Locale locale) {
        clearCacheIncludingAncestors();
        PropertiesHolder propertiesHolder = getMergedProperties(locale);
        Properties properties = propertiesHolder.getProperties();

        return properties;
    }
}

