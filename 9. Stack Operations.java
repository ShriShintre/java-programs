import java.io.BufferedReader;
 import java.io.InputStreamReader;
 import java.util.*;
 interface Stackoperations
{
 public void insert();
 public void delete();
 public void display();
 }
 class llist implements Stackoperations
 {
 LinkedList<Integer> list = new LinkedList<Integer>();
 int i;
 int count = 0;
 public void listtest()
 {
 while (true)
 {
 System.out.println("\n---QUEUE MENU---");
 System.out.println("\n 1.Insert 2.Delete 3.Display 4.Exit");
 System.out.println("\nEnter your choice:");
 Scanner s1 = new Scanner(System.in);
 try
 {
 i = s1.nextInt();
 }
 catch (Exception e)
 {
 System.out.println("Invalid choice");
 }
 switch (i)
 {
 case 1:
 insert();
break;
 case 2:
 delete();
 break;
 case 3:
 display();
 break;
 case 4:
 System.exit(1);
 break;
 }
 }
 }
 public void insert()
 {
 System.out.println("Enter the elements");
 Scanner s = new Scanner(System.in);
 try
 {
 list.add(s.nextInt());
 count++;
 }
 catch (Exception e)
 {
 e.printStackTrace();
 }
 }
 public void delete()
 {
 if (list.isEmpty())
 System.out.println("Stack is empty");
 else {
 System.out.println("Element removed is " + list.iterator().next());
System.out.println("\nTotal items in list are : "+--count);
 list.removeFirst();
 }
 }
 public void display()
 {
 Iterator iterator;
 if (list.isEmpty())
 System.out.println("stack is empty");
 else {
 System.out.println("Contents are:");
 iterator = list.iterator();
 while (iterator.hasNext()) {
 System.out.println(iterator.next() +"");
 }
 }
 }
 }
 class listexp {
 public static void main(String args[])
 {
 llist l = new llist();
 l.listtest();
 }
 }