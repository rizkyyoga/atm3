package com.yoga.atm.app.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yoga.atm.app.dao.AccountRepository;
import com.yoga.atm.app.dao.TransactionRepository;
import com.yoga.atm.app.enumerable.TransactionType;
import com.yoga.atm.app.model.Account;
import com.yoga.atm.app.model.Transaction;

@Controller
@PropertySource("classpath:message.properties")
@RequestMapping("")
public class WithdrawController {

	@Autowired
	private Environment env;

	@Autowired
	private AccountRepository accountService;

	@Autowired
	private TransactionRepository transactionService;

	@RequestMapping(value = "/withdraw", method = RequestMethod.GET)
	public ModelAndView withdraw(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		ModelAndView view = new ModelAndView();
		try {
			Account account = (Account) request.getSession().getAttribute("account");
			view.addObject("accountNumber", account.getAccountNumber());
			view.setViewName("withdraw/index");
		} catch (Exception e) {
			request.getSession().invalidate();
			view = new ModelAndView("redirect:/");
			redirectAttributes.addFlashAttribute("message", env.getProperty("app.unknown.error"));
		}
		return view;
	}

	@RequestMapping(value = "/withdrawl", method = RequestMethod.GET)
	public ModelAndView withdrawl(HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam(value = "amount", required = true) String amount) {
		ModelAndView view = new ModelAndView();
		try {
			Account account = (Account) request.getSession().getAttribute("account");
			String message = "";
			boolean goToSummary = true;
			if (!amount.matches("[0-9]+")) {
				goToSummary = false;
				message += env.getProperty("app.amount.number");
			} else {
				if (Double.valueOf(amount) % 10 != 0) {
					goToSummary = false;
					message += env.getProperty("app.invalid.amount");
				}
				if (Double.valueOf(amount) > 1000) {
					goToSummary = false;
					message += env.getProperty("app.amount.maximum");
				}
				if (Double.valueOf(amount) > account.getBalance()) {
					goToSummary = false;
					message += env.getProperty("app.amount.insufficient") + account.getBalance() + "<br>";
				}
			}

			if (goToSummary) {
				account.setBalance(account.getBalance() - Double.valueOf(amount));
				account = accountService.save(account);
				request.getSession().setAttribute("account", account);
				Transaction transaction = new Transaction(TransactionType.WITHDRAW, account, Double.valueOf(amount),
						new Date(), null, null);
				transactionService.save(transaction);
				view = new ModelAndView("redirect:/withdrawSummary");
				DecimalFormat formatter = new DecimalFormat("#,###.00");
				redirectAttributes.addFlashAttribute("balance", formatter.format(account.getBalance()));
				redirectAttributes.addFlashAttribute("withdraw", formatter.format(Double.valueOf(amount)));
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
				redirectAttributes.addFlashAttribute("date", dateFormat.format(new Date()));
				redirectAttributes.addFlashAttribute("accountNumber", account.getAccountNumber());
			} else {
				view = new ModelAndView("redirect:/withdraw");
				redirectAttributes.addFlashAttribute("message", message);
			}
		} catch (Exception e) {
			request.getSession().invalidate();
			view = new ModelAndView("redirect:/");
			redirectAttributes.addFlashAttribute("message", env.getProperty("app.unknown.error"));
		}
		return view;
	}

}
