
public class Memory {
	private Slot[] slots;
	private int memory_size;
	private int bit_lenght;
	private boolean read_enable;
	private int starting_slot;// which slot is staring index
	
	public Memory(int memory_size, int bit_lenght, boolean read_enable) {
		
		slots=new Slot[memory_size];
		
		for (int i = 0; i < slots.length; i++) {
			Slot sl=new Slot();
			slots[i]=sl;
		}
		this.memory_size = memory_size;
		this.bit_lenght = bit_lenght;
		this.read_enable = read_enable;
		starting_slot =0;
	}

	public Slot[] getSlots() {
		return slots;
	}
	public void setSlots(Slot[] slots) {
		this.slots = slots;
	}
	public int getMemory_size() {
		return memory_size;
	}
	public void setMemory_size(int memory_size) {
		this.memory_size = memory_size;
	}
	public int getBit_lenght() {
		return bit_lenght;
	}
	public void setBit_lenght(int bit_lenght) {
		this.bit_lenght = bit_lenght;
	}
	public boolean isRead_enable() {
		return read_enable;
	}
	public void setRead_enable(boolean read_enable) {
		this.read_enable = read_enable;
	}
	public int getStarting_slot() {
		return starting_slot;
	}
	public void setStarting_slot(int staring_slot) {
		this.starting_slot = staring_slot;
	}
	
	

}
