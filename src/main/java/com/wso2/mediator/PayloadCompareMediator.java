package com.wso2.mediator;

import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

public class PayloadCompareMediator extends AbstractMediator {

	// Ideally property name also should be passed in to the mediator.

	public static final String XML_COMPARE_FLAG = "uri.var.xmlcompare";

	public boolean mediate(MessageContext messageContext) {
		String targetPayload = "<a><b>test</b><c>test2</c></a>";
		String sourcePayload = "<a><c>test</c><b>test2</b></a>";
		XMLComparatorInterop xmlCompare = new XMLComparatorInterop();

		try {
			boolean xmlCompareFlag = xmlCompare.compare(AXIOMUtil.stringToOM(targetPayload),AXIOMUtil.stringToOM(sourcePayload));
			messageContext.setProperty(XML_COMPARE_FLAG,xmlCompareFlag);

		}catch (Exception ex){
			messageContext.setProperty(XML_COMPARE_FLAG,false);
		}

		return true;
	}

//	public static void main(String[] args) {
//		String targetPayload = "<a><b>test</b><c>test2</c></a>";
//		String sourcePayload = "<a><d>test2</c><b>test</b></a>";
//		try {
//			OMElement targetOME = AXIOMUtil.stringToOM(targetPayload);
//			OMElement sourceOME = AXIOMUtil.stringToOM(sourcePayload);
//			XMLComparatorInterop xmlCompare = new XMLComparatorInterop();
//			System.out.println(xmlCompare.compare(targetOME,sourceOME));
//		}catch (Exception e){
//		}
//	}
}
