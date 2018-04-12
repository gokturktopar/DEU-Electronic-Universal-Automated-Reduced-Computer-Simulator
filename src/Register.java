
public class Register {
	private String data;
	private boolean clock;
	private boolean clear;
	private boolean load;
	public Register(String data,boolean clock,boolean clear,boolean load){
		this.data=data;
		this.clock=clock;
		this.clear=clear;
		this.load=load;
	}
	public Register(){
		
		
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public boolean isClock() {
		return clock;
	}
	public void setClock(boolean clock) {
		this.clock = clock;
	}
	public boolean isClear() {
		return clear;
	}
	public void setClear(boolean clear) {
		this.clear = clear;
	}
	public boolean isLoad() {
		return load;
	}
	public void setLoad(boolean load) {
		this.load = load;
	}
	
	
	
}
