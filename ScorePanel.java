import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;
public class ScorePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	//パネルの初期位置
	static final int X = 0;
	static final int Y = -200;
	//パネルの位置
	int x = X;
	int y = Y;
	//パネルの大きさ
	int width = 800;
	int height = 200;

	//各所持値//
	//現在スコア
	public int score = 0;
	//追加ポイント
	public String point;
	//現在の単語のスコア
	int currentScore = 0;
	//ファイル名
	String filename = "";
	//総タイプ数
	int type = 0;
	//ミス数
	int miss = 0;
	//コンボ
	int combo = 0;
	//敵総数
	int numEnemy = 0;
	//倒した敵の数
	int hit = 0;
	//ルビ
	String ruby = "";
	//ルビのインデックス
	int current = 0;
	
	//スコアの位置
	private int scoreX = 20;
	private int scoreY = 90;
	//各変数の基本位置　左上
	int baseX = 210;
	int baseY = 30;
	//ポイントの初期サイズ
	private static final int S_POINT = 30;
	//スコア、ポイントの現在サイズ
	private int scoreSize = 65;
	private int pointSize = S_POINT;
	private int etcSize = 35;
	//スコア、ポイントのカラー
	private Color scoreColor = Color.black;
	private Color pointColor = Color.blue;
	//スコア、ポイントのフォント
	private Font scoreFont = new Font("Broadway", Font.PLAIN, scoreSize);
	private Font pointFont = new Font("Broadway", Font.ITALIC, pointSize);
	private Font etcFont = new Font("Cooper Black", Font.PLAIN, etcSize);
	//タイマー
	private Timer timer;		//パネル移動用のタイマー
	private Timer addTimer;		//ポイント追加アニメ用のタイマー
	//timer使用中のフラグ
	boolean useTimer;
	boolean useAddTimer;
	//タイマー用のカウント
	private int count;
	//表示します？
	boolean visible = false;
	//背景画像
	ImageIcon icon = new ImageIcon(getClass().getResource("img/scorePanel.gif"));
	public ScorePanel(){
		//パネルの配置
		setBounds(x,y,width,height);	
	}
	public void init() {
		score = 0;
		point = "";
		type = 0;
		combo = 0;
		miss = 0;
		numEnemy = 0;
		hit = 0;
		ruby = "";
		current = 0;
		currentScore = 0;
		//tt.ruby = "";//これはだめですよ。変更しちゃってるからね　あとでおかしなるよ　自戒の意味をこめてコメントアウトしておくけども。
		scoreColor = Color.black;
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
				if (y >= 0){
					stopTimer();
					return;
				}
				y += 5;
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
				if (y <= -200){
					visible = false;
					stopTimer();
					return;
				}
				y -= 5;
				setBounds(x,y,width,height);
			}
		});
		timer.start();
	}
	public void result() {
		//もし前のタイマーが動いていたら止める
		if(useTimer){
			stopTimer();
		}
		useTimer = true;
		timer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//終わり判定	
				if (y >= 100){
					stopTimer();			
					return;
				}
				y += 5;
				setBounds(x,y,width,height);
			}
		});
		timer.start();
	}
	//timerを止める
	public void stopTimer(){
		//まずtimerを止める
		timer.stop();
		//timer使用終了でーす
		useTimer = false;
	}
	public void addPointAnime(int point) {
		//もし前のタイマーが動いていたら止める
		if(useAddTimer){
			stopAddTimer();
		}
		//使っている
		useAddTimer = true;
		//スコアに追加
		this.score += point;
		//表示用のString型変数
		this.point = Integer.toString(point);
		//追加ポイントの正負によって色を変更。プラスの場合符号を追加。
		if(point >= 0) {
			this.point = "+" +this.point;
			pointColor = Color.red;
		} else {
			pointColor = Color.blue;
		}
		//現在スコアによって色を変更する
		if(score >= 1000){
			scoreColor = Color.red;
		}else if(score >= 500) {
			scoreColor = Color.magenta;
		} else if(score >= 400) {
			scoreColor = Color.pink;
		} else if(score >= 300){
			scoreColor = Color.pink;
		} else if(score >= 200) {
			scoreColor = Color.orange;
		} else if(score >= 0) {
			scoreColor = Color.black;
		} else if(score >= -100) {
			scoreColor = Color.blue;
		} else if(score >= -200) {
			scoreColor = Color.blue;
		}
		//ポイント追加アニメーション
		addTimer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("addPointAnime is Running");
				//終わり判定	
				if (count >= 25){
					stopAddTimer();
				}
				//カウント
				count++;
				//サイズ
				pointSize++;
				pointFont = new Font("Broadway", Font.ITALIC, pointSize);
			}
		});
		addTimer.start();
	}
	//addTimerを止める
	public void stopAddTimer(){
		//まずtimerを止める
		addTimer.stop();
		//カウントをリセット
		count = 0;
		//サイズを戻しマース
		pointSize = S_POINT;
		pointFont = new Font("Broadway", Font.PLAIN, pointSize);
		//timer使用終了でーす
		useAddTimer = false;
	}
	public void paintComponent(Graphics g) {
		if(visible){
			//まずJPanel自体の描画処理を行う
			//super.paintComponent(g);
			//背景画像
			g.drawImage(icon.getImage(),0,0,null);
			//スコアを描画
			g.setFont(scoreFont);
			g.setColor(scoreColor);
			g.drawString(Integer.toString(score),scoreX,scoreY);
			//追加ポイントを描画
			if(useAddTimer){
				g.setFont(pointFont);
				g.setColor(pointColor);
				g.drawString(point,scoreX,scoreY+70);
			}
			
			g.setColor(Color.black);
			
			//ファイル名
			g.setFont(new Font("Default",Font.PLAIN,25));
			g.drawString(filename,baseX,baseY);
			
			g.setFont(etcFont);
			
			//カレントポイント
			g.drawString(Integer.toString(currentScore)+" point",baseX,baseY+50);
			//type
			g.drawString(Integer.toString(type)+" type",baseX+230,baseY+50);
			//ミスカウントを描画
			g.drawString(Integer.toString(miss)+" miss",baseX,baseY+100);
			//コンボカウントを描画
			g.drawString(Integer.toString(combo)+" combo",baseX+230,baseY+100);
			//単語ヒット数
			g.drawString(Integer.toString(hit)+"/"+Integer.toString(numEnemy),baseX+455,baseY+75);
			g.drawString(" hit",baseX+520,baseY+120);
			//現在対象を描画
			if(current < ruby.length()){
				g.drawString(ruby.substring(current),baseX,baseY+150);
			}
		}
	}
}
