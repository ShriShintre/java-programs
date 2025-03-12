 import java.io.*;
 class p10
 {
 public static void main(String[] args) {
 FileInputStream Fin = null;
 FileOutputStream Fout = null;
 int i, j;
 if (args.length != 2) {
 System.out.println("Copying.,.,.,");
 return;
 }
 try {
 Fin = new FileInputStream(args[0]);
 Fout = new FileOutputStream(args[1]);
 do {
 i = Fin.read();
 if (i !=-1)
 Fout.write(i);
 } while (i !=-1);
 System.out.println("\nCopied!");
 } catch (FileNotFoundException e) {
 e.printStackTrace();
 } catch (IOException e) {
 e.printStackTrace();
 }
 try {
 Fin.close();
 Fout.close();
 } catch (IOException e) {
 System.out.print("\nError while closing.,.,\n");
 }
 }
 }
