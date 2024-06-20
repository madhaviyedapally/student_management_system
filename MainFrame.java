package view;

import controller.StudentController;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame {
    private StudentController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    public MainFrame() {
        controller = new StudentController();
        setTitle("Student Management System");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set up the table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Age", "Course"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Set up the form
        JPanel formPanel = new JPanel(new GridLayout(8, 5));
        formPanel.add(new JLabel("ID:"));
        JTextField idField = new JTextField();
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);
        formPanel.add(new JLabel("Age:"));
        JTextField ageField = new JTextField();
        formPanel.add(ageField);
        formPanel.add(new JLabel("Course:"));
        JTextField courseField = new JTextField();
        formPanel.add(courseField);
        JButton addButton = new JButton("Add");
        formPanel.add(addButton);
        JButton updateButton = new JButton("Update");
        formPanel.add(updateButton);
        JButton deleteButton = new JButton("Delete");
        formPanel.add(deleteButton);
        JButton searchButton = new JButton("Search");
        formPanel.add(searchButton);
        JButton displayAllButton = new JButton("Display All");
        formPanel.add(displayAllButton);

        add(formPanel, BorderLayout.NORTH);

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput(idField, nameField, ageField, courseField)) {
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String course = courseField.getText();
                    controller.addStudent(new Student(id, name, age, course));
                    refreshTable();
                    clearFields(idField, nameField, ageField, courseField);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput(idField, nameField, ageField, courseField)) {
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String course = courseField.getText();
                    controller.updateStudent(id, new Student(id, name, age, course));
                    refreshTable();
                    clearFields(idField, nameField, ageField, courseField);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!idField.getText().isEmpty()) {
                    int id = Integer.parseInt(idField.getText());
                    controller.deleteStudent(id);
                    refreshTable();
                    clearFields(idField, nameField, ageField, courseField);
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "ID field is required for deletion.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!idField.getText().isEmpty()) {
                    int id = Integer.parseInt(idField.getText());
                    Student student = controller.getStudentById(id);
                    if (student != null) {
                        nameField.setText(student.getName());
                        ageField.setText(String.valueOf(student.getAge()));
                        courseField.setText(student.getCourse());
                    } else {
                        JOptionPane.showMessageDialog(MainFrame.this, "Student not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "ID field is required for searching.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        displayAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });

        refreshTable();
    }

    private void refreshTable() {
        List<Student> students = controller.getAllStudents();
        tableModel.setRowCount(0);
        for (Student student : students) {
            tableModel.addRow(new Object[]{student.getId(), student.getName(), student.getAge(), student.getCourse()});
        }
    }

    private boolean validateInput(JTextField idField, JTextField nameField, JTextField ageField, JTextField courseField) {
        if (idField.getText().isEmpty() || nameField.getText().isEmpty() || ageField.getText().isEmpty() || courseField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(MainFrame.this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(idField.getText());
            Integer.parseInt(ageField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(MainFrame.this, "ID and Age must be integers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearFields(JTextField idField, JTextField nameField, JTextField ageField, JTextField courseField) {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        courseField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
