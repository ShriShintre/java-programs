 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStreamReader;
 class StringHandler {
 public static void main(String[] args) throws IOException {
 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 String s1, s2, s3, s4, s5;
 int i, l;
 s2 = " ";
 System.out.println("enter the string");
 s1 = br.readLine();
 System.out.println("enterd string is" + s1);
 System.out.println("length of the string is" + s1.length());
 StringBuffer sb = new StringBuffer(s1);
 System.out.println("capacity of string buffer" + sb.capacity());
 l = s1.length();
 if (l == 0)
 System.out.println("string is empty cannot be reversed");
 else {
 for (i = 1-1; i >= 0; i--) {
 s2 = s2 + s1.charAt(i);
 }
 System.out.println("the reversed string is" + s2);
 s3 = s2.toUpperCase();
 System.out.println("upper case reversing string is" + s3);
 System.out.println("enter new string");
 s4 = br.readLine();
 System.out.println("the enterd new string is" + s4);
 StringBuffer sb1 = new StringBuffer(s4);
 s5 = sb1.append(s3).toString();
System.out.println("the append string is" + s5);
 }
 }
 }