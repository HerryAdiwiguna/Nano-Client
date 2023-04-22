package id.co.bca.spring.client.model;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="customer")
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;
    @Column(name = "cust_name")
    private String custName;
    @Column(name = "cust_email")
    private String custEmail;
    @Column(name = "cust_type")
    private String custType;
    @Column(name = "cust_balance")
    private BigDecimal custBalance;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public BigDecimal getCustBalance() {
        return custBalance;
    }

    public void setCustBalance(BigDecimal custBalance) {
        this.custBalance = custBalance;
    }
}
