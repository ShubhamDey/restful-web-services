package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	@GetMapping(path = "/static-filtering")
	public SomeBean staticRetriveSomeBean() {
		return new SomeBean("value1", "value2", "value3");
	}
	
	@GetMapping(path = "/static-filtering-list")
	public List<SomeBean> staticRetrieveListofSomeBeans(){
		return Arrays.asList(new SomeBean("value11", "value12", "value13"), new SomeBean("value21", "value22", "value23"));
	}
	
	@GetMapping(path = "/dynamic-filtering")
	public MappingJacksonValue dynamicRetriveSomeOtherBean() {
		SomeOtherBean someOtherBean = new SomeOtherBean("value1", "value2", "value3");
		SimpleBeanPropertyFilter propertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
		FilterProvider filter = new SimpleFilterProvider().addFilter("SomeBeanFilter", propertyFilter);
		MappingJacksonValue mapping = new MappingJacksonValue(someOtherBean);
		mapping.setFilters(filter);
		return mapping;
	}
	
	@GetMapping(path = "/dynamic-filtering-list")
	public MappingJacksonValue retrieveListofSomeOtherBeans(){
		List<SomeOtherBean> someOtherBean= Arrays.asList(new SomeOtherBean("value11", "value12", "value13"), new SomeOtherBean("value21", "value22", "value23"));
		SimpleBeanPropertyFilter propertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
		FilterProvider filter = new SimpleFilterProvider().addFilter("SomeBeanFilter", propertyFilter);
		MappingJacksonValue mapping = new MappingJacksonValue(someOtherBean);
		mapping.setFilters(filter);
		return mapping;
	}
	
}
