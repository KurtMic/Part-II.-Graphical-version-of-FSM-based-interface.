import java.awt.*;
import java.util.Iterator;
import javax.swing.*;

public class ClerkMenuGUIState implements State {

    private GUIContext context;

    public ClerkMenuGUIState() {
        // empty constructor context will be passed in getPanel
    }

    @Override
    public JPanel getPanel(GUIContext context) {
        this.context = context;

        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("=== Clerk Menu ===", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 5, 5));

        JButton addClientBtn = new JButton("Add Client");
        JButton viewClientsBtn = new JButton("View Clients");
        JButton backBtn = new JButton("Back to Opening Menu");

        buttonPanel.add(addClientBtn);
        buttonPanel.add(viewClientsBtn);
        buttonPanel.add(backBtn);

        panel.add(buttonPanel, BorderLayout.CENTER);

        //Button actions
        addClientBtn.addActionListener(e -> showAddClientDialog());
        viewClientsBtn.addActionListener(e -> showAllClientsDialog());
        backBtn.addActionListener(e -> context.setState(new OpeningGUIState()));

        return panel;
    }

    private void showAddClientDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();

        Object[] message = {
                "ID (number):", idField,
                "Name:", nameField,
                "Email:", emailField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New Client", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String id = "C" + idField.getText().trim();
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();

                if (name.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name and Email cannot be empty!");
                    return;
                }

                Client client = new Client(id, name, email);
                ClientDatabase.instance().addClient(client);
                JOptionPane.showMessageDialog(null, "Client added: " + client.getId());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error adding client: " + ex.getMessage());
            }
        }
    }

    private void showAllClientsDialog() {
        StringBuilder sb = new StringBuilder();
        Iterator<Client> it = ClientDatabase.instance().getClients();
        while (it.hasNext()) {
            Client c = it.next();
            sb.append(c.getId()).append(" - ").append(c.getName()).append(" - ").append(c.getAddress()).append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "No clients found.");
    }
}
