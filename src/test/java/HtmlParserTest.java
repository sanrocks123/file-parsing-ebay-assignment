import credit.dto.CustomerDto;
import credit.fileparsers.CsvFileParserImpl;
import credit.fileparsers.FileParserAbstract;
import credit.fileparsers.PrnFileParserImpl;
import credit.fileparsers.SourceFileTypeEnum;
import credit.service.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Java Source HtmlParserTest created on 11/16/2021
 *
 * @author : Sanjeev Saxena
 * @version : 1.0
 * @email : sanrocks123@gmail.com
 */

public class HtmlParserTest {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void testCsvInputFileParser() {

        String basePath = System.getProperty("user.dir");

        List<FileParserAbstract> parsers = new ArrayList<>();
        parsers.add(new CsvFileParserImpl(String.format("%s\\Workbook2.csv", basePath)));
        parsers.add(new PrnFileParserImpl(String.format("%s\\Workbook2.prn", basePath)));

        // runtime polymorphism
        String htmlFile = parsers.stream().filter(p -> p.sourceFileType().equals(SourceFileTypeEnum.CSV)).findAny().get().buildHtmlPage();
        log.info("html file created at : {}", htmlFile);

        // place assertions
    }

    @Test
    public void testPrnInputFileParser() {

        String basePath = System.getProperty("user.dir");

        List<FileParserAbstract> parsers = new ArrayList<>();
        parsers.add(new CsvFileParserImpl(String.format("%s\\Workbook2.csv", basePath)));
        parsers.add(new PrnFileParserImpl(String.format("%s\\Workbook2.prn", basePath)));

        // runtime polymorphism
        String htmlFile = parsers.stream().filter(p -> p.sourceFileType().equals(SourceFileTypeEnum.PRN)).findAny().get().buildHtmlPage();
        log.info("html file created at : {}", htmlFile);

        // place assertions
    }

    @Test
    public void testHtmlServiceImpl() {
        HtmlPageGeneratorService html = new HtmlPageGeneratorServiceImpl();

        CustomerDto c1 = new CustomerDto();
        c1.setName("Sanjeev");
        c1.setAddress("INDIA");
        c1.setPostcode("2345FG");
        c1.setPhone("+91 9900987");
        c1.setCreditLimit("1000");
        c1.setBirthdate("19902511");
        String htmlFile = html.buildHtmlPage(Arrays.asList(c1));
        log.info("html file created at : {}", htmlFile);

        // place assertions
    }


}
