import java.awt.Graphics;
import java.awt.Font;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class TypeText{
	//表示文字
	String name;
	//読み方
	String ruby;
	//表示文字サイズ
	int size;
	//ルビサイズ
	int rubySize;
	//現在位置
	int x;
	int y;
	//ルビ位置
	int rubyX;
	int rubyY;
	//移動先
	int targetX;
	int targetY;
	//フォント一文字の横幅
	int wid;
	//移動速度
	int speed;
	//デフォルトスコア
	int maxScore;
	//点数
	int score;
	//減点速度
	int minusSpeed = 1;
	//スコアマイナスするときに使う
	int count = 0;
	/**
	 * 0:待機 1:移動中 2:ストップする(スコア減少も) 3:左端に詰まっている
	 * 4:終わりなので薄くする 5:表示のみ 9:終了)
	 */
	int state = 0;
	boolean visible = false;
	//フォントオブジェクトの作成
	private Font font;
	private Font rubyFont;
	//色を保持する変数color
	Color color = Color.black;
	Color rubyColor = Color.black;
	//ヒットエフェクト用のタイマー
	Timer timer;
	int ufufu = 0;
	boolean usedTimer = false;
	/**
	 * 作成するときに単語と読みとスコアだけ設定してstateは0ね
	 */	
	public TypeText(String name,String ruby) {
		this.name = name;
		this.ruby = ruby;
		maxScore = ruby.length() * 10;
		score = maxScore;
	}
	public void setSize(int size) {
		this.size = size;
		font = new Font("ＭＳ 明朝", Font.PLAIN, size);
	}
	public void setFont(){
		font = new Font("ＭＳ ゴシック", Font.PLAIN, size);
	}
	public void setRubySize(int rubySize) {
		this.rubySize = rubySize;
		rubyFont = new Font("ＭＳ ゴシック", Font.PLAIN, rubySize);
	}
	/**
	 * 一括でポジションを設定
	 */
	public void setPos(int x,int y,int rubyX,int rubyY){
		this.x = x;
		this.y = y;
		this.rubyX = rubyX;
		this.rubyY = rubyY;
	}
	public void start() {
		if(state == 0 || state == 5) {
			visible = true;
			state = 1;
		}
	}
	public void pause() {
		state = 2;
	}
	public void ikidomari() {
		state = 3;
	}
	public void usui() {
		state = 4;
	}
	public void print() {
		visible = true;
		state = 5;
	}
	public void end() {
		visible = false;
		state = 9;
	}
	/**
	 * ターゲットまで移動する
	 */
	public void move(){
		if (Math.abs(x - targetX) < speed) {
			x = targetX;
		} else if (x < targetX){
			x += speed;
		} else {
			x -= speed;
		} 
		if (Math.abs(y - targetY) < speed) {
			y = targetY;
		} else if (y < targetY){
			y += speed;
		} else {
			y -= speed;
		}
		if (x == targetX && y == targetY){
			ikidomari();
		}
	}
	/**
	 * 引数sをrubyと照合する。
	 */
	public boolean comparisonRuby(String s) {
		if (state != 0  && state != 9){
			if (s.equalsIgnoreCase(ruby)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * ノーマルモードでのヒット時アクション
	 * 薄くするだけ　ルビもよ
	 */
	public void hit_normal() {
		color = Color.gray;
		rubyColor = Color.gray;
		usui();
	}
	/**
	 * ゲームモードでのヒット時アクション
	 * 拡大しつつ色を消していく
	 */
	public void hit_flow() {	//正解したとき
//		もし前のタイマーが動いていたら止める
		if(usedTimer){
			stopTimer();
		}
		//使っている
		usedTimer = true;
		pause();				//まず動きを止めるわな
		timer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("hit_flow name="+name+color+ufufu);
				if (color.getRGB() == -1){
					end();
					stopTimer();
					return;
				}
				ufufu++;
				size += 4;
				font = new Font("ＭＳ ゴシック", Font.PLAIN, size);
				color = color.brighter();
			}
		});
		timer.start();
	}
	//timerをストップ
	public void stopTimer() {
		timer.stop();
		usedTimer = false;
	}
	//
	public void bakuhatu() {
//		もし前のタイマーが動いていたら止める
		if(usedTimer){
			stopTimer();
		}
		//使っている
		usedTimer = true;
		pause();				//まず動きを止めるわな
		timer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("bakuhatu name="+name+color+ufufu);
				if (ufufu >= 10){
					end();
					stopTimer();
					return;
				}
				size++;
				font = new Font("ＭＳ ゴシック", Font.PLAIN, size);
				ufufu++;
			}
		});
		timer.start();
	}
	//衝突　どっかーん
	public boolean collide(int i){
		if(y >= i) {
			return true;
		} else {
			return false;
		}	
	}
	//スコアを減らす
	public void scoreMinus() {
		if (score > 0) {
			count++;
			if(count >= 2){
				score -= minusSpeed;
				count = 0;
			}
		}
	}
	//描画系//
	public void draw(Graphics g) {
		if (visible){
			g.setColor(color);
			g.setFont(font);
			g.drawString(name,x,y);
		}
	}
	public void drawRuby(Graphics g){
		if(visible){
			g.setColor(rubyColor);
			g.setFont(rubyFont);
			g.drawString(ruby,rubyX,rubyY);
		}
	}
	public void drawLine(Graphics g,int charNo){
		if (state == 1) {
			//現在の単語
			g.setColor(Color.blue);
			g.drawLine(x,y+25,x+(charNo*wid),y+25);
			//現在位置
			g.setColor(Color.red);
			g.fill3DRect(x+(charNo*wid), y+25, wid, 5,true);
		} else if (state == 4) {
			g.setColor(Color.gray);
			g.drawLine(x, y+25, x+ruby.length()*wid, y+25);
		}
	}
}