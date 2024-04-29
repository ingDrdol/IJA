package ija.project.ijarobots.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class Logger implements System.Logger {

    private final File file = new File("data/log.txt");
    @Override
    public String getName() {
       return "IJALogger";
    }

    @Override
    public boolean isLoggable(Level level) {
        return switch (level) {
            case ERROR, WARNING, INFO -> true;
            default -> false;
        };
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        if (isLoggable(level)) {
            if (level.getSeverity() == Level.ERROR.getSeverity()){
                System.err.println(LocalDateTime.now() + "::" + level.getName() + "::" + msg
                        + "::thrown by::" + thrown.getClass());
                }
            try {
                FileWriter writer = new FileWriter(file, true);
                BufferedWriter buffer = new BufferedWriter(writer);
                buffer.write(LocalDateTime.now() + "::" + level.getName() + "::" + msg
                        + "::thrown by::" + thrown.getClass());
                buffer.newLine();
                buffer.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        if (isLoggable(level)){
            if (level.getSeverity() == Level.ERROR.getSeverity()) {
                System.err.println(LocalDateTime.now() + "::" + level.getName() + "::" +
                        String.format(format, params));
            }
            try {
                FileWriter writer = new FileWriter(file, true);
                BufferedWriter buffer = new BufferedWriter(writer);
                buffer.write(LocalDateTime.now() + "::" + level.getName() + "::" +
                        String.format(format, params));
                buffer.newLine();
                buffer.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }


}
