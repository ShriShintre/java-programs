 class OddThread extends Thread {
 public void run() {
 for (int i = 1; i <= 10; i += 2) {
 System.out.println("Odd Thread: " + i);
 try {
 Thread.sleep(100); // Sleep for a short while to allow the other
 thread to run
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 }
 }
 }
 class EvenThread extends Thread {
 public void run() {
 for (int i = 2; i <= 10; i += 2) {
 System.out.println("Even Thread: " + i);
 try {
 Thread.sleep(100); // Sleep for a short while to allow the other
 thread to run
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 }
 }
 }
 public class p6b {
 public static void main(String[] args) {
 OddThread oddThread = new OddThread();
 EvenThread evenThread = new EvenThread();
 oddThread.start();
 evenThread.start();
 }
 }