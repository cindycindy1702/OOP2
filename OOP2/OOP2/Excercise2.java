import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

public class Excercise2 implements ActionListener
{
    static JFrame frm = new JFrame("單位換算器"); 
    
    static JPanel panelNorth = new JPanel();
    static String[] types = {"長度", "重量", "溫度"};
    static JComboBox<String> typeCombo = new JComboBox<String>(types);
    
    static JPanel panelSouth = new JPanel();
    static JButton btn = new JButton("換算");

    static JPanel panelCenter = new JPanel();
    static JPanel panelRow1 = new JPanel(); 
    static JPanel panelRow2 = new JPanel(); 

    static JTextField txtSource = new JTextField(10);
    static JTextField txtTarget = new JTextField(10);

    // ===== 需要補上的陣列 =====
    static String[] lengthUnits = {"公尺", "公分", "英吋", "英尺"};
    static String[] weightUnits = {"公斤", "公克", "磅", "盎司"};
    static String[] tempUnits = {"攝氏", "華氏", "克氏"};
    
    static JComboBox<String> comboSourceUnit = new JComboBox<String>(lengthUnits);
    static JComboBox<String> comboTargetUnit = new JComboBox<String>(lengthUnits);

    public static void main(String[] args)
    {
        frm.setSize(480, 280);
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frm.setLayout(new BorderLayout());
        
        panelNorth.add(typeCombo);
        panelSouth.add(btn);

        panelCenter.setLayout(new GridLayout(2, 1));
        
        panelRow1.add(txtSource);
        panelRow1.add(comboSourceUnit);
        
        panelRow2.add(txtTarget);
        panelRow2.add(comboTargetUnit);

        panelCenter.add(panelRow1);
        panelCenter.add(panelRow2);
        
        frm.add(panelCenter, BorderLayout.CENTER);

        txtTarget.setEditable(false); 

        frm.add(panelNorth, BorderLayout.NORTH); 
        frm.add(panelSouth, BorderLayout.SOUTH);
        
        Excercise2 listener = new Excercise2();
        btn.addActionListener(listener);
        typeCombo.addActionListener(listener);
        
        frm.setVisible(true); 
    }

    public void actionPerformed(ActionEvent e) 
    {
        // ===== 邏輯一：當切換上方的下拉式選單時 =====
        if (e.getSource() == typeCombo) {
            String selectedType = (String) typeCombo.getSelectedItem();
            
            // 1. 先使用 removeAllItems() 清空原本的單位
            comboSourceUnit.removeAllItems();
            comboTargetUnit.removeAllItems();
            
            // 2. 根據選到的類型，把新的單位陣列用迴圈加進去
            if (selectedType.equals("長度")) {
                for (String unit : lengthUnits) {
                    comboSourceUnit.addItem(unit);
                    comboTargetUnit.addItem(unit);
                }
            } else if (selectedType.equals("重量")) {
                for (String unit : weightUnits) {
                    comboSourceUnit.addItem(unit);
                    comboTargetUnit.addItem(unit);
                }
            } else if (selectedType.equals("溫度")) {
                for (String unit : tempUnits) {
                    comboSourceUnit.addItem(unit);
                    comboTargetUnit.addItem(unit);
                }
            }
        }
        
        // ===== 邏輯二：當按下換算按鈕時 =====
        else if (e.getSource() == btn) {
            try {
                // 1. 取得使用者輸入的數字 (把它轉換成 double)
                double input = Double.parseDouble(txtSource.getText());
                double result = 0.0;
                
                String type = (String) typeCombo.getSelectedItem();
                String srcUnit = (String) comboSourceUnit.getSelectedItem();
                String tgtUnit = (String) comboTargetUnit.getSelectedItem();
                
                // 2. 根據目前類型進行數學換算
                if (type.equals("長度")) {
                    // 小技巧：全部先換算成「公分」
                    double cm = 0;
                    if (srcUnit.equals("公尺")) cm = input * 100;
                    else if (srcUnit.equals("公分")) cm = input;
                    else if (srcUnit.equals("英吋")) cm = input * 2.54;
                    else if (srcUnit.equals("英尺")) cm = input * 30.48;

                    // 再從公分換算成「目標單位」
                    if (tgtUnit.equals("公尺")) result = cm / 100;
                    else if (tgtUnit.equals("公分")) result = cm;
                    else if (tgtUnit.equals("英吋")) result = cm / 2.54;
                    else if (tgtUnit.equals("英尺")) result = cm / 30.48;
                } 
                else if (type.equals("重量")) {
                    // 全部先換算成「公克」
                    double g = 0;
                    if (srcUnit.equals("公斤")) g = input * 1000;
                    else if (srcUnit.equals("公克")) g = input;
                    else if (srcUnit.equals("磅")) g = input * 453.59237;
                    else if (srcUnit.equals("盎司")) g = input * 28.3495231;

                    if (tgtUnit.equals("公斤")) result = g / 1000;
                    else if (tgtUnit.equals("公克")) result = g;
                    else if (tgtUnit.equals("磅")) result = g / 453.59237;
                    else if (tgtUnit.equals("盎司")) result = g / 28.3495231;
                } 
                else if (type.equals("溫度")) {
                    // 全部先換算成「攝氏」
                    double c = 0;
                    if (srcUnit.equals("攝氏")) c = input;
                    else if (srcUnit.equals("華氏")) c = (input - 32) * 5 / 9;
                    else if (srcUnit.equals("克氏")) c = input - 273.15;

                    if (tgtUnit.equals("攝氏")) result = c;
                    else if (tgtUnit.equals("華氏")) result = c * 9 / 5 + 32;
                    else if (tgtUnit.equals("克氏")) result = c + 273.15;
                }
                
                // 3. 把計算結果轉成字串，顯示到第二個框框 (並只顯示到小數點後四位)
                txtTarget.setText(String.format("%.4f", result));
                
            } catch (NumberFormatException ex) {
                // 防呆：如果輸入英文字母或是空值，會跳出警告訊息
                JOptionPane.showMessageDialog(frm, "請輸入有效的數字！", "錯誤", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}