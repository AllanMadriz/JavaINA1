package Config;
//Clase config que nos permite la conexion al BD, aqui nos dan los datos de acceso y la BD que queremos
public final class Config {
        public static String getConnectionString() throws ClassNotFoundException{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                return "jdbc:sqlserver://localhost;databaseName=Facturacion;user=sa;password=sa";
        }
}
