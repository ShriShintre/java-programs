class SuperClass {
 int a, b;
 SuperClass(int x, int y) {
 a = x;
 b = y;
 }
 void show() {
 System.out.println("in super class");
 System.out.println("a and b are" + a + " " + b);
 }
 }
 class SubClass extends SuperClass {
 int ans, add;
 SubClass(int a, int b, int c) {
 super(a, b);
 ans = c;
 }
 void show() {
 System.out.println("in sub class");
 System.out.println("c value is " + ans);
 super.show();
add = a + b + ans;
 System.out.println("addition of a b and c " + add);
 }
 }
 class lab3a {
 public static void main(String[] args) {
 SubClass ob = new SubClass(10, 20, 30);
 ob.show();
 }
 }