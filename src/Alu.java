
public class Alu {
	
	
private Boolean overflow;
public Alu(){
	overflow=false;
	
}
	
	public void Not(Register s1,Register d) //1's complement  for D4T3 statement
	{
		
       String a=s1.getData(); 
       String temp="";
       for (int i =0; i <4; i++) {
		
    	   if(a.substring(i, i+1).equalsIgnoreCase("1"))
    	   {
    		  temp+="0";
    	   }
    	   
    	   else if(a.substring(i, i+1).equalsIgnoreCase("0"))
    	   {
    		temp+="1";
    	   }
    	  		   
	}
       d.setData(temp);
       
		
	}
	
	public void Add(Register s1,Register s2,Register d) // for D0T3 statement
	{
		
       String a=s1.getData(); 
       String b=s2.getData(); 
       String temp_add[]=new String[4];
       boolean overflow_process=false;
       
    for (int i = 3; i >=0; i--) {
		if (a.substring(i,i+1).equals("0")&&b.substring(i,i+1).equals("0")) {
			
			if(overflow_process){
				temp_add[i]="1";
				overflow_process=false;
			}
			else{
				temp_add[i]="0";
			}
		}
		else if ((a.substring(i,i+1).equals("1")&&b.substring(i,i+1).equals("0"))||
				(a.substring(i,i+1).equals("0")&&b.substring(i,i+1).equals("1"))) {
			if(overflow_process){
				temp_add[i]="0";
			}
			else{
				temp_add[i]="1";
			}
		}
		else if (a.substring(i,i+1).equals("1")&&b.substring(i,i+1).equals("1")) {
		if(overflow_process){
			temp_add[i]="1";
			}
			else{
				temp_add[i]="0";
			}
		overflow_process=true;
		}
	}
    String add="";
    overflow=overflow_process;
    for (int i = 0; i < temp_add.length; i++) {
		add+=temp_add[i];
	}
       d.setData(add);	
	}
	
	public void And(Register s1,Register s2,Register d) //for D5T3 statement
	{
		String a=s1.getData();
		String b=s2.getData();
		String temp_end ="";
		
		
			for (int i =0; i <4; i++) {
				
				if (a.substring(i,i+1).equals("0")&&b.substring(i,i+1).equals("0")) {
					temp_end+="0";
				}
				else if ((a.substring(i,i+1).equals("1")&&b.substring(i,i+1).equals("0"))||
						(a.substring(i,i+1).equals("0")&&b.substring(i,i+1).equals("1"))) {
					temp_end+="0";
				}
				else if (a.substring(i,i+1).equals("1")&&b.substring(i,i+1).equals("1")) {
					temp_end+="1";
				}
			}
			d.setData(temp_end);
		
		
	}
	public void Transfer(Register s1,Register d)//for  D9T3 statement
	{
		d.setData(s1.getData());
	}
	public void DBT(Register s1,Register d) //for  D3T3 statement
	{
		String s1_temp=s1.getData();		
		String dbt = "";
		
		dbt=s1_temp.substring(0,1)+s1_temp.substring(0,1)+s1_temp.substring(1,2)+s1_temp.substring(2,3);
		d.setData(dbt);
		
	}
	
	public void DBL(Register s1,Register d) //for  D2T3 statement
	{
		String s1_temp=s1.getData();		
		String dbl = "";
		
		dbl=s1_temp.substring(1,2)+s1_temp.substring(2,3)+s1_temp.substring(3,4)+"0";
		
		d.setData(dbl);
		
	}
	
	
	public void Load1(Register s1,Register s2,Register d) //for D6QT3 statement
	{
		d.setData(s2.getData()+s1.getData());
	}
	public void Load2(String s1,String s2,Register AR) //for D6Q'T3 statement
	{
		AR.setData(s2+s1);
	}
	
	public void Load3(Register d,Register AR,DataStackMemory DM) //for D6Q'T4 statement
	{
		int decimal_AR=Integer.parseInt(AR.getData(), 2);
		
		d.setData(DM.getSlots()[decimal_AR].getData());
	}
	
	public void Store1(Register s2,Register d) //for D7QT3 statement
	{
		String a=d.getData();
	
		s2.setData(a);
	}
	
	public void Store2(String s1,String s2,Register AR) //for D7Q'T3 statement
	{
		
		AR.setData(s2+s1);
	}
	
	public void Store3(Register d,Register AR,DataStackMemory DM) //for D7Q’T4 statement
	{
		int decimal_AR=Integer.parseInt(AR.getData(), 2);
		DM.getSlots()[decimal_AR].setData(d.getData());
	}
	
	
	public void Increment(Register s1,Register d)//for D1T3 statement 
	{
		String temp_inc[]=new String[4];
		boolean over=false;
		if(s1.getData().substring(3,4).equals("1")){
			temp_inc[3]="0";
			over=true;
		}
		else{
			temp_inc[3]="1";
		}
		
		for (int i = 2; i >=0; i--) {
			if(over){
				if(s1.getData().substring(i,i+1).equals("1")){
					temp_inc[i]="0";
				}
				else{
					temp_inc[i]="1";
				}
			}
			else{
				if(s1.getData().substring(i,i+1).equals("1")){
					temp_inc[i]="1";
				}
				else{
					temp_inc[i]="0";
				}
			}
		}
		 String add="";
		    overflow=over;
		    for (int i = 0; i < temp_inc.length; i++) {
				add+=temp_inc[i];
			}
		    d.setData(add);
	}
	
	public void Call1(DataStackMemory SM,StackPointer SP,ProgramCounter PC)   //for D10T3 statement
	{
		if(SP.getData()==null){
			SP.setData("0000");
		}
		int decimal_SP=Integer.parseInt(SP.getData(), 2);
		SM.getSlots()[decimal_SP].setData(PC.getData());
		
	}
	
	public void Call2(Register IR,StackPointer SP,ProgramCounter PC)  //for D10T4 statement
	{
		
		PC.setData(IR.getData().substring(7,11));
		int decimal_SP=Integer.parseInt(SP.getData(), 2);
		decimal_SP=decimal_SP+1;
		SP.setData(Process.string_to_bin_string(Integer.toString(decimal_SP), 4));
		
	}
	
	public void Return1(StackPointer SP)   //for D11T3 statement
	{
		int decimal_SP=Integer.parseInt(SP.getData(), 2);
		decimal_SP--;
		SP.setData(Process.string_to_bin_string(Integer.toString(decimal_SP), 4));
		
	}
	
	public void Return2(DataStackMemory SM,StackPointer SP,ProgramCounter PC)   //for D11T4 statement
	{
		int decimal_SP=Integer.parseInt(SP.getData(), 2);
		PC.setData(SM.getSlots()[decimal_SP].getData());
	}
	
	public void Push1(Register AR,Register IR) //for D14T3 statement
	{
		
		AR.setData(IR.getData().substring(8,11)); //take IR[3..0] sub[7,10] because data left to right 
		
	}
	
	public void Push2(Register AR,DataStackMemory DM,DataStackMemory SM,StackPointer SP) //for D14T4 statement
	{

		int decimal_SP=Integer.parseInt(SP.getData(), 2);
		int decimal_AR=Integer.parseInt(AR.getData(), 2);
		
		SM.getSlots()[decimal_SP].setData(DM.getSlots()[decimal_AR].getData());
	}
	
	public void Push3(StackPointer SP) //for D14T5 statement
	{
		int decimal_SP=Integer.parseInt(SP.getData(), 2);
		decimal_SP=decimal_SP+1;
		SP.setData(Process.string_to_bin_string(Integer.toString(decimal_SP), 4));
	}
	
	public void Pop1(Register AR,Register IR) //for D15T3 statement
	{
		
		AR.setData(IR.getData().substring(8,11)); //take IR[3..0] sub[7,10] because data left to right 
		
	}
	
	public void Pop2(StackPointer SP) //for D15T4 statement
	{

		int decimal_SP=Integer.parseInt(SP.getData(), 2);
		decimal_SP=decimal_SP-1;
		SP.setData(Process.string_to_bin_string(Integer.toString(decimal_SP), 4));
	}
	
	public void Pop3(Register AR,DataStackMemory DM,DataStackMemory SM,StackPointer SP) //for D15T5 statement
	{

		int decimal_SP=Integer.parseInt(SP.getData(), 2);
		int decimal_AR=Integer.parseInt(AR.getData(), 2);
		
		DM.getSlots()[decimal_AR].setData(SM.getSlots()[decimal_SP].getData());
	}
	
	
	
	public void JMR(ProgramCounter pc,Register ins_register,int jmr){
		
		int pc_int=Integer.parseInt(pc.getData(),2);
		pc_int+=jmr;
		pc.setData(Process.string_to_bin_string(Integer.toString(pc_int), 5));
		
	}
	public void JMP1(Register IR,ProgramCounter pc){
		IR.getData().substring(7,11);
	}
	public void JMP2(Register IR,ProgramCounter pc){
		if(overflow){
			pc.setData(IR.getData().substring(7,11));
		}
	}

	public Boolean getOverflow() {
		return overflow;
	}

	public void setOverflow(Boolean overflow) {
		this.overflow = overflow;
	}

	

}
