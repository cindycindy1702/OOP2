import javax.swing.*;
import java.awt.*;

public class MyHouse extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // 1. 天空與草地
        g2.setColor(new Color(135, 206, 235));      // 天藍色
        g2.fillRect(0, 0, 500, 305);
        g2.setColor(new Color(60, 170, 95));        // 綠色
        g2.fillRect(0, 305, 500, 95);

        // 2. 太陽：黃色正圓 + 12 條光芒
        int cx = 400, cy = 70;
        g2.setColor(Color.YELLOW);
        g2.fillOval(cx - 30, cy - 30, 60, 60);
        g2.setColor(Color.ORANGE);
        for (int i = 0; i < 12; i++) {
            double a = 2 * Math.PI * i / 12;        // 弧度
            int x1 = (int) (cx + 40 * Math.cos(a));
            int y1 = (int) (cy + 40 * Math.sin(a));
            int x2 = (int) (cx + 60 * Math.cos(a));
            int y2 = (int) (cy + 60 * Math.sin(a));
            g2.drawLine(x1, y1, x2, y2);
        }

        // 3. 雲朵：3 個互相重疊的白色橢圓
        g2.setColor(Color.WHITE);
        g2.fillOval(60, 50, 80, 40);
        g2.fillOval(100, 35, 80, 50);
        g2.fillOval(140, 50, 80, 40);

        // 4. 房屋主體：先填色，再畫黑框
        g2.setColor(new Color(255, 235, 180));
        g2.fillRect(150, 210, 200, 130);
        g2.setColor(Color.BLACK);
        g2.drawRect(150, 210, 200, 130);

        // 5. 屋頂：只用 drawLine 畫三角形
        g2.setColor(Color.BLACK);
        g2.drawLine(130, 210, 250, 140);            // 左斜邊
        g2.drawLine(250, 140, 370, 210);            // 右斜邊
        g2.drawLine(130, 210, 370, 210);            // 底邊

        // 6. 門與門把
        g2.setColor(new Color(139, 69, 19));        // 棕色
        g2.fillRoundRect(225, 270, 50, 70, 15, 15);
        g2.setColor(Color.YELLOW);
        g2.fillOval(262, 303, 8, 8);                // 門把

        // 7. 窗戶：兩扇，填色 + 外框 + 十字窗框
        int[] winX = {170, 290};
        int winY = 235, w = 40, h = 40;
        for (int x : winX) {
            g2.setColor(new Color(173, 216, 230));
            g2.fillRect(x, winY, w, h);
            g2.setColor(Color.BLACK);
            g2.drawRect(x, winY, w, h);
            g2.drawLine(x, winY + h / 2, x + w, winY + h / 2);   // 橫線
            g2.drawLine(x + w / 2, winY, x + w / 2, winY + h);   // 直線
        }

        // 8. 簽名
        g2.setColor(Color.BLACK);
        g2.drawString("A1143301 . 歐芯妤", 10, 390);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("我的小房子");
        MyHouse p = new MyHouse();
        p.setPreferredSize(new Dimension(500, 400));
        f.add(p);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
