# Payload Compare Mediator
## How to use it from WSO2 EI
```
<api xmlns="http://ws.apache.org/ns/synapse" name="xmlcompare" context="/xmlcompare">
   <resource methods="POST" url-mapping="/result">
      <inSequence>
         <property name="TARGETPAYLOAD" expression="$trp:targetPayload"/>
	  <property name="SOURCEPAYLOAD" expression="$trp:sourcePayload"/>
         <log/>
         <class name="com.electro.mediator.PayloadCompareMediator"/>
         <log>
            <property name="CompareXML" expression="$ctx:uri.var.xmlcompare"/>
         </log>
         <respond/>
      </inSequence>
   </resource>
</api>
```

## Positive Testcases
### Test Case 1
```
Target = <RootElement><ChildElement1>Value1</ChildElement1><ChildElement2>value2</ChildElement2></RootElement>
Source = <RootElement><ChildElement1>Value1</ChildElement1><ChildElement2>value2</ChildElement2></RootElement>
```

### Test Case 2
```
Target = <RootElement><ChildElement1>Value1</ChildElement1><ChildElement2>value2</ChildElement2></RootElement>
Source = <RootElement><ChildElement2>Value1</ChildElement2><ChildElement1>value2</ChildElement1></RootElement>
```
## Negative Testcases
### Test Case 3
```
Target = <RootElement><ChildElement1>ValueWrong</ChildElement1><ChildElement2>value2</ChildElement2></RootElement>
Source = <RootElement><ChildElement2>Value1</ChildElement2><ChildElement1>value2</ChildElement1></RootElement>
```

### Test Case 4
```
Target = <RootElement><ChildElement1>Value1</ChildElement1><ChildElement2>value2</ChildElement2></RootElement>
Source = <RootElement><ChildElement2>Value1</ChildElement2><ChildElement1>value2</ChildElement1></RootElement>
```
