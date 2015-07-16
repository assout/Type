import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;
public class SimpleTextLabel extends JLabel{
	private static final long serialVersionUID = 1L;
	private Timer t;
	private String s;
	public SimpleTextLabel() {
		super();
		setBounds(250,300,500,50);
		setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 38));
	}
	public void setS(String s) {
		this.s = s;
	}
	public void startTenmetu() {
		t = new Timer(500, new ActionListener() {
			  boolean flg = true;
			  public void actionPerformed(ActionEvent e) {
				System.out.println("tenmetuTimer"+s);
			    setText((flg)?s:"");
			    flg = !flg;
			  }
			});
		t.start();
	}
	public void stopTenmetu() {
		t.stop();
		s = "";
	}
}
