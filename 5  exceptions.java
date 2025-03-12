import java.io.File;
 import java.io.FileNotFoundException;
 import java.util.Scanner;
 public class exceptiondemo {
 public static void main(String[] args) {
 AIOOBE();
 FNFE();
NFE();
 }
 public static void AIOOBE() {
 try {
 int[] arr = { 1, 2, 3 };
 System.out.println(arr[3]); // Trying to access index 3 which is out
 of bounds
 } catch (ArrayIndexOutOfBoundsException e) {
 System.out.println("ArrayIndexOutOfBoundsException caught: " +
 e.getMessage());
 }
 }
 public static void FNFE() {
 try {
 File file = new File("nonexistentfile.txt");
 Scanner scanner = new Scanner(file); // Attempting to read from a
 non-existent file
 } catch (FileNotFoundException e) {
 System.out.println("FileNotFoundException caught: " +
 e.getMessage());
 }
 }
 public static void NFE() {
 try {
 String str = "abc";
 int num = Integer.parseInt(str); // Trying to parse a non-numeric
 string
 } catch (NumberFormatException e) {
 System.out.println("NumberFormatException caught: " +
 e.getMessage());
 }
 }
 }