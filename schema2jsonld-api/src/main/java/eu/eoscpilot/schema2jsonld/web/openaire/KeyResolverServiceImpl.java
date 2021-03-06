package eu.eoscpilot.schema2jsonld.web.openaire;

import eu.eoscpilot.schema2jsonld.web.exception.ValidationException;
import eu.eoscpilot.schema2jsonld.web.common.KeyResolverService;
import org.springframework.stereotype.Component;

@Component("openAireKeyResolver")
public class KeyResolverServiceImpl implements KeyResolverService {

    public eu.eoscpilot.schema2jsonld.web.common.Key resolve(String key) {
        if (key == null || key.isEmpty())
            throw new ValidationException(String.format("unsupported format in key: %s", key));

        String[] parts = key.split("@");
        if (parts.length != 2) throw new ValidationException(String.format("unsupported format in key: %s", key));
        if(!OpenAireKey.KeyType.isValid(parts[0])) throw new ValidationException(String.format("unsupported type in key: %s", key));

        return new OpenAireKey(OpenAireKey.KeyType.from(parts[0]), parts[1]);
    }
}
