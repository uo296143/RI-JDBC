package uo.ri.cws.application.service.invoice.create.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;

public class FindInvoiceById {
	
	private static final String TINVOICES_FINDBYID = 
	            "SELECT ID, VERSION, AMOUNT, VAT, NUMBER, DATE FROM TINVOICES "
	                    + "WHERE ID = ?";
	private String id;

	public FindInvoiceById(String id) {
		this.id = id;
	}
	
	public Optional<InvoiceDto> execute(){
		
		 Optional<InvoiceDto> result = Optional.empty();
		 try (Connection c = Jdbc.createThreadConnection()) {
	            try (PreparedStatement pst = c
	                    .prepareStatement(TINVOICES_FINDBYID)) {
	                pst.setString(1, id);
	                try (ResultSet rs = pst.executeQuery()) {
	                    if (rs.next()) {
	                        InvoiceDto invoice = new InvoiceDto();
	                        invoice.id = rs.getString(1);
	                        invoice.version = rs.getLong(2);
	                        invoice.amount = rs.getDouble(3);
	                        invoice.vat = rs.getDouble(4);
	                        invoice.number = rs.getLong(5);
	                        invoice.date = rs.getDate(6).toLocalDate();
	                        invoice.state = rs.getString(7);
	                        result = Optional.of(invoice);
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
		 return result;
      
	}

}
