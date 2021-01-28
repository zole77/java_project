import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BoardControl {
    public BoardControl() {
    }

    public void Write() {
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            OpenDB A1 = new OpenDB();
            String line = "";
            while(line.equals("EOF") != true ){
                line = br.readLine();
                System.out.println(line);
            }


            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
