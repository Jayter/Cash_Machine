package atm.command;

import atm.*;
import atm.exception.*;

import java.util.ResourceBundle;

/**
 * Created by Nicki on 19.11.2015.
 */

/**
 * Implements the DEPOSIT operation.
 */
class DepositCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit_en");
    /**
     * Adds entered by the user amount to the specified account.
     *
     * @throws InterruptOperationException
     */
    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(res.getString("before"));
        /*
          Asking the user to enter currency code and getting corresponding currency manipulator.
        */
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        /*
          Getting denomination and amount.
        */
        String[] money = ConsoleHelper.getValidTwoDigits(currencyCode);
        int denomination = Integer.parseInt(money[0]);
        int amount = Integer.parseInt(money[1]);
        /*
          Putting money into storage.
        */
        manipulator.addAmount(denomination, amount);
        /*
          Informing the user about successful deposit.
        */
        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), denomination * amount, currencyCode));
    }
}
