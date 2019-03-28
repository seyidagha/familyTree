package com.family.app;

import java.io.IOException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.family.models.FamilyException;

@ControllerAdvice
public class ExceptionController {
	@RequestMapping(value = "/exception/{type}", method = RequestMethod.GET)
	public String exception(@PathVariable(name = "type") String exception) throws IOException {

		if (exception.equalsIgnoreCase("error")) {
			throw new FamilyException("A1001", "This is a custom exception message.");
		} else if (exception.equalsIgnoreCase("io-error")) {
			throw new IOException();
		} else {
			return "success";
		}
	}

	@ExceptionHandler(FamilyException.class)
	public ModelAndView handleMyException(FamilyException mex) {

		ModelAndView model = new ModelAndView();
		model.addObject("errCode", mex.getErrCode());
		model.addObject("errMsg", mex.getErrMsg());
		model.setViewName("error/generic_error");
		return model;
	}

	// @ExceptionHandler(Exception.class)
	// public ModelAndView handleException(Exception ex) {
	//
	// ModelAndView model = new ModelAndView();
	// model.addObject("errMsg", "This is a 'Exception.class' message.");
	// model.setViewName("error/generic_error");
	// return model;
	//
	// }
}
