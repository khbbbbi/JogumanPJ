package profile;
// 수정

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import db.DB;
import main.MainFrame;
import post.Posting;

public class Interest extends JFrame implements ActionListener, MouseListener{

   private JPanel PanBase, PanMid, PanNor, PanCen;
   private DefaultTableModel Likemodel;
   private JList Likelist;
   private ImageIcon iconExit;
   private JButton btnExit, btnHome;
   private JLabel lblH, lbl2, lblprofile, profilelbl, reviewlbl;
   private JPanel PanTop,toolPan , p ,p1, p2;
   private JLabel lblN1, lblN2, lblN3, lblN4, lblN5, lblN6, lblN7, lblN8;
   private MainFrame mainFrame;
   private MainFrame mf;
   private JLabel lblN9;
   private Posting posting;
   
   private String title;
   private String price;
   private String name;
   private ResultSet rs;
   private JTable Liketable;
   private JScrollPane scrolledTable;
//   private String Myid;
   
   static String id;
   
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
   
   
   public class Model {
       String title;
       String price;
       String name;
       String state;
       String term;
       String content;

       public Model(String title, String price, String name, String state, String term, String content) {
           this.title = title;
           this.price = price;
           this.name = name;
           this.state = state;
          this.term = term;
          this.content = content;

       }


       public String getTitle() {
           return title;
       }

       public String getPrice() {
           return price;
       }
       public String getName() {
          return name;
       }
       public String getState() {
           return state;
       }

       public String getTerm() {
           return term;
       }
       public String getContent() {
          return content;
       }


       public void setTitle(String title) {
           this.title = title;
       }

       public void setPrice(String price) {
           this.price = price;
       }
       public void setName(String name) {
          this.name = name;
       }
       public void setState(String state) {
          this.title = state;
       }
       
       public void setTerm(String term) {
          this.price = term;
       }
       public void setContnet(String content) {
          this.name = content;
       }
   }
   
   //테이블 헤더 속성 이름
   String header[]= {"제목","가격(원)","상태","사용기간", "내용"};
   private String use_name;
   
   
   
   
   

   public Interest(MainFrame mainFrame, String id) {
      this.mainFrame = mainFrame;

      this.id = id;
      setResizable(false);
      setTitle("관심목록");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      setLocation(350, 5);
      setSize(500, 700);
      setLocationRelativeTo(this);
      setLayout(new BorderLayout());
      
      setBase();
      
      add(PanBase);
      setVisible(true);
   }
   
   private void setBase() {
      PanBase = new JPanel();
      PanBase.setBackground(Color.white);
      PanBase.setLayout(new BorderLayout());
      
      setNor();
      setCen();
      
      PanBase.add(PanNor,BorderLayout.NORTH);
      PanBase.add(PanCen,BorderLayout.CENTER);
   }
   private void setNor() {
      PanNor = new JPanel();
      PanNor.setBackground(Color.white);
      PanNor.setLayout(new BorderLayout());
      
      toolPan = new JPanel();
      toolPan.setLayout(new GridLayout(1,10));
      toolPan.setBackground(Color.white);
      toolPan.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
      
      iconExit = new ImageIcon("images/return.png");//뒤로가기 버튼 이미지 넣기
      Image img = iconExit.getImage();
      Image imgsize = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
      ImageIcon imgcha = new ImageIcon(imgsize);
      btnExit = new JButton(imgcha);
      getBtnExit().addActionListener(this);

       
       lblN1 = new JLabel();
       lblN2 = new JLabel();
       lblN3 = new JLabel();
       lblN4 = new JLabel();
       lblN5 = new JLabel();
       lblN6 = new JLabel();
       lblN7 = new JLabel();
       lblN8 = new JLabel();
       lblN9 = new JLabel();

       getBtnExit().setContentAreaFilled(false);
       getBtnExit().setFocusPainted(false);

       
       toolPan.add(getBtnExit());
      toolPan.add(lblN1);
      toolPan.add(lblN2);
      toolPan.add(lblN3);
      toolPan.add(lblN4);
      toolPan.add(lblN5);
      toolPan.add(lblN6);
      toolPan.add(lblN7);      
      toolPan.add(lblN8);
      toolPan.add(lblN9);

      PanNor.add(toolPan);
   }
   private void setCen() {
      //베이스 패널의 borderlayout 센터 패널
      PanCen = new JPanel();
      PanCen.setBackground(new Color(125,230,119));
      PanCen.setLayout(new BorderLayout());
      PanCen.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
      
      
      
      //센터패널 안에 있는 패널
      PanMid = new JPanel();
      PanMid.setLayout(new BorderLayout());
      PanMid.setBackground(Color.WHITE);
      PanMid.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      
      
      
      p1 = new JPanel();
      p1.setLayout(new FlowLayout(FlowLayout.LEFT));
      p1.setBackground(Color.white);
      p1.setBorder(BorderFactory.createEmptyBorder(40,60,0,10));//위,왼쪽,아래,오른

      
   
      
      
      
      //프로필 버튼 이미지 넣기
      ImageIcon iconProfile = new ImageIcon("images/user.png");
      Image img = iconProfile.getImage();
      Image imgsize = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
      ImageIcon imgcha = new ImageIcon(imgsize);
      lblprofile = new JLabel(imgcha);
      
      
      
      
        //사용자 이름 설정
         
         String username = "SELECT user_name FROM User where user_id ='" + id + "';" ;
         ResultSet rs = DB.DBselect(username);
               
         
         try {
            if(rs.next()) {   //sql문에 내용이 있을 때
               use_name = rs.getString("user_name");
               
               System.out.println(use_name);
            } else { //내용이 없으면 false
               System.out.println(id);
               System.out.println("오류");
            }
         } catch (SQLException e1) {
            e1.printStackTrace();
         }
        
       profilelbl = new JLabel(use_name);
       profilelbl.setFont(new Font("a소나무L",Font.BOLD, 25));
      
   
       lbl2 = new JLabel("   님의 관심목록");
       lbl2.setFont(new Font("a소나무L",Font.PLAIN, 20));
      //테이블 
      SetLikeTable();
      

      
      p = new JPanel();
      p.setBackground(Color.WHITE);
      p.setBorder(BorderFactory.createEmptyBorder(30,20,40,20)); //위,왼쪽,아래,오른
      
      
      p1.add(lblprofile);
      p1.add(profilelbl);
      
      p1.add(lbl2);
      PanMid.add(p1);
      p.add(scrolledTable);
      PanCen.add(PanMid, BorderLayout.NORTH);
      PanCen.add(p, BorderLayout.CENTER);
   }

   //테이블에 사용자 관심목록 가져오
    private void SetLikeTable() {
       
       boolean check = false;
       this.id = id;
       
       
      Likemodel = new DefaultTableModel(header,0);///header ?
      
      Liketable = new JTable(Likemodel);
      
      Liketable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      
      scrolledTable = new JScrollPane(Liketable,scrolledTable.VERTICAL_SCROLLBAR_ALWAYS, scrolledTable.HORIZONTAL_SCROLLBAR_AS_NEEDED );
      this.add("Center",scrolledTable);   
      scrolledTable.setPreferredSize(new Dimension(390,390));
      
      
      
      Liketable.setFillsViewportHeight(true); //스크롤팬에 꽉 차서 보이게 하기
      Liketable.setBackground(Color.WHITE);
      Liketable.addMouseListener(this);
      
      
      //제목","가격(원)","판매자"의 크기 부여
      //"제목","가격(원)","상태","사용기간", "내용"
      Liketable.getColumn("제목").setPreferredWidth(100);
      Liketable.getColumn("가격(원)").setPreferredWidth(30);
      Liketable.getColumn("상태").setPreferredWidth(30);
      Liketable.getColumn("사용기간").setPreferredWidth(40);
      Liketable.getColumn("내용").setPreferredWidth(40);
      //
      //Liketable.getColumn("판매자").setPreferredWidth(40);
      
      
      //테이블 열 크기 주기
      Liketable.setRowHeight(25);
      
      
      //테이블 헤더 고정, 배경색
      Liketable.getTableHeader().setReorderingAllowed(false);
      Liketable.getTableHeader().setBackground(new Color(125, 230, 119));
      
      
      //폰트
      Liketable.setFont(new Font("a소나무L", Font.PLAIN, 13));
      Liketable.getTableHeader().setFont(new Font("a소나무L", Font.BOLD, 11));
        
      
      //테이블 열 가운데 정렬.
      DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
       dtcr.setHorizontalAlignment(SwingConstants.CENTER);
         
       TableColumnModel tcm = Liketable.getColumnModel();
         
       for (int i = 1; i < tcm.getColumnCount(); i++) {
            tcm.getColumn(i).setCellRenderer(dtcr);
         }
        
       
       //나의 관심목록 출력
      //
       String sql = "SELECT * FROM Interest"
             + " where love_id = '" + id +  "';" ;

       ResultSet rs = DB.DBselect(sql);
      
      try {
         int row =1;
         while(rs.next()) {
            Vector record = new Vector<>();
            //, "제목", "가격", "상태","사용기간", "내용"
            //record.add(rs.getString("user_name"));
            record.add(rs.getString("post_title"));
            record.add(rs.getString("post_price"));
            record.add(rs.getString("post_state"));
            record.add(rs.getString("post_term"));
            record.add(rs.getString("post_memo"));
            
            
            Likemodel.addRow(record);

         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
    


   public static void main(String[] args) {
      
      DB.DBconnect(dbURL, dbID, dbPassword);
      
      Interest it = new Interest(null, id);
      System.out.println(id);
   }
   @Override
   public void actionPerformed(ActionEvent e) {
      Object obj =  e.getSource();
      if(obj == getBtnExit()) {//뒤로가기 버튼 누르면...
         mf = new MainFrame(null);
         mf.setId(id);
         setVisible(false);
      }
   }

   @Override
   public void mouseClicked(MouseEvent e) {
      Object obj = e.getSource();
      if(e.getClickCount() == 1) {
         
//      posting = new Posting ("게시물 보기", this, al, id);
//      setVisible(false);
      }
//      if(obj == Liketable) {
//         int row=Liketable.getSelectedRow();   
//         int col=Liketable.getSelectedColumn();
//         printCell(row,col);
//         title.setText(Liketable.getValueAt(row, 0));
//         price.setText((String)Liketable.getValueAt(row, 1));
//         name.setText((String)Liketable.getValueAt(row, 2));   
         

      
//      if(obj == lblprof) {
//         myP = new Mypage("마이페이지", id);
//         setVisible(false);
//      }else if(e.getClickCount() == 2) {
//         //해당 게시물 창이 수행되도록
//         post();
//         
//         posting = new Posting ("게시물 보기", this, al, id);
//         dispose();
//      }
//      }
      
      
      // 일단 해볼게요
//      private void post() {
//         int row = table.getSelectedRow();
//         int column = table.getSelectedColumn();
//         
//         data = table.getModel();
//         
//         String user = (String)data.getValueAt(row, 0);
//         String title = (String)data.getValueAt(row, 1);
//         String price = (String)data.getValueAt(row, 2);
//         String condition = (String)data.getValueAt(row, 3);
//         String usedate = (String)data.getValueAt(row, 4);
//         String content = (String)data.getValueAt(row, 5);
//         
//         al = new ArrayList<>();
//         al.add(user);
//         al.add(title);
//         al.add(price);
//         al.add(condition);
//         al.add(usedate);
//         al.add(content);
////         System.out.println(al);
//         
//         
//         
//         
//      }
      //제목, 가격, 판매자
      
      
//      public void mouseClicked(MouseEvent e) {
//           //선택한 셀의 행 번호계산 
//           int row = Liketable.getSelectedRow();
//           
//           //테이블의 모델객체 얻어오기
//           TableModel data = Liketable.getModel();
//           
//           //선택한 테이블의 row의 모든 값을 이용하여 MemberDTO객체 생성하기
//           String title = (String)data.getValueAt(row,0);
//           String price = (String)data.getValueAt(row,1);
//           String name = (String)data.getValueAt(row,2);
//           
//           
//           MemberDTO selectUser =
//             new MemberDTO(id, pass, name, addr,100, memo,"001");
//           
//           //새 창을 띄우면서 dto객체를 넘기기
//           DataInfo frame = new DataInfo(selectUser);
//           
//          }​
      
      
      
      
   }

   @Override
   public void mousePressed(MouseEvent e) {
      
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

   public JButton getBtnExit() {
      return btnExit;
   }

}