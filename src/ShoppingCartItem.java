import java.net.URL;

public class ShoppingCartItem implements Cloneable
{
	public String itemName;
	public int itemCost;
	public int quantity;
	public URL descriptionURL;

	public ShoppingCartItem()
	{
	}

	public ShoppingCartItem(String itemName, int itemCost, int quantity, URL descriptionURL)
	{
		this.itemName = itemName;
		this.itemCost = itemCost;
		this.quantity = quantity;
		this.descriptionURL = descriptionURL;
	}

// The add method is a quick method for combining two similar
// items.

	public void add(ShoppingCartItem otherItem)
	{
		this.quantity = this.quantity + otherItem.quantity;
	}

// The subtract method is similar to the add method, but it
// removes a certain quantity of items.

	public void subtract(ShoppingCartItem otherItem)
	{
		this.quantity = this.quantity - otherItem.quantity;
	}

// You can store items in a hash table if you implement hashCode. It's
// always a good idea to do this.

	public int hashCode()
	{
		return itemName.hashCode() + itemCost;
	}

// it only
// compares the item names and item costs.

	public boolean equals(Object other)
	{
		if (this == other) return true;

		if (!(other instanceof ShoppingCartItem))
			return false;

		ShoppingCartItem otherItem = (ShoppingCartItem) other;

		return (itemName.equals(otherItem.itemName)) && (itemCost == otherItem.itemCost);
	}

// Create a copy of this object

	public ShoppingCartItem copy()
	{
		return new ShoppingCartItem(itemName, itemCost, quantity, descriptionURL);
	}

// Create a printable version of this object

	public String toString()
	{
		return itemName+" cost: "+itemCost+" qty: "+quantity+" desc: "+
			descriptionURL;
	}
}