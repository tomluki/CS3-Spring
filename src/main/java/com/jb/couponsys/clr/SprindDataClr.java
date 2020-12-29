package com.jb.couponsys.clr;

import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.jb.couponsys.bean.Category;
import com.jb.couponsys.bean.Company;
import com.jb.couponsys.bean.Coupon;
import com.jb.couponsys.bean.Customer;
import com.jb.couponsys.login.ClientType;
import com.jb.couponsys.login.LoginManager;
import com.jb.couponsys.service.AdminService;
import com.jb.couponsys.service.CompanyService;
import com.jb.couponsys.service.CustomerService;

//@Component
@Order(1)
public class SprindDataClr implements CommandLineRunner {

	@Autowired
	private AdminService admin;
//	@Autowired
	private CompanyService company;
//	@Autowired
	private CustomerService customer;
	@Autowired
	private LoginManager login;

	@Override
	public void run(String... args) throws Exception {
		try {
			String companyToken;
			System.out.println("admin testing");
			Company c1 = new Company("tcomp1", "admin@admin.com", "Admin1234");
			Company c2 = new Company("tcomp2", "dmin@admin.com", "Admin1234");
			Company c3 = new Company("tcomp3", "temail3", "tpass3");
			admin.addCompany(c1);
			admin.addCompany(c2);
			admin.addCompany(c3);
			admin.getOneCompany(1).setName("testcomp1");
			admin.updateCompany(admin.getOneCompany(1));
			System.out.println(admin.getAllCompanies());
			Customer cu1 = new Customer("tcust1", "tcust1", "admin@admin.com", "Admin1234");
			Customer cu2 = new Customer("tcust2", "tcust2", "temail2", "tpass2");
			Customer cu3 = new Customer("tcust3", "tcust2", "temail3", "tpass3");
			admin.addCustomer(cu1);
			admin.addCustomer(cu2);
			admin.addCustomer(cu3);
			System.out.println("check login here");
			System.out.println("check it");
System.out.println(admin.getOneCompanyForTheWeb(1));
System.out.println(admin.getOneCustomerForTheWeb(1));
			company.login("admin@admin.com", "Tpass12345");
			System.out.println(company.getCompanyDetails());
			Coupon p1 = new Coupon(Category.gaming, "cooling pad", "quality artifact", Date.valueOf("2018-08-22"),
					Date.valueOf("2021-08-23"), 5, 55.5, "SDfsd");
			Coupon p2 = new Coupon(Category.restaurant, "flora", "farfora", Date.valueOf("2005-02-28"),
					Date.valueOf("2021-08-23"), 5, 54, "dsdf");
			Coupon p3 = new Coupon(Category.food, "ice cream", "choclate", Date.valueOf("2015-05-28"),
					Date.valueOf("2019-12-23"), 7, 50, "dsdf");
			Coupon p4 = new Coupon(Category.electronics, "leptop", "good leptop", Date.valueOf("2014-02-10"),
					Date.valueOf("2019-04-08"), 3, 60, "dsdf");
			Coupon p5 = new Coupon(Category.cloths, "shirt", "good quality", Date.valueOf("2014-02-10"),
					Date.valueOf("2019-04-08"), 5, 60, "dsdf");
			company.addCoupon(p1);
			company.addCoupon(p2);
			company.addCoupon(p3);
			company.addCoupon(p4);
			company.addCoupon(p5);
			company.getOneCoupon(1).setAmount(15);
			company.updateCoupon(company.getOneCoupon(1));
			System.out.println("category");
			System.out.println(company.getCompanyCouponByCategory(Category.food));
			System.out.println("price");
			System.out.println(company.getCompanyCouponByPrice(55));
			System.out.println("error");
			customer.login("testEmail1", "tpass1");
			System.out.println("error here");
			customer.purchaseCoupon(company.getOneCoupon(1));
			customer.purchaseCoupon(company.getOneCoupon(2));
			System.out.println(customer.getCustomerDetails());
			System.out.println(customer.getCustomerCoupons());
			System.out.println("why you do this?!");
			System.out.println(company.getOneForTheWeb(1));
			

		} catch (Exception e) {
			e.getMessage();
		}

	}

}
