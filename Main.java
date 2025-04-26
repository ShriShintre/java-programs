/*
 * Hotel Staff Management System
 * Using Java Swing and JDBC
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser; // Make sure to include JCalendar in your classpath
import java.util.Date;
import java.text.SimpleDateFormat;



public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}

// Database Connection Class
class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hotelmanagement";
    private static final String USER = "root";
    private static final String PASSWORD = "system";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

// Login Frame
class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Hotel Staff Management System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Hotel Staff Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        loginButton.addActionListener(e -> {
            // In a real application, validate credentials against database
            // For this demo, we'll use admin/admin
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("admin") && password.equals("admin")) {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Invalid username or password",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panel);
    }
}

// Main Application Frame
class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    public MainFrame() {
        setTitle("Hotel Staff Management System");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // Add the different panels
        tabbedPane.addTab("Dashboard", new DashboardPanel());
        tabbedPane.addTab("Employees", new EmployeePanel());
        tabbedPane.addTab("Departments", new DepartmentPanel());
        tabbedPane.addTab("Schedules", new SchedulePanel());
        tabbedPane.addTab("Performance", new PerformancePanel());

        add(tabbedPane);

        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> {
            ImageIcon icon = new ImageIcon(getClass().getResource("./hotel.jpg"));
            Image scaledImage = icon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);

            // Create a new ImageIcon from the scaled image
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JOptionPane.showMessageDialog(MainFrame.this,
                    "Hotel Staff Management System\nStaff Vision\nPresented by:\nShrinath\nRashmi\nSantosh\n2025",
                    "About",
                    JOptionPane.INFORMATION_MESSAGE,scaledIcon);
        });
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }
}

// Dashboard Panel
class DashboardPanel extends JPanel {
    private JLabel totalEmployeesLabel;
    private JLabel todayScheduledLabel;
    private JPanel chartPanel;

    public DashboardPanel() {
        setLayout(new BorderLayout());

        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel employeeStatsPanel = createStatPanel("Employee Statistics");
        JPanel scheduleStatsPanel = createStatPanel("Schedule Statistics");

        statsPanel.add(employeeStatsPanel);
        statsPanel.add(scheduleStatsPanel);

        chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());
        chartPanel.setBorder(BorderFactory.createTitledBorder("Department Distribution"));

        // In a real application, this would be a real chart
        JPanel mockChartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = getWidth();
                int height = getHeight();

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int[] values = {25, 20, 30, 15, 10};
                Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE};
                String[] depts = {"Front Desk", "Housekeeping", "Food & Beverage", "Maintenance", "Management"};

                int startAngle = 0;
                int centerX = width / 2;
                int centerY = height / 2;
                int radius = Math.min(width, height) / 3;

                int legendX = width - 200;
                int legendY = 50;

                for (int i = 0; i < values.length; i++) {
                    int arcAngle = (int) (values[i] * 3.6);

                    g2d.setColor(colors[i]);
                    g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, startAngle, arcAngle);

                    g2d.fillRect(legendX, legendY + i * 25, 20, 20);
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(depts[i] + " (" + values[i] + "%)", legendX + 30, legendY + i * 25 + 15);

                    startAngle += arcAngle;
                }
            }
        };

        chartPanel.add(mockChartPanel, BorderLayout.CENTER);

        add(statsPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);

        loadDashboardData();
    }

    private JPanel createStatPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(title));

        if (title.equals("Employee Statistics")) {
            totalEmployeesLabel = new JLabel("Total Employees: Loading...");
            JLabel departmentsLabel = new JLabel("Total Departments: Loading...");
            JLabel avgSalaryLabel = new JLabel("Average Salary: Loading...");

            panel.add(totalEmployeesLabel);
            panel.add(Box.createVerticalStrut(10));
            panel.add(departmentsLabel);
            panel.add(Box.createVerticalStrut(10));
            panel.add(avgSalaryLabel);
        } else {
            todayScheduledLabel = new JLabel("Employees Scheduled Today: Loading...");
            JLabel absentLabel = new JLabel("Absent Today: Loading...");
            JLabel upcomingLabel = new JLabel("Upcoming Reviews: Loading...");

            panel.add(todayScheduledLabel);
            panel.add(Box.createVerticalStrut(10));
            panel.add(absentLabel);
            panel.add(Box.createVerticalStrut(10));
            panel.add(upcomingLabel);
        }

        return panel;
    }

    private void loadDashboardData() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            private int totalEmployees = 0;
            private int totalDepartments = 0;
            private double avgSalary = 0.0;
            private int scheduledToday = 0;

            @Override
            protected Void doInBackground() throws Exception {
                try (Connection conn = DatabaseConnection.getConnection()) {
                    String empQuery = "SELECT COUNT(*) FROM employees";
                    try (PreparedStatement pstmt = conn.prepareStatement(empQuery);
                         ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            totalEmployees = rs.getInt(1);
                        }
                    }

                    String deptQuery = "SELECT COUNT(*) FROM departments";
                    try (PreparedStatement pstmt = conn.prepareStatement(deptQuery);
                         ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            totalDepartments = rs.getInt(1);
                        }
                    }

                    String salaryQuery = "SELECT AVG(salary) FROM employees";
                    try (PreparedStatement pstmt = conn.prepareStatement(salaryQuery);
                         ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            avgSalary = rs.getDouble(1);
                        }
                    }

                    String scheduleQuery = "SELECT COUNT(*) FROM schedules WHERE shift_date = CURDATE()";
                    try (PreparedStatement pstmt = conn.prepareStatement(scheduleQuery);
                         ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            scheduledToday = rs.getInt(1);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    totalEmployees = 45;
                    totalDepartments = 5;
                    avgSalary = 3200.00;
                    scheduledToday = 15;
                }
                return null;
            }

            @Override
            protected void done() {
                totalEmployeesLabel.setText("Total Employees: " + totalEmployees);
                JLabel departmentsLabel = (JLabel) ((JPanel) totalEmployeesLabel.getParent()).getComponent(2);
                departmentsLabel.setText("Total Departments: " + totalDepartments);

                JLabel avgSalaryLabel = (JLabel) ((JPanel) totalEmployeesLabel.getParent()).getComponent(4);
                avgSalaryLabel.setText("Average Salary: $" + String.format("%.2f", avgSalary));

                todayScheduledLabel.setText("Employees Scheduled Today: " + scheduledToday);
                JLabel absentLabel = (JLabel) ((JPanel) todayScheduledLabel.getParent()).getComponent(2);
                absentLabel.setText("Absent Today: 2");

                JLabel upcomingLabel = (JLabel) ((JPanel) todayScheduledLabel.getParent()).getComponent(4);
                upcomingLabel.setText("Upcoming Reviews: 5");
            }
        };

        worker.execute();
    }
}

// Employee Panel
class EmployeePanel extends JPanel {
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> departmentFilterCombo;

    public EmployeePanel() {
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        searchPanel.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);

        searchPanel.add(new JLabel("Department:"));
        departmentFilterCombo = new JComboBox<>(new String[]{"All Departments", "Front Desk", "Housekeeping", "Food & Beverage", "Maintenance", "Management"});
        searchPanel.add(departmentFilterCombo);

        JButton searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        String[] columns = {"ID", "Name", "Department", "Position", "Hire Date", "Salary", "Contact", "Email"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(employeeTable);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Employee");
        JButton editButton = new JButton("Edit Employee");
        JButton deleteButton = new JButton("Delete Employee");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        addButton.addActionListener(e -> showEmployeeDialog(null));

        editButton.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow >= 0) {
                int empId = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    Employee employee = getEmployeeById(empId);
                    showEmployeeDialog(employee);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error fetching employee data: " + ex.getMessage(),
                            "Database Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select an employee to edit",
                        "Selection Required",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow >= 0) {
                int empId = (int) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this employee?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        deleteEmployee(empId);
                        loadEmployeeData();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Error deleting employee: " + ex.getMessage(),
                                "Database Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select an employee to delete",
                        "Selection Required",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        searchButton.addActionListener(e -> loadEmployeeData());
        departmentFilterCombo.addActionListener(e -> loadEmployeeData());
        searchField.addActionListener(e -> loadEmployeeData());

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadEmployeeData();
    }

    private void loadEmployeeData() {
        String searchTerm = searchField.getText().trim();
        String departmentFilter = (String) departmentFilterCombo.getSelectedItem();

        tableModel.setRowCount(0);

        SwingWorker<Void, Object[]> worker = new SwingWorker<Void, Object[]>() {
            @Override
            protected Void doInBackground() throws Exception {
                try (Connection conn = DatabaseConnection.getConnection()) {
                    StringBuilder queryBuilder = new StringBuilder();
                    queryBuilder.append("SELECT e.emp_id, e.first_name, e.last_name, d.dept_name, ");
                    queryBuilder.append("e.position, e.hire_date, e.salary, e.contact_number, e.email ");
                    queryBuilder.append("FROM employees e ");
                    queryBuilder.append("LEFT JOIN departments d ON e.dept_id = d.dept_id ");
                    queryBuilder.append("WHERE 1=1 ");

                    if (!searchTerm.isEmpty()) {
                        queryBuilder.append("AND (e.first_name LIKE ? OR e.last_name LIKE ? OR e.email LIKE ?) ");
                    }

                    if (departmentFilter != null && !departmentFilter.equals("All Departments")) {
                        queryBuilder.append("AND d.dept_name = ? ");
                    }

                    queryBuilder.append("ORDER BY e.emp_id");

                    try (PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString())) {
                        int paramIndex = 1;

                        if (!searchTerm.isEmpty()) {
                            String searchPattern = "%" + searchTerm + "%";
                            pstmt.setString(paramIndex++, searchPattern);
                            pstmt.setString(paramIndex++, searchPattern);
                            pstmt.setString(paramIndex++, searchPattern);
                        }

                        if (departmentFilter != null && !departmentFilter.equals("All Departments")) {
                            pstmt.setString(paramIndex, departmentFilter);
                        }

                        try (ResultSet rs = pstmt.executeQuery()) {
                            while (rs.next()) {
                                Object[] row = {
                                        rs.getInt("emp_id"),
                                        rs.getString("first_name") + " " + rs.getString("last_name"),
                                        rs.getString("dept_name"),
                                        rs.getString("position"),
                                        rs.getDate("hire_date"),
                                        rs.getDouble("salary"),
                                        rs.getString("contact_number"),
                                        rs.getString("email")
                                };
                                publish(row);
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    for (int i = 1; i <= 10; i++) {
                        Object[] row = {
                                i,
                                "Employee " + i,
                                i % 5 == 0 ? "Management" :
                                        i % 4 == 0 ? "Maintenance" :
                                                i % 3 == 0 ? "Food & Beverage" :
                                                        i % 2 == 0 ? "Housekeeping" : "Front Desk",
                                i % 3 == 0 ? "Manager" : i % 2 == 0 ? "Supervisor" : "Staff",
                                new java.sql.Date(System.currentTimeMillis() - (i * 30L * 24 * 60 * 60 * 1000)),
                                2000.0 + (i * 200),
                                "555-" + (1000 + i),
                                "employee" + i + "@hotel.com"
                        };
                        publish(row);
                    }
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Object[]> chunks) {
                for (Object[] row : chunks) {
                    tableModel.addRow(row);
                }
            }
        };

        worker.execute();
    }

    private Employee getEmployeeById(int empId) throws SQLException {
        Employee employee = new Employee();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM employees WHERE emp_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, empId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        employee.setEmpId(rs.getInt("emp_id"));
                        employee.setFirstName(rs.getString("first_name"));
                        employee.setLastName(rs.getString("last_name"));
                        employee.setDeptId(rs.getInt("dept_id"));
                        employee.setPosition(rs.getString("position"));
                        employee.setHireDate(rs.getDate("hire_date"));
                        employee.setSalary(rs.getDouble("salary"));
                        employee.setContactNumber(rs.getString("contact_number"));
                        employee.setEmail(rs.getString("email"));
                        employee.setAddress(rs.getString("address"));
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return employee;
    }

    private void deleteEmployee(int empId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String deletePerformanceQuery = "DELETE FROM performance WHERE emp_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deletePerformanceQuery)) {
                pstmt.setInt(1, empId);
                pstmt.executeUpdate();
            }

            String deleteSchedulesQuery = "DELETE FROM schedules WHERE emp_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSchedulesQuery)) {
                pstmt.setInt(1, empId);
                pstmt.executeUpdate();
            }

            String deleteEmployeeQuery = "DELETE FROM employees WHERE emp_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteEmployeeQuery)) {
                pstmt.setInt(1, empId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    private void showEmployeeDialog(Employee employee) {
        boolean isNewEmployee = (employee == null);
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this),
                isNewEmployee ? "Add New Employee" : "Edit Employee",
                true);

        if (employee == null) {
            employee = new Employee();
        }

        Employee finalEmployee = employee;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("First Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField firstNameField = new JTextField(finalEmployee.getFirstName(), 20);
        panel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Last Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JTextField lastNameField = new JTextField(finalEmployee.getLastName(), 20);
        panel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Department:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JComboBox<Department> departmentCombo = new JComboBox<>();
        panel.add(departmentCombo, gbc);

        loadDepartments(departmentCombo, finalEmployee.getDeptId());

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Position:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JTextField positionField = new JTextField(finalEmployee.getPosition(), 20);
        panel.add(positionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Hire Date:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        JTextField hireDateField = new JTextField(20);
        if (finalEmployee.getHireDate() != null) {
            hireDateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(finalEmployee.getHireDate()));
        } else {
            hireDateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
        panel.add(hireDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Salary:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        JTextField salaryField = new JTextField(finalEmployee.getSalary() > 0 ? String.valueOf(finalEmployee.getSalary()) : "", 20);
        panel.add(salaryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Contact Number:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        JTextField contactField = new JTextField(finalEmployee.getContactNumber(), 20);
        panel.add(contactField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        JTextField emailField = new JTextField(finalEmployee.getEmail(), 20);
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("Address:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        JTextField addressField = new JTextField(finalEmployee.getAddress(), 20);
        panel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);

        saveButton.addActionListener(e -> {
            try {
                if (firstNameField.getText().trim().isEmpty() ||
                        lastNameField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "First name and last name are required",
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Employee emp = new Employee();
                emp.setEmpId(finalEmployee.getEmpId());
                emp.setFirstName(firstNameField.getText().trim());
                emp.setLastName(lastNameField.getText().trim());

                Department selectedDept = (Department) departmentCombo.getSelectedItem();
                emp.setDeptId(selectedDept.getDeptId());

                emp.setPosition(positionField.getText().trim());

                try {
                    java.sql.Date hireDate = java.sql.Date.valueOf(hireDateField.getText().trim());
                    emp.setHireDate(hireDate);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Invalid date format. Please use YYYY-MM-DD",
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    if (!salaryField.getText().trim().isEmpty()) {
                        emp.setSalary(Double.parseDouble(salaryField.getText().trim()));
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Invalid salary format. Please enter a numeric value",
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                emp.setContactNumber(contactField.getText().trim());
                emp.setEmail(emailField.getText().trim());
                emp.setAddress(addressField.getText().trim());

                if (finalEmployee.getEmpId() == 0) {
                    addEmployee(emp);
                } else {
                    updateEmployee(emp);
                }

                loadEmployeeData();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Error saving employee: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void loadDepartments(JComboBox<Department> comboBox, int selectedDeptId) {
        comboBox.removeAllItems();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT dept_id, dept_name FROM departments ORDER BY dept_name";
            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {

                Department selectedDept = null;

                while (rs.next()) {
                    Department dept = new Department(
                            rs.getInt("dept_id"),
                            rs.getString("dept_name")
                    );
                    comboBox.addItem(dept);

                    if (dept.getDeptId() == selectedDeptId) {
                        selectedDept = dept;
                    }
                }

                if (selectedDept != null) {
                    comboBox.setSelectedItem(selectedDept);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Department[] dummyDepts = {
                    new Department(1, "Front Desk"),
                    new Department(2, "Housekeeping"),
                    new Department(3, "Food & Beverage"),
                    new Department(4, "Maintenance"),
                    new Department(5, "Management")
            };

            Department selectedDept = null;

            for (Department dept : dummyDepts) {
                comboBox.addItem(dept);
                if (dept.getDeptId() == selectedDeptId) {
                    selectedDept = dept;
                }
            }

            if (selectedDept != null) {
                comboBox.setSelectedItem(selectedDept);
            }
        }
    }

    private void addEmployee(Employee employee) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO employees (first_name, last_name, dept_id, position, " +
                    "hire_date, salary, contact_number, email, address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, employee.getFirstName());
                pstmt.setString(2, employee.getLastName());
                pstmt.setInt(3, employee.getDeptId());
                pstmt.setString(4, employee.getPosition());
                pstmt.setDate(5, employee.getHireDate());
                pstmt.setDouble(6, employee.getSalary());
                pstmt.setString(7, employee.getContactNumber());
                pstmt.setString(8, employee.getEmail());
                pstmt.setString(9, employee.getAddress());

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    private void updateEmployee(Employee employee) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE employees SET first_name = ?, last_name = ?, dept_id = ?, " +
                    "position = ?, hire_date = ?, salary = ?, contact_number = ?, " +
                    "email = ?, address = ? WHERE emp_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, employee.getFirstName());
                pstmt.setString(2, employee.getLastName());
                pstmt.setInt(3, employee.getDeptId());
                pstmt.setString(4, employee.getPosition());
                pstmt.setDate(5, employee.getHireDate());
                pstmt.setDouble(6, employee.getSalary());
                pstmt.setString(7, employee.getContactNumber());
                pstmt.setString(8, employee.getEmail());
                pstmt.setString(9, employee.getAddress());
                pstmt.setInt(10, employee.getEmpId());

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
}

// Department Panel
class DepartmentPanel extends JPanel {
    private JTable departmentTable;
    private DefaultTableModel tableModel;

    public DepartmentPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Department Name", "Description", "Employee Count"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        departmentTable = new JTable(tableModel);
        departmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(departmentTable);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton addButton = new JButton("Add Department");
        JButton editButton = new JButton("Edit Department");
        JButton deleteButton = new JButton("Delete Department");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        addButton.addActionListener(e -> showDepartmentDialog(null));

        editButton.addActionListener(e -> {
            int selectedRow = departmentTable.getSelectedRow();
            if (selectedRow >= 0) {
                int deptId = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    Department department = getDepartmentById(deptId);
                    showDepartmentDialog(department);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error fetching department data: " + ex.getMessage(),
                            "Database Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a department to edit",
                        "Selection Required",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = departmentTable.getSelectedRow();
            if (selectedRow >= 0) {
                int deptId = (int) tableModel.getValueAt(selectedRow, 0);
                int employeeCount = (int) tableModel.getValueAt(selectedRow, 3);

                if (employeeCount > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Cannot delete department with employees. Reassign employees first.",
                            "Delete Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this department?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        deleteDepartment(deptId);
                        loadDepartmentData();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Error deleting department: " + ex.getMessage(),
                                "Database Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a department to delete",
                        "Selection Required",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadDepartmentData();
    }

    private void loadDepartmentData() {
        tableModel.setRowCount(0);

        SwingWorker<Void, Object[]> worker = new SwingWorker<Void, Object[]>() {
            @Override
            protected Void doInBackground() throws Exception {
                try (Connection conn = DatabaseConnection.getConnection()) {
                    String query = "SELECT d.dept_id, d.dept_name, d.description, " +
                            "(SELECT COUNT(*) FROM employees e WHERE e.dept_id = d.dept_id) as emp_count " +
                            "FROM departments d ORDER BY d.dept_id";

                    try (PreparedStatement pstmt = conn.prepareStatement(query);
                         ResultSet rs = pstmt.executeQuery()) {

                        while (rs.next()) {
                            Object[] row = {
                                    rs.getInt("dept_id"),
                                    rs.getString("dept_name"),
                                    rs.getString("description"),
                                    rs.getInt("emp_count")
                            };
                            publish(row);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    Object[][] dummyData = {
                            {1, "Front Desk", "Handles check-ins, check-outs, and guest inquiries", 10},
                            {2, "Housekeeping", "Maintains cleanliness of rooms and public areas", 15},
                            {3, "Food & Beverage", "Manages restaurant, room service, and catering", 12},
                            {4, "Maintenance", "Handles repairs and facility upkeep", 5},
                            {5, "Management", "Oversees hotel operations", 3}
                    };

                    for (Object[] row : dummyData) {
                        publish(row);
                    }
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Object[]> chunks) {
                for (Object[] row : chunks) {
                    tableModel.addRow(row);
                }
            }
        };

        worker.execute();
    }

    private Department getDepartmentById(int deptId) throws SQLException {
        Department department = new Department();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM departments WHERE dept_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, deptId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        department.setDeptId(rs.getInt("dept_id"));
                        department.setDeptName(rs.getString("dept_name"));
                        department.setDescription(rs.getString("description"));
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return department;
    }

    private void deleteDepartment(int deptId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM departments WHERE dept_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, deptId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    private void showDepartmentDialog(Department department) {
        boolean isNewDepartment = (department == null);
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this),
                isNewDepartment ? "Add New Department" : "Edit Department",
                true);

        if (department == null) {
            department = new Department();
        }

        Department finalDepartment = department;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Department Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField nameField = new JTextField(finalDepartment.getDeptName(), 20);
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JTextArea descriptionArea = new JTextArea(finalDepartment.getDescription(), 5, 20);
        descriptionArea.setLineWrap(true);
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        panel.add(descScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);

        saveButton.addActionListener(e -> {
            try {
                if (nameField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Department name is required",
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Department dept = new Department();
                dept.setDeptId(finalDepartment.getDeptId());
                dept.setDeptName(nameField.getText().trim());
                dept.setDescription(descriptionArea.getText().trim());

                if (finalDepartment.getDeptId() == 0) {
                    addDepartment(dept);
                } else {
                    updateDepartment(dept);
                }

                loadDepartmentData();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Error saving department: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void addDepartment(Department department) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO departments (dept_name, description) VALUES (?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, department.getDeptName());
                pstmt.setString(2, department.getDescription());

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    private void updateDepartment(Department department) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE departments SET dept_name = ?, description = ? WHERE dept_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, department.getDeptName());
                pstmt.setString(2, department.getDescription());
                pstmt.setInt(3, department.getDeptId());

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
}

// Schedule Panel
class SchedulePanel extends JPanel {
    private JTable scheduleTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> filterCombo;
    private JDateChooser dateChooser;

    public SchedulePanel() {
        setLayout(new BorderLayout());

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        filterPanel.add(new JLabel("Filter by:"));
        filterCombo = new JComboBox<>(new String[]{"All Employees", "Today's Shifts", "This Week", "Custom Date"});
        filterPanel.add(filterCombo);

        filterPanel.add(new JLabel("Date:"));
        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());
        dateChooser.setEnabled(false);
        filterPanel.add(dateChooser);

        JButton applyFilterButton = new JButton("Apply Filter");
        filterPanel.add(applyFilterButton);

        String[] columns = {"ID", "Employee", "Department", "Date", "Start Time", "End Time", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        scheduleTable = new JTable(tableModel);
        scheduleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(scheduleTable);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Schedule");
        JButton editButton = new JButton("Edit Schedule");
        JButton deleteButton = new JButton("Delete Schedule");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        filterCombo.addActionListener(e -> {
            String selectedFilter = (String) filterCombo.getSelectedItem();
            dateChooser.setEnabled(selectedFilter.equals("Custom Date"));
        });

        applyFilterButton.addActionListener(e -> loadScheduleData());

        addButton.addActionListener(e -> showScheduleDialog(null));

        editButton.addActionListener(e -> {
            int selectedRow = scheduleTable.getSelectedRow();
            if (selectedRow >= 0) {
                int scheduleId = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    Schedule schedule = getScheduleById(scheduleId);
                    showScheduleDialog(schedule);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error fetching schedule data: " + ex.getMessage(),
                            "Database Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a schedule to edit",
                        "Selection Required",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = scheduleTable.getSelectedRow();
            if (selectedRow >= 0) {
                int scheduleId = (int) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this schedule?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        deleteSchedule(scheduleId);
                        loadScheduleData();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Error deleting schedule: " + ex.getMessage(),
                                "Database Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a schedule to delete",
                        "Selection Required",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        add(filterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadScheduleData();
    }

    private void loadScheduleData() {
        tableModel.setRowCount(0);

        String filterType = (String) filterCombo.getSelectedItem();
        Date filterDate = dateChooser.getDate();

        SwingWorker<Void, Object[]> worker = new SwingWorker<Void, Object[]>() {
            @Override
            protected Void doInBackground() throws Exception {
                try (Connection conn = DatabaseConnection.getConnection()) {
                    StringBuilder queryBuilder = new StringBuilder();
                    queryBuilder.append("SELECT s.schedule_id, e.first_name, e.last_name, d.dept_name, ");
                    queryBuilder.append("s.shift_date, s.start_time, s.end_time, s.status ");
                    queryBuilder.append("FROM schedules s ");
                    queryBuilder.append("JOIN employees e ON s.emp_id = e.emp_id ");
                    queryBuilder.append("JOIN departments d ON e.dept_id = d.dept_id ");
                    queryBuilder.append("WHERE 1=1 ");

                    if (filterType.equals("Today's Shifts")) {
                        queryBuilder.append("AND s.shift_date = CURDATE() ");
                    } else if (filterType.equals("This Week")) {
                        queryBuilder.append("AND YEARWEEK(s.shift_date, 1) = YEARWEEK(CURDATE(), 1) ");
                    } else if (filterType.equals("Custom Date") && filterDate != null) {
                        queryBuilder.append("AND s.shift_date = ? ");
                    }

                    queryBuilder.append("ORDER BY s.shift_date, s.start_time");

                    try (PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString())) {
                        if (filterType.equals("Custom Date") && filterDate != null) {
                            pstmt.setDate(1, new java.sql.Date(filterDate.getTime()));
                        }

                        try (ResultSet rs = pstmt.executeQuery()) {
                            while (rs.next()) {
                                Object[] row = {
                                        rs.getInt("schedule_id"),
                                        rs.getString("first_name") + " " + rs.getString("last_name"),
                                        rs.getString("dept_name"),
                                        rs.getDate("shift_date"),
                                        rs.getTime("start_time"),
                                        rs.getTime("end_time"),
                                        rs.getString("status")
                                };
                                publish(row);
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    Date today = new Date();

                    for (int i = 1; i <= 10; i++) {
                        Object[] row = {
                                i,
                                "Employee " + i,
                                i % 5 == 0 ? "Management" :
                                        i % 4 == 0 ? "Maintenance" :
                                                i % 3 == 0 ? "Food & Beverage" :
                                                        i % 2 == 0 ? "Housekeeping" : "Front Desk",
                                new java.sql.Date(today.getTime()),
                                java.sql.Time.valueOf("08:00:00"),
                                java.sql.Time.valueOf("16:00:00"),
                                i % 4 == 0 ? "Completed" :
                                        i % 3 == 0 ? "Late" :
                                                i % 2 == 0 ? "Absent" : "Scheduled"
                        };
                        publish(row);
                    }
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Object[]> chunks) {
                for (Object[] row : chunks) {
                    tableModel.addRow(row);
                }
            }
        };

        worker.execute();
    }

    private Schedule getScheduleById(int scheduleId) throws SQLException {
        Schedule schedule = new Schedule();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM schedules WHERE schedule_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, scheduleId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        schedule.setScheduleId(rs.getInt("schedule_id"));
                        schedule.setEmpId(rs.getInt("emp_id"));
                        schedule.setShiftDate(rs.getDate("shift_date"));
                        schedule.setStartTime(rs.getTime("start_time"));
                        schedule.setEndTime(rs.getTime("end_time"));
                        schedule.setStatus(rs.getString("status"));
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return schedule;
    }

    private void deleteSchedule(int scheduleId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM schedules WHERE schedule_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, scheduleId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    private void showScheduleDialog(Schedule schedule) {
        boolean isNewSchedule = (schedule == null);
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this),
                isNewSchedule ? "Add New Schedule" : "Edit Schedule",
                true);

        if (schedule == null) {
            schedule = new Schedule();
            schedule.setShiftDate(new java.sql.Date(System.currentTimeMillis()));
            schedule.setStartTime(java.sql.Time.valueOf("09:00:00"));
            schedule.setEndTime(java.sql.Time.valueOf("17:00:00"));
            schedule.setStatus("Scheduled");
        }

        Schedule finalSchedule = schedule;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Employee
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Employee:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JComboBox<Employee> employeeCombo = new JComboBox<>();
        panel.add(employeeCombo, gbc);

        loadEmployees(employeeCombo, finalSchedule.getEmpId());

        // Date
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Date:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JDateChooser shiftDateChooser = new JDateChooser();
        shiftDateChooser.setDate(finalSchedule.getShiftDate());
        panel.add(shiftDateChooser, gbc);

        // Start Time
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Start Time:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JSpinner startTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm:ss");
        startTimeSpinner.setEditor(startTimeEditor);
        if (finalSchedule.getStartTime() != null) {
            startTimeSpinner.setValue(new Date(finalSchedule.getStartTime().getTime()));
        } else {
            startTimeSpinner.setValue(new Date());
        }
        panel.add(startTimeSpinner, gbc);

        // End Time
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("End Time:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JSpinner endTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm:ss");
        endTimeSpinner.setEditor(endTimeEditor);
        if (finalSchedule.getEndTime() != null) {
            endTimeSpinner.setValue(new Date(finalSchedule.getEndTime().getTime()));
        } else {
            endTimeSpinner.setValue(new Date());
        }
        panel.add(endTimeSpinner, gbc);

        // Status
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Scheduled", "Completed", "Absent", "Late"});
        statusCombo.setSelectedItem(finalSchedule.getStatus());
        panel.add(statusCombo, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);

        saveButton.addActionListener(e -> {
            try {
                Employee selectedEmployee = (Employee) employeeCombo.getSelectedItem();
                if (selectedEmployee == null) {
                    JOptionPane.showMessageDialog(dialog, "Please select an employee", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                finalSchedule.setEmpId(selectedEmployee.getEmpId());
                finalSchedule.setShiftDate(new java.sql.Date(shiftDateChooser.getDate().getTime()));
                Date startTimeDate = (Date) startTimeSpinner.getValue();
                finalSchedule.setStartTime(new java.sql.Time(startTimeDate.getTime()));
                Date endTimeDate = (Date) endTimeSpinner.getValue();
                finalSchedule.setEndTime(new java.sql.Time(endTimeDate.getTime()));
                finalSchedule.setStatus((String) statusCombo.getSelectedItem());

                if (isNewSchedule) {
                    addSchedule(finalSchedule);
                } else {
                    updateSchedule(finalSchedule);
                }
                loadScheduleData();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error saving schedule: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void loadEmployees(JComboBox<Employee> combo, int selectedEmpId) {
        combo.removeAllItems();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT emp_id, first_name, last_name FROM employees ORDER BY first_name";
            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                Employee selectedEmployee = null;
                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setEmpId(rs.getInt("emp_id"));
                    emp.setFirstName(rs.getString("first_name"));
                    emp.setLastName(rs.getString("last_name"));
                    combo.addItem(emp);
                    if (emp.getEmpId() == selectedEmpId) {
                        selectedEmployee = emp;
                    }
                }
                if (selectedEmployee != null) {
                    combo.setSelectedItem(selectedEmployee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Employee dummy = new Employee();
            dummy.setEmpId(1);
            dummy.setFirstName("John");
            dummy.setLastName("Doe");
            combo.addItem(dummy);
            combo.setSelectedItem(dummy);
        }
    }

    private void addSchedule(Schedule schedule) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO schedules (emp_id, shift_date, start_time, end_time, status) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, schedule.getEmpId());
                pstmt.setDate(2, schedule.getShiftDate());
                pstmt.setTime(3, schedule.getStartTime());
                pstmt.setTime(4, schedule.getEndTime());
                pstmt.setString(5, schedule.getStatus());
                pstmt.executeUpdate();
            }
        }
    }

    private void updateSchedule(Schedule schedule) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE schedules SET emp_id = ?, shift_date = ?, start_time = ?, end_time = ?, status = ? WHERE schedule_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, schedule.getEmpId());
                pstmt.setDate(2, schedule.getShiftDate());
                pstmt.setTime(3, schedule.getStartTime());
                pstmt.setTime(4, schedule.getEndTime());
                pstmt.setString(5, schedule.getStatus());
                pstmt.setInt(6, schedule.getScheduleId());
                pstmt.executeUpdate();
            }
        }
    }
}

// Performance Panel
class PerformancePanel extends JPanel {
    private JTable performanceTable;
    private DefaultTableModel tableModel;

    public PerformancePanel() {
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Employee", "Date", "Rating", "Comments", "Reviewer"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        performanceTable = new JTable(tableModel);
        performanceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(performanceTable);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Review");
        JButton editButton = new JButton("Edit Review");
        JButton deleteButton = new JButton("Delete Review");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        addButton.addActionListener(e -> showPerformanceDialog(null));
        editButton.addActionListener(e -> {
            int selectedRow = performanceTable.getSelectedRow();
            if (selectedRow >= 0) {
                int perfId = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    Performance review = getPerformanceById(perfId);
                    showPerformanceDialog(review);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error fetching performance review: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a review to edit", "Selection Required", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = performanceTable.getSelectedRow();
            if (selectedRow >= 0) {
                int perfId = (int) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this review?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        deletePerformance(perfId);
                        loadPerformanceData();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Error deleting review: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a review to delete", "Selection Required", JOptionPane.WARNING_MESSAGE);
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadPerformanceData();
    }

    private void loadPerformanceData() {
        tableModel.setRowCount(0);
        SwingWorker<Void, Object[]> worker = new SwingWorker<Void, Object[]>() {
            @Override
            protected Void doInBackground() throws Exception {
                try (Connection conn = DatabaseConnection.getConnection()) {
                    String query = "SELECT p.perf_id, e.first_name, e.last_name, p.review_date, p.rating, p.comments, p.reviewer " +
                            "FROM performance p JOIN employees e ON p.emp_id = e.emp_id ORDER BY p.review_date DESC";
                    try (PreparedStatement pstmt = conn.prepareStatement(query);
                         ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            Object[] row = {
                                    rs.getInt("perf_id"),
                                    rs.getString("first_name") + " " + rs.getString("last_name"),
                                    rs.getDate("review_date"),
                                    rs.getInt("rating"),
                                    rs.getString("comments"),
                                    rs.getString("reviewer")
                            };
                            publish(row);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    for (int i = 1; i <= 5; i++) {
                        Object[] row = {
                                i,
                                "Employee " + i,
                                new java.sql.Date(System.currentTimeMillis() - i * 86400000L),
                                3 + (i % 3),
                                "Review comments " + i,
                                "Manager " + i
                        };
                        publish(row);
                    }
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Object[]> chunks) {
                for (Object[] row : chunks) {
                    tableModel.addRow(row);
                }
            }
        };

        worker.execute();
    }

    private Performance getPerformanceById(int perfId) throws SQLException {
        Performance performance = new Performance();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM performance WHERE perf_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, perfId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        performance.setPerfId(rs.getInt("perf_id"));
                        performance.setEmpId(rs.getInt("emp_id"));
                        performance.setReviewDate(rs.getDate("review_date"));
                        performance.setRating(rs.getInt("rating"));
                        performance.setComments(rs.getString("comments"));
                        performance.setReviewer(rs.getString("reviewer"));
                    }
                }
            }
        }
        return performance;
    }

    private void deletePerformance(int perfId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM performance WHERE perf_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, perfId);
                pstmt.executeUpdate();
            }
        }
    }

    private void showPerformanceDialog(Performance performance) {
        boolean isNewReview = (performance == null);
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this),
                isNewReview ? "Add New Review" : "Edit Review", true);

        if (performance == null) {
            performance = new Performance();
            performance.setReviewDate(new java.sql.Date(System.currentTimeMillis()));
        }

        Performance finalPerformance = performance;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Employee:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JComboBox<Employee> employeeCombo = new JComboBox<>();
        panel.add(employeeCombo, gbc);
        loadEmployeesForPerformance(employeeCombo, finalPerformance.getEmpId());

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Review Date:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JDateChooser reviewDateChooser = new JDateChooser();
        reviewDateChooser.setDate(finalPerformance.getReviewDate());
        panel.add(reviewDateChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Rating:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JComboBox<Integer> ratingCombo = new JComboBox<>(new Integer[]{1,2,3,4,5});
        ratingCombo.setSelectedItem(finalPerformance.getRating());
        panel.add(ratingCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Comments:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JTextArea commentsArea = new JTextArea(finalPerformance.getComments(), 5, 20);
        commentsArea.setLineWrap(true);
        JScrollPane commentsScroll = new JScrollPane(commentsArea);
        panel.add(commentsScroll, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Reviewer:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        JTextField reviewerField = new JTextField(finalPerformance.getReviewer(), 20);
        panel.add(reviewerField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);

        saveButton.addActionListener(e -> {
            try {
                Employee selectedEmployee = (Employee) employeeCombo.getSelectedItem();
                if (selectedEmployee == null) {
                    JOptionPane.showMessageDialog(dialog, "Please select an employee", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                finalPerformance.setEmpId(selectedEmployee.getEmpId());
                finalPerformance.setReviewDate(new java.sql.Date(reviewDateChooser.getDate().getTime()));
                finalPerformance.setRating((Integer) ratingCombo.getSelectedItem());
                finalPerformance.setComments(commentsArea.getText().trim());
                finalPerformance.setReviewer(reviewerField.getText().trim());

                if (isNewReview) {
                    addPerformance(finalPerformance);
                } else {
                    updatePerformance(finalPerformance);
                }
                loadPerformanceData();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error saving review: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void loadEmployeesForPerformance(JComboBox<Employee> combo, int selectedEmpId) {
        combo.removeAllItems();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT emp_id, first_name, last_name FROM employees ORDER BY first_name";
            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                Employee selectedEmployee = null;
                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setEmpId(rs.getInt("emp_id"));
                    emp.setFirstName(rs.getString("first_name"));
                    emp.setLastName(rs.getString("last_name"));
                    combo.addItem(emp);
                    if (emp.getEmpId() == selectedEmpId) {
                        selectedEmployee = emp;
                    }
                }
                if (selectedEmployee != null) {
                    combo.setSelectedItem(selectedEmployee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Employee dummy = new Employee();
            dummy.setEmpId(1);
            dummy.setFirstName("John");
            dummy.setLastName("Doe");
            combo.addItem(dummy);
            combo.setSelectedItem(dummy);
        }
    }

    private void addPerformance(Performance performance) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO performance (emp_id, review_date, rating, comments, reviewer) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, performance.getEmpId());
                pstmt.setDate(2, performance.getReviewDate());
                pstmt.setInt(3, performance.getRating());
                pstmt.setString(4, performance.getComments());
                pstmt.setString(5, performance.getReviewer());
                pstmt.executeUpdate();
            }
        }
    }

    private void updatePerformance(Performance performance) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE performance SET emp_id = ?, review_date = ?, rating = ?, comments = ?, reviewer = ? WHERE perf_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, performance.getEmpId());
                pstmt.setDate(2, performance.getReviewDate());
                pstmt.setInt(3, performance.getRating());
                pstmt.setString(4, performance.getComments());
                pstmt.setString(5, performance.getReviewer());
                pstmt.setInt(6, performance.getPerfId());
                pstmt.executeUpdate();
            }
        }
    }
}

// POJO Classes

class Employee {
    private int empId;
    private String firstName;
    private String lastName;
    private int deptId;
    private String position;
    private java.sql.Date hireDate;
    private double salary;
    private String contactNumber;
    private String email;
    private String address;

    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }
    public String getFirstName() { return firstName == null ? "" : firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName == null ? "" : lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public int getDeptId() { return deptId; }
    public void setDeptId(int deptId) { this.deptId = deptId; }
    public String getPosition() { return position == null ? "" : position; }
    public void setPosition(String position) { this.position = position; }
    public java.sql.Date getHireDate() { return hireDate; }
    public void setHireDate(java.sql.Date hireDate) { this.hireDate = hireDate; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public String getContactNumber() { return contactNumber == null ? "" : contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public String getEmail() { return email == null ? "" : email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address == null ? "" : address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}

class Department {
    private int deptId;
    private String deptName;
    private String description;

    public Department() {}
    public Department(int deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
    }

    public int getDeptId() { return deptId; }
    public void setDeptId(int deptId) { this.deptId = deptId; }
    public String getDeptName() { return deptName == null ? "" : deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }
    public String getDescription() { return description == null ? "" : description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return getDeptName();
    }
}

class Schedule {
    private int scheduleId;
    private int empId;
    private java.sql.Date shiftDate;
    private java.sql.Time startTime;
    private java.sql.Time endTime;
    private String status;

    public int getScheduleId() { return scheduleId; }
    public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }
    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }
    public java.sql.Date getShiftDate() { return shiftDate; }
    public void setShiftDate(java.sql.Date shiftDate) { this.shiftDate = shiftDate; }
    public java.sql.Time getStartTime() { return startTime; }
    public void setStartTime(java.sql.Time startTime) { this.startTime = startTime; }
    public java.sql.Time getEndTime() { return endTime; }
    public void setEndTime(java.sql.Time endTime) { this.endTime = endTime; }
    public String getStatus() { return status == null ? "" : status; }
    public void setStatus(String status) { this.status = status; }
}

class Performance {
    private int perfId;
    private int empId;
    private java.sql.Date reviewDate;
    private int rating;
    private String comments;
    private String reviewer;

    public int getPerfId() { return perfId; }
    public void setPerfId(int perfId) { this.perfId = perfId; }
    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }
    public java.sql.Date getReviewDate() { return reviewDate; }
    public void setReviewDate(java.sql.Date reviewDate) { this.reviewDate = reviewDate; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComments() { return comments == null ? "" : comments; }
    public void setComments(String comments) { this.comments = comments; }
    public String getReviewer() { return reviewer == null ? "" : reviewer; }
    public void setReviewer(String reviewer) { this.reviewer = reviewer; }
}
