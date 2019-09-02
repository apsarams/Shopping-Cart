import java.awt.Button;
import java.awt.Event;

// This class implements a Button that supports the
// Command interface. When the button is pressed, it
// invokes the doCommand method in the Command interface.

public class CommandButton extends Button
{

// The interface where we will invoke doCommand

     protected Command buttonCommand;

// It's always polite to implement the empty constructor if
// you can get away with it.

     public CommandButton()
     {
     }

// Allow a CommandButton with a command but no label

     public CommandButton(Command command)
     {
          buttonCommand = command;
     }

// Allow a CommandButton to use the typical Button constructor

     public CommandButton(String label)
     {
          super(label);
     }

// The most useful constructor allows a label and a command

     public CommandButton(String label, Command command)
     {
          super(label);

          buttonCommand = command;
     }

// When we get an action event, invoke doCommand in buttonCommand

     public boolean action(Event evt, Object which)
     {

// Make sure the action event is for this object
          if (evt.target != this) return false;

// Make sure we have a buttonCommand defined!
          if (buttonCommand == null) return false;

          buttonCommand.doCommand();

          return true;
     }

// Since you can create a CommandButton without passing it a
// Command interface, you need to be able to set the command later.

     public void setCommand(Command command)
     {
          buttonCommand = command;
     }
}