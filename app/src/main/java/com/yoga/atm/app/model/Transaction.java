package com.yoga.atm.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.yoga.atm.app.enumerable.TransactionType;

@Entity
@Table(name = "transaction")
public class Transaction {

	public Transaction() {
	}

	public Transaction(TransactionType type, Account account, Double amount, Date date, Account destinationAccount,
			String reference) {
		this.type = type;
		this.account = account;
		this.amount = amount;
		this.date = date;
		this.destinationAccount = destinationAccount;
		this.reference = reference;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private TransactionType type;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account", nullable = false)
	private Account account;

	@Column(name = "amount")
	private Double amount;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "destination_account", nullable = true)
	private Account destinationAccount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date", length = 19)
	private Date date;

	@Column(name = "reference", length = 6)
	private String reference;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Account getDestinationAccount() {
		return destinationAccount;
	}

	public void setDestinationAccount(Account destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
