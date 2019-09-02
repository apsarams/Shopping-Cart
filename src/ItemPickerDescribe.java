
import java.lang.Object.*;

public class ItemPickerDescribe extends Object implements Command
{
	ItemPickerApplet cart;

	public ItemPickerDescribe(ItemPickerApplet cart)
	{
		this.cart = cart;
	}

	public void doCommand()
	{
		cart.doDescribe();
	}
}