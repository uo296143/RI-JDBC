package uo.ri.cws.application.ui.manager.contracts.professionalgroup.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateProfessionalGroupAction implements Action {

    @Override
    public void execute() throws BusinessException {
        String name = Console.readString("Professional group name");                
        double trienniumPayment = Console.readDouble("Triennium payment");
        double productivityRate = Console.readDouble("Productivity rate");
        Optional<ProfessionalGroupDto> optionalDto = Factories.service.forProfessionalGroupCrudService().findByName(name);
        if(optionalDto.isEmpty()) {
        	Console.print("El grupo profesional no existe");
        }else {
        	ProfessionalGroupDto dto = optionalDto.get();
        	dto.name = name;
            dto.trienniumPayment = trienniumPayment;
            dto.productivityRate = productivityRate;
            Factories.service.forProfessionalGroupCrudService().update(dto);
            Console.println("Professional group updated");
        }
        
    }
}