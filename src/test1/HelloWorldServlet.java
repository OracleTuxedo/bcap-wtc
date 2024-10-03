package test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.RollbackException;
import javax.transaction.TransactionManager;

import weblogic.wtc.gwt.TuxedoConnection;
import weblogic.wtc.gwt.TuxedoConnectionFactory;
import weblogic.wtc.jatmi.ApplicationToMonitorInterface;
import weblogic.wtc.jatmi.Reply;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TPReplyException;
import weblogic.wtc.jatmi.TypedBuffer;
import weblogic.wtc.jatmi.TypedCArray;
import weblogic.wtc.jatmi.TypedString;

import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/hi")
public class HelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = 10L;
	
	private static final Logger logger = Logger.getLogger(HelloWorldServlet.class.getName());
	
	public HelloWorldServlet() {
		logger.setLevel(Level.ALL);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("LeRucco Hola HelloWorld");
		response.getWriter().println("Hi Hola LeRucco");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set response type to JSON for example
//        response.setContentType("application/json");
//        request.setCharacterEncoding("UTF-8");
        
        logger.info("LE RUCCO NIPPON CAHAYA ASIA");
        logger.info("LE RUCCO NIPPON PELINDUNG ASIA");
        logger.info("LE RUCCO NIPPON PEMIMPIN ASIA");

        // 1. Read the request body using BufferedReader
        StringBuilder stringBuilder = new StringBuilder();
        
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        String requestBody = stringBuilder.toString();

        // 2. Print the body to the response (for demonstration purposes)
//        PrintWriter out = response.getWriter();
//        out.println(requestBody);
        // You can now process `requestBody`, e.g., deserialize JSON or process form data
        
//        String responseTuxedo = callTuxedoString(requestBody);
        String responseTuxedo = callTuxedoTypedCArray(requestBody);
        
        
        response.setContentType("text/plain");
        response.getWriter().write(responseTuxedo);
	}
	
	private String callTuxedoString(String requestBody) throws ServletException, IOException {
		logger.info("################################### START Hi LeRucco ###################################");
		// https://docs.oracle.com/en/middleware/fusion-middleware/weblogic-server/12.2.1.4/wtcpg/transactions.html#GUID-C7DE7B50-63C6-43ED-9665-CCD757E05FEE

		String dataMessage = "";
		logger.info("Initialize All Variable");
		try {
			javax.naming.Context myContext = new InitialContext();
			logger.info("InitialContext");
			TransactionManager tm = (javax.transaction.TransactionManager) myContext
					.lookup("javax.transaction.TransactionManager");
			logger.info("TransactionManager");

			// Begin Transaction
			tm.begin();
			logger.info("TransactionManager");
			TuxedoConnectionFactory tuxConFactory = (TuxedoConnectionFactory) myContext
					.lookup("tuxedo.services.TuxedoConnection");
			logger.info("TuxedoConnectionFactory");
			TuxedoConnection myTux = tuxConFactory.getTuxedoConnection();
			logger.info("TuxedoConnection");
			
			logger.info("Input String Panjang");
			TypedString dataInput = new TypedString(requestBody);
			
			logger.info("TypedCArray dengan Input String Panjang");
			Reply reply = myTux.tpcall("SLCFPROXY", (TypedString) dataInput, ApplicationToMonitorInterface.TPNOTRAN);
			logger.info("Reply SLCFPROXY");
			TypedString dataResponse = (TypedString) reply.getReplyBuffer();
			logger.info("TypedCArray dataResponse from Reply");
			dataMessage = dataResponse.toString();
			logger.info("dataResponse [" + dataResponse.toString() + "]");
			
			logger.info("TypedCArray dataMessage from dataResponse");
			
			myTux.tpterm();
			tm.commit();
			logger.info("myTux.tpterm() tm.commit()");
		} catch (NamingException ne) {
			logger.info("ERROR: Naming Exception looking up JNDI: " + ne);

		} catch (RollbackException re) {
			logger.info("ERROR: TRANSACTION ROLLED BACK: " + re);

		} catch (TPReplyException var16) {
			logger.info("TPReplyException:" + var16);

			try {
				throw var16;
			} catch (TPReplyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (TPException te) {
			logger.info("ERROR: tpcall failed: TpException: " + te);

		} catch (Exception e) {
			logger.info("ERROR: Exception: " + e);

		} finally {
			logger.info("RESULT: [" + dataMessage.toString() + "]");
			String message = dataMessage;
			logger.info("RESULT: [" + message + "]");
			logger.info("################################### US_ASCII END Hi LeRucco ###################################");
		}
		return dataMessage;
	}

	private String callTuxedoTypedCArray(String requestBody) throws ServletException, IOException {
		logger.info("################################### START Hi LeRucco ###################################");
		// https://docs.oracle.com/en/middleware/fusion-middleware/weblogic-server/12.2.1.4/wtcpg/transactions.html#GUID-C7DE7B50-63C6-43ED-9665-CCD757E05FEE

		byte[] dataMessage = {};
		logger.info("Initialize All Variable");
		try {
			javax.naming.Context myContext = new InitialContext();
			logger.info("InitialContext");
			TransactionManager tm = (javax.transaction.TransactionManager) myContext
					.lookup("javax.transaction.TransactionManager");
			logger.info("TransactionManager");

			// Begin Transaction
			tm.begin();
			logger.info("TransactionManager");
			TuxedoConnectionFactory tuxConFactory = (TuxedoConnectionFactory) myContext
					.lookup("tuxedo.services.TuxedoConnection");
			logger.info("TuxedoConnectionFactory");
			TuxedoConnection myTux = tuxConFactory.getTuxedoConnection();
			logger.info("TuxedoConnection");
			
			byte[] input = requestBody.getBytes();
			
			logger.info("Input String Panjang");
			TypedCArray dataInput = new TypedCArray();
			dataInput.carray = input;
			dataInput.setSendSize(input.length);
			
			logger.info("TypedCArray dengan Input String Panjang");
			Reply reply = myTux.tpcall("SLCFPROXY", (TypedBuffer) dataInput, ApplicationToMonitorInterface.TPNOTRAN);
			logger.info("Reply SLCFPROXY");
			TypedCArray dataResponse = (TypedCArray) reply.getReplyBuffer();
			logger.info("TypedCArray dataResponse from Reply");
			dataMessage = dataResponse.carray;
			logger.info("dataResponse [" + dataResponse.toString() + "]");
			
			logger.info("TypedCArray dataMessage from dataResponse");
			
			myTux.tpterm();
			tm.commit();
			logger.info("myTux.tpterm() tm.commit()");
		} catch (NamingException ne) {
			logger.info("ERROR: Naming Exception looking up JNDI: " + ne);

		} catch (RollbackException re) {
			logger.info("ERROR: TRANSACTION ROLLED BACK: " + re);

		} catch (TPReplyException var16) {
			logger.info("TPReplyException:" + var16);

			try {
				throw var16;
			} catch (TPReplyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (TPException te) {
			logger.info("ERROR: tpcall failed: TpException: " + te);

		} catch (Exception e) {
			logger.info("ERROR: Exception: " + e);

		} finally {
			logger.info("RESULT: [" + dataMessage.toString() + "]");
			String message = new String(dataMessage, StandardCharsets.US_ASCII);
			logger.info("RESULT: [" + message + "]");
			logger.info("################################### US_ASCII END Hi LeRucco ###################################");
		}
		return new String(dataMessage, StandardCharsets.US_ASCII);
	}
}
