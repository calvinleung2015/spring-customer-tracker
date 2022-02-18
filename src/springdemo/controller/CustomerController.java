package springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springdemo.dao.CustomerDAO;
import springdemo.entity.Customer;
import springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	// need to inject our customer service
	// Spring will scan for a component that implements CustomerService interface
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list") //Requests data from given resource. **data is visible in URL and will show in URL
	public String listCustomers(Model theModel) {
		
		// get customer from the service
		List<Customer> theCustomers = customerService.getCustomers();
		
		//**The model is a container for your application data
		//**You can put anything in the model - info from database, strings, objects, etc...
		//**Your view page (JSP) can access data from the model
		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Customer theCustomer = new Customer();
		//"customer" is a name - you can give any attribute name, the Customer is a value.
		theModel.addAttribute("customer", theCustomer); 
		return "customer-form";
	}
	
	//saveCustomer is based on the info from customer-form.jsp
	@PostMapping("/saveCustomer") // Submits data to given resource
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		
		//save the customer using our service
		customerService.saveCustomer(theCustomer);
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
		
		// get the customer from the service
		Customer theCustomer = customerService.getCustomer(theId);
		
		// set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);
		
		//send over to our form
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
		
		//delete the customer
		customerService.deleteCustomer(theId);
		
		
		return "redirect:/customer/list";
	}
}
