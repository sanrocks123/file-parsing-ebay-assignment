package credit.fileparsers;

import credit.dto.CustomerDto;
import credit.service.HtmlPageGeneratorService;
import credit.service.HtmlPageGeneratorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Java Source FileParserAbstract created on 11/16/2021
 *
 * @author : Sanjeev Saxena
 * @version : 1.0
 * @email : sanrocks123@gmail.com
 */

public abstract class FileParserAbstract {

    private String filePath;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private HtmlPageGeneratorService htmlSvc = new HtmlPageGeneratorServiceImpl();

    /**
     * @param filePath
     */
    public FileParserAbstract(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return
     */
    public abstract SourceFileTypeEnum sourceFileType();

    /**
     * @return
     */
    public String buildHtmlPage() {
        List<String> lines = readFile();
        List<CustomerDto> customers = applyMapper(lines);
        return htmlSvc.buildHtmlPage(customers);
    }


    /**
     * @return
     */
    private List<String> readFile() {
        log.info("parsing filepath : {}", filePath);

        List<String> lines = new ArrayList<>();
        String line = "";
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Scanner sc = new Scanner(fis);
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                lines.add(line);
            }
            sc.close();
        } catch (IOException e) {
            log.error("error parsing file", e);
            // throw exception if required
        }

        log.info("parsed file, size: {}, contents: {}", lines.size(), lines);
        return lines;
    }

    /**
     * @return
     */
    protected abstract List<CustomerDto> applyMapper(List<String> lines);
}
