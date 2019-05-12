package com.hit.server;

import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.lang.reflect.Type;
import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class HandleRequest<T> extends java.lang.Object implements java.lang.Runnable {
	Socket socket;
	CacheUnitController<T> controller;

	public HandleRequest(Socket s, CacheUnitController<T> controller) {
		socket = s;
		this.controller = controller;
	}

	@Override
	public void run() {
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		Request<DataModel<T>[]> request = null;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());

			String req = null;
			req = (String) in.readObject();  // Here we are reading the request from the Client.
			System.out.println(req);
			
			if (req.equals("showStatistics")) {
				String response = controller.getStatistics();
				out.writeObject(response);
				out.flush();
				
			} else {
				Type ref = new TypeToken<Request<DataModel<T>[]>>() {}.getType();			
				request = new Gson().fromJson(req, ref);
				String actionType = request.getHeaders().get("action");

					switch (actionType) {
						case "GET": {
							DataModel<T>[] DMresponse = controller.get((DataModel<T>[]) request.getBody());
							if (DMresponse.length == 0) { // If we didnt get any DM.
								out.writeObject("There is nothing to show yet."); // return message to the client
								out.flush();
							}
							else {
								String temp=" ";
								System.out.println("\n");
								for (int i=0;i<DMresponse.length;i++)
								{
									if (DMresponse[i]==null)
										i++;
									temp+= DMresponse[i].toString();
									temp+="\n ";
								}
								
								System.out.println(temp);
								//String json = new Gson().toJson(temp);
								out.writeObject(temp);
								out.flush();
							}
							break;
						}
						case "UPDATE": {
							Boolean response = controller.update((DataModel<T>[]) request.getBody());
							if (response.equals(true)) {
								out.writeObject("'Update' request succeeded"); // return message to the client
							} else
								out.writeObject("'Update' request failed"); // return message to the client
							out.flush();
							break;
						}
						case "DELETE": {
							Boolean response = controller.delete((DataModel<T>[]) request.getBody());
							if (response.equals(true)) {
								out.writeObject("'Delete' request succeeded"); // return message to the client
							} else
								out.writeObject("'Delete' request failed"); // return message to the client
							out.flush();
							break;
						}
					}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// }
	}
}
