interface area {
 float compute(float x, float y);
 }
 class rectangle {
 public float compute(float x, float y) {
 return (x * y);
 }
 }
 class triangle {
 public float compute(float x, float y) {
 return (x * y / 2);
 }
 }
 class result extends rectangle implements area {
 public float compute(float x, float y) {
 return (x * y);
 }
}
 class result1 extends triangle implements area {
 public float compute(float x, float y) {
 return (x * y / 2);
 }
 }
 class lab3b {
 public static void main(String[] args) {
 result rect = new result();
 result1 tri = new result1();
 area a;
 a = rect;
 System.out.println("area of rectangle= " + a.compute(10, 20));
 a = tri;
 System.out.println("area of triangle " + a.compute(10, 20));
 }
 }