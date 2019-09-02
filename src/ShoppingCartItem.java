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

	public void add(ShoppingCartItem otherItem)
	{
		this.quantity = this.quantity + otherItem.quantity;
	}

	public void subtract(ShoppingCartItem otherItem)
	{
		this.quantity = this.quantity - otherItem.quantity;
	}

	public int hashCode()
	{
		return itemName.hashCode() + itemCost;
	}

	public boolean equals(Object other)
	{
		if (this == other) return true;

		if (!(other instanceof ShoppingCartItem))
			return false;

		ShoppingCartItem otherItem = (ShoppingCartItem) other;

		return (itemName.equals(otherItem.itemName)) && (itemCost == otherItem.itemCost);
	}

	public ShoppingCartItem copy()
	{
		return new ShoppingCartItem(itemName, itemCost, quantity, descriptionURL);
	}

	public String toString()
	{
		return itemName+" cost: "+itemCost+" qty: "+quantity+" desc: "+
			descriptionURL;
	}
}
