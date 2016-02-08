package atm;

import atm.command.CommandExecutor;
import atm.exception.InterruptOperationException;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Created by Nicki on 18.11.2015.
 */

/**
 * Represents the cash machine.
 */
public class CashMachine
{   /**
     * Specifies the path to resource package.
     */
    public static final String RESOURCE_PATH = "atm.resources.";

    public static void main(String[] args)
    {
        Locale.setDefault(Locale.ENGLISH);
        ResourceBundle res = ResourceBundle.getBundle(RESOURCE_PATH + "common_en", Locale.ENGLISH);
        try
        {
            CommandExecutor.execute(Operation.LOGIN);
            Operation operation;
            while (true) {
                ConsoleHelper.writeMessage(res.getString("choose.operation") + " \n" +
                        res.getString("operation.INFO") + ": 1;\n" +
                        res.getString("operation.DEPOSIT") + ": 2;\n" +
                        res.getString("operation.WITHDRAW") + ": 3;\n" +
                        res.getString("operation.EXIT") + ": 4");

                operation = ConsoleHelper.askOperation();

                CommandExecutor.execute(operation);
            }
        }
        catch (InterruptOperationException e)
        {
            ConsoleHelper.printExitMessage();
        }
    }
}
