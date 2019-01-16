import domain.Student;

import java.io.*;
import java.nio.Buffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestMain {

    public static void main(String[] args) {
        //System.out.println(reverString("sulongfei"));
        //reCode("苏龙飞");
        //getDataTime();
        try {
            //copyFile2("E:\\Users\\sulongfei\\Desktop\\新建文本文档.txt", "E:\\Users\\sulongfei\\Desktop\\新建文本文档test.txt");
            //findWord("E:\\Users\\sulongfei\\Desktop\\新建文本文档.txt","批改");



            File file = new File("E:\\Users\\sulongfei\\Desktop");
            //showFile(file,0);
            //test2();
            System.out.print(test3(50));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test1() {
        Integer f1 = 100, f2 = 100, f3 = 150, f4 = 150;
        System.out.println(f1 == f2);
        System.out.print(f3 == f4);
    }

    public static String reverString(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        return reverString(str.substring(1)) + str.charAt(0);
    }

    public static void reCode(String str1) {
        try {
            String str = new String(str1.getBytes("GB2312"), "ISO-8859-1");
            System.out.println(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void getDataTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(cal.getTime()));
    }

    public static void listSort() {
        List<Student> list = new ArrayList<Student>();
        list.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return 0;
            }
        });

        Collections.sort(list, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return 0;
            }
        });
    }

    public static void copyFile(String fileName1, String fileName2) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(fileName1);
            fos = new FileOutputStream(fileName2);
            int line;
            byte[] bt = new byte[1024 * 4];
            while ((line = fis.read(bt)) != -1) {
                fos.write(bt, 0, line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyFile2(String file1, String file2) throws Exception {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fis = new FileInputStream(file1);
            fos = new FileOutputStream(file2);
            in = fis.getChannel();
            out = fos.getChannel();
            in.transferTo(0,in.size(),out);
        } finally {
            fis.close();
            fos.close();
        }
    }

    public static void findWord(String file1,String word) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file1),"GB2312"));
        String readLine = null;
        int count = 0;
        while ((readLine = br.readLine())!=null){
            int index = -1;
            while (readLine.length()>word.length()&&(index=readLine.indexOf(word))>=0){
                count++;
                readLine = readLine.substring(word.length()+index);
            }
        }
        System.out.println(count);
    }

    public static void showFile(File file,int level)throws Exception{
        for (int i=0;i<level;i++){
            System.out.print("==");
        }
        System.out.println(file.getName());
        if (file.isDirectory()){
            for (File file1 : file.listFiles()) {
                showFile(file1,level+1);
            }
        }
    }

    public static int factorial(int n){
        if(n<=0){
            return 0;
        }
        if(n==1){
            return n;
        }else{
            return n*factorial(n-1);
        }
    }
    public static void test2(){
        int m=0;
        for(int i=1;i<=4;i++){
            for (int j=1;j<=4;j++){
                for (int k=0;k<=4;k++){
                    if(i!=j && j!=k && i!=k){
                        m++;
                        System.out.print(""+i+j+k+",");
                    }
                }
            }
        }
        System.out.print(m);
    }

    public static int test3(int n){
        if(n<2){
            return n;
        }
        return test3(n-1)+test3(n-2);
    }
}
