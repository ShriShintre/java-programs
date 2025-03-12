 class Q {
 int n = 0;
 boolean valset = false;
synchronized int get() {
 while (!valset)
 try {
 wait();
 } catch (InterruptedException e) {
 System.out.println("caught");
 }
 valset = false;
 System.out.println("got " + n);
 notify();
 return n;
 }
 synchronized void put(int n) {
 while (valset)
 try {
 wait();
 } catch (Exception e) {
 System.out.println("caught");
 }
 this.n =n;
 System.out.println("put " + n);
 valset = true;
 notify();
 }
 }
 class Producer implements Runnable {
 Q q;
 Producer(Q q) {
 this.q =q;
 Thread t1 = new Thread(this);
 t1.start();
 }
 public void run() {
 int i = 0;
 while (i < 10) {
 q.put(i++);
 }
 }
 }
class Consumer implements Runnable {
 Q q;
 Consumer(Q q) {
 this.q =q;
 Thread t2 = new Thread(this);
 t2.start();
 }
 public void run() {
 while (true) {
 q.get();
 }
 }
 }
 class p5 {
 public static void main(String[] args) {
 System.out.println("main thread starts");
 Q q = new Q();
 new Producer(q);
 new Consumer(q);
 System.out.println("main thread ends");
 }
 }
