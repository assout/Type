import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
public class Haikei {
	ImageIcon icon;
	Image imgDemo;
	Image imgOpening ;
	Image imgNormal;
    Image imgFlow;
    Image imgConfig;
    Image imgGameover;
    Image imgGamecrear;
    Image imgGamecrear_hard;
    Image imgGamecrear_easy;
    Image imgGamecrear_super;
	public Haikei() {
		icon = new ImageIcon(getClass().getResource("img/demo.gif"));
		imgDemo = icon.getImage();
		icon = new ImageIcon(getClass().getResource("img/normal.gif"));
		imgNormal = icon.getImage();
		icon = new ImageIcon(getClass().getResource("img/flow.gif"));
		imgFlow = icon.getImage();
		
		icon = new ImageIcon(getClass().getResource("img/gameover.gif"));
		imgGameover = icon.getImage();
		icon = new ImageIcon(getClass().getResource("img/gamecrear.gif"));
		imgGamecrear = icon.getImage();
		icon = new ImageIcon(getClass().getResource("img/gamecrear_hard.gif"));
		imgGamecrear_hard = icon.getImage();
		icon = new ImageIcon(getClass().getResource("img/gamecrear_easy.gif"));
		imgGamecrear_easy = icon.getImage();
		icon = new ImageIcon(getClass().getResource("img/gamecrear_super.gif"));
		imgGamecrear_super= icon.getImage();
		icon = new ImageIcon(getClass().getResource("img/config.gif"));
		imgConfig = icon.getImage();
	}
   	public void draw(Graphics g,int scene) {
   		switch (scene) {
   		case MainPanel.S_DEMO:
   			g.drawImage(imgDemo,0,0,null);
   			break;
   		case MainPanel.S_OPEN:
   			//背景画像を描画
   			g.drawImage(imgDemo,0,0,null);
   			break;
   		case MainPanel.S_NORMAL:
   			//背景画像を描画
   			g.drawImage(imgNormal,0,0,null);
   			break;
   		case MainPanel.S_FLOW:
   			if(MainPanel.phase == 5){
   				g.drawImage(imgGameover,0,0,null);
   			} else if(MainPanel.phase == 4){
   				if(MainPanel.levelNo == 2){
   					g.drawImage(imgGamecrear_hard,0,0,null);
   				} else if(MainPanel.levelNo == 1){
   					g.drawImage(imgGamecrear,0,0,null);
   				} else if(MainPanel.levelNo == 0){
   					g.drawImage(imgGamecrear_easy,0,0,null);
   				} else {
   					g.drawImage(imgGamecrear_super, 0,0,null);
   				}
   				
   			} else{
   				g.drawImage(imgFlow,0,0,null);
   			}
   			break;
   		default:
   		}
   	}
}