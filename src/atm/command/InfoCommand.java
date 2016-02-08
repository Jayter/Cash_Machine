package atm.command;

import atm.*;
import java.util.ResourceBundle;


/**
 * Created by Nicki on 19.11.2015.
 */

/**
 * Implements the INFO operation.
 */
class InfoCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH +"info_en");
    /**
     * Prints information about specified account.
     */
    @Override
    public void execute()
    {
        boolean hasMoney = false; //If at least one manipulator keeps money.

        ConsoleHelper.writeMessage(res.getString("before"));
        /*
          Printing all information about user`s account.
          I.e. information about the state of all currency manipulators.
        */
        for (CurrencyManipulator manipulator : CurrencyManipulatorFactory.getAllCurrencyManipulators())
        {
            if(manipulator.hasMoney())
            {
                if (manipulator.getTotalAmount()>0)
                {
                   System.out.println(manipulator.getCurrencyCode() + " " + manipulator.getTotalAmount());
                   hasMoney = true;
                }
            }
        }
        /*
          Informing user that no money is available.
        */
        if(!hasMoney)
            ConsoleHelper.writeMessage(res.getString("no.money"));
    }
}
