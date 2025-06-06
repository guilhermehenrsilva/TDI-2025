package br.edu.ifsuldeminas.mch.suap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Welcome {
	
	@RequestMapping("/bem_vindo")
     public String ola(Model model) {
		
		
		model.addAttribute("mensagem", "Seja bem vindo ao meu site.");
		
		return "Welcome.html";
	}
}