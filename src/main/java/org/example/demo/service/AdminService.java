package org.example.demo.service;

import models.Book;
import models.Loan;
import models.User;
import org.example.demo.data.AdminRepository;

import java.util.List;

public class AdminService {

    private final AdminRepository adminRepository = new AdminRepository();

    /**
     * update role.
     *
     * @param userId id
     */
    public void updateRoleToAdmin(int userId) {
        adminRepository.updateUserRoleToAdmin(userId);
    }

    /**
     * timListUser.
     *
     * @param id          id
     * @param surname     ho
     * @param lastname    ten
     * @param dateOfBirth sn
     * @param gender      gioi tinh
     * @param email       mail
     * @param userAccount account
     * @return list nguoi
     */
    public List<User> searchUser(String id, String surname, String lastname,
                                 String dateOfBirth, String gender, String email, String userAccount) {
        return adminRepository.searchUserByKeyword(id, surname, lastname,
                dateOfBirth, gender, email, userAccount);
    }

    /**
     * goi y sach.
     *
     * @param id             id
     * @param bookName       ten
     * @param publishedYear  nam xb
     * @param availableBooks con lai
     * @param totalBooks     tong
     * @param author         tac gia
     * @param publisher      nxs
     * @return list sach
     */
    public List<Book> searchBook(String id, String bookName, String publishedYear,
                                 String availableBooks, String totalBooks,
                                 String author, String publisher) {
        return adminRepository.searchBookByKeyword(id, bookName,
                publishedYear, availableBooks, totalBooks, author, publisher);
    }

    /**
     * goi y loan.
     *
     * @param transactionId id
     * @param userAccount   acc
     * @param bookId        id
     * @param borrowDate    ngay muon
     * @param endDate       ngay tra
     * @param status        tinh trang
     * @return list loan
     */
    public List<Loan> searchTransaction(String transactionId, String userAccount,
                                        String bookId, String borrowDate,
                                        String endDate, String status) {
        return adminRepository.searchTransactionByKeyword(transactionId,
                userAccount, bookId, borrowDate, endDate, status);
    }

    /**
     * lay sach.
     *
     * @return list
     */
    public List<Book> getAllBooks() {
        return adminRepository.getAllBooks();
    }

    /**
     * lay loan.
     *
     * @return list
     */
    public List<Loan> getAllTransactions() {
        return adminRepository.getAllTransactions();
    }

    /**
     * lay user.
     *
     * @return list
     */
    public List<User> getAllUsers() {
        return adminRepository.getAllUsers();
    }

    /**
     * xoa nguoi bang id.
     *
     * @param userId id
     * @return so
     */
    public int deleteUser(int userId) {
        return adminRepository.deleteUser(userId);
    }

    /**
     * them sach.
     *
     * @param book sach
     * @return so
     */
    public int addBook(Book book) {
        return adminRepository.addBook(book);
    }

    /**
     * xoa sach = id.
     *
     * @param bookId id
     * @return so
     */
    public int deleteBook(int bookId) {
        return adminRepository.deleteBook(bookId);
    }

    /**
     * them loan.
     *
     * @param loan loan
     * @return so
     */
    public int addTransaction(Loan loan) {
        return adminRepository.addTransaction(loan);
    }

    /**
     * xoa loan.
     *
     * @param transactionId id
     * @return logic
     */
    public boolean deleteTransaction(int transactionId) {
        return adminRepository.deleteTransactionById(transactionId);
    }

    /**
     * them nguoi.
     *
     * @param user user
     * @return logic
     */
    public boolean addUser(User user) {
        try {
            adminRepository.addUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * update user.
     *
     * @param user user.
     * @return logic
     */
    public boolean updateUser(User user) {
        try {
            adminRepository.updateUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * update book.
     *
     * @param book sach
     * @return logic
     */
    public boolean updateBook(Book book) {
        try {
            adminRepository.updateBook(book);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * update loan.
     *
     * @param loan loan
     * @return logic
     */
    public boolean updateTransaction(Loan loan) {
        try {
            adminRepository.updateTransaction(loan);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
