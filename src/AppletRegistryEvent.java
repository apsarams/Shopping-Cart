import java.applet.Applet;

public class AppletRegistryEvent extends Object
{
     public final static int ADD_APPLET = 1;
     public final static int REMOVE_APPLET = 2;

     public int id;
     public String appletName;
     public Applet applet;

     public AppletRegistryEvent()
     {
     }

     public AppletRegistryEvent(int id, String appletName, Applet applet)
     {
          this.id = id;
          this.appletName = appletName;
          this.applet = applet;
     }
}