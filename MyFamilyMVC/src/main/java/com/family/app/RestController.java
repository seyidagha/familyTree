package com.family.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.family.models.DatabaseModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class RestController {
	@Autowired
	private DatabaseModel databaseModel;
	private static final Logger logger = LoggerFactory.getLogger(RestController.class);

	@RequestMapping(value = "/getPerson/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ModelAndView getJsonPerson(@PathVariable("id") int id) {
		logger.info("Rest Controller getJsonPerson");
		String jsonInString = "Empty";
		try {
			List<List<Map<String, Object>>> l = databaseModel.nodesEdges(id, new ArrayList<Integer>(),
					new ArrayList<>(), new ArrayList<>());
			ObjectMapper mapper = new ObjectMapper();
			jsonInString = mapper.writeValueAsString(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView model = new ModelAndView();
		model.setViewName("personTree");
		model.addObject("jsonList", jsonInString);
		return model;
	}
}
