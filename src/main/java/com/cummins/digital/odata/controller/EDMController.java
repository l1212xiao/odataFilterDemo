/**
 *
 */
package com.cummins.digital.odata.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import com.cummins.digital.odata.service.OptionService;
import org.apache.olingo.server.api.*;
import org.apache.olingo.server.api.processor.EntityCollectionProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class EDMController.
 *
 * @author rohitghatol
 */
@RestController
@RequestMapping(EDMController.URI)
public class EDMController {

    @Autowired
    OptionService optionService;

    public static final String URI = "/odata";

    @RequestMapping(value = "/{tbName}")
    public String process(HttpServletRequest request, HttpServletResponse response,
                          @PathVariable("tbName") String tbName) {

        return optionService.parseUriToSql(request, response, tbName);

    }
}
