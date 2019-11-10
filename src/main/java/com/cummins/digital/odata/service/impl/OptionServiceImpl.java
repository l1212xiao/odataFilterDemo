package com.cummins.digital.odata.service.impl;

import com.cummins.digital.odata.service.OptionService;
import org.apache.olingo.server.api.uri.queryoption.QueryOption;
import org.apache.olingo.server.api.uri.queryoption.expression.BinaryOperatorKind;
import org.apache.olingo.server.core.uri.queryoption.FilterOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.SystemQueryOptionImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lvyunxiao
 * @classname OptionServiceImpl
 * @description OptionServiceImpl
 * @date 2019/11/10
 */
@Service
public class OptionServiceImpl implements OptionService {

    private static final Map<BinaryOperatorKind, String> BINARY_OPERATORS = new HashMap<BinaryOperatorKind, String>() {{
        put(BinaryOperatorKind.ADD, " + ");
        put(BinaryOperatorKind.AND, " AND ");
        put(BinaryOperatorKind.DIV, " / ");
        put(BinaryOperatorKind.EQ, " = ");
        put(BinaryOperatorKind.GE, " >= ");
        put(BinaryOperatorKind.GT, " > ");
        put(BinaryOperatorKind.LE, " =< ");
        put(BinaryOperatorKind.LT, " < ");
        put(BinaryOperatorKind.MOD, " % ");
        put(BinaryOperatorKind.MUL, " * ");
        put(BinaryOperatorKind.NE, " <> ");
        put(BinaryOperatorKind.OR, " OR ");
        put(BinaryOperatorKind.SUB, " - ");
    }};

    @Override
    public String parseUriToSql(HttpServletRequest request,
                                HttpServletResponse response,
                                String tbName) {

        String filterValue = request.getParameter("$filter");
        String selectValue = request.getParameter("$select");

        final Enumeration<String> parameterNames = request.getParameterNames();

        if (StringUtils.isEmpty(selectValue)) {
            selectValue = "*";
        }

        final String filterSqlStr = parseOptionNew("$filter", filterValue);

        String fullSQL = "SELECT " + selectValue + " FROM " + tbName + " " + tbName + " WHERE " + filterSqlStr;

        return fullSQL;

    }

    @Deprecated
    private QueryOption parseOptionA(final String optionKey, final String optionValue) {
        SystemQueryOptionImpl systemOption = new FilterOptionImpl();
        systemOption.setText(optionValue);
        return systemOption;
    }

    @Deprecated
    private String parseOption(String optionKey, String optionValue) {

        String eldOpstr = "";
        String opstr = "";

        //Width ge 700
        if ("$filter".equals(optionKey)) {
            final String[] strings = optionValue.split(" ");
            eldOpstr = strings[1];
            if (strings.length >= 3) {
                final BinaryOperatorKind kind = BinaryOperatorKind.get(strings[1]);
                opstr = BINARY_OPERATORS.get(kind);
            }
        }
        return optionValue.replace(eldOpstr, opstr);
    }

    private String parseOptionNew(String optionKey, String optionValue) {

        if ("$filter".equals(optionKey)) {

            final List<String> opCollect = Arrays.stream(BinaryOperatorKind.values())
                    .map(BinaryOperatorKind::toString)
                    .collect(Collectors.toList());

            for (String s : opCollect) {
                if (optionValue.contains(s)) {
                    String string = BinaryOperatorKind.get(s).toString();
                    String stringRep = BINARY_OPERATORS.get(BinaryOperatorKind.get(s));
                    optionValue = optionValue.replace(string, stringRep);
                }
            }
        }

        return optionValue;
    }

}
