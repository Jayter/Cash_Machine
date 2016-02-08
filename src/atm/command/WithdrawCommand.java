package atm.command;

import atm.*;
import atm.exception.*;

import java.util.ResourceBundle;

/**
 * Created by Nicki on 19.11.2015.
 */
/**
 * Implements the WITHDRAW operation.
 */
class WithdrawCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH +"withdraw_en");
    /**
     * Tries to withdraw entered by the user amount of money.
     *
     * @throws InterruptOperationException
     */
    @Override
    public void execute() throws InterruptOperationException
    {
        /*
          Asking the user to enter currency code and getting corresponding currency manipulator.
        */
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        /*
          If no money is available, informing the user and exiting the operation.
        */
        if(!currencyManipulator.hasMoney())
        {
            ConsoleHelper.writeMessage(res.getString("no.money"));
            return;
        }

        int sum; //Stores amount, entered by the user.
        /*
          Asking the user to enter amount.
        */
        ConsoleHelper.writeMessage(res.getString("before"));
        /*
          This loop is repeated until user enters amount that can be withdrawn.
        */
        while(true)
        {
            String s = ConsoleHelper.readString();
            try
            {
                sum = Integer.parseInt(s);
            }
            catch (NumberFormatException e)
            {
                ConsoleHelper.writeMessage(res.getString("specify.amount"));
                continue;
            }
            if (sum <= 0)
            {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            }
            if(currencyManipulator.isAmountAvailable(sum))
            {
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                continue;
            }
            break;
        }
        try
        {
            currencyManipulator.withdrawAmount(sum);
        }
        catch (NotEnoughMoneyException e)
        {
            ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            return;
        }
        /*
          Informing the user about successfully executed operation.
        */
        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), sum, currencyCode));
    }
}
