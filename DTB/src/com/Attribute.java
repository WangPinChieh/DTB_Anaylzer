package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Attribute {
	public static final int CONTINUOUS=0;
	public static final int DISCRETE=1;
	public static final int TARGET=2;
	public static final int IGNORE=3;
	protected String attributeName;
	private int attributeIndex;
	private int attributeType; // 0 for continuous, 1 for discrete, 2 for target, 4 for ignore
	private Map<String, List<Double>> continuousStatistics; 
	private Map<String, Integer[]> discreteStatistics; 
	
	public Attribute(String attributeName, int attributeIndex){
		this.attributeName = attributeName;
		this.attributeIndex = attributeIndex;
	}
	public Map<String, List<Double>> getContinuousStatistics() {
		if(continuousStatistics==null && attributeType==Attribute.CONTINUOUS)
			continuousStatistics = new HashMap<String, List<Double>>();
			
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
		String[] keys = discreteStatistics.keySet().toArray(new String[0]);
		return discreteStatistics.get(keys[0])[0];
	}
	public int getTargetBCount(){
		String[] keys = discreteStatistics.keySet().toArray(new String[0]);
		return discreteStatistics.get(keys[1])[0];
	}
	
	public double getAttributeIV(){
		int targetACount=0;
		int targetBCount=0;
		List<Double> IVList = new ArrayList<Double>();
		
		if(attributeType == Attribute.DISCRETE)
		{
			for(String key : discreteStatistics.keySet()){
				targetACount+=discreteStatistics.get(key)[0];
				targetBCount+=discreteStatistics.get(key)[1];
			}
			if(targetACount<=targetBCount) // means target A is an important target
			{
				System.out.println("Target A is an importatn target");
				for(String key : discreteStatistics.keySet()){
					double targetAPercentage = (discreteStatistics.get(key)[0]/targetACount);
					double targetBPercentage = (discreteStatistics.get(key)[1]/targetBCount);
					double woe = Math.log(targetBPercentage/targetAPercentage);
					IVList.add((targetBPercentage-targetAPercentage)*woe);
				}
			}
			else {
				System.out.println("Target B is an importatn target");
				for(String key : discreteStatistics.keySet()){
					double targetAPercentage = (discreteStatistics.get(key)[0]*1.0/targetACount);
					double targetBPercentage = (discreteStatistics.get(key)[1]*1.0/targetBCount);
					double woe = Math.log(targetAPercentage/targetBPercentage);
					IVList.add((targetAPercentage-targetBPercentage) * woe);
				}
			}
			double sum=0;
			for(Double iv:IVList) sum+=iv;
			
			return sum;		
		}
		
		else		
		return 0.0;
	}
	
	public double getAttributeImportance(){
		if(attributeType==Attribute.CONTINUOUS){
			double targetAMean=0.0;
			double targetBMean=0.0;
			double targetAStd=0.0;
			double targetBStd=0.0;
			double targetASum=0.0;
			double targetBSum=0.0;
			double squareADifference=0.0;
			double squareBDifference=0.0;
			String[] keys = continuousStatistics.keySet().toArray(new String[0]); 
			for(Double d : continuousStatistics.get(keys[0])){
				targetAMean += d;
			}
			targetAMean = targetAMean/continuousStatistics.get(keys[0]).size();
			for(Double d : continuousStatistics.get(keys[1])){
				targetBMean += d;
			}
			targetBMean = targetBMean/continuousStatistics.get(keys[1]).size();
			for(Double d : continuousStatistics.get(keys[0])){
				squareADifference+= Math.pow((d-targetAMean),2);
			}
			for(Double d : continuousStatistics.get(keys[1])){
				squareBDifference+= Math.pow((d-targetBMean),2);
			}
			targetAStd = Math.sqrt(squareADifference/continuousStatistics.get(keys[0]).size());
			targetBStd = Math.sqrt(squareBDifference/continuousStatistics.get(keys[1]).size());
			double importance=0.0;
			try{
				
			 importance = Math.abs(targetAMean-targetBMean)/((targetAStd+targetBStd)/2);
			}catch(Exception e){}
			return importance;
		}
		
		else if(attributeType==Attribute.DISCRETE)
		{
			int targetACount=0;
			int targetBCount=0;
			int targetATotalCount=0;
			int targetBTotalCount=0;
			double targetASupport=0.0;
			double targetBSupport=0.0;
			double targetAConfidence=0.0;
			double targetBConfidence=0.0;
			
			List<Double> AI = new ArrayList<Double>();
			for(String key:discreteStatistics.keySet())
			{
				targetATotalCount+=discreteStatistics.get(key)[0];
				targetBTotalCount+=discreteStatistics.get(key)[1];
			}
			for(String key:discreteStatistics.keySet()){
				targetACount = discreteStatistics.get(key)[0];
				targetBCount = discreteStatistics.get(key)[1];
				targetASupport = targetACount*1.0/targetATotalCount;
				targetBSupport = targetBCount*1.0/targetBTotalCount;
				targetAConfidence = targetASupport/(targetASupport+targetBSupport);
				targetBConfidence = targetBSupport/(targetASupport+targetBSupport);
				
				AI.add(Math.abs(targetAConfidence-targetBConfidence)*((targetACount+targetBCount)*1.0/(targetATotalCount+targetBTotalCount)));
			}
			double importance=0.0;
			for(Double d:AI)
				importance+=d;
			
			return importance;
		}
		
		else return 0.0;
		
		
	}
}
