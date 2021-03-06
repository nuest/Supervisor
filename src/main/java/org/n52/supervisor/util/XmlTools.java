/**
 * ﻿Copyright (C) 2013 52°North Initiative for Geospatial Open Source Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.n52.supervisor.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

/**
 * @author Daniel Nüst
 * 
 */
public class XmlTools {

    private static final int DEFAULT_INDENT = 4;

    public static final XmlOptions DEFAULT_OPTIONS = new XmlOptions();

    public static final String GML_NAMESPACE_PREFIX = "gml";

    public static final String GML_NAMESPACE_URI = "http://www.opengis.net/gml/3.2";

    public static final String OGC_NAMESPACE_PREFIX = "ogc";

    public static final String OGC_NAMESPACE_URI = "http://www.opengis.net/ogc";

    public static final String OM_NAMESPACE_PREFIX = "om";

    public static final String OM_NAMESPACE_URI = "http://www.opengis.net/om/1.0";

    public static final String OWS_1_1_NAMESPACE_URI = "http://www.opengis.net/ows/1.1";

    public static final String OWS_NAMESPACE_PREFIX = "ows";

    public static final String SOR_NAMESPACE_PREFIX = "sor";

    public static final String SOR_NAMESPACE_URI = "http://swsl.uni-muenster.de/sor";

    public static final String SOR_SCHEMA_LOCATION = SOR_NAMESPACE_URI
            + " http://giv-genesis.uni-muenster.de/schemas/sor/sorAll.xsd";

    public static final HashMap<String, String> SUGGESTED_PREFIXES;

    public static final String SWE_1_0_1_NAMESPACE_URI = "http://www.opengis.net/swe/1.0.1";

    public static final String SWE_GML_SCHEMA_LOCATION = "http://www.opengis.net/swe/1.0/gml32 http://giv-genesis.uni-muenster.de/schemas/sweCommon/1.0.1_gml32/phenomenon.xsd";

    public static final String SWE_NAMESPACE_PREFIX = "swe";

    public static final String XSI_NAMESPACE_PREFIX = "xsi";

    static {
        SUGGESTED_PREFIXES = new HashMap<String, String>();
        SUGGESTED_PREFIXES.put(SWE_1_0_1_NAMESPACE_URI, SWE_NAMESPACE_PREFIX);
        SUGGESTED_PREFIXES.put(GML_NAMESPACE_URI, GML_NAMESPACE_PREFIX);
        SUGGESTED_PREFIXES.put(SOR_NAMESPACE_URI, SOR_NAMESPACE_PREFIX);
        SUGGESTED_PREFIXES.put(OWS_1_1_NAMESPACE_URI, OWS_NAMESPACE_PREFIX);
        SUGGESTED_PREFIXES.put(OGC_NAMESPACE_URI, OGC_NAMESPACE_PREFIX);
        SUGGESTED_PREFIXES.put(OM_NAMESPACE_URI, OM_NAMESPACE_PREFIX);
        SUGGESTED_PREFIXES.put(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, XSI_NAMESPACE_PREFIX);

        DEFAULT_OPTIONS.setSaveNamespacesFirst();
        DEFAULT_OPTIONS.setSavePrettyPrint();
        DEFAULT_OPTIONS.setSaveSuggestedPrefixes(SUGGESTED_PREFIXES);
        DEFAULT_OPTIONS.setSaveAggressiveNamespaces();
        DEFAULT_OPTIONS.setSavePrettyPrintIndent(DEFAULT_INDENT);
    }

    /**
     * 
     * @param cursor
     */
    public static void insertAttributesForValidationSOR(XmlObject obj) {
        XmlCursor cursor = obj.newCursor();
        cursor.setAttributeText(new QName(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "schemaLocation"),
                                SOR_SCHEMA_LOCATION);
        cursor.toNextToken();
        cursor.insertNamespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
        cursor.dispose();
    }

    /**
     * 
     * @param cursor
     */
    public static void insertAttributesForValidationSweGml(XmlObject obj) {
        XmlCursor cursor = obj.newCursor();
        cursor.setAttributeText(new QName(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "schemaLocation"),
                                SWE_GML_SCHEMA_LOCATION);
        cursor.toNextToken();
        cursor.insertNamespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
        cursor.dispose();
    }

    /**
     * http://xmlbeans.apache.org/docs/2.0.0/guide/conValidationWithXmlBeans.html
     * 
     * @param xml
     * @return
     */
    public static String validateAndIterateErrors(XmlObject xml) {
        ArrayList<XmlError> validationErrors = new ArrayList<XmlError>();
        XmlOptions validationOptions = new XmlOptions();
        validationOptions.setErrorListener(validationErrors);

        boolean isValid = xml.validate(validationOptions);

        StringBuilder sb = new StringBuilder();
        if ( !isValid) {
            sb.append("XmlObject of type ");
            sb.append(xml.getClass().getSimpleName());
            sb.append(" (");
            sb.append(xml.xmlText().substring(0, 60));
            sb.append("...) is NOT valid! The validation errors are:");

            Iterator<XmlError> iter = validationErrors.iterator();
            while (iter.hasNext()) {
                sb.append("\n\t");
                sb.append(iter.next().toString());
            }
        }
        else {
            sb.append("XmlObject ");
            sb.append(xml.getClass().getSimpleName());
            sb.append(" (");
            sb.append(xml.xmlText().substring(0, 40));
            sb.append("...) is valid!");
        }

        return sb.toString();
    }
}