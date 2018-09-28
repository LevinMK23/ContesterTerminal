package program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Runtime rt = Runtime.getRuntime();
        String dir  = "src/fileSystem/testCase/";
        //generate();
        //ans();
        File file = new File("main.cpp");
        String compileInstruction = "g++ -o studentSolution main.cpp";
        Process process = rt.exec(compileInstruction);
        Scanner log = new Scanner(process.getInputStream());
        while (log.hasNext()) System.out.println(log.nextLine());
        boolean f = true;
        for (int i = 0; i < 40; i++) {
            Scanner stdin = new Scanner(new File(dir + i + ".test"));
            Process process2 = rt.exec("./studentSolution");
            try (PrintWriter wr = new PrintWriter(process2.getOutputStream())) {
                wr.write(stdin.nextInt() + " ");
                wr.write(stdin.nextInt() + " ");
                wr.write(stdin.nextInt() + "\n");
            }
            Scanner tin = new Scanner(process2.getInputStream());
            StringBuilder stAns = new StringBuilder();
            StringBuilder judgeAns = new StringBuilder();
            Scanner j = new Scanner(new File(dir + i + ".ans"));
            while(j.hasNext()) judgeAns.append(j.nextLine());
            judgeAns = new StringBuilder(judgeAns.toString().trim());
            while (tin.hasNext()) stAns.append(tin.nextLine());
            stAns = new StringBuilder(stAns.toString().trim());
            if(judgeAns.toString().equals(stAns.toString())){
                System.out.println("test " + (i + 1) + " passed!");
            }
            else {
                System.out.println("WRONG ANSWER");
                f = false;
                break;
            }
        }
        if(f) System.out.println("Задача решена!");
        else System.out.println("Задача не решена!");
    }

    private static void ans() throws FileNotFoundException {
        String dir  = "src/fileSystem/testCase/";
        for (int i = 0; i < 40; i++) {
            File file = new File(dir + i + ".test");
            Scanner in = new Scanner(file);
            File ans = new File(dir + i + ".ans");
            if (!file.exists()) {
                try {
                    boolean f = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try(PrintWriter writer = new PrintWriter(ans)){
                writer.write(String.valueOf(in.nextLong() + in.nextLong() + in.nextLong()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void generate(){
        String dir  = "src/fileSystem/testCase/";
        Random r = new Random();
        for (int i = 0; i < 40; i++) {
            File file = new File(dir + i + ".test");
            if (!file.exists()) {
                try {
                    boolean f = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try(PrintWriter writer = new PrintWriter(file)){
                writer.write(r.nextInt(1000000000) + " "
                                + r.nextInt(1000000000) + " "
                                + r.nextInt(1000000000)
                            );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
