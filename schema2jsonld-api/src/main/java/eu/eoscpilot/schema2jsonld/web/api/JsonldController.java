package eu.eoscpilot.schema2jsonld.web.api;

import eu.eoscpilot.schema2jsonld.web.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/json-ld")
public class JsonldController {

    private ApplicationContext applicationContext;

    @Autowired
    public JsonldController(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{profile}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String retrieve(@PathVariable String profile, @PathVariable String id, @RequestParam(value = "field", required = false) String[] fields) {
        eu.eoscpilot.schema2jsonld.web.common.ConversionService conversion = null;
        switch(profile.toLowerCase()){
            case "openaire":{
                conversion = this.applicationContext.getBean(eu.eoscpilot.schema2jsonld.web.openaire.ConversionServiceImpl.class);
                break;
            }
            case "ebi-metagenomics":{
                conversion = this.applicationContext.getBean(eu.eoscpilot.schema2jsonld.web.ebimetagenomics.ConversionServiceImpl.class);
                break;
            }
            case "bluebridge":{
                conversion = this.applicationContext.getBean(eu.eoscpilot.schema2jsonld.web.bluebridge.ConversionServiceImpl.class);
                break;
            }
            default: throw new ValidationException(String.format("unsupported profile %s", profile));
        }
        String jsonld = conversion.Convert(id);
        return jsonld;
    }
}
