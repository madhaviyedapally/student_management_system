package controller;

import model.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
    private List<Student> students;
    private static final String FILE_PATH = "students.txt";

    public StudentController() {
        students = new ArrayList<>();
        readStudentsFromFile();
    }

    public void addStudent(Student student) {
        students.add(student);
        writeStudentsToFile();
    }

    public void updateStudent(int id, Student updatedStudent) {
        for (Student student : students) {
            if (student.getId() == id) {
                student.setName(updatedStudent.getName());
                student.setAge(updatedStudent.getAge());
                student.setCourse(updatedStudent.getCourse());
                break;
            }
        }
        writeStudentsToFile();
    }

    public void deleteStudent(int id) {
        students.removeIf(student -> student.getId() == id);
        writeStudentsToFile();
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public Student getStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    private void readStudentsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int age = Integer.parseInt(data[2]);
                String course = data[3];
                students.add(new Student(id, name, age, course));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeStudentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Student student : students) {
                writer.write(student.getId() + "," + student.getName() + "," + student.getAge() + "," + student.getCourse());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
