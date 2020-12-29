package com.jb.couponsys.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jb.couponsys.bean.Category;
import com.jb.couponsys.bean.Coupon;
import com.jb.couponsys.bean.LoginResponse;
import com.jb.couponsys.login.ClientType;
import com.jb.couponsys.login.LoginManager;
import com.jb.couponsys.login.TokensManager;
import com.jb.couponsys.service.CompanyService;

import net.bytebuddy.asm.Advice.This;

@RestController
@RequestMapping("company")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class CompanyController {
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private TokensManager tokensManager;

//    //http://localhost:8080/company/signup/email/password
	@PostMapping("signup/{email}/{password}")
	public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
		try {
			String token = loginManager.login(email, password, ClientType.Company);
			return new ResponseEntity<>(new LoginResponse(token), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
		}
	}

	// http://localhost:8080/company/company-details
	@GetMapping("company-details")
	public ResponseEntity<?> getCompanyDetails(@RequestHeader("Authorization") String token) {
		try {
			tokensManager.isTokenExist(token);
			CompanyService companyService;
			companyService = (CompanyService) tokensManager.GetUserData(token).getClientService();
			return new ResponseEntity<>(companyService.getCompanyDetails(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/company/add-coupon
	@PostMapping("add-coupon")
	public ResponseEntity<?> addCoupon(@RequestHeader("Authorization") String token, @RequestBody Coupon coupon) {
		try {
			tokensManager.isTokenExist(token);
			CompanyService companyService;
			companyService = (CompanyService) tokensManager.GetUserData(token).getClientService();
			companyService.addCoupon(coupon);
			return new ResponseEntity<>( HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/company/update-coupon
	@PutMapping("update-coupon")
	public ResponseEntity<?> updateCoupon(@RequestHeader("Authorization") String token, @RequestBody Coupon coupon) {
		try {
			tokensManager.isTokenExist(token);
			CompanyService companyService;
			companyService = (CompanyService) tokensManager.GetUserData(token).getClientService();
			companyService.updateCoupon(coupon);
			return new ResponseEntity<>( HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/company/delete-coupon/{id}
	@DeleteMapping("delete-coupon/{id}")
	public ResponseEntity<?> deleteCoupon(@RequestHeader("Authorization") String token, @PathVariable int id) {
		try {
			tokensManager.isTokenExist(token);
			CompanyService companyService;
			companyService = (CompanyService) tokensManager.GetUserData(token).getClientService();
			companyService.deleteCoupon(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/company/get-coupon/{id}
	@GetMapping("get-coupon/{id}")
	public ResponseEntity<?> getOneCoupon(@RequestHeader("Authorization") String token, @PathVariable int id) {
		try {
			tokensManager.isTokenExist(token);
			CompanyService companyService;
			companyService = (CompanyService) tokensManager.GetUserData(token).getClientService();
			return new ResponseEntity<>(companyService.getOneForTheWeb(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/company/get-coupons
	@GetMapping("get-coupons")
	public ResponseEntity<?> getCompanyCoupons(@RequestHeader("Authorization") String token) {
		try {
			tokensManager.isTokenExist(token);
			CompanyService companyService;
			companyService = (CompanyService) tokensManager.GetUserData(token).getClientService();
			return new ResponseEntity<>(companyService.getCompanyCoupons(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/company/get-coupon/{category}
	@GetMapping("get-coupon/{category}")
	public ResponseEntity<?> getCouponsByCategory(@RequestHeader("Authorization") String token,
			@RequestParam Category category) {
		try {
			tokensManager.isTokenExist(token);
			CompanyService companyService;
			companyService = (CompanyService) tokensManager.GetUserData(token).getClientService();
			return new ResponseEntity<>(companyService.getCompanyCouponByCategory(category), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// http://localhost:8080/company/get-coupon/{price}
	@GetMapping("get-coupon/{price}")
	public ResponseEntity<?> getCouponsByPrice(@RequestHeader("Authorization") String token,
			@RequestParam("price") Double price) {
		try {
			tokensManager.isTokenExist(token);
			CompanyService companyService;
			companyService = (CompanyService) tokensManager.GetUserData(token).getClientService();
			return new ResponseEntity<>(companyService.getCompanyCouponByPrice(price), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
