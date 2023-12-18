package Resources;

public interface DBConstants {
    public static final String SYSTEM_CLOUD = new Utils.File().loadSystemCloud();
    public static final String URL_CLOUD = new Utils.Security.Encryption().decrypt(new Utils.File().loadURLCloud());
    public static final String USERNAME_CLOUD = new Utils.Security.Encryption().decrypt(new Utils.File().loadUsernameCloud());
    public static final String PASSWORD_CLOUD = new Utils.Security.Encryption().decrypt(new Utils.File().loadPasswordCloud());

    public static final String SYSTEM_LOCAL = new Utils.File().loadSystemLocal();
    public static final String URL_LOCAL = "jdbc:hsqldb:file:" + new Utils.Path().getPath(new String[] {"Resources", "DataBase", "CompressDB"}, "CompressDB");
    public static final String USERNAME_LOCAL = new Utils.Security.Encryption().decrypt(new Utils.File().loadUsernameLocal());
    public static final String PASSWORD_LOCAL = new Utils.Security.Encryption().decrypt(new Utils.File().loadPasswordLocal());
}