package credit.fileparsers;

import credit.dto.CustomerDto;
import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Java Source PrnFileParserImpl created on 11/16/2021
 *
 * @author : Sanjeev Saxena
 * @version : 1.0
 * @email : sanrocks123@gmail.com
 */

public class PrnFileParserImpl extends FileParserAbstract {

    public PrnFileParserImpl(String filePath) {
        super(filePath);
    }

    /**
     * @return
     */
    @Override
    public SourceFileTypeEnum sourceFileType() {
        return SourceFileTypeEnum.PRN;
    }


    /**
     * @return
     */
    @Override
    protected List<CustomerDto> applyMapper(List<String> lines) {
        Map<String, Integer> map = getHeaderColSpan(lines);
        StringBuilder sb = new StringBuilder();

        return lines.stream().map(line -> {
            CustomerDto customer = new CustomerDto();
            sb.append(line);
            customer.setName(getValue(sb, map, "Name"));
            customer.setAddress(getValue(sb, map, "Address"));
            customer.setPostcode(getValue(sb, map, "Postcode"));
            customer.setPhone(getValue(sb, map, "Phone"));
            customer.setCreditLimit(getValue(sb, map, "Credit") + getValue(sb, map, "Limit"));
            customer.setBirthdate(formatDOB(getValue(sb, map, "Birthday")));
            sb.setLength(0);
            return customer;
        }).collect(Collectors.toList());
    }

    /**
     * @param birthday
     * @return
     */
    private String formatDOB(String birthday) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(birthday, formatter);
        DecimalFormat df = new DecimalFormat("00");
        return String.format("%s/%s/%s", df.format(Double.valueOf(date.getDayOfMonth())),
                df.format(Double.valueOf(date.getMonthValue())), date.getYear());
    }

    /**
     * @param sb
     * @param map
     * @param key
     * @return
     */
    private String getValue(StringBuilder sb, Map<String, Integer> map, String key) {
        String value = sb.substring(0, map.get(key));
        sb.delete(0, map.get(key) + 1);
        return value.trim();
    }

    /**
     * @param lines
     * @return
     */
    private Map<String, Integer> getHeaderColSpan(List<String> lines) {

        String header = lines.remove(0);
        Map<String, Integer> map = new HashMap<>();

        String[] splitHeader = header.split(" ");
        String previous = null;
        String current = null;

        for (int i = 0; i < splitHeader.length; i++) {
            current = splitHeader[i];
            if (StringUtils.isEmpty(current)) {
                map.computeIfPresent(previous, (k, v) -> v + 1);
            } else {
                map.put(current, current.length());
                previous = current;
            }
        }

        log.info("map: {}", map);
        return map;
    }
}
