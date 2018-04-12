
public class ControlUnit {

	public int inst_memory_read_enable(int T){
		if(T==0){
			return 1;
			
		}
		else{
			
			return 0;
		}
		
	}
	public int data_memory_read_enable(int T,int D,int Q){
		if((D==6&&Q==0&&T==4)||D==14&&T==4){
			return 1;
			
		}
		else{
			
			return 0;
		}
		
	}
	public int data_memory_write_enable(int T,int D,int Q){
		if((D==7&&Q==0&&T==4)||D==15&&T==5){
			return 1;
			
		}
		else{
			
			return 0;
		}
		
	}
	public int stack_memory_read_enable(int T,int D){
		if((D==11&&T==4)||D==15&&T==5){
			return 1;
			
		}
		else{
			
			return 0;
		}
		
	}
	public int stack_memory_write_enable(int T,int D){
		if((D==10&&T==3)||D==14&&T==4){
			return 1;
			
		}
		else{
			
			return 0;
		}
		
	}
	public int program_counter_load(int T,int D,int Q){
		if((D==10&&T==4)||(D==11&&T==4)||(D==12&&Q==0&&T==3)||(D==12&&Q==1&&T==3)||(D==13&&T==3)){
			return 1;
			
		}
		else{
			
			return 0;
		}
		
	}
	public int adress_register_load(int T,int D,int Q){
		if((D==6&&Q==0&&T==3)||(D==7&&Q==0&&T==3)||(D==14&T==3)||(D==15&&T==3)){
			return 1;
			
		}
		else{
			
			return 0;
		}
		
	}
	public int ins_register_load(int T){
		if(T==0){
			return 1;
			
		}
		else{
			
			return 0;
		}
		
	}
	public int S1_register_load(int T){
		if(T==2){
			return 1;
			
		}
		else{
			
			return 0;
		}
		
	}
	public int S2_register_load(int T,int D,int Q){
		if(T==2||(T==2&&D==7&&Q==1)){
			return 1;
			
		}
		else{
			
			return 0;
		}
		
	}
	public int D_register_load(int T,int D,int Q){
		if((D==6&&Q==0&T==4)||(D==9&&T==3)||(D==0&&T==3)||(T==2)||(D==1&&T==3)||(D==2&&T==3)||(D==3&&T==3)||(D==4&&T==3)||(D==5&&T==3)||(D==6&&Q==1&&T==3)){
			return 1;
			
		}
		else{
			
			return 0;
		}
		
	}
}
