import java.awt.*;
import java.awt.List;
import java.util.*;

// This class is a special version of a scrollable list

public class ObjectList extends List
{
	Vector objects;	

	public ObjectList()
	{
		objects = new Vector();
	}

	public ObjectList(int items, boolean multiSelect)
	{
		super(items, multiSelect);
		objects = new Vector();
	}

	public synchronized void addObject(Object ob)
	{

		super.addItem(ob.toString());


		objects.addElement(ob);
	}

	public synchronized void addObject(Object ob, int position)
	{

		super.addItem(ob.toString(), position);

		if (position >= objects.size()) {
			objects.addElement(ob);
		} else {
			objects.insertElementAt(ob.toString(),
				position);
		}
	}

	public synchronized void addObject(String label, Object ob)
	{

		super.addItem(label);
		objects.addElement(ob);
	}

	public synchronized void addObject(String label, Object ob,
		int position)
	{

		super.addItem(label, position);
		if (position >= objects.size()) {
			objects.addElement(ob);
		} else {
			objects.insertElementAt(ob.toString(),
				position);
		}
	}

	public synchronized void delObject(Object ob)
	{

		int index = objects.indexOf(ob);


		if (index < 0) return;


		objects.removeElementAt(index);


		super.delItem(index);
	}

	public synchronized Object getSelectedObject()
	{

		int i = getSelectedIndex();

		if (i == -1) return null;


		return objects.elementAt(i);
	}

	public synchronized Object[] getSelectedObjects()
	{

		int[] selectedItems = getSelectedIndexes();


		Object[] whichObjects = new Object[
			selectedItems.length];

		for (int i=0; i < selectedItems.length; i++) {
			whichObjects[i] = objects.elementAt(i);
		}

		return whichObjects;
	}

	public int indexOf(Object ob)
	{

		return objects.indexOf(ob);
	}

	public Object objectAt(int index)
	{

		return objects.elementAt(index);
	}

	public void replaceObject(Object ob, int index)
	{

		replaceItem(ob.toString(), index);


		objects.setElementAt(ob, index);
	}

	public void replaceObject(String label, Object ob, int index)
	{

		replaceItem(label, index);


		objects.setElementAt(ob, index);
	}
}
