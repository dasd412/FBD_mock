
import java.util.*;

public class Mutant_Generator {
	

    public Set<Long> generateMutantCVR(long constant){//�Ķ���� constant�� xml���� ������ �����
        //����� ��ü ������ ���õ� ����Ʈ ���� �ڵ�

        //�� ����� ��ü Ŭ�������� ���� ����Ʈ ����
        List<Long> substitution=new LinkedList<Long>();
        List<Long>intrusion=new LinkedList<Long>();
        List<Long>omission=new LinkedList<Long>();
        List<Long>transposition=new LinkedList<Long>();



        //������ ����Ʈ�� ������� �ش��ϴ� ������ ��´�.
        substitution=getSubstitution(constant);
        intrusion=getIntrusion(constant);
        omission=getOmission(constant);
        transposition=getTransPosition(constant);


        //������ ���� ������ ��� �ؽ� �¿� ��´�.
        Set<Long>mutants=new HashSet<Long>();

        /*
        HashSet�� ������ ����:
        1.�ߺ��� ���� �ʴ´�.
        2.����Ʈ�� ��µ� ����(�ε���)�� ���� �ʿ����.
        3.���� �� ������ ��� O(1)�� ������ ����.
         */

        mutants.addAll(substitution);
        mutants.addAll(intrusion);
        mutants.addAll(omission);
        mutants.addAll(transposition);



        return mutants;
    }

    private List<Long>separateDigit(long number){

        List<Long>digits=new ArrayList<Long>();
        
        if(number==0) {//0�� �ִ� ���, �Ʒ��� while���� ���� �����Ƿ� GUARD�� �ֽ��ϴ�.
        	digits.add((long) 0);
        }

        while(number>0){

            long remainder=number-(number/10)*10;

            digits.add(0,remainder);

            number/=10;
        }
        
        return digits;

    }


    public List<Long> getTransPosition(long constant) {//�ڸ��� ���� ��ü�Ǵ� ��. ���� ��� 132 <->123, 100000�� ���� ���� ó�� �Ұ�.
        List<Long>transPosition=new ArrayList<Long>();


        List<Long>digits=separateDigit(constant);

        if(digits.size()==1){//�ڸ����� 1���̸� �ڸ������� ��ü�� ���� ����.
           
        }
        else if(digits.size()==2) {//�ڸ����� 2�� ���� �����ε����� ���ε����� ��ü�Ѵ�.
        	
        	long typo=0;
        	typo+=digits.get(digits.size()-1);
        	typo*=10;
        	typo+=digits.get(0);
        	
        	transPosition.add(typo);
        }
        else {//�ڸ����� 3�̻��� ���� ���� �ε����� ���� �ε���, �� �ε����� ���� �ε����� ��ü�Ѵ�. ���� �ε����� 1~��-1 ���̿� �����ϴ� ������ �ε����̴�.
        	
        	int start=0;
        	int end=digits.size()-1;
        	int randomIndex=(int)(Math.random()*(end-1))+1;
        	
        	long typo=makeTypoOfTransPosition(digits,digits.get(start),digits.get(randomIndex));//���� �ε��� �� <->���� �ε��� ��
        	transPosition.add(typo);
        	
        	typo=makeTypoOfTransPosition(digits,digits.get(randomIndex),digits.get(end));//���� �ε��� �� <-> �� �ε��� ��
        	transPosition.add(typo);
        	
        	
        }

        return transPosition;
    }

    private long makeTypoOfTransPosition(List<Long> digits, long a, long b) {//digits[a]<->digits[b] swap �޼���
		// TODO Auto-generated method stub
    	long typo=0;

    	
    	for(int i=0;i<digits.size();i++) {
    		if(digits.get(i)==a) {
    			typo+=b;
    		}
    		else if(digits.get(i)==b) {
    			typo+=a;
    		}
    		else {
    			typo+=digits.get(i);
    		}
    		typo*=10;
    	}
    	
    	typo/=10;
    	
    	
		return typo;
	}

	public List<Long> getOmission(long constant) {//�ڸ��� �ϳ� ����

        List<Long>omissions=new ArrayList<Long>();


        List<Long>digits=separateDigit(constant);

        if(digits.size()==1){//�ڸ����� 1�̸� ������ ���� ����.
            //do nothing
        }
        else if(digits.size()==2) {//�ڸ����� 2�� ���, ���� ���� ������ �Ͱ� ���� ���� ������ ���� ��´�.
        	long startOmitted=digits.get(0);
        	long endOmitted=digits.get(1);
        	omissions.add(startOmitted);
        	omissions.add(endOmitted);
        	
       
        }
        else {//�ڸ����� 3�̻��� ���,
        	/*
        	 �� �� ���� ������ �� 
        	 �� �� ���� ������ ��
        	 �� ���̿� ������ ���� ������ ��
        	 �� ��´�.
        	 */
        	
        	int start=0;
        	int end=digits.size()-1;
        	int randomIndex=(int)(Math.random()*(end-1))+1;//1~end-1 ������ ������ �ε��� �ѹ��� �����Ѵ�.
        	
        	long typo=makeTypoOfOmission(digits,start);//0��° ���� ������ ��Ÿ ����

        		omissions.add(typo);
        	
        	
        	
        	 typo=makeTypoOfOmission(digits,randomIndex);
        
        		omissions.add(typo); 
        	 
        	
        	
        	
        	 typo=makeTypoOfOmission(digits,end);//������ ���� ������ ��Ÿ ����
        	
        		 omissions.add(typo);
        	 
        	
        	
        	
        	
        	
        	
        	
        }
        
        return omissions;
    }

    private long makeTypoOfOmission(List<Long> digits, int index) {//�ڸ����迭���� index�� ������ ���� �����ϴ� �޼���. ������ ��Ÿ ���� �޼���.
		// TODO Auto-generated method stub
    	
    	long typo=0;
    	
    	for(int i=0;i<digits.size();i++) {
    		if(i!=index) {//index �Ķ���Ͱ� �ƴϸ�, 
    			typo+=digits.get(i);
    			typo*=10;
    		}
    	}
    	typo/=10;
    	
		return typo;
	}

	public List<Long> getIntrusion(long constant) {//�Ȱ��� Ű�� �ѹ� �� ������ ���
    
        List<Long>intrusions=new ArrayList<Long>();


        List<Long>digits=separateDigit(constant);
        
 
        if(digits.size()==1){//�ڸ����� 1���� ��� �� ���� 1�� �� �߰��ϸ� �ȴ�.
            long typo=digits.get(0)*10+digits.get(0);
            intrusions.add(typo);
        }
        else if(digits.size()<=3){//�ڸ����� 2~3���� ���� �ڸ����� ���� ���� �����Ƿ� �� �ڸ����� ���� intrusion ����.

            for (int i = 0; i <digits.size() ; i++) {

                long typo=makeTypoOfIntrusions(digits,i);
                
                intrusions.add(typo);//intrusion ���� ��Ÿ�� ����Ʈ�� �ִ´�.


            }//i

        }
        else {//�ڸ����� 3���� ������, ó��, ��, �� �ܿ� ������ �ϳ��� ��� intrusion�� �����Ѵ�.
        	int start=0;//ó�� �ε���
        	int end=digits.size()-1;//�� �ε���
        	int randomIndex=(int) (Math.random()*(end-1))+1;//1��° �ε��� ~ end-1��° �ε��� �� �ϳ��� �������� ����.

        	
        	long typo=makeTypoOfIntrusions(digits,start);//0��° ���� �ߺ� Ÿ������ ��Ÿ ����
        	intrusions.add(typo);
        	
        	typo=makeTypoOfIntrusions(digits,end);//������ ���� �ߺ� Ÿ������ ��Ÿ ����
        	intrusions.add(typo);
        	
        	typo=makeTypoOfIntrusions(digits,randomIndex);
        	intrusions.add(typo);

              
                
 
        
        	
        }
        return intrusions;

    }
    
    private long makeTypoOfIntrusions(List<Long>digits,int index) {//�ڸ������� index�� �ش��ϴ� �κп� intrusion�� �ϴ� �޼���.
    	
        long  typo=0;
        for (int j =0; j <digits.size(); j++) {
            if(j==index) {//����, ���� �ε����� ���ٸ�, intrusion �����Ѵ�.

                typo+=digits.get(j);
                typo*=10;

            }
            typo+=digits.get(j);
            typo*=10;


        }//j


        typo/=10;
    	
    	return typo;
    }
    
    

    public List<Long> getSubstitution(long constant) {
        List<Long>substitutions=new ArrayList<Long>();


        List<Long>digits=separateDigit(constant);

        return substitutions;
    }

}
