
import java.util.*;
import java.io.*;


public class FBD_Sim_Mock {

    public static void main(String[] args) throws IOException {

        FBD_Sim_Mock fbd=new FBD_Sim_Mock();
        Mutant_Generator generator=new Mutant_Generator();

        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));

        long constant=Long.parseLong(br.readLine());//<- Ȥ�ó� 9�ڸ� �� �̻��� ��� intrusion���� overflow�� �߻��ϱ� ������ int���� long���� �ٲ�.
        
        
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
