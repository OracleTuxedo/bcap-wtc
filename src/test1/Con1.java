package test1;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weblogic.wtc.gwt.TuxedoConnectionFactory;
import weblogic.wtc.gwt.TuxedoConnection;
import weblogic.wtc.jatmi.TypedCArray;
import weblogic.wtc.jatmi.Reply;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TPReplyException;
import weblogic.wtc.jatmi.TypedBuffer;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Servlet implementation class ConnectAja
 */
@WebServlet("/con1")
public class Con1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(HelloWorldServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Con1() {
    	logger.setLevel(Level.ALL);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("################################### START Con 1 LeRucco ###################################");
		TuxedoConnectionFactory tuxedoConnectionFactory;
        TuxedoConnection tuxedoConnection = null;
        TypedCArray dataInput;
        Reply reply;
        byte[] dataMessage = {};
        logger.info("Initialize All Variable");
		try {
			InitialContext context = new InitialContext();
			logger.info("InitialContext");
			tuxedoConnectionFactory = (TuxedoConnectionFactory) context.lookup("tuxedo.services.TuxedoConnection");
			logger.info("TuxedoConnectionFactory");
			tuxedoConnection = tuxedoConnectionFactory.getTuxedoConnection();
			logger.info("getTuxedoConnection()");
			byte[] input = "00000530DEVWAS0120240604103344010mti2100SAZ09V701R              MTI S                        DEVWAS0120240604103344010mti210020240604103344000261WEB       172.16.20.43                                WAZ090100H Nundefined      020240604103344000261                    A000000      00010                                                                        ID                                                                                                                                             D00000027                     000001@@".getBytes();
			logger.info("Input String Panjang");
			dataInput = new TypedCArray();
			dataInput.carray = input;
			dataInput.setSendSize(input.length);
			logger.info("TypedCArray dengan Input String Panjang");
			reply = tuxedoConnection.tpcall("SLCFPROXY", (TypedBuffer) dataInput, 0);
			logger.info("Reply SLCFPROXY");
			TypedCArray dataResponse = (TypedCArray) reply.getReplyBuffer();
			logger.info("TypedCArray dataResponse from Reply");
			dataMessage = dataResponse.carray;
			logger.info("TypedCArray dataMessage from dataResponse");
		} catch (NamingException var15) {
			logger.info("NamingException:" + var15.getMessage());
//            System.out.println("Could not get TuxedoConnectionFactory : NamingException:" + var15.getMessage());
            try {
				throw var15;
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (TPReplyException var16) {
//            System.out.println("tpcall threw TPReplyExcption");
            logger.info("TPReplyException:" + var16.getMessage());

            try {
				throw var16;
			} catch (TPReplyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (TPException var17) {
        	logger.info("TPException:" + var17.getMessage());
//            System.out.println("TPException:" + var17.getMessage());

            try {
				throw var17;
			} catch (TPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (Exception var18) {
        	logger.info("Exception:" + var18.getMessage());
//            System.out.println("Exception:" + var18.getMessage());
            throw var18;
        } finally {
            if (tuxedoConnection != null) {
            	tuxedoConnection.tpterm();
            }
            logger.info("RESULT: ["+dataMessage.toString()+"]");
            logger.info("################################### END Con 1 LeRucco ###################################");
            
            response.getWriter().append(dataMessage.toString()).append(request.getContextPath());
        }
        
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
