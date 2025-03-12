class rectangle {
 int l, b;
 rectangle() {
 l = 0;
 b = 0;
 }
 rectangle(int m) {
 l = m;
 b = m;
 }
 rectangle(int m, int n) {
 l = m;
 b = n;
 }
 int area() {
 return (l * b);
 }
 int area(int a) {
 return (a * a);
 }
 int area(int a, int b) {
 return (a * b);
 }
 }
 class lab1a{
 public static void main(String args[]) {
 int area1;
 rectangle r1 = new rectangle(5, 8);
 area1 = r1.area();
 System.out.println("\n***Constructor overloading and Method
 Overloading***\n");
 System.out.println("\nUsing Constructor Overloading\n");
 System.out.println("Area of Rectangle is" + area1);
 rectangle r2 = new rectangle(25);
 area1 = r2.area();
 System.out.println("Area of Square is:" +area1);
 rectangle r3 = new rectangle();
 area1 = r3.area(5, 10);
System.out.println("\nUsing Method Overloading\n");
 System.out.println("Area of Rectangle is" + area1);
 rectangle r4 = new rectangle();
 area1 = r4.area(18);
 System.out.println("Area of Square is:" +area1);
 }
 }