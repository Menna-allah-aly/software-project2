package com.FCI.SWE.Controller;

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
@Produces("text/html")
public class UserController {
	/**
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	@POST
	@Path("/doSearch")
	/**
	 * 
	 * @param uname
	 * @return
	 */
	public Response usersList(@FormParam("uname") String uname){
		System.out.println(uname);
		String serviceUrl = "http://localhost:8888/rest/SearchService";
		String urlParameters = "uname=" + uname;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		return null;
	}
	@GET
	@Path("/signup")
	public Response signUp() {
		return Response.ok(new Viewable("/jsp/register")).build();
	}

	/**
	 * 
	 * @param email 
	 * this function taking email for search to find specific person
	 * @return null 
	 */
	@POST
	@Path("/search")
	public Response search(@FormParam("searchemail") String email){
	//	return Response.ok(new Viewable("/jsp/home")).build();
		String serviceUrl = "http://localhost:8888/rest/search";
		String urlParameters = "searchemail=" + email;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			
			Map<String, String> map = new HashMap<String, String>();
			//User user = User.getUser(object.toJSONString());
			if (object.get("Status").toString().equals("OK")){
			map.put("name", object.get("name").toString());
		//	map.put("email", user.getEmail());
			return Response.ok(new Viewable("/jsp/search", map)).build();}
			else{
				map.put("name", "the name not found :s");
				//	map.put("email", user.getEmail());
					return Response.ok(new Viewable("/jsp/notfounded", map)).build();}

			
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Action function to render home page of application, home page contains
	 * only signup and login buttons
	 * 
	 * @return enty point page (Home page of this application)
	 */
	
	/**
	 * his function w make it to search on specific person who i want to accept his friend request
	 * @return string
	 */
	
	@POST
	@Path("/acceptsearch")
	public String searchforrequest(){
	//	return Response.ok(new Viewable("/jsp/home")).build();
		String serviceUrl = "http://localhost:8888/rest/SearchOnPeopleAdd";
		//String urlParameters = "searchemail=" + email;
		String retJson = Connection.connect(serviceUrl, "", "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			
			Map<String, String> map = new HashMap<String, String>();
			//User user = User.getUser(object.toJSONString());
			JSONArray arr= (JSONArray) object.get("request");
			if (object.get("Status").toString().equals("OK")){
				for(Integer i=0;i<arr.size();i++)
				map.put("name"+i.toString(), arr.get(i).toString());
		//	map.put("email", user.getEmail());
//			return Response.ok(new Viewable("/jsp/searchonrequestedpeople", map)).build();
				return arr.toJSONString(); }
			else{
				map.put("name", "the name not found :s");
				//	map.put("email", user.getEmail());
			//		return Response.ok(new Viewable("/jsp/notfounded", map)).build();
			return "ss"	;
			}

			
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	
	
	
	
	
	
	
	@GET
	@Path("/")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to render login page this function will be executed using
	 * url like this /rest/login
	 * 
	 * @return login page
	 */
	@GET
	@Path("/login")
	public Response login() {
		return Response.ok(new Viewable("/jsp/login")).build();
	}
	

	/**
	 * Action function to response to signup request, This function will act as
	 * a controller part and it will calls RegistrationService to make
	 * registration
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided user password
	 * @return Status string
	 */
	@POST
	@Path("/response")
	@Produces(MediaType.TEXT_PLAIN)
	public String response(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {

		String serviceUrl = "http://localhost:8888/rest/RegistrationService";
		String urlParameters = "uname=" + uname + "&email=" + email
				+ "&password=" + pass;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "Registered Successfully";

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return "Failed";
	}

	/**
	 * Action function to response to login request. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 * 
	 * @param uname
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return Home page view
	 */
	
	/**
	 * 
	 * @param uname
	 * @param pass
	 * this func taking name password for loging
	 * @return null
	 * 
	 */
	@POST
	@Path("/home")
	@Produces("text/html")
	public Response home(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		String urlParameters = "uname=" + uname + "&password=" + pass;

		String retJson = Connection.connect(
				"http://localhost:8888/rest/LoginService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			User user = User.getUser(object.toJSONString());
			map.put("name", user.getName());
			map.put("email", user.getEmail());
			return Response.ok(new Viewable("/jsp/home", map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;

	}
	/**
	 * this function for sign out current active user
	 * @return response
	 */
	@POST
	@Path("/signout")
	@Produces("text/html")
	public Response signout(){
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}
	
	/**
	 * 
	 * @param femail
	 * for sending request
	 * @return response
	 */
	@POST
	@Path("/sendrequest")
	@Produces("text/html")
	public Response sendrequest(@FormParam("friendemail") String femail){
		String serviceUrl = "http://localhost:8888/rest/sendrequest";
		String urlParameters = "friendemail=" + femail ;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		return Response.ok(new Viewable("/jsp/home")).build();
	}
	/**
	 * 
	 * @param femail 
	 * fun for accept request of friend
	 * @return response
	 */
	@POST
	@Path("/acceptrequest")
	@Produces("text/html")
	public Response acceptrequest(@FormParam("acceptfriend") String femail){
		String serviceUrl = "http://localhost:8888/rest/acceptrequest";
		String urlParameters = "acceptfriend=" + femail ;
		String retJson = Connection.connect(serviceUrl ,urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		return Response.ok(new Viewable("/jsp/accept")).build();
	}
	
	/**
	 * 
	 * @return reponse
	 */
	@POST
	@Path("/accept")
	@Produces("text/html")
	public Response accept(){
		return Response.ok(new Viewable("/jsp/home")).build();
			
	}
	
	/**
	 * 
	 * @param friendemail
	 * @param message
	 * for sending message to specifi person
	 * @return null
	 */
	
	@POST
	@Path("/sendmsg")
	@Produces("text/html")
	public Response sendmsg(@FormParam("friendemail") String friendemail,@FormParam("sendmsg")String message){
		System.out.println(message);
		String serviceUrl = "http://localhost:8888/rest/sendmsg";
		String urlParameters = "friendemail=" + friendemail+"&sendmsg=" + message ;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		//return Response.ok(new Viewable("/jsp/home")).build();
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("success"))
				return Response.ok(new Viewable("/jsp/home")).build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @return response
	 */
	
	@POST
	@Path("/group_message")
	@Produces("text/html")
	public Response group(){
		return Response.ok(new Viewable("/jsp/group")).build();
			
	}
	
/**
 * botton for back to last page
 * @return response
 */
	@POST
	@Path("/back")
	@Produces("text/html")
	public Response back(){
		return Response.ok(new Viewable("/jsp/home")).build();
			
	}
	/**
	 * 
	 * @param member_name
	 * @param group_id
	 * this fun used for sadd memeber to specidic group
	 * @return null
	 */
	@POST
	@Path("/group")
	@Produces("text/html")
	public Response sendgroup(@FormParam("membername") String member_name,@FormParam("ID")String group_id){
		//System.out.println(message);
		String serviceUrl = "http://localhost:8888/rest/group";
		String urlParameters = "membername=" + member_name+"&ID=" + group_id ;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		//return Response.ok(new Viewable("/jsp/home")).build();
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("success"))
				return Response.ok(new Viewable("/jsp/group")).build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param chat_msg
	 * @param chat_id
	 * this fun for sending message for group
	 * @return null
	 */
	
	@POST
	@Path("/sendgroupmsg")
	@Produces("text/html")
	public Response chat_message(@FormParam("message_of_group") String chat_msg,@FormParam("chat_id")int chat_id){
		//System.out.println(message);
		String serviceUrl = "http://localhost:8888/rest/sendgroupmsg";
		String urlParameters = "message_of_group=" + chat_msg+"&chat_id=" + chat_id ;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		//return Response.ok(new Viewable("/jsp/home")).build();
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("success"))
				return Response.ok(new Viewable("/jsp/group")).build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}