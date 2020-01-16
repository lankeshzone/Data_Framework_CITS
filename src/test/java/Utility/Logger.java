package Utility;

import org.apache.log4j.Level;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@SuppressWarnings("ALL")
public enum Logger {

    /**
     * Instance through which the enum methods can be accessed
     */
    INSTANCE;

    /**
     * DEBUGLEVEL for additional message log
     */
    public static int DEBUGLEVEL = 3;
    /**
     * Logger category
     */
    private static Logger log;

    /**
     * Compute log directory path based on date
     *
     * @return - logs directory path
     */
    public static String getLogDirectoryPath() {
        String separator = "/";
        DateFormat dateFormat = new SimpleDateFormat("yyyy" + separator + "MM" + separator + "dd" + separator + "HH.mm.ss");
        Date date = new Date();
        //create folder logs in user dir
        return String.valueOf(dateFormat.format(date));
    }

    /**
     * Get Debug level
     *
     * @return Debug level
     */
    public static int getDebugLevel() {
        return DEBUGLEVEL;
    }

    /**
     * @param aDebugLevel Set Debug level for message
     */
    public static void setDebugLevel(int aDebugLevel) {
        DEBUGLEVEL = aDebugLevel;
    }

    /**
     * Logging the message with timestamp and message type. <br>
     * If DEBUGLEVEL be seted to value greater that 10, then it showed searching region<br>
     * The image is saved when the error occurs<br>
     *
     * @param aType    : 0-Info | 1-Warn | 2-Error | other- Debug
     * @param aMessage unformated message for logging
     * @return formated message
     */
    public static String out(int aType, String aMessage) {
        Date timestamp = new Date();
        String formatMessage;
        String messageType;

        switch (aType) {
            case 0:
                messageType = "Info";
                break;
            case 1:
                messageType = "Warn";
                break;
            case 2:
                messageType = "Error";
                File file = new File("ErrorImages");
                File currentPath = new File(file.getAbsolutePath());
                currentPath.mkdirs();
                String errorfile = messageType + "_" + new SimpleDateFormat("dd.MM.YYYY_HH_mm_ss.S").format(timestamp);
                //  createScreenCapture(currentPath.getPath(), errorfile );
               // new Screen().saveScreenCapture(currentPath.getPath(), errorfile);
                break;
            default:
                messageType = "Debug";
        }

        formatMessage = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss.S").format(timestamp) + " " + messageType + ": " + aMessage;
        if ((aType <= 2 && aType >= 0) && !messageType.equals("Debug")) {
            System.out.println(formatMessage);

        } else {
            if (aType <= getDebugLevel() && messageType.equals("Debug")) {

                System.out.println(formatMessage);
                // log the Action from Sikuli
           //     Settings.ActionLogs = true;
            }
        }
        return formatMessage;
    }

/*
    public static void createScreenCapture(String aPath, String aFile) {
        File currentPath = new File(aPath);
        new File(currentPath.getAbsolutePath()).mkdirs();
        out(0, "createScreenCapture " + currentPath.getAbsolutePath());
        new Screen().saveScreenCapture(aPath, aFile);
    }
*/

    /**
     * Get logger for given class
     *
     * @param clazz Class for logging category
     * @return Logger category
     */
    public static Logger getLogger(Class<?> clazz) {
        //create filename based on date
        System.setProperty("logfilename", getLogDirectoryPath());
        log = Logger.getLogger(clazz);
        //if maven parameter "noLogging" has been set, then turn logging off
        if (System.getProperty("noLogging") != null) {
       //    log.setDebugLevel(Level.OFF);
        }
        return log;
    }

}
