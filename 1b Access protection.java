 class outer{
 int outdata = 10;
 void display() {
 inner inobj= new inner();
 System.out.println("Accessing from outer class");
 System.out.println("The value of outdata is " + outdata);
 System.out.println("The value of indata is " + inobj.indata);
 }
 class inner {
 int indata = 20;
 void inmethod() {
 System.out.println("Accessing from inner class");
 System.out.println("The sum of indata & outdata is " + (outdata +
 indata));
 }
 }
 }
 class lab1b{
 public static void main(String args[]) {
 outer outobj = new outer();
 outobj.display();
 outer.innerinobj1 = outobj.new inner();
inobj1.inmethod();
 }
 }