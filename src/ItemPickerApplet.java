import java.awt.*;
import java.applet.*;
import java.net.*;
import java.util.*;
import java.io.*;
import javax.swing.JApplet.*;
// This class represents the catalog portion of a shopping cart.
// You can select items and then either view a description of
// the item or add the item to the shopping cart.

public class ItemPickerApplet extends Applet implements Observer
{
	ObjectList items;
	ShoppingCart cart;
	AppletRegistry registry;

	public void init()
	{
// Watch the applet registry to see when the Shopping Cart applet
// becomes active
		registry = AppletRegistry.instance();
		registry.addObserver(this);

		items = new ObjectList();

// Get the URL of the list of items that are for sale
		String itemURL = getParameter("itemList");
		if (itemURL != null) fetchItems(itemURL);

// Put the items in the center of the screen 
		setLayout(new BorderLayout());
		add("Center", items);

		checkForShoppingCart();

// Add this applet to the registry
		registry.addApplet("Item Picker", this);
	}

	public void checkForShoppingCart()
	{
// See if the shopping cart has been loaded yet
		Applet applet = registry.findApplet("Shopping Cart");
		if (applet == null) return;

		ShoppingCartApplet cartApplet = (ShoppingCartApplet)
			applet;

// Get the shopping cart used by the shopping cart applet
		cart = cartApplet.getShoppingCart();

// Create the panel for adding items
		Panel southPanel = new Panel();

// Set up some command buttons for adding and describing items
		southPanel.add(new CommandButton("Describe Item",new ItemPickerDescribe(this)));
		southPanel.add(new CommandButton("Add Item",new ItemPickerDescribe(this)));

		add("South", southPanel);
	}

	public void update(Observable obs, Object ob)
	{
		if (cart != null) return;

		checkForShoppingCart();
	}

	public void doAdd()
	{
// Find out what object was selected
		Object ob = items.getSelectedObject();

		if (ob == null) return;

// Add the item to the cart
		cart.addItem(((ShoppingCartItem)ob).copy());
	}


	public void doDescribe()
	{

// Find out which object was selected
		Object ob = items.getSelectedObject();

		if (ob == null) return;

		ShoppingCartItem item = (ShoppingCartItem) ob;

// If it has a description URL, open it up in another frame
		if (item.descriptionURL != null) {
			getAppletContext().showDocument(
				item.descriptionURL, "descframe");
		}
	}

	public void parseItem(String str)
	{
		StringTokenizer tokenizer = new StringTokenizer(str, "|");

		if (tokenizer.countTokens() < 3) return;

		String name = tokenizer.nextToken();

		int cost = 0;
		try {
			cost = Integer.parseInt(tokenizer.nextToken());
		} catch (Exception ignore) {
		}

		URL descURL = null;

		try {
			descURL = new URL(tokenizer.nextToken());
		} catch (Exception ignore) {
		}

		items.addObject(name,
			new ShoppingCartItem(name, cost, 1, descURL));

	}

// fetchItems gets a list of available items

	public void fetchItems(String urlName)
	{
		try {
			URL url = new URL(urlName);

			DataInputStream inStream =
				new DataInputStream(
					url.openStream());

			String line;

			while ((line = inStream.readLine()) != null) {
				if (line.charAt(0) == '#') continue;
				parseItem(line);		
			}
		} catch (Exception e) {
		}
	}
}
