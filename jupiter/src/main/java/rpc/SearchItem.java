package rpc;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.MySQLConnection;
import entity.Item;
import external.GitHubClient;


/**
 * Servlet implementation class SearchItem
 */
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//response.setContentType("application/json");
		//PrintWriter writer = response.getWriter();
		
		
		/*
		 * JSONArray array = new JSONArray(); try { array.put(new
		 * JSONObject().put("username", "abcd")); array.put(new
		 * JSONObject().put("username", "1234")); } catch (JSONException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * 
		 * //writer.print(array); RpcHelper.writeJsonArray(response, array);
		 */		 
		
		/*
		 * HttpSession session = request.getSession(false); if (session == null) {
		 * response.setStatus(403); return; }
		 */

		
		
		String userId = request.getParameter("user_id");
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));

		GitHubClient client = new GitHubClient();
		List<Item> items = client.search(lat, lon, null);
		
		
		MySQLConnection connection = new MySQLConnection();
		Set<String> favoritedItemIds = connection.getFavoriteItemIds(userId);
		connection.close();

		
		JSONArray array = new JSONArray();
		for (Item item : items) {
			JSONObject obj = item.toJSONObject();
			try {
				obj.put("favorite", favoritedItemIds.contains(item.getItemId()));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			array.put(obj);

		}
		RpcHelper.writeJsonArray(response, array);


		
		//MySQLConnection connection = new MySQLConnection();
		//Set<String> favoritedItemIds = connection.getFavoriteItemIds(userId);
		//connection.close();

		
		/*
		 * JSONArray array = new JSONArray(); for (Item item : items) { JSONObject obj =
		 * item.toJSONObject(); obj.put("favorite",
		 * favoritedItemIds.contains(item.getItemId())); array.put(obj); }
		 * RpcHelper.writeJsonArray(response, array);
		 */

		
		


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
