import java.io.*;

public class Main {
    public static void main(String[] args){
        membership m1 = new membership();
        m1.Signup();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            FileReader fr = new FileReader("BufferedReaderEx1.txt");
            BufferedReader br_f = new BufferedReader(fr);

            int num = Integer.parseInt(br.readLine());
            br.close();

            String line = "";
            for(int i = 0; (line=br_f.readLine()) != null; i++){
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}