import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Choice;
import java.awt.SystemColor;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

public class test {
	
	static Process  processses;
	private  JFrame frame;
	private  JTable tabledatamemory;
	private  JTable tableinstructionmemory;
	private  JTable stackmemorytable;
	private  JTable LABELtable;
	private  File selectedFile;// file which is be taken user
	private String selectedtype;
	private JTextField adress_register_text;
	private JTextField program_counter_text;
	private JTextField inst_register_text;
	private JTextField stack_pointer_text;
	private JTextField input_register_text;
	private JTextField output_register_text;
	private JTextField register_s1_text;
	private JTextField register_s2_text;
	private JTextField register_d_text;
	private static int pc_temp;
	private JTextField Q_text;
	private JTextField opcode;
	private JTextField overflow_text;
	private JTextField message_box_text;
	
	private ButtonGroup btn_group = new ButtonGroup();
	private JTable process_table;
	private int process_screen_line_counter=0;
private String radio_previous="bin";
private JTextField register_0_textt;
private JTextField register_1_textt;
private JTextField register_2_textt;
	public static void main(String[] args) {
		pc_temp=0;
		//Screen screen=new Screen();
		processses=new Process();
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {
					test fr=new test();
			fr.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	public test() {
		
		initialize();
		
	}
	public void initialize() {
		frame = new JFrame();
		
		frame.getContentPane().setBackground(new Color(153, 204, 255));
		frame.getContentPane().setForeground(Color.GRAY);
		frame.setBounds(100, 100, 1500, 2000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblDeuArc = new JLabel("DEU ARC");
		lblDeuArc.setBounds(493, 11, 328, 31);
		lblDeuArc.setFont(new Font("Adobe Devanagari", Font.BOLD | Font.ITALIC, 20));
		lblDeuArc.setForeground(Color.BLACK);
		frame.getContentPane().add(lblDeuArc);
		
		Button filechoose = new Button("Choose File");
		filechoose.setForeground(Color.BLACK);
		filechoose.setBackground(Color.WHITE);
		filechoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { //File choose
					JFileChooser jfilechooser=new JFileChooser();
					jfilechooser.setCurrentDirectory(new File(System.getProperty("user.home")));
					int result = jfilechooser.showOpenDialog(frame);
					if (result == JFileChooser.APPROVE_OPTION) {
					  selectedFile = jfilechooser.getSelectedFile();
					    test.processses.setSelectedfile(selectedFile);
					    test.processses.read_parse();
					    test.processses.assign_memories();
					    FilleDataMemoryScreen();
						FilleTableLableScreen();
						FilleInstMemoryScreen();
					}
			}
		});
		filechoose.setBounds(10, 35, 70, 22);
		frame.getContentPane().add(filechoose);
		
		JLabel adressregister_text = new JLabel("ADRESS REGISTER");
		adressregister_text.setFont(new Font("Tahoma", Font.BOLD, 15));
		adressregister_text.setForeground(Color.BLACK);
		adressregister_text.setBackground(Color.LIGHT_GRAY);
		adressregister_text.setBounds(10, 83, 152, 14);
		frame.getContentPane().add(adressregister_text);
		
		JLabel ProgramCounterlbl = new JLabel("PROGRAM COUNTER\r\n");
		ProgramCounterlbl.setForeground(Color.BLACK);
		ProgramCounterlbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		ProgramCounterlbl.setBackground(Color.LIGHT_GRAY);
		ProgramCounterlbl.setBounds(219, 83, 177, 14);
		frame.getContentPane().add(ProgramCounterlbl);
		
		JLabel stacklbl = new JLabel("STACK POINTER");
		stacklbl.setForeground(Color.BLACK);
		stacklbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		stacklbl.setBackground(Color.LIGHT_GRAY);
		stacklbl.setBounds(219, 139, 152, 14);
		frame.getContentPane().add(stacklbl);
		
		JLabel outputlbl = new JLabel("OUTPUT REGISTER");
		outputlbl.setForeground(Color.BLACK);
		outputlbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		outputlbl.setBackground(Color.LIGHT_GRAY);
		outputlbl.setBounds(219, 195, 152, 14);
		frame.getContentPane().add(outputlbl);
		
		JLabel instructionlbl = new JLabel("INSTRUCTION REGISTER");
		instructionlbl.setForeground(Color.BLACK);
		instructionlbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		instructionlbl.setBackground(Color.LIGHT_GRAY);
		instructionlbl.setBounds(10, 139, 197, 14);
		frame.getContentPane().add(instructionlbl);
		
		JLabel inputlbl = new JLabel("INPUT REGISTER");
		inputlbl.setForeground(Color.BLACK);
		inputlbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		inputlbl.setBackground(Color.LIGHT_GRAY);
		inputlbl.setBounds(10, 195, 152, 14);
		frame.getContentPane().add(inputlbl);
		
		JLabel insmemolbl = new JLabel("INSTRUCTION MEMORY");
		insmemolbl.setForeground(Color.BLACK);
		insmemolbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		insmemolbl.setBackground(Color.LIGHT_GRAY);
		insmemolbl.setBounds(966, 83, 183, 14);
		frame.getContentPane().add(insmemolbl);
		
		JLabel datamemeorylbl = new JLabel("DATA MEMORY");
		datamemeorylbl.setForeground(Color.BLACK);
		datamemeorylbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		datamemeorylbl.setBackground(Color.LIGHT_GRAY);
		datamemeorylbl.setBounds(616, 73, 152, 14);
		frame.getContentPane().add(datamemeorylbl);
		
		JLabel r3 = new JLabel("D");
		r3.setForeground(Color.BLACK);
		r3.setFont(new Font("Tahoma", Font.BOLD, 15));
		r3.setBackground(Color.LIGHT_GRAY);
		r3.setBounds(219, 309, 152, 14);
		frame.getContentPane().add(r3);
		
		JLabel r2 = new JLabel("S2");
		r2.setForeground(Color.BLACK);
		r2.setFont(new Font("Tahoma", Font.BOLD, 15));
		r2.setBackground(Color.LIGHT_GRAY);
		r2.setBounds(282, 309, 152, 14);
		frame.getContentPane().add(r2);
		
		JLabel stackmemolbl = new JLabel("STACK MEMORY");
		stackmemolbl.setForeground(Color.BLACK);
		stackmemolbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		stackmemolbl.setBackground(Color.LIGHT_GRAY);
		stackmemolbl.setBounds(632, 400, 152, 14);
		frame.getContentPane().add(stackmemolbl);
		
		JLabel register1 = new JLabel("S1");
		register1.setForeground(Color.BLACK);
		register1.setFont(new Font("Tahoma", Font.BOLD, 15));
		register1.setBackground(Color.LIGHT_GRAY);
		register1.setBounds(352, 309, 152, 14);
		frame.getContentPane().add(register1);
		
		JScrollPane datamemorypanel = new JScrollPane();
		datamemorypanel.setBounds(553, 113, 268, 206);
		frame.getContentPane().add(datamemorypanel);
		
		tabledatamemory = new JTable();
		Object[][] datam=new Object[16][4];
		tabledatamemory.setModel(new DefaultTableModel(
				datam,
				new String[] {
					"", "BIN", "HEX", "DEC"
				}
			));
		
		tabledatamemory.getColumnModel().getColumn(0).setPreferredWidth(17);
		
	
		
		datamemorypanel.setViewportView(tabledatamemory);
		
		JScrollPane instrucpanel = new JScrollPane();
		instrucpanel.setBounds(830, 113, 522, 206);
		frame.getContentPane().add(instrucpanel);
		
		tableinstructionmemory = new JTable();
		Object[][] instm=new Object[32][2];
		tableinstructionmemory.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"", "BIN", "HEX", "DEC", "CODE"
			}
		));
		tableinstructionmemory.getColumnModel().getColumn(0).setPreferredWidth(35);
		instrucpanel.setViewportView(tableinstructionmemory);
		
		JScrollPane stackpanel = new JScrollPane();
		stackpanel.setBounds(553, 435, 314, 206);
		frame.getContentPane().add(stackpanel);
		
		stackmemorytable = new JTable();
		
		stackmemorytable.setModel(new DefaultTableModel(
			new Object[][] {
				{"0", null, null, null},
				{"1", null, null, null},
				{"2", null, null, null},
				{"3", null, null, null},
				{"4", null, null, null},
				{"5", null, null, null},
				{"6", null, null, null},
				{"7", null, null, null},
				{"8", null, null, null},
				{"9", null, null, null},
				{"10", null, null, null},
				{"11", null, null, null},
				{"12", null, null, null},
				{"13", null, null, null},
				{"14", null, null, null},
				{"15", null, null, null},
			},
			new String[] {
				"", "BIN", "HEX", "DEC"
			}
		));
		stackmemorytable.getColumnModel().getColumn(0).setPreferredWidth(24);
		stackpanel.setViewportView(stackmemorytable);
		
		JScrollPane labeltablepanel = new JScrollPane();
		
		labeltablepanel.setBounds(900, 424, 452, 206);
		frame.getContentPane().add(labeltablepanel);
		
		LABELtable = new JTable();
		Object[][] labeltable=new Object[15][5];
		
		LABELtable.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
					{null, null, null,null,null},
				},
				new String[] {
					"VARIBLE", "ADDRESS", "","CONTENT",""
				}
			));
			
		
	
		
		labeltablepanel.setViewportView(LABELtable);
		
		JLabel lblLabelTable = new JLabel("LABEL TABLE");
		
		lblLabelTable.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLabelTable.setBounds(1070, 388, 123, 14);
		frame.getContentPane().add(lblLabelTable);
		
		adress_register_text = new JTextField();
		adress_register_text.setBounds(10, 108, 152, 20);
		frame.getContentPane().add(adress_register_text);
		adress_register_text.setColumns(10);
		
		program_counter_text = new JTextField();
		program_counter_text.setColumns(10);
		program_counter_text.setBounds(219, 108, 162, 20);
		frame.getContentPane().add(program_counter_text);
		
		inst_register_text = new JTextField();
		inst_register_text.setColumns(10);
		inst_register_text.setBounds(10, 164, 152, 20);
		frame.getContentPane().add(inst_register_text);
		
		stack_pointer_text = new JTextField();
		stack_pointer_text.setColumns(10);
		stack_pointer_text.setBounds(219, 164, 149, 20);
		frame.getContentPane().add(stack_pointer_text);
		
		input_register_text = new JTextField();
		input_register_text.setColumns(10);
		input_register_text.setBounds(10, 220, 152, 20);
		frame.getContentPane().add(input_register_text);
		
		output_register_text = new JTextField();
		output_register_text.setColumns(10);
		output_register_text.setBounds(219, 220, 152, 20);
		frame.getContentPane().add(output_register_text);
		
		JButton MicroOperationBase = new JButton("Micro Operation Base");
		MicroOperationBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!test.processses.isProgram_end()){
					
					if(test.processses.isInput_register_exist()){
						message_box_text.setText("PLEASE ENTER CONTENT OF INPUT REGISTER");
					}
					register_s1_text.setText("");
					register_s2_text.setText("");
					register_d_text.setText("");
				test.processses.inc_T(false);
				if(test.processses.getT()==0){
					pc_temp=Integer.parseInt(test.processses.getProgramcounter().getData(), 2);
				inst_register_text.setText(test.processses.getInstruction_register().getData());
				program_counter_text.setText(test.processses.getProgramcounter().getData());
				
				if(input_register_text.getText()!=null&&!input_register_text.getText().equals("")){
					String temp="";
					for (int i = 0; i < 4-input_register_text.getText().length(); i++) {
						temp+="0";
					}
					temp+=input_register_text.getText();
					test.processses.getInput_register().setData(input_register_text.getText());
					
				}
				
				}
				else if(test.processses.getT()==1){
					program_counter_text.setText(test.processses.getProgramcounter().getData());
					if(input_register_text.getText()==null&&!input_register_text.getText().equals("")){
						test.processses.getInput_register().setData(input_register_text.getText());
					}
					
					
					
					}
				else if(test.processses.getT()==2){
					register_s1_text.setText(test.processses.getInstruction_register().getData().substring(9,11));
					register_s2_text.setText(test.processses.getInstruction_register().getData().substring(7,9));
					register_d_text.setText(test.processses.getInstruction_register().getData().substring(5,7));
					opcode.setText(test.processses.getInstruction_register().getData().substring(1, 5));
					Q_text.setText(test.processses.getInstruction_register().getData().substring(0, 1));
					if(input_register_text.getText()==null&&!input_register_text.getText().equals("")){
						test.processses.getInput_register().setData(input_register_text.getText());
					}
					}
				else if(test.processses.getT()==3){
					register_s1_text.setText(test.processses.getInstruction_register().getData().substring(9,11));
					register_s2_text.setText(test.processses.getInstruction_register().getData().substring(7,9));
					register_d_text.setText(test.processses.getInstruction_register().getData().substring(5,7));
					opcode.setText(test.processses.getInstruction_register().getData().substring(1, 5));
					Q_text.setText(test.processses.getInstruction_register().getData().substring(0, 1));
					program_counter_text.setText(test.processses.getProgramcounter().getData());
					if(test.processses.getR0().getData()!=null)
						register_0_textt.setText(test.processses.getR0().getData());
						if(test.processses.getR1().getData()!=null)
						register_1_textt.setText(test.processses.getR1().getData());
						if(test.processses.getR2().getData()!=null)
						register_2_textt.setText(test.processses.getR2().getData());
					if(test.processses.getInput_register().getData()!=null){
						input_register_text.setText(test.processses.getInput_register().getData());
					}
					if(test.processses.getOutput_register().getData()!=null){
						output_register_text.setText(test.processses.getOutput_register().getData());
					}
					if(test.processses.getStack_pointer().getData()!=null){
						stack_pointer_text.setText(test.processses.getStack_pointer().getData());
					}
					if(test.processses.getAdressregister().getData()!=null){
						adress_register_text.setText(test.processses.getAdressregister().getData());
					}
					if(test.processses.getAlu().getOverflow()){
						overflow_text.setText("1");
					}
					else{
						overflow_text.setText("0");
					}
					FilleStackMemoryScreen();
					}
				else if(test.processses.getT()==4){
					register_s1_text.setText(test.processses.getInstruction_register().getData().substring(9,11));
					register_s2_text.setText(test.processses.getInstruction_register().getData().substring(7,9));
					register_d_text.setText(test.processses.getInstruction_register().getData().substring(5,7));
					opcode.setText(test.processses.getInstruction_register().getData().substring(1, 5));
					Q_text.setText(test.processses.getInstruction_register().getData().substring(0, 1));
					program_counter_text.setText(test.processses.getProgramcounter().getData());
					if(test.processses.getR0().getData()!=null)
						register_0_textt.setText(test.processses.getR0().getData());
						if(test.processses.getR1().getData()!=null)
						register_1_textt.setText(test.processses.getR1().getData());
						if(test.processses.getR2().getData()!=null)
						register_2_textt.setText(test.processses.getR2().getData());
					if(test.processses.getInput_register().getData()!=null){
						input_register_text.setText(test.processses.getInput_register().getData());
					}
					if(test.processses.getOutput_register().getData()!=null){
						output_register_text.setText(test.processses.getOutput_register().getData());
					}
					if(test.processses.getStack_pointer().getData()!=null){
						stack_pointer_text.setText(test.processses.getStack_pointer().getData());
					}
					if(test.processses.getAdressregister().getData()!=null){
						adress_register_text.setText(test.processses.getAdressregister().getData());
					}
					if(test.processses.getAlu().getOverflow()){
						overflow_text.setText("1");
					}
					else{
						overflow_text.setText("0");
					}
					FilleStackMemoryScreen();
					}
				else if(test.processses.getT()==5){
					register_s1_text.setText(test.processses.getInstruction_register().getData().substring(9,11));
					register_s2_text.setText(test.processses.getInstruction_register().getData().substring(7,9));
					register_d_text.setText(test.processses.getInstruction_register().getData().substring(5,7));
					opcode.setText(test.processses.getInstruction_register().getData().substring(1, 5));
					Q_text.setText(test.processses.getInstruction_register().getData().substring(0, 1));
					program_counter_text.setText(test.processses.getProgramcounter().getData());
					if(test.processses.getR0().getData()!=null)
					register_0_textt.setText(test.processses.getR0().getData());
					if(test.processses.getR1().getData()!=null)
					register_1_textt.setText(test.processses.getR1().getData());
					if(test.processses.getR2().getData()!=null)
					register_2_textt.setText(test.processses.getR2().getData());
					if(test.processses.getInput_register().getData()!=null){
						input_register_text.setText(test.processses.getInput_register().getData());
					}
					if(test.processses.getOutput_register().getData()!=null){
						output_register_text.setText(test.processses.getOutput_register().getData());
					}
					if(test.processses.getStack_pointer().getData()!=null){
						stack_pointer_text.setText(test.processses.getStack_pointer().getData());
					}
					if(test.processses.getAdressregister().getData()!=null){
						adress_register_text.setText(test.processses.getAdressregister().getData());
					}
					if(test.processses.getAlu().getOverflow()){
						overflow_text.setText("1");
					}
					else{
						overflow_text.setText("0");
					}
					FilleStackMemoryScreen();
					}
				
				FilleProcessTable_Microop_Base(test.processses.getT(),test.processses.getLines_for_screen()[pc_temp]);
				  FilleDataMemoryScreen();
					FilleTableLableScreen();
					FilleInstMemoryScreen();
				
				}
				else{
					
				message_box_text.setText("PROGRAM TERMINATED SUCCESFULLY..");
				}
			}
		});
		MicroOperationBase.setBounds(10, 454, 183, 23);
		frame.getContentPane().add(MicroOperationBase);
		
		JButton InstructýonBase = new JButton("Instruction Base");
		InstructýonBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!test.processses.isProgram_end()){
				if(input_register_text.getText()!=null&&!input_register_text.getText().equals("")){
					String temp="";
					for (int i = 0; i < 4-input_register_text.getText().length(); i++) {
						temp+="0";
					}
					temp+=input_register_text.getText();
					test.processses.getInput_register().setData(input_register_text.getText());
					
				}
				pc_temp=Integer.parseInt(test.processses.getProgramcounter().getData(), 2);
				test.processses.inc_T(true);
				inst_register_text.setText(test.processses.getInstruction_register().getData());
				program_counter_text.setText(test.processses.getProgramcounter().getData());
				register_s1_text.setText(test.processses.getInstruction_register().getData().substring(9,11));
				register_s2_text.setText(test.processses.getInstruction_register().getData().substring(7,9));
				register_d_text.setText(test.processses.getInstruction_register().getData().substring(5,7));
				opcode.setText(test.processses.getInstruction_register().getData().substring(1, 5));
				Q_text.setText(test.processses.getInstruction_register().getData().substring(0, 1));
				FilleProcessTable_Ins_Base(test.processses.getLines_for_screen()[ process_screen_line_counter]);
				  FilleDataMemoryScreen();
					FilleTableLableScreen();
					FilleInstMemoryScreen();
					if(test.processses.getR0().getData()!=null)
						register_0_textt.setText(test.processses.getR0().getData());
						if(test.processses.getR1().getData()!=null)
						register_1_textt.setText(test.processses.getR1().getData());
						if(test.processses.getR2().getData()!=null)
						register_2_textt.setText(test.processses.getR2().getData());
						if(test.processses.getInput_register().getData()!=null){
							input_register_text.setText(test.processses.getInput_register().getData());
						}
						if(test.processses.getOutput_register().getData()!=null){
							output_register_text.setText(test.processses.getOutput_register().getData());
						}
						if(test.processses.getStack_pointer().getData()!=null){
							stack_pointer_text.setText(test.processses.getStack_pointer().getData());
						}
						if(test.processses.getAdressregister().getData()!=null){
							adress_register_text.setText(test.processses.getAdressregister().getData());
						}
						if(test.processses.getAlu().getOverflow()){
							overflow_text.setText("1");
						}
						else{
							overflow_text.setText("0");
						}
						FilleStackMemoryScreen();
						FilleProcessTable_Ins_Base(test.processses.getLines_for_screen()[pc_temp]);
			}
			else{
				message_box_text.setText("PROGRAM TERMINATED SUCCESFULLY..");
			}
			}
		});
		InstructýonBase.setBounds(225, 454, 146, 23);
		frame.getContentPane().add(InstructýonBase);
		
		register_s1_text = new JTextField();
		register_s1_text.setBounds(352, 334, 56, 20);
		frame.getContentPane().add(register_s1_text);
		register_s1_text.setColumns(10);
		
		register_s2_text = new JTextField();
		register_s2_text.setText("");
		register_s2_text.setBounds(282, 334, 46, 20);
		frame.getContentPane().add(register_s2_text);
		register_s2_text.setColumns(10);
		
		register_d_text = new JTextField();
		register_d_text.setBounds(202, 334, 59, 20);
		frame.getContentPane().add(register_d_text);
		register_d_text.setColumns(10);
		
		JScrollPane Process_Screen = new JScrollPane();
		Process_Screen.setBounds(10, 488, 486, 128);
		frame.getContentPane().add(Process_Screen);
		
		process_table = new JTable();
		process_table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"Process Name", "Time", "Content", "Code"
			}
		));
		process_table.getColumnModel().getColumn(0).setPreferredWidth(104);
		process_table.getColumnModel().getColumn(1).setPreferredWidth(47);
		process_table.getColumnModel().getColumn(2).setPreferredWidth(114);
		process_table.getColumnModel().getColumn(3).setPreferredWidth(113);
		Process_Screen.setViewportView(process_table);
		
		Q_text = new JTextField();
		Q_text.setBounds(10, 334, 86, 20);
		frame.getContentPane().add(Q_text);
		Q_text.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Q");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(22, 309, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("OPCODE");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(113, 309, 86, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		opcode = new JTextField();
		opcode.setText("");
		opcode.setBounds(106, 334, 86, 20);
		frame.getContentPane().add(opcode);
		opcode.setColumns(10);
		
		overflow_text = new JTextField();
		overflow_text.setBounds(219, 387, 86, 20);
		frame.getContentPane().add(overflow_text);
		overflow_text.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("OverFlow");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(212, 365, 96, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton export_button = new JButton("Export File");
		export_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
				    PrintWriter writer = new PrintWriter("instruction.mif", "UTF-8");
				    writer.println("DEPTH = 32; -- The size of memory in words");
				    writer.println("WIDTH = 11; -- The size of data in bits");
				    writer.println("ADDRESS_RADIX = HEX; -- The radix for address values");
				    writer.println("DATA_RADIX = BIN; -- The radix for data values");
				    writer.println("CONTENT -- start of (address : data pairs)");
				    writer.println("BEGIN");
				    for (int i = 0; i < test.processses.getInstruction_memory().getSlots().length; i++) {
				    	
						if (test.processses.getInstruction_memory().getSlots()[i].getData()!=null) {
							writer.println(i+" : "+processses.getInstruction_memory().getSlots()[i].getData()+";");
						}
						else{
							writer.println(i+" : "+"00000000000;");
						}
					}
				   writer.println("END;");
				    writer.close();
				} catch (IOException e) {
				   // do something
				}
			}
		});
		export_button.setBounds(145, 35, 129, 23);
		frame.getContentPane().add(export_button);
		
		JLabel message_box_txt = new JLabel("MESSAGE BOX");
		message_box_txt.setFont(new Font("Tahoma", Font.BOLD, 15));
		message_box_txt.setBounds(113, 627, 123, 14);
		frame.getContentPane().add(message_box_txt);
		
		message_box_text = new JTextField();
		message_box_text.setForeground(Color.RED);
		message_box_text.setBounds(22, 656, 366, 31);
		frame.getContentPane().add(message_box_text);
		message_box_text.setColumns(10);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(10, 401, 162, 27);
		frame.getContentPane().add(desktopPane);
		
		JRadioButton rb_dec = new JRadioButton("DEC");
		rb_dec.setBounds(0, 0, 54, 23);
		desktopPane.add(rb_dec);
		
		JRadioButton rb_bin = new JRadioButton("BIN");
		rb_bin.setSelected(true);
		rb_bin.setBounds(56, 0, 46, 23);
		desktopPane.add(rb_bin);
		
		JRadioButton rb_hex = new JRadioButton("HEX");
		rb_hex.setBounds(108, 0, 54, 23);
		desktopPane.add(rb_hex);
		btn_group.add(rb_dec);
		btn_group.add(rb_bin);
		btn_group.add(rb_hex);
		
		register_0_textt = new JTextField();
		register_0_textt.setBounds(10, 278, 152, 20);
		frame.getContentPane().add(register_0_textt);
		register_0_textt.setColumns(10);
		
		register_1_textt = new JTextField();
		register_1_textt.setColumns(10);
		register_1_textt.setBounds(182, 278, 152, 20);
		frame.getContentPane().add(register_1_textt);
		
		register_2_textt = new JTextField();
		register_2_textt.setColumns(10);
		register_2_textt.setBounds(344, 278, 152, 20);
		frame.getContentPane().add(register_2_textt);
		
		JLabel lblRegsterR_1 = new JLabel("REGISTER R1");
		lblRegsterR_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblRegsterR_1.setBounds(190, 253, 115, 14);
		frame.getContentPane().add(lblRegsterR_1);
		
		JLabel lblRegsterR = new JLabel("REGISTER R0");
		lblRegsterR.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblRegsterR.setBounds(20, 253, 112, 14);
		frame.getContentPane().add(lblRegsterR);
		
		JLabel lblRegsterR_2 = new JLabel("REGISTER R2");
		lblRegsterR_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblRegsterR_2.setBounds(352, 253, 115, 14);
		frame.getContentPane().add(lblRegsterR_2);
		rb_dec.addActionListener(new ActionListener() {
			 
		    @Override
		    public void actionPerformed(ActionEvent event) {
		 
		      if(radio_previous.equals("hex")){
		    	  hex_to_dec();
		      }
		      else{
		    	  bin_to_dec();
		      }
		      radio_previous="dec";
		    }
		});
		rb_hex.addActionListener(new ActionListener() {
			 
		    @Override
		    public void actionPerformed(ActionEvent event) {
		    	if(radio_previous.equals("bin")){
			    	  bin_to_hex();
			      }
			      else{
			    	  dec_to_hex();
			      }
		    	radio_previous="hex";
		 
		    }
		});
		rb_bin.addActionListener(new ActionListener() {
			 
		    @Override
		    public void actionPerformed(ActionEvent event) {
		    	if(radio_previous.equals("dec")){
			    	  dec_to_bin();
			      }
			      else{
			    	  hex_to_bin();
			      }
		      
		    	radio_previous="bin";
		    }
		});
		
		
	}


	public  void FilleTableLableScreen(){
		Object[][] labeltable=new Object[15][5];
		labeltable=test.processses.getLabelTable();
		LABELtable.setModel(new DefaultTableModel(
				labeltable,
				new String[] {
						"VARIBLE", "ADDRESS", "ADDRESS","CONTENT","CONTENT"
				}
			));
	}
	public void FilleProcessTable_Microop_Base(int T,String line){
		Object[][] screen=new Object[6][4];
		if(T==0){
			screen[0][0]="Fetch";
			screen[0][1]="T0";
			screen[0][2]="IR<--IM[PC]";
			screen[0][3]=line;
		}
		else if(T==1){
			screen[0][0]="Fetch";
			screen[0][1]="T0";
			screen[0][2]="IR<--IM[PC]";
			screen[0][3]=line;
			screen[1][0]="Fetch";
			screen[1][1]="T1";
			screen[1][2]="PC<--PC+1";
			screen[1][3]=line;
					
				}
		else if(T==2){
			screen[0][0]="Fetch";
			screen[0][1]="T0";
			screen[0][2]="IR<--IM[PC]";
			screen[0][3]=line;
			screen[1][0]="Fetch";
			screen[1][1]="T1";
			screen[1][2]="PC<--PC+1";
			screen[1][3]=line;
			screen[2][0]="Decode";
			screen[2][1]="T2";
			screen[2][2]="D0..D15<--IR[9..6],Q<--IR[10]=1, S2<--IR[1..0], S1<--IR[3..2], D<--IR[5..4]";
			screen[2][3]=line;
			
		}
		else if(T==3){
			screen[0][0]="Fetch";
			screen[0][1]="T0";
			screen[0][2]="IR<--IM[PC]";
			screen[0][3]=line;
			screen[1][0]="Fetch";
			screen[1][1]="T1";
			screen[1][2]="PC<--PC+1";
			screen[1][3]=line;
			screen[2][0]="Decode";
			screen[2][1]="T2";
			screen[2][2]="D0..D15<--IR[9..6],Q<--IR[10]=1, S2<--IR[1..0], S1<--IR[3..2], D<--IR[5..4]";
			screen[2][3]=line;
			screen[3][0]="Execute";
			screen[3][1]="T3";
			screen[3][3]=line;
			
		}
		else if(T==4){
			screen[0][0]="Fetch";
			screen[0][1]="T0";
			screen[0][2]="IR<--IM[PC]";
			screen[0][3]=line;
			screen[1][0]="Fetch";
			screen[1][1]="T1";
			screen[1][2]="PC<--PC+1";
			screen[1][3]=line;
			screen[2][0]="Decode";
			screen[2][1]="T2";
			screen[2][2]="D0..D15<--IR[9..6],Q<--IR[10]=1, S2<--IR[1..0], S1<--IR[3..2], D<--IR[5..4]";
			screen[2][3]=line;
			screen[3][0]="Execute";
			screen[3][1]="T3";
			screen[3][3]=line;
			screen[4][0]="Execute";
			screen[4][1]="T4";
			screen[4][3]=line;
			
		}
		else if(T==5){
			screen[0][0]="Fetch";
			screen[0][1]="T0";
			screen[0][2]="IR<--IM[PC]";
			screen[0][3]=line;
			screen[1][0]="Fetch";
			screen[1][1]="T1";
			screen[1][2]="PC<--PC+1";
			screen[1][3]=line;
			screen[2][0]="Decode";
			screen[2][1]="T2";
			screen[2][2]="D0..D15<--IR[9..6],Q<--IR[10]=1, S2<--IR[1..0], S1<--IR[3..2], D<--IR[5..4]";
			screen[2][3]=line;
			screen[3][0]="Execute";
			screen[3][1]="T3";
			screen[3][3]=line;
			screen[4][0]="Execute";
			screen[4][1]="T4";
			screen[4][3]=line;
			screen[5][0]="Execute";
			screen[5][1]="T5";
			screen[5][3]=line;
			
		}
		
		process_table.setModel(new DefaultTableModel(
				screen,
				new String[] {
					"Process Name", "Time", "Content", "Line"
				}
			));
		
	}
	public void FilleProcessTable_Ins_Base(String line){
		Object[][] screen=new Object[1][1];
		screen[0][0]=line;
		process_table.setModel(new DefaultTableModel(
				screen,
				new String[] {
					 "Line"
				}
			));
	}
	public void FilleStackMemoryScreen(){
		Object[][] stackm=new Object[16][4];
		
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 4; j++) {
				if(j==0){
					stackm[i][j]=i;
				}
				else if(j==1){
					if(test.processses.getStack_memory().getSlots()[i].getData()!=null){
						stackm[i][j]=test.processses.getStack_memory().getSlots()[i].getData();
					}
					
				}
				else if(j==2){
					if(test.processses.getStack_memory().getSlots()[i].getData()!=null){
						stackm[i][j]=Integer.toString(Integer.parseInt(test.processses.getStack_memory().getSlots()[i].getData(),2));
					}
					 
				}
				
				else if(j==3){
					if(test.processses.getStack_memory().getSlots()[i].getData()!=null){
						stackm[i][j]=Integer.toString(Integer.parseInt(test.processses.getStack_memory().getSlots()[i].getData(),2),16);;
					}
					
				}
				
				
					
				
			}
		}
		stackmemorytable.setModel(new DefaultTableModel(
				stackm,
				new String[] {
						"", "BIN", "HEX", "DEC"
				}
			));
	}
	public  void FilleInstMemoryScreen(){
		Object[][] instm=new Object[32][5];
		int k=0;
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 5; j++) {
				if(j==0){
					instm[i][j]=i;
				}
				else if(j==1){
					if(test.processses.getInstruction_memory().getSlots()[i].getData()!=null){
						instm[i][j]=test.processses.getInstruction_memory().getSlots()[i].getData();
					}
					
				}
				else if(j==2){
					if(test.processses.getInstruction_memory().getSlots()[i].getData()!=null){
						instm[i][j]=Integer.toString(Integer.parseInt(test.processses.getInstruction_memory().getSlots()[i].getData(),2));
					}
					 
				}
				
				else if(j==3){
					if(test.processses.getInstruction_memory().getSlots()[i].getData()!=null){
						instm[i][j]=Integer.toString(Integer.parseInt(test.processses.getInstruction_memory().getSlots()[i].getData(),2),16);;
					}
					
				}
				else if(j==4){
					if(test.processses.getInstruction_memory().getSlots()[i].getData()!=null){
					instm[i][j]=test.processses.getLines_for_screen()[i];
					k++;
						}
				}
				
					
				
			}
		}
		tableinstructionmemory.setModel(new DefaultTableModel(
				instm,
				new String[] {
						"", "BIN", "HEX", "DEC", "CODE"
				}
			));
		
	}
	public  void FilleDataMemoryScreen(){
		Object[][] datam=new Object[16][4];
		
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 4; j++) {
				if(j==0){
					datam[i][j]=i;
				}
				else if(j==1){
					if(test.processses.getData_memory().getSlots()[i].getData()!=null){
						datam[i][j]=test.processses.getData_memory().getSlots()[i].getData();;
					}
					else{
						datam[i][j]=null;	
					}
				}
				else if(j==2){
					if(test.processses.getData_memory().getSlots()[i].getData()!=null){
						int decimal = Integer.parseInt(test.processses.getData_memory().getSlots()[i].getData(),2);
						String hexStr = Integer.toString(decimal,16);
						
						datam[i][j]=hexStr;
					}
					else{
						datam[i][j]=null;	
					}		
						}
				else{
					if(test.processses.getData_memory().getSlots()[i].getData()!=null){
						int x=Integer.parseInt(test.processses.getData_memory().getSlots()[i].getData(), 2);
						datam[i][j]=Integer.toString(x);
					}
					else{
						datam[i][j]=null;	
					}
				}
			}
		}
		tabledatamemory.setModel(new DefaultTableModel(
				datam,
				new String[] {
					"", "BIN", "HEX", "DEC"
				}
			));
		
	
		
	}
	public void bin_to_hex(){
		
		
		adress_register_text.setText(convert("bh",adress_register_text.getText()));
		 program_counter_text.setText(convert("bh",program_counter_text.getText()));
		 inst_register_text.setText(convert("bh",inst_register_text.getText()));
		 stack_pointer_text.setText(convert("bh",stack_pointer_text.getText()));
		 input_register_text.setText(convert("bh",input_register_text.getText()));
		 output_register_text.setText(convert("bh",output_register_text.getText()));
		 register_s1_text.setText(convert("bh",register_s1_text.getText()));
		 register_s2_text.setText(convert("bh",register_s2_text.getText()));
		 register_d_text.setText(convert("bh",register_d_text.getText()));
		 Q_text.setText(convert("bh",Q_text.getText()));
		 opcode.setText(convert("bh",opcode.getText()));
		 overflow_text.setText(convert("bh",overflow_text.getText()));
		 register_0_textt.setText(convert("bh",register_0_textt.getText()));
		 register_1_textt.setText(convert("bh",register_1_textt.getText()));
		 register_2_textt.setText(convert("bh",register_1_textt.getText()));
	}
	public void hex_to_bin(){
		adress_register_text.setText(convert("hb",adress_register_text.getText()));
		 program_counter_text.setText(convert("hb",program_counter_text.getText()));
		 inst_register_text.setText(convert("hb",inst_register_text.getText()));
		 stack_pointer_text.setText(convert("hb",stack_pointer_text.getText()));
		 input_register_text.setText(convert("hb",input_register_text.getText()));
		 output_register_text.setText(convert("hb",output_register_text.getText()));
		 register_s1_text.setText(convert("hb",register_s1_text.getText()));
		 register_s2_text.setText(convert("hb",register_s2_text.getText()));
		 register_d_text.setText(convert("hb",register_d_text.getText()));
		 Q_text.setText(convert("hb",Q_text.getText()));
		 opcode.setText(convert("hb",opcode.getText()));
		 overflow_text.setText(convert("hb",overflow_text.getText()));
		 register_0_textt.setText(convert("hb",register_0_textt.getText()));
		 register_1_textt.setText(convert("hb",register_1_textt.getText()));
		 register_2_textt.setText(convert("hb",register_1_textt.getText()));
	}
	public void bin_to_dec(){
		adress_register_text.setText(convert("bd",adress_register_text.getText()));
		 program_counter_text.setText(convert("bd",program_counter_text.getText()));
		 inst_register_text.setText(convert("bd",inst_register_text.getText()));
		 stack_pointer_text.setText(convert("bd",stack_pointer_text.getText()));
		 input_register_text.setText(convert("bd",input_register_text.getText()));
		 output_register_text.setText(convert("bd",output_register_text.getText()));
		 register_s1_text.setText(convert("bd",register_s1_text.getText()));
		 register_s2_text.setText(convert("bd",register_s2_text.getText()));
		 register_d_text.setText(convert("bd",register_d_text.getText()));
		 Q_text.setText(convert("bd",Q_text.getText()));
		 opcode.setText(convert("bd",opcode.getText()));
		 overflow_text.setText(convert("bd",overflow_text.getText()));
		 register_0_textt.setText(convert("bd",register_0_textt.getText()));
		 register_1_textt.setText(convert("bd",register_1_textt.getText()));
		 register_2_textt.setText(convert("bd",register_1_textt.getText()));
		}
	public void dec_to_bin(){
		adress_register_text.setText(convert("db",adress_register_text.getText()));
		 program_counter_text.setText(convert("db",program_counter_text.getText()));
		 inst_register_text.setText(convert("db",inst_register_text.getText()));
		 stack_pointer_text.setText(convert("db",stack_pointer_text.getText()));
		 input_register_text.setText(convert("db",input_register_text.getText()));
		 output_register_text.setText(convert("db",output_register_text.getText()));
		 register_s1_text.setText(convert("db",register_s1_text.getText()));
		 register_s2_text.setText(convert("db",register_s2_text.getText()));
		 register_d_text.setText(convert("db",register_d_text.getText()));
		 Q_text.setText(convert("db",Q_text.getText()));
		 opcode.setText(convert("db",opcode.getText()));
		 overflow_text.setText(convert("db",overflow_text.getText()));
		 register_0_textt.setText(convert("db",register_0_textt.getText()));
		 register_1_textt.setText(convert("db",register_1_textt.getText()));
		 register_2_textt.setText(convert("db",register_1_textt.getText()));
	}
	public void hex_to_dec(){
		adress_register_text.setText(convert("hd",adress_register_text.getText()));
		 program_counter_text.setText(convert("hd",program_counter_text.getText()));
		 inst_register_text.setText(convert("hd",inst_register_text.getText()));
		 stack_pointer_text.setText(convert("hd",stack_pointer_text.getText()));
		 input_register_text.setText(convert("hd",input_register_text.getText()));
		 output_register_text.setText(convert("hd",output_register_text.getText()));
		 register_s1_text.setText(convert("hd",register_s1_text.getText()));
		 register_s2_text.setText(convert("hd",register_s2_text.getText()));
		 register_d_text.setText(convert("hd",register_d_text.getText()));
		 Q_text.setText(convert("hd",Q_text.getText()));
		 opcode.setText(convert("hd",opcode.getText()));
		 overflow_text.setText(convert("hd",overflow_text.getText()));
		 register_0_textt.setText(convert("hd",register_0_textt.getText()));
		 register_1_textt.setText(convert("hd",register_1_textt.getText()));
		 register_2_textt.setText(convert("hd",register_1_textt.getText()));
	}
	public void dec_to_hex(){
		adress_register_text.setText(convert("dh",adress_register_text.getText()));
		 program_counter_text.setText(convert("dh",program_counter_text.getText()));
		 inst_register_text.setText(convert("dh",inst_register_text.getText()));
		 stack_pointer_text.setText(convert("dh",stack_pointer_text.getText()));
		 input_register_text.setText(convert("dh",input_register_text.getText()));
		 output_register_text.setText(convert("dh",output_register_text.getText()));
		 register_s1_text.setText(convert("dh",register_s1_text.getText()));
		 register_s2_text.setText(convert("dh",register_s2_text.getText()));
		 register_d_text.setText(convert("dh",register_d_text.getText()));
		 Q_text.setText(convert("dh",Q_text.getText()));
		 opcode.setText(convert("dh",opcode.getText()));
		 overflow_text.setText(convert("dh",overflow_text.getText()));
		 register_0_textt.setText(convert("dh",register_0_textt.getText()));
		 register_1_textt.setText(convert("dh",register_1_textt.getText()));
		 register_2_textt.setText(convert("dh",register_1_textt.getText()));
	}
	public String convert(String ch,String data){
		if(ch.equals("bh")){
			if(data==null||data.equals("")){
				return "";
			}
			else{
				int decimal = Integer.parseInt(data,2);
				String hexStr = Integer.toString(decimal,16);
				return hexStr;
			}
		}
		else if(ch.equals("hb")){
			if(data==null||data.equals("")){
				return "";
			}
			else{
				int num = (Integer.parseInt(data, 16));
				String re=Integer.toBinaryString(num);
				return re;
			}
		}
		else if(ch.equals("bd")){
			if(data==null||data.equals("")){
				return "";
			}
			else{
				int decimal = Integer.parseInt(data,2);
				String hexStr = Integer.toString(decimal);
				return hexStr;
			}
		}
		else if(ch.equals("db")){
			if(data==null||data.equals("")){
				return "";
			}      
			else{
				int decimal = Integer.parseInt(data);
				String hexStr = Integer.toString(decimal,2);
				return hexStr;
			}
		}
		else if(ch.equals("hd")){
			if(data==null||data.equals("")){
				return "";
			}
			else{
				  String hexValue =data;
				  Integer result = Integer.parseInt(hexValue, 16);
				String r=Integer.toString(result);
				return r;
			}
		}
		else if(ch.equals("dh")){
			if(data==null||data.equals("")){
				return "";
			}
			else{
				int decimal = Integer.parseInt(data);
				String hexStr = Integer.toString(decimal,16);
				return hexStr;
			}
		}
		else{
			
			return "";
		}
		
	}
}
	
	 
