
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import java.io.*;


public class FBD_Sim_Mock {

    public static void main(String[] args) throws IOException {

    	

    	
    	Mock_Data random=new Mock_Data("random",10.0,15.0,10.0,100.0);
	
    
    	Mock_Data guided=new Mock_Data("guided",20.0,30.0,60.0,100.0);
		

    	Mock_Data manual=new Mock_Data("manual",60.0,70.0,70.0,100.0);
	
		mockText(random,guided,manual);
		mockXls(random,guided,manual,false);
		
		readAndSum();


    }

	private static void readAndSum() throws IOException {
		// TODO Auto-generated method stub
		
		List<Mock_Data>randoms=new ArrayList<>();
		List<Mock_Data>guideds=new ArrayList<>();
		List<Mock_Data>manuals=new ArrayList<>();
		
		List<ArrayList<Boolean>>results=new ArrayList<>();
		
		for(int i=0;i<3;i++) {
			results.add(new ArrayList<>());
		}

		String type = null;
		double tc=0;
		double mcdc=0;
		double son=0;
		double mom=0;
		
		File directory=new File("C:\\Users\\dasd4\\Desktop\\xml\\mockXSL\\");
		
		File[]xlsList=directory.listFiles();//해당 디렉토리에 있는 파일리스트 가져오기
		
		for(File file:xlsList) {
			
			if(file.isFile()) {
				String fileName=file.getName();
				if(!fileName.endsWith(".xls")){//액셀 파일 아닌것은 건너 띔.
					continue;
				}//if is xls
				
				String path=file.getAbsolutePath();
				
				try {
					FileInputStream inputStream=new FileInputStream(path);
					@SuppressWarnings("resource")
					HSSFWorkbook workbook=new HSSFWorkbook(inputStream);
					HSSFSheet sheet=workbook.getSheetAt(0);
					
					int rows=sheet.getPhysicalNumberOfRows();

					
					
					HSSFCell cell;
					
					
					boolean result;
					for(int columnIndex=1;columnIndex<=3;columnIndex++) {//1열부터 시작한다. 0이 원래 첫 인덱스임.
						for(int i=0;i<rows;i++) {//0행부터 읽기 시작한다.


							cell=sheet.getRow(i).getCell(columnIndex);//1열부터 읽는다.
							switch(i) {
							case 0:type=cell.getStringCellValue(); break;
							case 1:tc=cell.getNumericCellValue();break;
							case 2:mcdc=cell.getNumericCellValue();break;
							case 3:son=cell.getNumericCellValue();break;
							case 4:mom=cell.getNumericCellValue();break;
							
							default:
								result=Boolean.valueOf(cell.getStringCellValue());
								results.get(columnIndex-1).add(result);
								break;
							}
						}
						
						Mock_Data read=new Mock_Data(type,tc,mcdc,son,mom);

						switch(columnIndex) {
						case 1:randoms.add(read);break;
						case 2:guideds.add(read);break;
						case 3:manuals.add(read);break;
						
						}
					}
					
					

		
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
			}//if is file
			
		}//enhanced for
		


	
		//다음 3줄은 임의로 합계로 한것... 실제로는 확실한 합계를 따로 처리해야 함.
		Mock_Data sumRandom=new Mock_Data("random",10.0/3,15.0/3,10.0/3,100.0/3);
		Mock_Data sumGuided=new Mock_Data("guided",20.0/3,30.0/3,60.0/3,100.0/3);
		Mock_Data sumManual=new Mock_Data("manual",60.0/3,70.0/3,70.0/3,100.0/3);
		
		
		mockXls(sumRandom,sumGuided,sumManual,true);
		
		
	}

	private static void mockXls(Mock_Data random, Mock_Data guided, Mock_Data manual,boolean isSum) throws IOException{//isSum==true이면 집계결과 쓰기, false이면 개별 결과 쓰기
		// TODO Auto-generated method stub
		
		List<Mock_Data>list=new LinkedList<>();
		list.add(random);
		list.add(guided);
		list.add(manual);
		
		
		
		try {
			
			for(int index=0;index<3;index++) {
				
				StringBuilder sb=new StringBuilder();
			

			Iterator<Mock_Data>iter=list.iterator();
            @SuppressWarnings("resource")
			HSSFWorkbook writeBook=new HSSFWorkbook();//새 액셀 파일 생성

			HSSFSheet sheet=writeBook.createSheet("results");//새 시트 생성
	
		

			
			
			Mock_Data data;
            HSSFCell cell;
            
     
            
            HSSFRow[] rows=new HSSFRow[100];
            for(int i=0;i<rows.length;i++) {
            	rows[i]=sheet.createRow(i);
            }
            
            
            
            for(int i=0;i<rows.length;i++) {
            	cell=rows[i].createCell(0);
            	switch(i) {
				case 0:cell.setCellValue("type");break;
				case 1:cell.setCellValue("TC%");break;
				case 2:cell.setCellValue("MC/DC%");break;
				case 3:cell.setCellValue("분자");break;
				case 4:cell.setCellValue("분모");break;
				
				}
				
            	
            }
            int colIndex=1;

			while(iter.hasNext()) {
			
			data=iter.next();
			
		
			for(int i=0;i<rows.length;i++) {
				cell=rows[i].createCell(colIndex);//해당 행의 열 생성, 단 1열부터 시작.
				
				switch(i) {
				case 0:cell.setCellValue(data.getType());break;
				case 1:cell.setCellValue(data.getTcPercant());break;
				case 2:cell.setCellValue(data.getMcdcPercent());break;
				case 3:cell.setCellValue(data.getScoreSon());break;
				case 4:cell.setCellValue(data.getScoreMom());break;
				
				default:
					if(colIndex==1) {
						cell.setCellValue("false");
					}else if(colIndex==2) {
						cell.setCellValue("true");
					}
					else {

						cell.setCellValue("true");
						
					}
					break;
				}
				
			
			}
            
			colIndex++;//열 증가.
			
			}
			
			sb.append("C:\\Users\\dasd4\\Desktop\\xml\\mockXSL\\");
			
			sb.append("simulation_result");
			if(!isSum) {
				sb.append(Integer.toString(index));
			}
			else {
				sb.append("_SUM");
			}
			
			sb.append(".xls");
			
			FileOutputStream output=new FileOutputStream(sb.toString());

            writeBook.write(output);
            output.close();




			}//for index
		}//TRY
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	private static void mockText(Mock_Data random, Mock_Data guided, Mock_Data manual) throws IOException{
		// TODO Auto-generated method stub
		File Simulation_Result = new File("C:\\Users\\dasd4\\Desktop\\xml\\mockXSL" + "\\" + "Simulation_Result.txt");
		FileWriter fw = new FileWriter(Simulation_Result);
		BufferedWriter bw = new BufferedWriter(fw);
		

		bw.write(" - Result - " + "\n");
		bw.write("Model name = " + "test_MOCK" + "\n");
		bw.write("[Random] Toggle cov :" + random.getTcPercant()
				+ " % , " + "MC/DC cov :" + random.getMcdcPercent() + " %" + "\n");
		bw.write("[Random] Mutation kill score = (" + random.getScoreSon() + ") / ("
				+ random.getScoreMom() + ")" + "\n");

		bw.write("[Guided] Toggle cov :" +guided.getTcPercant()
				+ " % , " + "MC/DC cov :" + guided.getMcdcPercent() + " %" + "\n");
		bw.write("[Guided] Mutation kill score = (" + guided.getScoreSon() + ") / ("
				+ guided.getScoreMom()+ ")" + "\n");

		bw.write("[Manual] Toggle cov :" + manual.getTcPercant()
				+ " % , " + "MC/DC cov :" + manual.getMcdcPercent()+ " %" + "\n");
		bw.write("[Manual] Mutation kill score = (" + manual.getScoreSon()+ ") / ("
				+ manual.getScoreMom() + ")" + "\n");

		bw.write("\n" + "\n");
		bw.write("Kill list" + "\n");
		for (int i = 0; i < 100; i++) {
			bw.write(i + ". " + "[R]: " + true + " / [G]: "
					+ false  + " / [M]: " + false + "\n");
		}
		bw.close();
		fw.close();
	}
    
    
    
}
