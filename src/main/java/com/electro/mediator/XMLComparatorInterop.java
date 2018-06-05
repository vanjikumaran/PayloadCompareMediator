package com.electro.mediator;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.Vector;


public class XMLComparatorInterop {
    private static final Log log = LogFactory.getLog(XMLComparatorInterop.class);

    private Vector ignorableNamespaceList = new Vector();
    public boolean compare(OMElement targetPayload, OMElement sourcePayload) {

        boolean status = false;

        if (isIgnorable(targetPayload) ||
                isIgnorable(sourcePayload)) {
            return true;
        }

        if (targetPayload == null && sourcePayload == null) {
            log.info("Both Elements are null.");
            return true;
        }
        if (targetPayload == null && sourcePayload != null) {
            //failureNotice = "Element One is null and Element Two is not null";
            return false;
        }
        if (targetPayload != null && sourcePayload == null) {
            //failureNotice = "Element Two is null and Element One is not null";
            return false;
        }

        log.info("Now Checking " + targetPayload.getLocalName() + " and " +
                sourcePayload.getLocalName() +
                "=============================");

        log.info("Comparing Element Names .......");
        status = compare(targetPayload.getLocalName(), sourcePayload.getLocalName());
        if (!status)
            return false;

        log.info("Comparing Namespaces .........");
        status = compare(targetPayload.getNamespace(),
                sourcePayload.getNamespace());
        if (!status)
            return false;

        log.info("Comparing attributes .....");
        status = compareAllAttributes(targetPayload, sourcePayload);
        if (!status)
            return false;

        log.info("Comparing texts .....");

        status = compare(
                targetPayload.getText().trim(),
                sourcePayload.getText().trim());
        if (!status)
            return false;

        log.info("Comparing Children ......");
        status = compareAllChildren(targetPayload, sourcePayload);

        return status;
    }

    private boolean compareAllAttributes(OMElement targetPayload,
                                         OMElement sourcePayload) {
        boolean status = false;
        status = compareAttibutes(targetPayload, sourcePayload);
        status = compareAttibutes(sourcePayload, targetPayload);
        return status;
    }

    private boolean compareAllChildren(OMElement targetPayload,
                                       OMElement sourcePayload) {
        boolean status = false;
        status = compareChildren(targetPayload, sourcePayload);
        //status =compareChildren(sourcePayload, targetPayload);
        return status;
    }


    private boolean isIgnorable(OMElement elt) {
        if (elt != null) {
            OMNamespace namespace = elt.getNamespace();
            if (namespace != null) {
                return ignorableNamespaceList.contains(namespace.getNamespaceURI());
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    private boolean compareChildren(OMElement targetPayload, OMElement sourcePayload) {
        //ignore if the elements belong to any of the ignorable namespaces list
        boolean status = true;
        if (isIgnorable(targetPayload) ||
                isIgnorable(sourcePayload)) {
            return true;
        }
        Iterator targetPayloadChildren = targetPayload.getChildren();
        while (targetPayloadChildren.hasNext()) {
            OMNode omNode = (OMNode) targetPayloadChildren.next();
            if (omNode instanceof OMElement) {
                OMElement targetPayloadChild = (OMElement) omNode;
                OMElement sourcePayloadChild = null;
                //Do the comparison only if the element is not ignorable
                if (!isIgnorable(targetPayloadChild)) {
                    Iterator sourcePayloadChildren = sourcePayload.getChildren();
                    while (sourcePayloadChildren.hasNext()) {
                        status = false;
                        OMNode node = (OMNode) sourcePayloadChildren.next();
                        if (node.getType() == OMNode.ELEMENT_NODE) {
                            sourcePayloadChild = (OMElement) node;
                            if (sourcePayloadChild.getLocalName()
                                    .equals(targetPayloadChild.getLocalName())) {
                                //Do the comparison only if the element is not ignorable
                                if (!isIgnorable(sourcePayloadChild)) {
                                    if (sourcePayloadChild == null) {
                                        return false;
                                    }
                                }

                                status = compare(targetPayloadChild, sourcePayloadChild);

                            }
                        }
                        if (status) {
                            break;
                        }
                    }
                    if (!status) {
                        return false;
                    }
                } else
                    status = compare(targetPayloadChild, sourcePayloadChild);
            }
        }

        return status;
    }

    private boolean compareAttibutes(OMElement targetPayload, OMElement sourcePayload) {
        int targetPayloadAtribCount = 0;
        int sourcePayloadAtribCount = 0;
        Iterator attributes = targetPayload.getAllAttributes();
        while (attributes.hasNext()) {
            OMAttribute omAttribute = (OMAttribute) attributes.next();
            OMAttribute attr = sourcePayload.getAttribute(omAttribute.getQName());
            if (attr == null) {
                return false;
            }
            targetPayloadAtribCount++;
        }

        Iterator sourcePayloadIter = sourcePayload.getAllAttributes();
        while (sourcePayloadIter.hasNext()) {
            sourcePayloadIter.next();
            sourcePayloadAtribCount++;

        }

        return targetPayloadAtribCount == sourcePayloadAtribCount;
    }

    private boolean compare(String one, String two) {
        return one.equals(two);
    }

    private boolean compare(OMNamespace one, OMNamespace two) {
        if (one == null && two == null) {
            return true;
        } else if (one != null && two == null) {
            return false;
        } else if (one == null && two != null) {
            return false;
        }
        if (!one.getNamespaceURI().equals(two.getNamespaceURI())) {
            return false;
        }
        return true;
    }
}