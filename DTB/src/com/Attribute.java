package com;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attribute {
	public static final int CONTINUOUS=0;
	public static final int DISCRETE=1;
	public static final int TARGET=2;
	public static final int IGNORE=3;
	protected String attributeName;
	private int attributeIndex;
	private int attributeType; // 0 for continuous, 1 for discrete, 2 for target, 4 for ignore
	private Map<String, List<Integer>> continuousStatistics; 
	private Map<String, Integer[]> discreteStatistics; 
	
	public Attribute(String attributeName, int attributeIndex){
		this.attributeName = attributeName;
		this.attributeIndex = attributeIndex;
	}
	public Map<String, List<Integer>> getContinuousStatistics() {
		if(continuousStatistics==null && attributeType==Attribute.CONTINUOUS)
			continuousStatistics = new HashMap<String, List<Integer>>();
			
		return continuousStatistics;
	}

	public Map<String, Integer[]> getDiscreteStatistics() {
		if(discreteStatistics == null && (attributeType==Attribute.DISCRETE || attributeType==Attribute.TARGET))
			discreteStatistics = new HashMap<String, Integer[]>();
		
		return discreteStatistics;
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
	public int getTargetACount(){
		String[] key = discreteStatistics.keySet().toArray(new String[0]);
		return discreteStatistics.get(key[0])[0];
	}
	public int getTargetBCount(){
		String[] key = discreteStatistics.keySet().toArray(new String[0]);
		return discreteStatistics.get(key[1])[0];
	}
}
