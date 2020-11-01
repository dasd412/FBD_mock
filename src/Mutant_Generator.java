
import java.util.*;
    /*
	 * ����� ��ü ����Ʈ ����
	 */
public class Mutant_Generator {
	
	private class AdjacentKeys{//�� Ű�� ���� ������ ���� Ű�� ������ ��� �ִ� ��ųʸ� Ŭ����
		/*
		 * ������ Ű��� ������ pc���� �����ϴ� ���� Ű����(������ ��ġ)��
		 *  pc,��Ʈ�� ��� �����ϴ� ���� Ű����(���� ��ġ) ��θ� ������ ���Դϴ�.
		 *  
		 *  ���� ���ϸ� ������ Ű�� ��, substitution�� �Ͼ Ȯ���� �� 70%��� �����ֽ��ϴ�.
 		 */
		
		private int[]ZERO= {1,2,3,9};
		private int[]ONE= {0,2,3,4,5};
		private int[]TWO= {0,1,3,4,5,6};
		private int[]THREE={0,2,4,5,6};
		private int[]FOUR= {1,2,3,5,7,8};
		private int[]FIVE= {1,2,3,4,5,6,7,8,9};
		private int[]SIX= {2,3,5,7,8,9};
		private int[]SEVEN= {4,5,6,8};
		private int[]EIGHT= {4,5,6,7,9};
		private int[]NINE= {5,6,8,0};
		
		public int getAdjacentKey(long number) {//�Ķ���ͷ� ���� ������ ���� ������ Ű�� �����ϰ� �����ϴ� �޼ҵ�. 
			int picked=0;
			switch((int)number) {
			
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			}
			return picked;
		}
	}
	
	private AdjacentKeys adjKey;
	
	public Mutant_Generator() {
		this.adjKey=new AdjacentKeys();
	}
	

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
    
    /*
     * ����� �ڸ����� ������
     */

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

    /*
     * �ڸ��� ��ü�ϱ�
     */

    public List<Long> getTransPosition(long constant) {//�ڸ��� ���� ��ü�Ǵ� ��. ���� ��� 132 <->123, 100000�� ���� ���� ó�� �Ұ�.
        List<Long>transPosition=new ArrayList<Long>();


        List<Long>digits=separateDigit(constant);

        if(digits.size()==1){//�ڸ����� 1���̸� �ڸ������� ��ü�� ���� ����.
           
        }
        else if(digits.size()==2){//�ڸ����� 2�� ���� �����ε����� ���ε����� ��ü�Ѵ�.
        	if(digits.get(digits.size()-1)!=0) {
        	long typo=0;
        	typo+=digits.get(digits.size()-1);
        	typo*=10;
        	typo+=digits.get(0);
        	
        	transPosition.add(typo);	
        	}
        	
        }
        else {//�ڸ����� 3�̻��� ���� ���� �ε����� ���� �ε���, �� �ε����� ���� �ε����� ��ü�Ѵ�. ���� �ε����� 1~��-1 ���̿� �����ϴ� ������ �ε����̴�.

        	int start=0;
        	int end=digits.size()-1;
        	int randomIndex=(int)(Math.random()*(end-1))+1;


        	long typo;
        	if(digits.get(randomIndex)!=0) {//���� �������� ���� ���� 0�� ���, ���� ���� �ڸ� ��ü�� �ϸ� �ȵȴ�. ���� ��� 100�� ��� 010�� �ȴٸ�, �̴� ������ �ƴ�.
        		typo=makeTypoOfTransPosition(digits,start, randomIndex);//���� �ε��� �� <->���� �ε��� ��

        		if(typo!=constant) {//���� ��Ÿ�� ���� ����� ���ٸ�, ���� �ʴ´�. ���� ��� 100���� 0�� 0�� �ٲپ���� 100�̴�.

        			transPosition.add(typo);
        		}
        	}


        	
        	
        	typo=makeTypoOfTransPosition(digits,randomIndex, end);//���� �ε��� �� <-> �� �ε��� ��
        	if(typo!=constant) {//���� ��Ÿ�� ���� ����� ���ٸ�, ���� �ʴ´�. ���� ��� 100���� 0�� 0�� �ٲپ���� 100�̴�.
        		
        	transPosition.add(typo);
        	}
        	
        	
        }

        return transPosition;
    }

    private long makeTypoOfTransPosition(List<Long> digits, int a, int b) {//digits[a]<->digits[b] swap �޼���
		// TODO Auto-generated method stub
    	long typo=0;

    	
    	for(int i=0;i<digits.size();i++) {
    		if(i==a) {
    			typo+=digits.get(b);
    		}
    		else if(i==b) {
    			typo+=digits.get(a);
    		}
    		else {
    			typo+=digits.get(i);
    		}
    		typo*=10;
    	}
    	
    	typo/=10;
    	
    	
		return typo;
	}
    
    /*
     * �ڸ��� �ϳ� �����ϱ�
     */

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
    
    /*
     * �ڸ��� �ϳ� �ߺ� �Է��ϱ�
     */

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
    
    /*
     * �ڸ��� �ϳ��� ������ �� �ϳ��� ��ü�ϱ� 
     */
    

    public List<Long> getSubstitution(long constant) {
        List<Long>substitutions=new ArrayList<Long>();


        List<Long>digits=separateDigit(constant);
        long typo;
        
        if(digits.size()==1) {//�ڸ��� 1�� ���
        	typo=makeTypoOfSubstution(digits,0);
        	substitutions.add(typo);
        }
        else if(digits.size()<3){//�ڸ��� 2�� ��쿡�� �� �� ���ڶ� �� �� ���ڿ� ���� substitution�� �Ͼ ��츦 ��´�.
        	int start=0;
        	int end=1;
        	
        	typo=makeTypoOfSubstution(digits,start);
        	substitutions.add(typo);
        	
        	typo=makeTypoOfSubstution(digits,end);
        	substitutions.add(typo);
        	
        }
        else {//�ڸ����� 3�̻��� ��쿡�� �� �� ����, �� �� ����, �� ������ ���� ���ڿ� ���� substitution�� �Ͼ ��츦 ��´�.(3����)
        	int start=0;
        	int end=digits.size()-1;
        	int randomIndex=(int)(Math.random()*(end-1))+1;
        	
        	typo=makeTypoOfSubstution(digits,start);
        	substitutions.add(typo);
        	
        	typo=makeTypoOfSubstution(digits,end);
        	substitutions.add(typo);
        	
        	typo=makeTypoOfSubstution(digits,randomIndex);
        	substitutions.add(typo);
        	
        }

        return substitutions;
    }
    
    
    
    private long makeTypoOfSubstution(List<Long>digits,int index) {//�ڸ��� �迭���� ���� �ڸ����� ���� ������ ���� ��ü�ϱ�
    	long typo=0;
    	
    	long adjacent=adjKey.getAdjacentKey(digits.get(index));
    	
    	for(int i=0;i<digits.size();i++) {
    		if(i==index) {//�ش� �ε��������� ������ ���� ��ü
    			typo+=adjacent;
    		}
    		else {
    			typo+=digits.get(i);
    		}
    		typo*=10;
    	}
    	
    	typo/=10;
    	
    	
    	return typo;
    }

}
