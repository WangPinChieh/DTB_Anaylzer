package com;

import java.util.HashMap;
import java.util.Map;

public class Attribute {
	private String attributeName;
	private int attributeIndex;
	private int attributeType; // 0 for continuous, 1 for discrete, 2 for target, 4 for ignore
	private Map<String, Integer[]> statistics; 
	public Attribute(String attributeName, int attributeIndex){
		this.attributeName = attributeName;
		this.attributeIndex = attributeIndex;
		statistics = new HashMap<String, Integer[]>();
	}
	public Map<String, Integer[]> getStatistics() {
		if(statistics==null)
			statistics = new HashMap<String, Integer[]>();
			
		return statistics;
	}
	public void setStatistics(Map<String, Integer[]> statistics) {
		this.statistics = statistics;
	}
	public int getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(int attributeType) {
		this.attributeType = attributeType;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public int getAttributeIndex() {
		return attributeIndex;
	}
	public String toString(){
		return attributeName;
	}
}
