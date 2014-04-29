package com;

public class Attribute {
	private String attributeName;
	private int attributeIndex;
	private int attributeType; // 0 for continuous, 1 for discrete
	public Attribute(String attributeName, int attributeIndex){
		this.attributeName = attributeName;
		this.attributeIndex = attributeIndex;
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
