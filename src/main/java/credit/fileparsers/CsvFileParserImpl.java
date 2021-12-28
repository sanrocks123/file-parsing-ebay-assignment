package credit.fileparsers;

import credit.dto.CustomerDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Java Source CsvFileParserImpl created on 11/16/2021
 *
 * @author : Sanjeev Saxena
 * @version : 1.0
 * @email : sanrocks123@gmail.com
 */

public class CsvFileParserImpl extends FileParserAbstract {

    public CsvFileParserImpl(String filePath) {
        super(filePath);
    }

    /**
     * @return
     */
    @Override
    public SourceFileTypeEnum sourceFileType() {
        return SourceFileTypeEnum.CSV;
    }

    /**
     * @param lines
     * @return
     */
    @Override
    protected List<CustomerDto> applyMapper(List<String> lines) {
        lines.remove(0);
        return lines.stream().map(line -> {
            CustomerDto customer = new CustomerDto();
            String[] f = line.split(",");
            customer.setBirthdate(f[6]);
            customer.setCreditLimit(f[5]);
            customer.setPhone(f[4]);
            customer.setPostcode(f[3]);
            customer.setAddress(f[2]);
            customer.setName(String.format("%s, %s", f[0].substring(1), f[1].substring(1, f[1].length() - 1)));
            return customer;
        }).collect(Collectors.toList());
    }
}
