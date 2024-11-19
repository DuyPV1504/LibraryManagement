package org.example.demo.service;

import models.Loan;
import org.example.demo.data.LoanRepository;

import java.util.List;

public class LoanService {

    private LoanRepository loanRepository;

    public LoanService() {
        loanRepository = new LoanRepository();
    }

    public List<Loan> getAllTransaction() {
        return loanRepository.getAllTransactions();
    }

    public Loan getTransaction(int id) {
        return loanRepository.getTransaction(id);
    }

    public List<Loan> searchTransaction(String keyword) {
        return loanRepository.searchTransaction(keyword);
    }

    public boolean addTransaction(Loan loan) {
        try {
            return loanRepository.addTransaction(loan);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTransaction(Loan loan) {
        try {
            return true; // Thực hiện cập nhật loan vào database nếu cập nhật thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra
        }
    }

    public boolean deleteTransaction(int id) {
        try {
            return loanRepository.deleteTransaction(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

