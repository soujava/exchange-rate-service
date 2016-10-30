package org.soujava.exchange;


import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

@ApplicationScoped
public class ResourceDownloader {

    private static final Logger LOGGER = Logger.getLogger(ResourceDownloader.class.getName());

    private static final byte[] EMPTY = new byte[]{};
    private static final int BUFFER_SIZE = 1024;

    public byte[] download(String url) {
        try {
            URL resource = new URL(url);
            InputStream in = new BufferedInputStream(resource.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int index = 0;

            while (-1 != (index = in.read(buffer))) {
                out.write(buffer, 0, index);
            }
            out.close();
            in.close();
            return out.toByteArray();
        } catch (IOException ex) {
            LOGGER.info("An error happend while downloaded the resource: " + url);
            return EMPTY;
        }

    }


}
