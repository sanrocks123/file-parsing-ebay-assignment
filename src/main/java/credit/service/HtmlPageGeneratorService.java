package credit.service;

import credit.dto.CustomerDto;

import java.util.List;

/**
 * Java Source HtmlPageGenerator created on 11/17/2021
 *
 * @author : Sanjeev Saxena
 * @version : 1.0
 * @email : sanrocks123@gmail.com
 */

public interface HtmlPageGeneratorService {

    /**
     * API contract for html page generation
     *
     * @param customers
     * @return html file path
     */
    public String buildHtmlPage(final List<CustomerDto> customers);
}
