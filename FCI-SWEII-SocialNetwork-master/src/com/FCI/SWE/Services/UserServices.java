package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.UserEntity;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class UserServices {
	
	
	/*@GET
	@Path("/index")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}*/
	
	/*@POST
	@Path("/SearchService")
	public String searchFriend(@FormParam("uname") String uname){
		
	}*/


		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
			object.put("id", user.getId());
		}
		return object.toString();

	}
	@POST
	@Path("/signoutService")
	public String signoutService() {
		User.setCurrentActiveUser();
		JSONObject object = new JSONObject();
		object.put("status"," success");
		
			return object.toString();

	}
	@POST
	@Path("/sendrequest")
	public String sendrequestService(@FormParam("friendemail") String uemail)
			{
		JSONObject object = new JSONObject();
		UserEntity.sendrequest(uemail);
		
		object.put("status"," success");
		//System.out.print(uemail);
		//object.put("status", "accept");
		return object.toString();

	}
	
	@POST
	@Path("/acceptrequest")
	public String acceptrequestService(@FormParam("acceptfriend") String femail)
			{
		JSONObject object = new JSONObject();
		UserEntity.getrequest(femail);
		
     object.put("status"," accept");
		return object.toString();

	}

	@POST
	@Path("/search")
	public String searchService(@FormParam("searchemail") String email) {
		JSONObject object = new JSONObject();
	UserEntity user = UserEntity.search(email);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			//object.put("password", user.getPass());
			//object.put("id", user.getId());
		}
		return object.toJSONString();

	}

	
	@POST
	@Path("/SearchOnPeopleAdd")
	public String SearchOnPeopleAdd() {
		JSONObject object = new JSONObject();
	    ArrayList<String> request = UserEntity.SearchOnPeopleAdd();
	    JSONArray arr= new JSONArray();
	    
		if (request == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			for(int i=0;i<request.size();i++)
			{
				arr.add(request.get(i));
			}
			object.put("request", arr);
		}
		return object.toJSONString();

	}


}