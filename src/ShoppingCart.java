import java.applet.*;
import java.awt.*;
import java.net.*;
import java.util.*;


public class ShoppingCart extends Observable
{
	protected Vector items;	// the items in the cart
	protected int total;	// the total item

	public ShoppingCart()
	{
		items = new Vector();
		total = 0;
	}

// Add a new item and update the total

	public void addItem(ShoppingCartItem newItem)
	{

// See if there's already an item like this in the cart
		int currIndex = items.indexOf(newItem);

		ShoppingCartEvent event = new ShoppingCartEvent();

		if (currIndex == -1) {
// If the item is new, add it to the cart
			items.addElement(newItem);
			event.item = newItem;
			event.eventType = ShoppingCartEvent.ADDED_ITEM;
		} else {

// If there is a similar item, just add the quantities
		ShoppingCartItem currItem =
			(ShoppingCartItem) 
			items.elementAt(currIndex);

		currItem.add(newItem);
		event.item = currItem;
		event.eventType = ShoppingCartEvent.CHANGED_ITEM;
	}

	total += newItem.itemCost * newItem.quantity;

// Dialog display
	setChanged();
	notifyObservers(event);
	}

// Remove item removes an item from the cart and subtract the quantity.

	public void removeItem(ShoppingCartItem oldItem)
	{
// Find this object in the cart
	int currIndex = items.indexOf(oldItem);
	ShoppingCartEvent event = new ShoppingCartEvent();

	if (currIndex == -1) {
// If it wasn't there, just return 
		return;
	} else {
		ShoppingCartItem currItem =
				(ShoppingCartItem)
				items.elementAt(currIndex);

		if (oldItem.quantity > currItem.quantity) {
			oldItem.quantity = currItem.quantity;
		}

// Adjust the total
		total -= oldItem.itemCost * oldItem.quantity;

		currItem.subtract(oldItem);

		event.item = currItem;
		event.eventType = ShoppingCartEvent.CHANGED_ITEM;

// If the quantity drops to 0, remove the item entirely

		if (currItem.quantity == 0) {
			items.removeElementAt(currIndex);
			event.eventType =
				ShoppingCartEvent.REMOVED_ITEM;
		}
			
		}

// Dialog display

	setChanged();
	notifyObservers(event);
	}

// getItems: returns a copy of all the items in the cart

	public ShoppingCartItem[] getItems()
	{
	ShoppingCartItem[] itemArray = 
		new ShoppingCartItem[items.size()];

	items.copyInto(itemArray);

	return itemArray;
	}
}
