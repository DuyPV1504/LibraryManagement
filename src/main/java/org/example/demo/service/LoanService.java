package org.example.demo.service;

import models.Loan;
import org.example.demo.data.LoanRepository;

import java.util.List;

public class LoanService {

    private LoanRepository loanRepository;

    /**
     * khoi tao.
     */
    public LoanService() {
        loanRepository = new LoanRepository();
    }

    /**
     * lay loan.
     *
     * @return list
     */
    public List<Loan> getAllTransaction() {
        return loanRepository.getAllTransactions();
    }

    /**
     * lay loan = id
     *
     * @param id id
     * @return loan
     */
    public Loan getTransaction(int id) {
        return loanRepository.getTransaction(id);
    }

    /**
     * tim loan.
     *
     * @param keyword tso
     * @return loan
     */
    public List<Loan> searchTransaction(String keyword) {
        return loanRepository.searchTransaction(keyword);
    }

    /**
     * them loan.
     *
     * @param loan loan
     * @return logic
     */
    public boolean addTransaction(Loan loan) {
        try {
            return loanRepository.addTransaction(loan);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * update Loan.
     *
     * @param loan loan
     * @return logic
     */
    public boolean updateTransaction(Loan loan) {
        try {
            return true; // Thực hiện cập nhật loan vào database nếu cập nhật thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra
        }
    }

    /**
     * xoa loan.
     *
     * @param id id
     * @return logic
     */
    public boolean deleteTransaction(int id) {
        try {
            return loanRepository.deleteTransaction(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

