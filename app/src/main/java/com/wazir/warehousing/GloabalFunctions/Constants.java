package com.wazir.warehousing.GloabalFunctions;

import com.wazir.warehousing.R;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String USER_MANAGER = "AccessTypeUserManager";
    public static final String USER_WORKER = "AccessTypeUserStaff";
    public static final String CHANNEL_1 = "channel1";
    public static final String CHANNEL_2 = "channel23";
    public static final String N_TEMP = "0", N_HUMID = "1", N_LIGHT = "2", N_FIRE = "3", N_FLOOD = "4";
    public static final String FIRE_HAZ = "FIRE_HAZ", FLOOD_HAZ = "FLOOD_HAZ";
    public static final Map<String, Integer> COLOR_TACK = new HashMap<String, Integer>() {{
        put(N_TEMP, R.color.softOrange);
        put(N_FIRE, R.color.g_red);
        put(N_FLOOD, R.color.g_blue);
        put(N_HUMID, R.color.darkBlue);
        put(N_LIGHT, R.color.lightColor);
    }};
}
