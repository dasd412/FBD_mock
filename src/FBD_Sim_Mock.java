
import java.util.*;
import java.io.*;


public class FBD_Sim_Mock {

    public static void main(String[] args) throws IOException {

        FBD_Sim_Mock fbd=new FBD_Sim_Mock();
        Mutant_Generator generator=new Mutant_Generator();

        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));

        long constant=Long.parseLong(br.readLine());//<- 혹시나 9자리 수 이상일 경우 intrusion에서 overflow가 발생하기 때문에 int에서 long으로 바꿈.
        
        
//        List<Long>transpositions=generator.getTransPosition(constant);
//        for(int i=0;i<transpositions.size();i++) {
//        	System.out.println("transposition: "+transpositions.get(i));
//        }
//
//        
//        List<Long>omissions=generator.getOmission(constant);
//        
//        for (int i = 0; i <omissions.size() ; i++) {
//            
//        	System.out.println("omissions: "+omissions.get(i));
//        }
//        
//        
//
//
//        List<Long> intrusion=generator.getIntrusion(constant);
//        
//        for (int i = 0; i <intrusion.size() ; i++) {
//        
//        	System.out.println("intrusions: "+intrusion.get(i));
//        }

        List<Long>subs=generator.getSubstitution(constant);
        for(int i=0;i<subs.size();i++) {
        	System.out.println("subs: "+subs.get(i));
        }



    }
}
