package com.yoga.atm.app.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yoga.atm.app.dao.TransactionRepository;
import com.yoga.atm.app.model.Account;
import com.yoga.atm.app.model.Transaction;

@Controller
@PropertySource("classpath:message.properties")
@RequestMapping("")
public class ViewTransactionController {

	@Autowired
	private Environment env;

	@Autowired
	private TransactionRepository transactionService;

	@Value("${app.viewn.transaction}")
	private Integer nTransaction;

	private static List<Map<String, String>> formattedList;

	@RequestMapping(value = "/viewTransaction", method = RequestMethod.GET)
	public ModelAndView inputAccountNumber(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		ModelAndView view = new ModelAndView();
		try {
			Account account = (Account) request.getSession().getAttribute("account");
			formattedList = new ArrayList<Map<String, String>>();
			List<Transaction> listTransaction = transactionService.findNTransaction(account.getAccountNumber(),
					nTransaction);
			listTransaction.forEach(transaction -> {
				Map<String, String> formatted = new HashMap<String, String>();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
				formatted.put("date", dateFormat.format(transaction.getDate()));
				formatted.put("type", transaction.getType().toString());
				DecimalFormat formatter = new DecimalFormat("#,###.00");
				formatted.put("amount", formatter.format(transaction.getAmount()));
				if (transaction.getDestinationAccount() != null)
					formatted.put("destinationAccount", transaction.getDestinationAccount().getAccountNumber());
				formatted.put("reference", transaction.getReference());
				formattedList.add(formatted);
			});
			view.addObject("list", formattedList);
			view.setViewName("view/transaction");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			request.getSession().invalidate();
			view = new ModelAndView("redirect:/");
			redirectAttributes.addFlashAttribute("message", env.getProperty("app.unknown.error"));
		}
		return view;
	}

}
