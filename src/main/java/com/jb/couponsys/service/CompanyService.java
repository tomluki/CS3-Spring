package com.jb.couponsys.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jb.couponsys.bean.Category;
import com.jb.couponsys.bean.Company;
import com.jb.couponsys.bean.Coupon;
import com.jb.couponsys.exception.InvalidDetailsException;
import com.jb.couponsys.exception.NotInStockException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
@Scope("prototype")
public class CompanyService extends ClientService {
	private int companyID;

	public CompanyService() {
		super();
	}

	public CompanyService(int companyID) {
		this.companyID = companyID;
	}

	@Override
	public boolean login(String email, String password) throws InvalidDetailsException {
		List<Company> companies = companyRepo.findAll();
		for (Company company : companies) {
			if (company.getEmail().equals(email) && company.getPassword().equals(password)) {
				this.companyID = company.getId();
				return true;
			}
		}

		return false;
	}

	public Company getCompanyDetails() {
		return companyRepo.getOne(this.companyID);

	}

	public void addCoupon(Coupon coupon) throws InvalidDetailsException {
		if (coupon.getStartDate().after(coupon.getEndDate())) {
			throw new InvalidDetailsException("A coupon can't have a start date the comes after its end date");
		}
		List<Coupon> coupons = getCompanyCoupons();
		for (Coupon coupon2 : coupons) {
			if (coupon2.getTitle().equals(coupon.getTitle())) {
				throw new InvalidDetailsException("This title already exists");
			}

		}
		coupon.setCompanyId(this.companyID);
		couponRepo.save(coupon);
	}

	public void updateCoupon(Coupon coupon) throws InvalidDetailsException {
		if (coupon.getStartDate().after(coupon.getEndDate())) {
			throw new InvalidDetailsException("A coupon can't have a start date the comes after its end date");
		}

		couponRepo.saveAndFlush(coupon);
	}

	public void deleteCoupon(int couponID) throws NotInStockException {
		List<Coupon> coupons = getCompanyCoupons();
		for (Coupon coupon : coupons) {
			if (coupon.getId() == couponID) {
				Coupon p1 = couponRepo.getOne(couponID);
				couponRepo.delete(p1);
			}
		}
	}

	public Coupon getOneCoupon(int id) throws InvalidDetailsException {
		return couponRepo.getOne(id);
	}

	// this is to present a more user friendly interface
	public List<Coupon> getOneForTheWeb(int couponID) throws InvalidDetailsException {
		return getCompanyCoupons().stream().filter(p -> p.getId() == (couponID)).collect(Collectors.toList());
	}

	public List<Coupon> getCompanyCoupons() {
		return couponRepo.findCouponsByCompanyId(this.companyID);
	}

	public List<Coupon> getCompanyCouponByCategory(Category category) {
		return getCompanyCoupons().stream().filter(p -> p.getCategory() == (category)).collect(Collectors.toList());

	}

	public List<Coupon> getCompanyCouponByPrice(double price) {
		return getCompanyCoupons().stream().filter(p -> p.getPrice() <= price).collect(Collectors.toList());
	}
}
