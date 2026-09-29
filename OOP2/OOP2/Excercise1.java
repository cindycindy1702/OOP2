import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Excercise1
{
    // --- 你不小心刪掉的這 6 行變數要記得補回來 ---
    static JPanel pne = new JPanel();
    static JButton btn = new JButton("擲骰子");
    static JLabel labNorth = new JLabel("已擲 0 次，總和 0，平均 0.00", JLabel.CENTER);
    static JLabel labCenter = new JLabel("?", JLabel.CENTER);
    static int count = 0;
    static int sum = 0;
    // ----------------------------------------------

    public static void main(String[] args)
    {
        JFrame frm = new JFrame("骰子模擬器");
        
        // 1. 設定中央點數的字體大小 (60pt)
        labCenter.setFont(new Font("Dialog", Font.PLAIN, 60));
        
        // 2. 將按鈕放入 JPanel
        pne.add(btn);
        
        // 3. 將元件依序加入 JFrame，並明確指定 BorderLayout 的方位
        frm.add(labNorth, BorderLayout.NORTH);
        frm.add(labCenter, BorderLayout.CENTER);
        frm.add(pne, BorderLayout.SOUTH);
        
        frm.setSize(400, 320);
        frm.setLocationRelativeTo(null); // 4. 讓視窗開啟時置中

        // 修正後的 ActionListener 寫法
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                // 產生亂數
                Random rand = new Random();
                int num = rand.nextInt(6) + 1;
                
                // 判斷顏色
                if (num == 6) {
                    labCenter.setForeground(Color.GREEN);
                } else if (num == 1) {
                    labCenter.setForeground(Color.RED);
                } else {
                    labCenter.setForeground(Color.BLACK);
                }
                
                // 1. 更新中央標籤顯示的數字
                labCenter.setText(String.valueOf(num));
                
                // 2. 更新次數與總和
                count++;            // 相當於 count = count + 1;
                sum += num;         // 相當於 sum = sum + num;
                
                // 3. 計算平均值並更新上方標籤 (注意整數相除的問題，要先轉 double)
                double average = (double) sum / count;
                String info = String.format("已擲 %d 次，總和 %d，平均 %.2f", count, sum, average);
                labNorth.setText(info);
            }
        }); 
        
        // 建議將 setVisible 放在所有元件都設定完畢的最後一步
        frm.setVisible(true);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}