package bank;

import bank.model.Bankaccount;
import static com.sun.glass.ui.Cursor.setVisible;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class BankFrame extends JFrame {

    private final JLabel fromLabel = new JLabel("Sa računa:");
    private final JLabel amountLabel = new JLabel("Iznos: ");
    private final JLabel toLabel = new JLabel("Na račun:");
    private final JComboBox<Bankaccount> fromComboBox = new JComboBox<>();
    private final JTextField amountTextField = new JTextField(10);
    private JComboBox<Bankaccount> toComboBox = new JComboBox<>();
    private final JButton transferButton = new JButton("Execute");

    public BankFrame() {
        super("Bank transfer");
        setSize(450, 250);
        setLayout(new GridLayout(4, 1));
        JPanel fromPanel = new JPanel();
        JPanel amountPanel = new JPanel();
        JPanel toPanel = new JPanel();
        JPanel transferPanel = new JPanel();
        add(fromPanel);
        add(amountPanel);
        add(toPanel);
        add(transferPanel);

        fromPanel.add(fromLabel);
        List<Bankaccount> bankAccounts = Bankaccount.loadAll();
        bankAccounts.forEach(bankAccount -> fromComboBox.addItem(bankAccount));
        fromPanel.add(fromComboBox);
        amountPanel.add(amountLabel);
        amountPanel.add(amountTextField);
        toPanel.add(toLabel);
        bankAccounts.forEach(bankAccount -> toComboBox.addItem(bankAccount));
        toPanel.add(toComboBox);
        transferButton.addActionListener(this::executeTransfer);
        transferPanel.add(transferButton);
    }

    private void executeTransfer(ActionEvent e) {
        Bankaccount fromAccount = (Bankaccount) fromComboBox.getSelectedItem();
        Bankaccount toAccount = (Bankaccount) toComboBox.getSelectedItem();
        Double amount = Double.parseDouble(amountTextField.getText());
        Bankaccount.transferMoney(fromAccount, toAccount, amount);
    }

    public void showFrame() {
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        BankFrame bankFrame = new BankFrame();
        SwingUtilities.invokeLater(bankFrame::showFrame);
    }
}
