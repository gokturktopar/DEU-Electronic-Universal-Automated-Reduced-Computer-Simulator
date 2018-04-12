
public class StackPointer extends Register {
	private boolean inc_or_dec;
	public StackPointer(String data, boolean clock, boolean clear, boolean load) {
		super(data, clock, clear, load);
		// TODO Auto-generated constructor stub
	}
	public StackPointer() {
		
	}
	public boolean isInc_or_dec() {
		return inc_or_dec;
	}
	public void setInc_or_dec(boolean inc_or_dec) {
		this.inc_or_dec = inc_or_dec;
	}
	public void inc_dec(){
		
	}
}
