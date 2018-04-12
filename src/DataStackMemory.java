
public class DataStackMemory extends Memory {
	private boolean write_enable;
	public DataStackMemory(int memory_size, int bit_lenght, boolean read_enable) {
		
		super(memory_size, bit_lenght, read_enable);
	
		// TODO Auto-generated constructor stub
	}
	
	public boolean isWrite_enable() {
		return write_enable;
	}
	public void setWrite_enable(boolean write_enable) {
		this.write_enable = write_enable;
	}

	

}
