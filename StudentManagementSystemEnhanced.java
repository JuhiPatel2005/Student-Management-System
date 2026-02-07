import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.io.*;
import java.util.Properties;

public class StudentManagementSystemEnhanced extends JFrame {
    private String DB_URL;
    private String DB_USER;
    private String DB_PASSWORD;

    private JTextField txtId, txtName, txtEmail, txtPhone, txtCourse, txtSearch;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private Connection connection;
    
    public StudentManagementSystemEnhanced() {
        // Load database configuration
        loadDatabaseConfig();
        
        setTitle("Student Management System - Enhanced");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialize database connection
        initializeDatabase();
        
        // Create GUI
        createGUI();
        
        // Load initial data
        refreshTable();
    }
    
    private void loadDatabaseConfig() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("db.properties")) {
            props.load(input);
            DB_URL = props.getProperty("db.url", "jdbc:mysql://localhost:3306/student_db");
            DB_USER = props.getProperty("db.user", "root");
            DB_PASSWORD = props.getProperty("db.password");
        } catch (IOException e) {
            // If properties file not found, use defaults
            System.err.println("Warning: db.properties not found, using default values");
            DB_URL = "jdbc:mysql://localhost:3306/student_db";
            DB_USER = "root";
            DB_PASSWORD = "password";
        }
    }
    
    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            createTableIfNotExists();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Database connection failed: " + e.getMessage() + 
                "\n\nPlease ensure:\n" +
                "1. MySQL server is running\n" +
                "2. Database 'student_db' exists\n" +
                "3. Credentials in db.properties are correct", 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) UNIQUE, " +
                    "phone VARCHAR(15), " +
                    "course VARCHAR(50), " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void createGUI() {
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 245, 245));
        
        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(41, 128, 185));
        JLabel titleLabel = new JLabel("Student Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Form Panel
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.WEST);
        
        // Table Panel
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        
        // Status bar
        JPanel statusBar = createStatusBar();
        mainPanel.add(statusBar, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(41, 128, 185), 2), 
                "Student Details",
                0, 0, new Font("Arial", Font.BOLD, 14), new Color(41, 128, 185))));
        panel.setPreferredSize(new Dimension(350, 0));
        panel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // ID Field
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblId = new JLabel("Student ID:");
        lblId.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblId, gbc);
        gbc.gridx = 1;
        txtId = new JTextField(15);
        txtId.setEditable(false);
        txtId.setBackground(new Color(240, 240, 240));
        txtId.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(txtId, gbc);
        
        // Name Field
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblName = new JLabel("Name: *");
        lblName.setFont(new Font("Arial", Font.BOLD, 12));
        lblName.setForeground(new Color(231, 76, 60));
        panel.add(lblName, gbc);
        gbc.gridx = 1;
        txtName = new JTextField(15);
        txtName.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(txtName, gbc);
        
        // Email Field
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblEmail, gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(15);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(txtEmail, gbc);
        
        // Phone Field
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblPhone, gbc);
        gbc.gridx = 1;
        txtPhone = new JTextField(15);
        txtPhone.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(txtPhone, gbc);
        
        // Course Field
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblCourse = new JLabel("Course:");
        lblCourse.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblCourse, gbc);
        gbc.gridx = 1;
        txtCourse = new JTextField(15);
        txtCourse.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(txtCourse, gbc);
        
        // Required field note
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        JLabel lblRequired = new JLabel("* Required field");
        lblRequired.setFont(new Font("Arial", Font.ITALIC, 10));
        lblRequired.setForeground(Color.GRAY);
        panel.add(lblRequired, gbc);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnAdd = createStyledButton("Add", new Color(46, 204, 113));
        btnAdd.addActionListener(e -> addStudent());
        
        JButton btnUpdate = createStyledButton("Update", new Color(52, 152, 219));
        btnUpdate.addActionListener(e -> updateStudent());
        
        JButton btnDelete = createStyledButton("Delete", new Color(231, 76, 60));
        btnDelete.addActionListener(e -> deleteStudent());
        
        JButton btnClear = createStyledButton("Clear", new Color(149, 165, 166));
        btnClear.addActionListener(e -> clearFields());
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        
        gbc.gridy = 6;
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setFont(new Font("Arial", Font.BOLD, 12));
        searchPanel.add(lblSearch);
        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Arial", Font.PLAIN, 12));
        searchPanel.add(txtSearch);
        
        JButton btnSearch = createStyledButton("Search", new Color(52, 152, 219));
        btnSearch.addActionListener(e -> searchStudent());
        searchPanel.add(btnSearch);
        
        JButton btnRefresh = createStyledButton("Refresh All", new Color(149, 165, 166));
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            refreshTable();
        });
        searchPanel.add(btnRefresh);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Name", "Email", "Phone", "Course"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 12));
        studentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        studentTable.getTableHeader().setBackground(new Color(41, 128, 185));
        studentTable.getTableHeader().setForeground(Color.WHITE);
        studentTable.setRowHeight(25);
        studentTable.setSelectionBackground(new Color(52, 152, 219));
        studentTable.setSelectionForeground(Color.WHITE);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedStudent();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBackground(new Color(236, 240, 241));
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JLabel statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        statusBar.add(statusLabel);
        
        return statusBar;
    }
    
    private void addStudent() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String course = txtCourse.getText().trim();
        
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter student name", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            txtName.requestFocus();
            return;
        }
        
        String sql = "INSERT INTO students (name, email, phone, course) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email.isEmpty() ? null : email);
            pstmt.setString(3, phone.isEmpty() ? null : phone);
            pstmt.setString(4, course.isEmpty() ? null : course);
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student added successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            refreshTable();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(this, "Email already exists!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error adding student: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void updateStudent() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a student to update", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter student name", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            txtName.requestFocus();
            return;
        }
        
        String sql = "UPDATE students SET name=?, email=?, phone=?, course=? WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, txtEmail.getText().trim().isEmpty() ? null : txtEmail.getText().trim());
            pstmt.setString(3, txtPhone.getText().trim().isEmpty() ? null : txtPhone.getText().trim());
            pstmt.setString(4, txtCourse.getText().trim().isEmpty() ? null : txtCourse.getText().trim());
            pstmt.setInt(5, Integer.parseInt(id));
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Student updated successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                refreshTable();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating student: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteStudent() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this student?\nThis action cannot be undone.", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM students WHERE id=?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(id));
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Student deleted successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    refreshTable();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error deleting student: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void searchStudent() {
        String searchTerm = txtSearch.getText().trim();
        if (searchTerm.isEmpty()) {
            refreshTable();
            return;
        }
        
        String sql = "SELECT * FROM students WHERE name LIKE ? OR email LIKE ? OR course LIKE ? OR phone LIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);
            
            ResultSet rs = pstmt.executeQuery();
            tableModel.setRowCount(0);
            
            int count = 0;
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("course")
                });
                count++;
            }
            
            if (count == 0) {
                JOptionPane.showMessageDialog(this, 
                    "No students found matching: " + searchTerm, 
                    "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error searching: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void refreshTable() {

        if (connection == null) {
            JOptionPane.showMessageDialog(this,
                    "Database not connected!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "SELECT * FROM students ORDER BY id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            tableModel.setRowCount(0);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("course")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadSelectedStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow >= 0) {
            txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            
            Object email = tableModel.getValueAt(selectedRow, 2);
            txtEmail.setText(email != null ? email.toString() : "");
            
            Object phone = tableModel.getValueAt(selectedRow, 3);
            txtPhone.setText(phone != null ? phone.toString() : "");
            
            Object course = tableModel.getValueAt(selectedRow, 4);
            txtCourse.setText(course != null ? course.toString() : "");
        }
    }
    
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtCourse.setText("");
        studentTable.clearSelection();
        txtName.requestFocus();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new StudentManagementSystemEnhanced().setVisible(true);
        });
    }
}
