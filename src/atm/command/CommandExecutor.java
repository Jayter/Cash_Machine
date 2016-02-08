package atm.command;

import atm.Operation;
import atm.exception.InterruptOperationException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nicki on 19.11.2015.
 */

/**
 * Implements convenient interaction with encapsulated classes-commands.
 */
public class CommandExecutor
{
    /**
     * We don`t need to instantiate this class as we use only static method.
     */
    private CommandExecutor(){}
    /**
     * Contains pairs of operation and it`s corresponding command.
     */
    private static Map<Operation, Command> map = new HashMap<>();
    /*
      Initialization of the map.
    */
    static
    {
        map.put(Operation.INFO, new InfoCommand());
        map.put(Operation.DEPOSIT, new DepositCommand());
        map.put(Operation.WITHDRAW, new WithdrawCommand());
        map.put(Operation.EXIT, new ExitCommand());
        map.put(Operation.LOGIN, new LoginCommand());
    }
    /**
     * Invokes method {@link Command#execute()} of the specified command.
     *
     * @param operation the key of the command **which command to choose**
     * @throws InterruptOperationException
     */
    public static void execute(Operation operation)throws InterruptOperationException
    {
        map.get(operation).execute();
    }
}

