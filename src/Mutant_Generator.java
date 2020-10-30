
import java.util.*;

public class Mutant_Generator {
	

    public Set<Long> generateMutantCVR(long constant){//파라미터 constant는 xml에서 추출한 상숫값
        //상숫값 대체 오류와 관련된 뮤턴트 생성 코드

        //각 상숫값 대체 클래스들을 담을 리스트 생성
        List<Long> substitution=new LinkedList<Long>();
        List<Long>intrusion=new LinkedList<Long>();
        List<Long>omission=new LinkedList<Long>();
        List<Long>transposition=new LinkedList<Long>();



        //각각의 리스트에 상숫값에 해당하는 오류를 담는다.
        substitution=getSubstitution(constant);
        intrusion=getIntrusion(constant);
        omission=getOmission(constant);
        transposition=getTransPosition(constant);


        //각각의 유형 오류를 담아 해시 셋에 담는다.
        Set<Long>mutants=new HashSet<Long>();

        /*
        HashSet을 선택한 이유:
        1.중복은 담지 않는다.
        2.뮤턴트를 담는데 순서(인덱스)는 딱히 필요없다.
        3.삽입 및 삭제인 경우 O(1)일 정도로 빠름.
         */

        mutants.addAll(substitution);
        mutants.addAll(intrusion);
        mutants.addAll(omission);
        mutants.addAll(transposition);



        return mutants;
    }

    private List<Long>separateDigit(long number){

        List<Long>digits=new ArrayList<Long>();
        
        if(number==0) {//0만 있는 경우, 아래의 while문에 들어가지 않으므로 GUARD를 넣습니다.
        	digits.add((long) 0);
        }

        while(number>0){

            long remainder=number-(number/10)*10;

            digits.add(0,remainder);

            number/=10;
        }
        
        return digits;

    }


    public List<Long> getTransPosition(long constant) {//자릿수 끼리 교체되는 것. 예를 들어 132 <->123, 100000과 같은 예외 처리 할것.
        List<Long>transPosition=new ArrayList<Long>();


        List<Long>digits=separateDigit(constant);

        if(digits.size()==1){//자릿수가 1개이면 자릿수끼리 교체될 일이 없다.
           
        }
        else if(digits.size()==2) {//자릿수가 2인 경우는 시작인덱스와 끝인덱스를 교체한다.
        	
        	long typo=0;
        	typo+=digits.get(digits.size()-1);
        	typo*=10;
        	typo+=digits.get(0);
        	
        	transPosition.add(typo);
        }
        else {//자릿수가 3이상인 경우는 시작 인덱스와 랜덤 인덱스, 끝 인덱스와 랜덤 인덱스를 교체한다. 랜덤 인덱스는 1~끝-1 사이에 존재하는 랜덤한 인덱스이다.
        	
        	int start=0;
        	int end=digits.size()-1;
        	int randomIndex=(int)(Math.random()*(end-1))+1;
        	
        	long typo=makeTypoOfTransPosition(digits,digits.get(start),digits.get(randomIndex));//시작 인덱스 수 <->랜덤 인덱스 수
        	transPosition.add(typo);
        	
        	typo=makeTypoOfTransPosition(digits,digits.get(randomIndex),digits.get(end));//랜덤 인덱스 수 <-> 끝 인덱스 수
        	transPosition.add(typo);
        	
        	
        }

        return transPosition;
    }

    private long makeTypoOfTransPosition(List<Long> digits, long a, long b) {//digits[a]<->digits[b] swap 메서드
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

	public List<Long> getOmission(long constant) {//자릿수 하나 생략

        List<Long>omissions=new ArrayList<Long>();


        List<Long>digits=separateDigit(constant);

        if(digits.size()==1){//자릿수가 1이면 생략할 수가 없음.
            //do nothing
        }
        else if(digits.size()==2) {//자릿수가 2인 경우, 앞의 숫자 생략한 것과 뒤의 숫자 생력한 것을 담는다.
        	long startOmitted=digits.get(0);
        	long endOmitted=digits.get(1);
        	omissions.add(startOmitted);
        	omissions.add(endOmitted);
        	
       
        }
        else {//자릿수가 3이상인 경우,
        	/*
        	 맨 앞 숫자 생략한 것 
        	 맨 뒤 숫자 생략한 것
        	 그 사이에 랜덤한 숫자 생략한 것
        	 을 담는다.
        	 */
        	
        	int start=0;
        	int end=digits.size()-1;
        	int randomIndex=(int)(Math.random()*(end-1))+1;//1~end-1 사이의 랜덤한 인덱스 넘버를 선택한다.
        	
        	long typo=makeTypoOfOmission(digits,start);//0번째 숫자 생략한 오타 생성

        		omissions.add(typo);
        	
        	
        	
        	 typo=makeTypoOfOmission(digits,randomIndex);
        
        		omissions.add(typo); 
        	 
        	
        	
        	
        	 typo=makeTypoOfOmission(digits,end);//끝번쨰 숫자 생략한 오타 생성
        	
        		 omissions.add(typo);
        	 
        	
        	
        	
        	
        	
        	
        	
        }
        
        return omissions;
    }

    private long makeTypoOfOmission(List<Long> digits, int index) {//자릿수배열에서 index를 제외한 숫자 생성하는 메서드. 생략형 오타 생성 메서드.
		// TODO Auto-generated method stub
    	
    	long typo=0;
    	
    	for(int i=0;i<digits.size();i++) {
    		if(i!=index) {//index 파라미터가 아니면, 
    			typo+=digits.get(i);
    			typo*=10;
    		}
    	}
    	typo/=10;
    	
		return typo;
	}

	public List<Long> getIntrusion(long constant) {//똑같은 키를 한번 더 누르는 경우
    
        List<Long>intrusions=new ArrayList<Long>();


        List<Long>digits=separateDigit(constant);
        
 
        if(digits.size()==1){//자릿수가 1개인 경우 그 수를 1개 더 추가하면 된다.
            long typo=digits.get(0)*10+digits.get(0);
            intrusions.add(typo);
        }
        else if(digits.size()<=3){//자릿수가 2~3개인 경우는 자릿수가 별로 많지 않으므로 각 자릿수에 대해 intrusion 진행.

            for (int i = 0; i <digits.size() ; i++) {

                long typo=makeTypoOfIntrusions(digits,i);
                
                intrusions.add(typo);//intrusion 유형 오타를 리스트에 넣는다.


            }//i

        }
        else {//자릿수가 3개를 넘으면, 처음, 끝, 그 외에 랜덤한 하나를 집어서 intrusion을 진행한다.
        	int start=0;//처음 인덱스
        	int end=digits.size()-1;//끝 인덱스
        	int randomIndex=(int) (Math.random()*(end-1))+1;//1번째 인덱스 ~ end-1번째 인덱스 중 하나를 랜덤으로 고른다.

        	
        	long typo=makeTypoOfIntrusions(digits,start);//0번째 숫자 중복 타이핑한 오타 생성
        	intrusions.add(typo);
        	
        	typo=makeTypoOfIntrusions(digits,end);//끝번쨰 숫자 중복 타이핑한 오타 생성
        	intrusions.add(typo);
        	
        	typo=makeTypoOfIntrusions(digits,randomIndex);
        	intrusions.add(typo);

              
                
 
        
        	
        }
        return intrusions;

    }
    
    private long makeTypoOfIntrusions(List<Long>digits,int index) {//자릿수에서 index에 해당하는 부분에 intrusion을 하는 메서드.
    	
        long  typo=0;
        for (int j =0; j <digits.size(); j++) {
            if(j==index) {//만약, 랜덤 인덱스랑 같다면, intrusion 진행한다.

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
