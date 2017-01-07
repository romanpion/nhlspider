package com.romao.nhlspider.parser;


import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import timber.log.Timber;

/**
 * Created by roman on 02/01/2017.
 */

public class HtmlTextHandler {

    public static Document convertToDocument(String str) {
        String docString = prepareString(str);
        SAXBuilder sb = new SAXBuilder();

        try {
            InputStream stream = new ByteArrayInputStream(docString.getBytes("UTF-8"));
            return sb.build(stream);
        } catch (JDOMException ex) {
            Timber.e(ex, ex.getMessage());
        } catch (IOException ex) {
            Timber.e(ex, ex.getMessage());
        }

        return null;
    }

    public static String prepareString(final String docString) {
        String str = docString;

        str = escapeNonClosingTag(str, "meta");
        str = escapeNonClosingTag(str, "img");
        str = escapeNonClosingTag(str, "link");
        str = escapeNonClosingTag(str, "input");
        str = escapeClosingTag(str, "style");
        str = escapeClosingTag(str, "script");
        str = escapeClosingTag(str, "iframe");
        str = escapeBreaks(str);
        str = escapeNBSP(str);
        str = escapeComments(str);

        str = str.substring(str.indexOf("<html>"), (str.indexOf("</html>") + 7));

        return str;
    }

    private static String escapeComments(final String docString) {
        String str = docString;
        while (str.contains("<!--")) {
            int startIndex = str.indexOf("<!--");
            int endIndex = str.indexOf("-->", startIndex);

            str = str.substring(0, startIndex) + str.substring(endIndex + 3);
        }
        return str;
    }

    private static String escapeNBSP(String str) {
        return str.replaceAll("&nbsp;", " ");
    }

    private static String escapeBreaks(final String str) {
        String tempStr = str.replaceAll("<br>", "\n ").replaceAll("<BR>", " \n ");
        tempStr = tempStr.replaceAll("<br/>", "\n ").replaceAll("<BR/>", "\n ");
        tempStr = tempStr.replaceAll("<br />", "\n ").replaceAll("<BR />", "\n ");
        tempStr = tempStr.replaceAll("<br clear=\"all\">", "\n ").replaceAll("<BR clear=\"all\">", "\n ");
        tempStr = tempStr.replaceAll("<br clear=\"all\"/>", "\n ").replaceAll("<BR clear=\"all\"/>", "\n ");
        tempStr = tempStr.replaceAll("<br clear=\"all\" />", "\n ").replaceAll("<BR clear=\"all\" />", "\n ");
        tempStr = tempStr.replaceAll("<br clear=\"all\" >", "\n ").replaceAll("<BR clear=\"all\" >", "\n ");
        return tempStr;
    }

    private static String escapeNonClosingTag(final String str, final String tagName) {
        String tempStr = str;
        int tagPosition = Math.max(tempStr.indexOf("<" + tagName.toLowerCase()), tempStr.indexOf("<" + tagName.toUpperCase()));
        while (tagPosition >= 0) {
            int closingIndex = tempStr.indexOf(">", tagPosition);
            tempStr = tempStr.substring(0, tagPosition) + tempStr.substring(closingIndex + 1, tempStr.length());
            tagPosition = Math.max(tempStr.indexOf("<" + tagName.toLowerCase()), tempStr.indexOf("<" + tagName.toUpperCase()));
        }
        return tempStr;
    }

    private static String escapeClosingTag(final String str, final String tagName) {
        String tempStr = str;
        int tagStartPosition = getPositiveMin(tempStr.indexOf("<" + tagName.toLowerCase()), tempStr.indexOf("<" + tagName.toUpperCase()));
        while (tagStartPosition >= 0) {
            int tagEndPosition = getPositiveMin(tempStr.indexOf("/" + tagName.toLowerCase() + ">"), tempStr.indexOf("/" + tagName.toUpperCase() + ">"));
            tempStr = tempStr.substring(0, tagStartPosition) + tempStr.substring(tagEndPosition + tagName.length() + 2, tempStr.length());
            tagStartPosition = getPositiveMin(tempStr.indexOf("<" + tagName.toLowerCase()), tempStr.indexOf("<" + tagName.toUpperCase()));
        }
        return tempStr;
    }

    private static int getPositiveMin(final int a, final int b) {
        if (a >= 0 && b >= 0) {
            return Math.min(a, b);
        }
        return Math.max(a, b);
    }
}
