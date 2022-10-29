package io.crtp.ec135.utilities;
//https://mkyong.com/java/java-properties-file-examples/

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EC135Properties {

    Logger log = LoggerFactory.getLogger(EC135Properties.class);

    private static EC135Properties ec135Properties = null;
    private Properties prop = new Properties();

    public EC135Properties() {

        try (InputStream input = new FileInputStream(Constants.properties_file_name)) {

            // load a properties file
            prop.load(input);

            log.debug(prop.getProperty("db.url"));
            log.debug(prop.getProperty("db.user"));
            log.debug(prop.getProperty("db.password"));

            log.debug(prop.getProperty("dat.directory"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static EC135Properties getInstance() {
        if (ec135Properties == null ) {
            ec135Properties = new EC135Properties();
        }
        return ec135Properties;
    }

    public String getProp(String p){
        return prop.getProperty(p);
    }    
}
