
package com.mycompany.library;

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Library {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Student> students = readStudentFunction();
        // Add some students to the ArrayList here

        ArrayList<Book> books = readBookFunction();
        // Add some books to the ArrayList here
        String studentFirstName = "";
        String authorFirstName = "";
        while (true) {
            System.out.println("-------------MAIN-MENU------------");
            System.out.println("1. Search Book By Author First Name");
            System.out.println("2. List All Books Author First Name Alphabetically");
            System.out.println("3. Search Student By Name");
            System.out.println("4. Display Students Sorted By Names");
            System.out.println("5. Borrow a Book ");
            System.out.println("6. Return a Book");
            System.out.println("7. Get Books Borrowed by a Student");
            System.out.println("8. Exit");
            System.out.println("YourChoice:");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character left by nextInt()
            switch (option) {
                case 1:
                    ArrayList<Book> sortedBooksData = sortBookByAuthorFirstName(books);
                    System.out.println("Please enter the First Name of Author for the Book you want to searc: ");
                    authorFirstName = scanner.nextLine();
                    System.out.println("------------------------");
                    Book foundBook = searchBookByAuthorFirstName(sortedBooksData, authorFirstName);
                    if (foundBook == null) {
                        System.out.println("Book not found!");
                    } else {
                        System.out.println("Book found ");
                        System.out.println("Author' Name: " + (foundBook.getAuthorFirstName()) + " " + foundBook.getAuthorLastName());
                        System.out.println("Title: " + (foundBook.getBookTitle()));
                        System.out.println("Genre: " + (foundBook.getGenre()));
                    }
                    break;

                case 2:
                    System.out.println("------------------------");
                    System.out.println("Displaying Books Sorted By Author Name Alphabetically");
                    System.out.println("------------------------");
                    ArrayList<Book> sortedBooks = sortBookByAuthorFirstName(books);
                    for (Book sortedBook : sortedBooks) {
                        System.out.println("Authors Name: " + sortedBook.getAuthorFirstName() + " " + sortedBook.getAuthorLastName() + " || Title: " + sortedBook.getBookTitle());
                    }
                    break;
                    
                case 3:
                    ArrayList<Student> sortedStudents = sortStudentByFirstName(students);
                    System.out.println("Please enter the first name of the student you want to search for: ");
                    studentFirstName = scanner.nextLine();
                    System.out.println("------------------------");
                    Student foundStudent = searchStudentByFirstName(sortedStudents, studentFirstName);
                    if (foundStudent == null) {
                        System.out.println("Student not found!");
                    } else {
                        System.out.println("Student found ");
                        System.out.println("Full Name: " + (foundStudent.getFirstName()) + " " + foundStudent.getLastName());
                        System.out.println("Email: " + (foundStudent.getEmail()));
                        System.out.println("Number: " + (foundStudent.getPhoneNumber()));
                    }
                    break;
                    
                case 4:
                    System.out.println("------------------------");
                    System.out.println("Displaying Students Sorted By there First Names Alphabetically");
                    System.out.println("------------------------");
                    ArrayList<Student> sortedStudentsByFirstName = sortStudentByFirstName(students);
                    for (Student sortedStudent : sortedStudentsByFirstName) {
                        System.out.println("Name: " + sortedStudent.firstName + ' ' + sortedStudent.lastName + " || Email: " + sortedStudent.getEmail() + " || Phone Number: " + sortedStudent.getPhoneNumber());
                    }
                    break;
                    
                case 5:
                    // Borrow a book
                    System.out.println("------------------------");
                    System.out.println("Borrowing a Book");
                    System.out.println("------------------------");
                    System.out.println("Enter student first name whom is borrowing the book: ");
                    studentFirstName = scanner.nextLine();
                    System.out.println("Enter the First Name of Author of the book being borrowed: ");
                    authorFirstName = scanner.nextLine();

                    Student borrower = searchStudentByFirstName(sortStudentByFirstName(students), studentFirstName);

                    Book bookToBorrow = searchBookByAuthorFirstName(sortBookByAuthorFirstName(books), authorFirstName);

                    if (borrower != null && bookToBorrow != null) {
                        if (bookToBorrow.isBorrowed()) {
                            System.out.println("The book is currently borrowed. You have been added to the waiting list !");
                            bookToBorrow.getWaitingList().add(borrower);
                            break;
                        }

                        // Otherwise, borrow the book and set the borrower to the current student
                        bookToBorrow.setBorrowed(true);
                        bookToBorrow.setBorrower(borrower);
                        borrower.setBorrowedBooks(bookToBorrow);
                        borrower.setBorrowed(true);
                        System.out.println("Book borrowed successfully !");
                        break;
                    } else {
                        System.out.println("Invalid student or book ID !");
                    }

                    break;

                case 6: // Return book
                    System.out.println("------------------------");
                    System.out.println("Returning a Book");
                    System.out.println("------------------------");
                    System.out.println("Enter student First Name Whom is returning:");
                    studentFirstName = scanner.nextLine();
                    System.out.println("Enter book Author's First Name:");
                    authorFirstName = scanner.nextLine();
                    System.out.println("------------------------");
                    Student returner = searchStudentByFirstName(sortStudentByFirstName(students), studentFirstName);

                    Book bookToReturn = searchBookByAuthorFirstName(sortBookByAuthorFirstName(books), authorFirstName);

                    if (returner != null && bookToReturn != null) {
                        if (!bookToReturn.isBorrowed()) {
                            System.out.println("This book is not borrowed.");
                        } else if (!bookToReturn.getBorrower().getFirstName().equals(studentFirstName)) {
                            System.out.println("This book is borrowed by another student.");
                        } else {
                            bookToReturn.setBorrowed(false);
                            bookToReturn.setBorrower(null);
                            returner.getBorrowedBooks().remove(bookToReturn);
                            System.out.println("Book returned successfully !");

                            // Check waiting list and reassign borrower
                            ArrayList<Student> waitingList = bookToReturn.getWaitingList();
                            if (!waitingList.isEmpty()) {
                                Student firstStudent = waitingList.remove(0);
                                bookToReturn.setBorrowed(true);
                                bookToReturn.setBorrower(firstStudent);
                                firstStudent.setBorrowedBooks(bookToReturn);
                                System.out.println("This Book is now borrowed by " + firstStudent.getFirstName() + " " + firstStudent.getLastName());
                            }
                        }
                    } else {
                        System.out.println("Invalid student or book ID.");
                    }
                    break;
                    
                case 7: // Show borrowed books of a student by First Name
                    System.out.print("Enter student First Name: ");
                    studentFirstName = scanner.nextLine();
                    System.out.println("------------------------");
                    Student student = searchStudentByFirstName(sortStudentByFirstName(students), studentFirstName);
                    if (student == null) {
                        System.out.println("Student not found.");
                    } else {
                        ArrayList<Book> borrowedBooks = student.getBorrowedBooks();
                        if (borrowedBooks.isEmpty()) {
                            System.out.println("No books borrowed by this student.");
                        } else {
                            System.out.println("Books borrowed by " + student.getFirstName() + ":");
                            for (Book b : borrowedBooks) {
                                System.out.println("-> " + b.getBookTitle() + " by " + b.getAuthorFirstName() + " " + b.getAuthorLastName());
                            }
                        }
                    }
                    break;
                    
                case 8:
                    // Exit
                    return;

                default:
                    System.out.println("Invalid option.");
                    break;

            }
        }
    }

    public static ArrayList<Book> readBookFunction() {
        String csvFile = "./data.csv";
        String line = "";
        String csvSplitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"; // regular expression to split on comma not enclosed in quotes

        ArrayList<Book> bookList = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] bookData = line.split(csvSplitBy);
                Book book = new Book(bookData[0], bookData[1], bookData[2], bookData[3], bookData[4]);
                bookList.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        bookList.remove(0);
        return bookList;
    }

    public static class Book {

        private String id;
        private String authorFirstName;
        private String authorLastName;
        private String bookTitle;
        private String genre;
        private boolean borrowed;
        private Student borrower;
        private ArrayList<Student> waitingList;

        public Book(String id, String authorFirstName, String authorLastName, String bookTitle, String genre) {
            this.id = id;
            this.authorFirstName = authorFirstName;
            this.authorLastName = authorLastName;
            this.bookTitle = bookTitle;
            this.genre = genre;
            this.borrowed = false;
            this.waitingList = new ArrayList<Student>();
        }

        public String getId() {
            return id;
        }

        public String getAuthorFirstName() {
            return authorFirstName;
        }

        public String getAuthorLastName() {
            return authorLastName;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public String getGenre() {
            return genre;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setAuthorFirstName(String authorFirstName) {
            this.authorFirstName = authorFirstName;
        }

        public void setAuthorLastName(String authorLastName) {
            this.authorLastName = authorLastName;
        }

        public void setBookTitle(String bookTitle) {
            this.bookTitle = bookTitle;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public boolean isBorrowed() {
            return borrowed;
        }

        public void setBorrowed(boolean borrowed) {
            this.borrowed = borrowed;
        }

        public Student getBorrower() {
            return borrower;
        }

        public void setBorrower(Student student) {
            this.borrower = student;
        }

        public ArrayList<Student> getWaitingList() {
            return waitingList;
        }

        public void setWaitingList(Student student) {
            this.waitingList.add(student);
        }
    }

    public static class Student {

        private String id;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private boolean borrowed;
        private ArrayList<Book> borrowedBooks;

        public Student(String id, String firstName, String lastName, String email, String phoneNumber) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.borrowed = false;
            this.borrowedBooks = new ArrayList<Book>();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public ArrayList<Book> getBorrowedBooks() {
            return borrowedBooks;
        }

        public void setBorrowedBooks(Book book) {
            this.borrowedBooks.add(book);
        }

        public Boolean getBorrowed() {
            return borrowed;
        }

        public void setBorrowed(Boolean borrow) {
            this.borrowed = borrow;
        }
    }

    public static ArrayList<Student> sortStudentByFirstName(ArrayList<Student> students) {
        ArrayList<Student> sortedStudents = new ArrayList<>(students);
        int n = sortedStudents.size();
        for (int i = 1; i < n; ++i) {
            Student key = sortedStudents.get(i);
            int j = i - 1;

            while (j >= 0 && sortedStudents.get(j).getFirstName().compareTo(key.getFirstName()) > 0) {
                sortedStudents.set(j + 1, sortedStudents.get(j));
                j = j - 1;
            }
            sortedStudents.set(j + 1, key);
        }
        return sortedStudents;
    }

    public static Student searchStudentByFirstName(ArrayList<Student> students, String name) {
        int left = 0;
        int right = students.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (students.get(mid).getFirstName().equals(name)) {
                return students.get(mid);
            }

            if (students.get(mid).getFirstName().compareTo(name) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null; // student not found
    }

    public static Book searchBookByAuthorFirstName(ArrayList<Book> books, String name) {
        int left = 0;
        int right = books.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (books.get(mid).getAuthorFirstName().equals(name)) {
                return books.get(mid);
            }

            if (books.get(mid).getAuthorFirstName().compareTo(name) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null; // book not found
    }

    public static ArrayList<Student> readStudentFunction() {
        String csvFile = "./students.csv";
        String line = "";
        String csvSplitBy = ",";
        ArrayList<Student> studentList = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] studentData = line.split(csvSplitBy);
                Student student = new Student(studentData[0], studentData[1], studentData[2], studentData[3], studentData[4]);
                studentList.add(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        studentList.remove(0);
        return studentList;
    }

    public static ArrayList<Book> sortBookByAuthorFirstName(ArrayList<Book> books) {
        ArrayList<Book> sortedBooks = new ArrayList<>(books);
        int n = sortedBooks.size();
        for (int i = 1; i < n; ++i) {
            Book key = sortedBooks.get(i);
            int j = i - 1;

            while (j >= 0 && sortedBooks.get(j).getAuthorFirstName().compareTo(key.getAuthorFirstName()) > 0) {
                sortedBooks.set(j + 1, sortedBooks.get(j));
                j = j - 1;
            }
            sortedBooks.set(j + 1, key);
        }
        return sortedBooks;
    }

}
