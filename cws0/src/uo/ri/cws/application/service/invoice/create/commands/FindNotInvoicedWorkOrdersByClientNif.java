package uo.ri.cws.application.service.invoice.create.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindNotInvoicedWorkOrdersByClientNif {

	private static final String TWORKORDERS_FINDNOTINVOICED = 
            "select a.id, a.description, a.date, a.state, a.amount"
            + " from TWorkOrders as a, TVehicles as v, TClients as c"
            + " where a.vehicle_id = v.id" + " and v.client_id = c.id"
            + " and state like 'FINISHED' and nif like ?";
	private String nif;
	
	
	public FindNotInvoicedWorkOrdersByClientNif(String nif) {
		ArgumentChecks.isNotNull(nif);
		this.nif = nif;
	}
	 
	/**
     * Process: 
     * - Ask customer nif 
     * - Display all uncharged workorder (status <> 'INVOICED'). 
     *   For each workorder, display id, vehicle id, date, status, amount 
     *   and description
	 * @return 
     */
	 public List<InvoicingWorkOrderDto> execute() throws BusinessException {
	       
		 List<InvoicingWorkOrderDto> invoicingWOs = new ArrayList<InvoicingWorkOrderDto>();
		 InvoicingWorkOrderDto invoicingWO = new InvoicingWorkOrderDto();
		 
		 try (Connection c = Jdbc.createThreadConnection()) {
			 try (PreparedStatement pst = c
					 .prepareStatement(TWORKORDERS_FINDNOTINVOICED)) {
				 pst.setString(1, nif);
				 try (ResultSet rs = pst.executeQuery();) {
					 while (rs.next()) {
						invoicingWO.id = rs.getString(1);
						invoicingWO.description = rs.getString(2);
						invoicingWO.date = rs.getDate(3).toLocalDate().atStartOfDay();
						invoicingWO.state = rs.getString(4);
						invoicingWO.amount = rs.getDouble(5);
						invoicingWOs.add(invoicingWO);
					 }			 
				 }
			 }
		 } catch (SQLException e) {
			 throw new RuntimeException(e); 
		 }

		 return invoicingWOs;
	 }

}
