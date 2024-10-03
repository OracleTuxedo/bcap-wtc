package test1;

import java.io.IOException;
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

/**
 * Servlet implementation class Con2
 */
@WebServlet("/con2")
public class Con2 extends HttpServlet {
	private static final long serialVersionUID = 2L;

	private static final Logger logger = Logger.getLogger(HelloWorldServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Con2() {
		super();
		logger.setLevel(Level.ALL);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("################################### START Con 2 LeRucco ###################################");
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
			
			String panjang1 = "00000530DEVWAS0120240604103344010mti2100SAZ09V701R              MTI S                        DEVWAS0120240604103344010mti210020240604103344000261WEB       172.16.20.43                                WAZ090100H Nundefined      020240604103344000261                    A000000      00010                                                                        ID                                                                                                                                             D00000027                     000001";
			String panjang2 = "00000523                                SAC06V621R              MTI S                                                                            UNIT      192.168.179.1                   005056C00008                           020240912153431782                         00000      00000                                                                        EN                                                                                                                                              00000000                      ";
			String panjang3 = "00000529LeRuccoL202409200952040020000000SED03F209R              MTI S                        LeRuccoL20240920095204002000000020240920095204000731WEB       172.16.20.11                                WED030120H N1787130271     020240920095204000733                    A000000      00010                                                                        EN                                                                                                                                             D00000026                     Z900 ";

			byte[] input = panjang1.getBytes();
			
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
			String message = new String(dataMessage);
			logger.info("RESULT: [" + message + "]");
//			response.getWriter().append(message.toString()).append(request.getContextPath());
			response.getWriter().append(message.toString());
			logger.info("################################### END Con 2 LeRucco ###################################");
		}

	}

}
