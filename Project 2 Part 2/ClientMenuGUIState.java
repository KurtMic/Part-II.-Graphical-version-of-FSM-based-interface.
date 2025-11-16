import javax.swing.*;
import java.awt.*;

public class ClientMenuGUIState implements State {

    @Override
    public JPanel getPanel(GUIContext context) {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        int clientID = context.getClientID();
        Client client = context.getClientDatabase().search("C" + clientID);

        JLabel title = new JLabel("Client Menu: C" + clientID, JLabel.CENTER);
        panel.add(title);

        JButton wishlistBtn = new JButton("Wishlist");
        wishlistBtn.addActionListener(e -> context.setState(new WishlistMenuGUIState()));
        panel.add(wishlistBtn);

        JButton viewInvoicesBtn = new JButton("View Invoices");
        viewInvoicesBtn.addActionListener(e -> viewInvoices(context, client));
        panel.add(viewInvoicesBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> context.setState(new OpeningGUIState()));
        panel.add(logoutBtn);

        return panel;
    }

    private void viewInvoices(GUIContext context, Client client) {
        java.util.List<Invoice> invoices = context.getClientInvoices().get(client.getId());
        if (invoices == null || invoices.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No invoices.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Invoice inv : invoices) sb.append(inv).append("\n");
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Invoices", JOptionPane.INFORMATION_MESSAGE);
    }
}
