package atm;

/**
 * Created by Nicki on 18.11.2015.
 */

/**
 * Represents list of all operations that the user needs.
 */
public enum Operation
{
    LOGIN,
    INFO,
    DEPOSIT,
    WITHDRAW,
    EXIT;
    /**
     * Returns operation, specified by parameter.
     *
     * @param i - ordinal
     * @return Operation according to ordinal value
     */
    public static Operation getAllowableOperationByOrdinal(Integer i){
        switch (i){
            case 1:
                return INFO;
            case 2:
                return DEPOSIT;
            case 3:
                return WITHDRAW;
            case 4:
                return EXIT;
            default:
                throw new IllegalArgumentException();
        }
    }
}
