package jw.piano.database;

import jw.spigot_fluent_api.database.api.database_table.annotations.Column;
import jw.spigot_fluent_api.database.api.database_table.annotations.Key;
import jw.spigot_fluent_api.database.api.database_table.annotations.Table;

@Table(name = "Users")
public class SqlUser
{
    @Key
    @Column
    public int id;

    @Column
    public String name;

    @Column
    public String lastName;

    @Column
    public int lastTimeSeen;
}
