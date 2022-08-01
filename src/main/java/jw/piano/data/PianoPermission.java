package jw.piano.data;

import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.permissions.Permission;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PianoPermission
{
    public final static String PIANO = "piano";

    public final static String CREATE = PIANO +".create";

    public final static String REMOVE = PIANO +".remove";

    public final static String VOLUME = PIANO +".volume";

    public final static String RENAME = PIANO +".rename";

    public final static String SKIN = PIANO +".skin";

    public final static String ACTIVE = PIANO +".active";

    public final static String EFFECTS = PIANO +".effects";

    public final static String PEDAl = PIANO +".pedal";


    public final static String BENCH = PIANO +".bench";

    public final static String TELEPORT = PIANO +".teleport";

    public final static String DETECT_KEY = PIANO +".detect-key";

    public final static String DESKTOP_CLIENT = PIANO +".desktop-client";

    public static void register() {

        try
        {
            var fields = PianoPermission.class.getDeclaredFields();
            var names = new ArrayList<String>();
            for(var field : fields)
            {
                names.add(field.get(null).toString());
            }

            var pianoPermissions = new ArrayList<Permission>();
            for(var name  : names)
            {
                if(name.equals(PIANO))
                {
                   var subPermissions = new HashMap<String,Boolean>();
                   for(var name2 : names)
                   {
                       if(name.equals(PIANO))
                       {
                           continue;
                       }
                       subPermissions.put(name2,true);
                   }
                    pianoPermissions.add(new Permission(name,subPermissions));
                    continue;
                }
                pianoPermissions.add(new Permission(name));
            }
            FluentPlugin.addPermissions(pianoPermissions);
        }
        catch (Exception e)
        {
            FluentLogger.error("unable to load permissions ",e);
        }

    }

}
