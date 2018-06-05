package com.wso2.mediator;

import org.apache.synapse.MessageContext; 
import org.apache.synapse.mediators.AbstractMediator;

public class PayloadCompareMediator extends AbstractMediator { 
	
	public boolean mediate(MessageContext messageContext) {
		
		return true;
	}
}
