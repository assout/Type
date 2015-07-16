import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
public class Config extends JPanel{
	private static final long serialVersionUID = 1L;
	//ベース位置
	int baseX = 10;
	int baseY = 100;
	//横1、2
	int yokoX1 = 300;
	int yokoX2 = 550;
	int yokoX3 = 600;

	Key keyListener = new Key();

	//ボタン
	int buWid = 250;
	int buHei = 30;
	int buSize = 20;

	JRadioButton cha,tya;
	ButtonGroup chagyou = new ButtonGroup();

	JRadioButton sha,sya;
	ButtonGroup shagyou = new ButtonGroup();

	JRadioButton ja,jya;
	ButtonGroup jagyou = new ButtonGroup();

	JRadioButton si,shi,ci;
	ButtonGroup sigyou = new ButtonGroup();

	JRadioButton ji,zi;
	ButtonGroup jigyou = new ButtonGroup();

	JRadioButton ti,chi;
	ButtonGroup tigyou = new ButtonGroup();

	JRadioButton fu,hu;
	ButtonGroup fugyou = new ButtonGroup();
	
	JRadioButton n,nn;
	ButtonGroup ngyou = new ButtonGroup();

	Image imgConfig = new ImageIcon(getClass().getResource("img/config.gif")).getImage();
	public Config() {
		setBounds(0,0,800,600);

		cha = new JRadioButton("cha・chu・cye・cyo");
		tya = new JRadioButton("tya・tyu・tye・tyo");
		cha.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		tya.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		cha.setBounds(yokoX1,baseY,buWid,buHei);
		tya.setBounds(yokoX2,baseY,buWid,buHei);
		cha.setOpaque(false);
		tya.setOpaque(false);
		chagyou.add(cha);
		chagyou.add(tya);
		this.add(cha);
		this.add(tya);

		sha = new JRadioButton("sha・shu・she・sho");
		sya = new JRadioButton("sya・syu・sye・syo");
		sha.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		sya.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		sha.setBounds(yokoX1,baseY+50,buWid,buHei);
		sya.setBounds(yokoX2,baseY+50,buWid,buHei);
		sha.setOpaque(false);
		sya.setOpaque(false);
		shagyou.add(sha);
		shagyou.add(sya);
		this.add(sha);
		this.add(sya);

		ja = new JRadioButton("ja・ju・je・jo");
		jya = new JRadioButton("jya・jyu・jye・jyo");
		ja.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		jya.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		ja.setBounds(yokoX1,baseY+100,buWid,buHei);
		jya.setBounds(yokoX2,baseY+100,buWid,buHei);
		ja.setOpaque(false);
		jya.setOpaque(false);
		jagyou.add(ja);
		jagyou.add(jya);
		this.add(ja);
		this.add(jya);

		si = new JRadioButton("si");
		shi = new JRadioButton("shi");
		ci =  new JRadioButton("ci");
		si.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		shi.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		ci.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		si.setBounds(yokoX1,baseY+150,100,buHei);
		shi.setBounds(yokoX1+150,baseY+150,100,buHei);
		ci.setBounds(yokoX1+300,baseY+150,100,buHei);
		si.setOpaque(false);
		shi.setOpaque(false);
		ci.setOpaque(false);
		sigyou.add(si);
		sigyou.add(shi);
		sigyou.add(ci);
		this.add(si);
		this.add(shi);
		this.add(ci);

		ji = new JRadioButton("ji");
		zi = new JRadioButton("zi");
		ji.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		zi.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		ji.setBounds(yokoX1,baseY+200,buWid,buHei);
		zi.setBounds(yokoX2,baseY+200,buWid,buHei);
		ji.setOpaque(false);
		zi.setOpaque(false);
		jigyou.add(ji);
		jigyou.add(zi);
		this.add(ji);
		this.add(zi);

		ti = new JRadioButton("ti");
		chi = new JRadioButton("chi");
		ti.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		chi.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		ti.setBounds(yokoX1,baseY+250,buWid,buHei);
		chi.setBounds(yokoX2,baseY+250,buWid,buHei);
		ti.setOpaque(false);
		chi.setOpaque(false);
		tigyou.add(ti);
		tigyou.add(chi);
		this.add(ti);
		this.add(chi);

		fu = new JRadioButton("fu");
		hu = new JRadioButton("hu");
		fu.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		hu.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		fu.setBounds(yokoX1,baseY+300,buWid,buHei);
		hu.setBounds(yokoX2,baseY+300,buWid,buHei);
		fu.setOpaque(false);
		hu.setOpaque(false);
		fugyou.add(fu);
		fugyou.add(hu);
		this.add(fu);
		this.add(hu);
		
		n = new JRadioButton("n");
		nn = new JRadioButton("nn");
		n.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		nn.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, buSize));
		n.setBounds(yokoX1,baseY+350,buWid,buHei);
		nn.setBounds(yokoX2,baseY+350,buWid,buHei);
		n.setOpaque(false);
		nn.setOpaque(false);
		ngyou.add(n);
		ngyou.add(nn);
		this.add(n);
		this.add(nn);

		this.addKeyListener(keyListener);
		cha.addKeyListener(keyListener);
		tya.addKeyListener(keyListener);
		sha.addKeyListener(keyListener);
		sya.addKeyListener(keyListener);
		ja.addKeyListener(keyListener);
		jya.addKeyListener(keyListener);
		si.addKeyListener(keyListener);
		shi.addKeyListener(keyListener);
		ci.addKeyListener(keyListener);
		ji.addKeyListener(keyListener);
		zi.addKeyListener(keyListener);
		ti.addKeyListener(keyListener);
		chi.addKeyListener(keyListener);
		fu.addKeyListener(keyListener);
		hu.addKeyListener(keyListener);
		n.addKeyListener(keyListener);
		nn.addKeyListener(keyListener);
	}
	public void set() {
		if(MainPanel.inputType_CHACHUCHECHO){
			cha.setSelected(true);
		} else {
			tya.setSelected(true);
		}
		if(MainPanel.inputType_SHASHUSHESHO){
			sha.setSelected(true);
		} else {
			sya.setSelected(true);
		}
		if(MainPanel.inputType_JAJUJEJO){
			ja.setSelected(true);
		} else {
			jya.setSelected(true);
		}
		if(MainPanel.inputType_SI == 0){
			si.setSelected(true);
		} else if(MainPanel.inputType_SI == 1){
			shi.setSelected(true);
		} else if(MainPanel.inputType_SI == 2){
			ci.setSelected(true);
		}
		if(MainPanel.inputType_JI){
			ji.setSelected(true);
		} else{
			zi.setSelected(true);
		}
		if(MainPanel.inputType_TI){
			ti.setSelected(true);
		} else{
			chi.setSelected(true);
		}
		if(MainPanel.inputType_FU){
			fu.setSelected(true);
		} else{
			hu.setSelected(true);
		}
		if(MainPanel.inputType_N){
			n.setSelected(true);
		} else{
			nn.setSelected(true);
		}

	} 
	public void ready() {
		set();
		repaint();
	}
	public void remove() {
		MainPanel.keystate[KeyEvent.VK_SPACE] = false;
		repaint();
	}
	public void paintComponent(Graphics g) {
		//まずJPanel自体の描画処理を行う
		//super.paintComponent(g);
		//背景画像
		g.drawImage(imgConfig,0,0,null);
		//タイトル
		g.setFont(new Font("Tempus Sans ITC", Font.PLAIN,80));
		g.drawString("Config",10,70);
		//いけいけー
		g.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 25));
		g.drawString("ちゃ・ちゅ・ちぇ・ちょ",baseX,baseY+20);
		g.drawString("しゃ・しゅ・しぇ・しょ",baseX,baseY+70);
		g.drawString("じゃ・じゅ・じぇ・じょ",baseX,baseY+120);
		g.drawString("し　　　　　　　　　　",baseX,baseY+170);
		g.drawString("じ　　　　　　　　　　",baseX,baseY+220);
		g.drawString("ち　　　　　　　　　　",baseX,baseY+270);
		g.drawString("ふ　　　　　　　　　　",baseX,baseY+320);
		g.drawString("ん　　　　　　　　　　",baseX,baseY+370);
	}
	public class Key implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()<256) {
				MainPanel.keystate[e.getKeyCode()] = true;
			}
		}
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode()<256) {
				MainPanel.keystate[e.getKeyCode()] = false;
			}
			//enen = 999;
		}
		public void keyTyped(KeyEvent e) {
		}
	}
}
