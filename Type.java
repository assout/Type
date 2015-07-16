import java.awt.Container;
import javax.swing.JFrame;
public class Type extends JFrame{
	private static final long serialVersionUID = 1L;
	private MainPanel p;
	public Type(String s) {
		super(s);
		//MainPanelを生成しFrameに追加
		Container contentPane = getContentPane();
		p = new MainPanel();
		contentPane.add(p);
		// パネルサイズに合わせてフレームサイズを自動設定
        pack();
        
	}
	public static void main(String[] args) {
		//フォントがきったないのできれいにする。
		//System.setProperty("swing.plaf.metal.controlFont","Dialog-12");
		//フレームを作成
		Type frame = new Type("MIKAのTYPE");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //frame.setResizable(false);
		frame.setVisible(true);
	}
}