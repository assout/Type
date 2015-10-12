import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class MainPanel extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	//このパネルの大きさ
	public  static final int WIDTH = 800;
	public  static final int HEIGHT = 600;
	//サウンド//
	private AudioClip se_hit =	Applet.newAudioClip(getClass().getResource("se/hit.au"));
	private AudioClip se_miss =	Applet.newAudioClip(getClass().getResource("se/miss.au"));
	private AudioClip se_bakuhatu = Applet.newAudioClip(getClass().getResource("se/bakuhatu.au"));
	//フォント・オブジェクトの作成
	//private Font font1 = new Font("ＭＳ ゴシック", Font.PLAIN, 123);
	//キー関係//
	Key keyListener = new Key();
	NormalKeyListener normalKeyListener = new NormalKeyListener();
	static boolean[] keystate = new boolean[256];

	//シーン管理//
	public static final int S_OPEN = 0;		//オープニング
	public static final int S_DEMO = 1;		//デモ
	public static final int S_NORMAL = 3;		//ノーマルモード
	public static final int S_FLOW = 4;		//スクロールモード
	public static final int S_CONFIG = 6;		//コンフィグ画面

	int scene = 999;							//現在のシーン番号
	static int phase = -1;						//ゲーム場面の段階

	int enen = 999;
	//操作指示用
	Info info;
	Count count; 
	Rank rank;
	//テキストフィールド
	private JLabel kitiLabel = new JLabel(new ImageIcon(getClass().getResource("img/kiti.gif")));
	private JTextField inField;//入力用(シュートモード
	//各クラス
	private Haikei haikei;		//背景
	private ScorePanel sp;		//スコア
	private ScorePanel2 sp2;	//スコアパネル２
	private Config config = new Config();;				//コンフィグパネル
	private TypeText[] tt;		//タイプオブジェクト

	private final SimpleTextLabel kariLabel = new SimpleTextLabel();

	//
	String[] filename = {"presen.txt","easy.txt","java_word.txt","kanji_test.txt","english_test.txt","amenimomakezu.txt","nitijou.txt","ike.txt","a.txt","b.txt"};
	String[] filetitle = {"「プレゼン用」","「普通の単語」","「Java予約語」","「漢字テスト」","「英語テスト」","「雨ニモマケズ」","「日常会話」","「僕たちの冒険」","「男たちの大和」","「何したかったんだろう」"};
	int fileNo = 0; //ファイルナンバー
	boolean rubyVisible = true;

	String[] levelname = {"easy.txt","normal.txt","hard.txt","ike.txt","a.txt","b.txt"};
	String[] leveltitle = {"「簡単」","「普通」","「難しい」","「僕たちの冒険」","「男たちの大和」","「何したかったんだろう」"};
	static int levelNo = 0;//
	
	//入力方式ちゃちゅちょちぇ　　　　
	static boolean inputType_CHACHUCHECHO = true; //true=cha,true=cya
	static boolean inputType_SHASHUSHESHO = true;//true = sha false = sya;
	static boolean inputType_JAJUJEJO = true;//true = ja,ju,jo  false=jya,jyu,jyo;
	static int     inputType_SI = 0;//0=si,1=shi,2=ci;
	static boolean inputType_JI = true; //true=ji false=zi;
	static boolean inputType_TI = true; //true=ti false=chi
	static boolean inputType_FU = true; //true=fu false=hu
	static boolean inputType_N = true; //true:んはNひとつ
	String fileFolder = "n";
	//ゲーム変数
	int num;			//タイプするオブジェクトの数
	int hitCount;		//ヒット数
	int currentNo;		//現在の対象オブジェクト番号（ノーマルモードで使用)
	int charNo;		//何文字目かのINDEX（ノーマルモードで使用)
	
	int nokoriEnemy;
	//こういうのなんつーの　端
	public static final int RIGHTMOST = 800;
	public static final int LEFTMOST = 0;
	public static final int TOPMOST = 250;
	public static final int DOWNMOST = 600;

	private Thread thread;
	public MainPanel(){
		// このパネルの推奨サイズを設定。これをもとにpack()される。
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		//背景色の設定
		setBackground(Color.white);
		//前景色
		//setForeground(Color.white);
		// キーリスナーに登録
		this.addKeyListener(keyListener);
		// パネルがキーボードを受け付けるようにする（必須）
		this.setFocusable(true);
		//レイアウト
		this.setLayout(null);

		//各コンポーネントを追加//
		//this.setFont(font1);

		//インフォエリアを追加
		info = new Info();
		info.setOpaque(false);
		info.setFont(new Font("Tempus Sans ITC",Font.PLAIN, 45));
		info.setMargin(new Insets(10, 140, 10, 10));
		info.setFocusable(false);
		this.add(info);

//		//スコアパネルを追加
		sp = new ScorePanel();
		sp.setBackground(Color.white);
		sp.setFocusable(false);
		this.add(sp);

//		//スコアパネル２を追加
		sp2 = new ScorePanel2();
		sp2.setBackground(Color.white);
		sp2.setFocusable(false);
		this.add(sp2);

//		//カウントダウンパネル
		count = new Count();
		count.setBackground(Color.pink);
		count.setFont(new Font("Jokerman", Font.PLAIN, 250));
		this.add(count);

		//ランクパネル
		rank = new Rank();
		rank.setBackground(Color.pink);
		rank.setFont(new Font("Jokerman", Font.PLAIN, 250));
		//this.add(rank);

		//基地ラベル
		kitiLabel.setBounds(0,500,800,100);
		
		//インプットテキストフィールドを追加
		inField = new JTextField();
		inField.setBounds(90,550,350,40);
		inField.setFocusable(false);
		inField.setFont(new Font("ＭＳ ゴシック",Font.PLAIN,25));
		inField.addKeyListener(keyListener);
		
		this.add(inField);
		inField.setVisible(false);

		//背景クラス
		haikei = new Haikei();

		thread = new Thread(this);
		changeScene(S_DEMO);
		thread.start();
	}
	/**
	 * textファイルロード
	 */
	public void fileLoad(String fileName) {
		try {
			if (inputType_N){
				fileFolder = "n";
			} else{
				fileFolder = "nn";
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("files/" + fileFolder  + "/" + fileName)));
			//BufferedReader br = new BufferedReader(new FileReader("files/"+fileName));
			br.mark(1024);// 最初にマーク
			int i = 0;
			String name = "";
			String ruby = "";
			while (br.readLine() != null) {
				i++;
			}
			num = i / 2;
			tt = new TypeText[num];
			br.reset();
			i = 0;
			int a = 0;
			while (i < num) {
				if (a % 2 == 0) {
					name = br.readLine();
				} else if (a % 2 == 1){
					ruby = br.readLine();
					System.out.println("name="+name+" ruby="+ruby);
					tt[i] = new TypeText(name,ruby);
					i++;
				}
				a++;
			}
			br.close();

			if(fileName.equals("english_test.txt") || fileName.equals("kanji_test.txt")){
				rubyVisible = false;
			} else {
				rubyVisible = true;
			}
			if(fileName.equals("englixh_test.txt")){

			} else{
				inputTypeChenge();
			}
		} catch (IOException e) {
			System.out.println("fileload 例外"+e);
		}
	}
	/**
	 * typeTextオブジェクトの入力方式変更
	 */
	public void inputTypeChenge() {
		for(int i=0;i<num;i++){
			//ちゃ・ちゅ・ちぇ・ちょ
			if(inputType_CHACHUCHECHO){
				tt[i].ruby = tt[i].ruby.replaceAll("ttya", "ccha");
				tt[i].ruby = tt[i].ruby.replaceAll("ttyu", "cchu");
				tt[i].ruby = tt[i].ruby.replaceAll("ttye", "cche");
				tt[i].ruby = tt[i].ruby.replaceAll("ttyo", "ccho");

				tt[i].ruby = tt[i].ruby.replaceAll("tya", "cha");
				tt[i].ruby = tt[i].ruby.replaceAll("tyu", "chu");
				tt[i].ruby = tt[i].ruby.replaceAll("tye", "che");
				tt[i].ruby = tt[i].ruby.replaceAll("tyo", "cho");
			} else{
				tt[i].ruby = tt[i].ruby.replaceAll("ccha","ttya");
				tt[i].ruby = tt[i].ruby.replaceAll("cchu","ttyu");
				tt[i].ruby = tt[i].ruby.replaceAll("cche","ttye");
				tt[i].ruby = tt[i].ruby.replaceAll("ccho","ttyo");

				tt[i].ruby = tt[i].ruby.replaceAll("cha","tya");
				tt[i].ruby = tt[i].ruby.replaceAll("chu","tyu");
				tt[i].ruby = tt[i].ruby.replaceAll("che","tye");
				tt[i].ruby = tt[i].ruby.replaceAll("cho","tyo");
			}
			//しゃ・しゅ・しぇ・しょ
			if(inputType_SHASHUSHESHO){
				tt[i].ruby = tt[i].ruby.replaceAll("ssya","ssha");
				tt[i].ruby = tt[i].ruby.replaceAll("ssyu","sshu");
				tt[i].ruby = tt[i].ruby.replaceAll("ssye","sshe");
				tt[i].ruby = tt[i].ruby.replaceAll("ssyo","ssho");

				tt[i].ruby = tt[i].ruby.replaceAll("sya","sha");
				tt[i].ruby = tt[i].ruby.replaceAll("syu","shu");
				tt[i].ruby = tt[i].ruby.replaceAll("sye","she");
				tt[i].ruby = tt[i].ruby.replaceAll("syo","sho");	
			} else{
				tt[i].ruby = tt[i].ruby.replaceAll("ssha", "ssya");
				tt[i].ruby = tt[i].ruby.replaceAll("sshu", "ssyu");
				tt[i].ruby = tt[i].ruby.replaceAll("sshe", "ssye");
				tt[i].ruby = tt[i].ruby.replaceAll("ssho", "ssyo");

				tt[i].ruby = tt[i].ruby.replaceAll("sha", "sya");
				tt[i].ruby = tt[i].ruby.replaceAll("shu", "syu");
				tt[i].ruby = tt[i].ruby.replaceAll("she", "sye");
				tt[i].ruby = tt[i].ruby.replaceAll("sho", "syo");
			}
			//じゃ・じゅ・じぇ・じょ
			if(inputType_JAJUJEJO){
				tt[i].ruby = tt[i].ruby.replaceAll("jjya","jja");
				tt[i].ruby = tt[i].ruby.replaceAll("jjyu","jju");
				tt[i].ruby = tt[i].ruby.replaceAll("jjye","jje");
				tt[i].ruby = tt[i].ruby.replaceAll("jjyo","jjo");

				tt[i].ruby = tt[i].ruby.replaceAll("jya","ja");
				tt[i].ruby = tt[i].ruby.replaceAll("jyu","ju");
				tt[i].ruby = tt[i].ruby.replaceAll("jye","je");
				tt[i].ruby = tt[i].ruby.replaceAll("jyo","jo");
			} else {
				tt[i].ruby = tt[i].ruby.replaceAll("jja","jjya");
				tt[i].ruby = tt[i].ruby.replaceAll("jju","jjyu");
				tt[i].ruby = tt[i].ruby.replaceAll("jje","jjye");
				tt[i].ruby = tt[i].ruby.replaceAll("jjo","jjyo");

				tt[i].ruby = tt[i].ruby.replaceAll("ja","jya");
				tt[i].ruby = tt[i].ruby.replaceAll("ju","jyu");
				tt[i].ruby = tt[i].ruby.replaceAll("je","jye");
				tt[i].ruby = tt[i].ruby.replaceAll("jo","jyo");
			}
			//し
			if (inputType_SI == 0){
				tt[i].ruby = tt[i].ruby.replaceAll("sshi","ssi");
				tt[i].ruby = tt[i].ruby.replaceAll("cci","ssi");

				tt[i].ruby = tt[i].ruby.replaceAll("shi","si");
				tt[i].ruby = tt[i].ruby.replaceAll("ci","si");
			} else if(inputType_SI == 1){
				tt[i].ruby = tt[i].ruby.replaceAll("ssi","sshi");
				tt[i].ruby = tt[i].ruby.replaceAll("cci","sshi");

				tt[i].ruby = tt[i].ruby.replaceAll("si","shi");
				tt[i].ruby = tt[i].ruby.replaceAll("ci","shi");
			} else if(inputType_SI == 2){
				tt[i].ruby = tt[i].ruby.replaceAll("ssi","cci");
				tt[i].ruby = tt[i].ruby.replaceAll("sshi","cci");

				tt[i].ruby = tt[i].ruby.replaceAll("si","ci");
				tt[i].ruby = tt[i].ruby.replaceAll("shi","ci");
			}
			//じ
			if (inputType_JI){
				tt[i].ruby = tt[i].ruby.replaceAll("zzi","jji");

				tt[i].ruby = tt[i].ruby.replaceAll("zi","ji");
			} else{
				tt[i].ruby = tt[i].ruby.replaceAll("jji","zzi");

				tt[i].ruby = tt[i].ruby.replaceAll("ji","zi");
			}
			//ち
			if (inputType_TI){
				tt[i].ruby = tt[i].ruby.replaceAll("cchi","tti");

				tt[i].ruby = tt[i].ruby.replaceAll("chi","ti");
			} else{
				tt[i].ruby = tt[i].ruby.replaceAll("tti","cchi");

				tt[i].ruby = tt[i].ruby.replaceAll("ti","chi");
			}
			//ふ
			if (inputType_FU){
				tt[i].ruby = tt[i].ruby.replaceAll("hhu","ffu");

				tt[i].ruby = tt[i].ruby.replaceAll("hu","fu");
			} else{
				tt[i].ruby = tt[i].ruby.replaceAll("ffu","hhu");

				tt[i].ruby = tt[i].ruby.replaceAll("fu","hu");
			}
			//ん
//			if (inputType_N){
//				fileFolder = "n";
//			} else{
//				fileFolder = "nn";
//			}
		}
	}
	/**
	 * シャッフル
	 * 順番をランダムに並べ替える
	 */
	public void shuffle(TypeText[] tt) {
		for(int i=tt.length-1; i>0; i--){
			int t = (int)(Math.random() * i);  //0〜i-1の中から適当に選ぶ
			//選ばれた値と交換する
			TypeText tmp = tt[i];
			tt[i]  = tt[t];
			tt[t]  = tmp;
		}
	}
	/**
	 * メインループ
	 */
	public void run() {
		while (true) {
			switch (scene){
			case S_DEMO:		//デモシーン
				demo();
				break;
			case S_OPEN: 		//オープニングシーン
				opening();
				break;
			case S_NORMAL:		//ノーマルモードシーン
				normalMode();
				break;
			case S_FLOW:		//フローモードシーン
				flowMode();
				break;
			case S_CONFIG:		//設定画面
				config();
				break;
			default:
			}
			repaint();	//ここで再描画依頼
			//スリープ　ここでフレームレート調整よ
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("hahaha"+e);
			}
		}
	}
	/**
	 * デモシーン
	 * ソフト起動時のみ表示するよ
	 */
	public void demo(){
		if(phase == -1){
			kariLabel.setS("！PUSH ENTER KEY！");
			kariLabel.startTenmetu();
			this.add(kariLabel);
			phase = 0;
		} else if(phase == 0){				
			if(keystate[KeyEvent.VK_ENTER]){
				kariLabel.stopTenmetu();
				this.remove(kariLabel);
				changeScene(S_OPEN);
			}
		}
	}
	/**
	 * オープニングシーン
	 * セレクトメニューを表示
	 */
	public void opening() {
		if(phase == -1) {//初期化処理
			info.setText("Normal Mode\nGame Mode\nConfig");
			info.fadein();
			//rank.fadein();
			requestFocus();
			phase = 1;
		} else if(phase == 1){//
			if(enen == 0) {enen=999;
			info.remove();
			changeScene(S_NORMAL);
			} else if(enen == 1){enen=999;
			info.remove();
			changeScene(S_FLOW);
			} else if(enen == 2){enen=999;
			info.remove();
			changeScene(S_CONFIG);
			}
		}
	}
	/**
	 * TypeTextのノーマルモード用初期化
	 */
	public void ttInitNormal() { //TypeTextのノーマルモード用初期化
		int x = LEFTMOST;
		int y = TOPMOST;
		int length = 0;	//文字数
		int nameLe = 0;//ネームの方の文字数
		for (int i=0;i<num;i++) {
			if(rubyVisible==false){
				tt[i].rubyColor = Color.white;
			}
			tt[i].setSize(22);
			tt[i].setRubySize(22);
			tt[i].setPos(x,y,x,y+20);

			tt[i].wid = 11;
			length = tt[i].ruby.length();
			nameLe = tt[i].name.length();
			if(length >= nameLe*2){
				x += length * 11;	//文字サイズ分
			} else {
				x += nameLe * 22;
			}
			x += 35;			//文字間隔
			if (x + length * 12 >= RIGHTMOST-20){
				x = 0;
				y += 60;
			}
		}
	}
	/**
	 * ノーマルモード
	 * 普通のタイピングモード
	 */
	public void normalMode() {
		if(phase == -1){
			kariLabel.setBounds(300,150,600,50);
			kariLabel.setText(filetitle[fileNo]);
			this.add(kariLabel);
			info.setText(filename[fileNo]+"\nStart\nBack");
			info.ready();
			//ゲーム変数セット
			hitCount = 0;
			currentNo = 0;
			charNo = 0;
			phase = 0;
		} else if (phase == 0) {//スタートしていない時
			if(keystate[KeyEvent.VK_I]){
				fileNo = filename.length-3;
				info.setText(filename[fileNo]+"\nStart\nBack");
				kariLabel.setText(filetitle[fileNo]);
			} else if(keystate[KeyEvent.VK_K]){
				fileNo = filename.length-2;
				info.setText(filename[fileNo]+"\nStart\nBack");
				kariLabel.setText(filetitle[fileNo]);
			} else if(keystate[KeyEvent.VK_E]){
				fileNo = filename.length-1;
				info.setText(filename[fileNo]+"\nStart\nBack");
				kariLabel.setText(filetitle[fileNo]);
			}
			if(enen == 0){ enen=999;//file変更
				if (++fileNo >= filename.length-3) {
					fileNo=0;
				}
				info.setText(filename[fileNo]+"\nStart\nBack");
				kariLabel.setText(filetitle[fileNo]);
			} else if(enen == 1) {enen=999;//はじめる
				kariLabel.setText("");
				fileLoad(filename[fileNo]);
				if(filename[fileNo].equals("easy.txt") || filename[fileNo].equals("kanji_test.txt") || filename[fileNo].equals("english_test.txt") || filename[fileNo].equals("java_word.txt")){
					shuffle(tt);
					if(num >= 20){
						num = 20;
					}
				}
				ttInitNormal();
				info.fadeout();
				sp.init();
				sp.filename = filetitle[fileNo];
				sp.fadein();
				kariLabel.setBounds(250,300,500,50);
				kariLabel.setS("!PUSH SPACE KEY!");
				kariLabel.startTenmetu();
				phase = 1;
			} else if (enen == 2) {enen=999;//戻る
			kariLabel.setS("");
			this.remove(kariLabel);
			changeScene(S_OPEN);
			}
		} else if(phase == 1){
			if(keystate[KeyEvent.VK_SPACE]){
				kariLabel.stopTenmetu();
				this.remove(kariLabel);
				count.countdown();
				for(int i=0;i<num;i++) {
					//単語可視化
					tt[i].print();
					repaint();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				phase = 3;
			}
		} else if(phase == 3){//カウント終了を待ちスタートする
			if(count.countTimer.isRunning() == false) {
				//ノーマルモード用のキーリスナーを追加
				addKeyListener(normalKeyListener);
				//最初の単語スタート
				tt[0].color = Color.blue;
				if(rubyVisible == true){
					tt[0].rubyColor = Color.blue;
				}
				tt[0].start();

				if(rubyVisible){
					sp.ruby = tt[0].ruby;
				}
				sp.numEnemy = num;
				phase = 4;
			}
		} else if(phase == 4){//スタートしている時
			//スコアを減らしてく
			if(tt[currentNo].state == 1) {
				tt[currentNo].scoreMinus();
				sp.currentScore = tt[currentNo].score;
			}
		} else if(phase == 5){//終わり
			//ランク判定
			this.add(rank);
			rank.setRank(sp.score);
			rank.fadein();

			phase = 53;
		} else if(phase == 53) {
			if(keystate[KeyEvent.VK_ENTER]){
				rank.remove();
				this.remove(rank);

				info.setText("Retry\nOpening");
				info.ready();
				sp.fadeout();
				phase = 6;
			}
		} else if(phase == 6){
			if(enen == 0) {enen=999;
			changeScene(S_NORMAL);
			} else if(enen == 1){enen=999;
			info.remove();
			changeScene(S_OPEN);
			}
		}
	}
	/**
	 * ノーマルモードのときだけ有効にするキーリスナー
	 * ノーマルモードはキータイプによって進行するので、
	 * ループとイベントドリブンを併用する感じ
	 */
	class NormalKeyListener implements KeyListener{
		public void keyPressed(KeyEvent e){
			System.out.println("normal KeyListener");
			if (hitCount != num) {
				if (tt[currentNo].ruby.charAt(charNo) == e.getKeyChar() || keystate[KeyEvent.VK_Q]) {
					charNo++;
					sp.current++;
					sp.type++;
					sp.combo++;
				} else {//ミス
					sp.miss++;
					sp.combo = 0;
					se_miss.play();
					sp.addPointAnime(-10);
				}
				//ひとつの単語終了
				if (charNo == tt[currentNo].ruby.length()) {
					se_hit.play();
					hitCount++;
					sp.hit++;
					tt[currentNo].hit_normal();
					sp.addPointAnime(tt[currentNo].score);
					//最後の単語でなかったら次の単語のスタート
					if(hitCount != num){
						currentNo++;
						charNo = 0;
						sp.current=0;
						tt[currentNo].color = Color.blue;
						if(rubyVisible){
							tt[currentNo].rubyColor = Color.blue;
						}
						tt[currentNo].start();
						if(rubyVisible){
							sp.ruby = tt[currentNo].ruby;
						}
					}
				}
				//最後?
				if(hitCount != num){
				} else {//終わりへ
					removeKeyListener(normalKeyListener);
					phase = 5;
				}
			}
		}
		public void keyReleased(KeyEvent e) {
		}
		public void keyTyped(KeyEvent e) {
		}
	}
	/**
	 * TypeTextのゲームモード用初期化
	 */
	public void ttInitFlow() {
		int x;
		int y = 0;
		int targetY = 550;
		int speed;
		int length;	//文字数
		for(int i=0;i<num;i++){
			length = tt[i].name.length();
			length = length * 20;
			x = (int)(Math.random() * 550);
			if(x + length >= 580){
				x -= length;
			}
			speed = (int)(Math.random() * 5)+1;
			tt[i].setSize(25);
			tt[i].setFont();
			tt[i].x = x;
			tt[i].y = y;
			tt[i].targetX = x;
			tt[i].targetY = targetY;
			tt[i].speed = speed;
		}
	}
	/**
	 * ゲームモード
	 * 単語が上から降りてくるんで、守りきれ
	 */
	public void flowMode() {
		if(phase == -1){
			kariLabel.setBounds(300,150,600,50);
			kariLabel.setText(leveltitle[levelNo]);
			this.add(kariLabel);
			info.setText(levelname[levelNo]+"\nStart\nBack");
			info.ready();
			//ゲーム変数セットその1
			hitCount = 0;
			phase = 0;
		} else if (phase == 0) {//スタートしていない
			if(keystate[KeyEvent.VK_I]){
				levelNo = levelname.length-3;
				info.setText(levelname[levelNo]+"\nStart\nBack");
				kariLabel.setText(leveltitle[levelNo]);
			} else if(keystate[KeyEvent.VK_K]){
				levelNo = levelname.length-2;
				info.setText(levelname[levelNo]+"\nStart\nBack");
				kariLabel.setText(leveltitle[levelNo]);
			}  else if(keystate[KeyEvent.VK_E]){
				levelNo = levelname.length-1;
				info.setText(levelname[levelNo]+"\nStart\nBack");
				kariLabel.setText(leveltitle[levelNo]);
			}
			if (enen == 0){enen=999;//難易度変更
				if(++levelNo >= levelname.length-3){
					levelNo = 0;
				}
				info.setText(levelname[levelNo]+"\nStart\nBack");
				kariLabel.setText(leveltitle[levelNo]);
			} else if (enen == 1) {enen=999;//はじめる
				kariLabel.setText("");
				fileLoad(levelname[levelNo]);
				shuffle(tt);
				if(num >= 10){
					num = 10;
				}
				ttInitFlow();
				info.fadeout();
				//ゲーム変数セットその2
				nokoriEnemy = num;
				sp2.numEnemy = num;
				sp2.HP = 3;
				sp2.score = 0;
				//sp2.init();
				sp2.level = filetitle[fileNo];
				sp2.fadein();
				kariLabel.setBounds(250,300,500,50);
				kariLabel.setS("!PUSH SPACE KEY!");
				kariLabel.startTenmetu();
				//kiti
				this.add(kitiLabel);
				phase = 1;
			} else if(enen == 2){enen=999;//戻る
				kariLabel.setS("");
				this.remove(kariLabel);
				changeScene(S_OPEN);
			}
		} else if(phase == 1) {//スペースを押したらカウントが始まる
			if(keystate[KeyEvent.VK_SPACE]){
				kariLabel.stopTenmetu();
				this.remove(kariLabel);
				count.countdown();
				phase = 2;
			}
		} else if(phase == 2){//カウント終了を待ちスタートする
			if(count.countTimer.isRunning() == false) {
				inField.setVisible(true);
				inField.setFocusable(true);
				inField.requestFocus();
				inField.setText("");
				tt[0].start();

				phase = 3;
			}

		} else if(phase == 3){//スタートしている
			if (sp2.HP <= 0){ //ゲームオーバー判定
				inField.setCaretPosition(0);
				inField.setVisible(false);
				sp2.fadeout();
				this.remove(kitiLabel);
				phase = 5;//ゲームオーバーへ
			}
			if (nokoriEnemy == 0) { //クリア終わり判定
				inField.setCaretPosition(0);
				inField.setVisible(false);
				sp2.fadeout();
				this.remove(kitiLabel);
				phase = 4;//クリアへ
			}
			if(keystate[KeyEvent.VK_ENTER]){
				for(int i =0;i<num;i++){
		//			タイピング文字の照合　正解・不正解処理
					if (tt[i].comparisonRuby(inField.getText())) {
						nokoriEnemy--;
						sp2.numEnemy--;
						se_hit.play();
						
						tt[i].hit_flow();
						sp2.addPoint(tt[i].score);
						if(i < num-1) {
							tt[i+1].start();
						}
					}
				}
				inField.setText("");
				inField.setCaretPosition(0);
			}
			for (int i =0;i<num;i++) {
				//動かす
				if (tt[i].state == 1) {
					tt[i].move();
				}
				//当たり判定
				if (tt[i].state == 1 && tt[i].collide(500)) {
					tt[i].bakuhatu();
					se_bakuhatu.play();
					if(i < num-1){
						tt[i+1].start();
					}
					nokoriEnemy--;
					sp2.numEnemy--;
					sp2.HP--;
				}
				//次のオブジェクトのスタート
				if (i < num-1) {
					if(tt[i].state == 1) {
						switch (tt[i].speed) {
						case 1:
							if(tt[i].y >= 100) {
								tt[i+1].start();
							}
							break;
						case 2:
							if(tt[i].y >= 200) {
								tt[i+1].start();
							}
							break;
						case 3:
							if(tt[i].y >= 300) {
								tt[i+1].start();
							}
							break;
						case 4:
							if(tt[i].y >= 400) {
								tt[i+1].start();
							}
							break;
						case 5:
							if(tt[i].y >= 500) {
								tt[i+1].start();
							}
							break;
						default:
							break;
						}
					}
				}
			}
		} else if(phase == 4){//ゲームクリア　おめでとう
			if(keystate[KeyEvent.VK_SPACE]){
				phase = 6;
			}
		} else if(phase == 5){//ゲームオーバー　ざんねん
			if(keystate[KeyEvent.VK_SPACE]){
				phase = 6;
			}
		} else if(phase == 6){
			info.setText("Retry\nOpening");
			info.fadein();
			phase = 7;
		} else if(phase == 7){//チェンジシーン
			if(enen == 0) {enen=999;
			changeScene(S_FLOW);
			} else if(enen == 1){enen=999;
			info.remove();
			changeScene(S_OPEN);
			}
		}
	}
	/**
	 * コンフィグシーン
	 * コンフィグパネルを有効にするのさ
	 */
	public void config() {
		if (phase == -1){
			this.add(config);
			config.ready();
			phase = 1;
		} else if(phase == 1){
			if(keystate[KeyEvent.VK_SPACE]){
				se_hit.play();
				phase = 2;
			}
		} else if(phase == 2){
			if(config.cha.isSelected()) {
				inputType_CHACHUCHECHO = true;
			} else {
				inputType_CHACHUCHECHO = false;
			}
			if(config.sha.isSelected()){
				inputType_SHASHUSHESHO = true;
			} else {
				inputType_SHASHUSHESHO = false;
			}
			if(config.ja.isSelected()){
				inputType_JAJUJEJO = true;
			} else {
				inputType_JAJUJEJO = false;
			}
			if(config.si.isSelected()){
				inputType_SI = 0;
			} else if (config.shi.isSelected()){
				inputType_SI = 1;
			} else{
				inputType_SI = 2;
			}
			if(config.ji.isSelected()){
				inputType_JI = true;
			} else {
				inputType_JI = false;
			}
			if(config.ti.isSelected()){
				inputType_TI = true;
			} else {
				inputType_TI = false;
			}
			if(config.fu.isSelected()){
				inputType_FU = true;
			} else {
				inputType_FU = false;
			}
			if(config.n.isSelected()){
				inputType_N = true;
			} else {
				inputType_N = false;
			}
			config.remove();		//コンフィグパネルの後始末　必要
			this.remove(config);	//Configパネルをメインパネルからリムーブする
			info.ready();
			changeScene(S_OPEN);
		}
	}
	/**
	 * ゲームオーバーシーン
	 * ゲームオーバー処理
	 */
	public void gameover() {
		if (keystate[KeyEvent.VK_LEFT]) {//オープニングへ
			changeScene(S_OPEN);
		} 
	}
	/**
	 * シーン変更処理
	 * フェイズを初期化し、シーン変数を変更する。
	 */
	public void changeScene(int nextScene) {
		phase = -1;
		scene = nextScene;
	}
	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		//背景クラスがシーンごとの背景を描画
		haikei.draw(g,scene);
		//シーンごとの描画
		switch (scene){
		case S_NORMAL:
			if(phase >= 1){
				for(int i=0;i<num;i++) {
					tt[i].draw(g);
					tt[i].drawRuby(g);
					tt[i].drawLine(g,charNo);
				}
			}
			break;
		case S_FLOW:
			if(phase >= 1){
				for(int i=0;i<num;i++) {
					tt[i].draw(g);
				}
			}
			break;
		}
	}
	public class Key implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()<256) {
				keystate[e.getKeyCode()] = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_UP){
				info.select(0);
			} else if(e.getKeyCode() == KeyEvent.VK_DOWN){
				info.select(1);
			} else if(e.getKeyCode() == KeyEvent.VK_ENTER){
				enen = info.enter();
			} else if(e.getKeyCode() == KeyEvent.VK_DELETE){
				inField.setText("");
			}
		}
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode()<256) {
				keystate[e.getKeyCode()] = false;
			}
			//enen = 999;
		}
		public void keyTyped(KeyEvent e) {
		}
	}
}
