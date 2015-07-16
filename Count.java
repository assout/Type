import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
public class Count extends JPanel{
	private static final long serialVersionUID = 1L;
	//カウントダウン用タイマー
	Timer countTimer;
	//タイマー使用中フラグ
	boolean usedCountTimer;
//	countdown用
	int count;
	//カウント音
	private AudioClip se_count = Applet.newAudioClip(getClass().getResource("se/count.au"));
	public Count() {
		setBounds(220,200,300,300);
	}
	public void countdown() {
		se_count.play();
		count = 3;
		//もし前のタイマーが動いていたら止める
		if(usedCountTimer){
			stopCountTimer();
		}
		usedCountTimer = true;
		countTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("countdown is Running");
				//終わり判定	
				if (count <= 0){
					stopCountTimer();
					return;
				}
				se_count.play();
				count--;
			}
		});
		countTimer.start();
	}
	public void stopCountTimer() {
		//止めまーーす
		countTimer.stop();
		//timer使用終了でーす
		usedCountTimer = false;
	}
	public void paintComponent(Graphics g) {
		if(usedCountTimer){
			//まずJPanel自体の描画処理を行う
			super.paintComponent(g);
			//カウント
			g.drawString(Integer.toString(count),70,240);
		}
	}
}
