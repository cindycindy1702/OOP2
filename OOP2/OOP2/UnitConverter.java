import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UnitConverter extends JFrame {
    
    private JComboBox<String> typeCombo;
    private JTextField inputField, resultField;
    private JComboBox<String> sourceUnitCombo, targetUnitCombo;
    private JButton convertButton;

    // 定義各種類型的單位陣列
    private String[] lengthUnits = {"公尺", "公分", "英吋", "英尺"};
    private String[] weightUnits = {"公斤", "公克", "磅", "盎司"};
    private String[] tempUnits = {"攝氏", "華氏", "克氏"};

    public UnitConverter() {
        // 1. 視窗基本設定
        setTitle("單位換算器");
        setSize(480, 280); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 2. NORTH：換算類型選擇
        typeCombo = new JComboBox<>(new String[]{"長度", "重量", "溫度"});
        add(typeCombo, BorderLayout.NORTH);

        // 3. CENTER：輸入框與單位選單
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10)); 
        inputField = new JTextField();
        sourceUnitCombo = new JComboBox<>(lengthUnits); 
        
        resultField = new JTextField();
        resultField.setEditable(false); 
        targetUnitCombo = new JComboBox<>(lengthUnits); 

        centerPanel.add(inputField);
        centerPanel.add(sourceUnitCombo);
        centerPanel.add(resultField);
        centerPanel.add(targetUnitCombo);
        
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(centerPanel, BorderLayout.CENTER);

        // 4. SOUTH：換算按鈕
        convertButton = new JButton("換算");
        add(convertButton, BorderLayout.SOUTH);

        // ==========================================
        // 事件註冊區 
        // ==========================================
        
        // 下拉選單連動：切換類型時，更新單位的選項
        typeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUnitCombos();
            }
        });

        // 點擊換算按鈕
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateResult();
            }
        });
    }

    // 更新選單內容
    private void updateUnitCombos() {
        int selectedIndex = typeCombo.getSelectedIndex();
        String[] newUnits;
        
        if (selectedIndex == 0) {
            newUnits = lengthUnits;
        } else if (selectedIndex == 1) {
            newUnits = weightUnits;
        } else {
            newUnits = tempUnits;
        }

        sourceUnitCombo.removeAllItems();
        targetUnitCombo.removeAllItems();
        for (String unit : newUnits) {
            sourceUnitCombo.addItem(unit);
            targetUnitCombo.addItem(unit);
        }
    }

    // 執行換算邏輯
    private void calculateResult() {
        try {
            double inputVal = Double.parseDouble(inputField.getText());
            int typeIndex = typeCombo.getSelectedIndex();
            int sourceIndex = sourceUnitCombo.getSelectedIndex();
            int targetIndex = targetUnitCombo.getSelectedIndex();
            double result = 0.0;

            if (typeIndex == 0) { 
                // ==============================
                // 長度換算 (基準單位：公尺)
                // ==============================
                double meters = inputVal;
                if (sourceIndex == 1) meters = inputVal / 100.0;          // 公分 -> 公尺
                else if (sourceIndex == 2) meters = inputVal * 0.0254;    // 英吋 -> 公尺
                else if (sourceIndex == 3) meters = inputVal * 0.3048;    // 英尺 -> 公尺

                if (targetIndex == 0) result = meters;                    // 轉公尺
                else if (targetIndex == 1) result = meters * 100.0;       // 轉公分
                else if (targetIndex == 2) result = meters / 0.0254;      // 轉英吋
                else if (targetIndex == 3) result = meters / 0.3048;      // 轉英尺

            } else if (typeIndex == 1) { 
                // ==============================
                // 重量換算 (基準單位：公斤)
                // ==============================
                double kgs = inputVal;
                if (sourceIndex == 1) kgs = inputVal / 1000.0;            // 公克 -> 公斤
                else if (sourceIndex == 2) kgs = inputVal * 0.45359237;   // 磅 -> 公斤
                else if (sourceIndex == 3) kgs = inputVal * 0.02834952;   // 盎司 -> 公斤

                if (targetIndex == 0) result = kgs;                       // 轉公斤
                else if (targetIndex == 1) result = kgs * 1000.0;         // 轉公克
                else if (targetIndex == 2) result = kgs / 0.45359237;     // 轉磅
                else if (targetIndex == 3) result = kgs / 0.02834952;     // 轉盎司

            } else if (typeIndex == 2) { 
                // ==============================
                // 溫度換算 (基準單位：攝氏)
                // ==============================
                double celsius = inputVal;
                if (sourceIndex == 1) celsius = (inputVal - 32) * 5.0 / 9.0; // 華氏 -> 攝氏
                else if (sourceIndex == 2) celsius = inputVal - 273.15;      // 克氏 -> 攝氏

                if (targetIndex == 0) result = celsius;                      // 轉攝氏
                else if (targetIndex == 1) result = celsius * 9.0 / 5.0 + 32;// 轉華氏
                else if (targetIndex == 2) result = celsius + 273.15;        // 轉克氏
            }

            // 顯示結果，保留小數點後四位
            resultField.setText(String.format("%.4f", result));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "請輸入有效的數字！", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UnitConverter frame = new UnitConverter();
            frame.setLocationRelativeTo(null); 
            frame.setVisible(true);
        });
    }
}