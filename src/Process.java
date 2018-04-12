import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Process {
	private  String file_content[];
	private  DataStackMemory data_memory;
	private  DataStackMemory stack_memory;
	private StackPointer stack_pointer;
	private  Memory instruction_memory;
	private  String LabelTable[][];
	private  int labeltablecounter;
	private  ProgramCounter programcounter;
	private  Register adressregister;
	private Register instruction_register;
	private File selectedfile;
	private Register R0;
	private Register R1;
	private Register R2;
	private Register Dest;
	private Register s1;
	private Register s2;
	private Register input_register;
	private Register output_register;
	private boolean overflow;
	private int T;
	private int jmr[];
	private int jmr_counter;
	private int jmr_counter_temp;
	private boolean program_end;
	private int Q;
	private String lines_for_screen[];
	private int lines_for_screen_counter;
	private int D;//D0,D1..
	private Alu alu;
	private String s1_temp;
	private String s2_temp;
	private boolean input_register_exist;

private String operations[];

	public Process(){
	
		overflow=false;
		selectedfile=new File("");
		stack_memory=new DataStackMemory(16,5,true);
		alu=new Alu();
		jmr_counter=0;
		stack_pointer=new StackPointer();
	
		file_content=new String[100]; 
		data_memory=new DataStackMemory(16,4,true);
		instruction_memory=new Memory(32,11,true); 
		labeltablecounter=0;
		jmr_counter_temp=0;
		LabelTable=new String[16][5];
		input_register=new Register();
		output_register=new Register();
		programcounter=new ProgramCounter();
		adressregister=new Register();
		T=-1;
		input_register_exist=false;
		jmr=new int[32];
		R0=new Register();
		R1=new Register();
		R2=new Register();
		s1=new Register();
		s2=new Register();
		Dest=new Register();
		instruction_register=new Register();
		lines_for_screen=new String[50];
		lines_for_screen_counter=0;
		
		program_end=false;
	}
	
	public  void read_parse(){
		
		  String[] temp_parse = null;
		  String[] temp_parse_for_comment = null;
		
		  BufferedReader br = null;
			FileReader fr = null;
          
			try {

				fr = new FileReader(selectedfile);
				br = new BufferedReader(fr);

				String sCurrentLine;
            int counter=0;
				br = new BufferedReader(new FileReader(selectedfile));
             
				while ((sCurrentLine = br.readLine()) != null) {
					temp_parse_for_comment=sCurrentLine.split("%");
					
						temp_parse=temp_parse_for_comment[0].split("\\s*(=>|,|\\s)\\s*");
						
						for (int j = 0; j < temp_parse.length; j++) {
							
							
							file_content[counter]=temp_parse[j];
							
							counter++;
							
						}
						file_content[counter]="/";
						counter++;
					
				}
				
			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (br != null)
						br.close();

					if (fr != null)
						fr.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}

			}
			
	}
	public void assign_memories(){
		
		int datamemorystarting=0;
		int datamemorystartingtemp=0;
		LabelTable[0][1]="Bin";
		LabelTable[0][2]="Hex";
		LabelTable[0][3]="Bin";
		LabelTable[0][4]="Hex";
		int lable_Table_variable_counter=0;
	for (int i = 0; i < file_content.length; i++) {  // firstly variables are found
		if(file_content[i]!=null){
		if(file_content[i].length()>=1){
			if(file_content[i].substring(file_content[i].length()-1,file_content[i].length()).equals(":")){  // find variables's contents and addresses like A,S
				
				if(file_content[i+1]!=null&&file_content[i+2]!=null){
					if(file_content[i+1].equals("HEX")){
						int x = Integer.parseInt(file_content[i+2], 16 );
						LabelTable[labeltablecounter+1][0]=file_content[i].substring(0,file_content[i].length()-1);  // Label table elements assigns
						LabelTable[labeltablecounter+1][1]=Integer.toBinaryString(datamemorystarting);
						LabelTable[labeltablecounter+1][2]=Integer.toHexString(datamemorystarting);
						LabelTable[labeltablecounter+1][3]=Integer.toBinaryString(x);
						LabelTable[labeltablecounter+1][4]=Integer.toHexString(x);
						datamemorystarting++;
						labeltablecounter++;
						lable_Table_variable_counter++;
						
					}
					else if(file_content[i+1].equals("DEC")){
					
						LabelTable[labeltablecounter+1][0]=file_content[i].substring(0,file_content[i].length()-1); // Label table elements assigns
						LabelTable[labeltablecounter+1][1]=Integer.toBinaryString(datamemorystarting);
						LabelTable[labeltablecounter+1][2]=Integer.toHexString(datamemorystarting);
						LabelTable[labeltablecounter+1][3]=Integer.toBinaryString(Integer.parseInt(file_content[i+2]));
						LabelTable[labeltablecounter+1][4]=Integer.toHexString(Integer.parseInt(file_content[i+2]));
						datamemorystarting++;
						labeltablecounter++;
						lable_Table_variable_counter++;
					}
					else if(file_content[i+1].equals("BIN")){
						
						LabelTable[labeltablecounter+1][0]=file_content[i].substring(0,file_content[i].length()-1); // Label table elements assigns
						LabelTable[labeltablecounter+1][1]=Integer.toBinaryString(datamemorystarting);
						LabelTable[labeltablecounter+1][2]=Integer.toHexString(datamemorystarting);
						LabelTable[labeltablecounter+1][3]=file_content[i+2];
						int decimal = Integer.parseInt(file_content[i+2],2);
						String hexStr = Integer.toString(decimal,16);
						LabelTable[labeltablecounter+1][4]=hexStr;
						datamemorystarting++;
						labeltablecounter++;
						lable_Table_variable_counter++;
					}
					
					
				}
			}
		}
		
		 if(file_content[i].equals("ORG")){
				if(file_content[i+1]!=null&&file_content[i+2]!=null){
					if(file_content[i+1].equals("D")){
						data_memory.setStarting_slot(Integer.parseInt(file_content[i+2]));   // data memory starting index
						datamemorystarting=Integer.parseInt(file_content[i+2]);
						datamemorystartingtemp=datamemorystarting;
						adressregister.setData(string_to_bin_string(file_content[i+2],4));
					}
					else if(file_content[i+1].equals("C")){
						instruction_memory.setStarting_slot(Integer.parseInt(file_content[i+2])); 	// instruction memory starting index
						programcounter.setData(string_to_bin_string(file_content[i+2], 5));
						
					}
					else if(file_content[i+1].equals("S")){
						stack_memory.setStarting_slot(Integer.parseInt(file_content[i+2])); 	// stack memory starting index
						
						stack_pointer.setData(string_to_bin_string(file_content[i+2], 4));
						
					}
				}
			}
	}
	}
		
	for(int i=0;i<file_content.length;i++){
		if(file_content[i]!=null){
			 if(file_content[i].equals("LD")||file_content[i].equals("ST")){    // instruction memory contents assign
					String code="";
					int adress_or_data = 0;// if 0 direct data , if 1 adress of data
					if(file_content[i+1]!=null&&file_content[i+2]!=null){
						if(file_content[i+2].substring(0, 1).equals("#")){ // for Q and  LD 's opcode 
							code+="1";
							adress_or_data=1;
						}
						else if(file_content[i+2].substring(0, 1).equals("@")){ //go to address of file_content[i+2].substring(1, 2)
							code+="0";                                          //and read content of address
							adress_or_data=0; //assign Q
						}
						else{
							code+="1";
						}
						if(file_content[i].equals("LD")){
							code+="0110";
						}
						else{
							code+="0111";
						}
						if(file_content[i+1].equals("R0")){
							code+="00";
						}
						else if(file_content[i+1].equals("R1")){
							code+="01";
							
						}
						else if(file_content[i+1].equals("R2")){
							code+="10";
						}
						else if(file_content[i+1].equals("INPR")){
							code+="11";
							input_register_exist=true;
						}	else if(file_content[i+1].equals("OUTR")){
							code+="11";
						}
						if(file_content[i].equals("ST")){
							if(file_content[i+2].equals("R0")){
								code+="0000";
							}
							else if(file_content[i+2].equals("R1")){
								code+="0001";
								
							}
							else if(file_content[i+2].equals("R2")){
								code+="0010";
							}
							else if(file_content[i+2].equals("OUTR")){
								code+="0011";
							}	
						}
						if(adress_or_data==0){
							for (int j = 0; j < LabelTable.length; j++) {
								if(LabelTable[j][0]!=null){
								if(LabelTable[j][0].equals(file_content[i+2].substring(1,file_content[i+2].length()))){
									code+=string_to_bin_string(LabelTable[j][2], 4);
									
								}
							}
							}
						}
						else{
							code+=string_to_bin_string(file_content[i+2].substring(1,file_content[i+2].length()), 4);
						}
						instruction_memory.getSlots()[instruction_memory.getStarting_slot()].setData(code);
						
					}
					lines_for_screen[instruction_memory.getStarting_slot()]=file_content[i]+" "+file_content[i+1]+" "+file_content[i+2];
					instruction_memory.setStarting_slot(instruction_memory.getStarting_slot()+1);
				}
				
				else if(file_content[i].equals("TSF")){    // instruction memory contents assign
					if(file_content[i+1]!=null&&file_content[i+2]!=null){
						String code="01001";
						if(file_content[i+1].equals("R0")){ // for D and S2 
							code+="0000";            
						}
						else if(file_content[i+1].equals("R1")){ // transfer from S1 into D so s2 must be default(00)
							code+="0100";
						}
						else if(file_content[i+1].equals("R2")){//D-S2-S1
							code+="1000";
							
						}
						else if(file_content[i+1].equals("INPR")){//D-S2-S1
							code+="1000";
							input_register_exist=true;
							
						}
						else if(file_content[i+1].equals("OUTR")){//D-S2-S1
							code+="1000";
							
						}
						if(file_content[i+2].equals("R0")){
							code+="00";
						}
						else if(file_content[i+2].equals("R1")){
							code+="01";
						}
						else if(file_content[i+2].equals("R2")){
							code+="10";
							
						}
						else if(file_content[i+2].equals("INPR")){
							code+="11";
							input_register_exist=true;
						}	else if(file_content[i+2].equals("OUTR")){
							code+="11";
						}
						instruction_memory.getSlots()[instruction_memory.getStarting_slot()].setData(code);
						
						lines_for_screen[instruction_memory.getStarting_slot()]=file_content[i]+" "+file_content[i+1]+" "+file_content[i+2];
						instruction_memory.setStarting_slot(instruction_memory.getStarting_slot()+1);
					}
				}
				else if(file_content[i].equals("ADD")||file_content[i].equals("AND")){    // instruction memory contents assign
					if(file_content[i+1]!=null&&file_content[i+2]!=null&&file_content[i+3]!=null){
						String code;
						if(file_content[i].equals("ADD")){
							code="00000"; //ADD Q and opcode 
						}
						else{
							code="00101"; //AND Q and opcode
						}
						if(file_content[i+1].equals("R0")){ //for destination(D) part ->D-S2-S1
							code+="00";
						}
						else if(file_content[i+1].equals("R1")){
							code+="01";
						}
						else if(file_content[i+1].equals("R2")){
							code+="10";
						}
						else if(file_content[i+1].equals("OUTR")){
							code+="11";
						}
						if(file_content[i+2].equals("R0")){ //for S2 part ->D-S2-S1
							code+="00";
						}
						else if(file_content[i+2].equals("R1")){
							code+="01";
						}
						else if(file_content[i+2].equals("R2")){
							code+="10";
						}
						else if(file_content[i+2].equals("INPR")){
							code+="11";
							input_register_exist=true;
						}
						
						if(file_content[i+3].equals("R0")){ //for S1 part ->D-S2-S1
							code+="00";
						}
						else if(file_content[i+3].equals("R1")){
							code+="01";
						}
						else if(file_content[i+3].equals("R2")){
							code+="10";
						}
						else if(file_content[i+2].equals("INPR")){
							code+="11";
						}
						
					
						instruction_memory.getSlots()[instruction_memory.getStarting_slot()].setData(code);
						
						lines_for_screen[instruction_memory.getStarting_slot()]=file_content[i]+" "+file_content[i+1]+" "+file_content[i+2]+" "+file_content[i+3];
						instruction_memory.setStarting_slot(instruction_memory.getStarting_slot()+1);
					}
				}
				else if(file_content[i].equals("INC")||file_content[i].equals("DBL")||file_content[i].equals("DBT")||file_content[i].equals("NOT")){    // instruction memory contents assign
					if(file_content[i+1]!=null){
						String code = "";
						if(file_content[i].equals("DBL")){ 
							code+="00010";
						}
						if(file_content[i].equals("INC")){
							code+="00001";
						}
						if(file_content[i].equals("DBT")){
							code+="00011";
						}
						if(file_content[i].equals("NOT")){
							code+="00100";
						}
						if(!file_content[i+2].equals("/")){
							if(file_content[i+1].equals("R0")){  //only S1 changes 
								code+="00";
							}
							else if(file_content[i+1].equals("R1")){
								code+="01";
							}
							else if(file_content[i+1].equals("R2")){
								code+="10";
							}
							else if(file_content[i+1].equals("OUTR")){
								code+="11";
							}
							if(file_content[i+2].equals("R0")){  //only S1 changes 
								code+="0000";
							}
							else if(file_content[i+2].equals("R1")){
								code+="0001";
							}
							else if(file_content[i+2].equals("R2")){
								code+="0010";
							}
							else if(file_content[i+2].equals("INPR")){
								code+="0011";
								input_register_exist=true;
							}
						}
						else{
						if(file_content[i+1].equals("R0")){  //only S1 changes 
							code+="000000";
						}
						else if(file_content[i+1].equals("R1")){
							code+="010001";
						}
						else if(file_content[i+1].equals("R2")){
							code+="100010";
						}
						else if(file_content[i+1].equals("INPR")){
							code+="110010";
						}
						}
						instruction_memory.getSlots()[instruction_memory.getStarting_slot()].setData(code);
						
						lines_for_screen[instruction_memory.getStarting_slot()]=file_content[i]+" "+file_content[i+1];
						
						if(file_content[i+1]!=null){
							if(!file_content[i+1].equals("/")){
								lines_for_screen[instruction_memory.getStarting_slot()]+=" "+file_content[i+2];
							}
						}
						instruction_memory.setStarting_slot(instruction_memory.getStarting_slot()+1);
					}
				}
		
		
		else if(file_content[i].equals("HLT")){    // instruction memory contents assign
			
			instruction_memory.getSlots()[instruction_memory.getStarting_slot()].setData("01000000000");
		
			lines_for_screen[instruction_memory.getStarting_slot()]=file_content[i];
			instruction_memory.setStarting_slot(instruction_memory.getStarting_slot()+1);
		}
		else if(file_content[i].equals("CAL")){    // instruction memory contents assign
			String temp="";
				if(file_content[i+1].substring(0,1).equals("1")||file_content[i+1].substring(0,1).equals("2")
						||file_content[i+1].substring(0,1).equals("3")||file_content[i+1].substring(0,1).equals("4")
						||file_content[i+1].substring(0,1).equals("5")||file_content[i+1].substring(0,1).equals("6")
						||file_content[i+1].substring(0,1).equals("7")||file_content[i+1].substring(0,1).equals("8")
						||file_content[i+1].substring(0,1).equals("9")){
					temp=string_to_bin_string(file_content[i+1],5);
					
				}
				else{
					int counter=0;
					String code="";
					for (int j = i+2; j < file_content.length; j++) {
						if(file_content[j]==null){
							break;
						}
						else{
						if(file_content[j].equals("/")){
							
							counter++;
						}
						if(file_content[j].equals(file_content[i+1]+":")){
							break;
						}
						
						}
					}
					
					code=Integer.toBinaryString(instruction_memory.getStarting_slot()+counter);
					
					for (int j = 0; j < 5-code.length(); j++) {
						temp+="0";
					}
					temp+=code;
					LabelTable[labeltablecounter+1][0]=file_content[i+1];
					LabelTable[labeltablecounter+1][1]=code;
					int decimal = Integer.parseInt(code,2);
					String hexStr = Integer.toString(decimal,16);
					LabelTable[labeltablecounter+1][2]=hexStr;
					LabelTable[labeltablecounter+1][3]="010100"+temp;
					int decimal2 = Integer.parseInt("010100"+temp,2);
					String hexStr2 = Integer.toString(decimal2,16);
					LabelTable[labeltablecounter+1][4]="010100"+hexStr2;
					labeltablecounter++;
				}
					
					instruction_memory.getSlots()[instruction_memory.getStarting_slot()].setData("010100"+temp);
					
					lines_for_screen[instruction_memory.getStarting_slot()]=file_content[i];
					instruction_memory.setStarting_slot(instruction_memory.getStarting_slot()+1);
					
				}
		else if(file_content[i].equals("RET")){    // instruction memory contents assign
			
			instruction_memory.getSlots()[instruction_memory.getStarting_slot()].setData("01011000000");
			
			lines_for_screen[instruction_memory.getStarting_slot()]=file_content[i];
			instruction_memory.setStarting_slot(instruction_memory.getStarting_slot()+1);
			
		}
		else if(file_content[i].equals("JMR")){    // instruction memory contents assign
			String temp="";
			if(file_content[i+2].equals("BIN")){
				temp=file_content[i+1];
				if(temp.substring(0,1).equals("0")){
					jmr[jmr_counter]=Integer.parseInt(temp, 2);
					jmr_counter++;
				}
				else{
					boolean find=false;;
					int index=0;
					String temp2="";
					for (int j = 3; j >=0&&!find; j--) {
						if(file_content[i+1].substring(j, j+1).equals("1")){
							find=true;
							index=j;
							temp2+=file_content[i+1].substring(j, j+1);
						}
					}
					String t3="";
					for (int j = index-1; j>=0; j--) {
						if(file_content[i+1].substring(j, j+1).equals("1")){
							t3+="0";
						}
						else{
							t3+="1";
						}
					}
					t3+=temp2;
					jmr[jmr_counter]=-Integer.parseInt(t3, 2);
					jmr_counter++;
				}
			}
			else{
				int numb;
				String numb_="";
				numb=Integer.parseInt(file_content[i+1]);
				jmr[jmr_counter]=numb;
				jmr_counter++;
				numb_=Integer.toBinaryString(numb);
				String zero="";
				if(numb_.length()<4){
					for (int j = 0; j < 4-numb_.length(); j++) {
						zero+="0";
					}
					zero+=numb_;
					temp=zero;
				}
				else{
				temp+=numb_.substring(numb_.length()-4,numb_.length());	
				}
			}
			
			instruction_memory.getSlots()[instruction_memory.getStarting_slot()].setData("0110100"+temp);
			
			lines_for_screen[instruction_memory.getStarting_slot()]=file_content[i];
			instruction_memory.setStarting_slot(instruction_memory.getStarting_slot()+1);
			
		}
		
		else if(file_content[i].equals("POP")){    // instruction memory contents assign
			
			instruction_memory.getSlots()[instruction_memory.getStarting_slot()].setData("01111000000");
		
			lines_for_screen[instruction_memory.getStarting_slot()]=file_content[i];
			instruction_memory.setStarting_slot(instruction_memory.getStarting_slot()+1);
			
		}
		
	
	}
		else{
			break;
		}
	}
	for(int i=0;i<file_content.length;i++){
		if(file_content[i]!=null){
			 if(file_content[i].equals("PSH")){    // instruction memory contents assign
				if(file_content[i+1].substring(0,1).equals("#")){
					int numb=Integer.parseInt(file_content[i+1]);
					String numb_s=Integer.toString(numb,2);
					String temp="";
					for (int j = 0; j < 4-numb_s.length(); j++) {
						temp+="0";
					}
					temp+=numb_s;
					instruction_memory.getSlots()[instruction_memory.getStarting_slot()].setData("0111000"+temp);
				}
				else if(file_content[i+1].substring(0,1).equals("@")){
					if(file_content[i+2]!=null){
						if(file_content[i+2].equals("C")){
							instruction_memory.getSlots()[instruction_memory.getStarting_slot()].setData("011100"+instruction_memory.getSlots()[Integer.parseInt(file_content[i+1])].getData());
						}
						else if(file_content[i+2].equals("D")){
							instruction_memory.getSlots()[instruction_memory.getStarting_slot()].setData("011100"+data_memory.getSlots()[Integer.parseInt(file_content[i+1])].getData());
						}
						else{
							for (int j = 0; j < LabelTable.length; j++) {
								if(LabelTable[j][0]!=null){
								if(LabelTable[j][0].equals(file_content[i+2].substring(1,file_content[i+2].length()))){
									
									instruction_memory.getSlots()[instruction_memory.getStarting_slot()].setData("011100"+string_to_bin_string(LabelTable[j][1], 5));
								}
							}
							}
						}
					}
				}
				
				lines_for_screen[instruction_memory.getStarting_slot()]=file_content[i];
				instruction_memory.setStarting_slot(instruction_memory.getStarting_slot()+1);
			}
			 else if(file_content[i].equals("JMP")){    // instruction memory contents assign
					
					if (file_content[i+2]!="/") {
						int numb=Integer.parseInt(file_content[i+1]);
						String numb_s=Integer.toString(numb,2);
						String temp="";
						for (int j = 0; j < 4-numb_s.length(); j++) {
							temp+="0";
						}
						temp+=numb_s;
						instruction_memory.getSlots()[instruction_memory.getStarting_slot()].setData("1110000"+temp);
					}
					else {
						if(file_content[i+1].substring(0,1).equals("1")||file_content[i+1].substring(0,1).equals("2")
								||file_content[i+1].substring(0,1).equals("3")||file_content[i+1].substring(0,1).equals("4")
								||file_content[i+1].substring(0,1).equals("5")||file_content[i+1].substring(0,1).equals("6")
								||file_content[i+1].substring(0,1).equals("7")||file_content[i+1].substring(0,1).equals("8")
								||file_content[i+1].substring(0,1).equals("9")){
							int numb=Integer.parseInt(file_content[i+1]);
							String numb_s=Integer.toString(numb,2);
							String temp="";
							for (int j = 0; j < 4-numb_s.length(); j++) {
								temp+="0";
							}
							temp+=numb_s;
							instruction_memory.getSlots()[instruction_memory.getStarting_slot()].setData("0110000"+temp);
						}
						else{
							for (int j = 0; j < LabelTable.length; j++) {
								if(LabelTable[j][0]!=null){
								if(LabelTable[j][0].equals(file_content[i+2].substring(1,file_content[i+2].length()))){
									
									instruction_memory.getSlots()[instruction_memory.getStarting_slot()].setData("0110000"+string_to_bin_string(LabelTable[j][1], 4));
								}
							}
							}
						}
						}
					
					lines_for_screen[instruction_memory.getStarting_slot()]=file_content[i];
					instruction_memory.setStarting_slot(instruction_memory.getStarting_slot()+1);
				}
		}
	}
	
	
	int j=1;
	int finish=0;
	for (int i = data_memory.getStarting_slot(); i <16&&finish<lable_Table_variable_counter; i++) { // data memory elements assigns
		if(LabelTable[j][0]!=null){
			String tem="";
			for (int k = 0; k < 4-LabelTable[j][3].length(); k++) {
				tem+=0;
			}
			tem+=LabelTable[j][3];
			data_memory.getSlots()[i].setData(tem);
			j++;
			finish++;
		}
		else{
			break;
		}
	}
	
	
}
	public void fetch(){
		int pc_decimal=Integer.parseInt(programcounter.getData(),2);
		if(T==0){
		instruction_register.setData(instruction_memory.getSlots()[pc_decimal].getData());//  IR<--IM[PC]
		
		}
		else{
			pc_decimal++;
			programcounter.setData(string_to_bin_string(Integer.toString(pc_decimal), 5)); // pc++
			
			
		}
		
		
	}
	public void decode(){
		
		Q=Integer.parseInt(instruction_register.getData().substring(0,1));//Q<--IR[10]
		
		Dest.setData(instruction_register.getData().substring(5,7));
		s2.setData(instruction_register.getData().substring(7,9));
		s1.setData(instruction_register.getData().substring(9,11));
		s1_temp=s1.getData();
		s2_temp=s2.getData();
		/*
		 * Dest
		 */
		if(instruction_register.getData().substring(5,7).equals("00")){
			if(R0.getData()!=null)
			Dest.setData(R0.getData());
		}
		else if(instruction_register.getData().substring(5,7).equals("01")){
			if(R1.getData()!=null)
			Dest.setData(R1.getData());
		}
		else if(instruction_register.getData().substring(5,7).equals("10")){
			if(R2.getData()!=null)
			Dest.setData(R2.getData());
		}
		else if(instruction_register.getData().substring(5,7).equals("11")){
			if(output_register.getData()!=null)
			Dest.setData(output_register.getData());
		}
		/*
		 * S2
		 */
		if(instruction_register.getData().substring(7,9).equals("00")){
			
			if(R0.getData()!=null)
			s2.setData(R0.getData());
		}
		else if(instruction_register.getData().substring(7,9).equals("01")){
			
			if(R1.getData()!=null)
			s2.setData(R1.getData());
		}
		else if(instruction_register.getData().substring(7,9).equals("10")){
				
			if(R2.getData()!=null)
			s2.setData(R2.getData());
		}
		else if(instruction_register.getData().substring(7,9).equals("11")){
			
			if(input_register.getData()!=null)
			s2.setData(input_register.getData());
		}
		/*
		 * S1
		 * 
		 */
		if(instruction_register.getData().substring(9,11).equals("00")){
			
			if(R0.getData()!=null)
			s1.setData(R0.getData());
		}
		else if(instruction_register.getData().substring(9,11).equals("01")){
		
			if(R1.getData()!=null)
			s1.setData(R1.getData());
		}
		else if(instruction_register.getData().substring(9,11).equals("10")){
			
			if(R2.getData()!=null)
			s1.setData(R2.getData());
		}
		else if(instruction_register.getData().substring(9,11).equals("11")){
			
			if(input_register.getData()!=null)
			s1.setData(input_register.getData());
		}
		
		int d_index=Integer.parseInt(instruction_register.getData().substring(1,5),2);//D0..D15<-- IR[9..6]
		
		D=d_index;
		
	}
	public void execute(){
		if(T==3&&D<6){  //Arithmetic and Logic Operations
			
			if(D==0){   //ADD
				alu.Add(s1, s2, Dest);
			}
			else if(D==1){ //INC
				alu.Increment(s1, Dest);
			}
			else if(D==2){//DBL
			alu.DBL(s1, Dest);				
						}
			else if(D==3){//DBT
				alu.DBT(s1, Dest);	;
			}
			else if(D==4){//NOT
				alu.Not(s1, Dest);	;
			}
			else if(D==5){ //AND
				alu.And(s1, s2, Dest);
			}
			
			if(instruction_register.getData().substring(5,7).equals("00")){
				
				R0.setData(Dest.getData());
			}
			else if(instruction_register.getData().substring(5,7).equals("01")){
				
				R1.setData(Dest.getData());
			}
			else if(instruction_register.getData().substring(5,7).equals("10")){
				
				R2.setData(Dest.getData());
			}
			else if(instruction_register.getData().substring(5,7).equals("11")){
				
				output_register.setData(Dest.getData());
			}
		}
		
		
		/*  
		 * 
		 * Data Transfer
		 */
		if(T==3&&D==8){
			 //HLT
				program_end=true;
			
		}
		if(T==3&&(D==6||D==7||D==9)){
			
			
			if(D==9){ //TSF
				alu.Transfer(s1, Dest);
				if(instruction_register.getData().substring(5,7).equals("00")){
					
					R0.setData(Dest.getData());
				}
				else if(instruction_register.getData().substring(5,7).equals("01")){
				
					R1.setData(Dest.getData());
				}
				else if(instruction_register.getData().substring(5,7).equals("10")){
					
					R2.setData(Dest.getData());
				}
				else if(instruction_register.getData().substring(5,7).equals("11")){
					
					output_register.setData(Dest.getData());
				}
			}
			if(D==7&&Q==1){ //ST
				alu.Store1(s1, Dest);
				if(instruction_register.getData().substring(9,11).equals("00")){
					
					R0.setData(s1.getData());
				}
				else if(instruction_register.getData().substring(9,11).equals("01")){
					
					R1.setData(s1.getData());
				}
				else if(instruction_register.getData().substring(9,11).equals("10")){
					
					R2.setData(s1.getData());
				}
				else if(instruction_register.getData().substring(9,11).equals("11")){
					
					output_register.setData(s1.getData());
				}
			}
			if(D==7&&Q==0){ //ST
				alu.Store2(s1_temp,s2_temp, adressregister);
			}
			if(D==6&&Q==1){ //LD
				alu.Load1(s1, s2,Dest);
				if(instruction_register.getData().substring(5,7).equals("00")){
					
					R0.setData(Dest.getData());
				}
				else if(instruction_register.getData().substring(5,7).equals("01")){
					
					R1.setData(Dest.getData());
				}
				else if(instruction_register.getData().substring(5,7).equals("10")){
					
					R2.setData(Dest.getData());
				}
				else if(instruction_register.getData().substring(5,7).equals("11")){
					
					output_register.setData(Dest.getData());
				}
			}
			if(D==6&&Q==0){ //LD
				alu.Load2(s1_temp, s2_temp,adressregister);
			}
			
		}
		else if(T==4){
			if(D==7&&Q==0){ //ST
				alu.Store3(Dest, adressregister, data_memory);
			}
			if(D==6&&Q==0){ //LD
				alu.Load3(Dest, adressregister, data_memory);
				if(instruction_register.getData().substring(5,7).equals("00")){
					
					R0.setData(Dest.getData());
				}
				else if(instruction_register.getData().substring(5,7).equals("01")){
					
					R1.setData(Dest.getData());
				}
				else if(instruction_register.getData().substring(5,7).equals("10")){
					
					R2.setData(Dest.getData());
				}
				else if(instruction_register.getData().substring(5,7).equals("11")){
					
					output_register.setData(Dest.getData());
				}
			}
		}
		
		/* 
		 * Program Control
		 * 
		 */
		if(T==3){
			if(D==10){ //CAL
			alu.Call1(stack_memory,stack_pointer, programcounter);	
			}
			else if(D==13){ //JMR
				alu.JMR(programcounter, instruction_register,jmr[jmr_counter_temp]);
				jmr_counter_temp++;
			}
			else if(D==14){ //PSH
				alu.Push1(adressregister, instruction_register);
			}
			else if(D==11){ //RET
				alu.Return1(stack_pointer);
			}
			else if(D==12&&Q==0){ //JMP
				alu.JMP1(instruction_register, programcounter);
			}
			else if(D==12&&Q==1){ //JMP
				if(alu.getOverflow()){
					alu.JMP2(instruction_register, programcounter);
				}
				
			}
			if(D==15){ //POP
				alu.Pop1(adressregister, instruction_register);
			}
			
		}
		else if(T==4){
			if(D==10){ //CAL
				alu.Call2(instruction_register, stack_pointer, programcounter);
			}
			if(D==11){ //RET
				alu.Return2(stack_memory, stack_pointer, programcounter);
			}
			else if(D==14){ //PSH
				alu.Push2(adressregister, data_memory, stack_memory, stack_pointer);
			}
			if(D==15){ //POP
				alu.Pop2(stack_pointer);
			}
		}
		else if(T==5){
			 if(D==14){ //PSH
				alu.Push3(stack_pointer);
			}
			 if(D==15){ //POP
					alu.Pop3(adressregister, data_memory, stack_memory, stack_pointer);
				}
		}
		
		
	
	}
	public static String string_to_bin_string(String data,int bit_lenght){
		String bin=Integer.toBinaryString(Integer.parseInt(data));
		if(bin.length()<bit_lenght){
			int diff=bit_lenght-bin.length();
			String newbin="";
			for (int k = 0; k <diff; k++) {
				newbin+="0";
			}
			newbin+=bin;
			bin=newbin;
		}
	return bin;
	}
	public void inc_T(boolean inst_base){
		if(inst_base){
			T=0;
			fetch();
			T++;
			fetch();
			T++;
			decode();
			T++;
			execute();
			T++;
			execute();
			T++;
			execute();
			if(D==8){
				program_end=true;
			}
		}
		else{
		T++;
		if(T==0||T==1){
			fetch();
		}
		else if(T==2){
			decode();
		}
		else if(T==3){
			execute();
		}
		else if(T==4){
			if(D>5){
				execute();
				
			}
			else {
				T=-1;
			
			}
		}
		else if(T==5){
			if(D==14||D==15){
				execute();
				
			}
			else {
				T=-1;
			
			}
		}
		else {
			T=-1;
		
		}
	}
	}
	
	public String[] getFile_content() {
		return file_content;
	}
	public void setFile_content(String[] file_content) {
		this.file_content = file_content;
	}
	public DataStackMemory getData_memory() {
		return data_memory;
	}
	public void setData_memory(DataStackMemory data_memory) {
		this.data_memory = data_memory;
	}
	public Memory getInstruction_memory() {
		return instruction_memory;
	}
	public void setInstruction_memory(Memory instruction_memory) {
		this.instruction_memory = instruction_memory;
	}
	public String[][] getLabelTable() {
		return LabelTable;
	}
	public void setLabelTable(String[][] labelTable) {
		LabelTable = labelTable;
	}
	public int getLabeltablecounter() {
		return labeltablecounter;
	}
	public void setLabeltablecounter(int labeltablecounter) {
		this.labeltablecounter = labeltablecounter;
	}
	public ProgramCounter getProgramcounter() {
		return programcounter;
	}
	public void setProgramcounter(ProgramCounter programcounter) {
		this.programcounter = programcounter;
	}
	public Register getAdressregister() {
		return adressregister;
	}
	public void setAdressregister(Register adressregister) {
		this.adressregister = adressregister;
	}
	public File getSelectedfile() {
		return selectedfile;
	}
	public void setSelectedfile(File selectedfile) {
		this.selectedfile = selectedfile;
	}

	public String[] getLines_for_screen() {
		return lines_for_screen;
	}

	public void setLines_for_screen(String[] lines_for_screen) {
		this.lines_for_screen = lines_for_screen;
	}

	public Register getInstruction_register() {
		return instruction_register;
	}

	public void setInstruction_register(Register instruction_register) {
		this.instruction_register = instruction_register;
	}

	public int getT() {
		return T;
	}

	public void setT(int t) {
		T = t;
	}

public int getQ() {
		return Q;
	}

	public void setQ(int q) {
		Q = q;
	}

	public int getLines_for_screen_counter() {
		return lines_for_screen_counter;
	}

	public void setLines_for_screen_counter(int lines_for_screen_counter) {
		this.lines_for_screen_counter = lines_for_screen_counter;
	}

	public Register getInput_register() {
		return input_register;
	}

	public void setInput_register(Register input_register) {
		this.input_register = input_register;
	}

	public boolean isProgram_end() {
		return program_end;
	}

	public void setProgram_end(boolean program_end) {
		this.program_end = program_end;
	}

	public DataStackMemory getStack_memory() {
		return stack_memory;
	}

	public void setStack_memory(DataStackMemory stack_memory) {
		this.stack_memory = stack_memory;
	}

	public StackPointer getStack_pointer() {
		return stack_pointer;
	}

	public void setStack_pointer(StackPointer stack_pointer) {
		this.stack_pointer = stack_pointer;
	}

	public boolean isOverflow() {
		return overflow;
	}

	public void setOverflow(boolean overflow) {
		this.overflow = overflow;
	}



	public Register getOutput_register() {
		return output_register;
	}

	public void setOutput_register(Register output_register) {
		this.output_register = output_register;
	}

	

	public int getD() {
		return D;
	}

	public void setD(int d) {
		D = d;
	}

	public Alu getAlu() {
		return alu;
	}

	public void setAlu(Alu alu) {
		this.alu = alu;
	}

	public String[] getOperations() {
		return operations;
	}

	public void setOperations(String[] operations) {
		this.operations = operations;
	}

	public boolean isInput_register_exist() {
		return input_register_exist;
	}

	public void setInput_register_exist(boolean input_register_exist) {
		this.input_register_exist = input_register_exist;
	}

	public Register getR0() {
		return R0;
	}

	public void setR0(Register r0) {
		R0 = r0;
	}

	public Register getR1() {
		return R1;
	}

	public void setR1(Register r1) {
		R1 = r1;
	}

	public Register getR2() {
		return R2;
	}

	public void setR2(Register r2) {
		R2 = r2;
	}





	
	
}
