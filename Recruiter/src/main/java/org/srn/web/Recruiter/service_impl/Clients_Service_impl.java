package org.srn.web.Recruiter.service_impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.srn.web.Recruiter.Configuration.filters.MSRequestFilter;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dao_impl.Client_dao_Impl;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.Clients;
import org.srn.web.Recruiter.entity.Clients.ClientType;
import org.srn.web.Recruiter.entity.Clients.SubscriptionType;

@Service
@Transactional
public class Clients_Service_impl {

	@Autowired
	private Client_dao_Impl clients_dao_Impl;

	@Autowired
	private MSRequestFilter msRequestFilter;

	public ResponseEntity<ResponseDto> addNewClient(HttpSession session, String token, String client_name,
			String client_type, String website, String email, String contact, String address, String subscription_type,
			int is_subscribed) {
		// TODO Auto-generated method stub
		ResponseDto response = new ResponseDto();

		msRequestFilter.validateToken(session, token);
		try {
			if (session.getAttribute("isSession") != "true") {

				response.setMessage("Please login!");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

		try {
			if (client_name == null || client_name.isEmpty()) {
				response.setMessage("Name is mandatory !");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			if (client_type == null || client_type.isEmpty()) {
				response.setMessage("Client type is mandatory !");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			if (contact == null || contact.isEmpty()) {
				response.setMessage("Contact is mandatory !");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			if (email == null || email.isEmpty()) {
				response.setMessage("Email is mandatory !");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			if (address == null || address.isEmpty()) {
				response.setMessage("Address is mandatory !");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			if (website == null || website.isEmpty()) {
				response.setMessage("Website is mandatory !");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			if (subscription_type == null || subscription_type.isEmpty()) {
				response.setMessage("subscription_type is mandatory !");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

			if (is_subscribed == -1) {
				response.setMessage("is_subscribed is mandatory !");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			if (is_subscribed != 0 && is_subscribed != 1) {
				response.setMessage("is_subscribed can be either  0 or 1 !");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Internal server error !");
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

		try {
			Clients client = new Clients();
			client.setClient_name(client_name);
			if (ClientType.INDIVIDUAL.toString().contentEquals(client_type)) {
				client.setClient_type(ClientType.INDIVIDUAL);
			} else if (ClientType.ORGANIZATION.toString().contentEquals(client_type)) {
				client.setClient_type(ClientType.ORGANIZATION);
			} else if (ClientType.PLATFORM.toString().contentEquals(client_type)) {
				client.setClient_type(ClientType.PLATFORM);
			}
			client.setContact(contact);
			client.setEmail(email);
			client.setWebsite(website);
			client.setAddress(address);
			client.setIs_subscribed(is_subscribed);
			client.setCreated_by((String) session.getAttribute("email"));
			client.setStatus(0);
			client.setDt(new Date());
			// DEMO, SUBSCRIPTION

			if (SubscriptionType.DEMO.toString().contentEquals(subscription_type)) {
				client.setSubscription_type(SubscriptionType.DEMO);
			} else if (SubscriptionType.SUBSCRIPTION.toString().contentEquals(subscription_type)) {
				client.setSubscription_type(SubscriptionType.SUBSCRIPTION);
			}

			boolean check = clients_dao_Impl.save(client);
			System.out.println(check);
			if (check == true) {
				response.setMessage("Client saved succesfully");
				response.setBody(client);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage("New client is not created !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Internal server error");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}

	public ResponseEntity<ResponseDto> searchClients(HttpSession session, String token, String clientId,
			String clientName, String contact, String email, String website, String clientType, String address,
			String subscriptionType, String isSubscribed, String createdBy, String dt, String status) {
		// TODO Auto-generated method stub
		ResponseDto response = new ResponseDto();

		msRequestFilter.validateToken(session, token);
		List<Clients> list = new ArrayList<Clients>();
		try {
			if (session.getAttribute("isSession") != "true") {

				response.setMessage("Please login!");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

		if (!clientId.isBlank() && clientName.isBlank() && contact.isBlank() && email.isBlank() && website.isBlank()
				&& clientType.isBlank() && address.isBlank() && subscriptionType.isBlank() && isSubscribed.isBlank()
				&& createdBy.isBlank() && dt.isBlank() && status.isBlank()) {
			long client_id;
			try {
				client_id = Long.parseLong(clientId);
			} catch (Exception e) {
				response.setMessage("Enter valid client id !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			list = clients_dao_Impl.getByClientId(session, client_id);
			if (list == null || list.isEmpty()) {
				response.setMessage("Does not found");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage("Searched result : !");
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} else if (clientId.isBlank() && !clientName.isBlank() && contact.isBlank() && email.isBlank()
				&& website.isBlank() && clientType.isBlank() && address.isBlank() && subscriptionType.isBlank()
				&& isSubscribed.isBlank() && createdBy.isBlank() && dt.isBlank() && status.isBlank()) {
			list = clients_dao_Impl.getByClientName(session, clientName);
			if (list == null || list.isEmpty()) {
				response.setMessage("Does not found");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage("Searched result : !");
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} else if (clientId.isBlank() && clientName.isBlank() && !contact.isBlank() && email.isBlank()
				&& website.isBlank() && clientType.isBlank() && address.isBlank() && subscriptionType.isBlank()
				&& isSubscribed.isBlank() && createdBy.isBlank() && dt.isBlank() && status.isBlank()) {
			list = clients_dao_Impl.getByContact(session, contact);

			if (list == null || list.isEmpty()) {
				response.setMessage("Does not found");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage("Searched result : !");
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} else if (clientId.isBlank() && clientName.isBlank() && contact.isBlank() && !email.isBlank()
				&& website.isBlank() && clientType.isBlank() && address.isBlank() && subscriptionType.isBlank()
				&& isSubscribed.isBlank() && createdBy.isBlank() && dt.isBlank() && status.isBlank()) {
			list = clients_dao_Impl.getByEmail(session, email);
			if (list == null || list.isEmpty()) {
				response.setMessage("Does not found");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage("Searched result : !");
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} else if (clientId.isBlank() && clientName.isBlank() && contact.isBlank() && email.isBlank()
				&& !website.isBlank() && clientType.isBlank() && address.isBlank() && subscriptionType.isBlank()
				&& isSubscribed.isBlank() && createdBy.isBlank() && dt.isBlank() && status.isBlank()) {
			list = clients_dao_Impl.getByWebsite(session, website);

			if (list == null || list.isEmpty()) {
				response.setMessage("Does not found");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage("Searched result : !");
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} else if (clientId.isBlank() && clientName.isBlank() && contact.isBlank() && email.isBlank()
				&& website.isBlank() && !clientType.isBlank() && address.isBlank() && subscriptionType.isBlank()
				&& isSubscribed.isBlank() && createdBy.isBlank() && dt.isBlank() && status.isBlank()) {
			list = clients_dao_Impl.getByClientType(session, clientType);

			if (list == null || list.isEmpty()) {
				response.setMessage("Does not found");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage("Searched result : !");
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} else if (clientId.isBlank() && clientName.isBlank() && contact.isBlank() && email.isBlank()
				&& website.isBlank() && clientType.isBlank() && !address.isBlank() && subscriptionType.isBlank()
				&& isSubscribed.isBlank() && createdBy.isBlank() && dt.isBlank() && status.isBlank()) {
			list = clients_dao_Impl.getByAddress(session, address);

			if (list == null || list.isEmpty()) {
				response.setMessage("Does not found");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage("Searched result : !");
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} else if (clientId.isBlank() && clientName.isBlank() && contact.isBlank() && email.isBlank()
				&& website.isBlank() && clientType.isBlank() && address.isBlank() && !subscriptionType.isBlank()
				&& isSubscribed.isBlank() && createdBy.isBlank() && dt.isBlank() && status.isBlank()) {
			list = clients_dao_Impl.getBySubscriptionType(session, subscriptionType);

			if (list == null || list.isEmpty()) {
				response.setMessage("Does not found");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage("Searched result : !");
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} else if (clientId.isBlank() && clientName.isBlank() && contact.isBlank() && email.isBlank()
				&& website.isBlank() && clientType.isBlank() && address.isBlank() && subscriptionType.isBlank()
				&& !isSubscribed .isBlank() && createdBy.isBlank() && dt.isBlank() && status.isBlank()) {
			int isSubs;
			try {
				isSubs = Integer.parseInt(isSubscribed);
			} catch(Exception e) {
				response.setMessage("Invalid isSubscribed enter either 1 or 0 !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			if(isSubs!=0 && isSubs!=1) {
				response.setMessage("Invalid isSubscribed enter either 1 or 0 !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}else {
				list = clients_dao_Impl.getByIsSubscribed(session, isSubs);
			}
			if (list == null || list.isEmpty()) {
				response.setMessage("Does not found");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage("Searched result : !");
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} else if (clientId.isBlank() && clientName.isBlank() && contact.isBlank() && email.isBlank()
				&& website.isBlank() && clientType.isBlank() && address.isBlank() && subscriptionType.isBlank()
				&& isSubscribed.isBlank() && !createdBy.isBlank() && dt.isBlank() && status.isBlank()) {
			list = clients_dao_Impl.getByCreatedBy(session, createdBy);
			if (list == null || list.isEmpty()) {
				response.setMessage("Does not found");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage("Searched result : !");
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} else if (clientId.isBlank() && clientName.isBlank() && contact.isBlank() && email.isBlank()
				&& website.isBlank() && clientType.isBlank() && address.isBlank() && subscriptionType.isBlank()
				&& isSubscribed.isBlank() && createdBy.isBlank() && !dt.isBlank() && status.isBlank()) {
			list = clients_dao_Impl.getByDt(session, dt);
			if (list == null || list.isEmpty()) {
				response.setMessage("Does not found");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage("Searched result : !");
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} else if (clientId.isBlank() && clientName.isBlank() && contact.isBlank() && email.isBlank()
				&& website.isBlank() && clientType.isBlank() && address.isBlank() && subscriptionType.isBlank()
				&& isSubscribed.isBlank() && createdBy.isBlank() && dt.isBlank() && !status.isBlank()) {
			int checkStatus;
			try {
				checkStatus = Integer.parseInt(status);
			} catch(Exception e) {
				response.setMessage("Invalid status enter either 1 or 0 !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			if(checkStatus!=0 && checkStatus!=1) {
				response.setMessage("Invalid status enter either 1 or 0 !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}else {
				list = clients_dao_Impl.getByStatus(session, checkStatus);
			}
			
			if (list == null || list.isEmpty()) {
				response.setMessage("Does not found");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage("Searched result : !");
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} else {
			response.setMessage("Please enter one field !");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	
	}

	public ResponseEntity<ResponseDto> fetchAllClients(HttpSession session, String token) {
		// TODO Auto-generated method stub
		ResponseDto response = new ResponseDto();
		try {
			List<Clients> list = clients_dao_Impl.getAllClients(session);
			if (list == null || list.isEmpty()) {
				response.setMessage("Client does not found !");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.setMessage("Internal server error !");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}
}
