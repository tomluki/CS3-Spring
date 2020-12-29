package com.jb.couponsys.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.jb.couponsys.exception.InvalidDetailsException;
import com.jb.couponsys.service.AdminService;
import com.jb.couponsys.service.CompanyService;
import com.jb.couponsys.service.CustomerService;

@Component
@Lazy
public class LoginManager {

	private LoginManager() {
	}

	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private TokensManager tokensManager;
	@Autowired
	private AdminService adminService;
	private CustomerService customerService;
	private CompanyService companyService;

	public String login(String email, String password, ClientType clientType) throws Exception {

		switch (clientType) {
		case Admin:
			if (adminService.login(email, password)) {
				return tokensManager.add(adminService);
			}
			break;
		case Company:
			companyService = ctx.getBean(CompanyService.class);
			if (companyService.login(email, password)) {
				companyService.setCompanyID(companyService.getCompanyDetails().getId());
				return tokensManager.add(companyService);

			}
			break;
		
		case Customer:
			customerService = ctx.getBean(CustomerService.class);
			if (customerService.login(email, password)) {
				customerService.setCustomerId(customerService.getCustomerDetails().getId());
				return tokensManager.add(customerService);

			}
			break;
		}
		throw new InvalidDetailsException("Sorry, it seems your Email/Password is/are incorrect...");
	}
}
