import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
public class Rank extends JPanel{
	private static final long serialVersionUID = 1L;
	//パネルの初期位置
	int X = 220;
	int Y = 600;
	//パネルの位置
	int x = X;
	int y = Y;
	//パネルの停止高さ
	int targetY = 200;
	//パネルの大きさ
	int width = 300;
	int height = 300;
	//移動用タイマー
	Timer moveTimer;
	//移動タイマー使用中フラグ
	boolean usedMoveTimer;
	//ランク文字を表示するか？
	boolean rankVisible = false;
	//ランク
	String rank = "";
	//ランク音
	private AudioClip se;
	public Rank() {
		setBounds(x,y,width,height);
	}
	public void setRank(int score){
		if(score >= 1000){
			rank = "S";
			se = Applet.newAudioClip(getClass().getResource("se/a.au"));
		} else if(score >= 800) {
			rank = "A";
			se = Applet.newAudioClip(getClass().getResource("se/a.au"));
		} else if(score >= 600) {
			rank = "B";
			se = Applet.newAudioClip(getClass().getResource("se/b.au"));
		} else if(score >= 400) {
			rank = "C";
			se = Applet.newAudioClip(getClass().getResource("se/hit.au"));
		} else if(score >= 200){
			rank = "D";
			se = Applet.newAudioClip(getClass().getResource("se/miss.au"));
		} else {
			rank = "E";
			se = Applet.newAudioClip(getClass().getResource("se/miss.au"));
		}
	}
	public void fadein() {
		//もし前のタイマーが動いていたら止める
		if(usedMoveTimer){
			stopMoveTimer();
		}
		//使っている
		usedMoveTimer = true;
		moveTimer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("rankFadein");
				//終わり判定	
				if (y <= targetY){
					se.play();
					rankVisible = true;
					stopMoveTimer();
					return;
				}
				y -= 15;
				setBounds(x,y,width,height);
				repaint();
			}
		});
		moveTimer.start();
	}
	public void stopMoveTimer() {
		//止めまーーす
		moveTimer.stop();
		//timer使用終了でーす
		usedMoveTimer = false;
	}
	public void remove() {
		rankVisible = false;
		y = Y;
		setBounds(x,y,width,height);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(rankVisible){
			g.drawString(rank, 60, 240);
		}
	}
}
