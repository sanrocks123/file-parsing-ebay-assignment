package credit.service;

import credit.dto.CustomerDto;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * Java Source HtmlPageGeneratorServiceImpl created on 11/17/2021
 * <p>
 * We can use this service class for accepting customer info from API controllers entry point.
 * Also we can use Kafka listener annotation on method buildHtmlPage to fetch events from subscribed topics
 *
 * @author : Sanjeev Saxena
 * @version : 1.0
 * @email : sanrocks123@gmail.com
 */

public class HtmlPageGeneratorServiceImpl implements HtmlPageGeneratorService {

    private VelocityEngine ve = new VelocityEngine();
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     *
     */
    public HtmlPageGeneratorServiceImpl() {
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
    }

    /**
     * @return
     */
    public String buildHtmlPage(final List<CustomerDto> customers) {
        VelocityContext context = updateContext(customers);
        StringWriter sw = createAndMergeContext(context);
        return saveFile(sw.toString());
    }

    /**
     * @param context
     * @return
     */
    private StringWriter createAndMergeContext(VelocityContext context) {
        Template t = ve.getTemplate("customer.vm");
        StringWriter sw = new StringWriter();
        t.merge(context, sw);
        log.info("after template processing, contents : \n{}", sw.toString());
        return sw;
    }

    /**
     * @param data
     * @return
     */
    private String saveFile(String data) {
        String filePath = "";
        try {
            final File output = File.createTempFile("credit-limit", ".html");
            final FileWriter writer = new FileWriter(output);
            writer.write(data);
            writer.close();

            log.info("html exported, file: \n{}", output.getAbsolutePath());
            filePath = output.getAbsolutePath();
        } catch (final IOException ex) {
            log.error("export error", ex);
        }
        return filePath;
    }

    /**
     * @param customers
     * @return
     */
    private VelocityContext updateContext(List<CustomerDto> customers) {
        log.info("updateContextData, customers: \n{}", new JSONArray(customers).toString(4));
        VelocityContext context = new VelocityContext();
        context.put("customers", customers);
        return context;
    }

}
