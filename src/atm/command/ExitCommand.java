package atm.command;

import atm.*;
import atm.exception.InterruptOperationException;

import java.util.ResourceBundle;

/**
 * Created by Nicki on 19.11.2015.
 */

/**
 * Implements the EXIT operation.
 */
class ExitCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH +"exit_en");
    /**
     * Exits from the current session.
     *
     * @throws InterruptOperationException
     */
    @Override
    public void execute() throws InterruptOperationException
    {
        /*
          Asking the user whether he really wants to exit.
        */
        ConsoleHelper.writeMessage(res.getString("exit.question.y.n"));
        /*
          Getting result of the request.
        */
        String result = ConsoleHelper.readString();
        /*
          If the user really wants to exit the program, this method throws new
          InterruptOperationException that will be handled in main method.
        */
        if(result.equalsIgnoreCase(res.getString("yes")))
            throw new InterruptOperationException();
    }
}
