package uo.ri.cws.application.ui.manager.contracts.professionalgroup.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class AddProfessionalGroupAction implements Action {

    @Override
    public void execute() throws BusinessException {

        String name = Console.readString("Professional group name");
        double trienniumPayment = Console.readDouble("Triennium payment");
        double productivityRate = Console.readDouble("Productivity rate");

        ProfessionalGroupDto pgDto = new ProfessionalGroupDto();
        pgDto.trienniumPayment = trienniumPayment;
        pgDto.productivityRate = productivityRate;
        pgDto.name = name;
        Factories.service.forProfessionalGroupCrudService().create(pgDto);

        Console.println("Professional group registered");
    }
}