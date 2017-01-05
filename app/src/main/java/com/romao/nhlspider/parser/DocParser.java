package com.romao.nhlspider.parser;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import java.util.List;

/**
 * Created by rpiontkovsky on 1/3/2017.
 */

public abstract class DocParser {

    protected final Document document;
    protected final XPathFactory xPathFactory;

    public DocParser(final Document document) {
        this.document = document;
        xPathFactory = XPathFactory.instance();
    }

    protected List<Element> selectNodes(String xPath) {
        XPathExpression<Element> expr = xPathFactory.compile(xPath, Filters.element());
        return expr.evaluate(document);
    }

    protected Element selectFirst(String xPath) {
        return select(xPath, 0);
    }

    protected Element select(String xPath, int index) {
        List<Element> result = selectNodes(xPath);
        return result == null || result.isEmpty() || result.size() <= index ? null : result.get(index);
    }

}
