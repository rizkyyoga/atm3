package com.yoga.atm.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.yoga.atm.app.dao.AccountRepository;
import com.yoga.atm.app.dao.TransactionRepository;
import com.yoga.atm.app.enumerable.TransactionType;
import com.yoga.atm.app.model.Account;
import com.yoga.atm.app.model.Transaction;

@SpringBootTest
@AutoConfigureMockMvc
class AppApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AccountRepository accountService;

	@Autowired
	private TransactionRepository transactionService;

	// TESTCASE #1
	@Test
	void testcase1() throws Exception {
		transactionService.deleteAll();
		Account account = new Account();
		account.setAccountNumber("100000");
		account.setPin("100000");
		account.setName("java");
		account.setBalance(200.0);
		accountService.save(account);
		Account proofAccount = accountService.findByAccountNumber("100000").get(0);
		assertEquals(proofAccount.getAccountNumber(), "100000");
		assertEquals(proofAccount.getPin(), "100000");
		assertEquals(proofAccount.getName(), "java");
		assertEquals(proofAccount.getBalance(), 200.0);
	}

	// TESTCASE #2
	@Test
	void testcase2() throws Exception {
		transactionService.deleteAll();
		Account account = new Account();
		account.setAccountNumber("200000");
		account.setPin("200000");
		account.setName("java2");
		account.setBalance(300.0);
		account = accountService.save(account);
		List<Account> proofAccount = (List<Account>) accountService.findAll();
		boolean isInList = false;
		for (Account a : proofAccount) {
			if (a.getAccountNumber().equals("200000")) {
				isInList = true;
				break;
			}
		}
		assertTrue(isInList);
	}

	// TESTCASE #3
	@Test
	void testcase3() throws Exception {
		transactionService.deleteAll();
		Account acc = new Account();
		acc.setAccountNumber("100000");
		acc.setPin("100000");
		acc.setName("java");
		acc.setBalance(200.0);
		accountService.save(acc);
		Map<String, Object> sessionAttrs = new HashMap<>();
		Account account = accountService.findByAccountNumber("100000").get(0);
		double balance = account.getBalance();
		sessionAttrs.put("account", account);
		HttpSession session = this.mockMvc.perform(get("/withdrawl?amount=10").sessionAttrs(sessionAttrs))
				.andExpect(redirectedUrl("/withdrawSummary")).andReturn().getRequest().getSession();
		// check value in session
		assertEquals(balance - 10, ((Account) session.getAttribute("account")).getBalance());
		// check value in db
		assertEquals(balance - 10, accountService.findByAccountNumber("100000").get(0).getBalance());
	}

	// TESTCASE #4
	@Test
	void testcase4() throws Exception {
		transactionService.deleteAll();
		Account acc = new Account();
		acc.setAccountNumber("100000");
		acc.setPin("100000");
		acc.setName("java");
		acc.setBalance(200.0);
		accountService.save(acc);
		Map<String, Object> sessionAttrs = new HashMap<>();
		Account account = accountService.findByAccountNumber("100000").get(0);
		sessionAttrs.put("account", account);
		this.mockMvc.perform(get("/withdrawl?amount=210").sessionAttrs(sessionAttrs))
				.andExpect(redirectedUrl("/withdraw"))
				.andExpect(flash().attribute("message", "Insufficient balance $" + account.getBalance() + "<br>"));
	}

	// TESTCASE #5
	@Test
	void testcase5() throws Exception {
		transactionService.deleteAll();
		Account acc = new Account();
		acc.setAccountNumber("100000");
		acc.setPin("100000");
		acc.setName("java");
		acc.setBalance(200.0);
		accountService.save(acc);
		acc = new Account();
		acc.setAccountNumber("200000");
		acc.setPin("200000");
		acc.setName("java2");
		acc.setBalance(300.0);
		accountService.save(acc);
		Map<String, Object> sessionAttrs = new HashMap<>();
		Account account = accountService.findByAccountNumber("100000").get(0);
		double balance = account.getBalance();
		double balanceDestination = accountService.findByAccountNumber("200000").get(0).getBalance();
		sessionAttrs.put("account", account);
		HttpSession session = this.mockMvc
				.perform(post("/transfer").param("destination", "200000").param("reference", "123456")
						.param("amount", "20").sessionAttrs(sessionAttrs))
				.andExpect(redirectedUrl("/transferSummary")).andReturn().getRequest().getSession();
		// check value in session
		assertEquals(balance - 20, ((Account) session.getAttribute("account")).getBalance());
		// check value in db
		assertEquals(balance - 20, accountService.findByAccountNumber("100000").get(0).getBalance());
		assertEquals(balanceDestination + 20, accountService.findByAccountNumber("200000").get(0).getBalance());
	}

	// TESTCASE #6
	@Test
	void testcase6() throws Exception {
		transactionService.deleteAll();
		Account acc = new Account();
		acc.setAccountNumber("100000");
		acc.setPin("100000");
		acc.setName("java");
		acc.setBalance(200.0);
		accountService.save(acc);
		acc = new Account();
		acc.setAccountNumber("200000");
		acc.setPin("200000");
		acc.setName("java2");
		acc.setBalance(300.0);
		accountService.save(acc);
		Map<String, Object> sessionAttrs = new HashMap<>();
		Account account = accountService.findByAccountNumber("100000").get(0);
		sessionAttrs.put("account", account);
		this.mockMvc
				.perform(post("/transfer").param("destination", "200000").param("reference", "123456")
						.param("amount", "210").sessionAttrs(sessionAttrs))
				.andExpect(redirectedUrl("/transfer"))
				.andExpect(flash().attribute("message", "Insufficient balance $" + account.getBalance() + "<br>"));
	}

	// TESTCASE #7
	@Test
	void testcase7() throws Exception {
		transactionService.deleteAll();
		Account acc = new Account();
		acc.setAccountNumber("100000");
		acc.setPin("100000");
		acc.setName("java");
		acc.setBalance(200.0);
		accountService.save(acc);
		Map<String, Object> sessionAttrs = new HashMap<>();
		Account account = accountService.findByAccountNumber("100000").get(0);
		sessionAttrs.put("account", account);
		this.mockMvc
				.perform(post("/transfer").param("destination", "247819").param("reference", "123456")
						.param("amount", "10").sessionAttrs(sessionAttrs))
				.andExpect(redirectedUrl("/transfer")).andExpect(flash().attribute("message", "Invalid account<br>"));
	}

	// TESTCASE #8
	@Test
	void testcase8() throws Exception {
		transactionService.deleteAll();
		Account acc = new Account();
		acc.setAccountNumber("100000");
		acc.setPin("100000");
		acc.setName("java");
		acc.setBalance(200.0);
		accountService.save(acc);
		Map<String, Object> sessionAttrs = new HashMap<>();
		Account account = accountService.findByAccountNumber("100000").get(0);
		sessionAttrs.put("account", account);
		Map<String, Object> model = this.mockMvc.perform(get("/withdrawl?amount=10").sessionAttrs(sessionAttrs))
				.andExpect(redirectedUrl("/withdrawSummary")).andReturn().getFlashMap();
		String transactionDate = (String) model.get("date");
		Map<String, Object> model2 = this.mockMvc.perform(get("/viewTransaction").sessionAttrs(sessionAttrs))
				.andReturn().getModelAndView().getModel();
		boolean isInList = false;
		for (Map<String, String> a : (List<Map<String, String>>) model2.get("list")) {
			if (a.get("date").equals(transactionDate)) {
				isInList = true;
				break;
			}
		}
		assertTrue(isInList);
	}

	// TESTCASE #9
	@Test
	void testcase9() throws Exception {
		transactionService.deleteAll();
		Account acc = new Account();
		acc.setAccountNumber("100000");
		acc.setPin("100000");
		acc.setName("java");
		acc.setBalance(200.0);
		accountService.save(acc);
		acc = new Account();
		acc.setAccountNumber("200000");
		acc.setPin("200000");
		acc.setName("java2");
		acc.setBalance(300.0);
		accountService.save(acc);
		Account from = accountService.findByAccountNumber("100000").get(0);
		Account to = accountService.findByAccountNumber("200000").get(0);
		for (int i = 0; i < 5; i++) {
			Transaction t = new Transaction(TransactionType.TRANSFER, from, (double) (i + 1) * 10, new Date(), to,
					"00000" + i);
			transactionService.save(t);

		}
		Map<String, Object> sessionAttrs = new HashMap<>();
		Account account = accountService.findByAccountNumber("100000").get(0);
		sessionAttrs.put("account", account);
		Map<String, Object> model2 = this.mockMvc.perform(get("/viewTransaction").sessionAttrs(sessionAttrs))
				.andReturn().getModelAndView().getModel();
		boolean r0 = false, r1 = false, r2 = false, r3 = false, r4 = false;
		for (Map<String, String> a : (List<Map<String, String>>) model2.get("list")) {
			if (a.get("reference") != null && a.get("reference").equals("000000"))
				r0 = true;
			else if (a.get("reference") != null && a.get("reference").equals("000001"))
				r1 = true;
			else if (a.get("reference") != null && a.get("reference").equals("000002"))
				r2 = true;
			else if (a.get("reference") != null && a.get("reference").equals("000003"))
				r3 = true;
			else if (a.get("reference") != null && a.get("reference").equals("000004"))
				r4 = true;
		}
		assertTrue(r0 && r1 && r2 && r3 && r4);
	}

	// TESTCASE #10
	@Test
	void testcase10() throws Exception {
		transactionService.deleteAll();
		Account acc = new Account();
		acc.setAccountNumber("100000");
		acc.setPin("100000");
		acc.setName("java");
		acc.setBalance(200.0);
		accountService.save(acc);
		acc = new Account();
		acc.setAccountNumber("200000");
		acc.setPin("200000");
		acc.setName("java2");
		acc.setBalance(300.0);
		accountService.save(acc);
		Account from = accountService.findByAccountNumber("100000").get(0);
		Account to = accountService.findByAccountNumber("200000").get(0);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		for (int i = 0; i < 12; i++) {
			c.add(Calendar.HOUR, -1 * i);
			Transaction t = new Transaction(TransactionType.TRANSFER, from, (double) (i + 1) * 10, c.getTime(), to,
					(i < 10 ? "00000" : "0000") + i);
			transactionService.save(t);

		}
		Map<String, Object> sessionAttrs = new HashMap<>();
		Account account = accountService.findByAccountNumber("100000").get(0);
		sessionAttrs.put("account", account);
		Map<String, Object> model2 = this.mockMvc.perform(get("/viewTransaction").sessionAttrs(sessionAttrs))
				.andReturn().getModelAndView().getModel();
		boolean r0 = false, r1 = false, r2 = false, r3 = false, r4 = false, r11 = false;
		for (Map<String, String> a : (List<Map<String, String>>) model2.get("list")) {
			if (a.get("reference") != null && a.get("reference").equals("000000"))
				r0 = true;
			else if (a.get("reference") != null && a.get("reference").equals("000001"))
				r1 = true;
			else if (a.get("reference") != null && a.get("reference").equals("000002"))
				r2 = true;
			else if (a.get("reference") != null && a.get("reference").equals("000003"))
				r3 = true;
			else if (a.get("reference") != null && a.get("reference").equals("000004"))
				r4 = true;
			else if (a.get("reference") != null && a.get("reference").equals("000011"))
				r11 = true;
		}
		assertTrue(r0 && r1 && r2 && r3 && r4 && !r11);
	}

//	@Test
//	void inputAccountNumberPage() throws Exception {
//		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
//				.andExpect(view().name("welcome/inputAccountNumber"));
//	}
//
//	@Test
//	void inputPinPage() throws Exception {
//		this.mockMvc.perform(get("/pin?an=000001")).andDo(print()).andExpect(status().isOk())
//				.andExpect(view().name("welcome/inputPin"));
//	}
//
//	@Test
//	void login() throws Exception {
//		this.mockMvc.perform(post("/login").param("accountNumber", "000001").param("pin", "000001"))
//				.andExpect(redirectedUrl("/transaction"))
//				.andExpect(request().sessionAttribute("account", hasProperty("accountNumber", is("000001"))));
//	}
//
//	@Test
//	void transactionPage() throws Exception {
//		Map<String, Object> sessionAttrs = new HashMap<>();
//		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
//		this.mockMvc.perform(get("/transaction").sessionAttrs(sessionAttrs)).andDo(print()).andExpect(status().isOk())
//				.andExpect(view().name("transaction/index"));
//	}
//
//	@Test
//	void logout() throws Exception {
//		Map<String, Object> sessionAttrs = new HashMap<>();
//		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
//		HttpSession session = this.mockMvc.perform(get("/logout").sessionAttrs(sessionAttrs))
//				.andExpect(redirectedUrl("/")).andReturn().getRequest().getSession();
//		assertNull(session.getAttribute("account"));
//	}
//
//	@Test
//	void withdrawPage() throws Exception {
//		Map<String, Object> sessionAttrs = new HashMap<>();
//		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
//		this.mockMvc.perform(get("/withdraw").sessionAttrs(sessionAttrs)).andDo(print()).andExpect(status().isOk())
//				.andExpect(view().name("withdraw/index"));
//	}
//
//	@Test
//	void withdrawlPage() throws Exception {
//		Map<String, Object> sessionAttrs = new HashMap<>();
//		Account account = accountService.findByAccountNumber("000001").get(0);
//		double balance = account.getBalance();
//		sessionAttrs.put("account", account);
//		HttpSession session = this.mockMvc.perform(get("/withdrawl?amount=10").sessionAttrs(sessionAttrs))
//				.andExpect(redirectedUrl("/withdrawSummary")).andReturn().getRequest().getSession();
//		// check value in session
//		assertEquals(balance - 10, ((Account) session.getAttribute("account")).getBalance());
//		// check value in db
//		assertEquals(balance - 10, accountService.findByAccountNumber("000001").get(0).getBalance());
//	}
//
//	@Test
//	void withdrawSummaryPage() throws Exception {
//		Map<String, Object> sessionAttrs = new HashMap<>();
//		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
//		Map<String, Object> flash = new HashMap<>();
//		flash.put("balance", 340);
//		flash.put("withdraw", 10);
//		flash.put("date", "2019-10-24 08:23 AM");
//		flash.put("accountNumber", "000001");
//		this.mockMvc.perform(get("/withdrawSummary").flashAttrs(flash).sessionAttrs(sessionAttrs)).andDo(print())
//				.andExpect(status().isOk()).andExpect(view().name("withdraw/summary"));
//	}
//
//	@Test
//	void inputDestinationPage() throws Exception {
//		Map<String, Object> sessionAttrs = new HashMap<>();
//		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
//		this.mockMvc.perform(get("/transferDestination").sessionAttrs(sessionAttrs)).andDo(print())
//				.andExpect(status().isOk()).andExpect(view().name("transfer/destination"));
//	}
//
//	@Test
//	void inputAmountPage() throws Exception {
//		Map<String, Object> sessionAttrs = new HashMap<>();
//		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
//		this.mockMvc.perform(get("/transferAmount?destination=000002").sessionAttrs(sessionAttrs)).andDo(print())
//				.andExpect(status().isOk()).andExpect(view().name("transfer/amount"));
//	}
//
//	@Test
//	void transferConfirmPage() throws Exception {
//		Map<String, Object> sessionAttrs = new HashMap<>();
//		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
//		this.mockMvc.perform(get("/transferConfirm?destination=000002&amount=20").sessionAttrs(sessionAttrs))
//				.andDo(print()).andExpect(status().isOk()).andExpect(view().name("transfer/confirm"));
//	}
//
//	@Test
//	void transfer() throws Exception {
//		Map<String, Object> sessionAttrs = new HashMap<>();
//		Account account = accountService.findByAccountNumber("000001").get(0);
//		double balance = account.getBalance();
//		double balanceDestination = accountService.findByAccountNumber("000002").get(0).getBalance();
//		sessionAttrs.put("account", account);
//		HttpSession session = this.mockMvc
//				.perform(post("/transfer").param("destination", "000002").param("reference", "123456")
//						.param("amount", "20").sessionAttrs(sessionAttrs))
//				.andExpect(redirectedUrl("/transferSummary")).andReturn().getRequest().getSession();
//		// check value in session
//		assertEquals(balance - 20, ((Account) session.getAttribute("account")).getBalance());
//		// check value in db
//		assertEquals(balance - 20, accountService.findByAccountNumber("000001").get(0).getBalance());
//		assertEquals(balanceDestination + 20, accountService.findByAccountNumber("000002").get(0).getBalance());
//	}
//
//	@Test
//	void transferSummaryPage() throws Exception {
//		Map<String, Object> sessionAttrs = new HashMap<>();
//		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
//		Map<String, Object> flash = new HashMap<>();
//		flash.put("destination", "000002");
//		flash.put("amount", "20");
//		flash.put("reference", "123456");
//		flash.put("balance", "430");
//		this.mockMvc.perform(get("/transferSummary").flashAttrs(flash).sessionAttrs(sessionAttrs)).andDo(print())
//				.andExpect(status().isOk()).andExpect(view().name("transfer/summary"));
//	}
//
//	@Test
//	void viewTransactionPage() throws Exception {
//		Map<String, Object> sessionAttrs = new HashMap<>();
//		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
//		this.mockMvc.perform(get("/viewTransaction").sessionAttrs(sessionAttrs)).andDo(print())
//				.andExpect(status().isOk()).andExpect(view().name("view/transaction"));
//	}
}
