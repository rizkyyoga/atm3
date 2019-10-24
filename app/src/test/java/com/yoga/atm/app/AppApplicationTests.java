package com.yoga.atm.app;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.yoga.atm.app.dao.AccountRepository;
import com.yoga.atm.app.model.Account;

@SpringBootTest
@AutoConfigureMockMvc
class AppApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AccountRepository accountService;

	@Test
	void inputAccountNumberPage() throws Exception {
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("welcome/inputAccountNumber"));
	}

	@Test
	void inputPinPage() throws Exception {
		this.mockMvc.perform(get("/pin?an=000001")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("welcome/inputPin"));
	}

	@Test
	void login() throws Exception {
		this.mockMvc.perform(post("/login").param("accountNumber", "000001").param("pin", "000001"))
				.andExpect(redirectedUrl("/transaction"))
				.andExpect(request().sessionAttribute("account", hasProperty("accountNumber", is("000001"))));
	}

	@Test
	void transactionPage() throws Exception {
		Map<String, Object> sessionAttrs = new HashMap<>();
		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
		this.mockMvc.perform(get("/transaction").sessionAttrs(sessionAttrs)).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("transaction/index"));
	}

	@Test
	void logout() throws Exception {
		Map<String, Object> sessionAttrs = new HashMap<>();
		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
		HttpSession session = this.mockMvc.perform(get("/logout").sessionAttrs(sessionAttrs))
				.andExpect(redirectedUrl("/")).andReturn().getRequest().getSession();
		assertNull(session.getAttribute("account"));
	}

	@Test
	void withdrawPage() throws Exception {
		Map<String, Object> sessionAttrs = new HashMap<>();
		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
		this.mockMvc.perform(get("/withdraw").sessionAttrs(sessionAttrs)).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("withdraw/index"));
	}

	@Test
	void withdrawlPage() throws Exception {
		Map<String, Object> sessionAttrs = new HashMap<>();
		Account account = accountService.findByAccountNumber("000001").get(0);
		double balance = account.getBalance();
		sessionAttrs.put("account", account);
		HttpSession session = this.mockMvc.perform(get("/withdrawl?amount=10").sessionAttrs(sessionAttrs))
				.andExpect(redirectedUrl("/withdrawSummary")).andReturn().getRequest().getSession();
		// check value in session
		assertEquals(balance - 10, ((Account) session.getAttribute("account")).getBalance());
		// check value in db
		assertEquals(balance - 10, accountService.findByAccountNumber("000001").get(0).getBalance());
	}

	@Test
	void withdrawSummaryPage() throws Exception {
		Map<String, Object> sessionAttrs = new HashMap<>();
		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
		Map<String, Object> flash = new HashMap<>();
		flash.put("balance", 340);
		flash.put("withdraw", 10);
		flash.put("date", "2019-10-24 08:23 AM");
		flash.put("accountNumber", "000001");
		this.mockMvc.perform(get("/withdrawSummary").flashAttrs(flash).sessionAttrs(sessionAttrs)).andDo(print())
				.andExpect(status().isOk()).andExpect(view().name("withdraw/summary"));
	}

	@Test
	void inputDestinationPage() throws Exception {
		Map<String, Object> sessionAttrs = new HashMap<>();
		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
		this.mockMvc.perform(get("/transferDestination").sessionAttrs(sessionAttrs)).andDo(print())
				.andExpect(status().isOk()).andExpect(view().name("transfer/destination"));
	}

	@Test
	void inputAmountPage() throws Exception {
		Map<String, Object> sessionAttrs = new HashMap<>();
		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
		this.mockMvc.perform(get("/transferAmount?destination=000002").sessionAttrs(sessionAttrs)).andDo(print())
				.andExpect(status().isOk()).andExpect(view().name("transfer/amount"));
	}

	@Test
	void transferConfirmPage() throws Exception {
		Map<String, Object> sessionAttrs = new HashMap<>();
		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
		this.mockMvc.perform(get("/transferConfirm?destination=000002&amount=20").sessionAttrs(sessionAttrs))
				.andDo(print()).andExpect(status().isOk()).andExpect(view().name("transfer/confirm"));
	}

	@Test
	void transfer() throws Exception {
		Map<String, Object> sessionAttrs = new HashMap<>();
		Account account = accountService.findByAccountNumber("000001").get(0);
		double balance = account.getBalance();
		double balanceDestination = accountService.findByAccountNumber("000002").get(0).getBalance();
		sessionAttrs.put("account", account);
		HttpSession session = this.mockMvc
				.perform(post("/transfer").param("destination", "000002").param("reference", "123456")
						.param("amount", "20").sessionAttrs(sessionAttrs))
				.andExpect(redirectedUrl("/transferSummary")).andReturn().getRequest().getSession();
		// check value in session
		assertEquals(balance - 20, ((Account) session.getAttribute("account")).getBalance());
		// check value in db
		assertEquals(balance - 20, accountService.findByAccountNumber("000001").get(0).getBalance());
		assertEquals(balanceDestination + 20, accountService.findByAccountNumber("000002").get(0).getBalance());
	}

	@Test
	void transferSummaryPage() throws Exception {
		Map<String, Object> sessionAttrs = new HashMap<>();
		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
		Map<String, Object> flash = new HashMap<>();
		flash.put("destination", "000002");
		flash.put("amount", "20");
		flash.put("reference", "123456");
		flash.put("balance", "430");
		this.mockMvc.perform(get("/transferSummary").flashAttrs(flash).sessionAttrs(sessionAttrs)).andDo(print())
				.andExpect(status().isOk()).andExpect(view().name("transfer/summary"));
	}

	@Test
	void viewTransactionPage() throws Exception {
		Map<String, Object> sessionAttrs = new HashMap<>();
		sessionAttrs.put("account", accountService.findByAccountNumber("000001").get(0));
		this.mockMvc.perform(get("/viewTransaction").sessionAttrs(sessionAttrs)).andDo(print())
				.andExpect(status().isOk()).andExpect(view().name("view/transaction"));
	}
}
