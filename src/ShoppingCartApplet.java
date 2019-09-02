import java.applet.*;
import java.awt.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.lang.Object.*;
//import org.primefaces.component.commandbutton;

// This class provides a user interface for the ShoppingCart class

public class ShoppingCartApplet extends Applet implements Observer
{
	protected ShoppingCart cart;
	protected ObjectList itemList;
	protected TextField customerName;
	protected TextField totalField;

	public ShoppingCartApplet()
	{
// Make this class an observer of the shopping cart
		cart = new ShoppingCart();
		cart.addObserver(this);

// Create the list of objects in the cart
		itemList = new ObjectList();

// Create the field for the total cost so far
		totalField = new TextField(10);
		totalField.setEditable(false);
		totalField.setText("Total: "+cart.total);

		setLayout(new BorderLayout());

// Create a field for the customer name
		customerName = new TextField(20);

// Combine the label and the name field on a single panel
		Panel namePanel = new Panel();
		namePanel.add(new Label("Customer Name: "));
		namePanel.add(customerName);

// Put the name field up at the top and the item list in the center
		add("North", namePanel);
		add("Center", itemList);

// Create buttons for removing items and placing an order and put
// them along the bottom.

		Panel southPanel = new Panel();
		southPanel.add(new CommandButton("Remove Item"));//,new ShoppingCartRemove(this)));
		southPanel.add(new CommandButton("Place Order"));//,new ShoppingCartOrder(this)));
		southPanel.add(totalField);

		add("South", southPanel);

// Tell the applet registry about this applet
		AppletRegistry.instance().addApplet("Shopping Cart", this);
	}

	public String makeItemString(ShoppingCartItem item)
	{
		return item.itemName+"   Qty: "+item.quantity+
			"  Cost: "+item.itemCost;
	}

	public void update(Observable whichCart, Object ob)
	{
		ShoppingCartEvent event = (ShoppingCartEvent) ob;

		if (event.eventType == ShoppingCartEvent.ADDED_ITEM) {
// If there is a new item in the cart, add it to the scrollable list
			itemList.addObject(makeItemString(event.item),
				event.item);
			totalField.setText("Total: "+cart.total);
			itemList.validate();
		} else if (event.eventType ==
// If an item has been removed from the cart, remove it from the list
			ShoppingCartEvent.REMOVED_ITEM) {
			itemList.delObject(event.item);
			totalField.setText("Total: "+cart.total);
			itemList.validate();
		} else if (event.eventType ==
			ShoppingCartEvent.CHANGED_ITEM) {
// If an item has changed, update the list
			int index = itemList.indexOf(event.item);
			itemList.replaceObject(makeItemString(
				event.item), event.item, index);
			totalField.setText("Total: "+cart.total);
			itemList.validate();
		}
	}

// If the user clicks on "Remove Item," remove it from he list
	public void doRemove()
	{
		Object ob = itemList.getSelectedObject();
		if (ob == null) return;

		ShoppingCartItem item = ((ShoppingCartItem)ob).copy();
		item.quantity = 1;
		cart.removeItem(item);
	}

// doPlaceOrder uses PostSockURL to post the order to a web
// server. You will need to customize this method to fit your needs.

	public void doPlaceOrder()
	{
		try {
			URL postURL = new URL(
				getDocumentBase().getProtocol(),
				getDocumentBase().getHost(),
				getDocumentBase().getPort(),
				"/shopping");

			ByteArrayOutputStream byteOut = 
				new ByteArrayOutputStream();
			PrintStream outStream = 
				new PrintStream(byteOut);

			outStream.println("Custname: "+
				customerName.getText());
			ShoppingCartItem[] items = cart.getItems();
			for (int i=0; i < items.length; i++) {
				outStream.println(
				items[i].itemName+"|"+
				items[i].quantity);
			}

			//String response = PostSockURL.post(postURL,byteOut.toString());
			//System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}

	public ShoppingCart getShoppingCart()
	{
		return cart;
	}
}