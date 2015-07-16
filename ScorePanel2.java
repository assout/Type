import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;
//スコアやミスカウントなど各ゲーム情報を表示するパネル
public class ScorePanel2 extends JPanel{
	private static final long serialVersionUID = 1L;
	//パネルの初期位置
	static final int X = 800;
	static final int Y = 0;
	//パネルの位置
	int x = X;
	int y = Y;
	//パネルの大きさ
	int width = 200;
	int height = 600;
	
	//各所持値//
	//現在スコア
	public int score = 0;
	//残敵数
	int numEnemy = 0;
	//レベル
	String level = "";
	//基地の体力
	int HP = 3;
	//スコアの初期位置
	private static final int SCORE_X = 50;
	private static final int SCORE_Y = 110;	
	//スコアの現在位置
	private int scoreX = SCORE_X;
	private int scoreY = SCORE_Y;
	//スコアの初期サイズ
	private static final int S_SCORE = 50;
	//スコアの現在サイズ
	private int scoreSize = S_SCORE;
	//スコアのフォント
	private Font scoreFont = new Font("Broadway", Font.PLAIN, scoreSize);
	//タイマー
	private Timer timer;
	//timer使用中のフラグ
	private boolean useTimer;
	//表示します？
	boolean visible = false;
	//背景画像
	ImageIcon icon = new ImageIcon(getClass().getResource("img/scorePanel2.gif"));
	public ScorePanel2(){
		//パネルの配置
		setBounds(x,y,width,height);
		setFont(scoreFont);
	}
	public void fadein() {
		//表示する
		visible = true;
		//もし前のタイマーが動いていたら止める
		if(useTimer){
			stopTimer();
		}
		//使っている
		useTimer = true;
		timer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("sp fadein");
				//終わり判定	
				if (x <= 600){
					stopTimer();
					return;
				}
				x -= 5;
				setBounds(x,y,width,height);
				repaint();
			}
		});
		timer.start();
	}
	public void fadeout() {
		//もし前のタイマーが動いていたら止める
		if(useTimer){
			stopTimer();
		}
		useTimer = true;
		timer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("sp fadeout");
				//終わり判定	
				if (x >= 800){
					visible = false;
					stopTimer();
					return;
				}
				x += 5;
				setBounds(x,y,width,height);
			}
		});
		timer.start();
	}
	public void addPoint(int point){
		this.score += point;
	}
	//timerを止める
	public void stopTimer(){
		//まずtimerを止める
		timer.stop();
		//timer使用終了でーす
		useTimer = false;
	}
	public void paintComponent(Graphics g) {
		if(visible){
			//まずJPanel自体の描画処理を行う
			//super.paintComponent(g);
			
			//背景画像
			g.drawImage(icon.getImage(),0,0,null);
			//スコアを描画
			g.drawString("SCORE", scoreX-30, scoreY - 40);
			g.drawString(Integer.toString(score),scoreX,scoreY);
			//残り敵数
			g.drawString("Enemy",20, 350);
			g.drawString(Integer.toString(numEnemy),50,400);
			//基地の体力
			g.drawString("HP", 20, 480);
			g.drawString(Integer.toString(HP), 50, 520);
		}
	}
}
