package com.gmail.bi0qaw.dragonSphereZ.shape;

public class Shape {
	
	public enum ShapeType {
		SHAPE, ELLIPSE, ELLIPSOID, LINE, PLANE, POINT, TRIANGLE;
	}
	
	public ShapeType getShapeType() {
		return ShapeType.SHAPE;
	}	
	
}
