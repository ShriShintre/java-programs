 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStreamReader;
 class LessBalanceException extends Exception {
 LessBalanceException(double amt) {
 System.out.println("withdrawing " + amt + "is invalid");
}
 }
 class Account {
 static int count = 0;
 int accno;
 double bal;
 String name;
 Account(double bal, String n, int accno) {
 System.out.println("new account opend");
 this.bal = bal;
 count++;
 System.out.println("account holder name " + n);
 name = n;
 System.out.println("your account is " + accno);
 this.accno = accno;
 System.out.println("total no of accounts " + count);
 }
 void deposit(double amt) {
 System.out.println("available balence "+ bal);
 bal = bal + amt;
 System.out.println("rs. " + amt + " /- created");
 System.out.println("balance : " + bal);
 }
 void withdraw(double amt) throws LessBalanceException {
 System.out.println(" available balence : " + bal);
 bal-= amt;
 if (bal < 500) {
 bal += amt;
 throw new LessBalanceException(amt);
 }
 System.out.println("rs. " + amt + "/-debited");
 System.out.println("balance " + bal);
 }
 void balance() {
 System.out.println("customer information");
 System.out.println("customer name " + name);
 System.out.println("account number " + accno);
 System.out.println("balance " + bal);
 }
 }
class p4 {
 static int i = 0;
 public static void main(String args[]) throws IOException {
 Account ob[] = new Account[10];
 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 double amt;
 String name;
 int ch, accno, k;
 boolean t = false;
 while (true) {
 System.out.println("bank transaction");
 System.out.println("1.opening new account \n2.deposit \n3.withdraw
 \n4.balence \n5.exit");
 System.out.println("enter your choice ");
 ch = Integer.parseInt(br.readLine());
 switch (ch) {
 case 1:
 System.out.println("opening new account ");
 System.out.println("enter your name ");
 name = br.readLine();
 System.out.println("enter account number ");
 accno = Integer.parseInt(br.readLine());
 System.out.println("enter initial amount(to be>=500) ");
 amt = Double.parseDouble(br.readLine());
 if (amt < 500)
 System.out.println("you cannot create an account with
 less than rs.500/-");
 else {
 ob[i] = new Account(amt, name, accno);
 i++;
 }
 break;
 case 2:
 System.out.print("\nEnter Account number : ");
 accno = Integer.parseInt(br.readLine());
 for (k = 0; k < i; k++)
 if (accno == ob[k].accno) {
 t = true;
 break;
 }
 if (t) {
 System.out.print("\nEnter the Amount for Deposit : ");
 amt = Double.parseDouble(br.readLine());
 ob[k].deposit(amt);
 } else
System.out.println("Invalid Account Number...!!!");
 t = false;
 break;
 case 3:
 System.out.print("\nEnter Account number : ");
 accno = Integer.parseInt(br.readLine());
 for (k = 0; k < i; k++)
 if (accno == ob[k].accno) {
 t = true;
 break;
 }
 if (t) {
 System.out.print("\nEnter the Amount for Withdraw : ");
 amt = Double.parseDouble(br.readLine());
 try {
 ob[k].withdraw(amt);
 } catch (LessBalanceException e) {
 }
 } else
 System.out.println("Invalid Account Number...!!!");
 t = false;
 break;
 case 4:
 System.out.print("\nEnter Account number : ");
 accno = Integer.parseInt(br.readLine());
 for (k = 0; k < i; k++)
 if (accno == ob[k].accno) {
 t = true;
 break;
 }
 if (t) {
 // System.out.println(accno +" asdfsdf " +ob[k].accno);
 ob[k].balance();
 } else
 System.out.println("Invalid Account Number...!!!");
 t = false;
 break;
 case 5:
 System.exit(1);
 default:
 System.out.println("Invalid Choice !!!");
 }
 }
}
 }