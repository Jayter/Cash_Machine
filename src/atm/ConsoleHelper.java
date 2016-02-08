package atm;

import atm.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nicki on 18.11.2015.
 */

/**
 * Interacts with console.
 * <p>
 * This class is used to concentrate all work with console in one place.
 */
public class ConsoleHelper
{
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common_en");
    /**
     * Reads information that the user enters.
     */
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    /**
     * Writes the message-parameter in console.
     *
     * @param message  - information to be written
     */
    public static void writeMessage(String message)
    {
        System.out.println(message);
    }
    /**
     * Prints the exit message.
     * <p>
     * This method is invoked only by {@link CashMachine#main(String[])}.
     */
    public static void printExitMessage()
    {
        ConsoleHelper.writeMessage(res.getString("the.end"));
    }

    /**
     * Reads the string from console and returns it.
     *
     * @return String that the user enters
     * @throws InterruptOperationException
     */
    public static String readString() throws InterruptOperationException
    {
        String message = "";
        try
        {
            message = reader.readLine();
            /*
              The user can exit the ATM at any time, printing "exit".
              If it`s thrown by this method, it will be caught and handled in the main method.
            */
            if (message.equalsIgnoreCase(res.getString("operation.EXIT")))
                throw new InterruptOperationException();
        }
        catch (IOException ignored) {}
        return message;
    }

    /**
     * Asks the user to enter currency code and returns it.
     *
     * @return String currencyCode in upper case
     * @throws InterruptOperationException
     */
    public static String askCurrencyCode() throws InterruptOperationException
    {
        String currencyCode;
        writeMessage(res.getString("choose.currency.code"));
        /*
          This loop is repeated until the user enters string that consists of three letters.
          Correctness of data is checked by supplementary method.
        */
        while (true)
        {
            currencyCode = readString();

            if(!checkWithRegExpCurrencyCode(currencyCode))
            {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            break;
        }
        currencyCode = currencyCode.toUpperCase();

        return currencyCode;
    }

    /**
     * Asks user to enter the currency denomination and it`s amount.
     * <p>
     * This method is invoked only within {@link atm.command.DepositCommand#execute()}.
     * @param currencyCode  is used to write example input for the user
     * @return String[] that contains two integers in string format that the user enters
     * @throws InterruptOperationException
     */
    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException
    {
        String[] resultArr;
        writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
        /*
          This loop is repeated until the user enters valid data.
          Valid data is only two integers, each one is positive.
          There are no restrictions for denomination.
        */
        while (true)
        {
            String s = readString();
            resultArr = s.split(" ");
            int denomination;
            int amount;
            try
            {
                denomination = Integer.parseInt(resultArr[0]);
                amount = Integer.parseInt(resultArr[1]);
            }
            catch (NumberFormatException e)
            {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            if (denomination <= 0 || amount <= 0 || resultArr.length > 2)
            {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            break;
        }
        return resultArr;
    }
    /**
     * Reads number and returns corresponding operation.
     *
     * @return Operation, specified by the user
     * @throws InterruptOperationException
     */
    public static Operation askOperation() throws InterruptOperationException
    {
        /*
          This loop repeats until the user enters valid data.
          Correctness of data is checked by supplementary method.
        */
        while (true)
        {
            String number = readString();

            if (checkWithRegExpOperation(number))
                return Operation.getAllowableOperationByOrdinal(Integer.parseInt(number));
            else
                writeMessage(res.getString("invalid.data"));
        }

    }
    /**
     * Checks whether input parameter is in the interval [1;4].
     * <p>
     * This is an auxiliary method for {@link #askOperation()}.
     * @param input - the ordinal of operation
     * @return whether or no input matches pattern
     */
    private static boolean checkWithRegExpOperation(String input)
    {
        Pattern p = Pattern.compile("^[1-4]$");
        Matcher m = p.matcher(input);
        return m.matches();
    }
    /**
     * Checks whether input parameter consists of three letters.
     * <p>
     * This is an auxiliary method for {@link #askCurrencyCode()}.
     * @param input - currency code
     * @return whether or no input matches pattern
     */
    private static boolean checkWithRegExpCurrencyCode(String input)
    {
        Pattern p = Pattern.compile("^[a-zA-Z]{3}$");
        Matcher m = p.matcher(input);
        return m.matches();
    }
}
