package com.electro.mediator;

import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

public class PayloadCompareMediator extends AbstractMediator {

	// Ideally property name also should be passed in to the mediator.

	public static final String XML_COMPARE_FLAG = "uri.var.xmlcompare";
	private static final String TARGET_PAYLOAD = "TARGETPAYLOAD";
	private static final String SOURCE_PAYLOAD = "SOURCEPAYLOAD";

	public boolean mediate(MessageContext messageContext) {
		String targetPayload = messageContext.getProperty(TARGET_PAYLOAD).toString();
		String sourcePayload = messageContext.getProperty(SOURCE_PAYLOAD).toString();
		XMLComparatorInterop xmlCompare = new XMLComparatorInterop();
		try {
			boolean xmlCompareFlag = xmlCompare.compare(AXIOMUtil.stringToOM(targetPayload),AXIOMUtil.stringToOM(sourcePayload));
			messageContext.setProperty(XML_COMPARE_FLAG,xmlCompareFlag);
		}catch (Exception ex){
			messageContext.setProperty(XML_COMPARE_FLAG,false);
		}
		return true;
	}

}
