
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class Main extends JFrame {

    private EmergencyManager manager = new EmergencyManager();

    private JTextField nameField = new JTextField();
    private JTextField locationField = new JTextField();
    private JTextArea descriptionArea = new JTextArea(3, 20);

    private JComboBox<String> typeBox = new JComboBox<>(
        new String[]{"Medical", "Fire", "Security", "Infrastructure"}
    );

    private DefaultTableModel model = new DefaultTableModel(
        new String[]{"ID", "Student", "Type", "Location",
                     "Assigned Team", "Status"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private JTable table = new JTable(model);

    public Main() {
        setTitle("Campus Emergency & Alert Management System");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loadData();
        buildGUI();
        refreshTable();
    }

    private void buildGUI() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel(
            "Campus Emergency & Alert Management System",
            SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(36, 79, 125));
        title.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));

        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder(
            "Report Emergency"));

        form.add(new JLabel("Student Name:"));
        form.add(nameField);

        form.add(new JLabel("Emergency Type:"));
        form.add(typeBox);

        form.add(new JLabel("Campus Location:"));
        form.add(locationField);

        form.add(new JLabel("Description:"));
        form.add(new JScrollPane(descriptionArea));

        JButton submitButton = new JButton("Submit Emergency");
        JButton clearButton = new JButton("Clear Form");

        form.add(submitButton);
        form.add(clearButton);

        JPanel center = new JPanel(new BorderLayout(8, 8));
        center.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        center.add(form, BorderLayout.NORTH);

        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createTitledBorder(
            "Reported Incidents"));

        center.add(tableScroll, BorderLayout.CENTER);

        JButton updateButton = new JButton("Update Status");
        JButton refreshButton = new JButton("Refresh Table");

        JPanel actions = new JPanel();
        actions.add(updateButton);
        actions.add(refreshButton);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(actions, BorderLayout.NORTH);

        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> submitEmergency());

        clearButton.addActionListener(e -> {
            nameField.setText("");
            locationField.setText("");
            descriptionArea.setText("");
            typeBox.setSelectedIndex(0);
        });

        refreshButton.addActionListener(e -> refreshTable());
        updateButton.addActionListener(e -> updateStatus());
    }

    private void submitEmergency() {
        String name = nameField.getText().trim();
        String location = locationField.getText().trim();
        String description = descriptionArea.getText().trim();
        String type = (String) typeBox.getSelectedItem();

        if (name.isEmpty() || location.isEmpty()
                || description.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields.");
            return;
        }

        try {
            int id = manager.generateId();

            Emergency emergency = manager.createEmergency(
                id, name, type, location, description);

            manager.addEmergency(emergency);
            saveData();
            refreshTable();

            JOptionPane.showMessageDialog(this,
                "Emergency reported successfully!\n"
                + "Incident ID: " + id + "\n"
                + "Assigned Team: " + emergency.getAssignedTeam());

            nameField.setText("");
            locationField.setText("");
            descriptionArea.setText("");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateStatus() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select an incident from the table.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        String[] statuses = {
            "Pending", "In Progress", "Resolved"
        };

        String status = (String) JOptionPane.showInputDialog(
            this,
            "Select new status:",
            "Update Incident Status",
            JOptionPane.QUESTION_MESSAGE,
            null,
            statuses,
            model.getValueAt(row, 5)
        );

        if (status != null) {
            manager.updateStatus(id, status);
            saveData();
            refreshTable();
        }
    }

    private void refreshTable() {
        model.setRowCount(0);

        for (Emergency e : manager.getAllEmergencies()) {
            model.addRow(new Object[]{
                e.getId(),
                e.getStudentName(),
                e.getType(),
                e.getLocation(),
                e.getAssignedTeam(),
                e.getStatus()
            });
        }
    }

    private void loadData() {
        try {
            manager.loadEmergencies(FileStorage.load());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                "Could not load saved incidents: " + ex.getMessage());
        }
    }

    private void saveData() {
        try {
            FileStorage.save(manager.getAllEmergencies());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                "Could not save incidents: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }
}
