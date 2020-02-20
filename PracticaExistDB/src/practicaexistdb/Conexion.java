
package practicaexistdb;

import java.util.ArrayList;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQResultSequence;


public class Conexion {
    private static final String nomClaseDS="net.xqj.exist.ExistXQDataSource";
    private static XQConnection connection;
    ArrayList<Instituto> arrayInsti;


    
    
    public void obtenerConexion() throws ClassNotFoundException,
        InstantiationException, IllegalAccessException, XQException{
        
        XQDataSource xqs= (XQDataSource) Class.forName(nomClaseDS).newInstance();
        xqs.setProperty("serverName", "localhost");
        xqs.setProperty("port", "8080");
        xqs.setProperty("user", "admin");
        xqs.setProperty("password", "1234");
        connection=xqs.getConnection();
        System.out.println("conectado correctamente");
        
    }
    
    public void insertarInstituto(Instituto iaux) throws XQException{
            String query = "update insert(\n<Instituto>\n<id>"+iaux.getCodInsti()+"</id>\n<nombre>"+iaux.getNombre()+"</nombre>\n<provincia>"+iaux.getProvincia()+"</provincia>\n<calle>"+iaux.getCalle()+"</calle>\n</Instituto>\n) into doc('/db/AD/Institutos.xml')/Institutos";
            XQExpression xqe = connection.createExpression();
            xqe.executeCommand(query);
            System.out.println("prueba DEBUG:  "+iaux.toString());
    }
    
    public  void borrarInstituto(Instituto aux) throws XQException {
        
        String query = "update delete doc('/db/AD/Institutos.xml')/Institutos/Instituto[nombre=\""+aux.getNombre()+"\"]";
        XQExpression xqe = connection.createExpression();
        xqe.executeCommand(query);
        
        
    }
    
    public void modificarInstituto(Instituto reemplazar, Instituto reemplazador ) throws XQException  {
            int id_instituto = reemplazar.getCodInsti();
            String nombre = reemplazador.getNombre().replace(" ","_");
            String provincia = reemplazador.getProvincia();
            String calle = reemplazador.getCalle();
            String query = "update replace doc('/db/AD/Institutos.xml')/Institutos/Instituto[nombre=\""+reemplazar.getNombre()+"\"] with \n<Instituto>\n<id>"+id_instituto+"</id>\n<nombre>"+nombre+"</nombre>\n<provincia>"+provincia+"</provincia>\n<calle>"+calle+"</calle>\n</Instituto>";
            XQExpression xqe = connection.createExpression();
            xqe.executeCommand(query);
    }
    
    public void leerInstitutos(ArrayList<Instituto> institutos) throws XMLStreamException, XQException  {
       
            String query = "doc('/db/AD/Institutos.xml')/Institutos/Instituto"; 
            XQExpression xqe = connection.createExpression(); 
            XQResultSequence xqrs = xqe.executeQuery(query);
            int contador=0;
            
            while (xqrs.next()) { 
                XMLStreamReader xsr = xqrs.getItemAsStream(); 
                Instituto aux= new Instituto();
                while (xsr.hasNext()) { 
                    
                    if (xsr.getEventType() == XMLStreamConstants.CHARACTERS) {
                        String datos= limpiarCadena(xsr.getText());
                        
                        if(!datos.equals("")){
                            datos=datos.replace("_", " ");
                            switch (contador) {
                                case 0:
                                    aux.setCodInsti(Integer.parseInt(datos));
                                    break;
                                case 1:
                                    aux.setNombre(datos);
                                    break;
                                case 2:
                                    aux.setProvincia(datos);
                                    break;
                                case 3:
                                    aux.setCalle(datos);
                                    break;
                                default:
                                    break;
                            }
                             contador=contador+1;
                        }
                    } 
                    xsr.next(); 
                }
                institutos.add(aux);
                contador=0;
        }
            for(int x = 0; x < institutos.size(); ++x){
                    System.out.println(institutos.get(x).toString());
                
                }
    }

       
    
        public String limpiarCadena(String cad){
        cad=cad.replace("\n","");
        cad=cad.replace(" ","");
        cad=cad.replace("\t","");
        cad=cad.replace("\r","");
        return cad;
    }

}
