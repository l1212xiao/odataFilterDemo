package com.cummins.digital.odata.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lvyunxiao
 * @classname OptionService
 * @description OptionService
 * @date 2019/11/10
 */
public interface OptionService {
    String parseUriToSql(HttpServletRequest request,
                         HttpServletResponse response,
                         String tbName);
}
