import java.applet.Applet;
import java.util.*;

public class AppletRegistry extends Observable
{

// The single copy of the registry
     protected static AppletRegistry registry;

// The table of applets
     protected Hashtable applets;

// Used for generating unique applet names
     protected int nextUnique;

     protected AppletRegistry()
     {
          applets = new Hashtable();
          nextUnique = 0;
     }

// Returns the long instance of the registry. If there isn't a registry
// yet, it creates one.

     public synchronized static AppletRegistry instance()
     {
          if (registry == null) {
               registry = new AppletRegistry();
          }
          return registry;
     }

// Adds a new applet to the registry - stores it in the table and
// sends a notification to its observers.

     public synchronized void addApplet(String name, Applet newApplet)
     {
          applets.put(name, newApplet);
          setChanged();
          notifyObservers(new AppletRegistryEvent(
               AppletRegistryEvent.ADD_APPLET,
               name, newApplet));
     }

// Adds a new applet to the registry - stores it in the table and
// sends a notification to its observers. If uniqueName is false, the
// applet's name is non-unique. Store the applet in a table with a
// unique version of the name (appends <#> to the name where # is
// a constantly increasing number).

     public synchronized void addApplet(String name, Applet newApplet,
          boolean uniqueName)
     {
          if (!uniqueName && (applets.get(name) != null)) {
               name = name + "<"+nextUnique+">";
               nextUnique++;
          }

          applets.put(name, newApplet);
          setChanged();
          notifyObservers(new AppletRegistryEvent(
               AppletRegistryEvent.ADD_APPLET,
               name, newApplet));
     }

// removes an applet from the table and notifies the observers

     public synchronized void removeApplet(Applet applet)
     {
          Enumeration e = applets.keys();

          while (e.hasMoreElements()) {
               Object key = e.nextElement();

               if (applets.get(key) == applet) {
                    applets.remove(key);
                    setChanged();
                    notifyObservers(new AppletRegistryEvent(
                         AppletRegistryEvent.REMOVE_APPLET,
                         (String)key, applet));
                    return;
               }
          }
     }

// removes an applet from the table and notifies the observers

     public synchronized void removeApplet(String name)
     {
          Applet applet = (Applet) applets.get(name);
          if (applet == null) return;

          applets.remove(name);

          setChanged();
          notifyObservers(new AppletRegistryEvent(
               AppletRegistryEvent.REMOVE_APPLET,
               name, applet));
     }
     
// finds an applet by name, or returns null if not found

     public Applet findApplet(String name)
     {
          return (Applet) applets.get(name);
     }

// lets you see all the applets in the registry

     public Enumeration getApplets()
     {
          return applets.elements();
     }
}
