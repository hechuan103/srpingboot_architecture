package com.hc.architecture.webconf.convert;

import com.hc.architecture.config.common.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: hechuan
 * @Date: 2020/5/22 16:34
 * @Description: 日期
 */
public class EasiDateConverter implements Converter<String, Date> {

    private final static Logger logger = LoggerFactory.getLogger(EasiDateConverter.class);

    private final static String YYYY_MM = "YYYY-MM";
    private final static String YYYY_MM_DD = "YYYY-MM-DD";
    private final static String YYYY_MM_DD_HH_MM = "YYYY-MM-DD HH:mm";
    private final static String YYYY_MM_DD_HH_MM_SS = "YYYY-MM-DD HH:mm:ss";


    @Override
    public Date convert(String dateStr) {

        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        try {
            //正则匹配
            if (dateStr.matches("^\\d{4} - \\d{1,2}$")) {
                return parseDate(dateStr, YYYY_MM);
            } else if (dateStr.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
                return parseDate(dateStr, YYYY_MM_DD);
            } else if (dateStr.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}$")) {
                return parseDate(dateStr, YYYY_MM_DD_HH_MM);
            } else if (dateStr.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
                return parseDate(dateStr, YYYY_MM_DD_HH_MM_SS);
            } else {
                throw new BizException("date.converter.error", "未匹配到日期格式", "EasiDateConverter");
            }

        } catch (ParseException parseException) {
            throw new BizException("date.converter.error", "日期转换异常", "EasiDateConverter");
        }
    }

    private Date parseDate(String dateStr, String pattern) throws ParseException {
        logger.info("日期转换==datasTr= {}", dateStr);

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        return dateFormat.parse(dateStr);
    }


}
