import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class EventManagementApp {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/event_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Event Management System");
        frame.setSize(900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main Panel
        JTabbedPane tabbedPane = new JTabbedPane();
        frame.add(tabbedPane);

        // Add Event Tab
        JPanel addEventPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("Add Event", addEventPanel);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));  // Added extra field for Status
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Event"));

        JLabel nameLabel = new JLabel("Event Name:");
        JTextField nameField = new JTextField();

        JLabel dateLabel = new JLabel("Event Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField();

        JLabel descLabel = new JLabel("Description:");
        JTextArea descArea = new JTextArea(3, 20);
        JScrollPane descScroll = new JScrollPane(descArea);

        JLabel categoryLabel = new JLabel("Category:");
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"Seminar", "Workshop", "Party", "Other"});

        JLabel mediaLabel = new JLabel("Event Media (Image):");
        JButton uploadButton = new JButton("Upload Media");

        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Upcoming", "Ongoing", "Completed"});

        JButton submitButton = new JButton("Submit");
        JButton clearButton = new JButton("Clear");

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(dateLabel);
        formPanel.add(dateField);
        formPanel.add(descLabel);
        formPanel.add(descScroll);
        formPanel.add(categoryLabel);
        formPanel.add(categoryBox);
        formPanel.add(mediaLabel);
        formPanel.add(uploadButton);
        formPanel.add(statusLabel);
        formPanel.add(statusBox);
        formPanel.add(new JLabel());
        formPanel.add(submitButton);

        addEventPanel.add(formPanel, BorderLayout.NORTH);
        addEventPanel.add(clearButton, BorderLayout.SOUTH);

        // View Events Tab
        JPanel viewEventPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("View Events", viewEventPanel);

        JTable eventTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Event Name", "Event Date", "Description", "Category", "Media", "Status"}, 0);
        eventTable.setModel(tableModel);
        JScrollPane tableScroll = new JScrollPane(eventTable);

        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");
        JButton exportButton = new JButton("Export to CSV");
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(exportButton);

        viewEventPanel.add(tableScroll, BorderLayout.CENTER);
        viewEventPanel.add(buttonPanel, BorderLayout.SOUTH);

       // Search Event Tab
JPanel searchEventPanel = new JPanel(new BorderLayout());
tabbedPane.addTab("Search Events", searchEventPanel);

// Create search field, button, and table
JTextField searchField = new JTextField();
JButton searchButton = new JButton("Search");
JTable searchTable = new JTable();
DefaultTableModel searchTableModel = new DefaultTableModel(new String[]{"ID", "Event Name", "Event Date", "Description", "Category", "Media","Status"}, 0);
searchTable.setModel(searchTableModel);

// Set preferred size for the search field
searchField.setPreferredSize(new Dimension(300, 30)); // Width = 300, Height = 30

// Search panel layout with label, search field, and button
JPanel searchPanel = new JPanel();
searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Adjust alignment and gap
searchPanel.add(new JLabel("Search by Event Name:"));
searchPanel.add(searchField);
searchPanel.add(searchButton);

// Add the search panel and table to the main panel
searchEventPanel.add(searchPanel, BorderLayout.NORTH);
searchEventPanel.add(new JScrollPane(searchTable), BorderLayout.CENTER);

// Search button action to search for events
searchButton.addActionListener(e -> {
    String searchQuery = searchField.getText().toLowerCase();
    searchEvents(searchQuery, searchTableModel);
});


        // Upload Media File
        String[] mediaPath = new String[1];
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Event Image");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "gif"));
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                mediaPath[0] = selectedFile.getAbsolutePath();
            }
        });

        // Submit Button Action
        submitButton.addActionListener(e -> {
            String eventName = nameField.getText();
            String eventDate = dateField.getText();
            String description = descArea.getText();
            String category = (String) categoryBox.getSelectedItem();
            String status = (String) statusBox.getSelectedItem();

            if (eventName.isEmpty() || eventDate.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement ps = connection.prepareStatement("INSERT INTO events (event_name, event_date, event_description, category, media_path, status) VALUES (?, ?, ?, ?, ?, ?)")) {

                ps.setString(1, eventName);
                ps.setString(2, eventDate);
                ps.setString(3, description);
                ps.setString(4, category);
                ps.setString(5, mediaPath[0]);
                ps.setString(6, status);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Event added successfully!");
                nameField.setText("");
                dateField.setText("");
                descArea.setText("");
                loadEvents(tableModel);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving event: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Clear Button Action
        clearButton.addActionListener(e -> {
            nameField.setText("");
            dateField.setText("");
            descArea.setText("");
        });

        // Load events into table
        loadEvents(tableModel);

        // Delete Button Action
        deleteButton.addActionListener(e -> {
            int selectedRow = eventTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Please select an event to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int eventId = (int) tableModel.getValueAt(selectedRow, 0);
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement ps = connection.prepareStatement("DELETE FROM events WHERE id = ?")) {

                ps.setInt(1, eventId);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Event deleted successfully!");
                loadEvents(tableModel);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error deleting event: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Update Button Action
        updateButton.addActionListener(e -> {
            int selectedRow = eventTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Please select an event to update!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int eventId = (int) tableModel.getValueAt(selectedRow, 0);
            String eventName = (String) tableModel.getValueAt(selectedRow, 1);
            String eventDate = (String) tableModel.getValueAt(selectedRow, 2);
            String description = (String) tableModel.getValueAt(selectedRow, 3);
            String category = (String) tableModel.getValueAt(selectedRow, 4);
            String status = (String) tableModel.getValueAt(selectedRow, 6);

            // Show input dialog for updating
            JTextField nameFieldUpdate = new JTextField(eventName);
            JTextField dateFieldUpdate = new JTextField(eventDate);
            JTextArea descAreaUpdate = new JTextArea(description);
            JComboBox<String> categoryBoxUpdate = new JComboBox<>(new String[]{"Seminar", "Workshop", "Party", "Other"});
            categoryBoxUpdate.setSelectedItem(category);
            JComboBox<String> statusBoxUpdate = new JComboBox<>(new String[]{"Upcoming", "Ongoing", "Completed"});
            statusBoxUpdate.setSelectedItem(status);

            JPanel updatePanel = new JPanel(new GridLayout(6, 2));
            updatePanel.add(new JLabel("Event Name:"));
            updatePanel.add(nameFieldUpdate);
            updatePanel.add(new JLabel("Event Date:"));
            updatePanel.add(dateFieldUpdate);
            updatePanel.add(new JLabel("Description:"));
            updatePanel.add(new JScrollPane(descAreaUpdate));
            updatePanel.add(new JLabel("Category:"));
            updatePanel.add(categoryBoxUpdate);
            updatePanel.add(new JLabel("Status:"));
            updatePanel.add(statusBoxUpdate);

            int option = JOptionPane.showConfirmDialog(frame, updatePanel, "Update Event", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String updatedName = nameFieldUpdate.getText();
                String updatedDate = dateFieldUpdate.getText();
                String updatedDescription = descAreaUpdate.getText();
                String updatedCategory = (String) categoryBoxUpdate.getSelectedItem();
                String updatedStatus = (String) statusBoxUpdate.getSelectedItem();

                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement ps = connection.prepareStatement("UPDATE events SET event_name = ?, event_date = ?, event_description = ?, category = ?, status = ? WHERE id = ?")) {

                    ps.setString(1, updatedName);
                    ps.setString(2, updatedDate);
                    ps.setString(3, updatedDescription);
                    ps.setString(4, updatedCategory);
                    ps.setString(5, updatedStatus);
                    ps.setInt(6, eventId);
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(frame, "Event updated successfully!");
                    loadEvents(tableModel);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error updating event: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exportButton.addActionListener(e -> {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save as CSV");
                fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
                int result = fileChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (!file.getName().endsWith(".csv")) {
                        file = new File(file.getAbsolutePath() + ".csv");
                    }
        
                    try (FileWriter writer = new FileWriter(file)) {
                        // Write table header
                        for (int i = 0; i < tableModel.getColumnCount(); i++) {
                            writer.append(tableModel.getColumnName(i));
                            if (i < tableModel.getColumnCount() - 1) {
                                writer.append(",");
                            }
                        }
                        writer.append("\n");
        
                        // Write table data
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                                Object cellValue = tableModel.getValueAt(i, j);
                                // Check if the value is null and replace with an empty string
                                writer.append(cellValue != null ? cellValue.toString() : "");
                                if (j < tableModel.getColumnCount() - 1) {
                                    writer.append(",");
                                }
                            }
                            writer.append("\n");
                        }
        
                        JOptionPane.showMessageDialog(frame, "CSV file saved successfully!");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error exporting to CSV: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        

        // Load events from the database and populate the table
        loadEvents(tableModel);

        frame.setVisible(true);
    }

    private static void searchEvents(String searchQuery, DefaultTableModel searchTableModel) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM events WHERE event_name LIKE ?")) {
            
            // Adding "%" for LIKE query to match anywhere in the event name
            ps.setString(1, "%" + searchQuery + "%");
    
            ResultSet rs = ps.executeQuery();
    
            // Clear previous search results
            searchTableModel.setRowCount(0);
    
            // Populate searchTable with the results
            while (rs.next()) {
                searchTableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("event_name"),
                        rs.getString("event_date"),
                        rs.getString("event_description"),
                        rs.getString("category"),
                        rs.getString("media_path"),
                        rs.getString("status"),
                });
            }
    
            if (searchTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "No events found matching the search query.", "No Results", JOptionPane.INFORMATION_MESSAGE);
            }
    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error searching events: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    private static void loadEvents(DefaultTableModel tableModel) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM events")) {

            // Clear existing rows
            tableModel.setRowCount(0);

            // Add rows from the database
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("event_name");
                String date = resultSet.getString("event_date");
                String description = resultSet.getString("event_description");
                String category = resultSet.getString("category");
                String media = resultSet.getString("media_path");
                String status = resultSet.getString("status");
                tableModel.addRow(new Object[]{id, name, date, description, category, media, status});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error loading events: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

