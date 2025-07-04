import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator extends JFrame {

    private final JPanel pnlDisplay = new JPanel();
    private final JPanel pnlButtons = new JPanel();
    private final JTextField display = new JTextField();

    private final String[] btnLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
    };

    private final JButton[] btn = new JButton[btnLabels.length];
    private final Font font = new Font("COURIER NEW", Font.BOLD, 28);

    private String operatorAcum = null;
    private Double numberAcum = null;
    private boolean clearDisplay = false;

    public Calculator() {
        this.setBounds(100, 100, 300, 400);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.positioning();
        this.setVisible(true);
    }

    private void buildButtons() {
        for (int i = 0; i < btnLabels.length; i++) {
            btn[i] = new JButton(btnLabels[i]);
            btn[i].setFont(font);
            btn[i].addActionListener(e -> clickDigit(((JButton) e.getSource()).getText()));
            pnlButtons.add(btn[i]);
        }
    }

    private void clickDigit(String digit) {
        String digits = "0123456789";
        String operators = "/*-+=";

        if (digits.contains(digit)) {
            if (clearDisplay) {
                display.setText("");
                clearDisplay = false;
            }
            display.setText(display.getText() + digit);
        } else {
            if (operators.contains(digit)) {
                operation(digit);
            } else {
                // apenas o ponto
                if (!display.getText().contains(".")) {
                    display.setText(display.getText() + digit);
                }
            }
        }
    }

    private boolean acumExists() {
        return operatorAcum != null && numberAcum != null;
    }

    private void operation(String digito) {
        if (!display.getText().isEmpty()) {
            double CurrentValue = Double.parseDouble(display.getText());
            if (!digito.equals("=")) {
                if (!acumExists()) {
                    setNumberAcum(CurrentValue);
                    setOperatorAcum(digito);
                    clearDisplay = true;
                } else {
                    operation("=");
                    setNumberAcum(Double.parseDouble(display.getText()));
                    setOperatorAcum(digito);
                }
            } else {
                double total = 0.0;
                switch (operatorAcum) {
                    case "+" -> total = numberAcum + CurrentValue;
                    case "-" -> total = numberAcum - CurrentValue;
                    case "*" -> total = numberAcum * CurrentValue;
                    case "/" -> total = numberAcum / CurrentValue;
                    default -> {
                        display.setText("Erro");
                        return;
                    }
                }
                setNumberAcum(null);
                setOperatorAcum(null);
                display.setText(String.valueOf(total));
            }
        }
    }

    private void positioning() {
        pnlDisplay.setBounds(5, 5, this.getWidth() - 10, (int) (this.getHeight() * 0.2));
        int temp = pnlDisplay.getHeight() + 10;
        pnlButtons.setBounds(5, temp, this.getWidth() - 10, (int) (this.getHeight() * 0.8) - 53);

        this.setBackground(new Color(0, 0, 0));
        pnlDisplay.setBackground(new Color(255, 0, 0));
        pnlButtons.setBackground(new Color(0, 255, 0));

        this.add(pnlDisplay);
        this.add(pnlButtons);

        display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        display.setFont(font);
        pnlDisplay.setLayout(new GridLayout(1, 1));
        pnlDisplay.add(display);

        int raizQuad = (int) Math.sqrt(btnLabels.length);
        int linhas = (btnLabels.length % raizQuad == 0) ? raizQuad : raizQuad + 1;
        int colunas = raizQuad;

        pnlButtons.setLayout(new GridLayout(linhas, colunas));
        buildButtons();
    }

    public String getOperatorAcum() {
        return operatorAcum;
    }

    public void setOperatorAcum(String operatorAcum) {
        this.operatorAcum = operatorAcum;
    }

    public Double getNumberAcum() {
        return numberAcum;
    }

    public void setNumberAcum(Double numberAcum) {
        this.numberAcum = numberAcum;
    }

    public static void main(String[] args) {
        new Calculator();
    }
}