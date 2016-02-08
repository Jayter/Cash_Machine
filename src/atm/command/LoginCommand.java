package atm.command;

import atm.*;
import atm.exception.InterruptOperationException;

import java.util.ResourceBundle;

/**
 * Created by Nicki on 19.11.2015.
 */

/**
 * Implements the LOGIN operation.
 */
class LoginCommand implements Command
{
    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH +"login_en");
    /**
     * Authorizes the user.
     *
     * @throws InterruptOperationException
     */
    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(res.getString("before"));
        /*
          Repeats until the user enters correct login and password.
        */
        while (true)
        {
            ConsoleHelper.writeMessage(res.getString("specify.data"));
            String s1 = ConsoleHelper.readString();
            String s2 = ConsoleHelper.readString();
            if (validCreditCards.containsKey(s1))
            {
                if (validCreditCards.getString(s1).equals(s2))
                    ConsoleHelper.writeMessage(String.format(res.getString("success.format"), s1));
                else
                {
                    ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), s1));
                    ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
                    continue;
                }
            }
            else
            {
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), s1));
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
                continue;
            }
            break;
        }
    }
}
