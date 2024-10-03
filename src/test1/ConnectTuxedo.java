package test1;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import java.nio.charset.StandardCharsets;

@WebServlet("/connect")
public class ConnectTuxedo extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private static final Logger logger = Logger.getLogger(HelloWorldServlet.class.getName());
	
    public ConnectTuxedo() {
    	logger.setLevel(Level.ALL);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("LeRucco Connect Tuxedo");
		response.getWriter().println("LeRucco Connect Tuxedo");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("#################### doPost ####################");
		logger.info("LeRucco NIPPON CAHAYA ASIA");
        logger.info("LeRucco NIPPON PELINDUNG ASIA");
        logger.info("LeRucco NIPPON PEMIMPIN ASIA");
        
        // Get the content length to allocate byte array (optional but recommended)
        int contentLength = request.getContentLength();
        logger.info(String.valueOf(contentLength));
        
        // Initialize byte array to store the incoming data
        byte[] input = new byte[contentLength];

        // Read the byte array from the input stream
        InputStream inputStream = request.getInputStream();
        inputStream.read(input);
        
        logger.info(new String(input, StandardCharsets.UTF_8));
        
        logger.info("#################### Call Tuxedo TypedCArray ####################");
        
        // Call Oracle TuxedoConnection
        byte[] output = new byte[0];
		try {
//			output = callTuxedoTypedCArray(input);
			output = connectTxd(input);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        StringBuilder le = new StringBuilder();
        for (byte b : output) {
            le.append(b).append(", ");
        }
        logger.info(le.toString());

        // Set the content type to application/octet-stream for binary data
        response.setContentType("application/octet-stream");

        // Set the content length (optional but recommended)
        response.setContentLength(output.length);

        // Get the output stream of the response
        OutputStream outputStream = response.getOutputStream();

        // Write the byte array to the output stream
        outputStream.write(output);

        // Ensure everything is flushed to the client
        outputStream.flush();
	}
		
	private byte[] connectTxd(byte[] var0) throws ServletException, IOException, Exception {
        TuxedoConnection var3 = null;

        byte[] var20 = new byte[0];
        try {
            InitialContext var1 = new InitialContext();
            TuxedoConnectionFactory var2 = (TuxedoConnectionFactory)var1.lookup("tuxedo.services.TuxedoConnection");
            var3 = var2.getTuxedoConnection();
            
            logger.info("Request[" + var0.length + "] : [" + new String(var0) + "]");

            TypedCArray var4 = new TypedCArray();
            var4.carray = var0;
            var4.setSendSize(var0.length);
            Reply var6 = var3.tpcall("SLCFPROXY", var4, 0);
            TypedCArray var5 = (TypedCArray)var6.getReplyBuffer();
            var20 = var5.carray;
            
            logger.info("Response[" + var20.length + "] : [" + new String(var20) + "]");
            
        } catch (NamingException var15) {
            logger.info("Could not get TuxedoConnectionFactory : NamingException:\n" + var15.getMessage());
            logger.info(var15.getMessage());
//            throw var15;
        } catch (TPReplyException var16) {
            logger.info("tpcall threw TPReplyExcption " + var16);
            logger.info(var16.getMessage());
//            throw var16;
        } catch (TPException var17) {
            logger.info("tpcall threw TPException " + var17);
            logger.info(var17.getMessage());
//            throw var17;
        } catch (Exception var18) {
            logger.info("tpcall threw exception: " + var18);
            logger.info(var18.getMessage());
//            throw var18;
        } finally {
            var3.tpterm();
        }

        return var20;
    }
	
	private byte[] callTuxedoTypedCArray(byte[] input) throws ServletException, IOException {

		byte[] output = new byte[0];
		logger.info("Initialize All Variable");
		try {
			logger.info("InitialContext");
			javax.naming.Context myContext = new InitialContext();
			
			logger.info("TransactionManager");
			TransactionManager tm = (javax.transaction.TransactionManager) myContext.lookup("javax.transaction.TransactionManager");
			
			// Begin Transaction
			logger.info("TransactionManager");
			tm.begin();
			
			logger.info("TuxedoConnectionFactory");
			TuxedoConnectionFactory tuxConFactory = (TuxedoConnectionFactory) myContext.lookup("tuxedo.services.TuxedoConnection");
			
			logger.info("TuxedoConnection");
			TuxedoConnection myTux = tuxConFactory.getTuxedoConnection();
			
			logger.info("TypedCArray Initialization");
			TypedCArray dataInput = new TypedCArray();
			dataInput.carray = input;
			dataInput.setSendSize(input.length);
			
			logger.info("Reply SLCFPROXY");
			Reply reply = myTux.tpcall("SLCFPROXY", (TypedBuffer) dataInput, ApplicationToMonitorInterface.TPNOTRAN);
			
			logger.info("TypedCArray Response");
			TypedCArray dataResponse = (TypedCArray) reply.getReplyBuffer();
			
			logger.info("TypedCArray Response to byte[] ");
			output = dataResponse.carray;
			
			logger.info("myTux.tpterm() tm.commit()");
			myTux.tpterm();
			tm.commit();
			
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
			System.out.println("Output:");
			System.out.println(new String(output, StandardCharsets.UTF_8));
//			logger.info(new String(output, StandardCharsets.UTF_8));
		}
		
		return output;
	}

}
