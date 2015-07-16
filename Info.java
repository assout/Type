import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Info extends JTextArea{
	private static final long serialVersionUID = 1L;
	//パネルの初期位置
	int X = 800;
	int Y = 200;
	//パネルの位置
	int x = X;
	int y = Y;
	//パネルの大きさ
	int width = 700;
	int height = 200;
	//全部の色
	Color color = Color.black;
	//人画像の大きさ
	static final int MAN_WID = 100;
	static final int MAN_HEI = 200;
	//指画像の大きさ
	static final int YUBI_OOKISA = 30;
	//指画像の位置
	int imgX = 100;
	int imgY = 0;
	
	//選択項目の位置
	final int POS0 = 25;
	final int POS1 = 85;
	final int POS2 = 145;
	final int POS3 = 205;
	
	//アニメーション用タイマー
	Timer timer;
	//timer使用中フラグ
	boolean used;
	//パネルを表示するか
	boolean visible = true;
	//項目数
	int num;
	//現在の項目番号
	int current;
	//画像の向き　0：左　1：右 : 2:くるとき
	int direction=2;
	//画像の歩行状態
	int count;
	ImageIcon icon = new ImageIcon(getClass().getResource("img/man.gif"));;
	ImageIcon icon2 = new ImageIcon(getClass().getResource("img/yubi.gif"));
	private AudioClip se_cursor = Applet.newAudioClip(getClass().getResource("se/hit.au"));
	public Info() {
		imgY = POS0;
		setBounds(x,y,width,height);
	}
	public void setText(String s){
		super.setText(s);
		num = super.getLineCount();
	}
	public int getDirection() {
		return direction;
	}
	public void init(){
		x = X;
		y = Y;
		color = Color.black;
	}
	public void ready() {
		num = super.getLineCount();
		current = 0;
		direction = 1;
		imgY = POS0;
		x = 200;
		setBounds(x,y,width,height);
		visible = true;
		repaint();
	}
	public void remove() {
		visible = false;
		direction = 2;
		x = X;
		setBounds(x,y,width,height);
		repaint();
	}
	public void fadein() {
//		表示する
		visible = true;
		//もし前のタイマーが動いていたら止める
		if(used){
			stopTimer();
		}
		//使っている
		used = true;
		timer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("info fadein");
				//終わり判定	
				if (x <= 200){
					direction = 1;
					current = 0;
					imgY = POS0;
					stopTimer();
					return;
				}
				x -= 13;
				setBounds(x,y,width,height);
				repaint();
			}
		});
		timer.start();
	}
	public void fadeout() {
		//もし前のタイマーが動いていたら止める
		if(used){
			stopTimer();
		}
		setText("");
		direction = 0;
		used = true;
		timer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("info fadeout is Running");
				//終わり判定	
				if (x+width <= 0){
					visible = false;
					direction = 2;
					x = X;
					setBounds(x,y,width,height);
					stopTimer();
					return;
				}
				x -= 10;
				setBounds(x,y,width,height);
			}
		});
		timer.start();
	}
	public void select(int i) {
		if(direction == 1){
			if(i == 0){//up
				if (current == 0){
					
				} else if (current == 1){
					imgY = POS0;
					current = 0;
				} else if(current == 2){
					imgY = POS1;
					current = 1;
				} else if(current == 3){
					imgY = POS2;
					current = 2;
				}
			} else if(i == 1){//DOWN
				if (current == num-1){
					
				} else if (current == 0){
					imgY = POS1;
					current = 1;
				} else if(current == 1){
					imgY = POS2;
					current = 2;
				} else if(current == 2){
					imgY = POS3;
					current = 3;
				}
			}
		}
	}
	public int enter(){
		if(direction == 1){
			se_cursor.play();
			return current;
		} else{
			return 999;
		}
	}
	public void stopTimer() {
		//止めまーーす
		timer.stop();
		//timer使用終了でーす
		used = false;
	}
	public void paintComponent(Graphics g) {
		if(visible){
			//まずJTextArea自体の描画処理を行う
			super.paintComponent(g);
			//人画像を表示
			g.drawImage(icon.getImage(),0,0,0+MAN_WID,0+MAN_HEI,
			              count * MAN_WID, direction * MAN_HEI, MAN_WID + count * MAN_WID, direction * MAN_HEI + MAN_HEI, this);
			//指画像を表示
			g.drawImage(icon2.getImage(),imgX,imgY,imgX+YUBI_OOKISA,imgY+YUBI_OOKISA,
						0, direction * YUBI_OOKISA, YUBI_OOKISA, direction * YUBI_OOKISA + YUBI_OOKISA, this);
		}
		
	}
}
