 import shape.Square;
 import shape.Triangle;
 import shape.Circle;
 public class MainClass {
 public static void main(String[] args) {
 // Creating objects of Square, Triangle, and Circle
 Square s = new Square(5);
 Triangle t = new Triangle(3, 4);
 Circle c = new Circle(2.5);
 // Printing areas and perimeters of the shapes
 System.out.println("Square- Area: " + s.getArea() + ", Perimeter: " +
 s.getPerimeter());
 System.out.println("Triangle- Area: " + t.getArea() + ", Perimeter: " +
 t.getPerimeter());
 System.out.println("Circle- Area: " + c.getArea() + ", Circumference: "
 + c.getCircumference());
 }
}
 
 
 // Inside the same 'shape' folder, create a file named 'Circle.java'
 package shape;
 public class Circle {
 private double radius;
 public Circle(double radius) {
 this.radius = radius;
 }
 public double getArea() {
 return Math.PI * radius * radius;
 }
 public double getCircumference() {
 return 2 * Math.PI * radius;
 }
 }
 
 // Inside a folder named 'shape', create a file named 'Square.java'
 package shape;
 public class Square {
 private double side;
 public Square(double side) {
 this.side = side;
 }
 public double getArea() {
 return side * side;
 }
 public double getPerimeter() {
 return 4 * side;
 }
 }
 
 // Inside the same 'shape' folder, create a file named 'Triangle.java'
 package shape;
 public class Triangle {
 private double base;
private double height;
 public Triangle(double base, double height) {
 this.base = base;
 this.height = height;
 }
 public double getArea() {
 return 0.5 * base * height;
 }
 public double getPerimeter() {
 // Assuming it's an equilateral triangle for simplicity
 return 3 * base;
 }
 }