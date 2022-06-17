package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import db.DB;
import login.HintTextField;
import post.CreatePosting;
import post.Posting;
import profile.Interest;
import profile.Mypage;
import profile.Review;

public class MainFrame extends JFrame implements MouseListener, ActionListener{
	
	private Container c;
	private JTextField tf;
	private JButton serchbtn;
	private JToolBar toolBar;
	private Vector seoulvec;
	private JComboBox<String> combo;
	private JButton btnhome;
	private Vector<String> vecCombo;
	private Vector<String> vecarray;
	private JComboBox<String> comboarray;
	private JButton btnplus;
	private JButton btnreview;
	private JButton btnheart;
	private JList list;
	private DefaultListModel model;
	private CreatePosting cp;
	private Review review;
	private Interest gunsim;
	private JLabel lblprof;
	private Posting posting;
	private Mypage myP;
	private MainFrame mf;
	
	private String id;
//	int i;
	private String title;
	private String price;
	private DefaultTableModel tableModel;
	private JTable table;
	private TableModel data;
	private ArrayList<String> al;
	private String keyword;

	//	public String getId() {
//		return id;
//	}
//
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
		
	}
	
	//DB연결
	static String dbURL="jdbc:mysql://49.50.174.207/powerrainzo";
	static String dbID="blue";
	static String dbPassword="1234";
	
//	private static MainFrame m;

	public MainFrame(String keyword) {
		
		setTitle("메인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		//setLocation(500, 80);
		setSize(500,700);
		setLocationRelativeTo(this); 
		setLayout(new BorderLayout());
		
		if(keyword == null) {
			this.keyword = "";
		} else {
			this.keyword = keyword;
		}
		
		setNorth();
		setCenter();
		setSouth();
		
		setVisible(true);
		
	}
	

	private void setNorth() {
		JPanel upjp = new JPanel();
		upjp.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));	
		upjp.setPreferredSize(new Dimension(0, 80));
		upjp.setBackground(Color.WHITE);
		
		JPanel northjp = new JPanel();
		northjp.setLayout(new FlowLayout(FlowLayout.LEFT, 13 ,7));
		northjp.setBackground(Color.white);
		
		ImageIcon icondino = new ImageIcon("images/dinocolor.png");
		Image img = icondino.getImage();
		Image imgsize = img.getScaledInstance(60, 45, Image.SCALE_SMOOTH);
		ImageIcon imgprof = new ImageIcon(imgsize);
		JLabel lbldino = new JLabel(imgprof);
		
		JPanel northserchjp = new JPanel();
		northserchjp.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		tf = new HintTextField("검색어를 입력하세요.");
		tf.setPreferredSize(new Dimension(248, 28));
		serchbtn = new JButton("검  색");
		serchbtn.setFont(new Font("a소나무L", Font.PLAIN, 13));
		
		serchbtn.setPreferredSize(new Dimension(80, 27));
		serchbtn.setContentAreaFilled(false);
		serchbtn.setFocusPainted(false);
		serchbtn.addActionListener(this);
		
		northserchjp.add(tf);
		northserchjp.add(serchbtn);

		ImageIcon iconprofile = new ImageIcon("images/profile.png");
		Image img2 = iconprofile.getImage();
		Image imgsize2 = img2.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
		ImageIcon imgprof2 = new ImageIcon(imgsize2);
		lblprof = new JLabel(imgprof2);
		lblprof.addMouseListener(this);
		
		northjp.add(lbldino);
		northjp.add(northserchjp);
		northjp.add(lblprof);
		
		
		upjp.add(northjp);
		
		add(upjp, BorderLayout.NORTH);
		
		
	}

	
	
	
	private void setCenter() {
		JPanel centerjp = new JPanel();
		centerjp.setBackground(Color.white);
		
		String[] colName = { "사용자", "제목", "가격", "상태","사용기간", "내용"};
		tableModel = new DefaultTableModel(colName, 0);
		table = new JTable(tableModel); // TableModel를 이용
		
		//table스타일-셀너비/높이,정렬
		settablestyle();
		JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
				, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(470, 525));
		
		MainListDB dao = new MainListDB();
		ArrayList<MainListGAP> list = dao.getUserList(keyword);
		
		for(MainListGAP v: list) {
			String name = v.getName();
			String title = v.getTitle();
			String price = v.getPrice();
			String state = v.getState();
			String term = v.getTerm();
			String content = v.getContent();
			
			Object str[] = {name, title, price, state, term, content};
			
			tableModel.addRow(str);
		}
		table.addMouseListener(this);
		centerjp.add(sp);
		add(centerjp, BorderLayout.CENTER);
	}



	private void settablestyle() {
		//테이블 내용 가운데 정렬
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		
		TableColumnModel tcm = table.getColumnModel();
		
		for (int i = 1; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
		
		
		//열 너비 
		table.getColumn("사용자").setWidth(0);
		table.getColumn("사용자").setMinWidth(0);
		table.getColumn("사용자").setMaxWidth(0);
//		table.getColumn("사용자").setPreferredWidth(0);
		table.getColumn("제목").setPreferredWidth(120);
	    table.getColumn("가격").setPreferredWidth(20);
	    table.getColumn("상태").setPreferredWidth(20);
	    table.getColumn("사용기간").setPreferredWidth(40);
//	    table.getColumn("내용").setPreferredWidth(0);
	    table.getColumn("내용").setWidth(0);
		table.getColumn("내용").setMinWidth(0);
		table.getColumn("내용").setMaxWidth(0);
	    
	    //셀 높이
	    table.setRowHeight(30);
	    
	    //폰트
	    table.setFont(new Font("a소나무L", Font.PLAIN, 13));
	    table.getTableHeader().setFont(new Font("a소나무L", Font.BOLD, 11));
	    
	    //헤더 고정, 배경색
	    table.getTableHeader().setReorderingAllowed(false);
	    table.getTableHeader().setBackground(new Color(125, 230, 119));
	    
	}


	private void setSouth() {
		JPanel southjp = new JPanel();
		southjp.setBackground(Color.white);
		southjp.setLayout(new GridLayout(1,4));
		
		ImageIcon iconhome = new ImageIcon("images/house.png");
		btnhome = new JButton(iconhome);
		btnhome.setPreferredSize(new Dimension(35, 50));
		btnhome.addActionListener(this);
		
		ImageIcon iconplus = new ImageIcon("images/plus.png");
		btnplus = new JButton(iconplus);
		btnplus.setPreferredSize(new Dimension(35, 50));
		btnplus.addActionListener(this);
		
		ImageIcon iconreview = new ImageIcon("images/star.png");
		btnreview = new JButton(iconreview);
		btnreview.setPreferredSize(new Dimension(35, 50));
		btnreview.addActionListener(this);
		
		ImageIcon iconheart = new ImageIcon("images/heart.png");
		btnheart = new JButton(iconheart);
		btnheart.setPreferredSize(new Dimension(35, 50));
		btnheart.addActionListener(this);
		
		btnhome.setContentAreaFilled(false);
		btnplus.setContentAreaFilled(false);
		btnreview.setContentAreaFilled(false);
		btnheart.setContentAreaFilled(false);
		
		btnhome.setFocusPainted(false);
		btnplus.setFocusPainted(false);
		btnreview.setFocusPainted(false);
		btnheart.setFocusPainted(false);
		
		
		southjp.add(btnhome);
		southjp.add(btnplus);
		southjp.add(btnreview);
		southjp.add(btnheart);
		
		add(southjp, BorderLayout.SOUTH);
		
	}

	

	public static void main(String[] args) {

		DB.DBconnect(dbURL, dbID, dbPassword);
//		m = new MainFrame(); 
}


	@Override
	public void mouseClicked(MouseEvent e) {
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		Object obj = e.getSource();
		
		if(obj == lblprof) {
			myP = new Mypage("마이페이지", id);
			setVisible(false);
		}else if(e.getClickCount() == 2) {
			//해당 게시물 창이 수행되도록
			post();
			
			posting = new Posting ("게시물 보기", this, al, id);
			dispose();
		}
	}

	// 일단 해볼게요
	private void post() {
		int row = table.getSelectedRow();
		int column = table.getSelectedColumn();
		
		data = table.getModel();
		
		String user = (String)data.getValueAt(row, 0);
		String title = (String)data.getValueAt(row, 1);
		String price = (String)data.getValueAt(row, 2);
		String condition = (String)data.getValueAt(row, 3);
		String usedate = (String)data.getValueAt(row, 4);
		String content = (String)data.getValueAt(row, 5);
		
		al = new ArrayList<>();
		al.add(user);
		al.add(title);
		al.add(price);
		al.add(condition);
		al.add(usedate);
		al.add(content);
//		System.out.println(al);
		
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) { 
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		//게시물 작성
		if(obj == btnplus) {
			cp = new CreatePosting("게시물 작성", this, id);
			dispose();
		}else if(obj == btnreview) {
			review = new Review(this, id);
		}else if(obj == btnheart) {
			gunsim = new Interest(this, id);
			dispose();
		}
		else if(obj == serchbtn) {
			keyword = tf.getText();
			mf = new MainFrame(keyword);
			mf.setId(id);
			dispose();
		}
		else if(obj == btnhome) {
			keyword = "";
			mf = new MainFrame(keyword);
			mf.setId(id);
			dispose();
		}
	}


	public ArrayList<String> getAl() {
		return al;
	}

}
