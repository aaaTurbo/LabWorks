//Вариант 41522
public class Main {

    public static void randFillingArr(float[] arr, int min, int max){
        for (int i=0; i< arr.length; i++){
            arr[i]=((float)(Math.random() *(2*max)) +min);
        }
    }

    public static void fillingUsualArr(int[] arr){
        int a=4;
        for (int i=0; i< arr.length; i++){
            arr[i]=a;
            a+=2;
        }
    }

    public static void fillingDoubleArr(double[][] a, float[] c, int[] b){
        for (int i=0; i<a.length; i++){
            for (int j = 0; j<a[i].length; j++){
                if (b[i] == 12) a[i][j] = Math.cbrt(Math.asin(Math.sin(c[j])));
                else if (b[i] == 6 | b[i] == 8 | b[i] == 10) a[i][j] = Math.pow((Math.log10(Math.pow(Math.sin(c[j]), 2))), (3/Math.sin(c[j]) * (Math.pow(c[j]/2,3))+4));
                else a[i][j]=Math.cbrt(Math.sin(Math.cbrt(c[j]/2)));
            }
        }
    }

    public static void printDoubleArr(double[][] a){
        for(int i=0; i<a.length; i++){
            for (int j=0; j<a[i].length; j++){
                System.out.printf("%5.2f", a[i][j]);
                System.out.print(" ");
            }
            System.out.print('\n');
        }
    }

    public static void main(String[] args) {
        int[] a=new int[7];
        float[] x=new float[14];
        fillingUsualArr(a);
        randFillingArr(x, -14, 14);
        double[][] y=new double[7][14];
        fillingDoubleArr(y, x, a);
        printDoubleArr(y);
    }
}
