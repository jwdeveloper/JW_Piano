package jw.piano.database;

import jw.spigot_fluent_api.database.api.database_table.annotations.Column;
import jw.spigot_fluent_api.database.api.database_table.annotations.ForeignKey;
import jw.spigot_fluent_api.database.api.database_table.annotations.Key;
import jw.spigot_fluent_api.database.api.database_table.annotations.Table;

@Table(name = "Users")
public class SqlNotes
{
    @Key
    @Column
    public int id;

    @Column
    public String noteName;

    @Column
    public int userId;

    @ForeignKey(columnName = "userId")
    public SqlUser user;
}
